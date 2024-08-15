-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 01, 2023 at 06:13 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `file_sharing`
--

-- --------------------------------------------------------

--
-- Table structure for table `file`
--

CREATE TABLE `file` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(9) NOT NULL,
  `extention` varchar(6) NOT NULL,
  `size` double NOT NULL,
  `upload_date` datetime NOT NULL DEFAULT current_timestamp(),
  `path` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`id`, `name`, `type`, `extention`, `size`, `upload_date`, `path`, `user_id`) VALUES
(18, 'Capture2.PNG', 'Images', 'PNG', 885876, '2023-05-31 23:02:53', 'Media\\Images\\Capture2.PNG', 4),
(19, 'RMI-RPC.pdf', 'Documents', 'pdf', 312376, '2023-06-01 09:16:53', 'Media\\Documents\\RMI-RPC.pdf', 10),
(20, 'client server.pdf', 'Documents', 'pdf', 309715, '2023-06-01 09:17:49', 'Media\\Documents\\client server.pdf', 10),
(21, 'Assignment3_Osama.docx', 'Documents', 'docx', 401328, '2023-06-01 09:19:24', 'Media\\Documents\\Assignment3_Osama.docx', 6),
(26, 'Lecture 8.pdf', 'Documents', 'pdf', 449362, '2023-06-01 09:25:37', 'Media\\Documents\\Lecture 8.pdf', 10),
(29, 'failuremodel.pdf', 'Documents', 'pdf', 308301, '2023-06-01 09:51:09', 'Media\\Documents\\failuremodel.pdf', 10),
(31, 'FolderSharing.zip', 'Others', 'zip', 2760092, '2023-06-01 10:38:46', 'Media\\Others\\FolderSharing.zip', 10),
(35, 'Screenshot 2023-03-21 155150.png', 'Images', 'png', 705603, '2023-06-01 19:05:32', 'Media\\Images\\Screenshot 2023-03-21 155150.png', 7),
(36, 'null-29.pdf.zip', 'Others', 'zip', 1197880, '2023-06-01 19:06:28', 'Media\\Others\\null-29.pdf.zip', 7);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `login_status` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `last_login` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `email`, `password`, `login_status`, `created_at`, `last_login`) VALUES
(4, 'ahmed3', 'ahmed3@gmail.com', '13234', 0, '2023-05-29 17:49:23', '2023-05-29 17:49:23'),
(5, 'ahmed4', 'ahmed4@gmail.com', '13234', 0, '2023-05-29 21:57:06', '2023-05-29 21:57:06'),
(6, 'Mohammed', 'm@gmail.com', '0101', 0, '2023-05-30 17:55:24', '2023-05-30 17:55:24'),
(7, 'Osama', 'Osama@gmail.com', 'Osama', 0, '2023-05-30 18:04:15', '2023-05-30 18:04:15'),
(8, 'iii', 'iii@gmail.com', 'iii', 0, '2023-05-31 11:04:27', '2023-05-31 11:04:27'),
(9, 'abood', 'abood@gmail.com', '1234', 0, '2023-05-31 19:00:46', '2023-05-31 19:00:46'),
(10, 'ahmed', 'a@gmail.com', '111', 0, '2023-06-01 09:17:03', '2023-06-01 09:17:03'),
(11, 'm', 'm', 'm', 0, '2023-06-01 09:21:09', '2023-06-01 09:21:09');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username_unique` (`username`),
  ADD UNIQUE KEY `eamil_unique` (`email`(50));

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `file`
--
ALTER TABLE `file`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `file`
--
ALTER TABLE `file`
  ADD CONSTRAINT `file_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
