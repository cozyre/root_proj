<?php
class GroupController {
    private GroupModel $model;

    public function __construct(GroupModel $model) {
        $this->model = $model;
    }

    // GET ?route=group/history  (auth required)
    public function history(array $query, array $authUser): void {
        $limit = min(max((int)($query['limit'] ?? 10), 1), 50);
        $tours = $this->model->getJoinedToursByUser((int)$authUser['id'], $limit);
        $this->ok($tours);
    }

    // GET ?route=group/index  (public)
    public function index(): void {
        $this->ok($this->model->getAllUpcoming());
    }

    // GET ?route=group/show&id=5  (public)
    public function show(array $query): void {
        $id = (int)($query['id'] ?? 0);
        if (!$id) { $this->fail(400, 'id is required'); return; }

        $tour = $this->model->getById($id);
        if (!$tour) { $this->fail(404, 'Tour not found'); return; }

        $this->ok($tour);
    }

    private function ok($data, string $message = ''): void {
        http_response_code(200);
        echo json_encode(['success' => true, 'data' => $data, 'message' => $message]);
    }

    private function fail(int $code, string $message): void {
        http_response_code($code);
        echo json_encode(['success' => false, 'data' => null, 'message' => $message]);
    }
}