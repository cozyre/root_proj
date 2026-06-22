<?php
class Database {
    private string $host     = "localhost";
    private string $db_name  = "rootandroid";
    private string $username = "root";
    private string $password = "";

    private ?PDO $conn = null;

    public function connect(): PDO {
        if ($this->conn !== null) return $this->conn;

        try {
            $this->conn = new PDO(
                "mysql:host={$this->host};dbname={$this->db_name};charset=utf8mb4",
                $this->username,
                $this->password,
                [
                    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
                    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                    PDO::ATTR_EMULATE_PREPARES   => false,
                ]
            );
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode([
                'success' => false,
                'data' => null,
                'message' => 'Database connection failed']);
            exit;
        }

        return $this->conn;
    }
}
