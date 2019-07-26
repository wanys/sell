/*
Navicat MySQL Data Transfer

Source Server         : wan
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : sell

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-26 11:10:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('6');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '当前价格,单位分',
  `product_quantity` int(11) NOT NULL COMMENT '数量',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('d156302683618549565', 'm156302683614587074', 'p3', '芒果冰', '5.00', '10', 'xxx', '2019-07-12 21:24:19', '2019-07-13 21:27:17');
INSERT INTO `order_detail` VALUES ('d156302695841582849', 'm156302695836922990', 'p2', '米饭', '5.00', '10', 'zzz', '2019-07-12 21:19:35', '2019-07-13 21:27:13');
INSERT INTO `order_detail` VALUES ('d156302719174674113', 'm156302719170450353', 'p2', '米饭', '5.00', '10', 'zzz', '2019-07-12 21:19:35', '2019-07-13 21:27:13');
INSERT INTO `order_detail` VALUES ('d156302736977761272', 'm156302736973734612', 'p2', '米饭', '2.00', '10', 'zzz', '2019-07-12 21:19:35', '2019-07-13 22:15:55');
INSERT INTO `order_detail` VALUES ('d156302760960440874', 'm156302760956420923', 'p3', '芒果冰', '5.00', '10', 'xxx', '2019-07-12 21:24:19', '2019-07-13 22:15:50');
INSERT INTO `order_detail` VALUES ('d156302760962296466', 'm156302760956420923', 'p2', '米饭', '2.00', '10', 'zzz', '2019-07-12 21:19:35', '2019-07-13 22:18:35');

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('m156302683614587074', '霜2', '18244445555', '仓前街道', '113', '50.00', '0', '0', '2019-07-13 22:07:16', '2019-07-24 16:42:57');
INSERT INTO `order_master` VALUES ('m156302695836922990', '霜3', '18244445555', '仓前街道', '113', '50.00', '0', '0', '2019-07-13 22:09:18', '2019-07-24 16:42:58');
INSERT INTO `order_master` VALUES ('m156302719170450353', '霜4', '18244445555', '仓前街道', '113', '50.00', '0', '0', '2019-07-13 22:13:11', '2019-07-24 16:43:03');
INSERT INTO `order_master` VALUES ('m156302736973734612', '霜', '18244445555', '仓前街道', '113', '20.00', '2', '0', '2019-07-13 22:16:09', '2019-07-13 22:16:09');
INSERT INTO `order_master` VALUES ('m156302760956420923', '霜', '18244445555', '仓前街道', '113', '70.00', '2', '0', '2019-07-13 22:20:09', '2019-07-15 09:35:08');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='类目表';

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('1', '热销榜单', '2', '2019-07-25 17:14:12', '2019-07-25 17:14:12');
INSERT INTO `product_category` VALUES ('2', '女生最爱', '3', '2019-07-12 21:23:31', '2019-07-12 21:23:31');
INSERT INTO `product_category` VALUES ('3', '男生喜欢', '4', '2019-07-25 17:29:21', '2019-07-25 17:29:21');
INSERT INTO `product_category` VALUES ('4', '男生喜欢', '5', '2019-07-25 17:29:41', null);
INSERT INTO `product_category` VALUES ('5', '大人喜欢', '6', '2019-07-25 17:29:59', null);

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `product_status` int(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品';

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('123456', '皮皮虾', '3.20', '5', '很好吃的虾', 'http://xxxxx.jpg', '0', '2', '2019-07-25 14:48:40', '2019-07-25 14:52:57');
INSERT INTO `product_info` VALUES ('156403833583327915', '鸡翅', '5.00', '100', '烤鸡翅', 'https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=6ba3eb6bdbc8a786aa27425c0660a258/d31b0ef41bd5ad6e93782f7b8bcb39dbb7fd3c99.jpg', '0', '2', '2019-07-25 15:05:35', '2019-07-25 15:07:15');
INSERT INTO `product_info` VALUES ('p1', '面条', '5.00', '1', '面粉做的', 'https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=3c1735c769d9f2d3341c2cbdc885e176/9c16fdfaaf51f3def36cb3429feef01f3a29793d.jpg', '0', '2', '2019-07-12 21:19:02', '2019-07-25 15:08:04');
INSERT INTO `product_info` VALUES ('p2', '米饭', '2.00', '1', '大米做的蒸熟', 'zzz', '1', '3', '2019-07-12 21:19:35', '2019-07-25 14:54:16');
INSERT INTO `product_info` VALUES ('p3', '芒果冰', '5.00', '1', '凉凉的', 'xxx', '0', '3', '2019-07-12 21:24:19', '2019-07-25 11:27:33');

-- ----------------------------
-- Table structure for seller_info
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `id` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `openid` varchar(64) NOT NULL COMMENT '微信openid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卖家信息表';

-- ----------------------------
-- Records of seller_info
-- ----------------------------
INSERT INTO `seller_info` VALUES ('1', 'admin', 'admin', 'abc', '2019-07-25 19:28:17', '2019-07-25 19:28:17');
