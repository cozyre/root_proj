<?php
class NotificationModel {
    private $db;

    public function __construct($db) {
        $this->db = $db;
    }

    public function broadcast($groupId, $message, $createdBy) {
        $this->db->beginTransaction();
        try {
            $stmt = $this->db->prepare(
                "INSERT INTO notifications (group_id, message, created_by) VALUES (?, ?, ?)"
            );
            $stmt->execute([$groupId, $message, $createdBy]);
            $notificationId = $this->db->lastInsertId();

            $stmt = $this->db->prepare(
                "SELECT user_id FROM group_members WHERE group_id = ? AND status = 'accepted'"
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
            $this->db->rollBack();
            throw $e;
        }
    }

    public function getForUser($userId, $limit = 50) {
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

    public function markRead($notificationId, $userId) {
        $stmt = $this->db->prepare(
            "UPDATE notification_recipients SET is_read = 1 WHERE notification_id = ? AND user_id = ?"
        );
        return $stmt->execute([$notificationId, $userId]);
    }
}