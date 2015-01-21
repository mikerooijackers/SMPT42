<?php
// Begin een sessie zodat mensen in kunnen loggen
session_start();

// Declareer globale variabelen, zoals database connectie, cookies en onthoud-tijd deze worden in de classes opgeroepen
$GLOBALS['config'] = array(
	'mysql' => array(
		'host' => '127.0.0.1',
		'username' => 'root',
		'password' => '',
		'db' => 'photogallery'
	),
	'remember' => array(
		'cookie_name' => 'hash',
		'cookie_expiry' => 604800
	),
	'session' => array(
		'session_name' => 'user',
		'token_name'   => 'token'
	)
);

// Automatisch inladen van alle bestanden in de classes folder, dit zodat deze allemaal aan de init.php file gekoppeld staan
spl_autoload_register(function($class) {
	require_once 'classes/' . $class . '.php';
});

// Inladen van functies in de functions folder
require_once 'functions/sanitize.php';

if(Cookie::exists(Config::get('remember/cookie_name')) && !Session::exists(Config::get('session/session_name'))) {
	$hash = Cookie::get(Config::get('remember/cookie_name'));
	$hashCheck = DB::getInstance()->get('users_session', array('hash', '=', $hash));

	if($hashCheck->count()) {
		$user = new User($hashCheck->first()->user_id);
		$user->login();
	}
}
?>