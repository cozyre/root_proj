<?php

class SongModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    /**
     * Get all songs assigned to a group (regardless of day).
     * Used for the group's full songbook view.
     */
    public function getByGroup(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT s.id, s.title, s.author, gs.sort_order
             FROM songs s
             JOIN group_songs gs ON gs.song_id = s.id
             WHERE gs.group_id = :group_id
             ORDER BY gs.sort_order ASC, s.title ASC"
        );
        $stmt->execute(['group_id' => $groupId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    /**
     * Get songs assigned to a specific day in a group.
     * itenary_id links group_songs to a specific itinerary date.
     */
    public function getByGroupAndDate(int $groupId, string $date): array {
        $stmt = $this->db->prepare(
            "SELECT s.id, s.title, s.author, gs.sort_order
             FROM songs s
             JOIN group_songs gs  ON gs.song_id    = s.id
             JOIN itenaries i     ON gs.itenary_id = i.id
             WHERE gs.group_id = :group_id
               AND i.itenary_date = :date
             ORDER BY gs.sort_order ASC, s.title ASC"
        );
        $stmt->execute(['group_id' => $groupId, 'date' => $date]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    /**
     * Get a single song with full lyrics by ID.
     */
    public function getById(int $id): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, title, author, lyrics
             FROM songs
             WHERE id = :id
             LIMIT 1"
        );
        $stmt->execute(['id' => $id]);
        return $stmt->fetch(PDO::FETCH_ASSOC) ?: null;
    }

    /**
     * Search songs by title or author within a group.
     */
    public function search(int $groupId, string $query): array {
        $like = '%' . $query . '%';
        $stmt = $this->db->prepare(
            "SELECT s.id, s.title, s.author, gs.sort_order
             FROM songs s
             JOIN group_songs gs ON gs.song_id = s.id
             WHERE gs.group_id = :group_id
               AND (s.title LIKE :q1 OR s.author LIKE :q2)
             ORDER BY gs.sort_order ASC, s.title ASC"
        );
        $stmt->execute(['group_id' => $groupId, 'q1' => $like, 'q2' => $like]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function removeFromGroup($groupId, $songId, $itenaryId = null)
    {
        if ($itenaryId !== null) {
            $stmt = $this->db->prepare("
                DELETE FROM group_songs
                WHERE group_id = ?
                AND song_id = ?
                AND itenary_id = ?
            ");

            $stmt->bind_param("iii", $groupId, $songId, $itenaryId);
        } else {
            $stmt = $this->db->prepare("
                DELETE FROM group_songs
                WHERE group_id = ?
                AND song_id = ?
            ");

            $stmt->bind_param("ii", $groupId, $songId);
        }

        if (!$stmt) {
            return false;
        }

        $stmt->execute();

        return $stmt->affected_rows > 0;
    }

    /**
     * Browse the global song library (no group filter).
     * Optionally filter by a search query.
     */
    public function browse(string $query = ''): array {
        if ($query === '') {
            $stmt = $this->db->prepare(
                "SELECT id, title, author
                 FROM songs
                 ORDER BY title ASC"
            );
            $stmt->execute();
        } else {
            $like = '%' . $query . '%';
            $stmt = $this->db->prepare(
                "SELECT id, title, author
                 FROM songs
                 WHERE title LIKE :q1 OR author LIKE :q2
                 ORDER BY title ASC"
            );
            $stmt->execute(['q1' => $like, 'q2' => $like]);
        }
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}