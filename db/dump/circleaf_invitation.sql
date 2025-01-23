-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: circleaf
-- ------------------------------------------------------
-- Server version	8.4.3

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
-- Table structure for table `invitation`
--

DROP TABLE IF EXISTS `invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` binary(16) NOT NULL,
  `team_id` int DEFAULT NULL,
  `sender` int DEFAULT NULL,
  `recipient` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` int DEFAULT NULL,
  `update_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `team_id` (`team_id`),
  KEY `sender` (`sender`),
  KEY `recipient` (`recipient`),
  KEY `create_by` (`create_by`),
  KEY `update_by` (`update_by`),
  CONSTRAINT `invitation_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  CONSTRAINT `invitation_ibfk_2` FOREIGN KEY (`sender`) REFERENCES `account` (`id`),
  CONSTRAINT `invitation_ibfk_3` FOREIGN KEY (`recipient`) REFERENCES `account` (`id`),
  CONSTRAINT `invitation_ibfk_4` FOREIGN KEY (`create_by`) REFERENCES `account` (`id`),
  CONSTRAINT `invitation_ibfk_5` FOREIGN KEY (`update_by`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitation`
--

LOCK TABLES `invitation` WRITE;
/*!40000 ALTER TABLE `invitation` DISABLE KEYS */;
INSERT INTO `invitation` VALUES (2,_binary '˛45!I©r@à è˛',2,1,14,0,'2024-12-30 01:03:43',1,'2024-12-30 01:03:43',1),(3,_binary '≠7\Î{E†£CSo6\“\‘ı',2,1,14,0,'2024-12-30 01:03:58',1,'2024-12-30 01:03:58',1),(4,_binary '´\‰<qõJèµ¿´…üra\‰',1,1,14,0,'2024-12-30 01:08:16',1,'2024-12-30 01:08:16',1),(5,_binary '\Â7~æ˚3H!¶é’§ì',1,1,1,2,'2024-12-30 02:06:23',1,'2025-01-01 03:15:49',1);
/*!40000 ALTER TABLE `invitation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-19 23:07:37
