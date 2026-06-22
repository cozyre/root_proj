<?php
class GroupModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    public function getJoinedToursByUser(int $userId, int $limit = 10): array {
        $stmt = $this->db->prepare(
            "SELECT g.id, g.name, g.description, g.start_date, g.end_date,
                    g.location, g.status, a.join_date, a.status_join
             FROM accounts a
             JOIN groups g ON g.id = a.group_id
             WHERE a.user_id = :user_id
               AND a.deleted_at IS NULL
               AND g.deleted_at IS NULL
             ORDER BY a.join_date DESC
             LIMIT :limit"
        );
        $stmt->bindValue(':user_id', $userId, PDO::PARAM_INT);
        $stmt->bindValue(':limit',   $limit,   PDO::PARAM_INT);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getAllUpcoming(): array {
        $stmt = $this->db->prepare(
            "SELECT id, name, description, start_date, end_date,
                    location, dresscode, meetup_time, status
             FROM groups
             WHERE start_date > NOW()
               AND deleted_at IS NULL
               AND status = 'active'
             ORDER BY start_date ASC"
        );
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, name, description, start_date, end_date,
                    location, dresscode, meetup_time, meetup_address, status
             FROM groups
             WHERE id = :id AND deleted_at IS NULL"
        );
        $stmt->execute([':id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }
}