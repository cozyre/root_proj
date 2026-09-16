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

    public function createItem(): void {
        $user = $this->auth->requireAuth();
        
        $input = json_decode(file_get_contents('php://input'), true);
 
        if (!$input) {
            $this->error(400, 'Invalid JSON input');
            return;
        }
 
        // Validate required fields
        $required = ['itenary_id', 'start_time', 'end_time', 'type', 'description'];
        foreach ($required as $field) {
            if (!isset($input[$field])) {
                $this->error(400, "$field is required");
                return;
            }
        }
 
        // Validate time format
        if (!$this->isValidTime($input['start_time']) || 
            !$this->isValidTime($input['end_time'])) {
            $this->error(400, 'start_time and end_time must be in HH:MM format');
            return;
        }
 
        $result = $this->model->createItem(
            (int) $input['itenary_id'],
            $input['start_time'],
            $input['end_time'],
            $input['type'],
            $input['description']
        );
 
        if ($result) {
            $this->success(201, $result, 'Item created successfully');
        } else {
            $this->error(500, 'Failed to create item');
        }
    }

    public function updateItem(): void {
        $user = $this->auth->requireAuth();
 
        $itemId = isset($_GET['id']) ? (int) $_GET['id'] : 0;
        if (!$itemId) {
            $this->error(400, 'item id is required in query parameter');
            return;
        }
 
        $input = json_decode(file_get_contents('php://input'), true);
 
        if (!$input) {
            $this->error(400, 'Invalid JSON input');
            return;
        }
 
        // Validate required fields
        $required = ['start_time', 'end_time', 'type', 'description'];
        foreach ($required as $field) {
            if (!isset($input[$field])) {
                $this->error(400, "$field is required");
                return;
            }
        }
 
        // Validate time format
        if (!$this->isValidTime($input['start_time']) || 
            !$this->isValidTime($input['end_time'])) {
            $this->error(400, 'start_time and end_time must be in HH:MM format');
            return;
        }
 
        $result = $this->model->updateItem(
            $itemId,
            $input['start_time'],
            $input['end_time'],
            $input['type'],
            $input['description']
        );
 
        if ($result) {
            $this->success(200, $result, 'Item updated successfully');
        } else {
            $this->error(500, 'Failed to update item');
        }
    }

    public function deleteItem(): void {
        $user = $this->auth->requireAuth();
 
        $itemId = isset($_GET['id']) ? (int) $_GET['id'] : 0;
        if (!$itemId) {
            $this->error(400, 'item id is required in query parameter');
            return;
        }
 
        if ($this->model->deleteItem($itemId)) {
            $this->success(200, ['deleted' => true, 'id' => $itemId], 'Item deleted successfully');
        } else {
            $this->error(500, 'Failed to delete item');
        }
    }

    // --- Helpers ---

    private function isValidDate(string $date): bool {
        $d = DateTime::createFromFormat('Y-m-d', $date);
        return $d && $d->format('Y-m-d') === $date;
    }

    private function isValidTime(string $time): bool {
        return preg_match('/^([01]\d|2[0-3]):([0-5]\d)$/', $time) === 1;
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