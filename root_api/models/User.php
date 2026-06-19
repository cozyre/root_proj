<?php
class User {
    private PDO $db;
    private string $table = 'users';

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    // Find user by email OR username (for login)
    public function findByIdentifier(string $identifier): ?array {
        $stmt = $this->db->prepare(
            "SELECT * FROM {$this->table}
             WHERE (email = :identifier OR username = :identifier)
             AND deleted_at IS NULL
             LIMIT 1"
        );
        $stmt->execute(['identifier' => $identifier]);
        return $stmt->fetch() ?: null;
    }

    // Check if email already exists
    public function emailExists(string $email): bool {
        $stmt = $this->db->prepare(
            "SELECT id FROM {$this->table} WHERE email = :email AND deleted_at IS NULL LIMIT 1"
        );
        $stmt->execute(['email' => $email]);
        return (bool) $stmt->fetch();
    }

    // Check if username already exists
    public function usernameExists(string $username): bool {
        $stmt = $this->db->prepare(
            "SELECT id FROM {$this->table} WHERE username = :username AND deleted_at IS NULL LIMIT 1"
        );
        $stmt->execute(['username' => $username]);
        return (bool) $stmt->fetch();
    }

    // Create new user, returns new user ID or null
    public function create(array $data): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO {$this->table}
                (username, email, password_hash, first_name, last_name, phone, role, created_at, updated_at)
             VALUES
                (:username, :email, :password_hash, :first_name, :last_name, :phone, 'user', NOW(), NOW())"
        );
        $stmt->execute([
            'username'      => $data['username'],
            'email'         => $data['email'],
            'password_hash' => password_hash($data['password'], PASSWORD_BCRYPT),
            'first_name'    => $data['first_name'],
            'last_name'     => $data['last_name'],
            'phone'         => $data['phone'] ?? null,
        ]);

        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }

    // Get public-safe user data (no password hash)
    public function findById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, username, email, first_name, last_name, phone, profile_photo_url, role, created_at
             FROM {$this->table}
             WHERE id = :id AND deleted_at IS NULL LIMIT 1"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch() ?: null;
    }
}