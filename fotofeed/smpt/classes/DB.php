<?php
// Database wrapper
class DB {
	// Declaratie van private properties
	private static $_instance = null;
	private $_pdo, 
			$_query, 
			$_error = false, 
			$_results, 
			$_count = 0;

	// Bouwen van de class (php magic function) en verbinden naar database
	private function __construct() {
		try {
			$this->_pdo = new PDO('mysql:host=' . Config::get('mysql/host') . ';dbname=' . Config::get('mysql/db'), Config::get('mysql/username'), Config::get('mysql/password'));
		} catch (PDOException $e) {
			die($e->getMessage());
		}
	}

	// Check of er een verbinding bestaat, zoja return deze als instance, zoniet maak een nieuwe aan dmv de constructor
	public static function getInstance() {
		if(!isset(self::$_instance)) {
			self::$_instance = new DB();
		}

		return self::$_instance;
	}

	// Standaard query opbouw voor rechtstreeks gebruik of het versimpelen van andere functies
	public function query($sql, $params = array()) {
		$this->_error = false;
		if($this->_query = $this->_pdo->prepare($sql)) {
			$x = 1;
			if(count($params)) {
				foreach($params as $param) {
					$this->_query->bindValue($x, $param);
					$x++;
				}
			}

			if($this->_query->execute()) {
				$this->_results = $this->_query->fetchAll(PDO::FETCH_OBJ);
				$this->_count = $this->_query->rowCount();
			} else {
				$this->_error = true;
			}
		}

		return $this;
	}

	// Action opbouw die het gebruiken van de DB::getInstance versimpeld en er voor zorgt dat er geen complete queries in de hoofdcode gezet hoeven te worden
	public function action($action, $table, $where = array()) {
		if(count($where) === 3) {
			$operators = array('=', '>', '<', '>=', '<=');

			$field 		= $where[0];
			$operator 	= $where[1];
			$value 		= $where[2];

			if(in_array($operator, $operators)) {
				$sql = "{$action} FROM {$table} WHERE {$field} {$operator} ?";
				if(!$this->query($sql, array($value))->error()) {
					return $this;
				}
			}
		}

		return false;

	}

	// Versimpelde Get methode voor het oproepen van data uit DB
	public function get($table, $where) {
		return $this->action('SELECT *', $table, $where);
	}

	// Versimpelde Delete methode voor het deleten van data uit DB
	public function delete($table, $where) {
		return $this->action('DELETE', $table, $where);
	}

	// Functie voor het invoeren van data dmv van arrays die de velden bepalen en de values die de velden invullen
	public function insert($table, $fields = array()) {
			$keys 	= array_keys($fields);
			$values = '';
			$x = 1;

			foreach($fields as $field) {
				$values .= '?';
				if($x < count($fields)) {
					$values .= ', ';
				}
				$x++;
			}

			$sql = "INSERT INTO {$table} (`" . implode('`, `', $keys) . "`) VALUES ({$values})";

			if(!$this->query($sql, $fields)->error()) {
				return true;
			}
		
		return false;
	}

	// Functie voor het updaten van data dmv van array die de velden en values van die velden bepalen en in de query invullen
	public function update($table, $id, $fields) {
		$set = '';
		$x = 1;

		foreach($fields as $name => $value) {
			$set .= "{$name} = ?";
			if($x < count($fields)) {
				$set .= ',';
			}
			$x++;
		}
		$sql = "UPDATE {$table} SET {$set} WHERE id = {$id}";
		echo $sql;
		if(!$this->query($sql, $fields)->error()) {

			return true;
		}
		return false;
	}

	// Functie die resultaten terug geeft van een opgevraagde query
	public function results() {
		return $this->_results;
	}

	// Functie die het eerste resultaat terug geeft van een opgevraagde query
	public function first() {
		return $this->results()[0];
	}

	// Check of de error boolean op true of false staat
	public function error() {
		return $this->_error;
	}

	// Check of er data in het object zit
	public function count() {
		return $this->_count;
	}
}