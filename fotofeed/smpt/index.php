<?php

require_once 'core/init.php';

if(Session::exists('home')) {
	echo '<p>' . Session::flash('home') . '</p>';
}

$user = new User();
if($user->isLoggedIn()) {
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SMPT livefeed</title>
    <link rel="stylesheet" type="text/css" href="jquery.dataTables.css">
  
<!-- jQuery -->
<script type="text/javascript" charset="utf8" src="js/jquery.js"></script>

       
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="http://mrrio.github.io/jsPDF/dist/jspdf.debug.js"></script>
        <script src="js/excellentexport.js"></script>
        <script src="js/exportpfd.js"></script>
        <script src="js/jquery.watermark.min.js"></script>
        <script src="js/search.js"></script>
        <script src="js/DioProgress.js"></script>
        <script src="js/jquery.circliful.min.js"></script>
  
<!-- DataTables -->
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/typo.css">
    <link href="css/jquery.circliful.css" rel="stylesheet" type="text/css" />
    <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/DioProgress.css"/>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,800,600' rel='stylesheet' type='text/css'>
</head>
<body>
<nav>
	<h1>HomePlan</h1>
</nav>
<section class="container">
	<div class="vertical">
		<div class="profile">
			<img src="images/profile.jpg" class="profile_pic" />
			<div class="profile_data">
				<h2 class="profile_naam">Jan Jansen</h2>
				<span class="time">Vandaag</span>
			</div>
		</div>
		<img src="images/voorbeeld1.jpg" class="activity_pic_vertical" />
			<p class="comment">Tijd voor wat koffie :)!</p>
	</div>
	<div class="horizontal">
		<div class="profile">
			<img src="images/profile.jpg" class="profile_pic" />
			<div class="profile_data">
				<h2 class="profile_naam">Jan Jansen</h2>
				<span class="time">Gisteren</span>
			</div>
		</div>
		<img src="images/voorbeeld2.jpg" class="activity_pic_horizontal" />
			<p class="comment">Tijd voor wat lekkers :)!</p>
	</div>
</section>
</body>
</html>