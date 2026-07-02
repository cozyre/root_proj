<?php
class MemberController {

    private MemberModel    $model;
    private AuthMiddleware $auth;

    public function __construct(MemberModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    public function leaders(): void {
        $this->auth->requireAuth();
        $role = trim((string)($_GET['role'] ?? ''));

        if (!in_array($role, ['mentor', 'koordinator'], true)) {
            $this->fail(400, "role must be 'mentor' or 'koordinator'");
            return;
        }

        $this->ok($this->model->getByRole($role));
    }
    
    // GET ?route=member/list&group_id=X
    public function list(): void {
        $this->auth->requireAuth();
        $groupId = (int)($_GET['group_id'] ?? 0);

        if (!$groupId) { $this->fail(400, 'group_id is required'); return; }

        $this->ok($this->model->getByGroup($groupId));
    }

    // GET ?route=member/detail&user_id=X&group_id=Y
    public function detail(): void {
        $this->auth->requireAuth();
        $userId  = (int)($_GET['user_id']  ?? 0);
        $groupId = (int)($_GET['group_id'] ?? 0);

        if (!$userId || !$groupId) {
            $this->fail(400, 'user_id and group_id are required');
            return;
        }

        $member = $this->model->getDetail($userId, $groupId);
        if (!$member) { $this->fail(404, 'Member not found in this group'); return; }

        $this->ok($member);
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
}