-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: wasteless_db
-- ------------------------------------------------------
-- Server version	5.7.28-log

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
-- Table structure for table `bought_groceries`
--

DROP TABLE IF EXISTS `bought_groceries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bought_groceries` (
  `id_bought` int(10) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(90) NOT NULL,
  `quantity` int(11) NOT NULL,
  `calories` int(11) NOT NULL,
  `purchase_date` date NOT NULL,
  `expiration_date` date NOT NULL,
  `consumption_date` date DEFAULT NULL,
  `fk_user` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_bought`),
  KEY `fk_user_idx` (`fk_user`),
  CONSTRAINT `fk_user` FOREIGN KEY (`fk_user`) REFERENCES `users` (`id_users`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bought_groceries`
--

LOCK TABLES `bought_groceries` WRITE;
/*!40000 ALTER TABLE `bought_groceries` DISABLE KEYS */;
INSERT INTO `bought_groceries` (`id_bought`, `item_name`, `quantity`, `calories`, `purchase_date`, `expiration_date`, `consumption_date`, `fk_user`) VALUES (75,'Walnuts(100g)',3,654,'2020-03-26','2021-02-26','2020-04-22',1),(77,'Eggs(10)',5,720,'2020-04-22','2020-04-24','2020-04-22',1),(79,'Chicken Breast(700g)',1,1148,'2020-04-23','2020-04-24','2020-04-23',1),(80,'Green Beans(400g)',1,108,'2020-04-23','2020-04-24',NULL,1),(81,'Chicken Breast(700g)',1,1148,'2020-04-23','2020-04-25','2020-04-23',1),(82,'Grapes(100g)',3,67,'2020-04-23','2020-05-03','2020-04-23',1);
/*!40000 ALTER TABLE `bought_groceries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groceries`
--

DROP TABLE IF EXISTS `groceries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groceries` (
  `id_groceries` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `item_name` varchar(45) NOT NULL,
  `calories` int(11) NOT NULL DEFAULT '0',
  `days` int(11) NOT NULL,
  PRIMARY KEY (`id_groceries`),
  UNIQUE KEY `id_groceries_UNIQUE` (`id_groceries`),
  UNIQUE KEY `item_name_UNIQUE` (`item_name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groceries`
--

LOCK TABLES `groceries` WRITE;
/*!40000 ALTER TABLE `groceries` DISABLE KEYS */;
INSERT INTO `groceries` (`id_groceries`, `item_name`, `calories`, `days`) VALUES (1,'Apple',90,28),(2,'Bread(500g)',1300,4),(3,'Potatoes(1kg)',870,60),(4,'Grapes(100g)',67,10),(5,'Lemon',29,30),(6,'Green Beans(400g)',108,1095),(7,'Steak(500g)',1350,5),(8,'Chicken Breast(400g)',656,2),(9,'Chicken Breast(700g)',1148,2),(10,'Walnuts(100g)',654,365),(11,'Eggs(10)',720,14),(12,'Milk(1L)',423,2),(13,'Cheddar Cheese(300g)',996,33),(14,'Parmesan(200g)',862,182),(15,'Mozzarella Cheese(200g)',560,13);
/*!40000 ALTER TABLE `groceries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id_users` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id_users`),
  UNIQUE KEY `id_users_UNIQUE` (`id_users`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id_users`, `username`, `password`, `status`) VALUES (1,'Andrei98','andrei18',0),(2,'Ben','benpet',0),(3,'Cristina12','1234',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-23 16:33:40
