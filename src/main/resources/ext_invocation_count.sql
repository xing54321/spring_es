/*
Navicat MySQL Data Transfer

Source Server         : www.lbing.top
Source Server Version : 50729
Source Host           : www.lbing.top:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2021-02-04 03:34:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ext_invocation_count`
-- ----------------------------
DROP TABLE IF EXISTS `ext_invocation_count`;
CREATE TABLE `ext_invocation_count` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `featureId` int(11) unsigned NOT NULL COMMENT '业务id',
  `createDate` varchar(20) NOT NULL COMMENT '创建日期',
  `updateTime` varchar(40) NOT NULL COMMENT '更新时间',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `invocationCount` int(11) unsigned NOT NULL COMMENT '调用次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ext_invocation_count
-- ----------------------------
INSERT INTO `ext_invocation_count` VALUES ('2', '62', '2021-02-04', '2021-02-04 03:34:35.010', '/exturl/1', '4');
INSERT INTO `ext_invocation_count` VALUES ('3', '66', '2021-02-04', '2021-02-04 03:34:35.010', '/exturl/1', '4');
INSERT INTO `ext_invocation_count` VALUES ('4', '66', '2021-02-04', '2021-02-04 03:34:35.010', '/resturl/1', '4');
INSERT INTO `ext_invocation_count` VALUES ('5', '66', '2021-02-04', '2021-02-04 03:34:35.010', '/resturl/2', '4');
INSERT INTO `ext_invocation_count` VALUES ('6', '62', '2021-02-04', '2021-02-04 03:34:35.010', '/resturl/3', '4');
