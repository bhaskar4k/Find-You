CREATE DATABASE  IF NOT EXISTS `find_you` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `find_you`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: find_you
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `post_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `comment_id` varchar(255) NOT NULL,
  `comment` mediumtext NOT NULL,
  `comment_time` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES ('1|2023-03-17T11:06:13.746','1','1||1|2023-03-17T11:06:13.746||2023.03.17 - 11:59:24','ok','2023.03.17 - 11:59:24'),('1|2023-03-17T11:06:13.746','2','2||1|2023-03-17T11:06:13.746||2023.03.17 - 12:24:28','gg','2023.03.17 - 12:24:28'),('2|2023-03-17T12:21:44.113','1','1||2|2023-03-17T12:21:44.113||2023.03.17 - 12:54:51','k','2023.03.17 - 12:54:51'),('2|2023-03-17T12:21:52.901','3','3||2|2023-03-17T12:21:52.901||2023.03.17 - 14:04:02','chottu chamar','2023.03.17 - 14:04:02'),('1|2023-03-17T11:06:13.746','3','3||1|2023-03-17T11:06:13.746||2023.03.17 - 14:08:02','fffffffff','2023.03.17 - 14:08:02'),('1|2023-03-17T10:19:08.240','3','3||1|2023-03-17T10:19:08.240||2023.03.17 - 14:08:07','j','2023.03.17 - 14:08:07'),('22023-03-22T10:11:44.941','2','22023-03-22T10:11:44.94122023.03.22 - 10:12:01','rand','2023.03.22 - 10:12:01'),('42023-03-22T11:27:28.210','4','42023-03-22T11:27:28.21042023.03.22 - 11:27:35','aadada','2023.03.22 - 11:27:35'),('4|2023-03-22T11:30:22.594','4','4|2023-03-22T11:30:22.594||4||2023.03.22 - 11:30:26','ggg','2023.03.22 - 11:30:26'),('4|2023-03-22T11:31:15.345','4','4||4|2023-03-22T11:31:15.345||2023.03.22 - 11:31:18','ddad','2023.03.22 - 11:31:18'),('4|2023-03-22T12:18:37.981','4','4||4|2023-03-22T12:18:37.981||2023.03.22 - 12:19:31','ggg','2023.03.22 - 12:19:31'),('4|2023-03-22T12:20:41.778','4','4||4|2023-03-22T12:20:41.778||2023.03.22 - 12:20:51','dadaa','2023.03.22 - 12:20:51'),('4|2023-03-22T12:17:58.926','4','4||4|2023-03-22T12:17:58.926||2023.03.22 - 12:24:47','ggggggggggggg','2023.03.22 - 12:24:47'),('5|2023-03-22T12:26:36.367','5','5||5|2023-03-22T12:26:36.367||2023.03.22 - 12:27:37','faf','2023.03.22 - 12:27:37'),('4|2023-03-22T12:20:41.778','5','5||4|2023-03-22T12:20:41.778||2023.03.22 - 12:41:44','nice working','2023.03.22 - 12:41:44'),('4|2023-03-22T12:20:41.778','5','5||4|2023-03-22T12:20:41.778||2023.03.22 - 12:41:51','nt','2023.03.22 - 12:41:51'),('1|2023-03-17T10:19:08.240','2','2||1|2023-03-17T10:19:08.240||2023.03.26 - 01:28:01','nt','2023.03.26 - 01:28:01'),('1|2023-03-17T11:06:13.746','2','2||1|2023-03-17T11:06:13.746||2023.03.26 - 01:28:25','ntnt','2023.03.26 - 01:28:25'),('1|2023-03-17T11:06:13.746','2','2||1|2023-03-17T11:06:13.746||2023.03.27 - 19:20:49','Nice re bhasu..','2023.03.27 - 19:20:49'),('6|2023-03-27T22:43:21.600','1','1||6|2023-03-27T22:43:21.600||2023.03.27 - 22:44:37','mora chudir bhai','2023.03.27 - 22:44:37'),('5|2023-03-22T12:26:36.367','1','1||5|2023-03-22T12:26:36.367||2023.03.27 - 22:45:21','Hego rendir daktar','2023.03.27 - 22:45:21'),('1|2023-03-28T11:39:59.818','2','2||1|2023-03-28T11:39:59.818||2023.03.28 - 11:41:37','mang','2023.03.28 - 11:41:37'),('1|2023-03-28T11:39:59.818','2','2||1|2023-03-28T11:39:59.818||2023.03.28 - 11:41:37','','2023.03.28 - 11:41:37'),('1|2023-03-28T11:39:59.818','2','2||1|2023-03-28T11:39:59.818||2023.03.28 - 11:42:11','','2023.03.28 - 11:42:11'),('1|2023-03-28T11:39:59.818','2','2||1|2023-03-28T11:39:59.818||2023.03.28 - 11:42:11','','2023.03.28 - 11:42:11'),('1|2023-03-28T11:39:59.818','2','2||1|2023-03-28T11:39:59.818||2023.03.28 - 11:43:39','sud','2023.03.28 - 11:43:39'),('1|2023-03-28T11:39:59.818','2','2||1|2023-03-28T11:39:59.818||2023.03.28 - 11:44:42','nt','2023.03.28 - 11:44:42'),('2|2023-03-28T11:47:30.585','2','2||2|2023-03-28T11:47:30.585||2023.03.28 - 11:47:46','nt','2023.03.28 - 11:47:46'),('2|2023-03-28T11:47:30.585','1','1||2|2023-03-28T11:47:30.585||2023.03.29 - 21:12:51','nt re','2023.03.29 - 21:12:51'),('4|2023-03-22T12:17:48.120','1','1||4|2023-03-22T12:17:48.120||2023.04.03 - 01:13:47','cmnted from feed','2023.04.03 - 01:13:47'),('5|2023-03-22T12:26:36.367','2','2||5|2023-03-22T12:26:36.367||2023.04.04 - 11:54:00','Rendi','2023.04.04 - 11:54:00'),('4|2023-03-22T12:10:22.828','2','2||4|2023-03-22T12:10:22.828||2023.04.04 - 12:14:30','nt akash','2023.04.04 - 12:14:30'),('2|2023-04-04T12:33:17.924','2','2||2|2023-04-04T12:33:17.924||2023.04.04 - 12:33:42','lo dewde','2023.04.04 - 12:33:42');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follower_info`
