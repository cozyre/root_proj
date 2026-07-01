<?php
require_once __DIR__ . '/../models/ProfileModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class ProfileController {
    private ProfileModel  $model;
    private AuthMiddleware $auth;

    public function __construct(ProfileModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // GET ?route=profile/get
    public function get(): void {
        $authUser = $this->auth->requireAuth();
        $profile  = $this->model->getById((int) $authUser['id']);

        if (!$profile) {
            $this->fail(404, 'User not found');
            return;
        }

        $this->ok($profile);
    }

    // POST ?route=profile/update
    // Body: { first_name, last_name, username, phone?, bio?, hide_phone? }
    public function update(): void {
        $authUser = $this->auth->requireAuth();
        $body     = json_decode(file_get_contents('php://input'), true) ?? [];

        $firstName = trim($body['first_name'] ?? '');
        $lastName  = trim($body['last_name']  ?? '');
        $username  = trim($body['username']   ?? '');
        $phone     = trim($body['phone']      ?? '') ?: null;
        $bio       = trim($body['bio']        ?? '') ?: null;
        $hidePhone = isset($body['hide_phone']) ? (bool) $body['hide_phone'] : false;

        if (empty($firstName)) { $this->fail(400, 'first_name is required'); return; }
        if (empty($lastName))  { $this->fail(400, 'last_name is required');  return; }
        if (empty($username))  { $this->fail(400, 'username is required');   return; }

        if (!preg_match('/^[a-zA-Z0-9_]+$/', $username)) {
            $this->fail(400, 'Username may only contain letters, numbers, and underscores');
            return;
        }

        $userId = (int) $authUser['id'];

        if ($this->model->usernameExistsExcept($username, $userId)) {
            $this->fail(409, 'Username is already taken');
            return;
        }

        $this->model->update($userId, [
            'first_name' => $firstName,
            'last_name'  => $lastName,
            'username'   => $username,
            'phone'      => $phone,
            'bio'        => $bio,
            'hide_phone' => $hidePhone,
        ]);

        $this->ok($this->model->getById($userId), 'Profile updated');
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