<?php

class DevotionModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    /**
     * Get a single devotion by group and date.
     * One devotion per day per group.
     */
    public function getByGroupAndDate(int $groupId, string $date): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, group_id, title, content, devotion_date, created_at
             FROM devotions
             WHERE group_id = :group_id AND devotion_date = :date
             LIMIT 1"
        );
        $stmt->execute(['group_id' => $groupId, 'date' => $date]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    /**
     * Get all available devotion dates for a group.
     * Used for date navigation on the frontend.
     */
    public function getAvailableDates(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT devotion_date, title
             FROM devotions
             WHERE group_id = :group_id
             ORDER BY devotion_date ASC"
        );
        $stmt->execute(['group_id' => $groupId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}