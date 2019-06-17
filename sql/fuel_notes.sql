/*
Navicat MySQL Data Transfer

Source Server         : wangjian
Source Server Version : 50726
Source Host           : 192.168.3.8:3306
Source Database       : fuel

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-06-14 16:20:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fuel_notes
-- ----------------------------
DROP TABLE IF EXISTS `fuel_notes`;
CREATE TABLE `fuel_notes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `type` int(11) DEFAULT '0' COMMENT '类型：0普通 1订单',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：0 草稿 1已发布 2已撤回 3已删除',
  `create_by` int(11) DEFAULT NULL COMMENT '发布人',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息推送';
