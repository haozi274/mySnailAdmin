/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : mylayuiadmin

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-06-14 17:09:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '定时任务名称',
  `cron` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务执行时间',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类名',
  `method_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '方法名',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `create_by` int(11) DEFAULT NULL,
  `update_by` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务';
