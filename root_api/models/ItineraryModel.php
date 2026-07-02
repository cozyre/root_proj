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

    public function createItem(
        int $itineraryId,
        string $startTime,
        string $endTime,
        string $type,
        string $description
    ): ?array {
        try {
            $stmt = $this->db->prepare(
                "INSERT INTO itenary_items 
                 (itenary_id, start_time, end_time, type, description)
                 VALUES (:itenary_id, :start_time, :end_time, :type, :description)"
            );
            
            $success = $stmt->execute([
                'itenary_id' => $itineraryId,
                'start_time' => $startTime,
                'end_time' => $endTime,
                'type' => $type,
                'description' => $description
            ]);
 
            if (!$success) {
                error_log("Failed to insert itinerary item");
                return null;
            }
 
            // Return newly created item
            $id = (int) $this->db->lastInsertId();
 
            return [
                'id' => $id,
                'start_time' => $startTime,
                'end_time' => $endTime,
                'type' => $type,
                'description' => $description
            ];
 
        } catch (Exception $e) {
            error_log("Error in createItem: " . $e->getMessage());
            return null;
        }
    }

    public function updateItem(
        int $itemId,
        string $startTime,
        string $endTime,
        string $type,
        string $description
    ): ?array {
        try {
            $stmt = $this->db->prepare(
                "UPDATE itenary_items 
                 SET start_time = :start_time, 
                     end_time = :end_time, 
                     type = :type, 
                     description = :description
                 WHERE id = :id"
            );
            
            $success = $stmt->execute([
                'id' => $itemId,
                'start_time' => $startTime,
                'end_time' => $endTime,
                'type' => $type,
                'description' => $description
            ]);
 
            if (!$success) {
                error_log("Failed to update itinerary item");
                return null;
            }
 
            // Return updated item
            return [
                'id' => $itemId,
                'start_time' => $startTime,
                'end_time' => $endTime,
                'type' => $type,
                'description' => $description
            ];
 
        } catch (Exception $e) {
            error_log("Error in updateItem: " . $e->getMessage());
            return null;
        }
    }

    public function deleteItem(int $itemId): bool {
        try {
            $stmt = $this->db->prepare(
                "DELETE FROM itenary_items WHERE id = :id"
            );
            
            $success = $stmt->execute(['id' => $itemId]);
 
            if (!$success) {
                error_log("Failed to delete itinerary item");
                return false;
            }
 
            return true;
 
        } catch (Exception $e) {
            error_log("Error in deleteItem: " . $e->getMessage());
            return false;
        }
    }

    public function itemExists(int $itemId): bool {
        try {
            $stmt = $this->db->prepare(
                "SELECT id FROM itenary_items WHERE id = :id LIMIT 1"
            );
            
            $stmt->execute(['id' => $itemId]);
            
            return $stmt->rowCount() > 0;
 
        } catch (Exception $e) {
            error_log("Error in itemExists: " . $e->getMessage());
            return false;
        }
    }

    public function getItemById(int $itemId): ?array {
        try {
            $stmt = $this->db->prepare(
                "SELECT id, start_time, end_time, type, description
                 FROM itenary_items
                 WHERE id = :id
                 LIMIT 1"
            );
            
            $stmt->execute(['id' => $itemId]);
            
            $item = $stmt->fetch(PDO::FETCH_ASSOC);
            
            return $item ?: null;
 
        } catch (Exception $e) {
            error_log("Error in getItemById: " . $e->getMessage());
            return null;
        }
    }
}