<?php

class ItineraryModel {
    private PDO $db;

    public function __construct(PDO $db) {
        $this->db = $db;
    }

    /**
     * Get a single itinerary with all its items for a specific date.
     * Returns null if no itinerary exists for that date.
     */
    public function getByGroupAndDate(int $groupId, string $date): ?array {
        $stmt = $this->db->prepare(
            "SELECT id, itenary_date, itenary_desc
             FROM itenaries
             WHERE group_id = :group_id AND itenary_date = :date
             LIMIT 1"
        );
        $stmt->execute(['group_id' => $groupId, 'date' => $date]);
        $itinerary = $stmt->fetch(PDO::FETCH_ASSOC);

        if (!$itinerary) return null;

        $itinerary['items'] = $this->getItems((int) $itinerary['id']);
        return $itinerary;
    }

    /**
     * Get all items for a given itinerary, ordered by start_time.
     */
    private function getItems(int $itineraryId): array {
        $stmt = $this->db->prepare(
            "SELECT id, start_time, end_time, type, description
             FROM itenary_items
             WHERE itenary_id = :itenary_id
             ORDER BY start_time ASC"
        );
        $stmt->execute(['itenary_id' => $itineraryId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    /**
     * Get all available itinerary dates for a group, ascending.
     * Used for date navigation (< prev | next >) on the frontend.
     */
    public function getAvailableDates(int $groupId): array {
        $stmt = $this->db->prepare(
            "SELECT itenary_date, itenary_desc
             FROM itenaries
             WHERE group_id = :group_id
             ORDER BY itenary_date ASC"
        );
        $stmt->execute(['group_id' => $groupId]);
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}