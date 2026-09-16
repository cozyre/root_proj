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
            $this->fail(403, 'Forbidden: admin only');
            return;
        }

        $input = json_decode(file_get_contents("php://input"), true) ?? [];
        $groupId = filter_var($input['groupId'] ?? null, FILTER_VALIDATE_INT, [
            'options' => ['min_range' => 1],
        ]);
        $message = is_string($input['message'] ?? null) ? trim($input['message']) : '';

        $errors = [];
        if ($groupId === false) $errors['groupId'] = 'groupId must be a positive integer';
        if ($message === '') $errors['message'] = 'message is required';
        if (strlen($message) > 2000) $errors['message'] = 'message must not exceed 2000 characters';
        if (!empty($errors)) {
            http_response_code(422);
            echo json_encode(["status" => "error", "data" => null, "message" => "Validation failed", "errors" => $errors]);
            return;
        }

        try {
            $result = $this->model->broadcast($groupId, $message, $userId);
            echo json_encode(["status" => "success", "data" => $result]);
        } catch (Exception $e) {
            http_response_code(500);
            $this->fail(500, 'Database error while broadcasting notification');
        }
    }

    // GET ?route=notification/list
    public function list() {
        $user = $this->auth->requireAuth();
        $userId = (int) $user['id'];
        try {
            $notifications = $this->model->getForUser($userId);
            echo json_encode(["status" => "success", "data" => $notifications]);
        } catch (Throwable $e) {
            $this->fail(500, 'Failed to load notifications');
        }
    }

    // POST ?route=notification/markRead
    public function markRead() {
        $user = $this->auth->requireAuth();
        $userId = (int) $user['id'];
        $input = json_decode(file_get_contents("php://input"), true);
        $id = filter_var($input['notificationId'] ?? null, FILTER_VALIDATE_INT, [
            'options' => ['min_range' => 1],
        ]);
        if ($id === false) {
            http_response_code(422);
            echo json_encode(["status" => "error", "data" => null, "message" => "notificationId must be a positive integer"]);
            return;
        }
        try {
            if ($this->model->markRead($id, $userId) === 0) {
                $this->fail(404, 'Notification not found');
                return;
            }
            echo json_encode(["status" => "success", "data" => null]);
        } catch (Throwable $e) {
            $this->fail(500, 'Failed to mark notification as read');
        }
    }

    private function fail(int $code, string $message): void {
        http_response_code($code);
        echo json_encode(["status" => "error", "data" => null, "message" => $message]);
    }
}