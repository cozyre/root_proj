<?php
class GroupImageModel {

    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    /** Get all gallery images for a group (excludes 'placeholder' type). */
    public function getByGroup(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT id, group_id, image_url, caption, image_type, sort_order, created_at
             FROM group_images
             WHERE group_id = :group_id AND image_type = 'gallery'
             ORDER BY created_at DESC"
        );
        $stmt->execute(['group_id' => $groupId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, group_id, image_url, caption, image_type, sort_order, created_at
             FROM group_images
             WHERE id = :id"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    public function addImage(int $groupId, string $imageUrl, ?string $caption): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO group_images (group_id, image_url, caption, image_type, created_at)
             VALUES (:group_id, :image_url, :caption, 'gallery', NOW())"
        );
        $stmt->execute([
            'group_id'  => $groupId,
            'image_url' => $imageUrl,
            'caption'   => $caption,
        ]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }
}