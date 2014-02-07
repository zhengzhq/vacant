/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.6.13 : Database - sampledb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sampledb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sampledb`;

/*Table structure for table `t_login_log` */

DROP TABLE IF EXISTS `t_login_log`;

CREATE TABLE `t_login_log` (
  `login_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `ip` varchar(23) DEFAULT NULL,
  `login_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`login_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_login_log` */

insert  into `t_login_log`(`login_log_id`,`user_id`,`ip`,`login_datetime`) values (1,1,'192.168.12.7','2014-01-16 13:18:12'),(2,1,'127.0.0.1','2014-01-16 13:55:16'),(3,1,'192.168.12.7','2014-01-16 14:03:33');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL,
  `credits` int(11) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `last_visit` datetime DEFAULT NULL,
  `last_ip` varchar(23) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`user_id`,`user_name`,`credits`,`password`,`last_visit`,`last_ip`) values (1,'admin',15,'123456','2014-01-16 14:03:33','192.168.12.7');

/*Table structure for table `vacant_department` */

DROP TABLE IF EXISTS `vacant_department`;

CREATE TABLE `vacant_department` (
  `id` varchar(36) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_department` */

/*Table structure for table `vacant_resource` */

DROP TABLE IF EXISTS `vacant_resource`;

CREATE TABLE `vacant_resource` (
  `id` varchar(36) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  `is_display` varchar(1) DEFAULT NULL COMMENT '是否显示',
  `display_order` int(2) DEFAULT NULL COMMENT '显示顺序，不显示不用填',
  `display_name` varchar(20) DEFAULT NULL COMMENT '显示名称，不显示不用填',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '上级id，不显示不用填',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_resource` */

insert  into `vacant_resource`(`id`,`url`,`is_display`,`display_order`,`display_name`,`parent_id`) values ('57c46197-87b6-11e3-b558-08606e729ccc','#','1',1,'模块1',NULL),('5cfdfc40-87b6-11e3-b558-08606e729ccc','/model1/main','1',1,'页面1','57c46197-87b6-11e3-b558-08606e729ccc'),('77410bbd-87b6-11e3-b558-08606e729ccc','/model1/page2','1',2,'页面2','57c46197-87b6-11e3-b558-08606e729ccc'),('cf4ac3ec-870c-11e3-a27f-08606e729ccc','/login','0',NULL,NULL,NULL),('e023c0a6-8f40-11e3-b8e7-00215d2e38e8','/model1/query_page','0',NULL,NULL,NULL);

/*Table structure for table `vacant_resource_role` */

DROP TABLE IF EXISTS `vacant_resource_role`;

CREATE TABLE `vacant_resource_role` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_resource_role` */

insert  into `vacant_resource_role`(`id`,`resource_id`,`role_id`) values ('4f4247ff-8f42-11e3-b8e7-00215d2e38e8','e023c0a6-8f40-11e3-b8e7-00215d2e38e8','6f913757-870d-11e3-a27f-08606e729ccc'),('57c46197-87b6-11e3-b558-08606e729ccc','57c46197-87b6-11e3-b558-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc'),('bd8d4e18-870d-11e3-a27f-08606e729ccc','cf4ac3ec-870c-11e3-a27f-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc'),('d49ea829-87b6-11e3-b558-08606e729ccc','77410bbd-87b6-11e3-b558-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc'),('e6443fce-87b6-11e3-b558-08606e729ccc','5cfdfc40-87b6-11e3-b558-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc');

/*Table structure for table `vacant_role` */

DROP TABLE IF EXISTS `vacant_role`;

CREATE TABLE `vacant_role` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_role` */

insert  into `vacant_role`(`id`,`name`,`description`) values ('6f913757-870d-11e3-a27f-08606e729ccc','管理员','拥有系统所有权限');

/*Table structure for table `vacant_user` */

DROP TABLE IF EXISTS `vacant_user`;

CREATE TABLE `vacant_user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `area_code` varchar(12) DEFAULT NULL COMMENT '区划代码',
  `department_id` varchar(36) DEFAULT NULL COMMENT '部门id',
  `login_name` varchar(24) DEFAULT NULL COMMENT '登录名',
  `password` varchar(160) DEFAULT NULL COMMENT '密码',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `is_written_off` varchar(1) DEFAULT '0',
  `written_off_date` varchar(10) DEFAULT NULL,
  `written_off_reason` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_user` */

insert  into `vacant_user`(`id`,`area_code`,`department_id`,`login_name`,`password`,`name`,`is_written_off`,`written_off_date`,`written_off_reason`) values ('2b689697-8f93-11e3-b984-08606e729ccc','220104',NULL,'vacant','1','vacant','0',NULL,NULL),('a0f5e677-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user1','1','user1','0',NULL,NULL),('a0fa7b46-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user2','1','user2','0',NULL,NULL),('a0ff0c5d-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user3','1','user3','0',NULL,NULL),('a104c4c8-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user4','1','user4','0',NULL,NULL),('a10a8105-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user5','1','user5','0',NULL,NULL),('a10ef91b-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user6','1','user6','0',NULL,NULL),('a1138b0c-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user7','1','user7','0',NULL,NULL),('a117e882-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user8','8','user8','0',NULL,NULL),('a11c828e-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user9','1','user9','0',NULL,NULL),('a120e93f-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user1','10','user10','0',NULL,NULL),('a125664d-8f93-11e3-b984-08606e729ccc',NULL,NULL,'user1','11','user11','0',NULL,NULL),('fd27a7bd-870d-11e3-a27f-08606e729ccc','220104',NULL,'scott','1','郑志强','0',NULL,NULL);

/*Table structure for table `vacant_user_role` */

DROP TABLE IF EXISTS `vacant_user_role`;

CREATE TABLE `vacant_user_role` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_user_role` */

insert  into `vacant_user_role`(`id`,`user_id`,`role_id`) values ('3c14097d-870e-11e3-a27f-08606e729ccc','fd27a7bd-870d-11e3-a27f-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
