<?php

// Een functie om data die in de DB wordt gezet te cleanen zodat mysql string-injectie niet voor kan komen!
function escape($string) {
	return htmlentities($string, ENT_QUOTES, 'UTF-8');
}

?>