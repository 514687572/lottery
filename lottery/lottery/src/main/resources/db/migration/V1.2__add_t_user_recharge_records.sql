/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-08 17:43:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_user_recharge_records`
-- ----------------------------
CREATE TABLE `t_user_recharge_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `eos_price` decimal(20,4) DEFAULT NULL COMMENT 'eos价格',
  `recharge_num` int(11) DEFAULT NULL COMMENT '充值金额单位（分）',
  `recharge_type` int(11) DEFAULT NULL COMMENT '充值类型',
  `recharge_status` int(11) DEFAULT NULL COMMENT '充值状态',
  `eos_num` decimal(20,4) DEFAULT NULL COMMENT '充值eos数量',
  `time` datetime DEFAULT NULL COMMENT '充值时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_recharge_records
-- ----------------------------
