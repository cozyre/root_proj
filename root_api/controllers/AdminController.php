<?php

require_once __DIR__ . '/../models/AdminModel.php';
require_once __DIR__ . '/../middleware/AuthMiddleware.php';
require_once __DIR__ . '/../models/AccountModel.php';

class AdminController {

    private AccountModel $accountModel;
    private AdminModel    $model;
    private AuthMiddleware $auth;

    // Must match the upload path in GroupImageController
    private const GALLERY_DIR = __DIR__ . '/../../uploads/gallery/';

    public function __construct(AdminModel $model, AccountModel $accountModel,AuthMiddleware $auth) {
        $this->model = $model;
        $this->accountModel = $accountModel;
        $this->auth  = $auth;
    }

    // ─── 1. VIEW FINISHED TRIPS ───────────────────────────────────────────────
    // GET ?route=admin/trips
    public function trips(): void {
        $this->auth->requireRole('admin');
        $this->ok($this->model->getCompletedTrips());
    }

    // ─── 2. CREATE NEW TRIP ───────────────────────────────────────────────────
    // POST ?route=admin/trip/create
    // Body: { name, description?, start_date, end_date, location?, dresscode?,
    //         meetup_time?, meetup_address?, mentor_id, koordinator_id }
    public function tripCreate(): void {
        $admin = $this->auth->requireRole('admin');
        $body  = json_decode(file_get_contents('php://input'), true) ?? [];

        [$ok, $err] = $this->validateTripBody($body);
        if (!$ok) { $this->fail(400, $err); return; }

        $groupId = $this->model->createTrip($body, (int) $admin['id']);
        if (!$groupId) { $this->fail(500, 'Failed to create trip'); return; }

        // Auto-generate stubs for every date in range (none exist yet)
        $stubs = $this->generateStubs($groupId, $body['start_date'], $body['end_date'], [], []);

        $this->ok([
            'group'       => $this->model->getTripById($groupId),
            'dates'       => $this->model->getItineraryDates($groupId),
            'stubs_added' => $stubs,
        ], 'Trip created', 201);
    }

    // ─── 3. EDIT & UPDATE TRIP ────────────────────────────────────────────────
    // POST ?route=admin/trip/update
    // Body: { id, name, description?, start_date, end_date, location?, dresscode?,
    //         meetup_time?, meetup_address?, mentor_id, koordinator_id }
    public function tripUpdate(): void {
        $admin = $this->auth->requireRole('admin');
        $body  = json_decode(file_get_contents('php://input'), true) ?? [];

        $id = (int) ($body['id'] ?? 0);
        if (!$id) { $this->fail(400, 'id is required'); return; }

        [$ok, $err] = $this->validateTripBody($body);
        if (!$ok) { $this->fail(400, $err); return; }

        if (!$this->model->getTripById($id)) {
            $this->fail(404, 'Trip not found');
            return;
        }

        $this->model->updateTrip($id, $body);

        // Only add stubs for NEW dates — never delete or overwrite existing ones
        $existingItin = $this->model->getExistingItineraryDates($id);
        $existingDev  = $this->model->getExistingDevotionDates($id);
        $stubs = $this->generateStubs($id, $body['start_date'], $body['end_date'], $existingItin, $existingDev);

        $this->ok([
            'group'       => $this->model->getTripById($id),
            'dates'       => $this->model->getItineraryDates($id),
            'stubs_added' => $stubs,
        ], 'Trip updated');
    }

    // ─── DELETE TRIP ─────────────────────────────────────────────
    // POST ?route=admin/trip/delete
    // Body: { id }

    public function tripDelete(): void
    {
        $this->auth->requireRole('admin');

        $body = json_decode(file_get_contents('php://input'), true) ?? [];

        $id = (int)($body['id'] ?? 0);

        if (!$id) {
            $this->fail(400, 'id is required');
            return;
        }

        $trip = $this->model->getTripById($id);

        if (!$trip) {
            $this->fail(404, 'Trip not found');
            return;
        }

        $deleted = $this->model->deleteTrip($id);

        if (!$deleted) {
            $this->fail(500, 'Failed to delete trip');
            return;
        }

        $this->ok(null, 'Trip deleted');
    }

    // ─── 4a. APPROVE ORDER ────────────────────────────────────────────────────
    // POST ?route=admin/order/approve
    // Body: { account_id }
    public function orderApprove(): void {
        $admin = $this->auth->requireRole('admin');
        $body  = json_decode(file_get_contents('php://input'), true) ?? [];

        $accountId = (int) ($body['account_id'] ?? 0);
        if (!$accountId) { $this->fail(400, 'account_id is required'); return; }

        $order = $this->model->getOrderById($accountId);
        if (!$order) { $this->fail(404, 'Order not found'); return; }

        if ($order['status_join'] !== 'pending') {
            $this->fail(409, 'Order is already ' . $order['status_join']);
            return;
        }

        $this->model->approveOrder($accountId, (int) $admin['id']);
        $this->ok($this->model->getOrderById($accountId), 'Order approved');
    }

