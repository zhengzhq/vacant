/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.6.16 : Database - sampledb
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

/*Table structure for table `vacant_area` */

DROP TABLE IF EXISTS `vacant_area`;

CREATE TABLE `vacant_area` (
  `id` varchar(36) NOT NULL,
  `code` varchar(15) DEFAULT NULL COMMENT '区划代码',
  `name` varchar(45) DEFAULT NULL COMMENT '区划名称',
  `is_written_off` varchar(1) DEFAULT NULL,
  `written_off_date` varchar(10) DEFAULT NULL,
  `written_off_reason` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划';

/*Data for the table `vacant_area` */

/*Table structure for table `vacant_department` */

DROP TABLE IF EXISTS `vacant_department`;

CREATE TABLE `vacant_department` (
  `id` varchar(36) NOT NULL,
  `organ_id` varchar(36) DEFAULT NULL COMMENT '组织机构id',
  `name` varchar(45) DEFAULT NULL COMMENT '部门名称',
  `is_written_off` varchar(1) DEFAULT NULL,
  `written_off_date` varchar(10) DEFAULT NULL,
  `written_off_reason` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门，从属于部门';

/*Data for the table `vacant_department` */

insert  into `vacant_department`(`id`,`organ_id`,`name`,`is_written_off`,`written_off_date`,`written_off_reason`) values ('1','99714b2b-98af-11e3-853b-00215d2e38e8','优抚科','0',NULL,NULL),('2','99714b2b-98af-11e3-853b-00215d2e38e8','社会救助科','0',NULL,NULL);

/*Table structure for table `vacant_dictionary` */

DROP TABLE IF EXISTS `vacant_dictionary`;

CREATE TABLE `vacant_dictionary` (
  `id` varchar(36) NOT NULL,
  `type` varchar(45) DEFAULT NULL COMMENT '类型',
  `name` varchar(45) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

/*Data for the table `vacant_dictionary` */

insert  into `vacant_dictionary`(`id`,`type`,`name`) values ('b227cb15-96b5-11e3-9ea5-00215d2e38e8','yes_or_no','是、否'),('f4061295-9252-11e3-894c-00215d2e38e8','gender','性别');

/*Table structure for table `vacant_dictionary_item` */

DROP TABLE IF EXISTS `vacant_dictionary_item`;

CREATE TABLE `vacant_dictionary_item` (
  `id` varchar(36) NOT NULL,
  `dictionary_id` varchar(36) DEFAULT NULL COMMENT '字典id',
  `code` varchar(45) DEFAULT NULL COMMENT '代码',
  `value` varchar(45) DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项';

/*Data for the table `vacant_dictionary_item` */

insert  into `vacant_dictionary_item`(`id`,`dictionary_id`,`code`,`value`) values ('141711fe-9253-11e3-894c-00215d2e38e8','f4061295-9252-11e3-894c-00215d2e38e8','1','男'),('1abdc642-9253-11e3-894c-00215d2e38e8','f4061295-9252-11e3-894c-00215d2e38e8','2','女'),('2e23014e-96b6-11e3-9ea5-00215d2e38e8','b227cb15-96b5-11e3-9ea5-00215d2e38e8','1','是'),('2ed9e3c1-96b6-11e3-9ea5-00215d2e38e8','b227cb15-96b5-11e3-9ea5-00215d2e38e8','0','否');

/*Table structure for table `vacant_operation_log` */

DROP TABLE IF EXISTS `vacant_operation_log`;

CREATE TABLE `vacant_operation_log` (
  `id` varchar(36) NOT NULL,
  `user_login_name` varchar(20) DEFAULT NULL COMMENT '登录用户名',
  `user_name` varchar(45) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(45) DEFAULT NULL COMMENT '操作',
  `description` varchar(200) DEFAULT NULL COMMENT '操作描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_operation_log` */

/*Table structure for table `vacant_organ` */

DROP TABLE IF EXISTS `vacant_organ`;

CREATE TABLE `vacant_organ` (
  `id` varchar(36) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL COMMENT '上级单位id',
  `area_code` varchar(15) DEFAULT NULL COMMENT '区划代码',
  `level` varchar(1) DEFAULT NULL COMMENT '单位级别，organ_level',
  `name` varchar(45) DEFAULT NULL COMMENT '名称',
  `is_top` varchar(1) DEFAULT '0' COMMENT '是否是顶级组织机构',
  `is_written_off` varchar(1) DEFAULT '0',
  `written_off_date` varchar(10) DEFAULT NULL,
  `written_off_reason` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

/*Data for the table `vacant_organ` */

insert  into `vacant_organ`(`id`,`parent_id`,`area_code`,`level`,`name`,`is_top`,`is_written_off`,`written_off_date`,`written_off_reason`) values ('99714b2b-98af-11e3-853b-00215d2e38e8',NULL,'220105','3','二道区民政局','1','0',NULL,NULL),('c22c5a59-98af-11e3-853b-00215d2e38e8','99714b2b-98af-11e3-853b-00215d2e38e8','220105001','4','八里堡','0','0',NULL,NULL);

/*Table structure for table `vacant_resource` */

DROP TABLE IF EXISTS `vacant_resource`;

CREATE TABLE `vacant_resource` (
  `id` varchar(36) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL COMMENT '上级id，不显示不用填',
  `name` varchar(20) DEFAULT NULL COMMENT '显示名称，不显示不用填',
  `url` varchar(50) DEFAULT NULL,
  `display_order` int(2) DEFAULT NULL COMMENT '显示顺序，不显示不用填',
  `is_page` varchar(1) DEFAULT NULL COMMENT '是否是页面',
  `is_top` varchar(1) DEFAULT NULL COMMENT '是否是顶级资源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_resource` */

insert  into `vacant_resource`(`id`,`parent_id`,`name`,`url`,`display_order`,`is_page`,`is_top`) values ('57c46197-87b6-11e3-b558-08606e729ccc','','系统管理','#',2,'0','1'),('5cfdfc40-87b6-11e3-b558-08606e729ccc','57c46197-87b6-11e3-b558-08606e729ccc','用户管理','/user',3,'1','0'),('77410bbd-87b6-11e3-b558-08606e729ccc','57c46197-87b6-11e3-b558-08606e729ccc','资源管理','/resource',1,'1','0'),('df64d0e5-93f7-11e3-a33e-00215d2e38e8','57c46197-87b6-11e3-b558-08606e729ccc','角色管理','/role',2,'1','0');

/*Table structure for table `vacant_resource_role` */

DROP TABLE IF EXISTS `vacant_resource_role`;

CREATE TABLE `vacant_resource_role` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `resource_id` varchar(36) DEFAULT NULL COMMENT '资源id',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_resource_role` */

insert  into `vacant_resource_role`(`id`,`resource_id`,`role_id`) values ('0f6dacef-93f8-11e3-a33e-00215d2e38e8','df64d0e5-93f7-11e3-a33e-00215d2e38e8','6f913757-870d-11e3-a27f-08606e729ccc'),('57c46197-87b6-11e3-b558-08606e729ccc','57c46197-87b6-11e3-b558-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc'),('bd8d4e18-870d-11e3-a27f-08606e729ccc','cf4ac3ec-870c-11e3-a27f-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc'),('d49ea829-87b6-11e3-b558-08606e729ccc','77410bbd-87b6-11e3-b558-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc'),('e6443fce-87b6-11e3-b558-08606e729ccc','5cfdfc40-87b6-11e3-b558-08606e729ccc','6f913757-870d-11e3-a27f-08606e729ccc');

/*Table structure for table `vacant_role` */

DROP TABLE IF EXISTS `vacant_role`;

CREATE TABLE `vacant_role` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `description` varchar(50) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_role` */

insert  into `vacant_role`(`id`,`name`,`description`) values ('6f913757-870d-11e3-a27f-08606e729ccc','管理员','拥有系统所有权限'),('7cd0f80b-4623-423c-a06c-cc6b520c4ef9','啊啊','小学校');

/*Table structure for table `vacant_user` */

DROP TABLE IF EXISTS `vacant_user`;

CREATE TABLE `vacant_user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `area_code` varchar(12) DEFAULT NULL COMMENT '区划代码',
  `organ_id` varchar(36) DEFAULT NULL COMMENT '组织机构id',
  `department_id` varchar(36) DEFAULT NULL COMMENT '部门id',
  `login_name` varchar(24) DEFAULT NULL COMMENT '登录名',
  `password` varchar(160) DEFAULT NULL COMMENT '密码',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `gender` varchar(1) DEFAULT NULL COMMENT '性别，dict=gender',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  `is_written_off` varchar(1) DEFAULT '0' COMMENT '是否已注销',
  `written_off_date` varchar(10) DEFAULT NULL COMMENT '注销日期',
  `written_off_reason` varchar(45) DEFAULT NULL COMMENT '注销原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vacant_user` */

insert  into `vacant_user`(`id`,`area_code`,`organ_id`,`department_id`,`login_name`,`password`,`name`,`gender`,`role_id`,`is_written_off`,`written_off_date`,`written_off_reason`) values ('04ff082a-4600-4ca2-bb86-ddb29e3babaa',NULL,NULL,'1','add','add','add','1','6f913757-870d-11e3-a27f-08606e729ccc','1','2014-02-12','删除功能测试'),('267b01ac-a596-4fd6-b3d5-9d32d4110550',NULL,'c22c5a59-98af-11e3-853b-00215d2e38e8',NULL,'combobox','combobox','combobox','1','6f913757-870d-11e3-a27f-08606e729ccc','0',NULL,NULL),('27d35122-cc3a-48b7-8f10-baefbe9fb80c',NULL,'c22c5a59-98af-11e3-853b-00215d2e38e8',NULL,'联动测试','1','联动测试','1','6f913757-870d-11e3-a27f-08606e729ccc','0',NULL,NULL),('2b689697-8f93-11e3-b984-08606e729ccc',NULL,NULL,'2','vacant','1','vacant','2','6f913757-870d-11e3-a27f-08606e729ccc','1','2014-02-12','删除功能测试'),('42aa560d-525f-4d01-a70c-046f021a17fb',NULL,NULL,NULL,'add',NULL,'add',NULL,NULL,'1','2014-02-11',''),('5f9ad8ae-5cf9-4477-b017-259295e2be2d',NULL,NULL,'1','1','1','1',NULL,'6f913757-870d-11e3-a27f-08606e729ccc','1','2014-02-15','删除功能测试'),('a0f5e677-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user1','1','user1',NULL,NULL,'1','2014-02-11','删除功能测试'),('a0fa7b46-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user2','1','user2',NULL,NULL,'1','2014-02-11','删除功能测试'),('a0ff0c5d-8f93-11e3-b984-08606e729ccc',NULL,NULL,'1','user3','1','user3',NULL,NULL,'1','2014-02-12','删除功能测试'),('a104c4c8-8f93-11e3-b984-08606e729ccc',NULL,'c22c5a59-98af-11e3-853b-00215d2e38e8',NULL,'user4','1','user4','2','6f913757-870d-11e3-a27f-08606e729ccc','0',NULL,NULL),('a10a8105-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user5','1','user5',NULL,NULL,'1','2014-02-12','删除功能测试'),('a10ef91b-8f93-11e3-b984-08606e729ccc',NULL,'99714b2b-98af-11e3-853b-00215d2e38e8','1','user6','1','user6','1','6f913757-870d-11e3-a27f-08606e729ccc','0',NULL,NULL),('a1138b0c-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user77',NULL,'user7',NULL,NULL,NULL,NULL,NULL),('a117e882-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user88',NULL,'user8',NULL,NULL,NULL,NULL,NULL),('a11c828e-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user99',NULL,'user9',NULL,NULL,'1','2014-02-12','删除功能测试'),('a120e93f-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user1',NULL,'user1',NULL,NULL,'1','2014-02-12','删除功能测试'),('a125664d-8f93-11e3-b984-08606e729ccc',NULL,NULL,NULL,'user1','11','user11',NULL,NULL,'1','2014-02-12','删除功能测试'),('d5e2f868-603e-4159-8fb9-004587a356f9',NULL,'99714b2b-98af-11e3-853b-00215d2e38e8',NULL,'啊啊','爸爸','a此次','2','7cd0f80b-4623-423c-a06c-cc6b520c4ef9','0',NULL,NULL),('fd27a7bd-870d-11e3-a27f-08606e729ccc',NULL,'99714b2b-98af-11e3-853b-00215d2e38e8','2','scott','1','正','1','6f913757-870d-11e3-a27f-08606e729ccc','0',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
