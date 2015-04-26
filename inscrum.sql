-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: inscrum
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'Work at Nitro','Steps to get a job in Nitro showing how amazing I am');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_column`
--

DROP TABLE IF EXISTS `board_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_column` (
  `boardColumn_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(125) DEFAULT NULL,
  `final_status` bit(1) DEFAULT NULL,
  `board_id` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`boardColumn_id`),
  KEY `FK_BOARD_idx` (`board_id`),
  CONSTRAINT `FK_BOARD` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_column`
--

LOCK TABLES `board_column` WRITE;
/*!40000 ALTER TABLE `board_column` DISABLE KEYS */;
INSERT INTO `board_column` VALUES (1,'Backlog','',1,1),(2,'Ready','',1,2),(3,'In Progress','\0',1,3),(4,'QA','\0',1,4),(5,'Done','',1,5);
/*!40000 ALTER TABLE `board_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_column_task`
--

DROP TABLE IF EXISTS `board_column_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_column_task` (
  `boardColumn_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`boardColumn_id`,`task_id`),
  KEY `FK_BOARD_COLUMN_TASK_TASK_idx` (`task_id`),
  CONSTRAINT `FK_BOARD_COLUMN_TASK_COLUMN` FOREIGN KEY (`boardColumn_id`) REFERENCES `board_column` (`boardColumn_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_BOARD_COLUMN_TASK_TASK` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_column_task`
--

LOCK TABLES `board_column_task` WRITE;
/*!40000 ALTER TABLE `board_column_task` DISABLE KEYS */;
INSERT INTO `board_column_task` VALUES (1,2,1),(2,3,0),(2,4,1),(3,5,0),(4,1,0);
/*!40000 ALTER TABLE `board_column_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `estimation` int(11) DEFAULT NULL,
  `acceptance_criteria` varchar(400) DEFAULT NULL,
  `blocked` bit(1) DEFAULT b'0',
  `blocked_reason` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Start learning Scala','Download different tutorial and start with the basis. Good books to read are OÂ´Reilly for example',1,'Acceptance 1','\0',NULL),(2,'Choose a IDE to Develop','Download three IDEÂ´s (Eclipse, NetBeans, IntelliJ). Do some test developing Scala applications and see what of them suits better for me',2,'Acceptance 2','\0',NULL),(3,'Get used to the basis of the language','Develop some examples and create my own programs no get used to the language',2,'Acceptance 3','\0',NULL),(4,'Start learning Play framework using Scala','Go to Play Framework page. Download tutorials and examples.',1,'Acceptance 4','','This task will be blocked until Devin realize that has to hire me by hook or by crook'),(5,'Start Kanbankaze as an Open Source Project','Create a new Play Project usign Activator',3,'Acceptance 5','\0',NULL);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_comment`
--

DROP TABLE IF EXISTS `task_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(4000) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `task_id` int(11) DEFAULT NULL,
  `user_guid` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_task_comment_task_idx` (`task_id`),
  KEY `fk_task_comment_user_idx` (`user_guid`),
  CONSTRAINT `fk_task_comment_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_comment_user` FOREIGN KEY (`user_guid`) REFERENCES `user` (`guid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_comment`
--

LOCK TABLES `task_comment` WRITE;
/*!40000 ALTER TABLE `task_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_user`
--

DROP TABLE IF EXISTS `task_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_user` (
  `task_id` int(11) NOT NULL,
  `user_guid` binary(16) NOT NULL,
  PRIMARY KEY (`task_id`,`user_guid`),
  KEY `fk_task_user_user_idx` (`user_guid`),
  CONSTRAINT `fk_task_user_task` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_user_user` FOREIGN KEY (`user_guid`) REFERENCES `user` (`guid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_user`
--

LOCK TABLES `task_user` WRITE;
/*!40000 ALTER TABLE `task_user` DISABLE KEYS */;
INSERT INTO `task_user` VALUES (1,'GÄÓ	FÉ—Bë+s™Áž'),(2,'£ô–XÆ¨Aj¸ûaµ£ƒ'),(5,'£ô–XÆ¨Aj¸ûaµ£ƒ'),(5,'³ÙQ@ù¶ýqý kBë'),(3,'òG|Ú…õ@ž^‡-¹ÃF');
/*!40000 ALTER TABLE `task_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `guid` binary(16) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `avatar` varchar(256) DEFAULT NULL,
  `salt` varchar(256) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by_guid` binary(16) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by_guid` binary(16) DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `deleted_by_guid` binary(16) DEFAULT NULL,
  PRIMARY KEY (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('GÄÓ	FÉ—Bë+s™Áž','QA','Engineer','qa@kanbankaze.com','$2a$10$pv7Za6I6PK8JPlrEsQW3Ju78MYcyRd.QlvfVBU5r4dBByPfABa8zi','profile/qa_128x128.png','$2a$10$pv7Za6I6PK8JPlrEsQW3Ju','2015-04-26 19:46:06','è\nUÊsŒD‘\\Š–®ÌÓ','2015-04-26 19:46:06','è\nUÊsŒD‘\\Š–®ÌÓ',NULL,NULL),('£ô–XÆ¨Aj¸ûaµ£ƒ','test','test','t@t.com','$2a$10$9bbeHaJImEaEaG/ObAmY4.E4jLakTAjkMyaVTuki7yeTmLRqz.s9y','user.png','$2a$10$9bbeHaJImEaEaG/ObAmY4.','2015-04-12 13:24:44','u•vI{˜\0õ3c“u','2015-04-12 13:24:44','u•vI{˜\0õ3c“u',NULL,NULL),('³ÙQ@ù¶ýqý kBë','Developer','1','dev1@kanbankaze.com','$2a$10$m/mFJCagJI9t5ROCP/k6tu7LjCCNHNAf4f9YQ/7ATuEVLlTtoUEwq','profile/dev_128x128.png','$2a$10$m/mFJCagJI9t5ROCP/k6tu','2015-04-26 19:45:10','Mà@TM™ŠûÓ-_•Æ','2015-04-26 19:45:10','Mà@TM™ŠûÓ-_•Æ',NULL,NULL),('Ô\03Éü×E8Œèçj¾','Scrum','Master','scrummaster@kanbankaze.com','$2a$10$J.YkpOn9duAAByXG9TxM8ORyEWOwXpmUeGhbPY93Zc9JRIFT47FVG','profile/scrumMaster_128x128.png','$2a$10$J.YkpOn9duAAByXG9TxM8O','2015-04-26 19:43:50','úø3¬ÑÇO¾«\n3ù=?‹õ','2015-04-26 19:43:50','úø3¬ÑÇO¾«\n3ù=?‹õ',NULL,NULL),('òG|Ú…õ@ž^‡-¹ÃF','Product','Owner','product@kanbankaze.com','$2a$10$8KPm0mqiz7rIY14ZnMKBH.usTOohPblb1G3IdsRhHGc0fDgu0i8l2','profile/productOwner_128x128.png','$2a$10$8KPm0mqiz7rIY14ZnMKBH.','2015-04-26 19:46:47','¹ÈY®;I=‚>/V»DX','2015-04-26 19:46:47','¹ÈY®;I=‚>/V»DX',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_access_tokens`
--

DROP TABLE IF EXISTS `user_access_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_access_tokens` (
  `access_token` varchar(60) NOT NULL,
  `refresh_token` varchar(60) DEFAULT NULL,
  `user_guid` binary(16) DEFAULT NULL,
  `scope` varchar(100) DEFAULT NULL,
  `expires_in` int(10) unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `client_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`access_token`),
  KEY `fk_user_access_tokens_user_idx` (`user_guid`),
  KEY `fk_user_access_tokens_clients_idx` (`client_id`),
  CONSTRAINT `fk_user_access_tokens_clients` FOREIGN KEY (`client_id`) REFERENCES `user_clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_access_tokens_user` FOREIGN KEY (`user_guid`) REFERENCES `user` (`guid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_access_tokens`
--

LOCK TABLES `user_access_tokens` WRITE;
/*!40000 ALTER TABLE `user_access_tokens` DISABLE KEYS */;
INSERT INTO `user_access_tokens` VALUES ('YmY1NjliZGMtNWI2NS00YjA3LTgwYjYtYWY5NTRmY2NhMGJm','NDE5YmVlZmItOTAxZS00NDE0LWIzMzQtZjJkZDU4NTdjZWI1','£ô–XÆ¨Aj¸ûaµ£ƒ',NULL,3600,'2015-04-26 19:58:59','web-application');
/*!40000 ALTER TABLE `user_access_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_auth_codes`
--

DROP TABLE IF EXISTS `user_auth_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_auth_codes` (
  `authorization_code` varchar(40) NOT NULL,
  `user_guid` binary(16) DEFAULT NULL,
  `redirect_uri` varchar(256) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `scope` varchar(100) DEFAULT NULL,
  `client_id` varchar(100) DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  PRIMARY KEY (`authorization_code`),
  KEY `fk_user_auth_codes_user_idx` (`user_guid`),
  KEY `fk_user_auth_codes_client_idx` (`client_id`),
  CONSTRAINT `fk_user_auth_codes_client` FOREIGN KEY (`client_id`) REFERENCES `user_clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_auth_codes_user` FOREIGN KEY (`user_guid`) REFERENCES `user` (`guid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_auth_codes`
--

LOCK TABLES `user_auth_codes` WRITE;
/*!40000 ALTER TABLE `user_auth_codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_auth_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_client_grant_types`
--

DROP TABLE IF EXISTS `user_client_grant_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_client_grant_types` (
  `client_id` varchar(100) NOT NULL,
  `grant_type_id` int(11) NOT NULL,
  PRIMARY KEY (`client_id`,`grant_type_id`),
  KEY `user_client_grant_types_grant_idx` (`grant_type_id`),
  CONSTRAINT `fk_user_client_grant_types_client` FOREIGN KEY (`client_id`) REFERENCES `user_clients` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_client_grant_types_grant` FOREIGN KEY (`grant_type_id`) REFERENCES `user_grant_types` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_client_grant_types`
--

LOCK TABLES `user_client_grant_types` WRITE;
/*!40000 ALTER TABLE `user_client_grant_types` DISABLE KEYS */;
INSERT INTO `user_client_grant_types` VALUES ('web-application',1);
/*!40000 ALTER TABLE `user_client_grant_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_clients`
--

DROP TABLE IF EXISTS `user_clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_clients` (
  `id` varchar(100) NOT NULL,
  `secret` varchar(256) DEFAULT NULL,
  `redirect_uri` varchar(256) DEFAULT NULL,
  `scope` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_clients`
--

LOCK TABLES `user_clients` WRITE;
/*!40000 ALTER TABLE `user_clients` DISABLE KEYS */;
INSERT INTO `user_clients` VALUES ('web-application','secret','http://localhost:9000','public');
/*!40000 ALTER TABLE `user_clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_confirmation_tokens`
--

DROP TABLE IF EXISTS `user_confirmation_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_confirmation_tokens` (
  `id` int(10) unsigned NOT NULL,
  `uuid` binary(16) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  `expiration_time` timestamp NULL DEFAULT NULL,
  `is_sign_up` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_confirmation_tokens_user_idx` (`uuid`),
  CONSTRAINT `user_confirmation_tokens_user` FOREIGN KEY (`uuid`) REFERENCES `user` (`guid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_confirmation_tokens`
--

LOCK TABLES `user_confirmation_tokens` WRITE;
/*!40000 ALTER TABLE `user_confirmation_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_confirmation_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_grant_types`
--

DROP TABLE IF EXISTS `user_grant_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_grant_types` (
  `id` int(11) NOT NULL,
  `grant_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_grant_types`
--

LOCK TABLES `user_grant_types` WRITE;
/*!40000 ALTER TABLE `user_grant_types` DISABLE KEYS */;
INSERT INTO `user_grant_types` VALUES (1,'password');
/*!40000 ALTER TABLE `user_grant_types` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-26 21:48:21