    // ─── 4b. REJECT ORDER ─────────────────────────────────────────────────────
    // POST ?route=admin/order/reject
    // Body: { account_id }
    public function orderReject(): void {
        $admin = $this->auth->requireRole('admin');
        $body  = json_decode(file_get_contents('php://input'), true) ?? [];

        $accountId = (int) ($body['account_id'] ?? 0);
        if (!$accountId) { $this->fail(400, 'account_id is required'); return; }

        $order = $this->model->getOrderById($accountId);
        if (!$order) { $this->fail(404, 'Order not found'); return; }

        if ($order['status_join'] !== 'pending') {
            $this->fail(409, 'Order is already ' . $order['status_join']);
            return;
        }

        $this->model->rejectOrder($accountId, (int) $admin['id']);
        $this->ok($this->model->getOrderById($accountId), 'Order rejected');
    }

    // ─── 5. REMOVE MEMBER ─────────────────────────────────────────────────────
    // POST ?route=admin/member/remove
    // Body: { user_id, group_id }
    public function memberRemove(): void {
        $this->auth->requireRole('admin');
        $body = json_decode(file_get_contents('php://input'), true) ?? [];

        $userId  = (int) ($body['user_id']  ?? 0);
        $groupId = (int) ($body['group_id'] ?? 0);

        if (!$userId || !$groupId) {
            $this->fail(400, 'user_id and group_id are required');
            return;
        }

        $removed = $this->model->removeMember($userId, $groupId);
        if (!$removed) { $this->fail(404, 'Member not found in this group'); return; }

        $this->ok(null, 'Member removed from trip');
    }

    // ─── 6. REMOVE GALLERY IMAGE ──────────────────────────────────────────────
    // POST ?route=admin/gallery/removeImage
    // Body: { image_id }
    public function galleryRemoveImage(): void {
        $this->auth->requireRole('admin');
        $body    = json_decode(file_get_contents('php://input'), true) ?? [];
        $imageId = (int) ($body['image_id'] ?? 0);

        if (!$imageId) { $this->fail(400, 'image_id is required'); return; }

        $image = $this->model->getImageById($imageId);
        if (!$image) { $this->fail(404, 'Image not found'); return; }

        // Extract filename from stored URL and delete physical file
        $filename = basename(parse_url($image['image_url'], PHP_URL_PATH));
        $filePath = self::GALLERY_DIR . $filename;
        if (file_exists($filePath)) {
            @unlink($filePath);
        }

        $this->model->deleteImage($imageId);
        $this->ok(null, 'Image removed');
    }

    // ─── 7. ADD SONG TO GLOBAL LIBRARY ───────────────────────────────────────
    // POST ?route=admin/song/create
    // Body: { title, author?, lyrics? }
    public function songCreate(): void {
        $this->auth->requireRole('admin');
        $body  = json_decode(file_get_contents('php://input'), true) ?? [];
        $title = trim($body['title'] ?? '');

        if (empty($title)) { $this->fail(400, 'title is required'); return; }

        $id = $this->model->createSong([
            'title'  => $title,
            'author' => trim($body['author'] ?? '') ?: null,
            'lyrics' => $body['lyrics']              ?? null,
        ]);
        if (!$id) { $this->fail(500, 'Failed to create song'); return; }

        $this->ok($this->model->getSongById($id), 'Song created', 201);
    }

    // ─── 8. ASSIGN SONG TO GROUP / ITINERARY DAY ─────────────────────────────
    // POST ?route=admin/song/addToGroup
    // Body: { song_id, group_id, itenary_id (nullable), sort_order }
    public function songAddToGroup(): void {
        $this->auth->requireRole('admin');
        $body = json_decode(file_get_contents('php://input'), true) ?? [];

        $songId    = (int) ($body['song_id']    ?? 0);
        $groupId   = (int) ($body['group_id']   ?? 0);
        $itenaryId = isset($body['itenary_id']) && $body['itenary_id'] !== null
                     ? (int) $body['itenary_id'] : null;
        $sortOrder = (int) ($body['sort_order'] ?? 0);

        if (!$songId || !$groupId) {
            $this->fail(400, 'song_id and group_id are required');
            return;
        }

        // Prevent exact-duplicate pivot rows (NULL-safe check)
        if ($this->model->songExistsInGroup($songId, $groupId, $itenaryId)) {
            $this->fail(409, 'Song is already assigned to this group/day');
            return;
        }

        $ok = $this->model->addSongToGroup($songId, $groupId, $itenaryId, $sortOrder);
        if (!$ok) { $this->fail(500, 'Failed to assign song'); return; }

        $this->ok([
            'song_id'    => $songId,
            'group_id'   => $groupId,
            'itenary_id' => $itenaryId,
            'sort_order' => $sortOrder,
        ], 'Song assigned to group');
    }

