# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.27)
# Database: demo
# Generation Time: 2019-08-25 12:25:01 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table vacant_area
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_area`;

CREATE TABLE `vacant_area` (
  `id` varchar(36) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL COMMENT '上级id',
  `code` varchar(12) NOT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `parent_code` varchar(12) DEFAULT NULL COMMENT '上级代码',
  `full_name` varchar(50) DEFAULT NULL COMMENT '完整名称（县乡村）',
  `state` char(1) DEFAULT NULL COMMENT '是否可用：1是0否',
  `create_time` varchar(19) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划';

LOCK TABLES `vacant_area` WRITE;
/*!40000 ALTER TABLE `vacant_area` DISABLE KEYS */;

INSERT INTO `vacant_area` (`id`, `parent_id`, `code`, `name`, `parent_code`, `full_name`, `state`, `create_time`)
VALUES
	('2201',NULL,'2201','长春市',NULL,'长春市','1','20190824 06:16'),
	('7dc9d3bf-17de-412f-a465-95885a6b78e8','2201','220102','南关区',NULL,'长春市南关区','1','2019-08-25 19:03:06');

/*!40000 ALTER TABLE `vacant_area` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_czjl
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_czjl`;

CREATE TABLE `vacant_czjl` (
  `id` varchar(36) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '操作名称',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `params` varchar(500) DEFAULT NULL COMMENT '参数，json格式',
  `czr_id` varchar(36) DEFAULT NULL COMMENT '操作人id',
  `czr_name` varchar(45) DEFAULT NULL COMMENT '操作人姓名',
  `czsj` varchar(19) DEFAULT NULL COMMENT '操作时间',
  `ip` varchar(15) DEFAULT NULL COMMENT '客户端ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作记录	';

LOCK TABLES `vacant_czjl` WRITE;
/*!40000 ALTER TABLE `vacant_czjl` DISABLE KEYS */;

INSERT INTO `vacant_czjl` (`id`, `name`, `path`, `params`, `czr_id`, `czr_name`, `czsj`, `ip`)
VALUES
	('4023ff10-c720-11e9-a426-e2d78f47b5b9','修改角色','/vacant/role/save','id:[admin],menus:[1-zdshbz,11-csdbh,111-sqdj,112-sh,113,114,90-vacant,9010-vacant-security,be091612-7224-4951-94a5-8d11a4734083,901010-vacant-security,bb1bc31c-f63d-4679-90f2-05af334ac0e5,4bae304b-609a-42cf-b681-f1c2c62626f9],name:[管理员]','d640fc7c-62f4-11e9-9ced-33540d45353a','测试仪','2019-08-25 18:08:14','0:0:0:0:0:0:0:1'),
	('c90c6b84-c723-11e9-a426-e2d78f47b5b9','添加菜单','/vacant/menu/save','id:[],parentId:[9010-vacant-security],xssx:[0],name:[行政区划],path:[/vacant/area],rel:[vacant_area]','d640fc7c-62f4-11e9-9ced-33540d45353a','测试仪','2019-08-25 18:33:32','0:0:0:0:0:0:0:1'),
	('d118c17e-c723-11e9-a426-e2d78f47b5b9','修改角色','/vacant/role/save','id:[admin],menus:[1-zdshbz,11-csdbh,111-sqdj,112-sh,113,114,90-vacant,9010-vacant-security,be091612-7224-4951-94a5-8d11a4734083,d035e69a-4397-4d1f-a666-82c7a0c836a3,901010-vacant-security,bb1bc31c-f63d-4679-90f2-05af334ac0e5,4bae304b-609a-42cf-b681-f1c2c62626f9],name:[管理员]','d640fc7c-62f4-11e9-9ced-33540d45353a','测试仪','2019-08-25 18:33:45','0:0:0:0:0:0:0:1'),
	('ea835f58-c727-11e9-a426-e2d78f47b5b9','添加行政区划','/vacant/area/insert','id:[],parentId:[2201],subCode:[02],name:[南关区],state:[1]','d640fc7c-62f4-11e9-9ced-33540d45353a','测试仪','2019-08-25 19:03:06','127.0.0.1'),
	('f62ee4b2-c727-11e9-a426-e2d78f47b5b9','添加用户','/vacant/dept/save','id:[],createTime:[],areaCode:[2201],area_full_name:[长春市],name:[长春市民政局]','d640fc7c-62f4-11e9-9ced-33540d45353a','测试仪','2019-08-25 19:03:25','127.0.0.1');

/*!40000 ALTER TABLE `vacant_czjl` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_dept
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_dept`;

CREATE TABLE `vacant_dept` (
  `id` varchar(36) NOT NULL,
  `area_code` varchar(12) DEFAULT NULL COMMENT '行政区划',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `create_time` varchar(19) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

LOCK TABLES `vacant_dept` WRITE;
/*!40000 ALTER TABLE `vacant_dept` DISABLE KEYS */;

INSERT INTO `vacant_dept` (`id`, `area_code`, `name`, `create_time`)
VALUES
	('933857be-b819-457d-b4e6-96bd2fd068a3','2201','长春市民政局','2019-08-25 19:03:25');

/*!40000 ALTER TABLE `vacant_dept` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_lookup
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_lookup`;

CREATE TABLE `vacant_lookup` (
  `id` varchar(36) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `code` varchar(20) DEFAULT NULL COMMENT '代码',
  `text` varchar(50) DEFAULT NULL COMMENT '文本',
  `state` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查找表';

LOCK TABLES `vacant_lookup` WRITE;
/*!40000 ALTER TABLE `vacant_lookup` DISABLE KEYS */;

INSERT INTO `vacant_lookup` (`id`, `parent_id`, `type`, `code`, `text`, `state`)
VALUES
	('6d837e18-61a0-11e9-b772-60def37681fc','root','lookup_type','demo','示例查找类型，应符合<table>_<field>，或common_<type>',NULL),
	('975f1f53-61a0-11e9-b772-60def37681fc','root','common_state','1','有效','1'),
	('9ae81f1f-61a0-11e9-b772-60def37681fc','root','common_state','0','无效','1');

/*!40000 ALTER TABLE `vacant_lookup` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_menu`;

CREATE TABLE `vacant_menu` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '上级菜单id，顶级菜单的parent_id为root',
  `xssx` int(11) DEFAULT NULL COMMENT '在父节点下的显示顺序',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `rel` varchar(50) DEFAULT NULL COMMENT '选项卡id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `vacant_menu` WRITE;
/*!40000 ALTER TABLE `vacant_menu` DISABLE KEYS */;

INSERT INTO `vacant_menu` (`id`, `parent_id`, `xssx`, `name`, `path`, `rel`)
VALUES
	('1-zdshbz','root',1,'最低生活保障',NULL,NULL),
	('11-csdbh','1-zdshbz',1,'城市低保户',NULL,NULL),
	('111-sqdj','11-csdbh',1,'城市低保户申请登记','/zdshbz/csdbh/sqdj','zdshbz_csdbh_sqdj'),
	('112-sh','11-csdbh',2,'城市低保户申请审核','/zdshbz/csdbh/sqsh','zdshbz_csdbh_sqsh'),
	('113','11-csdbh',3,'hello','/hello','hello'),
	('114','11-csdbh',4,'我的主页','/main','main'),
	('4bae304b-609a-42cf-b681-f1c2c62626f9','9010-vacant-security',30,'用户','/vacant/user','vacant_security_user'),
	('90-vacant','root',90,'系统','AreaService',NULL),
	('9010-vacant-security','90-vacant',10,'安全','',''),
	('901010-vacant-security','9010-vacant-security',10,'菜单','/vacant/menu','vacant_security_menu'),
	('bb1bc31c-f63d-4679-90f2-05af334ac0e5','9010-vacant-security',20,'角色','/vacant/role','vacant_role'),
	('be091612-7224-4951-94a5-8d11a4734083','9010-vacant-security',0,'单位','/vacant/dept','vacant_dept'),
	('d035e69a-4397-4d1f-a666-82c7a0c836a3','9010-vacant-security',0,'行政区划','/vacant/area','vacant_area');

/*!40000 ALTER TABLE `vacant_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_role`;

CREATE TABLE `vacant_role` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `name` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `vacant_role` WRITE;
/*!40000 ALTER TABLE `vacant_role` DISABLE KEYS */;

INSERT INTO `vacant_role` (`id`, `name`)
VALUES
	('admin','管理员'),
	('user','普通用户');

/*!40000 ALTER TABLE `vacant_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_role_menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_role_menu`;

CREATE TABLE `vacant_role_menu` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `role_id` varchar(36) DEFAULT NULL,
  `menu_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx02` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `vacant_role_menu` WRITE;
/*!40000 ALTER TABLE `vacant_role_menu` DISABLE KEYS */;

INSERT INTO `vacant_role_menu` (`id`, `role_id`, `menu_id`)
VALUES
	('d11667f8-c723-11e9-a426-e2d78f47b5b9','admin','1-zdshbz'),
	('d116953e-c723-11e9-a426-e2d78f47b5b9','admin','11-csdbh'),
	('d116d81e-c723-11e9-a426-e2d78f47b5b9','admin','111-sqdj'),
	('d11710ae-c723-11e9-a426-e2d78f47b5b9','admin','112-sh'),
	('d1172fc6-c723-11e9-a426-e2d78f47b5b9','admin','113'),
	('d1174542-c723-11e9-a426-e2d78f47b5b9','admin','114'),
	('d11813e6-c723-11e9-a426-e2d78f47b5b9','admin','4bae304b-609a-42cf-b681-f1c2c62626f9'),
	('d11755aa-c723-11e9-a426-e2d78f47b5b9','admin','90-vacant'),
	('d1176c20-c723-11e9-a426-e2d78f47b5b9','admin','9010-vacant-security'),
	('d117df34-c723-11e9-a426-e2d78f47b5b9','admin','901010-vacant-security'),
	('d117fb90-c723-11e9-a426-e2d78f47b5b9','admin','bb1bc31c-f63d-4679-90f2-05af334ac0e5'),
	('d117880e-c723-11e9-a426-e2d78f47b5b9','admin','be091612-7224-4951-94a5-8d11a4734083'),
	('d117b7a2-c723-11e9-a426-e2d78f47b5b9','admin','d035e69a-4397-4d1f-a666-82c7a0c836a3'),
	('b0999b7a-6301-11e9-9ced-33540d45353a','user','114');

/*!40000 ALTER TABLE `vacant_role_menu` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_user`;

CREATE TABLE `vacant_user` (
  `id` varchar(36) NOT NULL,
  `area_code` varchar(12) DEFAULT NULL COMMENT '区划代码',
  `dept_id` varchar(36) DEFAULT NULL COMMENT '单位id',
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `username` varchar(45) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `state` char(1) DEFAULT NULL COMMENT '是否启用：0否1是',
  `create_time` varchar(19) DEFAULT NULL COMMENT '创建时间',
  `role_id` varchar(36) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx02` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

LOCK TABLES `vacant_user` WRITE;
/*!40000 ALTER TABLE `vacant_user` DISABLE KEYS */;

INSERT INTO `vacant_user` (`id`, `area_code`, `dept_id`, `name`, `username`, `password`, `state`, `create_time`, `role_id`)
VALUES
	('d640fc7c-62f4-11e9-9ced-33540d45353a','2201','933857be-b819-457d-b4e6-96bd2fd068a3','测试仪','user','$2a$10$FMMcUDs0wcBk2EsCh0oFkOY87k5w2fJ4hRTn7PEolDYVnPtIMrvAO','1',NULL,'admin');

/*!40000 ALTER TABLE `vacant_user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vacant_user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `vacant_user_role`;

CREATE TABLE `vacant_user_role` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户名',
  `role_id` varchar(36) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx02` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限';

LOCK TABLES `vacant_user_role` WRITE;
/*!40000 ALTER TABLE `vacant_user_role` DISABLE KEYS */;

INSERT INTO `vacant_user_role` (`id`, `user_id`, `role_id`)
VALUES
	('d2343b8e-630e-11e9-9ced-33540d45353a','d640fc7c-62f4-11e9-9ced-33540d45353a','admin'),
	('f9d9fdee-62f6-11e9-9ced-33540d45353a','d640fc7c-62f4-11e9-9ced-33540d45353a','user');

/*!40000 ALTER TABLE `vacant_user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
