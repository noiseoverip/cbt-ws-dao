-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 06, 2013 at 10:14 PM
-- Server version: 5.5.24-log
-- PHP Version: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cbt`
--

-- --------------------------------------------------------

--
-- Table structure for table `devicejobs`
--

CREATE TABLE IF NOT EXISTS `devicejobs` (
  `devicejob_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL,
  `testrun_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('WAITING','RUNNING','FINISHED') NOT NULL DEFAULT 'WAITING',
  PRIMARY KEY (`devicejob_id`),
  KEY `device_id` (`device_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `devicejobs`
--

INSERT INTO `devicejobs` (`devicejob_id`, `device_id`, `testrun_id`, `created`, `updated`, `status`) VALUES
(1, 1, 1, '2013-03-03 17:51:03', '2013-03-05 20:22:32', 'WAITING');

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE IF NOT EXISTS `devices` (
  `device_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `deviceunique_id` char(255) NOT NULL,
  `devicetype_id` bigint(20) NOT NULL,
  `state` enum('ONLINE','OFFLINE') NOT NULL,
  `OS_VERSION` char(50) NOT NULL,
  `OS_TYPE` char(50) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `devicestypes`
--

CREATE TABLE IF NOT EXISTS `devicestypes` (
  `devicetype_id` int(11) NOT NULL AUTO_INCREMENT,
  `model` char(100) NOT NULL,
  `manufacture` char(100) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`devicetype_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `devicestypes`
--

INSERT INTO `devicestypes` (`devicetype_id`, `model`, `manufacture`, `updated`) VALUES
(1, 'desire s', 'HTC', '2013-03-03 20:19:23');

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE IF NOT EXISTS `results` (
  `result_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` enum('PASSED','FAILED''','') NOT NULL,
  `metadata` longtext NOT NULL COMMENT 'json string of metadata values',
  `test_id` bigint(20) NOT NULL,
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `testconfig`
--

CREATE TABLE IF NOT EXISTS `testconfig` (
  `testconfig_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `testpackage_id` bigint(20) NOT NULL,
  `testtarget_id` bigint(20) NOT NULL,
  `testprofile_id` bigint(11) NOT NULL,
  `metadata` longtext CHARACTER SET utf16 COLLATE utf16_unicode_ci,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`testconfig_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `testconfig`
--

INSERT INTO `testconfig` (`testconfig_id`, `user_id`, `testpackage_id`, `testtarget_id`, `testprofile_id`, `metadata`, `updated`) VALUES
(1, 1, 1, 1, 1, '', '2013-03-06 23:00:00'),
(2, 1, 1, 1, 1, NULL, '2013-03-11 23:00:00'),
(3, 1, 1, 1, 1, NULL, '2013-03-03 15:13:39');

-- --------------------------------------------------------

--
-- Table structure for table `testpackage`
--

CREATE TABLE IF NOT EXISTS `testpackage` (
  `testpackage_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `path` char(255) DEFAULT NULL,
  `user_id` bigint(11) NOT NULL,
  `metadata` longtext,
  `updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`testpackage_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `testpackage`
--

INSERT INTO `testpackage` (`testpackage_id`, `path`, `user_id`, `metadata`, `updated`) VALUES
(1, 'C://Dev//CBT//ws-store//1//tp-9//uiautomator.jar', 1, '{"something":"something"}', '2013-03-05 20:20:24');

-- --------------------------------------------------------

--
-- Table structure for table `testprofile`
--

CREATE TABLE IF NOT EXISTS `testprofile` (
  `testprofile_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `mode` enum('NORMAL','FAST') NOT NULL COMMENT 'simple, fast',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deviceTypeCount` int(11) NOT NULL,
  `metadata` longtext,
  PRIMARY KEY (`testprofile_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `testprofile`
--

INSERT INTO `testprofile` (`testprofile_id`, `user_id`, `mode`, `updated`, `deviceTypeCount`, `metadata`) VALUES
(1, 0, 'NORMAL', '2013-03-03 15:01:14', 3, NULL),
(8, 0, 'NORMAL', '2013-03-03 12:49:44', 3, NULL),
(9, 0, 'NORMAL', '2013-03-03 12:51:50', 3, NULL),
(10, 0, 'NORMAL', '2013-03-03 12:55:00', 3, NULL),
(11, 0, 'NORMAL', '2013-03-03 12:55:52', 3, NULL),
(12, 0, 'NORMAL', '2013-03-03 12:58:02', 3, NULL),
(13, 0, 'NORMAL', '2013-03-03 13:11:51', 3, NULL),
(14, 0, 'NORMAL', '2013-03-03 13:12:07', 3, NULL),
(15, 0, 'NORMAL', '2013-03-03 13:19:06', 3, NULL),
(16, 0, 'NORMAL', '2013-03-03 13:24:08', 3, NULL),
(17, 0, 'NORMAL', '2013-03-03 13:24:25', 3, NULL),
(18, 0, 'NORMAL', '2013-03-03 13:24:26', 3, NULL),
(19, 0, 'NORMAL', '2013-03-03 13:26:23', 3, NULL),
(20, 0, 'NORMAL', '2013-03-03 13:33:30', 3, NULL),
(21, 0, 'NORMAL', '2013-03-03 13:33:39', 3, NULL),
(22, 0, 'NORMAL', '2013-03-03 13:33:39', 3, NULL),
(23, 0, 'NORMAL', '2013-03-03 13:34:48', 3, NULL),
(24, 0, 'NORMAL', '2013-03-03 13:35:09', 3, NULL),
(25, 0, 'NORMAL', '2013-03-03 13:35:49', 3, NULL),
(26, 0, 'NORMAL', '2013-03-03 13:35:49', 3, NULL),
(27, 0, 'NORMAL', '2013-03-03 15:13:53', 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `testprofile_devices`
--

CREATE TABLE IF NOT EXISTS `testprofile_devices` (
  `testprofile_id` bigint(20) NOT NULL,
  `device_type_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `testprofile_devices`
--

INSERT INTO `testprofile_devices` (`testprofile_id`, `device_type_id`) VALUES
(12, 1),
(12, 2),
(12, 3),
(13, 1),
(13, 2),
(13, 3),
(14, 1),
(14, 2),
(14, 3),
(15, 1),
(15, 2),
(15, 3),
(16, 1),
(16, 2),
(16, 3),
(17, 1),
(17, 2),
(17, 3),
(18, 1),
(18, 2),
(18, 3),
(19, 1),
(19, 2),
(19, 3),
(20, 1),
(20, 2),
(20, 3),
(21, 1),
(21, 2),
(21, 3),
(22, 1),
(22, 2),
(22, 3),
(23, 1),
(23, 2),
(23, 3),
(24, 1),
(24, 2),
(24, 3),
(25, 1),
(25, 2),
(25, 3),
(26, 1),
(26, 2),
(26, 3),
(27, 1),
(27, 2),
(27, 3);

-- --------------------------------------------------------

--
-- Table structure for table `testrun`
--

CREATE TABLE IF NOT EXISTS `testrun` (
  `testrun_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `testconfig_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('WAITING','RUNNING','FINISHED') NOT NULL DEFAULT 'WAITING',
  PRIMARY KEY (`testrun_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `testrun`
--

INSERT INTO `testrun` (`testrun_id`, `user_id`, `testconfig_id`, `created`, `updated`, `status`) VALUES
(1, 1, 1, '2013-03-03 17:26:42', '2013-03-03 16:26:42', 'WAITING');

-- --------------------------------------------------------

--
-- Table structure for table `testtarget`
--

CREATE TABLE IF NOT EXISTS `testtarget` (
  `testtarget_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `path` char(255) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `metadata` text,
  PRIMARY KEY (`testtarget_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `testtarget`
--

INSERT INTO `testtarget` (`testtarget_id`, `user_id`, `path`, `updated`, `metadata`) VALUES
(1, 1, 'C://Dev//CBT//ws-store//1//tt-4//app-4.apk', '2013-03-05 20:20:58', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` char(30) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `name`) VALUES
(1, 'saulius');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
