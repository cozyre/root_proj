-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 02, 2026 at 09:29 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rootandroid`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `status_join` enum('pending','approved','rejected') NOT NULL DEFAULT 'pending',
  `join_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `approved_date` datetime DEFAULT NULL,
  `approved_by` bigint(20) UNSIGNED DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `user_id`, `group_id`, `status_join`, `join_date`, `approved_date`, `approved_by`, `deleted_at`, `is_paid`) VALUES
(1, 4, 1, 'approved', '2026-06-26 08:06:31', '2026-06-26 15:06:31', 2, NULL, 1),
(2, 5, 1, 'pending', '2026-06-26 08:06:31', '2026-06-26 15:06:31', 2, NULL, 1),
(3, 6, 1, 'pending', '2026-06-26 08:06:31', NULL, NULL, NULL, 0),
(4, 9, 1, 'approved', '2026-06-26 08:06:31', NULL, 2, NULL, 1),
(5, 8, 2, 'approved', '2026-06-26 08:06:31', '2026-06-26 15:06:31', 2, NULL, 1),
(6, 9, 2, 'approved', '2026-06-26 08:06:31', '2026-06-26 15:06:31', 2, NULL, 1),
(7, 3, 3, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(8, 9, 3, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(9, 5, 3, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 0),
(10, 6, 3, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(11, 7, 4, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(12, 8, 4, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(13, 9, 4, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 0),
(14, 10, 4, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(15, 11, 5, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(16, 12, 5, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(17, 9, 5, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(18, 28, 5, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 0),
(19, 9, 6, 'rejected', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(20, 16, 6, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(21, 17, 6, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(22, 3, 7, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(23, 5, 7, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(24, 7, 7, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(25, 9, 7, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 0),
(26, 10, 8, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(27, 11, 8, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(28, 12, 8, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(29, 13, 8, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(30, 14, 9, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(31, 15, 9, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 0),
(32, 16, 9, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(33, 17, 10, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(34, 5, 10, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(35, 6, 10, 'approved', '2026-06-30 05:18:06', NULL, NULL, NULL, 1),
(36, 7, 10, 'pending', '2026-06-30 05:18:06', NULL, NULL, NULL, 0),
(37, 9, 9, 'pending', '2026-07-01 11:55:48', NULL, NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `devotions`
--

CREATE TABLE `devotions` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` longtext NOT NULL,
  `scripture_ref` varchar(200) DEFAULT NULL,
  `devotion_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `devotions`
--

INSERT INTO `devotions` (`id`, `group_id`, `title`, `content`, `scripture_ref`, `devotion_date`, `created_at`, `updated_at`) VALUES
(1, 1, 'Faith in Action', 'Reflection on living out faith.', 'James 2:17', '2026-07-10', '2026-06-26 08:06:31', '2026-06-26 08:06:31'),
(2, 1, 'Hope', 'Finding hope in difficult times.', 'Romans 15:13', '2026-07-11', '2026-06-26 08:06:31', '2026-06-26 08:06:31'),
(3, 2, 'Walking Together', 'Growing together as a community.', 'Hebrews 10:24-25', '2026-08-01', '2026-06-26 08:06:31', '2026-06-26 08:06:31');

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(150) NOT NULL,
  `description` text DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `dresscode` varchar(200) DEFAULT NULL,
  `meetup_time` time DEFAULT NULL,
  `meetup_address` text DEFAULT NULL,
  `mentor_id` bigint(20) UNSIGNED NOT NULL,
  `koordinator_id` bigint(20) UNSIGNED NOT NULL,
  `created_by` bigint(20) UNSIGNED NOT NULL,
  `status` enum('active','completed','archived') NOT NULL DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` datetime DEFAULT NULL,
  `price` int(100) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `name`, `description`, `start_date`, `end_date`, `location`, `dresscode`, `meetup_time`, `meetup_address`, `mentor_id`, `koordinator_id`, `created_by`, `status`, `created_at`, `updated_at`, `deleted_at`, `price`) VALUES
(1, 'Young Adults Retreat', 'Weekend spiritual retreat', '2026-07-10', '2026-07-12', 'Bandung', 'Casual', '08:00:00', 'Villa Lembang', 2, 3, 1, 'active', '2026-06-26 08:06:31', '2026-07-02 19:09:00', NULL, 4000000),
(2, 'Bible Study Batch A', 'Weekly bible study', '2026-08-01', '2026-09-30', 'Jakarta', 'Free', '19:00:00', 'Main Hall', 2, 3, 1, 'active', '2026-06-26 08:06:31', '2026-06-26 08:06:31', NULL, 0),
(3, 'Young Adults Alpha', 'Young adult fellowship', '2026-07-05', '2026-07-05', 'West Jakarta', 'Casual', '19:00:00', 'Main Hall', 7, 8, 1, 'archived', '2026-06-30 05:18:06', '2026-07-01 11:53:26', NULL, 0),
(4, 'Young Adults Beta', 'Weekly Bible study', '2028-07-07', '2028-07-12', 'Central Jakarta', 'Casual', '19:00:00', 'Room A', 6, 8, 1, 'completed', '2026-06-30 05:18:06', '2026-07-01 11:52:55', NULL, 0),
(5, 'Campus Fellowship', 'University fellowship', '2026-07-07', '2026-07-07', 'North Jakarta', 'Free', '18:00:00', 'Campus Hall', 6, 8, 1, 'active', '2026-06-30 05:18:06', '2026-07-01 11:53:32', NULL, 0),
(6, 'Music Ministry', 'Praise & Worship', '2029-07-08', '2029-07-12', 'Church', 'Black Shirt', '18:30:00', 'Music Room', 7, 8, 1, 'active', '2026-06-30 05:18:06', '2026-07-01 11:53:21', NULL, 0),
(7, 'Prayer Warriors', 'Prayer meeting', '2027-07-09', '2027-07-09', 'Online', 'Free', '20:00:00', 'Google Meet', 6, 8, 1, 'completed', '2026-06-30 05:18:06', '2026-07-01 11:53:04', NULL, 0),
(8, 'Married Couples', 'Marriage community', '2027-07-10', '2027-07-10', 'South Jakarta', 'Smart Casual', '16:00:00', 'Room B', 7, 8, 1, 'active', '2026-06-30 05:18:06', '2026-07-01 11:53:37', NULL, 0),
(9, 'English Fellowship', 'English speaking CG', '2027-07-11', '2027-07-11', 'Church Cafe', 'Casual', '17:00:00', 'Cafe', 6, 8, 1, 'active', '2026-06-30 05:18:06', '2026-07-01 11:52:45', NULL, 0),
(10, 'Creative Ministry', 'Media & Design team', '2027-07-12', '2027-07-12', 'Church', 'Black', '14:00:00', 'Studio', 7, 8, 1, 'active', '2026-06-30 05:18:06', '2026-07-01 11:52:35', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `group_images`
--

CREATE TABLE `group_images` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `image_url` text NOT NULL,
  `caption` varchar(255) DEFAULT NULL,
  `image_type` enum('gallery','placeholder') NOT NULL DEFAULT 'gallery',
  `sort_order` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `group_songs`
--

CREATE TABLE `group_songs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `song_id` bigint(20) UNSIGNED NOT NULL,
  `itenary_id` bigint(20) UNSIGNED DEFAULT NULL,
  `sort_order` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `group_songs`
--

INSERT INTO `group_songs` (`id`, `group_id`, `song_id`, `itenary_id`, `sort_order`) VALUES
(1, 1, 1, 1, 1),
(2, 1, 2, 1, 2),
(3, 1, 3, 2, 1),
(4, 2, 4, 3, 1),
(5, 2, 5, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `itenaries`
--

CREATE TABLE `itenaries` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `itenary_date` date NOT NULL,
  `itenary_desc` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `itenaries`
--

INSERT INTO `itenaries` (`id`, `group_id`, `itenary_date`, `itenary_desc`) VALUES
(1, 1, '2026-07-10', 'Arrival and Opening Session'),
(2, 1, '2026-07-11', 'Morning Worship'),
(3, 2, '2026-08-01', 'First Bible Study');

-- --------------------------------------------------------

--
-- Table structure for table `itenary_items`
--

CREATE TABLE `itenary_items` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `itenary_id` bigint(20) UNSIGNED NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time DEFAULT NULL,
  `type` enum('devotion','song','activity','') NOT NULL,
  `description` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `itenary_items`
--

INSERT INTO `itenary_items` (`id`, `itenary_id`, `start_time`, `end_time`, `type`, `description`) VALUES
(1, 1, '08:00:00', '09:00:00', 'activity', 'Registration'),
(2, 1, '09:00:00', '09:30:00', 'song', 'Praise & Worship'),
(3, 1, '09:30:00', '10:30:00', 'devotion', 'Opening Devotion'),
(4, 2, '07:00:00', '07:30:00', 'song', 'Morning Worship'),
(5, 2, '07:30:00', '08:15:00', 'devotion', 'Reflection'),
(6, 3, '19:00:00', '20:30:00', 'activity', 'Bible Discussion');

-- --------------------------------------------------------

--
-- Table structure for table `journals`
--

CREATE TABLE `journals` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` longtext NOT NULL,
  `journal_date` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `journals`
--

INSERT INTO `journals` (`id`, `user_id`, `group_id`, `title`, `content`, `journal_date`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 4, 1, 'First Day', 'I learned to trust God more.', '2026-07-10', '2026-06-26 08:06:32', '2026-06-26 08:06:32', NULL),
(2, 5, 1, 'Grateful', 'Met many new friends.', '2026-07-10', '2026-06-26 08:06:32', '2026-06-26 08:06:32', NULL),
(3, 4, 2, 'Bible Study Notes', 'Interesting discussion about faith.', '2026-08-01', '2026-06-26 08:06:32', '2026-06-26 08:06:32', NULL),
(4, 9, 1, 'my day', 'asjdhkajsdhkajd123 91823 u19ij ] as;slkjs o sldf\nas djaosdj 0qiwj dd1o2d ;aksjd a;', '2026-07-02', '2026-07-02 14:00:46', '2026-07-02 14:00:46', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `group_id` bigint(20) UNSIGNED NOT NULL,
  `sender_id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(200) NOT NULL,
  `body` text NOT NULL,
  `type` enum('broadcast','announcement','reminder') NOT NULL DEFAULT 'broadcast',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `group_id`, `sender_id`, `title`, `body`, `type`, `created_at`) VALUES
(1, 1, 2, 'Welcome!', 'Welcome to the retreat. Please arrive on time.', 'announcement', '2026-06-26 08:06:32'),
(2, 1, 3, 'Bring Bible', 'Remember to bring your Bible and notebook.', 'reminder', '2026-06-26 08:06:32'),
(3, 2, 2, 'Weekly Study', 'See you this Saturday at 7 PM.', 'broadcast', '2026-06-26 08:06:32');

-- --------------------------------------------------------

--
-- Table structure for table `songs`
--

CREATE TABLE `songs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(200) NOT NULL,
  `author` varchar(150) DEFAULT NULL,
  `lyrics` longtext DEFAULT NULL,
  `audio_url` varchar(500) DEFAULT NULL,
  `sort_order` int(11) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `songs`
--

INSERT INTO `songs` (`id`, `title`, `author`, `lyrics`, `audio_url`, `sort_order`, `created_at`, `updated_at`) VALUES
(1, 'How Great Is Our God', 'Chris Tomlin', 'Sample lyrics...', NULL, 1, '2026-06-26 08:06:31', '2026-06-26 08:06:31'),
(2, '10,000 Reasons', 'Matt Redman', 'Sample lyrics...', NULL, 2, '2026-06-26 08:06:31', '2026-06-26 08:06:31'),
(3, 'Goodness of God', 'Bethel Music', 'Sample lyrics...', NULL, 3, '2026-06-26 08:06:31', '2026-06-26 08:06:31'),
(4, 'Build My Life', 'Pat Barrett', 'Sample lyrics...', NULL, 4, '2026-06-26 08:06:31', '2026-06-26 08:06:31'),
(5, 'Way Maker', 'Sinach', 'Sample lyrics...', NULL, 5, '2026-06-26 08:06:31', '2026-06-26 08:06:31');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `profile_photo_url` text DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `bio` text DEFAULT NULL,
  `hide_phone` tinyint(1) NOT NULL DEFAULT 0,
  `role` enum('user','admin','mentor','koordinator') NOT NULL DEFAULT 'user',
  `fcm_token` varchar(255) DEFAULT NULL,
  `email_verified_at` datetime DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password_hash`, `first_name`, `last_name`, `profile_photo_url`, `phone`, `bio`, `hide_phone`, `role`, `fcm_token`, `email_verified_at`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'admin', 'admin@email.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'admin', '', NULL, '+12345678', NULL, 0, 'admin', NULL, NULL, '2026-06-19 07:32:48', '2026-06-19 07:32:48', NULL),
(2, 'mentor_john', 'john@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'John', 'Tan', NULL, '081111111112', 'Group mentor', 0, 'mentor', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(3, 'coord_sarah', 'sarah@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Sarah', 'Wijaya', NULL, '081111111113', 'Group coordinator', 0, 'koordinator', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(4, 'alice', 'alice@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Alice', 'Santoso', NULL, '081111111114', 'Member', 0, 'user', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(5, 'bob', 'bob@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Bob', 'Hartono', NULL, '081111111115', 'Member', 0, 'user', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(6, 'charlie', 'charlie@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Charlie', 'Gunawan', NULL, '081111111116', 'Member', 0, 'user', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(7, 'diana', 'diana@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Diana', 'Lim', NULL, '081111111117', 'Member', 0, 'user', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(8, 'edwin', 'edwin@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Edwin', 'Lee', NULL, '081111111118', 'Member', 0, 'user', NULL, '2026-06-26 15:06:31', '2026-06-26 08:06:31', '2026-06-26 08:10:26', NULL),
(9, 'austin', 'austin@email.com', '$2y$10$yaUetjXI4MEcV.EF9n0gkuIEQBWuk32B4k1Fk4LwpUWLGQS9ZHjkC', 'Austin', 'Halim', NULL, '085340974407', NULL, 0, 'user', NULL, NULL, '2026-06-29 15:44:48', '2026-06-29 15:44:48', NULL),
(25, 'kevin', 'kevin@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Kevin', 'Tan', NULL, '081111111001', 'Enjoys serving.', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(26, 'michelle', 'michelle@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Michelle', 'Wijaya', NULL, '081111111002', 'Bible study member.', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(27, 'daniel', 'daniel@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Daniel', 'Gunawan', NULL, '081111111003', 'Loves worship.', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(28, 'felicia', 'felicia@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Felicia', 'Hartono', NULL, '081111111004', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(29, 'jonathan', 'jonathan@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Jonathan', 'Lim', NULL, '081111111005', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(30, 'grace', 'grace@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Grace', 'Lim', NULL, '081111111006', '', 0, 'mentor', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(31, 'andrew', 'andrew@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Andrew', 'Setiawan', NULL, '081111111007', '', 0, 'mentor', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(32, 'steven', 'steven@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Steven', 'Halim', NULL, '081111111008', '', 0, 'koordinator', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(33, 'olivia', 'olivia@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Olivia', 'Santoso', NULL, '081111111009', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(34, 'amelia', 'amelia@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Amelia', 'Tanu', NULL, '081111111010', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(35, 'joshua', 'joshua@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Joshua', 'Kurniawan', NULL, '081111111011', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(36, 'richard', 'richard@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Richard', 'Lie', NULL, '081111111012', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(37, 'vincent', 'vincent@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Vincent', 'Wong', NULL, '081111111014', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL),
(38, 'natalie', 'natalie@example.com', '$2y$10$qeNaVD.wNnCKIIg/ImacuuPlWuLqwBenhWGVxLsO/bCNeLfbrHaSO', 'Natalie', 'Tan', NULL, '081111111015', '', 0, 'user', NULL, NULL, '2026-06-30 05:18:06', '2026-06-30 05:18:06', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_accounts_user_group` (`user_id`,`group_id`),
  ADD KEY `idx_accounts_group` (`group_id`),
  ADD KEY `idx_accounts_status` (`status_join`),
  ADD KEY `idx_accounts_deleted_at` (`deleted_at`),
  ADD KEY `fk_accounts_approved_by` (`approved_by`);

--
-- Indexes for table `devotions`
--
ALTER TABLE `devotions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_devotions_group_date` (`group_id`,`devotion_date`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_groups_mentor` (`mentor_id`),
  ADD KEY `idx_groups_koordinator` (`koordinator_id`),
  ADD KEY `idx_groups_status` (`status`),
  ADD KEY `idx_groups_deleted_at` (`deleted_at`),
  ADD KEY `fk_groups_created_by` (`created_by`);

--
-- Indexes for table `group_images`
--
ALTER TABLE `group_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_group_images_group` (`group_id`,`image_type`);

--
-- Indexes for table `group_songs`
--
ALTER TABLE `group_songs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_group_song_day` (`group_id`,`song_id`,`itenary_id`),
  ADD KEY `song_id` (`song_id`),
  ADD KEY `itenary_id` (`itenary_id`);

--
-- Indexes for table `itenaries`
--
ALTER TABLE `itenaries`
  ADD PRIMARY KEY (`id`),
  ADD KEY `group_id_fk` (`group_id`);

--
-- Indexes for table `itenary_items`
--
ALTER TABLE `itenary_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `itenary_id_fk` (`itenary_id`);

--
-- Indexes for table `journals`
--
ALTER TABLE `journals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_journals_user_group` (`user_id`,`group_id`),
  ADD KEY `idx_journals_date` (`journal_date`),
  ADD KEY `idx_journals_deleted_at` (`deleted_at`),
  ADD KEY `fk_journals_group` (`group_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_notifications_group` (`group_id`),
  ADD KEY `idx_notifications_sender` (`sender_id`);

--
-- Indexes for table `songs`
--
ALTER TABLE `songs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_users_username` (`username`),
  ADD UNIQUE KEY `uq_users_email` (`email`),
  ADD KEY `idx_users_role` (`role`),
  ADD KEY `idx_users_deleted_at` (`deleted_at`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `devotions`
--
ALTER TABLE `devotions`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `group_images`
--
ALTER TABLE `group_images`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `group_songs`
--
ALTER TABLE `group_songs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `itenaries`
--
ALTER TABLE `itenaries`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `itenary_items`
--
ALTER TABLE `itenary_items`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `journals`
--
ALTER TABLE `journals`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `songs`
--
ALTER TABLE `songs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `fk_accounts_approved_by` FOREIGN KEY (`approved_by`) REFERENCES `users` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `fk_accounts_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_accounts_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `devotions`
--
ALTER TABLE `devotions`
  ADD CONSTRAINT `fk_devotions_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `groups`
--
ALTER TABLE `groups`
  ADD CONSTRAINT `fk_groups_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_groups_koordinator` FOREIGN KEY (`koordinator_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_groups_mentor` FOREIGN KEY (`mentor_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `group_images`
--
ALTER TABLE `group_images`
  ADD CONSTRAINT `fk_group_images_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `group_songs`
--
ALTER TABLE `group_songs`
  ADD CONSTRAINT `group_songs_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `group_songs_ibfk_2` FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `group_songs_ibfk_3` FOREIGN KEY (`itenary_id`) REFERENCES `itenaries` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `itenaries`
--
ALTER TABLE `itenaries`
  ADD CONSTRAINT `group_id_fk` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `itenary_items`
--
ALTER TABLE `itenary_items`
  ADD CONSTRAINT `itenary_id_fk` FOREIGN KEY (`itenary_id`) REFERENCES `itenaries` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `journals`
--
ALTER TABLE `journals`
  ADD CONSTRAINT `fk_journals_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_journals_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk_notifications_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_notifications_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
