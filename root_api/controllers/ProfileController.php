<?php
require_once __DIR__ . '/../models/ProfileModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';

class ProfileController {
    private ProfileModel  $model;
    private AuthMiddleware $auth;

    // Photo variables
    private const ALLOWED_TYPES = ['image/jpeg', 'image/png', 'image/webp'];
    private const MAX_SIZE = 5 * 1024 * 1024; // 5MB
    private const UPLOAD_DIR = __DIR__ . '/../public/uploads/profile_photos/';
    private const PUBLIC_BASE = 'uploads/profile_photos/'; // relative path stored in DB

    public function __construct(ProfileModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // POST ?route=profile/uploadPhoto
    public function uploadPhoto(): void {
        $user = $this->auth->requireAuth(); // exits on failure
        $userId = (int) $user['id'];

        if (!isset($_FILES['image']) || $_FILES['image']['error'] !== UPLOAD_ERR_OK) {
            http_response_code(400);
            echo json_encode([
                'status' => 'error',
                'message' => 'No image uploaded or upload error',
            ]);
            return;
        }

        $file = $_FILES['image'];

        // Validate type
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        $mime = finfo_file($finfo, $file['tmp_name']);
        finfo_close($finfo);

        if (!in_array($mime, self::ALLOWED_TYPES, true)) {
            http_response_code(400);
            echo json_encode([
                'status' => 'error',
                'message' => 'Invalid file type. Only JPG, PNG, WEBP allowed',
                'errors' => ['image' => 'Unsupported file type'],
            ]);
            return;
        }

        // Validate size
        if ($file['size'] > self::MAX_SIZE) {
            http_response_code(400);
            echo json_encode([
                'status' => 'error',
                'message' => 'File too large. Max 5MB',
                'errors' => ['image' => 'File exceeds size limit'],
            ]);
            return;
        }

        // Ensure upload dir exists
        if (!is_dir(self::UPLOAD_DIR)) {
            mkdir(self::UPLOAD_DIR, 0755, true);
        }

        $ext = match ($mime) {
            'image/jpeg' => 'jpg',
            'image/png' => 'png',
            'image/webp' => 'webp',
        };
        $filename = 'user_' . $userId . '_' . time() . '.' . $ext;
        $destination = self::UPLOAD_DIR . $filename;

        if (!move_uploaded_file($file['tmp_name'], $destination)) {
            http_response_code(500);
            echo json_encode([
                'status' => 'error',
                'message' => 'Failed to save uploaded file',
            ]);
            return;
        }

        // Delete old photo if it exists
        $oldPhoto = $this->model->getCurrentPhotoPath($userId);
        if ($oldPhoto) {
            $oldFilePath = __DIR__ . '/../public/' . $oldPhoto;
            if (file_exists($oldFilePath)) {
                unlink($oldFilePath);
            }
        }

        $relativePath = self::PUBLIC_BASE . $filename;
        $this->model->updatePhoto($userId, $relativePath);

        $updatedProfile = $this->model->getById($userId);

        echo json_encode([
            'status' => 'success',
            'data' => $updatedProfile,
        ]);
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