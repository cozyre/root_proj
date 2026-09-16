<?php

require_once __DIR__ . '/../models/DevotionModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class DevotionController {
    private DevotionModel $model;
    private AuthMiddleware $auth;

    public function __construct(DevotionModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    /**
     * GET ?route=devotion/get&group_id=1&date=2025-12-01
     * Returns the devotion for a specific date.
     */
    public function get(): void {
        $user = $this->auth->requireAuth();

        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;
        $date    = trim($_GET['date'] ?? '');

        if (!$groupId) {
            $this->error(400, 'group_id is required');
            return;
        }
        if (!$date || !$this->isValidDate($date)) {
            $this->error(400, 'date is required and must be in YYYY-MM-DD format');
            return;
        }

        $devotion = $this->model->getByGroupAndDate($groupId, $date);

        if (!$devotion) {
            $this->error(404, 'No devotion found for this date');
            return;
        }

        $this->success(200, $devotion);
    }

    /**
     * GET ?route=devotion/getDates&group_id=1
     * Returns all dates that have a devotion (for navigation).
     */
    public function getDates(): void {
        $user = $this->auth->requireAuth();

        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;

        if (!$groupId) {
            $this->error(400, 'group_id is required');
            return;
        }

        $dates = $this->model->getAvailableDates($groupId);
        $this->success(200, $dates);
    }

    // --- Helpers ---

    private function isValidDate(string $date): bool {
        $d = DateTime::createFromFormat('Y-m-d', $date);
        return $d && $d->format('Y-m-d') === $date;
    }

    private function success(int $code, $data, string $message = ''): void {
        http_response_code($code);
        echo json_encode(['success' => true, 'data' => $data, 'message' => $message]);
    }

    private function error(int $code, string $message): void {
        http_response_code($code);
        echo json_encode(['success' => false, 'data' => null, 'message' => $message]);
    }
}