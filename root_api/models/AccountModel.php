<?php

class AccountModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    // Check if user already ordered this group
    public function findByUserAndGroup(int $userId, int $groupId): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, user_id, group_id, status_join, join_date, approved_date, is_paid
             FROM accounts
             WHERE user_id = :user_id AND group_id = :group_id AND deleted_at IS NULL
             LIMIT 1"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    // Create order (new accounts row)
    public function create(int $userId, int $groupId): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO accounts (user_id, group_id, status_join, join_date, is_paid)
             VALUES (:user_id, :group_id, 'pending', NOW(), 0)"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }

    // Get order status by user + group
    public function getStatus(int $userId, int $groupId): ?array {
        $stmt = $this->db->prepare(
            "SELECT a.id, a.status_join, a.join_date, a.approved_date, a.is_paid,
                    g.name AS group_name
             FROM accounts a
             JOIN groups g ON g.id = a.group_id
             WHERE a.user_id = :user_id AND a.group_id = :group_id AND a.deleted_at IS NULL
             LIMIT 1"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    // Get group detail — only if user has an approved account row for this group
    public function getGroupDetail(int $userId, int $groupId): ?array {
        $stmt = $this->db->prepare(
            "SELECT g.id, g.name, g.description, g.start_date, g.end_date,
                    g.location, g.dresscode, g.meetup_time, g.meetup_address, g.status,
                    mentor.first_name AS mentor_first_name, mentor.last_name AS mentor_last_name,
                    mentor.profile_photo_url AS mentor_photo,
                    coord.first_name AS coordinator_first_name, coord.last_name AS coordinator_last_name,
                    coord.profile_photo_url AS coordinator_photo
             FROM groups g
             JOIN accounts a ON a.group_id = g.id
             JOIN users mentor ON mentor.id = g.mentor_id
             JOIN users coord ON coord.id = g.koordinator_id
             WHERE a.user_id = :user_id
               AND a.group_id = :group_id
               AND a.status_join = 'approved'
               AND a.deleted_at IS NULL
               AND g.deleted_at IS NULL
             LIMIT 1"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    /**
     * Get all pending orders (admin view).
     * Optionally filter by group_id.
     * Returns user + group details for each pending account.
     */
        public function getPending(?int $groupId = null): array {
        if ($groupId) {
            $stmt = $this->db->prepare(
                "SELECT a.id, a.user_id, a.group_id, a.status_join, a.join_date,
                        u.first_name, u.last_name, u.profile_photo_url,
                        g.name AS group_name
                 FROM accounts a
                 JOIN users u ON u.id = a.user_id
                 JOIN groups g ON g.id = a.group_id
                 WHERE a.status_join = 'pending'
                   AND a.group_id = :group_id
                   AND a.deleted_at IS NULL
                   AND u.deleted_at IS NULL
                   AND g.deleted_at IS NULL
                 ORDER BY a.join_date ASC"
            );
            $stmt->execute(['group_id' => $groupId]);
        } else {
            $stmt = $this->db->prepare(
                "SELECT a.id, a.user_id, a.group_id, a.status_join, a.join_date,
                        u.first_name, u.last_name, u.profile_photo_url,
                        g.name AS group_name
                 FROM accounts a
                 JOIN users u ON u.id = a.user_id
                 JOIN groups g ON g.id = a.group_id
                 WHERE a.status_join = 'pending'
                   AND a.deleted_at IS NULL
                   AND u.deleted_at IS NULL
                   AND g.deleted_at IS NULL
                 ORDER BY a.join_date ASC"
            );
            $stmt->execute();
        }
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}