<?php

class AdminModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    // ─── Trips ───────────────────────────────────────────────────────────────

    /**
     * Completed/archived trips with mentor + koordinator names and member count.
     */
    public function getCompletedTrips(): array {
        $stmt = $this->db->prepare(
            "SELECT g.id, g.name, g.start_date, g.end_date, g.status, g.created_at,
                    CONCAT(m.first_name, ' ', m.last_name) AS mentor_name,
                    CONCAT(k.first_name, ' ', k.last_name) AS koordinator_name,
                    COUNT(a.id) AS member_count
             FROM groups g
             JOIN users m  ON m.id = g.mentor_id
             JOIN users k  ON k.id = g.koordinator_id
             LEFT JOIN accounts a
                    ON a.group_id = g.id
                   AND a.status_join = 'approved'
                   AND a.deleted_at  IS NULL
             WHERE g.status IN ('completed', 'archived')
               AND g.deleted_at IS NULL
             GROUP BY g.id
             ORDER BY g.end_date DESC"
        );
        $stmt->execute();
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    /** Insert a new group row. Returns new group ID or null on failure. */
    public function createTrip(array $data, int $adminId): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO groups
                 (name, description, start_date, end_date, location, dresscode,
                  meetup_time, meetup_address, mentor_id, koordinator_id,
                  created_by, status, created_at, updated_at)
             VALUES
                 (:name, :description, :start_date, :end_date, :location, :dresscode,
                  :meetup_time, :meetup_address, :mentor_id, :koordinator_id,
                  :created_by, 'active', NOW(), NOW())"
        );
        $stmt->execute([
            'name'           => $data['name'],
            'description'    => $data['description']    ?? null,
            'start_date'     => $data['start_date'],
            'end_date'       => $data['end_date'],
            'location'       => $data['location']       ?? null,
            'dresscode'      => $data['dresscode']       ?? null,
            'meetup_time'    => $data['meetup_time']    ?? null,
            'meetup_address' => $data['meetup_address'] ?? null,
            'mentor_id'      => $data['mentor_id'],
            'koordinator_id' => $data['koordinator_id'],
            'created_by'     => $adminId,
        ]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }

    /** Update an existing group row. */
    public function updateTrip(int $id, array $data): bool {
        $stmt = $this->db->prepare(
            "UPDATE groups
             SET name           = :name,
                 description    = :description,
                 start_date     = :start_date,
                 end_date       = :end_date,
                 location       = :location,
                 dresscode      = :dresscode,
                 meetup_time    = :meetup_time,
                 meetup_address = :meetup_address,
                 mentor_id      = :mentor_id,
                 koordinator_id = :koordinator_id,
                 updated_at     = NOW()
             WHERE id = :id AND deleted_at IS NULL"
        );
        return $stmt->execute([
            'name'           => $data['name'],
            'description'    => $data['description']    ?? null,
            'start_date'     => $data['start_date'],
            'end_date'       => $data['end_date'],
            'location'       => $data['location']       ?? null,
            'dresscode'      => $data['dresscode']       ?? null,
            'meetup_time'    => $data['meetup_time']    ?? null,
            'meetup_address' => $data['meetup_address'] ?? null,
            'mentor_id'      => $data['mentor_id'],
            'koordinator_id' => $data['koordinator_id'],
            'id'             => $id,
        ]);
    }

    /** Fetch one group by ID (admin-safe fields). */
    public function getTripById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, name, description, start_date, end_date,
                    location, dresscode, meetup_time, meetup_address,
                    mentor_id, koordinator_id, status, created_at
             FROM groups
             WHERE id = :id AND deleted_at IS NULL"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    // ─── Stub generation helpers ──────────────────────────────────────────────

    /** Flat list of existing itinerary dates for a group. */
    public function getExistingItineraryDates(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT itenary_date FROM itenaries WHERE group_id = :group_id"
        );
        $stmt->execute(['group_id' => $groupId]);
        return array_column($stmt->fetchAll(PDO::FETCH_ASSOC), 'itenary_date');
    }

    /** Flat list of existing devotion dates for a group. */
    public function getExistingDevotionDates(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT devotion_date FROM devotions WHERE group_id = :group_id"
        );
        $stmt->execute(['group_id' => $groupId]);
        return array_column($stmt->fetchAll(PDO::FETCH_ASSOC), 'devotion_date');
    }

    /** Insert one itinerary row. */
    public function createItinerary(int $groupId, string $date, string $desc): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO itenaries (group_id, itenary_date, itenary_desc)
             VALUES (:group_id, :date, :desc)"
        );
        $stmt->execute(['group_id' => $groupId, 'date' => $date, 'desc' => $desc]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }

    /** Insert one blank devotion stub (title set, content empty for admin to fill later). */
    public function createDevotionStub(int $groupId, string $date, string $title): bool {
        $stmt = $this->db->prepare(
            "INSERT INTO devotions (group_id, title, content, devotion_date)
             VALUES (:group_id, :title, '', :date)"
        );
        return $stmt->execute(['group_id' => $groupId, 'title' => $title, 'date' => $date]);
    }

    /** All itinerary dates for a group, ordered ascending (for response payload). */
    public function getItineraryDates(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT itenary_date, itenary_desc
             FROM itenaries
             WHERE group_id = :group_id
             ORDER BY itenary_date ASC"
        );
        $stmt->execute(['group_id' => $groupId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    // ─── Orders ──────────────────────────────────────────────────────────────

    public function getOrderById(int $accountId): ?array {
        $stmt = $this->db->prepare(
            "SELECT a.id, a.user_id, a.group_id, a.status_join,
                    a.join_date, a.approved_date, a.approved_by, a.is_paid,
                    CONCAT(u.first_name, ' ', u.last_name) AS user_name,
                    g.name AS group_name
             FROM accounts a
             JOIN users  u ON u.id = a.user_id
             JOIN groups g ON g.id = a.group_id
             WHERE a.id = :id AND a.deleted_at IS NULL"
        );
        $stmt->execute(['id' => $accountId]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    public function approveOrder(int $accountId, int $adminId): bool {
        $stmt = $this->db->prepare(
            "UPDATE accounts
             SET status_join = 'approved', approved_date = NOW(), approved_by = :admin_id
             WHERE id = :id AND deleted_at IS NULL"
        );
        return $stmt->execute(['admin_id' => $adminId, 'id' => $accountId]);
    }

    public function rejectOrder(int $accountId, int $adminId): bool {
        $stmt = $this->db->prepare(
            "UPDATE accounts
             SET status_join = 'rejected', approved_date = NOW(), approved_by = :admin_id
             WHERE id = :id AND deleted_at IS NULL"
        );
        return $stmt->execute(['admin_id' => $adminId, 'id' => $accountId]);
    }

    // ─── Members ─────────────────────────────────────────────────────────────

    /** Soft-delete the accounts row — removes member from group. */
    public function removeMember(int $userId, int $groupId): bool {
        $stmt = $this->db->prepare(
            "UPDATE accounts
             SET deleted_at = NOW()
             WHERE user_id = :user_id AND group_id = :group_id AND deleted_at IS NULL"
        );
        $stmt->execute(['user_id' => $userId, 'group_id' => $groupId]);
        return $stmt->rowCount() > 0;
    }

    // ─── Gallery ─────────────────────────────────────────────────────────────

    public function getImageById(int $imageId): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, image_url FROM group_images WHERE id = :id"
        );
        $stmt->execute(['id' => $imageId]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    public function deleteImage(int $imageId): bool {
        $stmt = $this->db->prepare("DELETE FROM group_images WHERE id = :id");
        return $stmt->execute(['id' => $imageId]);
    }

    // ─── Songs ───────────────────────────────────────────────────────────────

    public function createSong(array $data): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO songs (title, author, lyrics, created_at, updated_at)
             VALUES (:title, :author, :lyrics, NOW(), NOW())"
        );
        $stmt->execute([
            'title'  => $data['title'],
            'author' => $data['author'] ?? null,
            'lyrics' => $data['lyrics'] ?? null,
        ]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }
    public function updateSong(int $id, array $data): bool {
        $stmt = $this->db->prepare(
            "UPDATE songs
            SET title = :title,
                author = :author,
                lyrics = :lyrics,
                updated_at = NOW()
            WHERE id = :id"
        );

        return $stmt->execute([
            'id'     => $id,
            'title'  => $data['title'],
            'author' => $data['author'] ?? null,
            'lyrics' => $data['lyrics'] ?? null,
        ]);
    }

    public function getSongById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, title, author, lyrics FROM songs WHERE id = :id"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    /**
     * Check if (song_id, group_id, itenary_id) already exists.
     * itenary_id IS NULL needs special handling since NULL != NULL in SQL.
     */
    public function songExistsInGroup(int $songId, int $groupId, ?int $itenaryId): bool {
        if ($itenaryId === null) {
            $stmt = $this->db->prepare(
                "SELECT id FROM group_songs
                 WHERE group_id = :group_id AND song_id = :song_id AND itenary_id IS NULL
                 LIMIT 1"
            );
            $stmt->execute(['group_id' => $groupId, 'song_id' => $songId]);
        } else {
            $stmt = $this->db->prepare(
                "SELECT id FROM group_songs
                 WHERE group_id = :group_id AND song_id = :song_id AND itenary_id = :itenary_id
                 LIMIT 1"
            );
            $stmt->execute(['group_id' => $groupId, 'song_id' => $songId, 'itenary_id' => $itenaryId]);
        }
        return (bool) $stmt->fetch();
    }

    public function addSongToGroup(int $songId, int $groupId, ?int $itenaryId, int $sortOrder): bool {
        $stmt = $this->db->prepare(
            "INSERT INTO group_songs (group_id, song_id, itenary_id, sort_order)
             VALUES (:group_id, :song_id, :itenary_id, :sort_order)"
        );
        return $stmt->execute([
            'group_id'   => $groupId,
            'song_id'    => $songId,
            'itenary_id' => $itenaryId,
            'sort_order' => $sortOrder,
        ]);
    }

    // ─── Devotions ───────────────────────────────────────────────────────────

    public function getDevotionByGroupAndDate(int $groupId, string $date): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, group_id, title, content, scripture_ref, devotion_date
             FROM devotions
             WHERE group_id = :group_id AND devotion_date = :date
             LIMIT 1"
        );
        $stmt->execute(['group_id' => $groupId, 'date' => $date]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    public function getDevotionById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, group_id, title, content, scripture_ref, devotion_date
             FROM devotions
             WHERE id = :id"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    public function createDevotion(array $data): ?int {
        $stmt = $this->db->prepare(
            "INSERT INTO devotions (group_id, title, content, scripture_ref, devotion_date)
             VALUES (:group_id, :title, :content, :scripture_ref, :devotion_date)"
        );
        $stmt->execute([
            'group_id'      => $data['group_id'],
            'title'         => $data['title'],
            'content'       => $data['content'],
            'scripture_ref' => $data['scripture_ref'] ?? null,
            'devotion_date' => $data['devotion_date'],
        ]);
        $id = $this->db->lastInsertId();
        return $id ? (int) $id : null;
    }

    public function updateDevotion(int $id, array $data): bool {
        $stmt = $this->db->prepare(
            "UPDATE devotions
             SET title          = :title,
                 content        = :content,
                 scripture_ref  = :scripture_ref,
                 devotion_date  = :devotion_date,
                 updated_at     = NOW()
             WHERE id = :id"
        );
        return $stmt->execute([
            'title'         => $data['title'],
            'content'       => $data['content'],
            'scripture_ref' => $data['scripture_ref'] ?? null,
            'devotion_date' => $data['devotion_date'],
            'id'            => $id,
        ]);
    }
    public function deleteTrip(int $id): bool
    {
        $stmt = $this->db->prepare("
            UPDATE groups
            SET deleted_at = NOW()
            WHERE id = ?
        ");

        return $stmt->execute([$id]);
    }
}