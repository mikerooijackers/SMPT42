<?php 
	require_once 'core/init.php';


	if(Input::exists()) {
		if(Token::check(Input::get('token'))) {
			$validate = new Validate();
			$validation = $validate->check($_POST, array(
				'username' => array(
					'required' => true,
					'min'	   => 2,
					'max'	   => 20,
					'unique'   => 'users'	
				),
				'password' => array(
					'required'	=> true,
					'min'		=> 6
				),
				'passwordagain' => array(
					'required'	=> true,
					'matches'	=> 'password'
				),
				'name' => array(
					'required'	=> true,
					'min'		=> 2,
					'max'		=> 50
				),
			));

			if($validation->passed()) {
				$user = new User();

				$salt = Hash::salt(32);
				try {
					$user->create(array(
						'username'	=> Input::get('username'),
						'password'	=> Hash::make(Input::get('password'), $salt),
						'salt'		=> $salt,
						'name'		=> Input::get('name'),
						'joined'	=> date('Y-m-d H:i:s'),
						'group'		=> 1
					));

					Session::flash('home', 'Je bent succesvol geregistreerd! Je kunt nu inloggen.');
					Redirect::to('index.php');
				} catch (Exception $e) {
					die($e->getMessage());
				}
			} else {
				//output error
				foreach($validation->errors() as $error) {
					echo $error, '<br>';
				}
			}
		}
	}

?>
<form action="" method="post">
	<div class="field">
		<label for="username">Username</label>
		<input type="text" name="username" id="username" value="<?php echo escape(Input::get('username')); ?>" autocomplete="off">
	</div>

	<div class="field">
		<label for="password">Wachtwoord</label>
		<input type="password" name="password" id="password" autocomplete="off">
	</div>

	<div class="field">
		<label for="passwordagain">Herhaal wachtwoord</label>
		<input type="password" name="passwordagain" id="passwordagain" autocomplete="off">
	</div>

	<div class="field">
		<label for="name">Naam</label>
		<input type="text" name="name" id="name" value="<?php echo escape(Input::get('name')); ?>" autocomplete="off">
	</div>
	<input type="hidden" name="token" value="<?php echo Token::generate(); ?>">
	<input type="submit" value="Registreren!">
</form>
