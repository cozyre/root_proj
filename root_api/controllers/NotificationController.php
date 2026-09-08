<?php
require_once __DIR__ . '/../models/NotificationModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class NotificationController {
    private NotificationModel $model;
    private AuthMiddleware $auth;
    // private $db;
    // private $notification;

    public function __construct(NotificationModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // POST ?route=notification/broadcast  (admin only)
    public function broadcast() {
        $user = $this->auth->requireAuth();
        $userId = (int) $user['id'];
        if ($user['role'] !== 'admin') {
            http_response_code(403);
            echo json_encode(["status" => "error", "message" => "Forbidden: admin only"]);
            return;
        }

        $input = json_decode(file_get_contents("php://input"), true);
        $groupId = $input['groupId'] ?? null;
        $message = trim($input['message'] ?? '');

        $errors = [];
        if (!$groupId) $errors['groupId'] = 'groupId is required';
        if ($message === '') $errors['message'] = 'message is required';
        if (!empty($errors)) {
            http_response_code(422);
            echo json_encode(["status" => "error", "message" => "Validation failed", "errors" => $errors]);
            return;
        }

        try {
            $result = $this->model->broadcast($groupId, $message, $userId);
            echo json_encode(["status" => "success", "data" => $result]);
        } catch (Exception $e) {
            http_response_code(500);
            echo json_encode(["status" => "error", "message" => "Failed to broadcast notification"]);
        }
    }

    // GET ?route=notification/list
    public function list() {
        $user = $this->auth->requireAuth();
        $userId = (int) $user['id'];
        $notifications = $this->model->getForUser($userId);
        echo json_encode(["status" => "success", "data" => $notifications]);
    }

    // POST ?route=notification/markRead
    public function markRead() {
        $user = $this->auth->requireAuth();
        $userId = (int) $user['id'];
        $input = json_decode(file_get_contents("php://input"), true);
        $id = $input['notificationId'] ?? null;
        if (!$id) {
            http_response_code(422);
            echo json_encode(["status" => "error", "message" => "notificationId required"]);
            return;
        }
        $this->model->markRead($id, $userId);
        echo json_encode(["status" => "success", "data" => null]);
    }
}