/*
SQLyog Professional v12.08 (64 bit)
MySQL - 5.6.13 : Database - smartwall_6.0
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smartwall_6.0` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `smartwall_6.0`;

/*Table structure for table `sw_sa` */

DROP TABLE IF EXISTS `sw_sa`;

CREATE TABLE `sw_sa` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pic_path` varchar(50) NOT NULL COMMENT '图片路径',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `height` double NOT NULL COMMENT '高度',
  `width` double NOT NULL COMMENT '宽度',
  `gmt_create` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(13) DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
