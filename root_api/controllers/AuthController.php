<?php
require_once __DIR__ . '/../models/User.php';

class AuthController {
    private User $userModel;

    public function __construct(PDO $db) {
        $this->userModel = new User($db);
    }

    // POST ?route=auth/register
    public function register(array $body): void {
        $required = ['first_name', 'last_name', 'username', 'email', 'password', 'password_confirmation'];
        foreach ($required as $field) {
            if (empty($body[$field])) {
                $this->error(400, "Field '{$field}' is required");
                return;
            }
        }

        if (!filter_var($body['email'], FILTER_VALIDATE_EMAIL)) {
            $this->error(400, 'Invalid email format');
            return;
        }

        if ($body['password'] !== $body['password_confirmation']) {
            $this->error(400, 'Passwords do not match');
            return;
        }

        if (strlen($body['password']) < 8) {
            $this->error(400, 'Password must be at least 8 characters');
            return;
        }

        if (!preg_match('/^[a-zA-Z0-9_]+$/', $body['username'])) {
            $this->error(400, 'Username may only contain letters, numbers, and underscores');
            return;
        }

        if ($this->userModel->emailExists($body['email'])) {
            $this->error(409, 'Email is already registered');
            return;
        }

        if ($this->userModel->usernameExists($body['username'])) {
            $this->error(409, 'Username is already taken');
            return;
        }

        $userId = $this->userModel->create([
            'username'   => $body['username'],
            'email'      => $body['email'],
            'password'   => $body['password'],
            'first_name' => trim($body['first_name']),
            'last_name'  => trim($body['last_name']),
            'phone'      => $body['phone'] ?? null,
        ]);

        if (!$userId) {
            $this->error(500, 'Failed to create account');
            return;
        }

        $user = $this->userModel->findById($userId);
        $this->success(201, $user, 'Account created successfully');
    }

    // POST ?route=auth/login
    public function login(array $body): void {
        if (empty($body['identifier'])) {
            $this->error(400, 'Email or username is required');
            return;
        }
        if (empty($body['password'])) {
            $this->error(400, 'Password is required');
            return;
        }

        $user = $this->userModel->findByIdentifier($body['identifier']);

        if (!$user || !password_verify($body['password'], $user['password_hash'])) {
            $this->error(401, 'Invalid credentials');
            return;
        }

        $token = $this->generateJWT($user);
        unset($user['password_hash']);

        $this->success(200, [
            'token' => $token,
            'user'  => $user,
        ], 'Login successful');
    }

    // HS256 JWT — FIX: added 'id' alongside 'sub' so controllers can use $user['id']
    private function generateJWT(array $user): string {
        $secret = $_ENV['JWT_SECRET'] ?? 'CHANGE_THIS_SECRET_KEY_IN_PRODUCTION';

        $encode = fn($s) => rtrim(strtr(base64_encode($s), '+/', '-_'), '=');

        $header  = $encode(json_encode(['alg' => 'HS256', 'typ' => 'JWT']));
        $payload = $encode(json_encode([
            'sub'  => $user['id'],
            'id'   => $user['id'],   // convenience alias — controllers read $user['id']
            'role' => $user['role'],
            'iat'  => time(),
            'exp'  => time() + (60 * 60 * 24 * 7),
        ]));

        $signature = $encode(hash_hmac('sha256', "{$header}.{$payload}", $secret, true));

        return "{$header}.{$payload}.{$signature}";
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