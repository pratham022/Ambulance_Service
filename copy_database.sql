-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 16, 2021 at 05:32 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `copy_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `cab`
--

DROP TABLE IF EXISTS `cab`;
CREATE TABLE IF NOT EXISTS `cab` (
  `cab_id` int(11) NOT NULL AUTO_INCREMENT,
  `license_plate` varchar(50) NOT NULL,
  `manufacture_year` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `cab_lat` varchar(20) NOT NULL,
  `cab_lng` varchar(20) NOT NULL,
  `car_model_id` int(11) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cab_id`),
  UNIQUE KEY `license_plate` (`license_plate`),
  KEY `car_model_id` (`car_model_id`),
  KEY `owner_id` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `cab_ride`
--

DROP TABLE IF EXISTS `cab_ride`;
CREATE TABLE IF NOT EXISTS `cab_ride` (
  `cab_ride_id` int(11) NOT NULL AUTO_INCREMENT,
  `src_lat` varchar(20) NOT NULL,
  `src_long` varchar(20) NOT NULL,
  `dest_lat` varchar(20) NOT NULL,
  `dest_long` varchar(20) NOT NULL,
  `gps_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `gps_end_time` timestamp NULL DEFAULT NULL,
  `price` int(11) NOT NULL,
  `cancel` tinyint(1) NOT NULL,
  `shift_id` int(11) DEFAULT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `cust_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cab_ride_id`),
  KEY `cab_ride_ibfk_1` (`payment_id`),
  KEY `cab_ride_ibfk_2` (`cust_id`),
  KEY `shift_id` (`shift_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `car_model`
--

DROP TABLE IF EXISTS `car_model`;
CREATE TABLE IF NOT EXISTS `car_model` (
  `car_model_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_name` varchar(100) NOT NULL,
  `model_description` text NOT NULL,
  PRIMARY KEY (`car_model_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `cust_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `address` text,
  `password` varchar(40) NOT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
CREATE TABLE IF NOT EXISTS `driver` (
  `driver_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `license_no` varchar(30) NOT NULL,
  `expiry_date` date NOT NULL,
  `working` tinyint(1) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `address` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`driver_id`, `first_name`, `last_name`, `birth_date`, `license_no`, `expiry_date`, `working`, `password`, `phone`, `address`) VALUES
(1, 'Kamlesh', 'Shrouti', '2012-01-03', '772203451327', '2021-03-20', 1, '', '', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
CREATE TABLE IF NOT EXISTS `owner` (
  `owner_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `address` text NOT NULL,
  PRIMARY KEY (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `shift`
--

DROP TABLE IF EXISTS `shift`;
CREATE TABLE IF NOT EXISTS `shift` (
  `shift_id` int(11) NOT NULL AUTO_INCREMENT,
  `shift_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `shift_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `driver_id` int(11) DEFAULT NULL,
  `cab_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shift_id`),
  KEY `driver_id` (`driver_id`),
  KEY `cab_id` (`cab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cab`
--
ALTER TABLE `cab`
  ADD CONSTRAINT `cab_ibfk_1` FOREIGN KEY (`car_model_id`) REFERENCES `car_model` (`car_model_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`owner_id`) ON DELETE CASCADE;

--
-- Constraints for table `cab_ride`
--
ALTER TABLE `cab_ride`
  ADD CONSTRAINT `cab_ride_ibfk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ride_ibfk_2` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ride_ibfk_3` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`shift_id`) ON DELETE CASCADE;

--
-- Constraints for table `shift`
--
ALTER TABLE `shift`
  ADD CONSTRAINT `shift_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `shift_ibfk_2` FOREIGN KEY (`cab_id`) REFERENCES `cab` (`cab_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
