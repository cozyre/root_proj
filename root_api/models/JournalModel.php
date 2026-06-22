<?php
class JournalModel {

    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    // ─── Create ──────────────────────────────────────────────────────────

    public function create(int $userId, int $groupId, string $title, string $content, string $date): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO journals (user_id, group_id, title, content, journal_date, created_at, updated_at)
             VALUES (:user_id, :group_id, :title, :content, :date, NOW(), NOW())"
        );
        $stmt->execute([
            'user_id'  => $userId,
            'group_id' => $groupId,
            'title'    => $title,
            'content'  => $content,
            'date'     => $date,
        ]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }

    // ─── Read ────────────────────────────────────────────────────────────

    /** Get all journals for a specific user in a specific group. */
    public function getByUserAndGroup(int $userId, int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT id, user_id, group_id, title, content, journal_date, created_at, updated_at
             FROM journals
             WHERE user_id = :user_id
               AND group_id = :group_id
               AND deleted_at IS NULL
             ORDER BY journal_date DESC, created_at DESC"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, user_id, group_id, title, content, journal_date, created_at, updated_at
             FROM journals
             WHERE id = :id AND deleted_at IS NULL"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    // ─── Update ──────────────────────────────────────────────────────────

    /** user_id check ensures only the owner can update. */
    public function update(int $id, int $userId, string $title, string $content, string $date): bool {
        $stmt = $this->db->prepare(
            "UPDATE journals
             SET title = :title, content = :content, journal_date = :date, updated_at = NOW()
             WHERE id = :id AND user_id = :user_id AND deleted_at IS NULL"
        );
        $stmt->execute([
            'title'   => $title,
            'content' => $content,
            'date'    => $date,
            'id'      => $id,
            'user_id' => $userId,
        ]);
        return $stmt->rowCount() > 0;
    }

    // ─── Delete (soft) ───────────────────────────────────────────────────

    public function softDelete(int $id, int $userId): bool {
        $stmt = $this->db->prepare(
            "UPDATE journals
             SET deleted_at = NOW()
             WHERE id = :id AND user_id = :user_id AND deleted_at IS NULL"
        );
        $stmt->execute(['id' => $id, 'user_id' => $userId]);
        return $stmt->rowCount() > 0;
    }
}