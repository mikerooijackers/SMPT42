<?php

class Config {
	// Zorgt ervoor dat je de gegevens IN de global CONFIG array op een makkelijke manier kunt oproepen
	public static function get($path = null) {
		if($path) {
			$config = $GLOBALS['config'];
			$path = explode('/', $path);

			// Defineer ieder deel van de path-array als bit zodat je ze individueel kan oproepen mits de key in de config-array staat
			foreach($path as $bit) {
				if(isset($config[$bit])) {
					$config = $config[$bit];

				}
			}
			// Geef de waarde van de opgeroepte string op
			return $config;
		}

		return false;
	}
}