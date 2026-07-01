<?php
class ProfileModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    public function getById(int $userId): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, username, first_name, last_name, email,
                    phone, profile_photo_url, bio, hide_phone, role, created_at
             FROM users
             WHERE id = :id AND deleted_at IS NULL
             LIMIT 1"
        );
        $stmt->execute(['id' => $userId]);
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        if (!$row) return null;

        $row['hide_phone'] = (bool) $row['hide_phone'];
        return $row;
    }

    /** Returns false only on query failure; 0 changed rows (same data) is still ok. */
    public function update(int $userId, array $data): bool {
        $stmt = $this->db->prepare(
            "UPDATE users
             SET first_name = :first_name,
                 last_name  = :last_name,
                 username   = :username,
                 phone      = :phone,
                 bio        = :bio,
                 hide_phone = :hide_phone,
                 updated_at = NOW()
             WHERE id = :id AND deleted_at IS NULL"
        );
        return $stmt->execute([
            'first_name' => $data['first_name'],
            'last_name'  => $data['last_name'],
            'username'   => $data['username'],
            'phone'      => $data['phone'],
            'bio'        => $data['bio'],
            'hide_phone' => $data['hide_phone'] ? 1 : 0,
            'id'         => $userId,
        ]);
    }

    /** Check username conflict, excluding the owner's own row. */
    public function usernameExistsExcept(string $username, int $userId): bool {
        $stmt = $this->db->prepare(
            "SELECT id FROM users
             WHERE username = :username AND id != :id AND deleted_at IS NULL
             LIMIT 1"
        );
        $stmt->execute(['username' => $username, 'id' => $userId]);
        return (bool) $stmt->fetch();
    }
}