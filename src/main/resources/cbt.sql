-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 10, 2013 at 09:47 PM
-- Server version: 5.5.29
-- PHP Version: 5.4.6-1ubuntu1.1

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
-- Table structure for table `device`
--

CREATE TABLE IF NOT EXISTS `device` (
  `device_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `deviceunique_id` char(255) NOT NULL,
  `devicetype_id` bigint(20) NOT NULL,
  `state` enum('ONLINE','OFFLINE') NOT NULL DEFAULT 'OFFLINE',
  `deviceos_id` bigint(50) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`device_id`),
  UNIQUE KEY `deviceunique_id` (`deviceunique_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=41 ;

-- --------------------------------------------------------

--
-- Table structure for table `device_job`
--

CREATE TABLE IF NOT EXISTS `device_job` (
  `devicejob_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL,
  `testrun_id` bigint(20) NOT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('WAITING','CHECKEDOUT','RUNNING','FINISHED') NOT NULL DEFAULT 'WAITING',
  PRIMARY KEY (`devicejob_id`),
  KEY `device_id` (`device_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `device_os`
--

CREATE TABLE IF NOT EXISTS `device_os` (
  `deviceos_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` char(255) NOT NULL,
  PRIMARY KEY (`deviceos_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `device_type`
--

CREATE TABLE IF NOT EXISTS `device_type` (
  `devicetype_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `model` char(100) NOT NULL,
  `manufacture` char(100) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`devicetype_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `testprofile_devices`
--

CREATE TABLE IF NOT EXISTS `testprofile_devices` (
  `testprofile_id` bigint(20) NOT NULL,
  `device_type_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` char(30) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
