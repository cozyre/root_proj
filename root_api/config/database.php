<?php
class Database {
    private $host = "localhost";
    private $db_name = "root_db";
    private $username = "root";
    private $password = "";


    public $conn;
    public function connect() {
        $this->conn = null;
        try {
            $this->conn = new PDO(
                "mysql:host=" . $this->host . ";dbname=" . $this->db_name,
                $this->username,
                $this->password
            );
            $this->conn->exec("set names utf8");
            echo "Database successfully connected.";
        } catch(PDOException $e) {
            echo "Connection Error: " . $e->getMessage();
        }
        return $this->conn;
    }
}

