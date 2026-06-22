<?php

require_once __DIR__ . '/../models/ItineraryModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class ItineraryController {
    private ItineraryModel $model;
    private AuthMiddleware $auth;

    public function __construct(ItineraryModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    /**
     * GET ?route=itinerary/getByDate&group_id=1&date=2025-12-01
     * Returns the itinerary for a specific day, including all items.
     */
    public function getByDate(): void {
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

        $itinerary = $this->model->getByGroupAndDate($groupId, $date);

        if (!$itinerary) {
            $this->error(404, 'No itinerary found for this date');
            return;
        }

        $this->success(200, $itinerary);
    }

    /**
     * GET ?route=itinerary/getDates&group_id=1
     * Returns all available itinerary dates for a group (for date navigation).
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