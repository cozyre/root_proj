<?php

require_once __DIR__ . '/../models/SongModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class SongController {
    private SongModel $model;
    private AuthMiddleware $auth;

    public function __construct(SongModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    /**
     * GET ?route=song/list&group_id=1
     * All songs in a group's songbook (no day filter).
     */
    public function list(): void {
        $user    = $this->auth->requireAuth();
        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;

        if (!$groupId) { $this->error(400, 'group_id is required'); return; }

        $this->success(200, $this->model->getByGroup($groupId));
    }

    /**
     * GET ?route=song/listByDate&group_id=1&date=2025-12-01
     * Songs assigned to a specific itinerary day.
     */
    public function listByDate(): void {
        $user    = $this->auth->requireAuth();
        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;
        $date    = trim($_GET['date'] ?? '');

        if (!$groupId) { $this->error(400, 'group_id is required'); return; }
        if (!$date || !$this->isValidDate($date)) {
            $this->error(400, 'date is required in YYYY-MM-DD format');
            return;
        }

        $this->success(200, $this->model->getByGroupAndDate($groupId, $date));
    }

    public function removeFromGroup(): void
    {
        $user = $this->auth->requireAuth();

        $input = json_decode(file_get_contents('php://input'), true);

        $groupId = isset($input['group_id']) ? (int) $input['group_id'] : 0;
        $songId = isset($input['song_id']) ? (int) $input['song_id'] : 0;
        $itenaryId = isset($input['itenary_id']) ? (int) $input['itenary_id'] : null;

        if ($groupId <= 0 || $songId <= 0) {
            $this->error(400, 'group_id and song_id are required');
            return;
        }

        $deleted = $this->model->removeFromGroup(
            $groupId,
            $songId,
            $itenaryId
        );

        if ($deleted) {
            $this->success(200, [
                'group_id' => $groupId,
                'song_id' => $songId,
                'itenary_id' => $itenaryId
            ], 'Song removed from group');
            return;
        }

        $this->error(404, 'Song relation not found or already removed');
    }

    /**
     * GET ?route=song/get&id=5
     * Single song with full lyrics.
     */
    public function get(): void {
        $user = $this->auth->requireAuth();
        $id   = isset($_GET['id']) ? (int) $_GET['id'] : 0;

        if (!$id) { $this->error(400, 'id is required'); return; }

        $song = $this->model->getById($id);
        if (!$song) { $this->error(404, 'Song not found'); return; }

        $this->success(200, $song);
    }

    /**
     * GET ?route=song/search&group_id=1&q=amazing
     * Search within a group's assigned songs.
     */
    public function search(): void {
        $user    = $this->auth->requireAuth();
        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;
        $query   = trim($_GET['q'] ?? '');

        if (!$groupId) { $this->error(400, 'group_id is required'); return; }
        if ($query === '') { $this->error(400, 'q is required'); return; }

        $this->success(200, $this->model->search($groupId, $query));
    }

    /**
     * GET ?route=song/browse
     * GET ?route=song/browse&q=keyword
     * Global song library — for admin to pick songs when building a group.
     */
    public function browse(): void {
        $user  = $this->auth->requireAuth();
        $query = trim($_GET['q'] ?? '');

        $this->success(200, $this->model->browse($query));
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