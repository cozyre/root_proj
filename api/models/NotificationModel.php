<?php
class NotificationModel {
    private $db;

    public function __construct($db) {
        $this->db = $db;
    }

    public function broadcast(int $groupId, string $message, int $createdBy): array {
        $this->db->beginTransaction();
        try {
            $stmt = $this->db->prepare(
                "INSERT INTO notifications (group_id, message, created_by) VALUES (?, ?, ?)"
            );
            $stmt->execute([$groupId, $message, $createdBy]);
            $notificationId = $this->db->lastInsertId();

            $stmt = $this->db->prepare(
                "SELECT user_id FROM accounts
                 WHERE group_id = ? AND status_join = 'approved' AND deleted_at IS NULL"
            );
            $stmt->execute([$groupId]);
            $userIds = $stmt->fetchAll(PDO::FETCH_COLUMN);

            if (count($userIds) > 0) {
                $insert = $this->db->prepare(
                    "INSERT INTO notification_recipients (notification_id, user_id) VALUES (?, ?)"
                );
                foreach ($userIds as $uid) {
                    $insert->execute([$notificationId, $uid]);
                }
            }

            $this->db->commit();
            return ['notificationId' => (int)$notificationId, 'recipientCount' => count($userIds)];
        } catch (Exception $e) {
            if ($this->db->inTransaction()) {
                $this->db->rollBack();
            }
            throw $e;
        }
    }

    public function getForUser(int $userId, int $limit = 50): array {
        $stmt = $this->db->prepare(
            "SELECT n.id, n.message, n.group_id, n.created_at, r.is_read
             FROM notification_recipients r
             JOIN notifications n ON n.id = r.notification_id
             WHERE r.user_id = ?
             ORDER BY n.created_at DESC
             LIMIT ?"
        );
        $stmt->bindValue(1, $userId, PDO::PARAM_INT);
        $stmt->bindValue(2, $limit, PDO::PARAM_INT);
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function markRead(int $notificationId, int $userId): int {
        $stmt = $this->db->prepare(
            "UPDATE notification_recipients SET is_read = 1 WHERE notification_id = ? AND user_id = ?"
        );
        $stmt->execute([$notificationId, $userId]);
        return $stmt->rowCount();
    }
}