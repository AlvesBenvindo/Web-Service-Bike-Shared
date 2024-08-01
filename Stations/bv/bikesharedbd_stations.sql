-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bikesharedbd
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `id` bigint NOT NULL,
  `bonus` int DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `endpoint` varchar(255) NOT NULL,
  `latitude` float DEFAULT NULL,
  `local_name` varchar(255) DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `qtd_docks` int DEFAULT NULL,
  `qtd_docks_dispo` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lrqkwb5adr07q4gkw8hypcl1n` (`endpoint`),
  UNIQUE KEY `UK_f6787k9crm2wetdsqyqc8xwt5` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,50,NULL,'http://localhost:8081/wsStation',-8.83807,'Fortaleza de Luanda (São Miguel)',13.2319,'CXX_station1',3,0,1),(2,100,NULL,'http://localhost:8082/wsStation',-8.82722,'Presidência da República',13.2314,'CXX_station2',4,0,1),(3,30,NULL,'http://localhost:8083/wsStation',-8.83118,'Palácio do Governo',13.2355,'CXX_station3',2,0,1),(4,40,NULL,'http://localhost:8084/wsStation',-8.83889,'Palácio da Justiça',13.2293,'CXX_station4',1,0,1);
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-30  2:40:27
