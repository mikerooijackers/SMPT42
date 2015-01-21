<?php

require_once 'core/init.php';

$user = new User();

if(!$user->isLoggedIn()) {
	Redirect::to('index.php');
}

if(Input::exists()) {
	if(Token::check(Input::get('token'))) {
		$validate = new Validate();
		$validation = $validate->check($_POST, array(
			'name' => array(
				'required' => true,
				'min'	=> 2,
				'max' => 50
			)
		));
		var_dump($validation);
		if($validation->passed()) {

			try {
				$user->update(array(
					'name' => Input::get('name')
				));
				echo 'OK!';
				Session::flash('home', 'Je naam is veranderd.');
				Redirect::to('index.php');

			} catch(Exception $e) {
				die($e->getMessage());
			}
		} else {
			foreach($validation->errors() as $error) {
				echo $error, '<br>';
			}
		}
	}
}
?>

<form action="" method="post">
	<div class="field">
		<label for="name">Naam:</label>
		<input type="text" name="name" value="<?php echo escape($user->data()->name); ?>">

		<input type="submit" value="Veranderen">		
		<input type="hidden" name="token" value="<?php echo Token::generate(); ?>">
	</div>
</form>