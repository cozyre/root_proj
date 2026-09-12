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
    private const PUBLIC_BASE = 'uploads/profile_photos/';
    private const DEFAULT_PUBLIC_URL = 'http://10.0.2.2/root_proj/root_api/public/';

    public function __construct(ProfileModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // POST ?route=profile/uploadPhoto
    public function uploadPhoto(): void {
        $user = $this->auth->requireAuth(); // exits on failure
        $userId = (int) $user['id'];

        if (!isset($_FILES['image']) || $_FILES['image']['error'] !== UPLOAD_ERR_OK) {
            $this->fail(400, 'No image uploaded or upload error');
            return;
        }

        $file = $_FILES['image'];

        // Validate type
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        if ($finfo === false) {
            $this->fail(500, 'Unable to validate uploaded image');
            return;
        }
        $mime = finfo_file($finfo, $file['tmp_name']);
        finfo_close($finfo);

        if (!in_array($mime, self::ALLOWED_TYPES, true)) {
            $this->fail(400, 'Invalid file type. Only JPG, PNG, WEBP allowed');
            return;
        }

        // Validate size
        if ($file['size'] > self::MAX_SIZE) {
            $this->fail(400, 'File too large. Max 5MB');
            return;
        }

        // Ensure upload dir exists
        if (!is_dir(self::UPLOAD_DIR) && (!mkdir(self::UPLOAD_DIR, 0755, true) && !is_dir(self::UPLOAD_DIR))) {
            $this->fail(500, 'Failed to create upload directory');
            return;
        }

        $ext = match ($mime) {
            'image/jpeg' => 'jpg',
            'image/png' => 'png',
            'image/webp' => 'webp',
        };
        $filename = 'user_' . $userId . '_' . uniqid('', true) . '.' . $ext;
        $destination = self::UPLOAD_DIR . $filename;

        if (!move_uploaded_file($file['tmp_name'], $destination)) {
            $this->fail(500, 'Failed to save uploaded file');
            return;
        }

        try {
            $oldPhoto = $this->model->getCurrentPhotoPath($userId);
            $relativePath = self::PUBLIC_BASE . $filename;
            $photoUrl = rtrim($_ENV['API_PUBLIC_URL'] ?? self::DEFAULT_PUBLIC_URL, '/') . '/' . $relativePath;

            if (!$this->model->updatePhoto($userId, $photoUrl)) {
                unlink($destination);
                $this->fail(500, 'Failed to update profile photo');
                return;
            }
        } catch (Throwable $e) {
            if (is_file($destination)) {
                unlink($destination);
            }
            $this->fail(500, 'Failed to update profile photo');
            return;
        }

        // Remove the previous image only after the new file and DB update succeed.
        $this->deleteProfilePhoto($oldPhoto ?? null);

        $updatedProfile = $this->model->getById($userId);

        $this->ok($updatedProfile, 'Profile photo uploaded');
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

    private function deleteProfilePhoto(?string $photoPath): void {
        if (!$photoPath) return;

        $filename = basename(parse_url($photoPath, PHP_URL_PATH) ?: '');
        if ($filename === '') return;

        $uploadDir = realpath(self::UPLOAD_DIR);
        $oldFile = realpath(self::UPLOAD_DIR . $filename);
        if ($uploadDir !== false && $oldFile !== false
            && dirname($oldFile) === $uploadDir && is_file($oldFile)) {
            unlink($oldFile);
        }
    }
}