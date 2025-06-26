/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : pet_love_app

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 26/06/2025 11:03:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `LoginID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '登录日志 ID',
  `UserID` int(11) UNSIGNED NOT NULL COMMENT '用户ID',
  `LoginMethod` tinyint(4) NOT NULL COMMENT '登录方法（0=手机号验证码登录，1=密码登录，2=本机号码一键登录，3=生物识别登录）',
  `LoginTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '登录时间',
  `LoginIP` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录IP',
  `Location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录位置',
  `DeviceModel` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备型号',
  `DefaultLocation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '常驻地',
  `LoginResult` tinyint(4) NOT NULL COMMENT '登录结果（0=成功，1=失败）',
  `FailCount` tinyint(4) NOT NULL DEFAULT 0 COMMENT '连续登录失败次数',
  `ErrorReason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录失败的错误原因',
  PRIMARY KEY (`LoginID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for pet_info
-- ----------------------------
DROP TABLE IF EXISTS `pet_info`;
CREATE TABLE `pet_info`  (
  `PetID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '宠物 ID',
  `UserID` int(11) UNSIGNED NOT NULL COMMENT '用户 ID',
  `PetNickName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宠物昵称（唯一）',
  `PetClass` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宠物分类',
  `PetBreed` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宠物品种',
  `PetGender` tinyint(4) NOT NULL DEFAULT 0 COMMENT '宠物性别(0=未设置，1=公，2=母)',
  `Sterilized` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否绝育 (0=否，1=是)',
  `PetBirthday` date NOT NULL COMMENT '宠物生日',
  `AdoptionDate` date NOT NULL COMMENT '领养/购买日期',
  `PetAvatarURL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宠物头像',
  `Status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态 (0=生效，1=已死亡，2=已注销)',
  PRIMARY KEY (`PetID`) USING BTREE,
  UNIQUE INDEX `UK_User_PetNickName`(`UserID`, `PetNickName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '宠物信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pet_info
-- ----------------------------

-- ----------------------------
-- Table structure for status_info
-- ----------------------------
DROP TABLE IF EXISTS `status_info`;
CREATE TABLE `status_info`  (
  `StatusID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '状态记录ID',
  `UserID` int(11) UNSIGNED NOT NULL COMMENT '用户ID',
  `ChangeType` tinyint(4) NOT NULL COMMENT '状态变更类型(0=注册激活，1=手动冻结，2=自动冻结，3=注销申请，4=注销完成)',
  `OldStatus` tinyint(4) NOT NULL COMMENT '变更前状态（对应User.Status）',
  `NewStatus` tinyint(4) NOT NULL COMMENT '变更后状态（对应User.Status）',
  `ChangeTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '自动生成当前时间',
  `OperatorType` tinyint(4) NOT NULL COMMENT '操作人类型 (0=系统自动，1=用户自助，2=管理员手动)',
  `Reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '记录状态变更的具体原因',
  `Proof` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '存储操作凭证（如验证码、工单编号）',
  PRIMARY KEY (`StatusID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户状态信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of status_info
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `UserID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `Phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码',
  `NickName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称（长度≤10字，唯一）',
  `Password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `Gender` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别（0=未设置，1=男，2=女）',
  `Birthday` date NOT NULL COMMENT '出生日期',
  `Avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `RegisterTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `LastLoginTime` datetime(0) NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最近登录时间',
  `Minor` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否成年（0 = 否，1= 是）',
  `Auth` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否认证（0=未认证，1=已认证）',
  `Status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0=生效，1=已注销，2=冻结）',
  PRIMARY KEY (`UserID`) USING BTREE,
  UNIQUE INDEX `UK_Phone`(`Phone`) USING BTREE,
  UNIQUE INDEX `UK_NickName`(`NickName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