--

DROP TABLE IF EXISTS `follower_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follower_info` (
  `user_id` varchar(255) NOT NULL,
  `follower_user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follower_info`
--

LOCK TABLES `follower_info` WRITE;
/*!40000 ALTER TABLE `follower_info` DISABLE KEYS */;
INSERT INTO `follower_info` VALUES ('1','3'),('3','1'),('6','1'),('5','1'),('1','4'),('4','1'),('2','3'),('1','2'),('2','1'),('5','2');
/*!40000 ALTER TABLE `follower_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `following_info`
--

DROP TABLE IF EXISTS `following_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `following_info` (
  `user_id` varchar(255) NOT NULL,
  `following_user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `following_info`
--

LOCK TABLES `following_info` WRITE;
/*!40000 ALTER TABLE `following_info` DISABLE KEYS */;
INSERT INTO `following_info` VALUES ('3','1'),('1','3'),('1','6'),('1','5'),('4','1'),('1','4'),('3','2'),('2','1'),('1','2'),('2','5');
/*!40000 ALTER TABLE `following_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like`
--

DROP TABLE IF EXISTS `like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like` (
  `post_id` varchar(300) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like`
--

LOCK TABLES `like` WRITE;
/*!40000 ALTER TABLE `like` DISABLE KEYS */;
INSERT INTO `like` VALUES ('1|2023-03-17T10:19:08.240','1'),('1|2023-03-17T11:06:13.746','1'),('2|2023-03-17T12:21:52.901','1'),('2|2023-03-17T12:21:44.113','1'),('2|2023-03-17T12:21:52.901','3'),('1|2023-03-17T11:06:13.746','3'),('1|2023-03-17T10:19:08.240','3'),('2|2023-03-17T12:21:52.901','2'),('2|2023-03-17T12:21:44.113','2'),('22023-03-22T10:11:44.941','2'),('42023-03-22T11:27:28.210','4'),('4|2023-03-22T11:31:15.345','4'),('4|2023-03-22T12:18:37.981','4'),('4|2023-03-22T12:20:41.778','4'),('4|2023-03-22T12:17:58.926','4'),('5|2023-03-22T12:26:36.367','5'),('2|2023-03-26T01:27:41.814','2'),('1|2023-03-17T10:19:08.240','2'),('1|2023-03-17T11:06:13.746','2'),('6|2023-03-27T22:43:21.600','1'),('5|2023-03-22T12:26:36.367','1'),('1|2023-03-28T11:39:59.818','2'),('2|2023-03-28T11:47:30.585','2'),('4|2023-03-22T12:17:48.120','1'),('4|2023-03-22T11:30:22.594','2'),('5|2023-03-22T12:26:36.367','2'),('4|2023-03-22T12:13:17.901','2'),('4|2023-03-22T12:10:22.828','2');
/*!40000 ALTER TABLE `like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `user_id` varchar(255) NOT NULL,
  `post_id` varchar(500) NOT NULL,
  `post` mediumtext NOT NULL,
  `upload_time` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES ('1','1|2023-03-17T10:19:08.240','nt','2023.03.17 - 10:19:08'),('1','1|2023-03-17T11:06:13.746','Post-223','2023.03.17 - 11:06:13'),('2','2|2023-03-17T12:21:44.113','gud','2023.03.17 - 12:21:44'),('2','2|2023-03-17T12:21:52.901','Mang','2023.03.17 - 12:21:52'),('2','22023-03-22T10:11:44.941','Rand','2023.03.22 - 10:11:44'),('4','42023-03-22T11:27:28.210','afafa','2023.03.22 - 11:27:28'),('4','4|2023-03-22T11:30:22.594','afafa','2023.03.22 - 11:30:22'),('4','4|2023-03-22T11:31:15.345','afafa','2023.03.22 - 11:31:15'),('4','4|2023-03-22T11:48:45.337','ff','2023.03.22 - 11:48:45'),('4','4|2023-03-22T11:49:41.967','fd','2023.03.22 - 11:49:41'),('4','4|2023-03-22T11:50:21.084','g','2023.03.22 - 11:50:21'),('4','4|2023-03-22T11:52:18.210','gggg','2023.03.22 - 11:52:18'),('4','4|2023-03-22T11:54:30.702','nhk','2023.03.22 - 11:54:30'),('4','4|2023-03-22T11:55:14.764','ddfg','2023.03.22 - 11:55:14'),('4','4|2023-03-22T11:55:47.985','d','2023.03.22 - 11:55:47'),('4','4|2023-03-22T11:57:24.794','djjjjjj','2023.03.22 - 11:57:24'),('4','4|2023-03-22T12:01:33.996','djjjjjj','2023.03.22 - 12:01:33'),('4','4|2023-03-22T12:04:28.505','dhk','2023.03.22 - 12:04:28'),('4','4|2023-03-22T12:06:48.080','dada','2023.03.22 - 12:06:48'),('4','4|2023-03-22T12:08:28.105','vvvvvvvvvvv','2023.03.22 - 12:08:28'),('4','4|2023-03-22T12:10:22.828','s','2023.03.22 - 12:10:22'),('4','4|2023-03-22T12:13:17.901','a','2023.03.22 - 12:13:17'),('4','4|2023-03-22T12:17:48.120','b','2023.03.22 - 12:17:48'),('4','4|2023-03-22T12:17:58.926','c','2023.03.22 - 12:17:58'),('4','4|2023-03-22T12:18:07.799','jj','2023.03.22 - 12:18:07'),('4','4|2023-03-22T12:18:37.981','nhk','2023.03.22 - 12:18:37'),('4','4|2023-03-22T12:20:41.778','hhh','2023.03.22 - 12:20:41'),('5','5|2023-03-22T12:26:36.367','notgg','2023.03.22 - 12:26:36'),('2','2|2023-03-26T01:27:41.814','ramdy','2023.03.26 - 01:27:41'),('6','6|2023-03-27T22:43:21.600','Nandi mora','2023.03.27 - 22:43:21'),('1','1|2023-03-28T11:39:59.818','sodon','2023.03.28 - 11:39:59'),('2','2|2023-03-28T11:47:30.585','balchod','2023.03.28 - 11:47:30'),('2','2|2023-04-04T12:32:33.647','Bainchod new post','2023.04.04 - 12:32:33'),('2','2|2023-04-04T12:33:17.924','Udkhankir dim','2023.04.04 - 12:33:17');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_photo`
--

DROP TABLE IF EXISTS `profile_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_photo` (
  `user_id` varchar(255) NOT NULL,
  `imageFileName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_photo`
--

LOCK TABLES `profile_photo` WRITE;
/*!40000 ALTER TABLE `profile_photo` DISABLE KEYS */;
INSERT INTO `profile_photo` VALUES ('1','comica1593979994337.jpg'),('2','_DSC1175.JPG'),('3','IMG_20190520_165637-01.jpeg'),('4','IMG_2428.JPG'),('5','DOM color.PNG'),('6','IMG_2474.JPG');
/*!40000 ALTER TABLE `profile_photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `gender` varchar(45) NOT NULL,
  `email` varchar(250) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `bio` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'Bhaskar','Nandy','2023-03-01','male','bhasu@mail.com','123','123','jod chele bhai.. awp god.. bisal awp kore.. dhapadhap mare.. udma kill.. r seta vanga kelani dai.. op flayer.. Sathe cheleo valo khub.. khub i vodro chele.. Khisti daina.. Ato valo chele ki bolbo.. Dhon tao khub boro.. Meye chudbo'),(2,'Arijit','Pan','2023-03-03','male','pan@mail.com','123','123','noob noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob  noob '),(3,'Chamar','Das','2023-03-01','male','chamar@mail.com','123','123','nt'),(4,'Akash','Singha','2023-03-11','male','akash@mail.com','123','123','Lorem ipsum, or lipsum as it is sometimes known, is dummy text used in laying out print, graphic or web designs. The passage is attributed to an unknown typesetter in the 15th century who is'),(5,'Ritam','Bisui','2023-03-01','male','daktar@mail.com','123','123','DADAD ADAWAWD AD WAd AA dDDDad aDWAddAWFaF W  DWAW fawA   aD WADAwFSFWaDadWadADwaDAWwF AW aw dAW aw DA'),(6,'Mora','Mora','2023-03-03','male','mora@mail.com','123','123',NULL),(7,'Dalapati','Gandu','2023-03-03','male','dala@mail.com','123','123',NULL);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-04 12:50:24
