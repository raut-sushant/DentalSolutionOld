/* SQLyog Ultimate v8.55 
MySQL - 5.1.48-community : 
Database - ekdant ********************************************************************* */ 
 /*!40101 SET NAMES utf8 */; 
 /*!40101 SET SQL_MODE=''*/; 
 /*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */; 
 /*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */; 
 /*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
 /*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */; 
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ekdant` /*!40100 DEFAULT CHARACTER SET latin1 */;
  /*Table structure for table `accesses` */  
DROP TABLE IF EXISTS `accesses`;  CREATE TABLE `accesses` (
  `accessId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `loginId` varchar(16) NOT NULL,
  `userType` varchar(45) NOT NULL,
  `description` varchar(100) NOT NULL,
  `accessType` enum('Entry','Exit') NOT NULL,
  `accessDate` datetime NOT NULL,
  PRIMARY KEY (`accessId`),
  KEY `FK_accesses` (`userId`),
  CONSTRAINT `FK_accesses` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=780 DEFAULT CHARSET=latin1;  

/*Table structure for table `agenda` */  
DROP TABLE IF EXISTS `agenda`;  CREATE TABLE `agenda` (
  `cod_agenda` int(11) NOT NULL AUTO_INCREMENT,
  `data_agen` varchar(10) NOT NULL,
  `hora_agen` varchar(5) NOT NULL,
  `nome_pac` varchar(60) NOT NULL,
  `nome_dent` varchar(60) NOT NULL,
  `observacao_agen` varchar(300) DEFAULT NULL,
  `nome_tra` varchar(60) DEFAULT NULL,
  `faltou_agen` enum('Yes','No') DEFAULT NULL,
  PRIMARY KEY (`cod_agenda`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  

/*Table structure for table `checkup` */  
DROP TABLE IF EXISTS `checkup`;  CREATE TABLE `checkup` (
  `checkUpId` int(11) NOT NULL AUTO_INCREMENT,
  `patientId` int(11) NOT NULL,
  `treatment` varchar(100) DEFAULT NULL,
  `dignosis` text,
  `priscription` text,
  `fees` float DEFAULT NULL,
  `nextVisitDate` datetime DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `dentistName` varchar(50) NOT NULL,
  `refferedBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`checkUpId`),
  KEY `FK_checkup` (`patientId`),
  KEY `FK_Dentist_checkup` (`dentistName`),
  CONSTRAINT `FK_checkup` FOREIGN KEY (`patientId`) REFERENCES `patients` (`PATIENTID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;  

/*Table structure for table `cheques` */  
DROP TABLE IF EXISTS `cheques`;  CREATE TABLE `cheques` (
  `cod_cheque` int(11) NOT NULL AUTO_INCREMENT,
  `nome_pac` varchar(60) DEFAULT NULL,
  `nometitular_cheq` varchar(60) DEFAULT NULL,
  `valor_cheq` varchar(10) DEFAULT NULL,
  `banco_cheq` varchar(50) DEFAULT NULL,
  `agencia_cheq` varchar(20) DEFAULT NULL,
  `conta_cheq` varchar(10) DEFAULT NULL,
  `compensacao_cheq` varchar(10) DEFAULT NULL,
  `status_cheq` enum('Pendente','Pago') DEFAULT NULL,
  PRIMARY KEY (`cod_cheque`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  

/*Table structure for table `city` */  
DROP TABLE IF EXISTS `city`;  CREATE TABLE `city` (
  `cityId` int(11) NOT NULL AUTO_INCREMENT,
  `cityName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;  

/*Table structure for table `dentists` */  
DROP TABLE IF EXISTS `dentists`;  CREATE TABLE `dentists` (
  `DENTISTID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `BIRTHDAY` date NOT NULL,
  `GENDER` enum('Male','Female') NOT NULL,
  `PHOTO` blob,
  `ADDRESS` varchar(60) DEFAULT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `TELEPHONE` varchar(14) DEFAULT NULL,
  `MOBILE` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`DENTISTID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;  

/*Table structure for table `employees` */  
DROP TABLE IF EXISTS `employees`;  CREATE TABLE `employees` (
  `EMPLOYEEID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `BIRTHDAY` date NOT NULL,
  `GENDER` enum('Male','Female') NOT NULL,
  `PHOTO` blob,
  `ADDRESS` varchar(60) DEFAULT NULL,
  `CITY` varchar(50) DEFAULT NULL,
  `TELEPHONE` varchar(14) NOT NULL,
  `MOBILE` varchar(14) DEFAULT NULL,
  PRIMARY KEY (`EMPLOYEEID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;  

/*Table structure for table `odontograma` */  
DROP TABLE IF EXISTS `odontograma`;  CREATE TABLE `odontograma` (
  `cod_odontograma` int(11) NOT NULL AUTO_INCREMENT,
  `PATIENTID` int(11) DEFAULT NULL,
  `nome_pac` varchar(60) NOT NULL,
  `18_odon` text,
  `17_odon` text,
  `16_odon` text,
  `15-55_odon` text,
  `14-54_odon` text,
  `13-53_odon` text,
  `12-52_odon` text,
  `11-51_odon` text,
  `41-81_odon` text,
  `42-82_odon` text,
  `43-83_odon` text,
  `44-84_odon` text,
  `45-85_odon` text,
  `46_odon` text,
  `47_odon` text,
  `48_odon` text,
  `28_odon` text,
  `27_odon` text,
  `26_odon` text,
  `65-25_odon` text,
  `64-24_odon` text,
  `63-23_odon` text,
  `62-22_odon` text,
  `61-21_odon` text,
  `71-31_odon` text,
  `72-32_odon` text,
  `73-33_odon` text,
  `74-34_odon` text,
  `75-35_odon` text,
  `36_odon` text,
  `37_odon` text,
  `38_odon` text,
  PRIMARY KEY (`cod_odontograma`),
  KEY `FK_odontograma` (`PATIENTID`),
  CONSTRAINT `FK_odontograma` FOREIGN KEY (`PATIENTID`) REFERENCES `patients` (`PATIENTID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;  

/*Table structure for table `patients` */  
DROP TABLE IF EXISTS `patients`;  CREATE TABLE `patients` (
  `PATIENTID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `CASEID` varchar(10) NOT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `GENDER` enum('Male','Female') NOT NULL,
  `PHOTO` blob,
  `ADDRESS` varchar(60) DEFAULT NULL,
  `CITY` varchar(50) DEFAULT NULL,
  `TELEPHONE` varchar(14) DEFAULT NULL,
  `MOBILE` varchar(14) DEFAULT NULL,
  `EMAIL` varchar(60) DEFAULT NULL,
  `AGE` int(11) NOT NULL,
  PRIMARY KEY (`PATIENTID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;  

/*Table structure for table `photos` */  
DROP TABLE IF EXISTS `photos`;  CREATE TABLE `photos` (
  `photoId` int(11) NOT NULL AUTO_INCREMENT,
  `patientId` int(11) DEFAULT NULL,
  `photo` blob,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`photoId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  

/*Table structure for table `treatments` */  
DROP TABLE IF EXISTS `treatments`;  CREATE TABLE `treatments` (
  `treatmentId` int(11) NOT NULL AUTO_INCREMENT,
  `treatmentName` varchar(50) NOT NULL,
  `treatmentDescription` text,
  `treatmentCharges` float DEFAULT NULL,
  PRIMARY KEY (`treatmentId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;  

/*Table structure for table `users` */  
DROP TABLE IF EXISTS `users`;  CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `loginId` varchar(16) NOT NULL,
  `password` varchar(8) NOT NULL,
  `userType` enum('Employee','Dentist','Administrador') NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;  

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */; 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */; 
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */; 
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */; 
