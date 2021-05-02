-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 02, 2021 at 08:31 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

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

CREATE TABLE `cab` (
  `cab_id` int(11) NOT NULL,
  `license_plate` varchar(50) NOT NULL,
  `manufacture_year` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `cab_lat` decimal(10,8) NOT NULL,
  `cab_lng` decimal(10,8) NOT NULL,
  `car_model_id` int(11) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL,
  `driver_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cab`
--

INSERT INTO `cab` (`cab_id`, `license_plate`, `manufacture_year`, `active`, `cab_lat`, `cab_lng`, `car_model_id`, `owner_id`, `driver_id`) VALUES
(1, 'MH-31-AB-5609', 2001, 0, '23.40000000', '69.80000000', 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `cab_ride`
--

CREATE TABLE `cab_ride` (
  `cab_ride_id` int(11) NOT NULL,
  `src_lat` decimal(10,8) NOT NULL,
  `src_long` decimal(10,8) NOT NULL,
  `dest_lat` decimal(10,8) NOT NULL,
  `dest_long` decimal(10,8) NOT NULL,
  `gps_start_time` varchar(50) DEFAULT NULL,
  `gps_end_time` varchar(50) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `shift_id` int(11) DEFAULT NULL,
  `payment_id` int(11) DEFAULT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `cab_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cab_ride`
--

INSERT INTO `cab_ride` (`cab_ride_id`, `src_lat`, `src_long`, `dest_lat`, `dest_long`, `gps_start_time`, `gps_end_time`, `price`, `status`, `shift_id`, `payment_id`, `cust_id`, `cab_id`) VALUES
(9, '12.45000000', '15.45000000', '45.54000000', '23.54000000', '05/02/2021 20:21:46', NULL, 75623, 2, NULL, 1, 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `car_model`
--

CREATE TABLE `car_model` (
  `car_model_id` int(11) NOT NULL,
  `model_name` varchar(100) NOT NULL,
  `model_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `car_model`
--

INSERT INTO `car_model` (`car_model_id`, `model_name`, `model_description`) VALUES
(1, 'Maruti Suzuki', 'Four Seater AC Car 1000CC');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(12) NOT NULL,
  `address` text DEFAULT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cust_id`, `name`, `email`, `phone`, `address`, `password`) VALUES
(6, 'Prathamesh Thombre', 'pthombre657@gmail.com', '7030584432', 'Ambegaon Bk, Pune', 'pict@123'),
(7, 'Tanaya', 'tanaya58@gmail.com', '7774031715', '', 'pict@123'),
(8, 'Ramesh', NULL, '7058974854', NULL, 'pict@123'),
(9, 'Ramu', NULL, '9999912346', NULL, 'pict@123'),
(10, 'Shyam', NULL, '8888812345', NULL, 'pict@123');

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `driver_id` int(11) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `license_no` varchar(30) NOT NULL,
  `expiry_date` date NOT NULL,
  `working` tinyint(1) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `address` varchar(40) DEFAULT NULL,
  `on_duty` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`driver_id`, `first_name`, `last_name`, `birth_date`, `license_no`, `expiry_date`, `working`, `password`, `phone`, `address`, `on_duty`) VALUES
(1, 'Kamlesh', 'Shrouti', '2012-01-03', '772203451327', '2021-03-20', 1, 'kamlesh@123', '9898987867', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `owner_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`owner_id`, `name`, `email`, `phone`, `address`) VALUES
(1, 'Rambhau Kathote', 'ram@gmail.com', '7777712345', 'Shree Siddhivinayak Manasvi, Ambegaon Bk, Pune');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL,
  `type_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`payment_id`, `type_name`) VALUES
(1, 'Cash'),
(2, 'PhonePay');

-- --------------------------------------------------------

--
-- Table structure for table `shift`
--

CREATE TABLE `shift` (
  `shift_id` int(11) NOT NULL,
  `shift_start_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `shift_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `driver_id` int(11) DEFAULT NULL,
  `cab_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cab`
--
ALTER TABLE `cab`
  ADD PRIMARY KEY (`cab_id`),
  ADD UNIQUE KEY `license_plate` (`license_plate`),
  ADD KEY `car_model_id` (`car_model_id`),
  ADD KEY `owner_id` (`owner_id`),
  ADD KEY `driver_id` (`driver_id`);

--
-- Indexes for table `cab_ride`
--
ALTER TABLE `cab_ride`
  ADD PRIMARY KEY (`cab_ride_id`),
  ADD KEY `cab_ride_ibfk_1` (`payment_id`),
  ADD KEY `cab_ride_ibfk_2` (`cust_id`),
  ADD KEY `shift_id` (`shift_id`),
  ADD KEY `cab_id` (`cab_id`);

--
-- Indexes for table `car_model`
--
ALTER TABLE `car_model`
  ADD PRIMARY KEY (`car_model_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`driver_id`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`owner_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `shift`
--
ALTER TABLE `shift`
  ADD PRIMARY KEY (`shift_id`),
  ADD KEY `driver_id` (`driver_id`),
  ADD KEY `cab_id` (`cab_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cab`
--
ALTER TABLE `cab`
  MODIFY `cab_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cab_ride`
--
ALTER TABLE `cab_ride`
  MODIFY `cab_ride_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `car_model`
--
ALTER TABLE `car_model`
  MODIFY `car_model_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `cust_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `driver_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `owner`
--
ALTER TABLE `owner`
  MODIFY `owner_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `shift`
--
ALTER TABLE `shift`
  MODIFY `shift_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cab`
--
ALTER TABLE `cab`
  ADD CONSTRAINT `cab_ibfk_1` FOREIGN KEY (`car_model_id`) REFERENCES `car_model` (`car_model_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `owner` (`owner_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ibfk_3` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`);

--
-- Constraints for table `cab_ride`
--
ALTER TABLE `cab_ride`
  ADD CONSTRAINT `cab_ride_ibfk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ride_ibfk_2` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ride_ibfk_3` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`shift_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cab_ride_ibfk_4` FOREIGN KEY (`cab_id`) REFERENCES `cab` (`cab_id`) ON DELETE CASCADE;

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
