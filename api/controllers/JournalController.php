<?php
class JournalController {

    private JournalModel    $model;
    private AuthMiddleware  $auth;

    public function __construct(JournalModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // POST ?route=journal/create
    // Body: { group_id, title, content, journal_date? }
    public function create(): void {
        $authUser = $this->auth->requireAuth();
        $body     = json_decode(file_get_contents('php://input'), true) ?? [];

        $groupId = (int)($body['group_id'] ?? 0);
        $title   = trim($body['title']     ?? '');
        $content = trim($body['content']   ?? '');
        $date    = $body['journal_date']   ?? date('Y-m-d');

        if (!$groupId)       { $this->fail(400, 'group_id is required'); return; }
        if (empty($title))   { $this->fail(400, 'title is required');    return; }
        if (empty($content)) { $this->fail(400, 'content is required');  return; }
        if (!$this->validDate($date)) {
            $this->fail(400, 'Invalid journal_date. Use YYYY-MM-DD');
            return;
        }

        $userId = (int)$authUser['id'];
        $id     = $this->model->create($userId, $groupId, $title, $content, $date);

        if (!$id) { $this->fail(500, 'Failed to create journal'); return; }

        $this->ok($this->model->getById($id), 'Journal created', 201);
    }

    // GET ?route=journal/list&group_id=X
    public function list(): void {
        $authUser = $this->auth->requireAuth();
        $groupId  = (int)($_GET['group_id'] ?? 0);

        if (!$groupId) { $this->fail(400, 'group_id is required'); return; }

        $this->ok($this->model->getByUserAndGroup((int)$authUser['id'], $groupId));
    }

    // POST ?route=journal/update
    // Body: { id, title, content, journal_date }
    public function update(): void {
        $authUser  = $this->auth->requireAuth();
        $body      = json_decode(file_get_contents('php://input'), true) ?? [];

        $journalId = (int)($body['id']          ?? 0);
        $title     = trim($body['title']        ?? '');
        $content   = trim($body['content']      ?? '');
        $date      = $body['journal_date']      ?? date('Y-m-d');

        if (!$journalId)     { $this->fail(400, 'id is required');       return; }
        if (empty($title))   { $this->fail(400, 'title is required');    return; }
        if (empty($content)) { $this->fail(400, 'content is required');  return; }
        if (!$this->validDate($date)) {
            $this->fail(400, 'Invalid journal_date. Use YYYY-MM-DD');
            return;
        }

        $updated = $this->model->update($journalId, (int)$authUser['id'], $title, $content, $date);

        if (!$updated) { $this->fail(404, 'Journal not found or not yours to update'); return; }

        $this->ok($this->model->getById($journalId), 'Journal updated');
    }

    // POST ?route=journal/delete
    // Body: { id }
    public function delete(): void {
        $authUser  = $this->auth->requireAuth();
        $body      = json_decode(file_get_contents('php://input'), true) ?? [];
        $journalId = (int)($body['id'] ?? 0);

        if (!$journalId) { $this->fail(400, 'id is required'); return; }

        $deleted = $this->model->softDelete($journalId, (int)$authUser['id']);

        if (!$deleted) { $this->fail(404, 'Journal not found or not yours to delete'); return; }

        $this->ok(null, 'Journal deleted');
    }

    // ─── Helpers ─────────────────────────────────────────────────────────

    private function ok($data, string $message = '', int $code = 200): void {
        http_response_code($code);
        echo json_encode(['success' => true, 'data' => $data, 'message' => $message]);
    }

    private function fail(int $code, string $message): void {
        http_response_code($code);
        echo json_encode(['success' => false, 'data' => null, 'message' => $message]);
    }

    private function validDate(string $date): bool {
        $d = DateTime::createFromFormat('Y-m-d', $date);
        return $d && $d->format('Y-m-d') === $date;
    }
}