    // ─── 9a. CREATE DEVOTION ──────────────────────────────────────────────────
    // POST ?route=admin/devotion/create
    // Body: { group_id, devotion_date, title, content, scripture_ref? }
    public function devotionCreate(): void {
        $this->auth->requireRole('admin');
        $body = json_decode(file_get_contents('php://input'), true) ?? [];

        [$ok, $err, $data] = $this->validateDevotionBody($body);
        if (!$ok) { $this->fail(400, $err); return; }

        // If a stub was auto-generated, admin should use update, not create
        if ($this->model->getDevotionByGroupAndDate($data['group_id'], $data['devotion_date'])) {
            $this->fail(409, 'A devotion already exists for this date. Use admin/devotion/update instead.');
            return;
        }

        $id = $this->model->createDevotion($data);
        if (!$id) { $this->fail(500, 'Failed to create devotion'); return; }

        $this->ok($this->model->getDevotionById($id), 'Devotion created', 201);
    }

    // ─── 9b. UPDATE DEVOTION ──────────────────────────────────────────────────
    // POST ?route=admin/devotion/update
    // Body: { id, group_id, devotion_date, title, content, scripture_ref? }
    public function devotionUpdate(): void {
        $this->auth->requireRole('admin');
        $body = json_decode(file_get_contents('php://input'), true) ?? [];

        $id = (int) ($body['id'] ?? 0);
        if (!$id) { $this->fail(400, 'id is required'); return; }

        [$ok, $err, $data] = $this->validateDevotionBody($body);
        if (!$ok) { $this->fail(400, $err); return; }

        if (!$this->model->getDevotionById($id)) {
            $this->fail(404, 'Devotion not found');
            return;
        }

        $this->model->updateDevotion($id, $data);
        $this->ok($this->model->getDevotionById($id), 'Devotion updated');
    }

    public function pendingAccounts(): void {
        $user = $this->auth->requireRole('admin');
        
        $groupId = isset($_GET['group_id']) ? (int) $_GET['group_id'] : null;
        
        $pending = $this->accountModel->getPending($groupId);
        
        $this->ok($pending);
    }


    // ─── Private helpers ─────────────────────────────────────────────────────

    /**
     * Generate itinerary + devotion stubs for every date in [startDate, endDate].
     * Dates already in $existingItin / $existingDev are skipped — safe to call on update.
     * Note: song stubs are NOT auto-created here because group_songs.song_id is NOT NULL
     * (no placeholder song row exists). Admin assigns songs via admin/song/addToGroup.
     *
     * @return array{ itineraries: int, devotions: int }  count of rows inserted
     */
    private function generateStubs(
        int    $groupId,
        string $startDate,
        string $endDate,
        array  $existingItin,
        array  $existingDev
    ): array {
        $cur   = new DateTime($startDate);
        $end   = new DateTime($endDate);
        $day   = 1;
        $added = ['itineraries' => 0, 'devotions' => 0];

        while ($cur <= $end) {
            $dateStr = $cur->format('Y-m-d');

            if (!in_array($dateStr, $existingItin, true)) {
                $this->model->createItinerary($groupId, $dateStr, "Day {$day}");
                $added['itineraries']++;
            }

            if (!in_array($dateStr, $existingDev, true)) {
                $this->model->createDevotionStub($groupId, $dateStr, "Daily Bread - Day {$day}");
                $added['devotions']++;
            }

            $cur->modify('+1 day');
            $day++;
        }

        return $added;
    }

    /** Returns [bool $valid, string $error] */
    private function validateTripBody(array $body): array {
        foreach (['name', 'start_date', 'end_date', 'mentor_id', 'koordinator_id'] as $field) {
            if (empty($body[$field])) {
                return [false, "'{$field}' is required"];
            }
        }
        if (!$this->isValidDate($body['start_date'])) {
            return [false, 'start_date must be YYYY-MM-DD'];
        }
        if (!$this->isValidDate($body['end_date'])) {
            return [false, 'end_date must be YYYY-MM-DD'];
        }
        if ($body['start_date'] > $body['end_date']) {
            return [false, 'start_date must be on or before end_date'];
        }
        return [true, ''];
    }

    /** Returns [bool $valid, string $error, ?array $sanitisedData] */
    private function validateDevotionBody(array $body): array {
        $groupId = (int) ($body['group_id'] ?? 0);
        $title   = trim($body['title']      ?? '');
        $content = trim($body['content']    ?? '');
        $date    = trim($body['devotion_date'] ?? '');

        if (!$groupId)       return [false, 'group_id is required',          null];
        if (empty($title))   return [false, 'title is required',             null];
        if (empty($content)) return [false, 'content is required',           null];
        if (!$this->isValidDate($date)) {
            return [false, 'devotion_date must be YYYY-MM-DD', null];
        }

        return [true, '', [
            'group_id'      => $groupId,
            'title'         => $title,
            'content'       => $content,
            'scripture_ref' => trim($body['scripture_ref'] ?? '') ?: null,
            'devotion_date' => $date,
        ]];
    }

    private function isValidDate(string $date): bool {
        $d = DateTime::createFromFormat('Y-m-d', $date);
        return $d && $d->format('Y-m-d') === $date;
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