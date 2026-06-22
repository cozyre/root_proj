<?php
class GroupImageController {

    private GroupImageModel $model;
    private AuthMiddleware  $auth;

    private const UPLOAD_DIR   = __DIR__ . '/../../uploads/gallery/';
    private const ALLOWED_MIME = ['image/jpeg', 'image/png', 'image/webp'];
    private const MAX_BYTES    = 5 * 1024 * 1024; // 5 MB

    public function __construct(GroupImageModel $model, AuthMiddleware $auth) {
        $this->model = $model;
        $this->auth  = $auth;
    }

    // GET ?route=gallery/list&group_id=X
    public function list(): void {
        $this->auth->requireAuth();
        $groupId = (int)($_GET['group_id'] ?? 0);

        if (!$groupId) { $this->fail(400, 'group_id is required'); return; }

        $this->ok($this->model->getByGroup($groupId));
    }

    // POST ?route=gallery/upload  (multipart/form-data)
    // Fields: group_id, caption (optional)
    // File:   image
    //
    // Android sends the same multipart call whether the image came from the
    // device gallery or the camera — the difference is purely on the Android
    // side (how the URI/File is prepared before calling Retrofit).
    public function upload(): void {
        $this->auth->requireAuth();

        $groupId = (int)($_POST['group_id'] ?? 0);
        $caption = trim($_POST['caption']   ?? '') ?: null;

        if (!$groupId) { $this->fail(400, 'group_id is required'); return; }

        if (empty($_FILES['image']) || $_FILES['image']['error'] === UPLOAD_ERR_NO_FILE) {
            $this->fail(400, 'image file is required');
            return;
        }

        $file = $_FILES['image'];

        if ($file['error'] !== UPLOAD_ERR_OK) {
            $this->fail(400, 'Upload error code: ' . $file['error']);
            return;
        }

        if ($file['size'] > self::MAX_BYTES) {
            $this->fail(400, 'File too large. Max 5 MB.');
            return;
        }

        // Always verify MIME from the actual file, never trust the client header
        $finfo    = new finfo(FILEINFO_MIME_TYPE);
        $mimeType = $finfo->file($file['tmp_name']);

        if (!in_array($mimeType, self::ALLOWED_MIME)) {
            $this->fail(400, 'Invalid file type. Allowed: JPEG, PNG, WEBP');
            return;
        }

        $ext      = match ($mimeType) {
            'image/png'  => 'png',
            'image/webp' => 'webp',
            default      => 'jpg',
        };

        $filename   = 'grp' . $groupId . '_' . uniqid('', true) . '.' . $ext;
        $uploadPath = self::UPLOAD_DIR . $filename;

        if (!is_dir(self::UPLOAD_DIR)) {
            mkdir(self::UPLOAD_DIR, 0755, true);
        }

        if (!move_uploaded_file($file['tmp_name'], $uploadPath)) {
            $this->fail(500, 'Failed to move uploaded file');
            return;
        }

        $proto    = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off') ? 'https' : 'http';
        $host     = $_SERVER['HTTP_HOST'];
        $imageUrl = $proto . '://' . $host . '/root_proj/root_api/uploads/gallery/' . $filename;

        $id = $this->model->addImage($groupId, $imageUrl, $caption);
        if (!$id) { $this->fail(500, 'Failed to save image record'); return; }

        $this->ok($this->model->getById($id), 'Image uploaded', 201);
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