-- MySQL dump 10.13  Distrib 8.2.0, for macos13 (x86_64)
--
-- Host: 122.228.237.118    Database: pet-life-platform
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advertisement` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ad_position` varchar(50) NOT NULL COMMENT '广告位标识（1-6）',
  `ad_name` varchar(100) NOT NULL COMMENT '广告自定义名称',
  `brand` varchar(100) NOT NULL COMMENT '品牌方名称',
  `status` tinyint NOT NULL COMMENT '状态：1-运行中，2-已结清，3-已失效',
  `click_count` int NOT NULL DEFAULT '0' COMMENT '点击量，初始为0',
  `cleard` tinyint NOT NULL DEFAULT '0' COMMENT '是否结清：0-未结清，1-已结清',
  `ad_revenue` decimal(10,2) DEFAULT NULL COMMENT '收入（元）',
  `revenue_attachment` varchar(255) DEFAULT NULL COMMENT '打款截图文件路径',
  `target_url` varchar(255) NOT NULL COMMENT '广告目标链接（https格式）',
  `ad_image` varchar(255) NOT NULL COMMENT '广告图片文件路径',
  `ad_start_time` datetime NOT NULL COMMENT '广告开始时间（YYYY-MM-DD）',
  `ad_end_time` datetime NOT NULL COMMENT '广告结束时间（YYYY-MM-DD）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(50) NOT NULL COMMENT '创建人账号',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `last_updater` varchar(50) NOT NULL COMMENT '最后更新人账号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ad_position` (`ad_position`,`status`),
  KEY `idx_status` (`status`) COMMENT '按状态筛选索引',
  KEY `idx_ad_position` (`ad_position`) COMMENT '按广告位筛选索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='广告信息表（含新增、编辑、状态流转等全量字段）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement`
--

LOCK TABLES `advertisement` WRITE;
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_check_ad_time` BEFORE INSERT ON `advertisement` FOR EACH ROW BEGIN
    IF NEW.`ad_start_time` > NEW.`ad_end_time` THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '广告开始时间不能晚于结束时间';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_check_ad_time_update` BEFORE UPDATE ON `advertisement` FOR EACH ROW BEGIN
    IF NEW.`ad_start_time` > NEW.`ad_end_time` THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '广告开始时间不能晚于结束时间';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
