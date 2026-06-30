<?php

class AccountController {
    private AccountModel $model;
    private AuthMiddleware $auth;

    public function __construct(AccountModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // POST ?route=account/order
    public function order(): void {
        $user    = $this->auth->requireAuth();
        $body    = json_decode(file_get_contents('php://input'), true) ?? [];
        $groupId = isset($body['group_id']) ? (int) $body['group_id'] : 0;

        if (!$groupId) {
            $this->fail(400, 'group_id is required');
            return;
        }

        $existing = $this->model->findByUserAndGroup($user['id'], $groupId);
        if ($existing) {
            $this->fail(409, 'You have already ordered this trip. Current status: ' . $existing['status_join']);
            return;
        }

        $id = $this->model->create($user['id'], $groupId);
        if (!$id) {
            $this->fail(500, 'Failed to create order');
            return;
        }

        $this->ok([
            'account_id'  => $id,
            'status_join' => 'pending',
            'is_paid'     => false,
        ], 'Order submitted. Waiting for admin approval.', 201);
    }

    // GET ?route=account/status&group_id=1
    public function status(): void {
        $user    = $this->auth->requireAuth();
        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;

        if (!$groupId) {
            $this->fail(400, 'group_id is required');
            return;
        }

        $record = $this->model->getStatus($user['id'], $groupId);
        if (!$record) {
            $this->fail(404, 'No order found for this group');
            return;
        }

        $this->ok([
            'account_id'    => (int) $record['id'],
            'group_name'    => $record['group_name'],
            'status_join'   => $record['status_join'],
            'join_date'     => $record['join_date'],
            'approved_date' => $record['approved_date'],
            'is_paid'       => (bool) $record['is_paid'],
        ]);
    }

    // GET ?route=account/groupDetail&group_id=1
    public function groupDetail(): void {
        $user    = $this->auth->requireAuth();
        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : 0;

        if (!$groupId) {
            $this->fail(400, 'group_id is required');
            return;
        }

        $detail = $this->model->getGroupDetail($user['id'], $groupId);
        if (!$detail) {
            $this->fail(403, 'Access denied or group not found');
            return;
        }

        $this->ok([
            'id'             => (int) $detail['id'],
            'name'           => $detail['name'],
            'description'    => $detail['description'],
            'start_date'     => $detail['start_date'],
            'end_date'       => $detail['end_date'],
            'location'       => $detail['location'],
            'dresscode'      => $detail['dresscode'],
            'meetup_time'    => $detail['meetup_time'],
            'meetup_address' => $detail['meetup_address'],
            'status'         => $detail['status'],
            'mentor' => [
                'name'  => trim($detail['mentor_first_name'] . ' ' . $detail['mentor_last_name']),
                'photo' => $detail['mentor_photo'],
            ],
            'coordinator' => [
                'name'  => trim($detail['coordinator_first_name'] . ' ' . $detail['coordinator_last_name']),
                'photo' => $detail['coordinator_photo'],
            ],
        ]);
    }

    private function ok($data, string $message = '', int $code = 200): void {
        http_response_code($code);
        echo json_encode(['success' => true, 'data' => $data, 'message' => $message]);
    }

    private function fail(int $code, string $message): void {
        http_response_code($code);
        echo json_encode(['success' => false, 'data' => null, 'message' => $message]);
    }
}