<?php
class MemberModel {

    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    /**
     * List all approved members of a group.
     * Returns only public-safe fields for the directory screen.
     */
    public function getByGroup(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT u.id, u.username, u.first_name, u.last_name,
                    u.profile_photo_url, u.role
            FROM users u
            INNER JOIN accounts a ON u.id = a.user_id
            WHERE a.group_id = :group_id
            AND a.status_join = 'approved'
            AND a.deleted_at IS NULL
            AND u.deleted_at IS NULL
            ORDER BY u.first_name ASC, u.last_name ASC"
        );

        $stmt->execute([
            'group_id' => $groupId
        ]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    /**
     * Detailed profile of one member, verified to be in the same group.
     * Phone is included — privacy toggle can be enforced at the DB level later
     * by adding a hide_phone column to the users table.
     */
    public function getDetail(int $userId, int $groupId): ?array {
        $stmt = $this->db->prepare(
            "SELECT u.id, u.username, u.first_name, u.last_name,
                    u.email, u.phone, u.profile_photo_url, u.role,
                    a.status_join, a.join_date, u.bio
             FROM users u
             INNER JOIN accounts a ON u.id = a.user_id
             WHERE u.id         = :user_id
               AND a.group_id   = :group_id
               AND a.status_join = 'approved'
               AND u.deleted_at  IS NULL"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    public function getByRole(string $role): array {
        $stmt = $this->db->prepare(
            "SELECT id, username, first_name, last_name,
                    profile_photo_url, role
            FROM users
            WHERE role = :role
            AND deleted_at IS NULL
            ORDER BY first_name ASC, last_name ASC"
        );
        $stmt->execute(['role' => $role]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}