INSERT INTO `gen_table` (`table_id`, `table_name`, `table_comment`, `sub_table_name`, `sub_table_fk_name`, `class_name`, `tpl_category`, `tpl_web_type`, `package_name`, `module_name`, `business_name`, `function_name`, `function_author`, `gen_type`, `gen_path`, `options`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'login_log','登录日志表',NULL,NULL,'LoginLog','crud','','com.petlife.platform.system','system','log','登录日志','ruoyi','0','/',NULL,'admin','2025-06-26 13:07:41','',NULL,NULL),(2,'pet_info','宠物信息表',NULL,NULL,'PetInfo','crud','','com.petlife.platform.system','system','info','宠物信息','ruoyi','0','/',NULL,'admin','2025-06-26 13:07:41','',NULL,NULL),(3,'status_info','用户状态信息表',NULL,NULL,'StatusInfo','crud','','com.petlife.platform.system','system','info','用户状态信息','ruoyi','0','/',NULL,'admin','2025-06-26 13:07:41','',NULL,NULL),(4,'user','用户表',NULL,NULL,'User','crud','','com.petlife.platform.system','system','user','用户','ruoyi','0','/',NULL,'admin','2025-06-26 13:07:48','',NULL,NULL),(5,'pet_breed','宠物品种',NULL,NULL,'PetBreed','crud','element-ui','com.petlife.platform.system','system','breed','宠物品种','ruoyi','0','/','{\"parentMenuId\":1061}','admin','2025-06-30 11:40:46','','2025-06-30 11:41:14',NULL),(6,'pet_classification','宠物分类',NULL,NULL,'PetClassification','crud','element-ui','com.petlife.platform.system','system','classification','宠物分类','ruoyi','0','/','{\"parentMenuId\":1061}','admin','2025-06-30 11:40:48','','2025-07-03 14:51:53',NULL);
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
INSERT INTO `gen_table_column` (`column_id`, `table_id`, `column_name`, `column_comment`, `column_type`, `java_type`, `java_field`, `is_pk`, `is_increment`, `is_required`, `is_insert`, `is_edit`, `is_list`, `is_query`, `query_type`, `html_type`, `dict_type`, `sort`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (1,1,'LoginID','登录日志 ID','int unsigned','String','LoginID','1','1','0','1',NULL,NULL,NULL,'EQ',NULL,'',1,'admin','2025-06-26 13:07:41','',NULL),(2,1,'UserID','用户ID','int unsigned','String','UserID','0','0','1','1','1','1','1','EQ',NULL,'',2,'admin','2025-06-26 13:07:41','',NULL),(3,1,'LoginMethod','登录方法（0=手机号验证码登录，1=密码登录，2=本机号码一键登录，3=生物识别登录）','tinyint','Long','LoginMethod','0','0','1','1','1','1','1','EQ','input','',3,'admin','2025-06-26 13:07:41','',NULL),(4,1,'LoginTime','登录时间','datetime','Date','LoginTime','0','0','1','1','1','1','1','EQ','datetime','',4,'admin','2025-06-26 13:07:41','',NULL),(5,1,'LoginIP','登录IP','varchar(50)','String','LoginIP','0','0','1','1','1','1','1','EQ','input','',5,'admin','2025-06-26 13:07:41','',NULL),(6,1,'Location','登录位置','varchar(100)','String','Location','0','0','1','1','1','1','1','EQ','input','',6,'admin','2025-06-26 13:07:41','',NULL),(7,1,'DeviceModel','设备型号','varchar(100)','String','DeviceModel','0','0','1','1','1','1','1','EQ','input','',7,'admin','2025-06-26 13:07:41','',NULL),(8,1,'DefaultLocation','常驻地','varchar(100)','String','DefaultLocation','0','0','0','1','1','1','1','EQ','input','',8,'admin','2025-06-26 13:07:41','',NULL),(9,1,'LoginResult','登录结果（0=成功，1=失败）','tinyint','Long','LoginResult','0','0','1','1','1','1','1','EQ','input','',9,'admin','2025-06-26 13:07:41','',NULL),(10,1,'FailCount','连续登录失败次数','tinyint','Long','FailCount','0','0','1','1','1','1','1','EQ','input','',10,'admin','2025-06-26 13:07:41','',NULL),(11,1,'ErrorReason','登录失败的错误原因','varchar(200)','String','ErrorReason','0','0','0','1','1','1','1','EQ','input','',11,'admin','2025-06-26 13:07:41','',NULL),(12,2,'PetID','宠物 ID','int unsigned','String','PetID','1','1','0','1',NULL,NULL,NULL,'EQ',NULL,'',1,'admin','2025-06-26 13:07:41','',NULL),(13,2,'UserID','用户 ID','int unsigned','String','UserID','0','0','1','1','1','1','1','EQ',NULL,'',2,'admin','2025-06-26 13:07:41','',NULL),(14,2,'PetNickName','宠物昵称（唯一）','varchar(10)','String','PetNickName','0','0','1','1','1','1','1','LIKE','input','',3,'admin','2025-06-26 13:07:41','',NULL),(15,2,'PetClass','宠物分类','varchar(10)','String','PetClass','0','0','1','1','1','1','1','EQ','input','',4,'admin','2025-06-26 13:07:41','',NULL),(16,2,'PetBreed','宠物品种','varchar(50)','String','PetBreed','0','0','1','1','1','1','1','EQ','input','',5,'admin','2025-06-26 13:07:41','',NULL),(17,2,'PetGender','宠物性别(0=未设置，1=公，2=母)','tinyint','Long','PetGender','0','0','1','1','1','1','1','EQ','input','',6,'admin','2025-06-26 13:07:41','',NULL),(18,2,'Sterilized','是否绝育 (0=否，1=是)','tinyint','Long','Sterilized','0','0','1','1','1','1','1','EQ','input','',7,'admin','2025-06-26 13:07:41','',NULL),(19,2,'PetBirthday','宠物生日','date','Date','PetBirthday','0','0','1','1','1','1','1','EQ','datetime','',8,'admin','2025-06-26 13:07:41','',NULL),(20,2,'AdoptionDate','领养/购买日期','date','Date','AdoptionDate','0','0','1','1','1','1','1','EQ','datetime','',9,'admin','2025-06-26 13:07:41','',NULL),(21,2,'PetAvatarURL','宠物头像','varchar(255)','String','PetAvatarURL','0','0','1','1','1','1','1','EQ','input','',10,'admin','2025-06-26 13:07:41','',NULL),(22,2,'Status','状态 (0=生效，1=已死亡，2=已注销)','tinyint','Long','Status','0','0','1','1','1','1','1','EQ','radio','',11,'admin','2025-06-26 13:07:41','',NULL),(23,3,'StatusID','状态记录ID','int unsigned','String','StatusID','1','1','0','1',NULL,NULL,NULL,'EQ',NULL,'',1,'admin','2025-06-26 13:07:41','',NULL),(24,3,'UserID','用户ID','int unsigned','String','UserID','0','0','1','1','1','1','1','EQ',NULL,'',2,'admin','2025-06-26 13:07:41','',NULL),(25,3,'ChangeType','状态变更类型(0=注册激活，1=手动冻结，2=自动冻结，3=注销申请，4=注销完成)','tinyint','Long','ChangeType','0','0','1','1','1','1','1','EQ','select','',3,'admin','2025-06-26 13:07:41','',NULL),(26,3,'OldStatus','变更前状态（对应User.Status）','tinyint','Long','OldStatus','0','0','1','1','1','1','1','EQ','radio','',4,'admin','2025-06-26 13:07:41','',NULL),(27,3,'NewStatus','变更后状态（对应User.Status）','tinyint','Long','NewStatus','0','0','1','1','1','1','1','EQ','radio','',5,'admin','2025-06-26 13:07:41','',NULL),(28,3,'ChangeTime','自动生成当前时间','datetime','Date','ChangeTime','0','0','1','1','1','1','1','EQ','datetime','',6,'admin','2025-06-26 13:07:41','',NULL),(29,3,'OperatorType','操作人类型 (0=系统自动，1=用户自助，2=管理员手动)','tinyint','Long','OperatorType','0','0','1','1','1','1','1','EQ','select','',7,'admin','2025-06-26 13:07:41','',NULL),(30,3,'Reason','记录状态变更的具体原因','varchar(200)','String','Reason','0','0','1','1','1','1','1','EQ','input','',8,'admin','2025-06-26 13:07:41','',NULL),(31,3,'Proof','存储操作凭证（如验证码、工单编号）','varchar(255)','String','Proof','0','0','0','1','1','1','1','EQ','input','',9,'admin','2025-06-26 13:07:41','',NULL),(32,4,'UserID','id','int unsigned','String','UserID','1','1','0','1',NULL,NULL,NULL,'EQ',NULL,'',1,'admin','2025-06-26 13:07:48','',NULL),(33,4,'Phone','电话号码','varchar(13)','String','Phone','0','0','1','1','1','1','1','EQ','input','',2,'admin','2025-06-26 13:07:48','',NULL),(34,4,'NickName','昵称（长度≤10字，唯一）','varchar(10)','String','NickName','0','0','1','1','1','1','1','LIKE','input','',3,'admin','2025-06-26 13:07:48','',NULL),(35,4,'Password','密码','varchar(100)','String','Password','0','0','1','1','1','1','1','EQ','input','',4,'admin','2025-06-26 13:07:48','',NULL),(36,4,'Gender','性别（0=未设置，1=男，2=女）','tinyint','Long','Gender','0','0','1','1','1','1','1','EQ','input','',5,'admin','2025-06-26 13:07:48','',NULL),(37,4,'Birthday','出生日期','date','Date','Birthday','0','0','1','1','1','1','1','EQ','datetime','',6,'admin','2025-06-26 13:07:48','',NULL),(38,4,'Avatar_url','头像','varchar(255)','String','avatarUrl','0','0','1','1','1','1','1','EQ','input','',7,'admin','2025-06-26 13:07:48','',NULL),(39,4,'RegisterTime','注册时间','datetime','Date','RegisterTime','0','0','1','1','1','1','1','EQ','datetime','',8,'admin','2025-06-26 13:07:48','',NULL),(40,4,'LastLoginTime','最近登录时间','datetime','Date','LastLoginTime','0','0','1','1','1','1','1','EQ','datetime','',9,'admin','2025-06-26 13:07:48','',NULL),(41,4,'Minor','是否成年（0 = 否，1= 是）','tinyint','Long','Minor','0','0','1','1','1','1','1','EQ','input','',10,'admin','2025-06-26 13:07:48','',NULL),(42,4,'Auth','是否认证（0=未认证，1=已认证）','tinyint','Long','Auth','0','0','1','1','1','1','1','EQ','input','',11,'admin','2025-06-26 13:07:48','',NULL),(43,4,'Status','状态（0=生效，1=已注销，2=冻结）','tinyint','Long','Status','0','0','1','1','1','1','1','EQ','radio','',12,'admin','2025-06-26 13:07:48','',NULL),(44,5,'pet_breed_id','宠物品种ID','int(20) unsigned','Long','petBreedId','1','1','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2025-06-30 11:40:46','','2025-06-30 11:41:14'),(45,5,'cn_no','中文排序','varchar(10)','String','cnNo','0','0','1','1','1','1','1','EQ','input','',2,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(46,5,'en_no','英文排序','varchar(10)','String','enNo','0','0','1','1','1','1','1','EQ','input','',3,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(47,5,'pet_class_id','宠物分类id','varchar(20)','String','petClassId','0','0','0','1','1','1','1','EQ','input','',4,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(48,5,'pet_class','宠物分类','varchar(10)','String','petClass','0','0','1','1','1','1','1','EQ','input','',5,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(49,5,'cn_initials','中文首字母','varchar(10)','String','cnInitials','0','0','1','1','1','1','1','EQ','input','',6,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(50,5,'pet_breed','宠物品种','varchar(50)','String','petBreed','0','0','1','1','1','1','1','EQ','input','',7,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(51,5,'en_initials','英文首字母','varchar(50)','String','enInitials','0','0','1','1','1','1','1','EQ','input','',8,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(52,5,'pet_breed_en','宠物品种（英文）','varchar(50)','String','petBreedEn','0','0','1','1','1','1','1','EQ','input','',9,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:14'),(53,5,'status','状态','tinyint(4)','Integer','status','0','0','1','1','1','1','1','EQ','radio','',10,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:15'),(54,5,'last_update_time','最后更新时间','datetime','Date','lastUpdateTime','0','0','1','1','1','1','1','EQ','datetime','',11,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:15'),(55,5,'create_time','创建时间','datetime','Date','createTime','0','0','1','1',NULL,NULL,NULL,'EQ','datetime','',12,'admin','2025-06-30 11:40:47','','2025-06-30 11:41:15'),(56,5,'creator','创建人','varchar(40)','String','creator','0','0','1','1','1','1','1','EQ','input','',13,'admin','2025-06-30 11:40:48','','2025-06-30 11:41:15'),(57,5,'last_updater','最后更新人','varchar(40)','String','lastUpdater','0','0','1','1','1','1','1','EQ','input','',14,'admin','2025-06-30 11:40:48','','2025-06-30 11:41:15'),(58,6,'pet_class_id','宠物分类ID','int(20) unsigned','Long','petClassId','1','1','0','1',NULL,NULL,NULL,'EQ','input','',1,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(59,6,'no','序号','varchar(10)','String','no','0','0','1','1','1','1','1','EQ','input','',2,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(60,6,'pet_class','宠物分类','varchar(40)','String','petClass','0','0','1','1','1','1','1','EQ','input','',3,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(61,6,'pet_class_en','宠物分类（英文）','varchar(40)','String','petClassEn','0','0','1','1','1','1','1','EQ','input','',4,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(62,6,'status','状态','tinyint(4)','Integer','status','0','0','1','1','1','1','1','EQ','radio','pet_status',5,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(63,6,'create_time','创建时间','datetime','Date','createTime','0','0','1','1',NULL,NULL,NULL,'EQ','datetime','',6,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(64,6,'creator','创建人','varchar(40)','String','creator','0','0','1','1','1','1','1','EQ','input','',7,'admin','2025-06-30 11:40:48','','2025-07-03 14:51:53'),(65,6,'last_update_time','最后更新时间','datetime','Date','lastUpdateTime','0','0','1','1','1','1','1','EQ','datetime','',8,'admin','2025-06-30 11:40:49','','2025-07-03 14:51:53'),(66,6,'last_updater','最后更新人','varchar(40)','String','lastUpdater','0','0','1','1','1','1','1','EQ','input','',9,'admin','2025-06-30 11:40:49','','2025-07-03 14:51:53');
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_log`
--

DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_log` (
  `LoginID` int unsigned NOT NULL AUTO_INCREMENT COMMENT '登录日志 ID',
  `UserID` int unsigned NOT NULL COMMENT '用户ID',
  `LoginMethod` tinyint NOT NULL COMMENT '登录方法（0=手机号验证码登录，1=密码登录，2=本机号码一键登录，3=生物识别登录）',
  `LoginTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `LoginIP` varchar(50) NOT NULL COMMENT '登录IP',
  `Location` varchar(100) NOT NULL COMMENT '登录位置',
  `DeviceModel` varchar(100) NOT NULL COMMENT '设备型号',
  `DefaultLocation` varchar(100) DEFAULT NULL COMMENT '常驻地',
  `LoginResult` tinyint NOT NULL COMMENT '登录结果（0=成功，1=失败）',
  `FailCount` tinyint NOT NULL DEFAULT '0' COMMENT '连续登录失败次数',
  `ErrorReason` varchar(200) DEFAULT NULL COMMENT '登录失败的错误原因',
  PRIMARY KEY (`LoginID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='登录日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_log`
--

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;
INSERT INTO `login_log` (`LoginID`, `UserID`, `LoginMethod`, `LoginTime`, `LoginIP`, `Location`, `DeviceModel`, `DefaultLocation`, `LoginResult`, `FailCount`, `ErrorReason`) VALUES (1,0,0,'2025-07-16 12:15:46','unknown','unknown','unknown',NULL,1,1,'手机号未注册'),(2,60,0,'2025-07-16 13:41:48','unknown','unknown','unknown',NULL,0,0,NULL),(3,0,0,'2025-07-16 14:44:22','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(4,8,0,'2025-07-16 14:51:59','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(5,67,0,'2025-07-16 15:16:24','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(6,68,0,'2025-07-16 15:19:23','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(7,69,0,'2025-07-16 15:22:46','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(8,70,0,'2025-07-16 15:25:52','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(9,27,0,'2025-07-16 15:28:12','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(10,1,0,'2025-07-16 15:29:20','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(11,9,0,'2025-07-16 15:33:58','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(12,71,0,'2025-07-16 15:34:51','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(13,72,0,'2025-07-16 15:39:33','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(14,73,0,'2025-07-16 15:46:49','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(15,74,0,'2025-07-16 15:49:16','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(16,75,0,'2025-07-16 15:51:16','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(17,75,0,'2025-07-16 15:56:51','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(18,76,0,'2025-07-16 15:58:44','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(19,76,0,'2025-07-16 16:02:12','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(20,27,0,'2025-07-16 22:03:42','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(21,27,0,'2025-07-17 09:35:48','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(22,0,0,'2025-07-17 09:35:49','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(23,27,1,'2025-07-17 09:36:26','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(24,1,0,'2025-07-17 14:05:25','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(25,27,0,'2025-07-17 14:10:08','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(26,8,1,'2025-07-17 14:23:18','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(27,8,1,'2025-07-17 14:23:47','112.47.113.92','unknown','unknown',NULL,1,2,'密码错误'),(28,8,1,'2025-07-17 14:25:16','112.47.113.92','unknown','unknown',NULL,1,3,'密码错误'),(29,1,1,'2025-07-17 14:25:30','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(30,1,1,'2025-07-17 14:26:14','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(31,0,1,'2025-07-17 14:27:03','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(32,0,1,'2025-07-17 14:27:07','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(33,0,1,'2025-07-17 14:27:19','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(34,0,1,'2025-07-17 14:27:49','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(35,1,1,'2025-07-17 14:31:19','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(36,1,1,'2025-07-17 14:31:59','112.47.113.92','unknown','unknown',NULL,1,2,'密码错误'),(37,1,1,'2025-07-17 14:32:03','112.47.113.92','unknown','unknown',NULL,1,3,'密码错误'),(38,1,1,'2025-07-17 14:33:25','112.47.113.92','unknown','unknown',NULL,1,4,'密码错误'),(39,1,1,'2025-07-17 14:35:22','112.47.113.92','unknown','unknown',NULL,1,5,'账号锁定'),(40,1,1,'2025-07-17 14:37:04','112.47.113.92','unknown','unknown',NULL,1,5,'账号锁定'),(41,8,1,'2025-07-17 14:37:14','112.47.113.92','unknown','unknown',NULL,1,4,'密码错误'),(42,8,1,'2025-07-17 14:37:18','112.47.113.92','unknown','unknown',NULL,1,5,'账号锁定'),(43,77,0,'2025-07-17 14:38:22','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(44,77,1,'2025-07-17 14:39:39','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(45,77,1,'2025-07-17 14:39:42','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(46,1,0,'2025-07-17 15:31:20','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(47,78,0,'2025-07-17 15:53:34','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(48,1,0,'2025-07-17 16:21:44','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(49,0,0,'2025-07-17 16:27:24','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(50,8,0,'2025-07-17 16:27:40','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(51,0,0,'2025-07-17 16:27:41','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(52,1,1,'2025-07-17 16:29:55','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(53,8,0,'2025-07-17 16:46:31','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(54,0,0,'2025-07-17 16:46:52','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(55,0,0,'2025-07-17 16:46:53','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(56,0,0,'2025-07-17 16:47:47','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(57,0,0,'2025-07-17 16:49:09','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(58,0,0,'2025-07-17 16:49:23','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(59,0,0,'2025-07-17 16:51:00','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(60,79,0,'2025-07-17 16:51:38','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(61,79,0,'2025-07-17 16:52:45','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(62,79,0,'2025-07-17 16:56:18','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(63,79,0,'2025-07-17 17:00:17','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(64,79,0,'2025-07-17 17:05:14','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(65,79,0,'2025-07-17 17:07:57','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(66,79,0,'2025-07-17 17:10:31','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(67,1,1,'2025-07-17 17:13:12','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(68,1,1,'2025-07-17 17:13:19','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(69,0,1,'2025-07-17 17:13:26','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(70,79,0,'2025-07-17 17:17:35','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(71,0,0,'2025-07-17 17:17:40','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(72,0,0,'2025-07-17 17:20:03','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(73,0,0,'2025-07-17 17:21:18','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(74,80,0,'2025-07-17 17:22:11','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(75,0,1,'2025-07-17 17:25:47','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(76,0,1,'2025-07-17 17:37:31','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(77,0,1,'2025-07-17 17:42:15','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(78,1,1,'2025-07-17 17:43:06','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(79,0,1,'2025-07-17 17:43:08','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(80,0,1,'2025-07-17 17:44:45','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(81,81,0,'2025-07-17 17:49:16','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(82,81,1,'2025-07-17 17:50:37','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(83,81,1,'2025-07-17 17:50:38','112.47.113.92','unknown','unknown',NULL,1,2,'密码错误'),(84,81,1,'2025-07-17 17:50:42','112.47.113.92','unknown','unknown',NULL,1,3,'密码错误'),(85,81,1,'2025-07-17 17:51:45','112.47.113.92','unknown','unknown',NULL,1,4,'密码错误'),(86,81,1,'2025-07-17 17:52:12','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(87,81,1,'2025-07-17 17:53:56','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(88,81,1,'2025-07-17 17:56:38','112.47.113.92','unknown','unknown',NULL,1,2,'密码错误'),(89,81,1,'2025-07-17 17:59:07','112.47.113.92','unknown','unknown',NULL,1,3,'密码错误'),(90,1,1,'2025-07-17 18:03:12','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(91,30,1,'2025-07-17 18:05:26','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(92,0,1,'2025-07-17 18:05:39','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(93,0,1,'2025-07-17 18:05:45','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(94,82,0,'2025-07-17 18:06:41','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(95,82,1,'2025-07-17 18:06:56','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(96,82,1,'2025-07-17 18:07:11','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(97,82,1,'2025-07-17 18:08:25','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(98,82,1,'2025-07-17 18:08:31','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(99,0,1,'2025-07-17 18:09:54','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(100,0,1,'2025-07-17 18:09:59','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(101,83,0,'2025-07-17 18:10:21','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(102,83,1,'2025-07-17 18:10:47','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(103,83,1,'2025-07-17 18:10:53','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(104,83,1,'2025-07-17 18:13:25','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(105,83,1,'2025-07-17 18:14:23','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(106,83,1,'2025-07-17 18:15:08','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(107,83,1,'2025-07-17 18:16:20','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(108,83,1,'2025-07-17 18:16:49','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(109,0,1,'2025-07-17 18:16:55','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(110,0,1,'2025-07-17 18:17:00','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(111,84,0,'2025-07-17 18:17:20','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(112,84,1,'2025-07-17 18:17:43','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(113,84,1,'2025-07-17 18:19:17','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(114,84,1,'2025-07-17 18:20:39','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(115,84,1,'2025-07-17 18:20:58','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(116,0,1,'2025-07-17 18:22:47','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(117,85,0,'2025-07-17 18:23:14','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(118,85,1,'2025-07-17 18:24:18','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(119,0,1,'2025-07-17 18:24:31','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(120,86,0,'2025-07-17 18:24:53','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(121,86,1,'2025-07-17 18:25:28','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(122,0,0,'2025-07-17 19:39:37','0:0:0:0:0:0:0:1','unknown','unknown',NULL,1,1,'验证码过期'),(123,1,0,'2025-07-17 19:39:59','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(124,87,0,'2025-07-17 19:45:52','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(125,87,0,'2025-07-17 19:47:26','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(126,0,1,'2025-07-17 20:02:14','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(127,0,1,'2025-07-17 20:02:20','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(128,88,0,'2025-07-17 20:03:32','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(129,88,1,'2025-07-17 20:03:47','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(130,87,0,'2025-07-17 20:03:53','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(131,88,1,'2025-07-17 20:06:11','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(132,0,1,'2025-07-17 20:09:02','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(133,0,1,'2025-07-17 20:09:11','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(134,89,0,'2025-07-17 20:09:37','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(135,89,1,'2025-07-17 20:09:45','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(136,89,1,'2025-07-17 20:09:50','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(137,87,0,'2025-07-17 20:11:04','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(138,87,0,'2025-07-17 20:15:44','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(139,88,1,'2025-07-17 20:16:18','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(140,88,1,'2025-07-17 20:17:05','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(141,0,1,'2025-07-17 20:19:15','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(142,0,1,'2025-07-17 20:19:24','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(143,90,0,'2025-07-17 20:21:01','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(144,90,1,'2025-07-17 20:21:09','112.47.113.92','unknown','unknown',NULL,1,1,'密码错误'),(145,90,1,'2025-07-17 20:21:13','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(146,90,1,'2025-07-17 20:21:54','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(147,89,0,'2025-07-17 20:35:51','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(148,89,0,'2025-07-17 20:37:41','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(149,0,0,'2025-07-17 20:38:14','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(150,89,0,'2025-07-17 20:38:44','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(151,0,0,'2025-07-17 20:38:59','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(152,91,0,'2025-07-17 20:40:09','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(153,8,0,'2025-07-17 20:54:48','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(154,8,0,'2025-07-17 21:00:16','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(155,8,0,'2025-07-17 21:02:58','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(156,1,0,'2025-07-17 21:03:30','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(157,8,0,'2025-07-17 21:04:03','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(158,8,0,'2025-07-17 21:07:01','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(159,0,1,'2025-07-17 21:24:26','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(160,92,0,'2025-07-17 21:24:53','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(161,27,0,'2025-07-17 22:14:41','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(162,27,1,'2025-07-17 22:14:57','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(163,0,0,'2025-07-17 22:15:15','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(164,93,0,'2025-07-17 22:21:23','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(165,93,0,'2025-07-17 22:23:40','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(166,0,0,'2025-07-17 22:24:06','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(167,94,0,'2025-07-17 22:25:25','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(168,94,0,'2025-07-17 22:26:06','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(169,93,1,'2025-07-17 22:26:32','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(170,27,0,'2025-07-17 22:27:00','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(171,93,0,'2025-07-17 22:27:45','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(172,8,0,'2025-07-17 22:41:52','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(173,0,0,'2025-07-17 22:42:04','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(174,0,0,'2025-07-17 22:42:05','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(175,0,0,'2025-07-17 22:42:06','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(176,1,1,'2025-07-17 22:42:19','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(177,1,1,'2025-07-17 22:42:22','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(178,1,1,'2025-07-17 22:42:23','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(179,1,1,'2025-07-17 22:42:23','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(180,1,1,'2025-07-17 22:42:24','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(181,1,1,'2025-07-17 22:42:24','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(182,1,1,'2025-07-17 22:42:32','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(183,8,0,'2025-07-17 22:42:58','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(184,0,0,'2025-07-17 22:43:18','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(185,0,0,'2025-07-17 22:43:20','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(186,0,0,'2025-07-17 22:43:23','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(187,0,0,'2025-07-17 22:43:24','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(188,0,1,'2025-07-17 22:43:39','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(189,0,1,'2025-07-17 22:43:54','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(190,0,1,'2025-07-17 22:43:54','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(191,0,1,'2025-07-17 22:43:54','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(192,0,1,'2025-07-17 22:43:55','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(193,0,1,'2025-07-17 22:43:55','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(194,0,1,'2025-07-17 22:43:56','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(195,0,1,'2025-07-17 22:43:56','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(196,0,1,'2025-07-17 22:43:57','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(197,0,1,'2025-07-17 22:43:57','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(198,8,0,'2025-07-17 22:52:24','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(199,0,0,'2025-07-17 22:52:58','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(200,95,0,'2025-07-17 22:53:46','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(201,0,0,'2025-07-17 22:55:27','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(202,96,0,'2025-07-17 22:56:55','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(203,0,0,'2025-07-17 22:57:16','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(204,97,0,'2025-07-17 22:58:00','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(205,0,0,'2025-07-17 23:02:46','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(206,98,0,'2025-07-17 23:03:11','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(207,0,0,'2025-07-17 23:07:27','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(208,99,0,'2025-07-17 23:08:05','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(209,0,0,'2025-07-17 23:20:31','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(210,100,0,'2025-07-17 23:21:06','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(211,100,0,'2025-07-17 23:23:16','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(212,100,0,'2025-07-17 23:25:18','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(213,100,0,'2025-07-17 23:26:28','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(214,100,0,'2025-07-17 23:28:01','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(215,100,0,'2025-07-17 23:29:46','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(216,100,0,'2025-07-17 23:31:39','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(217,100,0,'2025-07-17 23:43:39','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(218,8,0,'2025-07-18 12:58:45','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(219,0,0,'2025-07-18 12:59:07','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(220,0,0,'2025-07-18 13:02:03','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(221,0,0,'2025-07-18 13:12:33','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(222,101,0,'2025-07-18 13:13:33','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(223,0,0,'2025-07-18 13:17:07','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(224,102,0,'2025-07-18 13:17:33','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(225,102,0,'2025-07-18 13:18:30','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(226,30,0,'2025-07-18 13:18:48','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(227,102,0,'2025-07-18 13:20:10','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(228,102,0,'2025-07-18 13:22:39','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(229,102,0,'2025-07-18 13:38:25','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(230,30,0,'2025-07-18 13:39:22','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(231,102,0,'2025-07-18 13:50:11','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(232,102,0,'2025-07-18 13:51:13','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(233,102,0,'2025-07-18 14:12:07','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(234,102,0,'2025-07-18 14:23:01','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(235,102,0,'2025-07-18 14:24:57','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(236,102,0,'2025-07-18 14:28:05','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(237,102,0,'2025-07-18 14:48:46','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(238,102,0,'2025-07-18 14:51:38','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(239,102,0,'2025-07-18 14:52:48','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(240,102,0,'2025-07-18 15:04:16','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(241,101,0,'2025-07-18 15:04:57','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(242,0,0,'2025-07-18 15:05:10','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(243,0,0,'2025-07-18 15:07:06','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(244,102,0,'2025-07-18 19:43:56','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(245,0,0,'2025-07-18 19:44:26','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(246,0,0,'2025-07-18 19:46:22','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(247,103,0,'2025-07-18 19:47:30','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(248,0,0,'2025-07-18 19:51:03','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(249,0,0,'2025-07-18 19:51:03','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(250,104,0,'2025-07-18 19:51:44','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(251,1,0,'2025-07-18 20:16:36','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(252,93,0,'2025-07-18 20:17:52','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(253,0,0,'2025-07-18 20:19:06','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(254,93,0,'2025-07-18 20:19:09','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(255,1,1,'2025-07-18 20:21:46','0:0:0:0:0:0:0:1','unknown','unknown',NULL,0,0,NULL),(256,1,0,'2025-07-18 20:22:28','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(257,87,0,'2025-07-18 20:22:39','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(258,0,0,'2025-07-18 20:23:09','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(259,102,0,'2025-07-18 20:24:19','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(260,105,0,'2025-07-18 20:24:41','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(261,0,0,'2025-07-18 20:30:32','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(262,106,0,'2025-07-18 20:30:58','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(263,106,0,'2025-07-18 20:31:44','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(264,0,0,'2025-07-18 20:32:01','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(265,107,0,'2025-07-18 20:32:37','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(266,0,0,'2025-07-18 20:35:13','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(267,108,0,'2025-07-18 20:35:54','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(268,103,0,'2025-07-18 20:36:10','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(269,0,0,'2025-07-18 20:39:44','120.32.198.65','unknown','unknown',NULL,1,1,'手机号未注册'),(270,101,0,'2025-07-18 20:42:01','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(271,0,0,'2025-07-18 20:46:42','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(272,0,0,'2025-07-18 20:47:25','120.32.198.65','unknown','unknown',NULL,1,1,'手机号未注册'),(273,109,0,'2025-07-18 20:47:51','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(274,110,0,'2025-07-18 20:50:18','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(275,102,0,'2025-07-18 21:06:00','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(276,0,0,'2025-07-18 21:07:42','120.32.198.65','unknown','unknown',NULL,1,1,'手机号未注册'),(277,111,0,'2025-07-18 21:08:15','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(278,0,0,'2025-07-18 21:09:58','120.32.198.65','unknown','unknown',NULL,1,1,'手机号未注册'),(279,112,0,'2025-07-18 21:10:26','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(280,112,0,'2025-07-18 21:11:03','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(281,0,0,'2025-07-18 21:15:00','120.32.198.65','unknown','unknown',NULL,1,1,'手机号未注册'),(282,113,0,'2025-07-18 21:15:33','120.32.198.65','unknown','unknown',NULL,0,0,NULL),(283,102,0,'2025-07-18 21:23:25','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(284,102,0,'2025-07-18 21:27:53','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(285,102,0,'2025-07-18 21:29:04','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(286,102,0,'2025-07-18 21:33:59','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(287,102,0,'2025-07-18 21:38:32','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(288,102,0,'2025-07-18 21:56:04','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(289,27,0,'2025-07-18 22:15:42','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(290,27,1,'2025-07-18 22:16:00','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(291,108,0,'2025-07-18 22:16:37','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(292,0,0,'2025-07-18 22:17:17','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(293,108,0,'2025-07-18 22:18:16','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(294,0,0,'2025-07-18 22:20:25','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(295,114,0,'2025-07-18 22:20:57','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(296,114,1,'2025-07-18 22:21:48','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(297,114,0,'2025-07-18 22:22:00','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(298,0,0,'2025-07-18 22:22:11','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(299,115,0,'2025-07-18 22:22:56','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(300,115,0,'2025-07-18 22:23:25','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(301,0,0,'2025-07-18 22:23:58','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(302,116,0,'2025-07-18 22:24:27','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(303,102,0,'2025-07-18 22:25:31','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(304,102,0,'2025-07-18 22:32:17','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(305,0,0,'2025-07-18 22:33:53','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(306,117,0,'2025-07-18 22:34:16','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(307,102,0,'2025-07-18 22:36:03','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(308,0,0,'2025-07-18 22:36:16','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(309,102,0,'2025-07-18 22:38:33','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(310,0,0,'2025-07-18 22:38:55','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(311,118,0,'2025-07-18 22:39:29','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(312,102,0,'2025-07-18 22:39:57','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(313,118,0,'2025-07-18 22:41:02','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(314,0,0,'2025-07-18 22:41:19','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(315,118,0,'2025-07-18 22:42:05','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(316,0,0,'2025-07-18 22:42:43','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(317,0,0,'2025-07-18 22:42:46','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(318,0,0,'2025-07-18 22:42:46','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(319,0,0,'2025-07-18 22:42:47','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(320,0,0,'2025-07-18 22:42:47','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(321,0,0,'2025-07-18 22:42:47','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(322,0,0,'2025-07-18 22:42:47','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(323,0,0,'2025-07-18 22:42:47','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(324,0,0,'2025-07-18 22:43:07','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(325,0,0,'2025-07-18 22:43:42','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(326,0,0,'2025-07-18 22:43:45','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(327,102,0,'2025-07-18 22:43:49','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(328,119,0,'2025-07-18 22:44:37','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(329,0,0,'2025-07-18 22:45:21','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(330,119,0,'2025-07-18 22:45:28','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(331,102,0,'2025-07-18 22:45:59','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(332,0,0,'2025-07-18 22:46:51','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(333,78,0,'2025-07-18 22:47:03','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(334,0,0,'2025-07-18 22:47:08','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(335,0,1,'2025-07-18 22:53:13','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(336,1,1,'2025-07-18 22:54:43','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(337,0,1,'2025-07-19 10:24:50','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(338,0,1,'2025-07-19 14:02:30','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(339,0,1,'2025-07-19 14:06:01','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(340,0,1,'2025-07-19 14:06:35','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(341,0,1,'2025-07-19 14:12:44','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(342,0,1,'2025-07-19 14:18:19','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(343,0,1,'2025-07-19 14:18:21','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(344,0,1,'2025-07-19 14:19:05','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(345,0,1,'2025-07-19 14:20:34','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(346,0,1,'2025-07-19 14:21:07','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(347,0,1,'2025-07-19 14:21:14','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(348,0,1,'2025-07-19 14:21:41','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(349,0,1,'2025-07-19 14:21:44','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(350,0,1,'2025-07-19 14:21:55','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(351,0,1,'2025-07-19 14:21:59','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(352,0,1,'2025-07-19 14:22:04','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(353,0,1,'2025-07-19 14:22:47','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(354,0,1,'2025-07-19 14:24:41','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(355,0,1,'2025-07-19 14:24:56','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(356,0,1,'2025-07-19 14:24:58','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(357,0,1,'2025-07-19 14:25:00','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(358,0,1,'2025-07-19 14:25:03','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(359,0,1,'2025-07-19 14:25:06','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(360,0,1,'2025-07-19 14:28:50','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(361,0,1,'2025-07-19 14:28:53','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(362,0,1,'2025-07-19 14:29:13','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(363,0,1,'2025-07-19 14:35:08','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(364,0,1,'2025-07-19 14:35:10','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(365,27,0,'2025-07-19 14:48:31','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(366,93,0,'2025-07-19 14:48:44','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(367,0,1,'2025-07-19 14:58:08','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(368,120,0,'2025-07-19 14:58:44','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(369,0,1,'2025-07-19 14:59:22','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(370,121,0,'2025-07-19 14:59:47','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(371,0,1,'2025-07-19 15:14:59','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(372,122,0,'2025-07-19 15:15:26','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(373,0,1,'2025-07-19 15:20:46','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(374,0,0,'2025-07-19 15:31:25','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(375,0,0,'2025-07-19 15:36:49','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(376,123,0,'2025-07-19 15:42:19','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(377,0,0,'2025-07-19 15:42:38','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(378,0,0,'2025-07-19 15:42:41','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(379,0,0,'2025-07-19 15:42:44','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(380,0,0,'2025-07-19 15:43:20','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(381,124,0,'2025-07-19 15:45:13','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(382,0,0,'2025-07-19 15:52:54','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(383,125,0,'2025-07-19 15:53:32','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(384,102,0,'2025-07-19 16:00:49','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(385,97,0,'2025-07-19 17:04:35','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(386,0,0,'2025-07-19 17:05:03','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(387,0,0,'2025-07-19 19:03:36','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(388,126,0,'2025-07-19 19:04:09','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(389,0,1,'2025-07-19 19:05:44','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(390,0,1,'2025-07-19 19:05:48','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(391,0,0,'2025-07-19 19:16:05','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(392,127,0,'2025-07-19 19:16:41','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(393,0,1,'2025-07-19 19:18:52','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(394,27,1,'2025-07-19 19:36:07','112.48.6.49','unknown','unknown',NULL,1,1,'密码错误'),(395,0,1,'2025-07-19 19:36:21','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(396,128,0,'2025-07-19 19:36:50','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(397,128,0,'2025-07-19 19:40:02','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(398,118,0,'2025-07-19 19:41:07','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(399,118,1,'2025-07-19 19:41:47','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(400,27,0,'2025-07-20 11:22:09','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(401,0,0,'2025-07-20 11:29:56','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(402,129,0,'2025-07-20 11:35:05','112.47.80.166','unknown','unknown',NULL,0,0,NULL),(403,0,0,'2025-07-20 11:38:37','112.47.80.166','unknown','unknown',NULL,1,1,'验证码过期'),(404,129,1,'2025-07-20 11:39:04','112.47.80.166','unknown','unknown',NULL,0,0,NULL),(405,0,1,'2025-07-20 11:39:11','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(406,0,1,'2025-07-20 11:39:29','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(407,0,0,'2025-07-20 11:39:43','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(408,0,0,'2025-07-20 11:44:18','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(409,130,0,'2025-07-20 11:44:51','112.47.80.166','unknown','unknown',NULL,0,0,NULL),(410,0,0,'2025-07-20 12:17:47','112.48.6.49','unknown','unknown',NULL,1,1,'验证码错误'),(411,1,1,'2025-07-20 12:17:55','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(412,27,0,'2025-07-20 13:26:31','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(413,78,0,'2025-07-20 14:23:23','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(414,78,0,'2025-07-20 14:24:33','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(415,0,0,'2025-07-20 14:44:55','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(416,102,0,'2025-07-20 15:11:09','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(417,27,0,'2025-07-20 15:17:14','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(418,0,0,'2025-07-20 15:17:40','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(419,0,0,'2025-07-20 15:17:47','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(420,0,0,'2025-07-20 15:18:00','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(421,131,0,'2025-07-20 15:18:10','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(422,131,0,'2025-07-20 15:19:25','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(423,0,0,'2025-07-20 15:47:27','27.38.148.125','unknown','unknown',NULL,1,1,'手机号未注册'),(424,0,0,'2025-07-20 15:47:38','27.38.148.125','unknown','unknown',NULL,1,1,'验证码过期'),(425,0,1,'2025-07-20 15:48:01','27.38.148.125','unknown','unknown',NULL,1,1,'手机号未注册'),(426,27,0,'2025-07-20 15:49:09','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(427,0,1,'2025-07-20 15:57:27','27.38.148.125','unknown','unknown',NULL,1,1,'手机号未注册'),(428,132,0,'2025-07-20 15:58:02','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(429,132,0,'2025-07-20 15:58:48','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(430,0,0,'2025-07-20 15:59:00','27.38.148.125','unknown','unknown',NULL,1,1,'验证码过期'),(431,132,1,'2025-07-20 15:59:14','27.38.148.125','unknown','unknown',NULL,1,1,'密码错误'),(432,132,1,'2025-07-20 15:59:19','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(433,132,1,'2025-07-20 16:00:08','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(434,132,0,'2025-07-20 16:00:22','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(435,0,0,'2025-07-20 16:00:47','27.38.148.125','unknown','unknown',NULL,1,1,'验证码过期'),(436,0,0,'2025-07-20 16:00:47','27.38.148.125','unknown','unknown',NULL,1,1,'手机号未注册'),(437,0,0,'2025-07-20 16:00:48','27.38.148.125','unknown','unknown',NULL,1,1,'验证码过期'),(438,133,0,'2025-07-20 16:03:11','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(439,0,0,'2025-07-20 16:35:28','223.104.79.90','unknown','unknown',NULL,1,1,'手机号未注册'),(440,134,0,'2025-07-20 16:41:14','223.104.79.90','unknown','unknown',NULL,0,0,NULL),(441,134,1,'2025-07-20 16:49:15','223.104.79.90','unknown','unknown',NULL,1,1,'密码错误'),(442,134,1,'2025-07-20 16:49:41','223.104.79.90','unknown','unknown',NULL,0,0,NULL),(443,134,1,'2025-07-20 17:01:11','223.104.79.90','unknown','unknown',NULL,0,0,NULL),(444,134,1,'2025-07-20 17:01:16','223.104.79.90','unknown','unknown',NULL,0,0,NULL),(445,134,1,'2025-07-20 17:01:20','223.104.79.90','unknown','unknown',NULL,0,0,NULL),(446,0,0,'2025-07-20 17:11:55','27.38.148.125','unknown','unknown',NULL,1,1,'手机号未注册'),(447,135,0,'2025-07-20 17:12:46','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(448,135,0,'2025-07-20 17:28:46','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(449,134,1,'2025-07-20 17:39:36','223.104.79.90','unknown','unknown',NULL,1,1,'密码错误'),(450,134,1,'2025-07-20 17:39:48','223.104.79.90','unknown','unknown',NULL,0,0,NULL),(451,129,1,'2025-07-20 20:37:41','112.47.80.166','unknown','unknown',NULL,1,1,'密码错误'),(452,129,1,'2025-07-20 20:37:59','112.47.80.166','unknown','unknown',NULL,1,2,'密码错误'),(453,0,1,'2025-07-20 20:38:10','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(454,136,0,'2025-07-20 21:07:54','112.47.80.166','unknown','unknown',NULL,0,0,NULL),(455,136,1,'2025-07-20 21:11:56','112.47.80.166','unknown','unknown',NULL,1,1,'密码错误'),(456,136,1,'2025-07-20 21:12:10','112.47.80.166','unknown','unknown',NULL,0,0,NULL),(457,0,0,'2025-07-20 21:16:00','112.47.80.166','unknown','unknown',NULL,1,1,'手机号未注册'),(458,137,0,'2025-07-20 21:16:28','112.47.80.166','unknown','unknown',NULL,0,0,NULL),(459,0,0,'2025-07-20 22:54:07','39.144.238.29','unknown','unknown',NULL,1,1,'手机号未注册'),(460,138,0,'2025-07-20 22:55:59','39.144.238.29','unknown','unknown',NULL,0,0,NULL),(461,0,1,'2025-07-20 23:20:51','39.144.238.29','unknown','unknown',NULL,1,1,'手机号未注册'),(462,27,0,'2025-07-20 23:44:27','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(463,118,0,'2025-07-20 23:44:39','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(464,106,0,'2025-07-20 23:45:02','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(465,0,0,'2025-07-21 15:05:49','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(466,139,0,'2025-07-21 15:06:56','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(467,0,0,'2025-07-21 15:07:45','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(468,140,0,'2025-07-21 15:08:56','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(469,0,0,'2025-07-21 15:09:58','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(470,0,0,'2025-07-21 15:47:38','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(471,0,0,'2025-07-21 15:53:00','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(472,141,0,'2025-07-21 15:53:26','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(473,141,1,'2025-07-21 15:53:42','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(474,0,0,'2025-07-21 15:58:28','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(475,142,0,'2025-07-21 15:58:51','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(476,141,1,'2025-07-21 15:59:06','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(477,141,1,'2025-07-21 15:59:12','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(478,0,1,'2025-07-21 15:59:37','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(479,0,1,'2025-07-21 15:59:46','112.47.113.92','unknown','unknown',NULL,1,1,'手机号未注册'),(480,0,0,'2025-07-21 20:11:59','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(481,143,0,'2025-07-21 20:12:25','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(482,1,1,'2025-07-21 20:32:39','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(483,0,0,'2025-07-21 22:11:24','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(484,144,0,'2025-07-21 22:12:38','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(485,0,0,'2025-07-21 22:27:20','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(486,78,0,'2025-07-21 22:55:56','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(487,78,0,'2025-07-21 22:57:10','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(488,78,0,'2025-07-21 23:02:01','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(489,0,0,'2025-07-21 23:03:24','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(490,97,0,'2025-07-22 15:03:30','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(491,0,0,'2025-07-22 15:04:27','112.47.113.92','unknown','unknown',NULL,1,1,'验证码过期'),(492,97,0,'2025-07-22 15:04:31','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(493,97,0,'2025-07-22 15:10:39','112.47.113.92','unknown','unknown',NULL,0,0,NULL),(494,27,0,'2025-07-22 15:16:54','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(495,27,0,'2025-07-22 16:13:18','112.48.6.49','unknown','unknown',NULL,0,0,NULL),(496,0,0,'2025-07-22 16:16:50','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(497,0,0,'2025-07-22 16:20:27','112.48.6.49','unknown','unknown',NULL,1,1,'验证码过期'),(498,0,0,'2025-07-22 16:20:30','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(499,134,1,'2025-07-22 20:48:21','120.235.127.79','unknown','unknown',NULL,0,0,NULL),(500,134,1,'2025-07-22 20:48:22','120.235.127.79','unknown','unknown',NULL,0,0,NULL),(501,134,1,'2025-07-22 20:48:29','120.235.127.79','unknown','unknown',NULL,0,0,NULL),(502,0,1,'2025-07-22 20:50:44','120.235.127.79','unknown','unknown',NULL,1,1,'手机号未注册'),(503,145,0,'2025-07-22 20:52:37','120.235.127.79','unknown','unknown',NULL,0,0,NULL),(504,132,0,'2025-07-22 21:54:00','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(505,135,1,'2025-07-22 21:54:29','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(506,0,0,'2025-07-22 22:25:16','14.154.0.247','unknown','unknown',NULL,1,1,'手机号未注册'),(507,146,0,'2025-07-22 22:26:44','14.154.0.247','unknown','unknown',NULL,0,0,NULL),(508,0,0,'2025-07-22 22:30:51','27.38.148.125','unknown','unknown',NULL,1,1,'手机号未注册'),(509,147,0,'2025-07-22 22:32:32','27.38.148.125','unknown','unknown',NULL,0,0,NULL),(510,0,0,'2025-07-23 15:43:32','112.48.6.49','unknown','unknown',NULL,1,1,'手机号未注册'),(511,148,0,'2025-07-23 15:44:10','112.48.6.49','unknown','unknown',NULL,0,0,NULL);
/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet_breed`
--

DROP TABLE IF EXISTS `pet_breed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet_breed` (
  `pet_breed_id` varchar(20) NOT NULL COMMENT '宠物品种ID',
  `cn_no` varchar(10) NOT NULL COMMENT '中文排序',
  `en_no` varchar(10) NOT NULL COMMENT '英文排序',
  `pet_class_id` varchar(20) DEFAULT NULL COMMENT '宠物分类id',
  `pet_class` varchar(10) NOT NULL COMMENT '宠物分类',
  `cn_initials` varchar(10) NOT NULL COMMENT '中文首字母',
  `pet_breed` varchar(50) NOT NULL COMMENT '宠物品种',
  `en_initials` varchar(50) NOT NULL COMMENT '英文首字母',
  `pet_breed_en` varchar(50) NOT NULL COMMENT '宠物品种（英文）',
  `status` tinyint NOT NULL COMMENT '状态',
  `last_update_time` datetime NOT NULL COMMENT '最后更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `last_updater` varchar(40) NOT NULL COMMENT '最后更新人',
  PRIMARY KEY (`pet_breed_id`) USING BTREE,
  UNIQUE KEY `pet_breed` (`pet_breed`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宠物品种';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet_breed`
--

LOCK TABLES `pet_breed` WRITE;
/*!40000 ALTER TABLE `pet_breed` DISABLE KEYS */;
INSERT INTO `pet_breed` (`pet_breed_id`, `cn_no`, `en_no`, `pet_class_id`, `pet_class`, `cn_initials`, `pet_breed`, `en_initials`, `pet_breed_en`, `status`, `last_update_time`, `create_time`, `creator`, `last_updater`) VALUES ('BIRD_BREED_001','1','1','2','猫','h','黑金刚','h','heijg',0,'2025-07-21 21:25:49','2025-06-27 19:22:59','admin','admin'),('BIRD_BREED_002','1.3','1.3','1','鸟','N','鹦鹉','P','Parrot',0,'2025-06-28 13:37:31','2025-06-27 17:31:44','admin','admin'),('BIRD_BREED_003','3.2','3.2','1','鸟','B','八哥','M','Myna',0,'2025-07-03 23:02:33','2025-07-03 23:02:33','admin','admin'),('CAT_BREED_002','1.2.1','1.2.1','2','猫','A','美短','A','American Shorthair',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_003','1.2.3','1.2.3','2','猫','B','布偶猫','R','Ragdoll',0,'2025-07-20 17:21:00','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_004','1.2.4','1.2.4','2','猫','D','加菲猫','G','Garfield',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_005','1.2.6','1.2.6','2','猫','F','波斯猫','P','Persian',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_006','1.2.7','1.2.7','2','猫','G','橘猫','O','Orange Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_007','1.2.8','1.2.8','2','猫','H','黑猫','B','Black Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_008','1.2.9','1.2.9','2','猫','I','异国短毛猫','F','Exotic Shorthair',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_009','1.2.10','1.2.10','2','猫','J','日本猫','J','Japanese Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_010','1.2.11','1.2.11','2','猫','K','俄罗斯蓝猫','R','Russian Blue',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_011','1.2.12','1.2.12','2','猫','L','狸花猫','L','Chinese LiHua',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_012','1.2.13','1.2.13','2','猫','M','孟加拉猫','B','Bengal',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_013','1.2.14','1.2.14','2','猫','N','奴来猫','N','Norwegian Forest Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_014','1.2.15','1.2.15','2','猫','O','欧亚红猫','R','European Red',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_015','1.2.16','1.2.16','2','猫','P','蒲公英猫','D','Dandelion Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_016','1.2.17','1.2.17','2','猫','Q','巧克力猫','C','Chocolate Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_017','1.2.18','1.2.18','2','猫','R','蓝猫','B','Blue Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_018','1.2.19','1.2.19','2','猫','S','暹罗猫','S','Siamese',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_019','1.2.20','1.2.20','2','猫','T','土猫','T','Local Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_020','1.2.21','1.2.21','2','猫','U','金渐层','G','Golden Shaded',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_021','1.2.22','1.2.22','2','猫','V','梵猫','V','Vampire Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_022','1.2.23','1.2.23','2','猫','W','缅因猫','M','Maine Coon',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_023','1.2.24','1.2.24','2','猫','X','喜马拉雅猫','H','Himalayan',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_024','1.2.25','1.2.25','2','猫','Y','约瑟夫猫','J','Joseph Cat',0,'2025-07-01 13:00:46','2025-07-01 13:00:46','admin','admin'),('CAT_BREED_025','1.2','1.2','2','猫','M','英短','B','British Shorthair',0,'2025-06-28 12:40:50','2025-06-27 17:31:44','admin','admin'),('CAT_BREED_026','1.2.26','1.2.26','2','猫','X','希腊猫','S','Santorini Cat',0,'2025-07-03 23:05:45','2025-07-03 23:05:45','admin','admin'),('DOG_BREED_001','1.1','1.1','3','狗','Q','金毛','J','Golden Retriever',0,'2025-06-28 13:14:50','2025-06-27 17:31:44','admin','admin'),('DOG_BREED_002','1.1.1','1.1.1','3','狗','B','比格犬','B','Beagle',0,'2025-07-21 21:26:40','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_003','1.1.2','1.1.2','3','狗','B','边牧','B','Border Collie',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_004','1.1.3','1.1.3','3','狗','C','柴犬','C','Shiba Inu',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_005','1.1.4','1.1.4','3','狗','D','德牧','G','German Shepherd',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_006','1.1.5','1.1.5','3','狗','E','二哈','E','Husky',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_007','1.1.6','1.1.6','3','狗','F','法斗','F','French Bulldog',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_008','1.1.8','1.1.8','3','狗','H','哈士奇','H','Siberian Husky',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_009','1.1.9','1.1.9','3','狗','I','柯基','C','Corgi',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_010','1.1.10','1.1.10','3','狗','J','京巴','P','Pekingese',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_011','1.1.11','1.1.11','3','狗','K','开士','K','Kaiser',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_012','1.1.12','1.1.12','3','狗','L','拉布拉多','L','Labrador',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_013','1.1.13','1.1.13','3','狗','M','马犬','M','Malinois',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_014','1.1.14','1.1.14','3','狗','N','牛头梗','B','Bull Terrier',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_015','1.1.15','1.1.15','3','狗','O','八哥犬','D','Boxer',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_016','1.1.16','1.1.16','3','狗','P','派','P','Pai',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_017','1.1.17','1.1.17','3','狗','Q','秋田犬','A','Akita',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_018','1.1.18','1.1.18','3','狗','R','罗威纳','R','Rottweiler',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_019','1.1.19','1.1.19','3','狗','S','萨摩耶','S','Samoyed',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_020','1.1.20','1.1.20','3','狗','T','泰迪','T','Teddy',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_021','1.1.21','1.1.21','3','狗','U','斗牛犬','B','Bulldog',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_022','1.1.22','1.1.22','3','狗','V','西施犬','S','Shih Tzu',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_023','1.1.23','1.1.23','3','狗','W','汪星人','W','WangXingRen',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_024','1.1.24','1.1.24','3','狗','X','喜乐蒂','S','Shetland Sheepdog',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin'),('DOG_BREED_025','1.1.25','1.1.25','3','狗','Y','约克夏','Y','Yorkshire Terrier',0,'2025-07-01 12:59:05','2025-07-01 12:59:05','admin','admin');
/*!40000 ALTER TABLE `pet_breed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet_classification`
--

DROP TABLE IF EXISTS `pet_classification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet_classification` (
  `pet_class_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '宠物分类ID',
  `no` varchar(10) NOT NULL COMMENT '序号',
  `pet_class` varchar(40) NOT NULL COMMENT '宠物分类',
  `pet_class_en` varchar(40) NOT NULL COMMENT '宠物分类（英文）',
  `status` tinyint NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(40) NOT NULL COMMENT '创建人',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `last_updater` varchar(40) NOT NULL COMMENT '最后更新人',
  PRIMARY KEY (`pet_class_id`),
  UNIQUE KEY `pet_class` (`pet_class`) USING BTREE,
  UNIQUE KEY `pet_class_en` (`pet_class_en`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宠物分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet_classification`
--

LOCK TABLES `pet_classification` WRITE;
/*!40000 ALTER TABLE `pet_classification` DISABLE KEYS */;
INSERT INTO `pet_classification` (`pet_class_id`, `no`, `pet_class`, `pet_class_en`, `status`, `create_time`, `creator`, `last_update_time`, `last_updater`) VALUES (1,'3','鸟','Bird',1,'2025-06-27 14:05:31','admin','2025-07-08 17:43:10','admin'),(2,'1','猫','Cat',0,'2025-06-27 14:05:31','admin','2025-07-21 22:05:50','admin'),(3,'1.2','狗','Dog',0,'2025-06-27 14:05:31','admin','2025-07-08 17:43:10','admin'),(7,'1','蛇','Snake',1,'2025-06-30 14:29:38','admin','2025-07-03 23:38:42','admin');
/*!40000 ALTER TABLE `pet_classification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet_info`
--

DROP TABLE IF EXISTS `pet_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet_info` (
  `pet_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '宠物 ID',
  `user_id` int unsigned NOT NULL COMMENT '用户 ID',
  `pet_nick_name` varchar(10) NOT NULL COMMENT '宠物昵称（唯一）',
  `pet_class` varchar(10) NOT NULL COMMENT '宠物分类',
  `pet_breed` varchar(50) NOT NULL COMMENT '宠物品种',
  `pet_gender` tinyint NOT NULL DEFAULT '0' COMMENT '宠物性别(0=未设置，1=公，2=母)',
  `sterilized` tinyint NOT NULL DEFAULT '0' COMMENT '是否绝育 (0=否，1=是)',
  `pet_birthday` date NOT NULL COMMENT '宠物生日',
  `adoption_date` date NOT NULL COMMENT '领养/购买日期',
  `pet_avatar_url` varchar(255) NOT NULL COMMENT '宠物头像',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 (0=生效，1=已死亡，2=已注销)',
  PRIMARY KEY (`pet_id`) USING BTREE,
  UNIQUE KEY `UK_User_PetNickName` (`user_id`,`pet_nick_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='宠物信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet_info`
--

LOCK TABLES `pet_info` WRITE;
/*!40000 ALTER TABLE `pet_info` DISABLE KEYS */;
INSERT INTO `pet_info` (`pet_id`, `user_id`, `pet_nick_name`, `pet_class`, `pet_breed`, `pet_gender`, `sterilized`, `pet_birthday`, `adoption_date`, `pet_avatar_url`, `status`) VALUES (7,1,'美短 1','猫','美短',2,1,'2024-01-15','2024-02-15','https://via.placeholder.com/150',0),(8,1,'英短 1','猫','英短',1,1,'2024-01-20','2024-02-20','https://via.placeholder.com/150',0),(9,1,'布偶猫 1','猫','布偶猫',2,0,'2024-02-25','2024-03-25','https://via.placeholder.com/150',0),(10,1,'波斯猫 1','猫','波斯猫',1,0,'2024-03-10','2024-04-10','https://via.placeholder.com/150',0),(11,1,'孟加拉猫 1','猫','孟加拉猫',2,1,'2024-04-05','2024-05-05','https://via.placeholder.com/150',0),(12,1,'黑猫 1','猫','黑猫',1,1,'2024-05-15','2024-06-15','https://via.placeholder.com/150',0),(13,1,'美短 11','猫','美短',2,1,'2024-01-15','2024-02-15','https://via.placeholder.com/150',0),(14,1,'英短 11','猫','英短',1,1,'2024-01-20','2024-02-20','https://via.placeholder.com/150',0),(15,1,'布偶猫 11','猫','布偶猫',2,0,'2024-02-25','2024-03-25','https://via.placeholder.com/150',0),(16,1,'波斯猫 11','猫','波斯猫',1,0,'2024-03-10','2024-04-10','https://via.placeholder.com/150',0),(17,1,'孟加拉猫 11','猫','孟加拉猫',2,1,'2024-04-05','2024-05-05','https://via.placeholder.com/150',0),(18,1,'黑猫 11','猫','黑猫',1,1,'2024-05-15','2024-06-15','https://via.placeholder.com/150',0),(19,1,'加菲猫 11','猫','加菲猫',1,0,'2024-06-20','2024-07-20','https://via.placeholder.com/150',0),(20,1,'金毛宝1','狗','金毛',1,0,'2024-01-10','2024-02-10','https://via.placeholder.com/150',0),(21,1,'金毛宝2','狗','金毛',2,1,'2023-12-05','2024-01-05','https://via.placeholder.com/150',0),(22,1,'比格小1','狗','比格犬',1,0,'2024-02-15','2024-03-15','https://via.placeholder.com/150',0),(23,1,'比格小2','狗','比格犬',2,1,'2023-11-20','2024-02-20','https://via.placeholder.com/150',0),(24,1,'边牧聪1','狗','边牧',1,0,'2024-03-20','2024-04-20','https://via.placeholder.com/150',0),(25,1,'边牧聪2','狗','边牧',2,1,'2023-12-25','2024-01-25','https://via.placeholder.com/150',0),(26,1,'柴犬憨1','狗','柴犬',1,0,'2024-04-05','2024-05-05','https://via.placeholder.com/150',0),(27,1,'柴犬憨2','狗','柴犬',2,1,'2023-11-10','2024-02-10','https://via.placeholder.com/150',0),(28,1,'德牧威1','狗','德牧',1,0,'2024-05-10','2024-06-10','https://via.placeholder.com/150',0),(29,1,'德牧威2','狗','德牧',2,1,'2023-10-15','2024-01-15','https://via.placeholder.com/150',0),(30,1,'二哈皮1','狗','二哈',1,0,'2024-06-15','2024-07-15','https://via.placeholder.com/150',0),(31,1,'二哈皮2','狗','二哈',2,1,'2023-09-20','2024-02-20','https://via.placeholder.com/150',0),(32,1,'法斗萌1','狗','法斗',1,0,'2024-07-20','2024-08-20','https://via.placeholder.com/150',0),(33,1,'法斗萌2','狗','法斗',2,1,'2023-08-25','2024-03-25','https://via.placeholder.com/150',0),(34,1,'哈士奇疯1','狗','哈士奇',1,0,'2024-08-25','2024-09-25','https://via.placeholder.com/150',0),(35,27,'无','猫','边牧',0,1,'1951-02-02','1951-02-02','/static/touxiang.svg',0),(36,41,'小金','狗','金毛',1,0,'2020-01-01','2020-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(37,52,'小金','狗','金毛',1,0,'2020-01-01','2020-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(38,54,'小金','狗','金毛',1,0,'2019-01-01','2019-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(39,61,'小金','狗','金毛',1,0,'2020-01-01','2020-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(40,62,'小金','狗','金毛',1,0,'2020-01-01','2020-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(41,64,'小金','狗','金毛',1,0,'2020-01-01','2020-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(42,67,'小金','狗','金毛',1,0,'2020-01-01','2020-03-01','/profile/avatar/2025/07/14/a70acd57c4f246379d1471f8a8561501.jpg',0),(43,79,'1','猫','美短',0,0,'2000-06-06','2000-06-06','/static/touxiang.svg',0),(44,80,'111111','猫','美短',0,0,'2000-06-06','2000-06-06','/static/touxiang.svg',0),(45,83,'kunii','猫','美短',0,0,'2000-06-06','2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/17/66c040d50ddb49fd9385979ff2aaeb25.gif',0),(46,85,'o','猫','美短',0,0,'2000-06-06','2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/17/c55463e655c84068878c008cddd8e2d5.gif',0),(47,87,'狗蛋','狗','拉布拉多',1,1,'2022-08-01','2022-10-01','/profile/temp/2025/07/17/2c895297a4da41cb8b69e255bd74925b.gif',0),(48,88,'YYYY','猫','美短',0,0,'2000-06-06','2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/17/c88f5787d55c4c52944229408acd4cc5.gif',0),(49,90,'$R','狗','金毛',0,1,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/17/c8cb148cabde4fecb3ebf5f9680c0f92.gif',0),(50,89,'12有','猫','美短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/17/c5fd4d8e1d82456ab2c8100e21024cfb.gif',0),(51,8,'yyyyy','狗','京巴',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/17/61984a4db6f34fafa2aa7e152a9071ea.gif',0),(52,111,'8888','猫','美短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/18/3721f3812f7d44d5b7b6e6beba0a8bb4.gif',0),(53,112,'888888','猫','美短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/18/30c30ca395c94a0f84fc0b05d11f81a0.gif',0),(54,113,'hhhhh','狗','牛头梗',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/18/ed1c6a14f4324dc19cebe0fc297c1797.jpg',0),(55,108,'厨子','狗','边牧',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/18/8a8df74472e14f21bee30fa640b13851.jpg',0),(56,114,'嘟嘟','猫','孟加拉猫',0,1,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/18/0c2a31d7581d4a43a3b13e22b11eb178.jpg',0),(57,117,'让人','猫','金渐层',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/18/568cf809656b4b72884e95787c09c253.jpg',0),(58,123,'ii','猫','孟加拉猫',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/19/3f5430ca5032421e86eea1df3dc23601.gif',0),(59,125,'888','猫','孟加拉猫',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/19/5c361197afd84c838a0fe12683e6b6e8.gif',0),(60,126,'1646545','猫','土猫',0,0,'2000-06-06','2000-06-09','/profile/temp/avatar/2025/07/19/8e78d019eed348738cbbe827a2261bf4.jpg',0),(61,127,'，，，，','猫','美短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/19/ffa65532bf9f44e88e083883b5e9f749.jpg',0),(62,128,'问问','猫','美短',0,1,'2003-06-06','2003-07-06','/profile/temp/avatar/2025/07/19/a62d648578954df1b1eefa9288a9528e.jpg',0),(63,118,'我哦','狗','德牧',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/19/d4db225c2d394468bcc7027447e8cf5f.jpg',0),(64,129,'aaa','猫','布偶猫',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/20/c8f0140c2617452ea1fbd2f1ce00bc2c.png',0),(65,131,'鱼','猫','英短',0,1,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/20/88b5ddfb1fd44a668cdb857e52c67740.jpg',0),(66,132,'母鸡','猫','布偶猫',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/20/e2940012b050456d8ac23ce74f7cc630.jpg',0),(67,133,'他','猫','美短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/20/f8ec9cce957b41c6852f8394cd3c5d61.jpg',0),(68,134,'阿湾','狗','金毛',1,1,'2020-12-06','2020-12-06','/profile/temp/avatar/2025/07/20/5d7a11cb79414811a1c6001f5e7b0ac1.jpg',0),(69,106,'还好','猫','英短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/20/c5d1f1796ace4f618259061d9bef8cb1.jpg',0),(70,141,'4444','猫','美短',0,0,'2000-06-06','2000-06-06','/profile/temp/avatar/2025/07/21/90a68992d9ac476aab5aee7f1d00d0cf.gif',0),(71,145,'哈哈','狗','二哈',1,1,'2011-06-06','2023-07-26','/profile/temp/avatar/2025/07/22/6e93b52b66a243bcb94820b1b688220e.jpg',0),(72,146,'匆匆','猫','英短',1,0,'2025-01-03','2025-03-06','/profile/temp/avatar/2025/07/22/2e4d50778a024c49a13d8837d1bad98e.jpg',0),(73,148,'鳄鱼','狗','比格犬',0,0,'2006-06-06','2025-07-23','/profile/temp/avatar/2025/07/23/4d06a9df48fe489d9605286c50646e4a.jpg',0);
/*!40000 ALTER TABLE `pet_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_info`
--

DROP TABLE IF EXISTS `status_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_info` (
  `StatusID` int unsigned NOT NULL AUTO_INCREMENT COMMENT '状态记录ID',
  `UserID` int unsigned NOT NULL COMMENT '用户ID',
  `ChangeType` tinyint NOT NULL COMMENT '状态变更类型(0=注册激活，1=手动冻结，2=自动冻结，3=注销申请，4=注销完成)',
  `OldStatus` tinyint NOT NULL COMMENT '变更前状态（对应User.Status）',
  `NewStatus` tinyint NOT NULL COMMENT '变更后状态（对应User.Status）',
  `ChangeTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '自动生成当前时间',
  `OperatorType` tinyint NOT NULL COMMENT '操作人类型 (0=系统自动，1=用户自助，2=管理员手动)',
  `Reason` varchar(200) NOT NULL COMMENT '记录状态变更的具体原因',
  `Proof` varchar(255) DEFAULT NULL COMMENT '存储操作凭证（如验证码、工单编号）',
  PRIMARY KEY (`StatusID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户状态信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_info`
--

LOCK TABLES `status_info` WRITE;
/*!40000 ALTER TABLE `status_info` DISABLE KEYS */;
INSERT INTO `status_info` (`StatusID`, `UserID`, `ChangeType`, `OldStatus`, `NewStatus`, `ChangeTime`, `OperatorType`, `Reason`, `Proof`) VALUES (1,57,0,1,0,'2025-07-16 13:18:51',0,'注册激活',NULL),(2,58,0,1,0,'2025-07-16 13:23:27',0,'注册激活',NULL),(3,59,0,1,0,'2025-07-16 13:26:13',0,'注册激活',NULL),(4,60,0,1,0,'2025-07-16 13:34:38',0,'注册激活',NULL),(5,61,0,1,0,'2025-07-16 14:06:28',0,'注册激活',NULL),(6,62,0,1,0,'2025-07-16 14:47:33',0,'注册激活',NULL),(7,63,0,1,0,'2025-07-16 14:47:55',0,'注册激活',NULL),(8,64,0,1,0,'2025-07-16 15:04:52',0,'注册激活',NULL),(9,65,0,1,0,'2025-07-16 15:08:05',0,'注册激活',NULL),(10,66,0,1,0,'2025-07-16 15:09:52',0,'注册激活',NULL),(11,67,0,1,0,'2025-07-16 15:16:24',0,'注册激活',NULL),(12,68,0,1,0,'2025-07-16 15:19:23',0,'注册激活',NULL),(13,69,0,1,0,'2025-07-16 15:22:46',0,'注册激活',NULL),(14,70,0,1,0,'2025-07-16 15:25:51',0,'注册激活',NULL),(15,71,0,1,0,'2025-07-16 15:34:51',0,'注册激活',NULL),(16,72,0,1,0,'2025-07-16 15:39:33',0,'注册激活',NULL),(17,73,0,1,0,'2025-07-16 15:46:48',0,'注册激活',NULL),(18,74,0,1,0,'2025-07-16 15:49:16',0,'注册激活',NULL),(19,75,0,1,0,'2025-07-16 15:51:15',0,'注册激活',NULL),(20,76,0,1,0,'2025-07-16 15:58:44',0,'注册激活',NULL),(21,77,0,1,0,'2025-07-17 14:38:22',0,'注册激活',NULL),(22,78,0,1,0,'2025-07-17 15:53:34',0,'注册激活',NULL),(23,79,0,1,0,'2025-07-17 16:51:38',0,'注册激活',NULL),(24,80,0,1,0,'2025-07-17 17:22:11',0,'注册激活',NULL),(25,81,0,1,0,'2025-07-17 17:49:16',0,'注册激活',NULL),(26,82,0,1,0,'2025-07-17 18:06:40',0,'注册激活',NULL),(27,83,0,1,0,'2025-07-17 18:10:21',0,'注册激活',NULL),(28,84,0,1,0,'2025-07-17 18:17:20',0,'注册激活',NULL),(29,85,0,1,0,'2025-07-17 18:23:13',0,'注册激活',NULL),(30,86,0,1,0,'2025-07-17 18:24:53',0,'注册激活',NULL),(31,87,0,1,0,'2025-07-17 19:45:51',0,'注册激活',NULL),(32,88,0,1,0,'2025-07-17 20:03:31',0,'注册激活',NULL),(33,89,0,1,0,'2025-07-17 20:09:37',0,'注册激活',NULL),(34,90,0,1,0,'2025-07-17 20:21:01',0,'注册激活',NULL),(35,91,0,1,0,'2025-07-17 20:40:09',0,'注册激活',NULL),(36,92,0,1,0,'2025-07-17 21:24:53',0,'注册激活',NULL),(37,93,0,1,0,'2025-07-17 22:21:23',0,'注册激活',NULL),(38,94,0,1,0,'2025-07-17 22:25:24',0,'注册激活',NULL),(39,95,0,1,0,'2025-07-17 22:53:46',0,'注册激活',NULL),(40,96,0,1,0,'2025-07-17 22:56:55',0,'注册激活',NULL),(41,97,0,1,0,'2025-07-17 22:58:00',0,'注册激活',NULL),(42,98,0,1,0,'2025-07-17 23:03:11',0,'注册激活',NULL),(43,99,0,1,0,'2025-07-17 23:08:05',0,'注册激活',NULL),(44,100,0,1,0,'2025-07-17 23:21:06',0,'注册激活',NULL),(45,101,0,1,0,'2025-07-18 13:13:33',0,'注册激活',NULL),(46,102,0,1,0,'2025-07-18 13:17:33',0,'注册激活',NULL),(47,103,0,1,0,'2025-07-18 19:47:30',0,'注册激活',NULL),(48,104,0,1,0,'2025-07-18 19:51:44',0,'注册激活',NULL),(49,105,0,1,0,'2025-07-18 20:24:41',0,'注册激活',NULL),(50,106,0,1,0,'2025-07-18 20:30:58',0,'注册激活',NULL),(51,107,0,1,0,'2025-07-18 20:32:37',0,'注册激活',NULL),(52,108,0,1,0,'2025-07-18 20:35:54',0,'注册激活',NULL),(53,109,0,1,0,'2025-07-18 20:47:51',0,'注册激活',NULL),(54,110,0,1,0,'2025-07-18 20:50:17',0,'注册激活',NULL),(55,111,0,1,0,'2025-07-18 21:08:14',0,'注册激活',NULL),(56,112,0,1,0,'2025-07-18 21:10:26',0,'注册激活',NULL),(57,113,0,1,0,'2025-07-18 21:15:33',0,'注册激活',NULL),(58,114,0,1,0,'2025-07-18 22:20:56',0,'注册激活',NULL),(59,115,0,1,0,'2025-07-18 22:22:56',0,'注册激活',NULL),(60,116,0,1,0,'2025-07-18 22:24:27',0,'注册激活',NULL),(61,117,0,1,0,'2025-07-18 22:34:16',0,'注册激活',NULL),(62,118,0,1,0,'2025-07-18 22:39:29',0,'注册激活',NULL),(63,119,0,1,0,'2025-07-18 22:44:36',0,'注册激活',NULL),(64,120,0,1,0,'2025-07-19 14:58:44',0,'注册激活',NULL),(65,121,0,1,0,'2025-07-19 14:59:47',0,'注册激活',NULL),(66,122,0,1,0,'2025-07-19 15:15:26',0,'注册激活',NULL),(67,123,0,1,0,'2025-07-19 15:42:18',0,'注册激活',NULL),(68,124,0,1,0,'2025-07-19 15:45:13',0,'注册激活',NULL),(69,125,0,1,0,'2025-07-19 15:53:31',0,'注册激活',NULL),(70,126,0,1,0,'2025-07-19 19:04:09',0,'注册激活',NULL),(71,127,0,1,0,'2025-07-19 19:16:41',0,'注册激活',NULL),(72,128,0,1,0,'2025-07-19 19:36:50',0,'注册激活',NULL),(73,129,0,1,0,'2025-07-20 11:35:05',0,'注册激活',NULL),(74,130,0,1,0,'2025-07-20 11:44:51',0,'注册激活',NULL),(75,131,0,1,0,'2025-07-20 15:18:10',0,'注册激活',NULL),(76,132,0,1,0,'2025-07-20 15:58:02',0,'注册激活',NULL),(77,133,0,1,0,'2025-07-20 16:03:11',0,'注册激活',NULL),(78,134,0,1,0,'2025-07-20 16:41:14',0,'注册激活',NULL),(79,135,0,1,0,'2025-07-20 17:12:46',0,'注册激活',NULL),(80,136,0,1,0,'2025-07-20 21:07:54',0,'注册激活',NULL),(81,137,0,1,0,'2025-07-20 21:16:28',0,'注册激活',NULL),(82,138,0,1,0,'2025-07-20 22:55:59',0,'注册激活',NULL),(83,139,0,1,0,'2025-07-21 15:06:56',0,'注册激活',NULL),(84,140,0,1,0,'2025-07-21 15:08:56',0,'注册激活',NULL),(85,141,0,1,0,'2025-07-21 15:53:26',0,'注册激活',NULL),(86,142,0,1,0,'2025-07-21 15:58:51',0,'注册激活',NULL),(87,143,0,1,0,'2025-07-21 20:12:24',0,'注册激活',NULL),(88,144,0,1,0,'2025-07-21 22:12:38',0,'注册激活',NULL),(89,145,0,1,0,'2025-07-22 20:52:37',0,'注册激活',NULL),(90,146,0,1,0,'2025-07-22 22:26:44',0,'注册激活',NULL),(91,147,0,1,0,'2025-07-22 22:32:32',0,'注册激活',NULL),(92,148,0,1,0,'2025-07-23 15:44:10',0,'注册激活',NULL);
/*!40000 ALTER TABLE `status_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'主框架页-默认皮肤样式名称','sys.index.skinName','skin-blue','Y','admin','2025-06-26 13:04:59','',NULL,'蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow'),(2,'用户管理-账号初始密码','sys.user.initPassword','123456','Y','admin','2025-06-26 13:04:59','',NULL,'初始化密码 123456'),(3,'主框架页-侧边栏主题','sys.index.sideTheme','theme-dark','Y','admin','2025-06-26 13:04:59','',NULL,'深色主题theme-dark，浅色主题theme-light'),(4,'账号自助-验证码开关','sys.account.captchaEnabled','true','Y','admin','2025-06-26 13:04:59','',NULL,'是否开启验证码功能（true开启，false关闭）'),(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','false','Y','admin','2025-06-26 13:04:59','',NULL,'是否开启注册用户功能（true开启，false关闭）'),(6,'用户登录-黑名单列表','sys.login.blackIPList','','Y','admin','2025-06-26 13:04:59','',NULL,'设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）'),(7,'用户管理-初始密码修改策略','sys.account.initPasswordModify','1','Y','admin','2025-06-26 13:04:59','',NULL,'0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框'),(8,'用户管理-账号密码更新周期','sys.account.passwordValidateDays','0','Y','admin','2025-06-26 13:04:59','',NULL,'密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` (`dept_id`, `parent_id`, `ancestors`, `dept_name`, `order_num`, `leader`, `phone`, `email`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (100,0,'0','若依科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(101,100,'0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(102,100,'0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(103,101,'0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(104,101,'0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(105,101,'0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(106,101,'0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(107,101,'0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(108,102,'0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL),(109,102,'0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2025-06-26 13:04:58','',NULL);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,1,'男','0','sys_user_sex','','','Y','0','admin','2025-06-26 13:04:59','',NULL,'性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2025-06-26 13:04:59','',NULL,'性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2025-06-26 13:04:59','',NULL,'性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2025-06-26 13:04:59','',NULL,'显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2025-06-26 13:04:59','',NULL,'正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2025-06-26 13:04:59','',NULL,'正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2025-06-26 13:04:59','',NULL,'默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2025-06-26 13:04:59','',NULL,'系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2025-06-26 13:04:59','',NULL,'系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2025-06-26 13:04:59','',NULL,'通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2025-06-26 13:04:59','',NULL,'公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2025-06-26 13:04:59','',NULL,'正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'关闭状态'),(18,99,'其他','0','sys_oper_type','','info','N','0','admin','2025-06-26 13:04:59','',NULL,'其他操作'),(19,1,'新增','1','sys_oper_type','','info','N','0','admin','2025-06-26 13:04:59','',NULL,'新增操作'),(20,2,'修改','2','sys_oper_type','','info','N','0','admin','2025-06-26 13:04:59','',NULL,'修改操作'),(21,3,'删除','3','sys_oper_type','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'删除操作'),(22,4,'授权','4','sys_oper_type','','primary','N','0','admin','2025-06-26 13:04:59','',NULL,'授权操作'),(23,5,'导出','5','sys_oper_type','','warning','N','0','admin','2025-06-26 13:04:59','',NULL,'导出操作'),(24,6,'导入','6','sys_oper_type','','warning','N','0','admin','2025-06-26 13:04:59','',NULL,'导入操作'),(25,7,'强退','7','sys_oper_type','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'强退操作'),(26,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2025-06-26 13:04:59','',NULL,'生成操作'),(27,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'清空操作'),(28,1,'成功','0','sys_common_status','','primary','N','0','admin','2025-06-26 13:04:59','',NULL,'正常状态'),(29,2,'失败','1','sys_common_status','','danger','N','0','admin','2025-06-26 13:04:59','',NULL,'停用状态'),(30,1,'生效','0','pet_class_status',NULL,'primary','N','0','admin','2025-07-03 14:49:26','',NULL,'数据生效状态'),(31,2,'失效','1','pet_class_status',NULL,'danger','N','0','admin','2025-07-03 14:49:26','',NULL,'数据失效状态');
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'用户性别','sys_user_sex','0','admin','2025-06-26 13:04:59','',NULL,'用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2025-06-26 13:04:59','',NULL,'菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2025-06-26 13:04:59','',NULL,'系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2025-06-26 13:04:59','',NULL,'任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2025-06-26 13:04:59','',NULL,'任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2025-06-26 13:04:59','',NULL,'系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2025-06-26 13:04:59','',NULL,'通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2025-06-26 13:04:59','',NULL,'通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2025-06-26 13:04:59','',NULL,'操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2025-06-26 13:04:59','',NULL,'登录状态列表'),(11,'宠物状态','pet_class_status','0','admin','2025-07-03 14:40:10','',NULL,'宠物管理状态');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` (`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'系统默认（无参）','DEFAULT','ryTask.ryNoParams','0/10 * * * * ?','3','1','1','admin','2025-06-26 13:04:59','',NULL,''),(2,'系统默认（有参）','DEFAULT','ryTask.ryParams(\'ry\')','0/15 * * * * ?','3','1','1','admin','2025-06-26 13:04:59','',NULL,''),(3,'系统默认（多参）','DEFAULT','ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)','0/20 * * * * ?','3','1','1','admin','2025-06-26 13:04:59','',NULL,'');
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`),
  KEY `idx_sys_logininfor_s` (`status`),
  KEY `idx_sys_logininfor_lt` (`login_time`)
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` (`info_id`, `user_name`, `ipaddr`, `login_location`, `browser`, `os`, `status`, `msg`, `login_time`) VALUES (100,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-06-26 13:05:10'),(101,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-06-26 14:58:14'),(102,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-06-26 14:58:19'),(103,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-06-26 19:43:01'),(104,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-06-28 20:29:08'),(105,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-06-28 20:29:14'),(106,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-06-30 11:31:28'),(107,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-06-30 11:48:25'),(108,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-06-30 11:48:36'),(109,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-06-30 13:04:14'),(110,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-06-30 15:05:53'),(111,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-06-30 15:43:42'),(112,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-06-30 15:43:56'),(113,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-06-30 16:23:55'),(114,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-06-30 16:24:03'),(115,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-01 12:53:55'),(116,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-01 13:34:36'),(117,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-01 13:34:50'),(118,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-02 21:28:39'),(119,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-02 21:29:02'),(120,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-02 21:31:12'),(121,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-02 22:20:47'),(122,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','Decryption error','2025-07-02 22:25:47'),(123,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-02 23:49:03'),(124,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-02 23:49:38'),(125,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','用户不存在/密码错误','2025-07-02 23:59:59'),(126,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','用户不存在/密码错误','2025-07-03 00:00:10'),(127,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-07-03 00:05:14'),(128,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-07-03 00:05:19'),(129,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','Cannot invoke \"org.springframework.security.core.Authentication.getName()\" because \"usernamePasswordAuthenticationToken\" is null','2025-07-03 00:05:25'),(130,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','Cannot invoke \"org.springframework.security.core.Authentication.getName()\" because \"usernamePasswordAuthenticationToken\" is null','2025-07-03 00:05:41'),(131,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','Cannot invoke \"org.springframework.security.core.Authentication.getName()\" because \"usernamePasswordAuthenticationToken\" is null','2025-07-03 00:09:14'),(132,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-03 00:12:58'),(133,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-03 00:13:22'),(134,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-03 00:13:29'),(135,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-03 00:14:37'),(136,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-03 00:14:49'),(137,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','1','验证码错误','2025-07-03 13:02:40'),(138,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 13:02:45'),(139,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','1','验证码错误','2025-07-03 13:02:55'),(140,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 13:03:26'),(141,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 13:58:36'),(142,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 13:59:43'),(143,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 14:08:41'),(144,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 14:52:37'),(145,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 14:52:51'),(146,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 14:58:14'),(147,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','1','验证码错误','2025-07-03 15:00:30'),(148,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 15:00:34'),(149,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 15:57:52'),(150,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 15:59:55'),(151,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 16:13:41'),(152,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 16:13:49'),(153,'admin','103.82.39.213','XX XX','Unknown','Unknown','1','验证码已失效','2025-07-03 16:20:25'),(154,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 16:22:55'),(155,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 16:26:25'),(156,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 16:27:04'),(157,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 21:52:38'),(158,'admin','127.0.0.1','内网IP','Mobile Safari','Mac OS X (iPhone)','0','退出成功','2025-07-03 21:52:53'),(159,'admin','127.0.0.1','内网IP','Mobile Safari','Mac OS X (iPhone)','0','登录成功','2025-07-03 21:53:13'),(160,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-03 22:29:44'),(161,'admin','192.168.0.224','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 22:30:56'),(162,'admin','192.168.0.224','内网IP','Mobile Safari','Mac OS X (iPhone)','0','登录成功','2025-07-03 22:31:02'),(163,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 22:55:48'),(164,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-03 23:35:17'),(165,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-03 23:38:19'),(166,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 10:19:49'),(167,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 10:20:30'),(168,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','1','验证码已失效','2025-07-04 11:33:42'),(169,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-04 11:33:46'),(170,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-04 12:27:48'),(171,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-04 12:27:54'),(172,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-04 12:28:03'),(173,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-04 12:28:09'),(174,'admin','103.127.218.188','XX XX','Unknown','Unknown','1','验证码已失效','2025-07-04 12:30:24'),(175,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-04 12:49:27'),(176,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-04 12:49:34'),(177,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-04 12:49:41'),(178,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-04 12:49:49'),(179,NULL,'127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 13:13:25'),(180,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-07-04 13:13:36'),(181,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','用户不存在/密码错误','2025-07-04 13:13:41'),(182,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','用户不存在/密码错误','2025-07-04 13:13:53'),(183,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','用户不存在/密码错误','2025-07-04 13:14:15'),(184,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 13:28:05'),(185,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 14:45:31'),(186,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:08:45'),(187,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-07-04 15:09:10'),(188,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:09:17'),(189,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:10:10'),(190,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:12:35'),(191,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:13:33'),(192,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:15:37'),(193,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:16:34'),(194,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:21:07'),(195,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码错误','2025-07-04 15:37:17'),(196,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:37:23'),(197,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 15:48:52'),(198,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 17:37:25'),(199,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 17:52:06'),(200,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 17:52:47'),(201,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 17:52:59'),(202,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 18:40:56'),(203,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 18:41:24'),(204,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 20:22:22'),(205,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 20:23:54'),(206,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码已失效','2025-07-04 22:17:22'),(207,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 22:17:26'),(208,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-04 22:18:26'),(209,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-04 22:18:34'),(210,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 20:12:55'),(211,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 20:13:22'),(212,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 20:13:55'),(213,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 20:14:26'),(214,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 20:29:51'),(215,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 20:30:17'),(216,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码已失效','2025-07-05 20:33:21'),(217,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 20:33:27'),(218,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 20:33:42'),(219,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 20:35:19'),(220,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 20:35:25'),(221,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 21:00:56'),(222,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 21:01:10'),(223,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码已失效','2025-07-05 21:13:51'),(224,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 21:14:02'),(225,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 21:14:23'),(226,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码已失效','2025-07-05 21:18:26'),(227,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 21:18:35'),(228,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 21:18:56'),(229,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 21:22:35'),(230,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-05 21:22:46'),(231,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-05 21:30:04'),(232,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 11:05:42'),(233,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 11:05:52'),(234,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 11:45:10'),(235,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 13:05:11'),(236,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 13:08:13'),(237,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 13:08:22'),(238,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 13:11:42'),(239,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 13:11:53'),(240,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 13:12:05'),(241,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 15:42:58'),(242,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 15:50:13'),(243,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 15:50:22'),(244,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 15:55:05'),(245,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 15:55:19'),(246,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 16:05:12'),(247,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 20:39:07'),(248,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 20:42:32'),(249,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 20:42:41'),(250,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 20:42:48'),(251,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码已失效','2025-07-06 20:45:27'),(252,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-06 20:45:32'),(253,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-06 20:45:39'),(254,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','1','验证码已失效','2025-07-07 11:29:48'),(255,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-09 13:59:57'),(256,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','退出成功','2025-07-09 14:28:50'),(257,'admin','127.0.0.1','内网IP','Chrome 13','Windows 10','0','登录成功','2025-07-09 14:29:05'),(258,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-09 18:46:04'),(259,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-10 15:45:05'),(260,'admin','27.149.87.4','XX XX','Chrome Mobile','Android 1.x','0','登录成功','2025-07-10 15:52:43'),(261,'admin','112.48.6.49','XX XX','Mobile Safari','Mac OS X (iPhone)','0','登录成功','2025-07-11 18:23:57'),(262,'admin','112.48.6.49','XX XX','Mobile Safari','Mac OS X (iPhone)','0','退出成功','2025-07-11 18:24:12'),(263,'admin','112.48.6.49','XX XX','Mobile Safari','Mac OS X (iPhone)','0','登录成功','2025-07-11 22:00:23'),(264,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-12 15:49:37'),(265,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-12 15:52:13'),(266,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-12 18:09:50'),(267,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-12 18:09:57'),(268,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-13 15:54:39'),(269,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-13 18:21:27'),(270,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-13 19:52:05'),(271,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-13 19:52:14'),(272,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-14 10:49:06'),(273,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-14 10:49:18'),(274,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:05:55'),(275,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:06:02'),(276,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:07:19'),(277,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:07:29'),(278,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:08:02'),(279,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:08:07'),(280,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:08:29'),(281,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:08:36'),(282,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:11:05'),(283,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:11:14'),(284,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:14:05'),(285,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:14:13'),(286,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:16:52'),(287,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:17:04'),(288,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 12:20:03'),(289,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 12:26:23'),(290,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 13:12:46'),(291,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 13:13:07'),(292,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 13:43:25'),(293,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 13:43:30'),(294,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-20 13:44:56'),(295,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-20 13:45:05'),(296,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-20 13:49:26'),(297,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-20 13:49:32'),(298,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-20 14:13:30'),(299,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-20 14:13:35'),(300,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 14:20:31'),(301,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 14:20:35'),(302,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','退出成功','2025-07-20 14:21:48'),(303,'admin','112.48.6.49','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 14:21:54'),(304,'admin','112.47.113.92','XX XX','Chrome 13','Windows 10','0','登录成功','2025-07-20 14:53:25'),(305,'admin','112.47.80.166','XX XX','Chrome 13','Windows 10','0','登录成功','2025-07-20 16:05:12'),(306,'admin','27.38.148.125','XX XX','Chrome 13','Mac OS X','0','登录成功','2025-07-20 16:06:59'),(307,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-21 20:10:41'),(308,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-21 20:10:44'),(309,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','退出成功','2025-07-21 21:43:39'),(310,'admin','127.0.0.1','内网IP','Chrome 13','Mac OS X','0','登录成功','2025-07-21 21:43:49');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) DEFAULT '' COMMENT '路由名称',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1086 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'系统管理',0,1,'system',NULL,'','',1,0,'M','0','0','','system','admin','2025-06-26 13:04:59','',NULL,'系统管理目录'),(2,'系统监控',0,2,'monitor',NULL,'','',1,0,'M','0','0','','monitor','admin','2025-06-26 13:04:59','',NULL,'系统监控目录'),(3,'系统工具',0,3,'tool',NULL,'','',1,0,'M','0','0','','tool','admin','2025-06-26 13:04:59','',NULL,'系统工具目录'),(4,'若依官网',0,4,'http://ruoyi.vip',NULL,'','',0,0,'M','1','0','','guide','admin','2025-06-26 13:04:59','admin','2025-07-16 11:03:57','若依官网地址'),(100,'用户管理',1,1,'user','system/user/index','','',1,0,'C','0','0','system:user:list','user','admin','2025-06-26 13:04:59','',NULL,'用户管理菜单'),(101,'角色管理',1,2,'role','system/role/index','','',1,0,'C','0','0','system:role:list','peoples','admin','2025-06-26 13:04:59','',NULL,'角色管理菜单'),(102,'菜单管理',1,3,'menu','system/menu/index','','',1,0,'C','0','0','system:menu:list','tree-table','admin','2025-06-26 13:04:59','',NULL,'菜单管理菜单'),(103,'部门管理',1,4,'dept','system/dept/index','','',1,0,'C','0','0','system:dept:list','tree','admin','2025-06-26 13:04:59','',NULL,'部门管理菜单'),(104,'岗位管理',1,5,'post','system/post/index','','',1,0,'C','0','0','system:post:list','post','admin','2025-06-26 13:04:59','',NULL,'岗位管理菜单'),(105,'字典管理',1,6,'dict','system/dict/index','','',1,0,'C','0','0','system:dict:list','dict','admin','2025-06-26 13:04:59','',NULL,'字典管理菜单'),(106,'参数设置',1,7,'config','system/config/index','','',1,0,'C','0','0','system:config:list','edit','admin','2025-06-26 13:04:59','',NULL,'参数设置菜单'),(107,'通知公告',1,8,'notice','system/notice/index','','',1,0,'C','0','0','system:notice:list','message','admin','2025-06-26 13:04:59','',NULL,'通知公告菜单'),(108,'日志管理',1,9,'log','','','',1,0,'M','0','0','','log','admin','2025-06-26 13:04:59','',NULL,'日志管理菜单'),(109,'在线用户',2,1,'online','monitor/online/index','','',1,0,'C','0','0','monitor:online:list','online','admin','2025-06-26 13:04:59','',NULL,'在线用户菜单'),(110,'定时任务',2,2,'job','monitor/job/index','','',1,0,'C','0','0','monitor:job:list','job','admin','2025-06-26 13:04:59','',NULL,'定时任务菜单'),(111,'数据监控',2,3,'druid','monitor/druid/index','','',1,0,'C','0','0','monitor:druid:list','druid','admin','2025-06-26 13:04:59','',NULL,'数据监控菜单'),(112,'服务监控',2,4,'server','monitor/server/index','','',1,0,'C','0','0','monitor:server:list','server','admin','2025-06-26 13:04:59','',NULL,'服务监控菜单'),(113,'缓存监控',2,5,'cache','monitor/cache/index','','',1,0,'C','0','0','monitor:cache:list','redis','admin','2025-06-26 13:04:59','',NULL,'缓存监控菜单'),(114,'缓存列表',2,6,'cacheList','monitor/cache/list','','',1,0,'C','0','0','monitor:cache:list','redis-list','admin','2025-06-26 13:04:59','',NULL,'缓存列表菜单'),(115,'表单构建',3,1,'build','tool/build/index','','',1,0,'C','0','0','tool:build:list','build','admin','2025-06-26 13:04:59','',NULL,'表单构建菜单'),(116,'代码生成',3,2,'gen','tool/gen/index','','',1,0,'C','0','0','tool:gen:list','code','admin','2025-06-26 13:04:59','',NULL,'代码生成菜单'),(117,'系统接口',3,3,'swagger','tool/swagger/index','','',1,0,'C','0','0','tool:swagger:list','swagger','admin','2025-06-26 13:04:59','',NULL,'系统接口菜单'),(500,'操作日志',108,1,'operlog','monitor/operlog/index','','',1,0,'C','0','0','monitor:operlog:list','form','admin','2025-06-26 13:04:59','',NULL,'操作日志菜单'),(501,'登录日志',108,2,'logininfor','monitor/logininfor/index','','',1,0,'C','0','0','monitor:logininfor:list','logininfor','admin','2025-06-26 13:04:59','',NULL,'登录日志菜单'),(1000,'用户查询',100,1,'','','','',1,0,'F','0','0','system:user:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1001,'用户新增',100,2,'','','','',1,0,'F','0','0','system:user:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1002,'用户修改',100,3,'','','','',1,0,'F','0','0','system:user:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1003,'用户删除',100,4,'','','','',1,0,'F','0','0','system:user:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1004,'用户导出',100,5,'','','','',1,0,'F','0','0','system:user:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1005,'用户导入',100,6,'','','','',1,0,'F','0','0','system:user:import','#','admin','2025-06-26 13:04:59','',NULL,''),(1006,'重置密码',100,7,'','','','',1,0,'F','0','0','system:user:resetPwd','#','admin','2025-06-26 13:04:59','',NULL,''),(1007,'角色查询',101,1,'','','','',1,0,'F','0','0','system:role:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1008,'角色新增',101,2,'','','','',1,0,'F','0','0','system:role:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1009,'角色修改',101,3,'','','','',1,0,'F','0','0','system:role:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1010,'角色删除',101,4,'','','','',1,0,'F','0','0','system:role:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1011,'角色导出',101,5,'','','','',1,0,'F','0','0','system:role:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1012,'菜单查询',102,1,'','','','',1,0,'F','0','0','system:menu:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1013,'菜单新增',102,2,'','','','',1,0,'F','0','0','system:menu:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1014,'菜单修改',102,3,'','','','',1,0,'F','0','0','system:menu:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1015,'菜单删除',102,4,'','','','',1,0,'F','0','0','system:menu:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1016,'部门查询',103,1,'','','','',1,0,'F','0','0','system:dept:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1017,'部门新增',103,2,'','','','',1,0,'F','0','0','system:dept:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1018,'部门修改',103,3,'','','','',1,0,'F','0','0','system:dept:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1019,'部门删除',103,4,'','','','',1,0,'F','0','0','system:dept:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1020,'岗位查询',104,1,'','','','',1,0,'F','0','0','system:post:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1021,'岗位新增',104,2,'','','','',1,0,'F','0','0','system:post:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1022,'岗位修改',104,3,'','','','',1,0,'F','0','0','system:post:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1023,'岗位删除',104,4,'','','','',1,0,'F','0','0','system:post:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1024,'岗位导出',104,5,'','','','',1,0,'F','0','0','system:post:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1025,'字典查询',105,1,'#','','','',1,0,'F','0','0','system:dict:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1026,'字典新增',105,2,'#','','','',1,0,'F','0','0','system:dict:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1027,'字典修改',105,3,'#','','','',1,0,'F','0','0','system:dict:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1028,'字典删除',105,4,'#','','','',1,0,'F','0','0','system:dict:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1029,'字典导出',105,5,'#','','','',1,0,'F','0','0','system:dict:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1030,'参数查询',106,1,'#','','','',1,0,'F','0','0','system:config:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1031,'参数新增',106,2,'#','','','',1,0,'F','0','0','system:config:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1032,'参数修改',106,3,'#','','','',1,0,'F','0','0','system:config:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1033,'参数删除',106,4,'#','','','',1,0,'F','0','0','system:config:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1034,'参数导出',106,5,'#','','','',1,0,'F','0','0','system:config:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1035,'公告查询',107,1,'#','','','',1,0,'F','0','0','system:notice:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1036,'公告新增',107,2,'#','','','',1,0,'F','0','0','system:notice:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1037,'公告修改',107,3,'#','','','',1,0,'F','0','0','system:notice:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1038,'公告删除',107,4,'#','','','',1,0,'F','0','0','system:notice:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1039,'操作查询',500,1,'#','','','',1,0,'F','0','0','monitor:operlog:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1040,'操作删除',500,2,'#','','','',1,0,'F','0','0','monitor:operlog:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1041,'日志导出',500,3,'#','','','',1,0,'F','0','0','monitor:operlog:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1042,'登录查询',501,1,'#','','','',1,0,'F','0','0','monitor:logininfor:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1043,'登录删除',501,2,'#','','','',1,0,'F','0','0','monitor:logininfor:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1044,'日志导出',501,3,'#','','','',1,0,'F','0','0','monitor:logininfor:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1045,'账户解锁',501,4,'#','','','',1,0,'F','0','0','monitor:logininfor:unlock','#','admin','2025-06-26 13:04:59','',NULL,''),(1046,'在线查询',109,1,'#','','','',1,0,'F','0','0','monitor:online:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1047,'批量强退',109,2,'#','','','',1,0,'F','0','0','monitor:online:batchLogout','#','admin','2025-06-26 13:04:59','',NULL,''),(1048,'单条强退',109,3,'#','','','',1,0,'F','0','0','monitor:online:forceLogout','#','admin','2025-06-26 13:04:59','',NULL,''),(1049,'任务查询',110,1,'#','','','',1,0,'F','0','0','monitor:job:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1050,'任务新增',110,2,'#','','','',1,0,'F','0','0','monitor:job:add','#','admin','2025-06-26 13:04:59','',NULL,''),(1051,'任务修改',110,3,'#','','','',1,0,'F','0','0','monitor:job:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1052,'任务删除',110,4,'#','','','',1,0,'F','0','0','monitor:job:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1053,'状态修改',110,5,'#','','','',1,0,'F','0','0','monitor:job:changeStatus','#','admin','2025-06-26 13:04:59','',NULL,''),(1054,'任务导出',110,6,'#','','','',1,0,'F','0','0','monitor:job:export','#','admin','2025-06-26 13:04:59','',NULL,''),(1055,'生成查询',116,1,'#','','','',1,0,'F','0','0','tool:gen:query','#','admin','2025-06-26 13:04:59','',NULL,''),(1056,'生成修改',116,2,'#','','','',1,0,'F','0','0','tool:gen:edit','#','admin','2025-06-26 13:04:59','',NULL,''),(1057,'生成删除',116,3,'#','','','',1,0,'F','0','0','tool:gen:remove','#','admin','2025-06-26 13:04:59','',NULL,''),(1058,'导入代码',116,4,'#','','','',1,0,'F','0','0','tool:gen:import','#','admin','2025-06-26 13:04:59','',NULL,''),(1059,'预览代码',116,5,'#','','','',1,0,'F','0','0','tool:gen:preview','#','admin','2025-06-26 13:04:59','',NULL,''),(1060,'生成代码',116,6,'#','','','',1,0,'F','0','0','tool:gen:code','#','admin','2025-06-26 13:04:59','',NULL,''),(1061,'宠物设置',0,0,'pet',NULL,NULL,'',1,0,'M','0','0','','404','admin','2025-06-30 11:40:24','admin','2025-07-09 18:47:16',''),(1068,'宠物品种',1061,1,'breed','breed/breed/index',NULL,'',1,0,'C','0','0','breed:breed:list','#','admin','2025-06-30 11:44:03','',NULL,'宠物品种菜单'),(1069,'宠物品种查询',1068,1,'#','',NULL,'',1,0,'F','0','0','breed:breed:query','#','admin','2025-06-30 11:44:03','',NULL,''),(1070,'宠物品种新增',1068,2,'#','',NULL,'',1,0,'F','0','0','breed:breed:add','#','admin','2025-06-30 11:44:03','',NULL,''),(1071,'宠物品种修改',1068,3,'#','',NULL,'',1,0,'F','0','0','breed:breed:edit','#','admin','2025-06-30 11:44:03','',NULL,''),(1072,'宠物品种删除',1068,4,'#','',NULL,'',1,0,'F','0','0','breed:breed:remove','#','admin','2025-06-30 11:44:03','',NULL,''),(1073,'宠物品种导出',1068,5,'#','',NULL,'',1,0,'F','0','0','breed:breed:export','#','admin','2025-06-30 11:44:03','',NULL,''),(1080,'宠物分类',1061,1,'petType','petType/petType/index',NULL,'',1,0,'C','0','0','petType:petType:list','#','admin','2025-06-30 11:45:47','',NULL,'宠物分类菜单'),(1081,'宠物分类查询',1080,1,'#','',NULL,'',1,0,'F','0','0','petType:petType:query','#','admin','2025-06-30 11:45:47','',NULL,''),(1082,'宠物分类新增',1080,2,'#','',NULL,'',1,0,'F','0','0','petType:petType:add','#','admin','2025-06-30 11:45:47','',NULL,''),(1083,'宠物分类修改',1080,3,'#','',NULL,'',1,0,'F','0','0','petType:petType:edit','#','admin','2025-06-30 11:45:47','',NULL,''),(1084,'宠物分类删除',1080,4,'#','',NULL,'',1,0,'F','0','0','petType:petType:remove','#','admin','2025-06-30 11:45:47','',NULL,''),(1085,'宠物分类导出',1080,5,'#','',NULL,'',1,0,'F','0','0','petType:petType:export','#','admin','2025-06-30 11:45:47','',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` (`notice_id`, `notice_title`, `notice_type`, `notice_content`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'温馨提醒：2018-07-01 若依新版本发布啦','2',_binary '新版本内容','0','admin','2025-06-26 13:04:59','',NULL,'管理员'),(2,'维护通知：2018-07-01 若依系统凌晨维护','1',_binary '维护内容','0','admin','2025-06-26 13:04:59','',NULL,'管理员');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`),
  KEY `idx_sys_oper_log_bt` (`business_type`),
  KEY `idx_sys_oper_log_s` (`status`),
  KEY `idx_sys_oper_log_ot` (`oper_time`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
INSERT INTO `sys_oper_log` (`oper_id`, `title`, `business_type`, `method`, `request_method`, `operator_type`, `oper_name`, `dept_name`, `oper_url`, `oper_ip`, `oper_location`, `oper_param`, `json_result`, `status`, `error_msg`, `oper_time`, `cost_time`) VALUES (100,'用户头像',2,'com.petlife.platform.web.controller.system.SysProfileController.avatar()','POST',1,'admin','研发部门','/system/user/profile/avatar','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"imgUrl\":\"/profile/avatar/2025/06/26/be99e41e0cd64142ba489455a82e3111.jpg\",\"code\":200}',0,NULL,'2025-06-26 13:06:59',110),(101,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-26 13:07:00',19),(102,'代码生成',6,'com.petlife.platform.generator.controller.GenController.importTableSave()','POST',1,'admin','研发部门','/tool/gen/importTable','127.0.0.1','内网IP','{\"tables\":\"pet_info,login_log,status_info\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-26 13:07:41',239),(103,'代码生成',6,'com.petlife.platform.generator.controller.GenController.importTableSave()','POST',1,'admin','研发部门','/tool/gen/importTable','127.0.0.1','内网IP','{\"tables\":\"user\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-26 13:07:48',63),(104,'菜单管理',1,'com.petlife.platform.web.controller.system.SysMenuController.add()','POST',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createBy\":\"admin\",\"icon\":\"system\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuName\":\"系统设置\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":0,\"path\":\"pet\",\"status\":\"0\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 11:40:24',344),(105,'代码生成',6,'com.petlife.platform.generator.controller.GenController.importTableSave()','POST',1,'admin','研发部门','/tool/gen/importTable','127.0.0.1','内网IP','{\"tables\":\"pet_breed,pet_classification\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 11:40:49',2824),(106,'代码生成',2,'com.petlife.platform.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"businessName\":\"classification\",\"className\":\"PetClassification\",\"columns\":[{\"capJavaField\":\"PetClassId\",\"columnComment\":\"宠物分类ID\",\"columnId\":58,\"columnName\":\"pet_class_id\",\"columnType\":\"int(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"petClassId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"No\",\"columnComment\":\"序号\",\"columnId\":59,\"columnName\":\"no\",\"columnType\":\"varchar(10)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"no\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"PetClass\",\"columnComment\":\"宠物分类\",\"columnId\":60,\"columnName\":\"pet_class\",\"columnType\":\"varchar(40)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"petClass\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"PetClassEn\",\"columnComment\":\"宠物分类（英文）\",\"columnId\":61,\"columnName\":\"pet_class_en\",\"columnType\":\"varchar(40)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 11:41:04',1212),(107,'代码生成',2,'com.petlife.platform.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"businessName\":\"breed\",\"className\":\"PetBreed\",\"columns\":[{\"capJavaField\":\"PetBreedId\",\"columnComment\":\"宠物品种ID\",\"columnId\":44,\"columnName\":\"pet_breed_id\",\"columnType\":\"int(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:46\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"petBreedId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"CnNo\",\"columnComment\":\"中文排序\",\"columnId\":45,\"columnName\":\"cn_no\",\"columnType\":\"varchar(10)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:47\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"cnNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"EnNo\",\"columnComment\":\"英文排序\",\"columnId\":46,\"columnName\":\"en_no\",\"columnType\":\"varchar(10)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:47\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"enNo\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":5,\"updateBy\":\"\",\"usableColumn\":false},{\"capJavaField\":\"PetClassId\",\"columnComment\":\"宠物分类id\",\"columnId\":47,\"columnName\":\"pet_class_id\",\"columnType\":\"varchar(20)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:47\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequ','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 11:41:15',1518),(108,'代码生成',8,'com.petlife.platform.generator.controller.GenController.batchGenCode()','GET',1,'admin','研发部门','/tool/gen/batchGenCode','127.0.0.1','内网IP','{\"tables\":\"pet_classification\"}',NULL,0,NULL,'2025-06-30 11:41:24',918),(109,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-06-30 12:18:47\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"4\",\"petClass\":\"鸟\",\"petClassId\":\"1\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 12:18:47',250),(110,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"H\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 12:21:51\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 12:21:51\",\"lastUpdater\":\"admin\",\"petBreed\":\"哈士奇\",\"petBreedEn\":\"Siberian Husky\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 12:21:52',279),(111,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"H\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 12:24:47\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 12:24:47\",\"lastUpdater\":\"admin\",\"petBreed\":\"哈士奇\",\"petBreedEn\":\"Siberian Husky\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 12:24:48',600),(112,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"H\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 13:05:18\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 13:05:18\",\"lastUpdater\":\"admin\",\"petBreed\":\"哈士奇\",\"petBreedEn\":\"Siberian Husky\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 13:05:18',305),(113,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"H\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 13:05:27\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 13:05:27\",\"lastUpdater\":\"admin\",\"petBreed\":\"哈士奇\",\"petBreedEn\":\"Siberian Husky\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 13:05:28',152),(114,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"A\",\"cnNo\":\"1.1.3\",\"createTime\":\"2025-06-30 13:07:46\",\"creator\":\"admin\",\"enInitials\":\"A\",\"enNo\":\"1.1.3\",\"lastUpdateTime\":\"2025-06-30 13:07:46\",\"lastUpdater\":\"admin\",\"petBreed\":\"阿富汗猎犬\",\"petBreedEn\":\"Afghan Hound\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 13:07:47',544),(115,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-06-30 13:12:23\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"4\",\"petClass\":\"鸟\",\"petClassId\":\"1\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 13:12:23',202),(116,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-06-30 13:14:42\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"4\",\"petClass\":\"鸟\",\"petClassId\":\"1\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 13:14:43',440),(117,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"Authorization\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luX3VzZXJfa2V5IjoiNzRjMjczZjYtNDFkNy00MzAzLWI5YTktODdlNzUzMzFmOTRkIn0.j1qPqrz-IFO1uwMV4ZPQ7vPODDdhR9lhu5ZiA3zkgT4NVuaYDKYBVbroCorTA_Ex9dZNEwne_g1h013wXFHCtg\"}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 13:16:03',217),(118,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:17:29',2956),(119,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:20:09',1066),(120,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:27:41',1641),(121,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:31:00',1356),(122,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:33:58',2135),(123,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:35:53',1466),(124,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:40:47',977),(125,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-06-30 13:42:05',1103),(126,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-27 14:05:31\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:07:57\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:07:57',132),(127,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:10:25\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:10:25',86),(128,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:11:56\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:11:57',92),(129,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:12:37\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:12:37',64),(130,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:12:46\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"狗\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"宠物名或者宠物（英文）重复\",\"code\":500}',0,NULL,'2025-06-30 14:12:46',486),(131,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:14:56\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:14:56',87),(132,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:15:08\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"狗\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"宠物名或者宠物（英文）重复\",\"code\":500}',0,NULL,'2025-06-30 14:15:08',60),(133,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:15:22\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:15:22',59),(134,'宠物分类',1,'com.petlife.platform.petTypes.controller.PetClassificationController.add()','POST',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-30 14:16:19\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:16:19\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"蛇\",\"petClassEn\":\"Snake\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:16:19',169),(135,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:28:27\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:28:27',134),(136,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:28:37\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:28:37',61),(137,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:28:44\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"狗\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"宠物名或者宠物（英文）重复\",\"code\":500}',0,NULL,'2025-06-30 14:28:44',60),(138,'宠物分类',1,'com.petlife.platform.petTypes.controller.PetClassificationController.add()','POST',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-30 14:29:25\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:29:25\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"蛇\",\"petClassEn\":\"Snake\",\"status\":0}','{\"msg\":\"宠物名或者宠物（英文）重复\",\"code\":500}',0,NULL,'2025-06-30 14:29:26',116),(139,'宠物分类',1,'com.petlife.platform.petTypes.controller.PetClassificationController.add()','POST',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-30 14:29:38\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:29:38\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"蛇\",\"petClassEn\":\"Snake\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 14:29:38',93),(140,'宠物分类',1,'com.petlife.platform.petTypes.controller.PetClassificationController.add()','POST',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-30 14:30:30\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 14:30:30\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"蛇\",\"petClassEn\":\"Snake\",\"status\":0}','{\"msg\":\"宠物名或者宠物（英文）重复\",\"code\":500}',0,NULL,'2025-06-30 14:30:30',62),(141,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','103.82.39.213','XX XX','{\"cnInitials\":\"A\",\"cnNo\":\"1.1.3\",\"createTime\":\"2025-06-30 15:18:39\",\"creator\":\"admin\",\"enInitials\":\"A\",\"enNo\":\"1.1.3\",\"lastUpdateTime\":\"2025-06-30 15:18:39\",\"lastUpdater\":\"admin\",\"petBreed\":\"阿富汗猎犬\",\"petBreedEn\":\"Afghan Hound\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 15:18:39',241),(142,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','103.82.39.213','XX XX','{\"cnInitials\":\"H\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 15:19:27\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 15:19:27\",\"lastUpdater\":\"admin\",\"petBreed\":\"哈士奇\",\"petBreedEn\":\"Siberian Husky\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 15:19:27',96),(143,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','103.82.39.213','XX XX','{\"cnInitials\":\"H\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 16:36:22\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 16:36:22\",\"lastUpdater\":\"admin\",\"petBreed\":\"哈士奇\",\"petBreedEn\":\"Siberian Husky\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-06-30 16:36:22',171),(144,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','103.82.39.213','XX XX','{\"cnInitials\":\"D\",\"cnNo\":\"1.1.2\",\"createTime\":\"2025-06-30 16:37:30\",\"creator\":\"admin\",\"enInitials\":\"G\",\"enNo\":\"1.1.2\",\"lastUpdateTime\":\"2025-06-30 16:37:30\",\"lastUpdater\":\"admin\",\"petBreed\":\"大丹犬\",\"petBreedEn\":\"Great Dane\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 16:37:31',126),(145,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','103.82.39.213','XX XX','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-06-30 16:41:41\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"4\",\"petClass\":\"鸟\",\"petClassId\":\"1\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-06-30 16:41:41',196),(146,'宠物分类',1,'com.petlife.platform.petTypes.controller.PetClassificationController.add()','POST',1,'admin','研发部门','/petType','103.82.39.213','XX XX','{\"createTime\":\"2025-06-30 16:58:34\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-06-30 16:58:34\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"蛇\",\"petClassEn\":\"Snake\",\"status\":0}','{\"msg\":\"宠物名或者宠物（英文）重复\",\"code\":500}',0,NULL,'2025-06-30 16:58:35',176),(147,'代码生成',2,'com.petlife.platform.generator.controller.GenController.editSave()','PUT',1,'admin','研发部门','/tool/gen','127.0.0.1','内网IP','{\"businessName\":\"classification\",\"className\":\"PetClassification\",\"columns\":[{\"capJavaField\":\"PetClassId\",\"columnComment\":\"宠物分类ID\",\"columnId\":58,\"columnName\":\"pet_class_id\",\"columnType\":\"int(20) unsigned\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":false,\"htmlType\":\"input\",\"increment\":true,\"insert\":true,\"isIncrement\":\"1\",\"isInsert\":\"1\",\"isPk\":\"1\",\"isRequired\":\"0\",\"javaField\":\"petClassId\",\"javaType\":\"Long\",\"list\":false,\"params\":{},\"pk\":true,\"query\":false,\"queryType\":\"EQ\",\"required\":false,\"sort\":1,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2025-06-30 11:41:03\",\"usableColumn\":false},{\"capJavaField\":\"No\",\"columnComment\":\"序号\",\"columnId\":59,\"columnName\":\"no\",\"columnType\":\"varchar(10)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"no\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":2,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2025-06-30 11:41:03\",\"usableColumn\":false},{\"capJavaField\":\"PetClass\",\"columnComment\":\"宠物分类\",\"columnId\":60,\"columnName\":\"pet_class\",\"columnType\":\"varchar(40)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"input\",\"increment\":false,\"insert\":true,\"isEdit\":\"1\",\"isIncrement\":\"0\",\"isInsert\":\"1\",\"isList\":\"1\",\"isPk\":\"0\",\"isQuery\":\"1\",\"isRequired\":\"1\",\"javaField\":\"petClass\",\"javaType\":\"String\",\"list\":true,\"params\":{},\"pk\":false,\"query\":true,\"queryType\":\"EQ\",\"required\":true,\"sort\":3,\"superColumn\":false,\"tableId\":6,\"updateBy\":\"\",\"updateTime\":\"2025-06-30 11:41:03\",\"usableColumn\":false},{\"capJavaField\":\"PetClassEn\",\"columnComment\":\"宠物分类（英文）\",\"columnId\":61,\"columnName\":\"pet_class_en\",\"columnType\":\"varchar(40)\",\"createBy\":\"admin\",\"createTime\":\"2025-06-30 11:40:48\",\"dictType\":\"\",\"edit\":true,\"htmlType\":\"in','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-03 14:51:53',789),(148,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-07-03 15:03:16',1418),(149,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-07-03 15:26:58',163),(150,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','127.0.0.1','内网IP','{\"pageSize\":\"10\",\"pageNum\":\"1\"}',NULL,0,NULL,'2025-07-03 15:30:06',94),(151,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"J\",\"cnNo\":\"1.1\",\"createTime\":\"2025-07-03 15:36:30\",\"creator\":\"admin\",\"enInitials\":\"G\",\"enNo\":\"1.1\",\"lastUpdateTime\":\"2025-07-03 15:36:30\",\"lastUpdater\":\"admin\",\"petBreed\":\"金毛\",\"petBreedEn\":\"Golden Retriever\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-07-03 15:36:31',533),(152,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"J\",\"cnNo\":\"1.1\",\"createTime\":\"2025-07-03 15:36:44\",\"creator\":\"admin\",\"enInitials\":\"G\",\"enNo\":\"1.1\",\"lastUpdateTime\":\"2025-07-03 15:36:44\",\"lastUpdater\":\"admin\",\"petBreed\":\"金毛\",\"petBreedEn\":\"Golden Retriever\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"宠物品种重复\",\"code\":500}',0,NULL,'2025-07-03 15:36:45',89),(153,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"B\",\"cnNo\":\"3.2\",\"createTime\":\"2025-07-03 23:02:32\",\"creator\":\"admin\",\"enInitials\":\"M\",\"enNo\":\"3.2\",\"lastUpdateTime\":\"2025-07-03 23:02:32\",\"lastUpdater\":\"admin\",\"petBreed\":\"八哥\",\"petBreedEn\":\"Myna\",\"petBreedId\":\"BIRD_BREED_003\",\"petClass\":\"鸟\",\"petClassId\":\"1\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-03 23:02:32',286),(154,'宠物品种',1,'com.petlife.platform.breeds.controller.PetBreedController.add()','POST',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"X\",\"cnNo\":\"1.2.26\",\"createTime\":\"2025-07-03 23:05:45\",\"creator\":\"admin\",\"enInitials\":\"S\",\"enNo\":\"1.2.26\",\"lastUpdateTime\":\"2025-07-03 23:05:45\",\"lastUpdater\":\"admin\",\"petBreed\":\"希腊猫\",\"petBreedEn\":\"Santorini Cat\",\"petBreedId\":\"CAT_BREED_026\",\"petClass\":\"猫\",\"petClassId\":\"2\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-03 23:05:45',413),(155,'菜单管理',2,'com.petlife.platform.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-06-30 11:40:24\",\"icon\":\"404\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":1061,\"menuName\":\"宠物设置\",\"menuType\":\"M\",\"orderNum\":1,\"params\":{},\"parentId\":0,\"path\":\"pet\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-09 18:46:55',241),(156,'菜单管理',2,'com.petlife.platform.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-06-30 11:40:24\",\"icon\":\"404\",\"isCache\":\"0\",\"isFrame\":\"1\",\"menuId\":1061,\"menuName\":\"宠物设置\",\"menuType\":\"M\",\"orderNum\":0,\"params\":{},\"parentId\":0,\"path\":\"pet\",\"perms\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"0\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-09 18:47:16',298),(157,'用户头像',2,'com.petlife.platform.web.controller.system.SysProfileController.avatar()','POST',1,'admin','研发部门','/system/user/profile/avatar','127.0.0.1','内网IP','','{\"msg\":\"操作成功\",\"imgUrl\":\"/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png\",\"code\":200}',0,NULL,'2025-07-13 13:23:18',490),(158,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-13 15:51:34',799),(159,'菜单管理',3,'com.petlife.platform.web.controller.system.SysMenuController.remove()','DELETE',1,'admin','研发部门','/system/menu/4','127.0.0.1','内网IP','4','{\"msg\":\"菜单已分配,不允许删除\",\"code\":601}',0,NULL,'2025-07-16 11:03:29',210),(160,'菜单管理',2,'com.petlife.platform.web.controller.system.SysMenuController.edit()','PUT',1,'admin','研发部门','/system/menu','127.0.0.1','内网IP','{\"children\":[],\"createTime\":\"2025-06-26 13:04:59\",\"icon\":\"guide\",\"isCache\":\"0\",\"isFrame\":\"0\",\"menuId\":4,\"menuName\":\"若依官网\",\"menuType\":\"M\",\"orderNum\":4,\"params\":{},\"parentId\":0,\"path\":\"http://ruoyi.vip\",\"perms\":\"\",\"query\":\"\",\"routeName\":\"\",\"status\":\"0\",\"updateBy\":\"admin\",\"visible\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-16 11:03:57',306),(161,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','112.48.6.49','XX XX','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 12:08:24',330),(162,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','112.48.6.49','XX XX','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 13:43:22',292),(163,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 13:44:53',499),(164,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 14:04:37',590),(165,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"avatar\":\"/profile/avatar/2025/07/20/20164cb5b49a4bc38b787c99811ca314.jpeg\",\"params\":{}}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 14:13:19',266),(166,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 14:13:21',340),(167,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','112.48.6.49','XX XX','{\"admin\":false,\"email\":\"ry@163.com\",\"nickName\":\"宠物管理员\",\"params\":{},\"phonenumber\":\"15888888888\",\"sex\":\"1\"}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 14:20:27',361),(168,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','112.48.6.49','XX XX','{\"admin\":false,\"avatar\":\"/profile/avatar/2025/07/20/d4ad29042b1843aabe748548d2aae33a.jpeg\",\"params\":{}}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 14:21:16',165),(169,'宠物品种',5,'com.petlife.platform.breeds.controller.PetBreedController.export()','POST',1,'admin','研发部门','/breed/export','112.48.6.49','XX XX','{\"pageSize\":\"10\",\"pageNum\":\"2\"}',NULL,0,NULL,'2025-07-20 14:26:40',1028),(170,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','27.38.148.125','XX XX','{\"cnInitials\":\"B\",\"cnNo\":\"1.2.3\",\"createTime\":\"2025-07-01 13:00:46\",\"creator\":\"admin\",\"enInitials\":\"R\",\"enNo\":\"1.2.3\",\"lastUpdateTime\":\"2025-07-20 17:20:59\",\"lastUpdater\":\"admin\",\"petBreed\":\"布偶猫\",\"petBreedEn\":\"Ragdoll\",\"petBreedId\":\"CAT_BREED_003\",\"petClass\":\"猫\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-20 17:20:59',247),(171,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-07-21 21:14:52\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"BIRD_BREED_001\",\"petClass\":\"猫\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:14:52',546),(172,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-07-21 21:15:00\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"BIRD_BREED_001\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:15:00',333),(173,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-07-21 21:15:09\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"BIRD_BREED_001\",\"petClass\":\"猫\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:15:10',398),(174,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-07-21 21:25:40\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"BIRD_BREED_001\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:25:41',532),(175,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"h\",\"cnNo\":\"1\",\"createTime\":\"2025-06-27 19:22:59\",\"creator\":\"admin\",\"enInitials\":\"h\",\"enNo\":\"1\",\"lastUpdateTime\":\"2025-07-21 21:25:48\",\"lastUpdater\":\"admin\",\"petBreed\":\"黑金刚\",\"petBreedEn\":\"heijg\",\"petBreedId\":\"BIRD_BREED_001\",\"petClass\":\"猫\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:25:49',394),(176,'宠物品种',2,'com.petlife.platform.breeds.controller.PetBreedController.edit()','PUT',1,'admin','研发部门','/breed','127.0.0.1','内网IP','{\"cnInitials\":\"B\",\"cnNo\":\"1.1.1\",\"createTime\":\"2025-07-01 12:59:05\",\"creator\":\"admin\",\"enInitials\":\"B\",\"enNo\":\"1.1.1\",\"lastUpdateTime\":\"2025-07-21 21:26:39\",\"lastUpdater\":\"admin\",\"petBreed\":\"比格犬\",\"petBreedEn\":\"Beagle\",\"petBreedId\":\"DOG_BREED_002\",\"petClass\":\"狗\",\"petClassId\":\"3\",\"status\":0}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:26:40',502),(177,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-27 14:05:31\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-07-21 21:41:50\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫1\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:41:50',314),(178,'宠物分类',2,'com.petlife.platform.petTypes.controller.PetClassificationController.edit()','PUT',1,'admin','研发部门','/petType','127.0.0.1','内网IP','{\"createTime\":\"2025-06-27 14:05:31\",\"creator\":\"admin\",\"lastUpdateTime\":\"2025-07-21 21:41:55\",\"lastUpdater\":\"admin\",\"no\":\"1\",\"petClass\":\"猫\",\"petClassEn\":\"Cat\",\"petClassId\":\"2\",\"status\":1}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:41:55',189),(179,'个人信息',2,'com.petlife.platform.web.controller.system.SysProfileController.updateProfile()','PUT',1,'admin','研发部门','/system/user/profile','127.0.0.1','内网IP','{\"admin\":false,\"avatar\":\"/profile/avatar/2025/07/21/97d04d80c2be422ba20df7e4268824a5.jpeg\",\"params\":{}}','{\"msg\":\"操作成功\",\"code\":200}',0,NULL,'2025-07-21 21:43:33',546);
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
INSERT INTO `sys_post` (`post_id`, `post_code`, `post_name`, `post_sort`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'ceo','董事长',1,'0','admin','2025-06-26 13:04:58','',NULL,''),(2,'se','项目经理',2,'0','admin','2025-06-26 13:04:58','',NULL,''),(3,'hr','人力资源',3,'0','admin','2025-06-26 13:04:58','',NULL,''),(4,'user','普通员工',4,'0','admin','2025-06-26 13:04:58','',NULL,'');
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `data_scope`, `menu_check_strictly`, `dept_check_strictly`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,'超级管理员','admin',1,'1',1,1,'0','0','admin','2025-06-26 13:04:58','',NULL,'超级管理员'),(2,'普通角色','common',2,'2',1,1,'0','0','admin','2025-06-26 13:04:58','',NULL,'普通角色');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
INSERT INTO `sys_role_dept` (`role_id`, `dept_id`) VALUES (2,100),(2,101),(2,105);
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (2,1),(2,2),(2,3),(2,4),(2,100),(2,101),(2,102),(2,103),(2,104),(2,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,117),(2,500),(2,501),(2,1000),(2,1001),(2,1002),(2,1003),(2,1004),(2,1005),(2,1006),(2,1007),(2,1008),(2,1009),(2,1010),(2,1011),(2,1012),(2,1013),(2,1014),(2,1015),(2,1016),(2,1017),(2,1018),(2,1019),(2,1020),(2,1021),(2,1022),(2,1023),(2,1024),(2,1025),(2,1026),(2,1027),(2,1028),(2,1029),(2,1030),(2,1031),(2,1032),(2,1033),(2,1034),(2,1035),(2,1036),(2,1037),(2,1038),(2,1039),(2,1040),(2,1041),(2,1042),(2,1043),(2,1044),(2,1045),(2,1046),(2,1047),(2,1048),(2,1049),(2,1050),(2,1051),(2,1052),(2,1053),(2,1054),(2,1055),(2,1056),(2,1057),(2,1058),(2,1059),(2,1060);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`user_id`, `dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phonenumber`, `sex`, `avatar`, `password`, `status`, `del_flag`, `login_ip`, `login_date`, `pwd_update_date`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1,103,'admin','宠物管理员','00','ry@163.com','15888888888','1','/profile/avatar/2025/07/21/97d04d80c2be422ba20df7e4268824a5.jpeg','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-07-21 21:43:49','2025-06-26 13:04:58','admin','2025-06-26 13:04:58','','2025-07-21 21:43:49','管理员'),(2,105,'ry','若依','00','ry@qq.com','15666666666','1','','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2','0','0','127.0.0.1','2025-06-26 13:04:58','2025-06-26 13:04:58','admin','2025-06-26 13:04:58','',NULL,'测试员');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
INSERT INTO `sys_user_post` (`user_id`, `post_id`) VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID，自增主键',
  `phone` varchar(13) NOT NULL COMMENT '手机号（+86前缀+11位数字）',
  `nick_name` varchar(10) NOT NULL COMMENT '昵称（中文/字母/数字，≤10字符）',
  `password` varchar(100) NOT NULL COMMENT 'bcrypt加密算法',
  `gender` tinyint NOT NULL DEFAULT '0' COMMENT '性别（0=未设置，1=男，2=女）',
  `birthday` date NOT NULL COMMENT '出生日期（YYYY-MM-DD）',
  `avatar_url` varchar(255) NOT NULL COMMENT '头像图片URL（≤10MB）',
  `register_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `minor` tinyint NOT NULL COMMENT '未成年人标记（0=成年，1=未成年）',
  `auth` tinyint NOT NULL DEFAULT '0' COMMENT '实名认证状态（0=未认证，1=已认证）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '账号状态（0=生效，1=已注销，2=冻结）',
  `last_logoff_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近离线时间，每次用户离线后自动更新为当前时间',
  `personal_signature` varchar(300) DEFAULT NULL COMMENT '用户填写的个性签名，最长不能超过100个字',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_nick_name` (`nick_name`),
  CONSTRAINT `chk_gender` CHECK ((`gender` in (0,1,2)))
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基础信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `phone`, `nick_name`, `password`, `gender`, `birthday`, `avatar_url`, `register_time`, `last_login_time`, `minor`, `auth`, `status`, `last_logoff_time`, `personal_signature`) VALUES (1,'13812345678','小王','$2a$10$JMyqk9/qqagbcwJDugbc2OJDJM06Qr8/yE/SVpJrxiB/KP/pLPg8K',2,'2001-01-01','/uploadPath/avatar/2025/06/26/be99e41e0cd64142ba489455a82e3111.jpg','2025-06-28 21:18:01','2025-07-06 14:37:52',0,0,0,'2025-07-06 14:37:52',NULL),(3,'13888880001','frozenUser','$2a$10$DZiEB2TGgAEZ10aRXHdEmeRxENcct7fdR4hlNSJO/wkDjmq4PsPKq',0,'2000-01-01','http://example.com/avatar3.jpg','2025-06-28 21:18:01','2025-06-28 21:37:22',0,0,1,'2025-06-29 15:44:48',NULL),(4,'13888880002','lockUser','$2a$10$DZiEB2TGgAEZ10aRXHdEmeRxENcct7fdR4hlNSJO/wkDjmq4PsPKq',0,'2000-01-01','http://example.com/avatar4.jpg','2025-06-28 21:18:01','2025-06-28 21:37:22',0,0,2,'2025-06-29 15:44:48',NULL),(5,'13887654321','用户4321','$2a$10$kUGbxPJZqsMrkSjZO3RU/OA1DpPmAaKSmnv8bkDdgJEb6fQsdQ2xa',0,'2000-01-01','/uploadPath/avatar/2025/06/26/be99e41e0cd64142ba489455a82e3111.jpg','2025-07-03 21:25:40','2025-07-03 21:25:40',0,0,0,'2025-07-03 21:25:40',NULL),(6,'13812345675','用户5675','$2a$10$tRzzbpon3/3KsAPlvO3UZO51cAcMtVkYn1uzYTCmLFZiZGk8zvfQC',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-06 11:48:19','2025-07-06 11:48:19',0,0,0,'2025-07-06 11:48:19',NULL),(7,'19334535353','用户5353','$2a$10$DO.0KgjjhgDtckgGawghBe/jiXgAksoX8VLjhMSgyyNTTOZxhJ3E.',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-07 12:00:23','2025-07-07 12:00:23',0,0,0,'2025-07-07 12:00:23',NULL),(8,'18888888888','AAA','$2a$10$Exa8uHLBYF6RgbuC8kp6S.4eNuOos3OiQfCxfSOZ.g7IAPtNJ1ED2',0,'2025-06-06','/static/touxiang.svg','2025-07-07 12:01:16','2025-07-08 21:49:35',0,0,0,'2025-07-08 21:49:35',NULL),(9,'19999999999','用户9999','$2a$10$rIvDwsMlKqqSgFECxC/Qv.E6IjnZSyRu9cdCJe2G5fLdqUBRQmJL2',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-07 12:03:58','2025-07-07 12:03:58',0,0,0,'2025-07-07 12:03:58',NULL),(10,'19999999993','用户9993','$2a$10$V.pEG1dPX7dJavN5PxVgK.wpaDBIuMKd8Efzti1mwgf04fv4Dfj/y',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-07 12:08:29','2025-07-07 12:08:29',0,0,0,'2025-07-07 12:08:29',NULL),(11,'13812345677','77','$2a$10$3KTYhkwXGc6I2mksE/EoUuwiUxc/nOoF9dmsIKztjUnBZUZIkCzBK',0,'2023-07-12','/static/touxiang.svg','2025-07-07 12:12:23','2025-07-12 16:23:38',0,0,0,'2025-07-12 16:23:38',NULL),(12,'16464646464','uuu','$2a$10$q/Qxbt6TG.3a0vgl.OzGCedIXkoNMxF.ZWtTalLpbX6x86YZXfboW',0,'2025-07-10','/static/touxiang.svg','2025-07-10 15:52:37','2025-07-10 15:55:15',0,0,0,'2025-07-10 15:55:15',NULL),(14,'18242424223','用户4223','$2a$10$QCYJGys3kSQiAB8qpSOWGuPOHbiv5rm24eIjQN3B8U86OC4QxtIJ2',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-11 18:55:54','2025-07-11 18:55:54',0,0,0,'2025-07-11 18:55:54',NULL),(15,'18888888887','ABC','$2a$10$x/6Pf7ds643UohTS4kzlqeFD.ohwJgSVMeTVogx4tL65NkAgfCbEG',0,'2000-06-06','https://122.228.237.118:53627undefined','2025-07-12 15:12:37','2025-07-14 20:48:15',0,0,0,'2025-07-14 20:48:15',NULL),(16,'18888888788','用户8788','$2a$10$YaZdV.bgLJfk2/rUbkr4d.ZkHyrpEdhxqhHAx9tjAklZDCkRpxtNy',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 15:22:47','2025-07-12 15:22:47',0,0,0,'2025-07-12 15:22:47',NULL),(17,'18888888353','用户8353','$2a$10$/xie1551bnZGdREiykUksu3T.G0qZRwsa8d2XBQxUxuNuFQYkQ.2a',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 17:10:27','2025-07-12 17:10:27',0,0,0,'2025-07-12 17:10:27',NULL),(18,'15335353553','AAAS','$2a$10$QBMWhX/S9XpKuYfwL5VhoupcFCiYK2czZ02P5Z4UDOak7M2GqCeye',0,'2023-07-12','blob:http://localhost:5173/c42854f5-43a5-4194-94ff-e8cc2d1013e3','2025-07-12 17:27:55','2025-07-12 17:28:14',0,0,0,'2025-07-12 17:28:14',NULL),(19,'14564664664','用户4664','$2a$10$aMzpav9o1BqfunYZG2ROWenVx5AsUBl9YY2f/OE.9cmjIqTQ2xnI6',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 17:44:37','2025-07-12 17:44:37',0,0,0,'2025-07-12 17:44:37',NULL),(20,'17773545354','用户5354','$2a$10$r1DFXWYr1H.LVed7EVHwF.uEDJj4ZhGY1bwJ5CQTfWVEaemG6WGAG',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 22:13:49','2025-07-12 22:13:49',0,0,0,'2025-07-12 22:13:49',NULL),(21,'13242424242','用户4242','$2a$10$sFR5Jx7uq5n1tN4ck10lVeZtLU/YXSzIbhojtQXxF6y/Dm0c.3CUq',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 22:15:40','2025-07-12 22:15:40',0,0,0,'2025-07-12 22:15:40',NULL),(22,'18888885335','用户5335','$2a$10$ki6z/nhfWqyYnt2evXRR5uPofWLolTipu1ccvX4OuZijRCrvnAEoW',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 22:23:37','2025-07-12 22:23:37',0,0,0,'2025-07-12 22:23:37',NULL),(23,'18956789123','，，，','$2a$10$6A.zF0b1yPhcdxeygn.ZW.2kMYv6TVTpuon05n6IjiVzHvj67XbP.',0,'1951-02-02','file:///storage/emulated/0/Android/data/io.dcloud.HBuilder/apps/HBuilder/doc/uniapp_temp/compressed/1752331169721_1752060081718.jpg','2025-07-12 22:37:25','2025-07-12 22:39:34',0,0,0,'2025-07-12 22:39:34',NULL),(24,'19754896848','用户6848','$2a$10$yN4GdCdO5TTp6uLOHLL8P.ZspicySGJN0k6KOh.HydwKJ8pzaZ9BO',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 22:41:51','2025-07-12 22:41:51',0,0,0,'2025-07-12 22:41:51',NULL),(25,'15059233711','用户3711','$2a$10$YWxm5Zvh2aKYiSBAaquzQu139j5eEzxZyVo.lIyGEXUmNIm3UGQGa',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 23:18:28','2025-07-12 23:18:28',0,0,0,'2025-07-12 23:18:28',NULL),(26,'13812345699','用户5699','$2a$10$Fq.p5pJ/OsEr6TEj/hm6oOCJ6IMtC.U6qOy9FKCfZozMs/ZzzOHFi',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-12 23:18:50','2025-07-12 23:18:50',0,0,0,'2025-07-12 23:18:50',NULL),(27,'17350254080','张三','$2a$10$TqwX/o2wT2ygCFVRA/whwudJlzZ85wRCLjgTLwNTBcwAc2LNQpq4q',0,'1951-02-05','file:///storage/emulated/0/Android/data/io.chongAi/apps/__UNI__8A235A8/doc/uniapp_temp/compressed/1752378739954_Screenshot_20250709_145503.jpg','2025-07-13 11:51:46','2025-07-13 11:53:09',0,0,0,'2025-07-13 11:53:09',NULL),(28,'18123456789','用户6789','$2a$10$JoXZOhQmh29enyA2Wsh94.FZ1Kg5YYDtg2LgroDg76xygPdfNSQzC',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-13 12:46:09','2025-07-13 12:46:09',0,0,0,'2025-07-13 12:46:09',NULL),(29,'13612345678','狗蛋ww','$2a$10$5hapyxTdjVq9AJXkPfZq3uFMZpas82qJN0ekR3XVh68rFltPF4F9K',0,'2007-02-02','file:///storage/emulated/0/Android/data/io.chongAi/apps/__UNI__8A235A8/doc/uniapp_temp/compressed/1752383719462_Image_1752383703508.jpg','2025-07-13 13:07:16','2025-07-13 13:42:08',0,0,0,'2025-07-13 13:42:08',NULL),(30,'15555555555','用户5555','$2a$10$eXxotIl6xUOE3pmdLGD4ye7Fp8E7W8J77TOZ8FcKwDJUGqWResqhS',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-13 17:09:30','2025-07-13 17:09:30',0,0,0,'2025-07-13 17:09:30',NULL),(31,'13412345678','用户5678','$2a$10$17vbDKC6I9fjAFTTGGAI9OmJo/KpijH995ekVuMRjvvOizzrdwCuO',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-13 21:27:12','2025-07-13 21:27:12',0,0,0,'2025-07-13 21:27:12',NULL),(36,'13212345678','测试用户','$2a$10$XnxESJ.COBBZ3V16d1II2eaPqregWlC/VbtWIJYmddF19tyeIe0ce',0,'2000-01-01','http://example.com/avatar.jpg','2025-07-14 12:01:34','2025-07-14 12:01:34',0,0,0,'2025-07-14 12:01:34',NULL),(37,'13112345678','测试用户2','$2a$10$QPN05G7T/C3FxAt97inrrOp.WegfwEV8pRFSEkBDDAFapUuYieQ9S',0,'1995-05-15','http://example.com/avatar.jpg','2025-07-14 12:52:56','2025-07-14 12:52:56',0,0,0,'2025-07-14 12:52:56',NULL),(38,'13312345678','测试用户3','$2a$10$mSf8gME0Fg9VLf7JUjVRnu/gjwx6FVVTrTiOefe.fwC8BvTbtfhNG',0,'1995-05-15','http://example.com/avatar.jpg','2025-07-14 13:02:29','2025-07-14 13:02:29',0,0,0,'2025-07-14 13:02:29',NULL),(40,'15812345678','测试用户A','$2a$10$tT3i1TfZFrSaVDdWnasGzuKHBDqx7RRDfdWdlYXf9srwCEL4tkcNa',0,'2000-01-01','/profile/avatar/default.jpg','2025-07-14 15:04:50','2025-07-14 15:04:50',0,0,0,'2025-07-14 15:04:50',NULL),(41,'15912345678','测试用户B','$2a$10$FBAOgnXEUVT98yEX9ZJphe9I.iBMdqL557pcHOlo2hdJiSb9UUgvK',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-14 15:17:58','2025-07-14 15:17:58',0,0,0,'2025-07-14 15:17:58',NULL),(42,'18888888854','Admin111','$2a$10$ojDqTVL2WKL.VJXZHhgcX.HTpe.04/EQCDBRFCCCl51hJjGbykfke',0,'1999-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/bb635cf143c246549893f47edee21d3c.gif','2025-07-15 17:52:05','2025-07-15 17:52:05',0,0,0,'2025-07-15 17:52:05',NULL),(43,'18645646456','Admin12344','$2a$10$bZggCaKWLCtJfCetdhiQLui5YLpLL6sm/3bf.Nc60ZLdD6qOsehVa',0,'2020-06-06','/static/touxiang.svg','2025-07-15 17:56:01','2025-07-15 17:56:01',1,0,0,'2025-07-15 17:56:01',NULL),(44,'18867465646','Admin12ggs','$2a$10$Wi3DyyKNcgvzVXMnxF.2xejad5wSfcPGX8M462rogK.3KdqMep9yy',0,'2020-06-06','/static/touxiang.svg','2025-07-15 17:59:00','2025-07-15 17:59:00',1,0,0,'2025-07-15 17:59:00',NULL),(45,'18867465647','adAda大','$2a$10$Eb1nOdSdDtd2E2B9CMuafO4kHg12y5a0rpJcrJ2BQd0TfxuAoLB6.',0,'2007-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/6b515c75441947e188ea89c24cba6f0c.gif','2025-07-15 18:06:25','2025-07-15 18:06:25',0,0,0,'2025-07-15 18:06:25',NULL),(46,'18867465648','Amlong','$2a$10$j82Lm1crDzYYI8RvTDyLOeUllwsI.NCBWyy5yjdz.IxsteO3NhABC',0,'2007-07-01','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/338c2e24c29e4854b042eb133e55b55d.gif','2025-07-15 18:11:39','2025-07-15 18:11:39',0,0,0,'2025-07-15 18:11:39',NULL),(47,'18867465641','ALON','$2a$10$bXadtlDC5zRl9P0iNxyer.RkEmVopZJXCoVCV2sa8owM3As2KWfxy',0,'2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/41adb1f57cca43b78a059b7aed071ce7.gif','2025-07-15 18:12:32','2025-07-15 18:12:32',0,0,0,'2025-07-15 18:12:32',NULL),(48,'18466456456','aLONG','$2a$10$TUtKwwxf7uNLeTCRMAP2JO8218DQDcByAG2sLw7t6LRn4C3LDpQu2',0,'2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/08c9a897ffe2418c931ba0d689b37ea7.gif','2025-07-15 18:13:41','2025-07-15 18:13:41',0,0,0,'2025-07-15 18:13:41',NULL),(49,'18466456457','Admin122','$2a$10$BbR44/iD5K7jUdL3v1YCfeaMSL.IpgTLkZTYhqgq22Rxd0GeQXVAq',0,'2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/f3d65bb312dc4e0e8ef96d8a2e37cb61.gif','2025-07-15 18:14:36','2025-07-15 18:14:36',0,0,0,'2025-07-15 18:14:36',NULL),(50,'18466456458','Ad2e24','$2a$10$lxuO3GuJjZHn8P8MLhLUI.9DekAgneGZYtWROoKsVqqCPIj03Wj.u',0,'2000-06-06','https://122.228.237.118:53627/profile/temp/avatar/2025/07/15/6163920d62424efa925e202df1102126.gif','2025-07-15 18:15:38','2025-07-15 18:15:38',0,0,0,'2025-07-15 18:15:38',NULL),(51,'18466456451','Adminuu','$2a$10$aq2XqtYhQyUen3qU317Z/Oa7XkCNcsdCADtwHKeZj48Gsu7Zcu2gG',0,'2000-06-06','/static/touxiang.svg','2025-07-15 18:20:32','2025-07-15 18:20:32',0,0,0,'2025-07-15 18:20:32',NULL),(52,'15822345678','测试用户D','$2a$10$IwZGtrYm0kgiBKtF3DJIHulRSHPyhc/6.n78YeyTyF7IKHy4WR5OW',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-15 19:45:11','2025-07-15 19:45:11',0,0,0,'2025-07-15 19:45:11',NULL),(53,'15832345678','测试用户E','$2a$10$41uoVIGhPPRSq4vOOPstT.Oal5z4m4ORzADQs2DJiTBDhFu/rlEWW',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-15 20:28:09','2025-07-15 20:28:09',0,0,0,'2025-07-15 20:28:09',NULL),(54,'15842345678','非法@昵称!','$2a$10$NGA0rSdMTcT9OJuXr/ARyuFbMchYXJ30hrPMbNrmTf.UxWBeoJlhS',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-15 21:04:48','2025-07-15 21:04:48',0,0,0,'2025-07-15 21:04:48',NULL),(55,'15852345678','测试用户F','$2a$10$yshQ2mW5b0XytPnjYREZuuVFqFhHzA8anwVx5LZsbb4M5loOPxf9C',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-15 21:09:25','2025-07-15 21:09:25',0,0,0,'2025-07-15 21:09:25',NULL),(56,'15862345678','测试用户G','$2a$10$lyhwb8xwF3IeHzxFWYw12ODEToZo2r9E2ZoYM8pvGXF2mAvMN1fPG',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-15 21:11:02','2025-07-15 21:11:02',0,0,0,'2025-07-15 21:11:02',NULL),(57,'17392173121','ADmin坤','$2a$10$jwP7TN6im2FTRgoj1LHHqeG8xjPr6r/txknoMTXdYQlldL5mJJvfO',0,'2007-06-06','/static/touxiang.svg','2025-07-16 13:18:50','2025-07-16 13:18:50',0,0,0,'2025-07-16 13:18:50',NULL),(58,'17392173122','坤字','$2a$10$Z4STT4ieM2SBeS3rEnTlquwlNNJSpGFN95YpL3CyUD.aEfxUHtiEW',0,'2001-06-06','/profile/temp/avatar/2025/07/16/e5de72caba1449df9f061982aaa66d6f.gif','2025-07-16 13:23:27','2025-07-16 13:23:27',0,0,0,'2025-07-16 13:23:27',NULL),(59,'18938345353','坤字坤','$2a$10$KXdbQnZa0jUcHdAMNVHxn.g8cTDrg4Q1xukd/b0dauCpr14MxOrhq',0,'2006-06-06','/profile/temp/avatar/2025/07/16/fa835594bcc546759c2a9d37de645ee8.gif','2025-07-16 13:26:12','2025-07-16 13:26:12',0,0,0,'2025-07-16 13:26:12',NULL),(60,'18958771687','坤坤坤字','$2a$10$mgo86u6ohvEF/rIS/0QFAuv.TzRLGdNMhonyzDm6QG9oGI.LyTRIO',0,'2007-06-06','/profile/temp/avatar/2025/07/16/7183412c49e14d7eb7631df8278ff26a.gif','2025-07-16 13:34:38','2025-07-16 13:34:38',0,0,0,'2025-07-16 13:34:38',NULL),(61,'15872345678','测试用户J','$2a$10$0RuiCZsAXBTKJyJjusaZsuPOfwJWoMk/eNx6AlVJk1gF1zVSziTb2',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-16 14:06:27','2025-07-16 14:06:27',0,0,0,'2025-07-16 14:06:27',NULL),(62,'15892345678','测试用户H','$2a$10$1Bf3VqXd/B1z/uXXYCmJ0OiKdmwT3T6c7/K4FFsDW3C6UaHZ5g2Tm',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-16 14:47:32','2025-07-16 14:47:32',0,0,0,'2025-07-16 14:47:32',NULL),(63,'19877767676','Admin123','$2a$10$6Gw4srcvKR2X/LOMNpuuXeYQn3xnK58OLz8mhlwDWNmgQl9i8QYL6',0,'2000-06-06','/profile/temp/avatar/2025/07/16/c77c5fe2783a4133b62e30dee00edd03.gif','2025-07-16 14:47:54','2025-07-16 14:47:54',0,0,0,'2025-07-16 14:47:54',NULL),(64,'15882345678','测试用户K','$2a$10$GqUij8W0r.Sjz5ay6HMNquAg3QZgO9O0xWIBNYgWOh4wXo0xrk9r6',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-16 15:04:51','2025-07-16 15:04:51',0,0,0,'2025-07-16 15:04:51',NULL),(65,'18345345345','Admink','$2a$10$h21zxzMmocCzEga2awd/HuuB/an5KgzYfPA6VkbJ3IpKCFIlFMkBi',0,'2000-06-06','/profile/temp/avatar/2025/07/16/4e056d46f0264080a3bf090adc1e93ae.gif','2025-07-16 15:08:05','2025-07-16 15:08:05',0,0,0,'2025-07-16 15:08:05',NULL),(66,'18345345341','Ad坤坤','$2a$10$xJo.MLXdxTtLbcS.5ad.leyEvfhkBAPGmZxpzZvBEDNH9sHIf8Gz2',0,'2000-06-06','/profile/temp/avatar/2025/07/16/107cbc8730e842e5bf6d9035d497fa37.gif','2025-07-16 15:09:52','2025-07-16 15:09:52',0,0,0,'2025-07-16 15:09:52',NULL),(67,'15811345678','测试用户L','$2a$10$sF4nmkngYTzoqkQ.cF.5duJ7C8Jx7cUOCeRfzibCJuzI6oLTgJ/ea',0,'2000-01-01','/profile/avatar/2025/07/13/77b2ecd4934440ef856d603fc5565123.png','2025-07-16 15:16:23','2025-07-16 15:16:23',0,0,0,'2025-07-16 15:16:23',NULL),(68,'18345345342','Adkunkun','$2a$10$r.j9229KfqUeLByr1TruaOtATJDIR.rsQQ8G0oGN7em58hy4Hh5fO',0,'2000-06-06','/profile/temp/avatar/2025/07/16/8ba8e7f1617248a3a8b669fe0a4cb7b3.gif','2025-07-16 15:19:22','2025-07-16 15:19:22',0,0,0,'2025-07-16 15:19:22',NULL),(69,'18978787787','Akun了','$2a$10$tafJ6gwW.Z0Z6Uc6y21fTeFVLr5OUbzsZu0PKWo0FwWCnxfBFIGOW',0,'2000-06-06','/profile/temp/avatar/2025/07/16/c7da15020d244e4faecb979b05a46a9e.gif','2025-07-16 15:22:45','2025-07-16 15:22:45',0,0,0,'2025-07-16 15:22:45',NULL),(70,'18978787786','Admin12l','$2a$10$8SexPaRSuDesb6Iknb67du6fOxjm65OkhVe5zvdKX8q/uxGYGqjQ.',0,'2000-06-06','/profile/temp/avatar/2025/07/16/3769504e34b1463f8ebd9a7cb6c8ad27.gif','2025-07-16 15:25:51','2025-07-16 15:25:51',0,0,0,'2025-07-16 15:25:51',NULL),(71,'18654646464','力量','$2a$10$d5ghWWGkPNxAuUw6UkKFFeNZgkY5OiU4xiBJOiBrS4BkE.BgEyFxO',0,'2000-06-06','/profile/temp/avatar/2025/07/16/b27d3ed56ba6493ea0b6ba34c62be1b2.gif','2025-07-16 15:34:50','2025-07-16 15:34:50',0,0,0,'2025-07-16 15:34:50',NULL),(72,'16735353535','ADmin123l','$2a$10$cJyuo9U5pJOknijcSjPY4.fGDOi8GD0nqhdoFNhGkqYP3GJiwf4kG',0,'2000-06-06','/profile/temp/avatar/2025/07/16/121ce010f1344768a5bd80cab7aeb3e8.gif','2025-07-16 15:39:32','2025-07-16 15:39:32',0,0,0,'2025-07-16 15:39:32',NULL),(73,'18942342423','Aamin123tu','$2a$10$72.uV9IX7QJjo3gVwBSS9OC0WBEO/sOpmfrQ1LaHrRpCNLvG2WiPC',0,'2000-06-06','/profile/temp/avatar/2025/07/16/d3410a32d0d84712a82333a9daf0e740.gif','2025-07-16 15:46:48','2025-07-16 15:46:48',0,0,0,'2025-07-16 15:46:48',NULL),(74,'18943535353','A9','$2a$10$UrK7iiQCqw71FhpkMYLKHOdv8tXEYx/U4iXI7zsTqm7DK3W2ZamoS',0,'2000-06-06','/profile/temp/avatar/2025/07/16/4a5ad259f21d44a387f3e3856616cd4a.gif','2025-07-16 15:49:15','2025-07-16 15:49:15',0,0,0,'2025-07-16 15:49:15',NULL),(75,'19898987666','u1','$2a$10$5Dwg0Z8g3r5VdSwhXrRezOZ8QtCzQ/hI94N7cA87J0JbkhUgBPwzG',0,'2000-06-06','/profile/temp/avatar/2025/07/16/9c889e5d50e248b2b69fe08901728bd9.gif','2025-07-16 15:51:15','2025-07-16 15:51:15',0,0,0,'2025-07-16 15:51:15',NULL),(76,'19852525252','admin1','$2a$10$LYq9qPWejdyJldiuMgRAGeVUxsYAT7wvTWybSl9XECcpYTJbDtgqm',0,'2000-06-06','/profile/temp/avatar/2025/07/16/1782b200161147c1808b71cdd928354b.gif','2025-07-16 15:58:43','2025-07-16 15:58:43',0,0,0,'2025-07-16 15:58:43',NULL),(77,'16456464646','AD乐乐','$2a$10$vjy2M8/ozpKC0oKM4LNjCeAlJqFgYq/xKA017q9X6.mx2I0F7ZOiS',0,'2000-06-06','/profile/temp/avatar/2025/07/17/7276ca8e84ca42d2ab359334fb9114a3.gif','2025-07-17 14:38:21','2025-07-17 14:38:21',0,0,0,'2025-07-17 14:38:21',NULL),(78,'17812345678','测试用户P','$2a$10$uIQ1S.JFo.59Zg8T20YO3.mP8CcmssyEV.y1DVzlAX/gy/sNMzIwO',0,'2000-01-01','/profile/temp/2025/07/17/2c895297a4da41cb8b69e255bd74925b.gif','2025-07-17 15:53:34','2025-07-17 15:53:34',0,0,0,'2025-07-17 15:53:34',NULL),(79,'18675756755','咯','$2a$10$UVH.mlW03o1BWM2NJgKx7eyYGdZstFgoroezN6ubiIUbSET1pFl.q',0,'2007-06-06','/profile/temp/avatar/2025/07/17/0194d556b83349e089a547713ae9e65f.gif','2025-07-17 16:51:38','2025-07-17 16:51:38',0,0,0,'2025-07-17 16:51:38',NULL),(80,'18675756751','ADlele','$2a$10$/VE80bKyi1jBMRR0RM/P4OfKdVX2YTXzlWhMELvOg6OL15Pbb3ayi',0,'2000-06-06','/profile/temp/avatar/2025/07/17/29ec551436624168886dd94e825ce5e3.gif','2025-07-17 17:22:10','2025-07-17 17:22:10',0,0,0,'2025-07-17 17:22:10',NULL),(81,'16666666666','Alll','$2a$10$HKRZWycBHtmrQWFnywGq3.oSKmFPQ6S.Lw.qtH7LZull659F77GBq',0,'2000-06-06','/profile/temp/avatar/2025/07/17/9992ab34fe6741918ec67aacb7d5ff43.gif','2025-07-17 17:49:15','2025-07-17 17:49:15',0,0,0,'2025-07-17 17:49:15',NULL),(82,'13333333333','Adlllll','$2a$10$XlmPCkBNMo9bNkE1nWRRYeaxbKnwmaefYXXY2M1vrox7MoJx7tpza',0,'2006-06-06','/profile/temp/avatar/2025/07/17/d972532111a24f7f8928a72ca157fcb7.gif','2025-07-17 18:06:40','2025-07-17 18:06:40',0,0,0,'2025-07-17 18:06:40',NULL),(83,'13333333334','Adqqq','$2a$10$/MqOH/mvy7IKeOPGf8/OnuEDu1rcKGra0MV6g/P3Mo/ghxQ9/ppGC',0,'2000-06-06','/profile/temp/avatar/2025/07/17/72eb27d291904079b01a9b0f94cda7f0.gif','2025-07-17 18:10:21','2025-07-17 18:10:21',0,0,0,'2025-07-17 18:10:21',NULL),(84,'13333333335','ikunlll','$2a$10$ik0q/Ziic7p.TVUiQg4.gOnmHpvZTAz/WuzMkRHQbpaLztll/HZRe',0,'2000-06-06','/profile/temp/avatar/2025/07/17/099988c9f3784eeca46ca1c9a0cbbcf1.gif','2025-07-17 18:17:19','2025-07-17 18:17:19',0,0,0,'2025-07-17 18:17:19',NULL),(85,'13333333336','needPetInf','$2a$10$dOogYubvMEaoWNPqqGFWp.2zWzrR3Dv7VrdZk6jmBF8h7kf0rmLX2',0,'2000-06-06','/profile/temp/avatar/2025/07/17/4b1667f66f6144299454d1e14a54412f.gif','2025-07-17 18:23:13','2025-07-17 18:23:13',0,0,0,'2025-07-17 18:23:13',NULL),(86,'13333333337','Adooo','$2a$10$eK4tfTX4ngbybfyEXm3s1e9uUS3vpwZK8R6d55o2bV9038IthRd.W',0,'2000-06-06','/profile/temp/avatar/2025/07/17/a9e6cdec3c6c4cc68c729ec9b1448ff5.gif','2025-07-17 18:24:53','2025-07-17 18:24:53',0,0,0,'2025-07-17 18:24:53',NULL),(87,'17822345678','测试用户O','$2a$10$7SE6kpIgo//44OnGiLgmY.T9Y4QTw.8uCGO1S..G.5jYEOvg.6Be.',0,'2000-01-01','/profile/temp/2025/07/17/2c895297a4da41cb8b69e255bd74925b.gif','2025-07-17 19:45:51','2025-07-17 19:45:51',0,0,0,'2025-07-17 19:45:51',NULL),(88,'13333333339','ll了','$2a$10$m6HNtFqbo5pfB5NdQHlEkOhOXbSKdf8l1tPwm6iJFahM1FaeM8Bvm',0,'2000-06-06','/profile/temp/avatar/2025/07/17/57cd6637826f4d9eb76f9084e98c39bb.gif','2025-07-17 20:03:31','2025-07-17 20:03:31',0,0,0,'2025-07-17 20:03:31',NULL),(89,'13333333331','111oo','$2a$10$p.C5gED2ZSLOYEGYJjXENO8s881b3X4A05DPOQqcFIFSe3zt1B1BG',0,'2000-06-06','/profile/temp/avatar/2025/07/17/c453eaa21a3f400aa6ec93473cd4dec2.gif','2025-07-17 20:09:36','2025-07-17 20:09:36',0,0,0,'2025-07-17 20:09:36',NULL),(90,'18444444444','A000','$2a$10$T2u2obMrE29sh7CJowfK.OYdyYhCGmlV0isIEI2kO5YEbxraoQb0u',0,'2000-06-06','/profile/temp/avatar/2025/07/17/b343cd9e1e9f41df8b35eb8892c7c55a.gif','2025-07-17 20:21:00','2025-07-17 20:21:00',0,0,0,'2025-07-17 20:21:00',NULL),(91,'13333333332','smsReactiv','$2a$10$fKl1gR/nCsyra686StjMrOJmYKrrDrfEupkNTmQvRIkYDQxG0skPG',0,'2004-06-06','/profile/temp/avatar/2025/07/17/908f8946ace048de867e35781ffd8d2b.gif','2025-07-17 20:40:09','2025-07-17 20:40:09',0,0,0,'2025-07-17 20:40:09',NULL),(92,'19999999990','1234567As.','$2a$10$k4mV/um9R2fvtNF4kiduoOMSQLzYprbKdFGbHVUinrONC3GiGLtra',0,'2000-06-06','/profile/temp/avatar/2025/07/17/a2edb6fc34d94bda9fb4a96660695950.gif','2025-07-17 21:24:52','2025-07-17 21:24:52',0,0,0,'2025-07-17 21:24:52',NULL),(93,'16812345678','z半g','$2a$10$hiPevJzA9wjEhXhWDfWOf.Wnh20iLNLnHDwuJd7AFeNzmWz/oJBLe',0,'2001-02-02','/profile/temp/avatar/2025/07/17/57783208a1e24e40ad243b990cc34e90.jpg','2025-07-17 22:21:23','2025-07-17 22:21:23',0,0,0,'2025-07-17 22:21:23',NULL),(94,'16822345678','時事','$2a$10$Q8ceyB2HxAycxVE3HxfrDe.8G3o40l3Mhi9oiwyyi8FwOMQibhSTK',0,'2005-02-02','/profile/temp/avatar/2025/07/17/21151308abe84a24919c9d769f236a36.jpg','2025-07-17 22:25:24','2025-07-17 22:25:24',0,0,0,'2025-07-17 22:25:24',NULL),(95,'16855555555','，，，，，','$2a$10$Kt3273agvPFWVMuajOgQQuY5L1ZOnle5HGLEhUI1OHuLKRKulFNd.',0,'1951-02-02','/profile/temp/avatar/2025/07/17/eef92c1482094eef9aff986f7782d0a7.jpg','2025-07-17 22:53:46','2025-07-17 22:53:46',0,0,0,'2025-07-17 22:53:46',NULL),(96,'15535355353','44','$2a$10$4dFWPrHGY6euv2Uit//ur.UCCjjDLUeXfTMMXI0t8t.FNYzD3Kyo2',0,'2000-06-06','/profile/temp/avatar/2025/07/17/843dbb22b8e241d79e05b4a1ffdc5e55.gif','2025-07-17 22:56:54','2025-07-17 22:56:54',0,0,0,'2025-07-17 22:56:54',NULL),(97,'15666666666','115网盘','$2a$10$gr2c.Up.p2kVZZEIrKjnnuPczV./q42WJI3NUm1mIhJZ4YdXf1E2.',0,'1951-02-02','/profile/temp/avatar/2025/07/17/8f3612ec728940cfa8514e8aa1259fa6.jpg','2025-07-17 22:57:59','2025-07-17 22:57:59',0,0,0,'2025-07-17 22:57:59',NULL),(98,'16855556665','111','$2a$10$YLGjnKkaa94sEo7p1VpH.ufPiU4xtq5UUsxll.GcfCFiA8WLKbAt.',0,'1951-02-02','/profile/temp/avatar/2025/07/17/c66b8660e5654048bfc46bfeea40048c.jpg','2025-07-17 23:03:10','2025-07-17 23:03:10',0,0,0,'2025-07-17 23:03:10',NULL),(99,'18888888886','1111号','$2a$10$pPL8HwLsZR.RgYGu/vTjlOtaJkWWHOKiqt87bXMz/EVhsTc9CRoHy',0,'1951-02-02','/profile/temp/avatar/2025/07/17/00d134c1215f43de9bfc56d2e9f4bf2a.jpg','2025-07-17 23:08:05','2025-07-17 23:08:05',0,0,0,'2025-07-17 23:08:05',NULL),(100,'16888888888','处理','$2a$10$jwWHevdmwzBMffsiiU/TOOwsmiMc6T7m/mgdHztC5FYhgvK2BhP22',0,'1951-02-02','/profile/temp/avatar/2025/07/17/af03a8cb93574f6c9b72b4a5c72813cb.jpg','2025-07-17 23:21:05','2025-07-17 23:21:05',0,0,0,'2025-07-17 23:21:05',NULL),(101,'15555555557','I坤','$2a$10$8UO5b.xGyHwxdXbyCq0ucuipjLhGh1i1VX/EpFNQJ0G2Ir8PVJQnm',0,'2000-06-06','/profile/temp/avatar/2025/07/18/509682b970714c78912bf0e4823e9d91.jpeg','2025-07-18 13:13:32','2025-07-18 13:13:32',0,0,0,'2025-07-18 13:13:32',NULL),(102,'15555555556','Ad00','$2a$10$uQ464AA/zqczcCAeAxuJruS2uIKpaFYYdQxPaHf3Hzyt0TXQB/AO2',0,'2000-06-06','/profile/temp/avatar/2025/07/18/c292329c60bf4e70af2c061162fbba06.jpeg','2025-07-18 13:17:32','2025-07-18 13:17:32',0,0,0,'2025-07-18 13:17:32',NULL),(103,'15566666666','。，，，','$2a$10$GxuZ/UgHMKnKsxb/.B80VuoDNjXFEpEf9XnjCiSSlTdx4hYP7gtaK',0,'2000-06-06','/profile/temp/avatar/2025/07/18/bffe531699f24247a43f18b8baab0590.jpg','2025-07-18 19:47:29','2025-07-18 19:47:29',0,0,0,'2025-07-18 19:47:29',NULL),(104,'16588888888','ia','$2a$10$SnC2HSmQPAz/SZKVfhpvtemZbrSGwfhRVdw5eujSLEWTFt.swIXC6',0,'2000-06-06','/profile/temp/avatar/2025/07/18/a04520a74864434994fecee8ddb96ab8.gif','2025-07-18 19:51:43','2025-07-18 19:51:43',0,0,0,'2025-07-18 19:51:43',NULL),(105,'17813345678','张三无','$2a$10$NDPm50OrxmNabK1qo7A1KeV6VbF7192eB3e0D/VXYi5ewrXd/sgPa',0,'2000-06-06','/profile/temp/avatar/2025/07/18/8bea37f2afbf4ccdac9e3f09181e78b6.jpg','2025-07-18 20:24:41','2025-07-18 20:24:41',0,0,0,'2025-07-18 20:24:41',NULL),(106,'17832345678','张三岁','$2a$10$VgzY1lozgdjbCKlGE8UOnugdkG3nR1QHLcn1gapWfWkegtbS4Pmd2',0,'2000-06-06','/profile/temp/avatar/2025/07/18/3b6ffae493ee4cd8a2e30dec68f693b1.jpg','2025-07-18 20:30:57','2025-07-18 20:30:57',0,0,0,'2025-07-18 20:30:57',NULL),(107,'17842345678','狗蛋s','$2a$10$x/Ma5KMaDJUnXkDY2rJUZeCSgrpoB2keWfBhIvMe/.vQjx0txCzuS',0,'2000-06-06','/profile/temp/avatar/2025/07/18/d4c61fac135c49498c8b72e365637777.jpg','2025-07-18 20:32:36','2025-07-18 20:32:36',0,0,0,'2025-07-18 20:32:36',NULL),(108,'17852345678','只是','$2a$10$gfzsblcC3C/3Taci2JN29OTpcc9AOwAsz5bMPUXGDVF2lTDs1dkZe',0,'2000-06-06','/profile/temp/avatar/2025/07/18/41af376e08cc44f88e5cc9e7868761a5.jpg','2025-07-18 20:35:54','2025-07-18 20:35:54',0,0,0,'2025-07-18 20:35:54',NULL),(109,'17397217433','六百六','$2a$10$jzjimpH3LaLpCZlVAzJEVOrMDMyq7bd/cVLgsmFEYF9mP6v4ZxkTK',0,'2000-06-06','/profile/temp/avatar/2025/07/18/4898874d1462427eb27b277bd2f2633c.jpg','2025-07-18 20:47:50','2025-07-18 20:47:50',0,0,0,'2025-07-18 20:47:50',NULL),(110,'17644242424','ikun','$2a$10$oPRNJ8pQWlFKMJCKCS.IbOYTS/P1dmK8hc2MjDbtSBKlMKoCfdqFK',0,'2000-06-06','/profile/temp/avatar/2025/07/18/55625b75ebb64f5da52463f2413f416c.gif','2025-07-18 20:50:17','2025-07-18 20:50:17',0,0,0,'2025-07-18 20:50:17',NULL),(111,'17776465646','Admin驱蚊','$2a$10$X7HC9B37zgB3ul.cqT.yPO/ZoQYqUlzkL4bipnmCPecdLulyoO0wa',0,'2000-06-06','/profile/temp/avatar/2025/07/18/9893a66722c841b6af23788a2d466244.gif','2025-07-18 21:08:14','2025-07-18 21:08:14',0,0,0,'2025-07-18 21:08:14',NULL),(112,'17668676876','42342424','$2a$10$OljQRcHvWWKga2HmUyRh9uU/V7uDPjqD/ErBxqg1/uGNrRCvNEAfS',0,'2000-06-06','/profile/temp/avatar/2025/07/18/ebc5033d1b0f4c3d88d80b6a1b25c77e.gif','2025-07-18 21:10:25','2025-07-18 21:10:25',0,0,0,'2025-07-18 21:10:25',NULL),(113,'18999999999','ysvjsb','$2a$10$TRQeahbWbLiJvIzZQ.pH3O/cQUPT8aJfHRRX7rtGeP2tlliGDwKou',0,'2000-06-06','/profile/temp/avatar/2025/07/18/f0e884b28e88485f9d5d3e949c166cf9.jpg','2025-07-18 21:15:33','2025-07-18 21:15:33',0,0,0,'2025-07-18 21:15:33',NULL),(114,'17862345678','长啥','$2a$10$ZugAO23G9.ZH0praDbKL6ePmgKwy22UD0NUc.E8MclTTZ8mViaIbK',0,'2000-06-06','/profile/temp/avatar/2025/07/18/37f11cb0741248f1b2f69cf14e4ceea0.jpg','2025-07-18 22:20:56','2025-07-18 22:20:56',0,0,0,'2025-07-18 22:20:56',NULL),(115,'17871234567','初五','$2a$10$EY/RBHJ0ilQkHHGc./eAiuTQcilX3LCS1yMenOTIglq1ut19M.xge',0,'2000-06-06','/profile/temp/avatar/2025/07/18/fa327704c0144374b58af13f0d660f33.jpg','2025-07-18 22:22:55','2025-07-18 22:22:55',0,0,0,'2025-07-18 22:22:55',NULL),(116,'17881234567','噢噢','$2a$10$ooOv1cCNAzSay6rhjk5j.O7MISrvBdRBp4whLBfZxpqU0LeMAp.w2',0,'2000-06-06','/profile/temp/avatar/2025/07/18/03820dbb034d4ddbb828e2d0b3afc50f.jpg','2025-07-18 22:24:26','2025-07-18 22:24:26',0,0,0,'2025-07-18 22:24:26',NULL),(117,'17912345678','张三i','$2a$10$xDDguxUfe.xrQxox0lYdNegWO.uB3reYTS7BlkNR5UuBo66YkudxC',0,'2000-06-06','/profile/temp/avatar/2025/07/18/eb13658a1fce4934a9f04fde47ef9d63.jpg','2025-07-18 22:34:15','2025-07-18 22:34:15',0,0,0,'2025-07-18 22:34:15',NULL),(118,'18912345678','偶','$2a$10$NOq7Vs24hDREWxqOuThn5.IqQLsyyYBMPO8iqFTqWuBgQO4MUi8hC',0,'2000-06-06','/profile/temp/avatar/2025/07/18/9212b54e6a2e410d8757ae562c8c8d01.jpg','2025-07-18 22:39:28','2025-07-18 22:39:28',0,0,0,'2025-07-18 22:39:28',NULL),(119,'15248117012','陈源','$2a$10$sTdPURnqZ0SiZ.v3N4FBzuejnAiKQcHI/675y6e2vp5gQ.wEa46IW',0,'2000-06-06','/profile/temp/avatar/2025/07/18/8cc32c96fcd14378ad2b03228b91cf96.jpg','2025-07-18 22:44:36','2025-07-18 22:44:36',0,0,0,'2025-07-18 22:44:36',NULL),(120,'17777777778','88u','$2a$10$v8O4NXEH4wTUOegHSfTo/.BVw4YrGy5J9SOGypVps4VJXVi3HEBQa',0,'2000-06-06','/profile/temp/avatar/2025/07/19/8cb1002713b0481bb5543d0361f988cf.gif','2025-07-19 14:58:43','2025-07-19 14:58:43',0,0,0,'2025-07-19 14:58:43',NULL),(121,'17777777534','8888','$2a$10$vER8r5TRdy2u1dCtXC1Areg5imx0R4b1sHZSpFTws6diI53MMpA3a',0,'2000-06-06','/profile/temp/avatar/2025/07/19/f504ec0d80c941a0b49014f9c229ef7c.gif','2025-07-19 14:59:47','2025-07-19 14:59:47',0,0,0,'2025-07-19 14:59:47',NULL),(122,'17777777535','11iiii','$2a$10$Ldv9EDRBDxRnrrXK7UP7keYrb6EdY0W5qsKOQi1s0AQKAntgMVwrm',0,'2000-06-06','/profile/temp/avatar/2025/07/19/f191d5ac69ce44c8a48111eb3b672d41.gif','2025-07-19 15:15:26','2025-07-19 15:15:26',0,0,0,'2025-07-19 15:15:26',NULL),(123,'15225234532','iiiik','$2a$10$/ZdqOFXq4Ne5yFRHCcumhOG/nWNgBhZLFx155wATqi4QYFCTy3JGu',0,'2003-07-19','/profile/temp/avatar/2025/07/19/25e30bcf0e4a4619a5e98da082a2f0a0.gif','2025-07-19 15:42:18','2025-07-19 15:42:18',0,0,0,'2025-07-19 15:42:18',NULL),(124,'17944534534','A0','$2a$10$KvDvOeUji.lYtNgZDjpOMufQDsm6PhcEZcUA2DSHFM9mWlH.ype4.',0,'1995-06-06','/profile/temp/avatar/2025/07/19/e4866c2ce470413da7c9d8ef28675cb5.gif','2025-07-19 15:45:12','2025-07-19 15:45:12',0,0,0,'2025-07-19 15:45:12',NULL),(125,'15225234531','888!','$2a$10$BF/0l79mmjhsiEQGbIigXe79SkjmNsCY6DNte7DkzkD1neAoF.gQm',0,'2006-06-06','/profile/temp/avatar/2025/07/19/b51a864ab59a4b42903b5b9f20fa3ac6.gif','2025-07-19 15:53:31','2025-07-19 15:53:31',0,0,0,'2025-07-19 15:53:31',NULL),(126,'18648464848','哈咯啦咯啦','$2a$10$e.TtEMXcXIx1kazOR4KQ3.c6WvVmRvbv6eHfUbQLdejGlbHImhOuu',0,'2004-06-06','/profile/temp/avatar/2025/07/19/fe20f5a2cdb94301b4c9e189cfb76e43.jpg','2025-07-19 19:04:08','2025-07-19 19:04:08',0,0,0,'2025-07-19 19:04:08',NULL),(127,'17878948488','1点','$2a$10$.ZqpiT89LzOEbW87DM1gQem15uLwAOM8tp2.5QmisvlX.SjIeCsv.',0,'2000-06-06','/profile/temp/avatar/2025/07/19/624c17eaabea4154b66944860eac6c12.jpg','2025-07-19 19:16:40','2025-07-19 19:16:40',0,0,0,'2025-07-19 19:16:40',NULL),(128,'17350254088','刚刚','$2a$10$88iRFhf.pzwiNN4eF3zcDuRYu8PdvoEZy2OYtlSjqGibUEQm3Eyai',0,'2000-06-06','/profile/temp/avatar/2025/07/19/005685d82c5f4cf992e6ba57fd6283fe.jpg','2025-07-19 19:36:49','2025-07-19 19:36:49',0,0,0,'2025-07-19 19:36:49',NULL),(129,'15060590877','0XAA','$2a$10$40Z604/3oSEpJj9Y3K/qaeqia/nJKSwgpzaCdBJUeYUY1YvmjmCke',0,'1998-06-06','/profile/temp/avatar/2025/07/20/cf5b9c4650494f7098f5954cf517d223.png','2025-07-20 11:35:05','2025-07-20 11:35:05',0,0,0,'2025-07-20 11:35:05',NULL),(130,'15060590878','bbbb','$2a$10$RbwIuMTYNm4XfWcgpqBXRODQX.9zXA//qarSSl6FBWswXCv2usj8O',0,'2000-06-06','/profile/temp/avatar/2025/07/20/f032966d86f14b4aa6ca277d834bc5d0.png','2025-07-20 11:44:51','2025-07-20 11:44:51',0,0,0,'2025-07-20 11:44:51',NULL),(131,'19812345678','那就','$2a$10$R9UGxWgW4ku/Acbw5FCRZ.njJnDQ1ABO8I1spc8lzk4Rm7sqvDENu',0,'2000-06-06','/profile/temp/avatar/2025/07/20/36a57471a8de4c6687e563a512f5c473.jpg','2025-07-20 15:18:09','2025-07-20 15:18:09',0,0,0,'2025-07-20 15:18:09',NULL),(132,'18516211116','木木','$2a$10$/A0IqaNdEhjkTlbBSebQh.nZjJ9f6Gal7TOYKT9qvs0V.CFl6iTZe',0,'2000-06-06','/profile/temp/avatar/2025/07/20/460d220766ec4cdfb707c8d03f5d3a00.jpg','2025-07-20 15:58:01','2025-07-20 15:58:01',0,0,0,'2025-07-20 15:58:01',NULL),(133,'18516211110','米','$2a$10$YQOZp.qsSDdkkuKorUHuvuZLvclpDUkODqTJiebgEwowqOAbA96d6',0,'2000-06-06','/profile/temp/avatar/2025/07/20/7026b41cdf354f99a427d0d8ac18191a.jpg','2025-07-20 16:03:10','2025-07-20 16:03:10',0,0,0,'2025-07-20 16:03:10',NULL),(134,'13924606636','小吉','$2a$10$GRgLLYZT76Uz901/Z/9DCOOT4p4exkO94.uCOiu9Ay.pvNO/A65U2',0,'2000-06-06','/profile/temp/avatar/2025/07/20/d11ef27ae0a84ce79f0bf5d80d2088c8.jpg','2025-07-20 16:41:13','2025-07-20 16:41:13',0,0,0,'2025-07-20 16:41:13',NULL),(135,'18627301115','木','$2a$10$2vZWavbLLfyjmniZ3WPHvuxUKVMcydJxsAdktIpMnbcYK.ueqDtFm',0,'2000-06-06','/profile/temp/avatar/2025/07/20/599a24f62d0c4c5284a781942f42b739.jpg','2025-07-20 17:12:46','2025-07-20 17:12:46',0,0,0,'2025-07-20 17:12:46',NULL),(136,'15060590888','aaab','$2a$10$yC5KwS6vLpub11XIa8QZ5OdlAQxqjm7oDbkhJswBL9YnPh4ekdPAW',0,'2000-06-06','/profile/temp/avatar/2025/07/20/1e81c646d7674a77855effce1846a497.png','2025-07-20 21:07:53','2025-07-20 21:07:53',0,0,0,'2025-07-20 21:07:53',NULL),(137,'15060590777','bbbbb','$2a$10$846x.Fu9QJAFB3GHz7CCzOzqpnOgMm4NxIfx8CEL0lFAc/HXdwc6.',0,'2000-06-06','/profile/temp/avatar/2025/07/20/5953f38de06a450fa871a1242955df09.png','2025-07-20 21:16:27','2025-07-20 21:16:27',0,0,0,'2025-07-20 21:16:27',NULL),(138,'18225782917','杨攀','$2a$10$j4PrbZEwFTVTranBhDCP7OGjDlXcu1QnL69Wm.VCKEsyEoXMT/ETG',0,'2000-06-08','/profile/temp/avatar/2025/07/20/9627b873050641c1a82b0b5a37df4f7c.jpg','2025-07-20 22:55:58','2025-07-20 22:55:58',0,0,0,'2025-07-20 22:55:58',NULL),(139,'17555555555','Admin22','$2a$10$7Ts3UBeX37l2GCJvR6OmJeeLHAg8eGnAS6n96F4Zoqmji3swe001.',0,'2007-06-06','/profile/temp/avatar/2025/07/21/b855e1dd9427495f8ad24341d971b1e3.gif','2025-07-21 15:06:55','2025-07-21 15:06:55',0,0,0,'2025-07-21 15:06:55',NULL),(140,'17777777777','1','$2a$10$NaoREoVr0E3/6doqaqA4bOF074RC7MCvHAJlbwSe6iVuqcjSahKP2',0,'2000-06-06','/profile/temp/avatar/2025/07/21/f7870318b0a64746affd9930a599ada4.gif','2025-07-21 15:08:55','2025-07-21 15:08:55',0,0,0,'2025-07-21 15:08:55',NULL),(141,'18777777777','qqqq1','$2a$10$3ilhAKUZePTpCdhWzNuKl.LCzFpVgD.FFdtOQk6j1/IlE2n6zF4H2',0,'2000-06-06','/profile/temp/avatar/2025/07/21/d98942139dbf4b2089140510262a1927.gif','2025-07-21 15:53:25','2025-07-21 15:53:25',0,0,0,'2025-07-21 15:53:25',NULL),(142,'18666666666','771','$2a$10$dFekYxomZ5Fimcq38jRj6.wfHYYjnIMIwL8ijiZRmNjPrwKlCIkAW',0,'2000-06-06','/profile/temp/avatar/2025/07/21/873bf10571df4f3ca0ea9702387b0367.jpg','2025-07-21 15:58:51','2025-07-21 15:58:51',0,0,0,'2025-07-21 15:58:51',NULL),(143,'18712345678','几','$2a$10$DkAUtrJlZnyuiBVNStRjGu4TIksRH6CqSh/UBrqNKCc/crHKxscq2',0,'2000-06-06','/profile/temp/avatar/2025/07/21/a6b8bfb7f9a6458dbd50700c84241664.jpg','2025-07-21 20:12:24','2025-07-21 20:12:24',0,0,0,'2025-07-21 20:12:24',NULL),(144,'18722345678','腻','$2a$10$AIFTN2dl351dG4xQS5bPSOpazTVW3L9SJpDczQELAdQJsLblb1Pq.',0,'2000-06-06','/profile/temp/avatar/2025/07/21/62f97d61ec6d41d0ac4ef115784a5a34.jpg','2025-07-21 22:12:37','2025-07-21 22:12:37',0,0,0,'2025-07-21 22:12:37',NULL),(145,'13624606636','二哈哈','$2a$10$e876oX/YcOhYRFZ2nbXNn.DLRYlLmTHLBem3/febmr9XleShBQtxS',0,'1995-10-06','/profile/temp/avatar/2025/07/22/f6a9845738dc46d49f48253a0177ce08.jpg','2025-07-22 20:52:36','2025-07-22 20:52:36',0,0,0,'2025-07-22 20:52:36',NULL),(146,'16620465724','匆匆','$2a$10$sdxOz2lqhkYFrgMIL.KZcOEUnLtiMmZ4sZoV2jD8S7tgHjtwAtsIG',0,'1993-07-03','/profile/temp/avatar/2025/07/22/2409cf12333440b7aa233169de34e607.jpg','2025-07-22 22:26:42','2025-07-22 22:26:42',0,0,0,'2025-07-22 22:26:42',NULL),(147,'18516211161','没姐姐','$2a$10$9bPnWyj4qJ.8vbm4cPbGdetqzCoJcxrLsq4s7tnZCPBW5fBWYdOFG',0,'2001-07-22','/profile/temp/avatar/2025/07/22/c84dde5edc9448f7aa358e9dfbc24d92.jpg','2025-07-22 22:32:31','2025-07-22 22:32:31',0,0,0,'2025-07-22 22:32:31',NULL),(148,'18752345678','一样','$2a$10$sZqFTT0G0v08oWiJQ0c5d.clC17fS.bp3ELKJjpUfqF89yFI6X/4O',0,'2000-06-06','/profile/temp/avatar/2025/07/23/e2e832efcd744a25be52f764005bb2a6.jpg','2025-07-23 15:44:08','2025-07-23 15:44:08',0,0,0,'2025-07-23 15:44:08',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-26 13:18:43
