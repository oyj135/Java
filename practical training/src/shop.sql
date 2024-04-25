/*
Navicat MySQL Data Transfer

Source Server         : mysq01
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2023-06-08 23:01:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `employeeName` varchar(20) DEFAULT NULL,
  `employeeJob` varchar(20) DEFAULT NULL,
  `employeePhone` int(20) DEFAULT NULL,
  `employeeIdNumber` int(30) DEFAULT NULL,
  `employeeEmail` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '李华', '员工', '1124', '42277', '2422');
INSERT INTO `employee` VALUES ('24', '王健', '收银员', '4788974', '36547891', '685947852@ak.com');
INSERT INTO `employee` VALUES ('25', '王飞', '收银员', '4789641', '47896546', '789651235@ak.com');
INSERT INTO `employee` VALUES ('26', '王婉', '超市管理', '4795621', '15793644', '684591485@ak.com');
INSERT INTO `employee` VALUES ('27', '阳杨', '超市管理', '4879654', '17483644', '789564892@ak.com');
INSERT INTO `employee` VALUES ('28', '李爱国', '收银员', '4785361', '14786996', '145963255@ak.com');

-- ----------------------------
-- Table structure for `t_goods`
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsName` varchar(20) NOT NULL,
  `goodsCode` int(20) DEFAULT NULL,
  `price` double NOT NULL,
  `count` int(11) NOT NULL,
  `goodsTypeId` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `goodsTypeId` (`goodsTypeId`) USING BTREE,
  CONSTRAINT `t_goods_ibfk_1` FOREIGN KEY (`goodsTypeId`) REFERENCES `t_goodstype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=534 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('9', '西瓜水', '6', '250', '10', '10');
INSERT INTO `t_goods` VALUES ('16', '可口可乐', '114', '3', '98', '10');
INSERT INTO `t_goods` VALUES ('20', '芒果', '1234', '10', '100', '10');
INSERT INTO `t_goods` VALUES ('21', '酷儿橙汁', '118', '4.5', '100', '10');
INSERT INTO `t_goods` VALUES ('22', '美汁源果粒橙', '119', '3.5', '100', '10');
INSERT INTO `t_goods` VALUES ('23', '康师傅绿茶', '120', '4', '100', '10');
INSERT INTO `t_goods` VALUES ('24', '康师傅冰红茶', '201', '4', '100', '10');
INSERT INTO `t_goods` VALUES ('25', '王老吉', '202', '3', '100', '10');
INSERT INTO `t_goods` VALUES ('27', '农夫山泉', '204', '2', '100', '10');
INSERT INTO `t_goods` VALUES ('28', '金银花露', '205', '5', '100', '10');
INSERT INTO `t_goods` VALUES ('29', '椰子汁', '206', '2.5', '100', '10');
INSERT INTO `t_goods` VALUES ('30', '蒙牛酸酸乳', '207', '2.5', '100', '10');
INSERT INTO `t_goods` VALUES ('32', '好丽友蛋黄派', '208', '2.5', '100', '29');
INSERT INTO `t_goods` VALUES ('33', '可比克薯片', '209', '3.5', '100', '29');
INSERT INTO `t_goods` VALUES ('34', '上好佳虾条', '310', '3.5', '100', '29');
INSERT INTO `t_goods` VALUES ('35', '绿盛牛肉粒', '311', '10', '100', '29');
INSERT INTO `t_goods` VALUES ('36', '台洲湾鱼片', '312', '5.5', '100', '29');
INSERT INTO `t_goods` VALUES ('37', '得力山楂片', '313', '4.5', '100', '29');
INSERT INTO `t_goods` VALUES ('38', '茂发西梅', '314', '5', '100', '29');
INSERT INTO `t_goods` VALUES ('39', '洽洽香瓜子', '315', '6', '100', '29');
INSERT INTO `t_goods` VALUES ('40', '恒康开心果 ', '316', '6', '100', '29');
INSERT INTO `t_goods` VALUES ('41', '上好佳薄荷糖', '317', '3', '100', '29');
INSERT INTO `t_goods` VALUES ('42', '旺仔牛奶糖', '318', '5', '100', '29');
INSERT INTO `t_goods` VALUES ('43', '清嘴柠檬含片', '319', '5.5', '100', '29');
INSERT INTO `t_goods` VALUES ('44', '王老吉润喉糖', '320', '4', '100', '29');
INSERT INTO `t_goods` VALUES ('45', '伊利牛初乳片', '440', '2', '100', '29');
INSERT INTO `t_goods` VALUES ('46', '绿箭口香糖', '443', '3', '100', '29');
INSERT INTO `t_goods` VALUES ('47', '猪肉', '445', '26.8', '100', '30');
INSERT INTO `t_goods` VALUES ('48', '牛肉', '456', '38.1', '100', '30');
INSERT INTO `t_goods` VALUES ('49', '羊肉', '475', '48.1', '100', '30');
INSERT INTO `t_goods` VALUES ('50', '鸡蛋', '356', '3.9', '100', '30');
INSERT INTO `t_goods` VALUES ('51', '大白菜', '346', '1.6', '100', '30');
INSERT INTO `t_goods` VALUES ('52', '土豆', '234', '1.4', '100', '30');
INSERT INTO `t_goods` VALUES ('53', '西红柿', '674', '2.5', '100', '30');
INSERT INTO `t_goods` VALUES ('54', '黄瓜', '234', '2.2', '100', '30');
INSERT INTO `t_goods` VALUES ('55', '芹菜', '657', '3', '100', '30');
INSERT INTO `t_goods` VALUES ('56', '茄子', '567', '2.3', '100', '30');
INSERT INTO `t_goods` VALUES ('57', '青椒', '343', '4', '100', '30');
INSERT INTO `t_goods` VALUES ('58', '白萝卜', '345', '0.8', '100', '30');
INSERT INTO `t_goods` VALUES ('59', '大葱', '222', '2', '100', '30');
INSERT INTO `t_goods` VALUES ('60', '冬瓜', '254', '1.6', '100', '30');
INSERT INTO `t_goods` VALUES ('61', '无籽西瓜', '356', '2', '100', '31');
INSERT INTO `t_goods` VALUES ('62', '青蛇果', '555', '12.8', '100', '31');
INSERT INTO `t_goods` VALUES ('63', '红富士', '556', '4.5', '100', '31');
INSERT INTO `t_goods` VALUES ('64', '哈密瓜', '557', '5.6', '100', '31');
INSERT INTO `t_goods` VALUES ('65', '柚子', '558', '6.5', '100', '31');
INSERT INTO `t_goods` VALUES ('66', '柠檬', '889', '17.8', '100', '31');
INSERT INTO `t_goods` VALUES ('67', '黄金猕猴桃', '231', '48', '100', '31');
INSERT INTO `t_goods` VALUES ('68', '牛油果', '234', '15', '100', '31');
INSERT INTO `t_goods` VALUES ('69', '菠萝', '156', '5.9', '100', '31');
INSERT INTO `t_goods` VALUES ('70', '芒果', '774', '3.8', '100', '31');
INSERT INTO `t_goods` VALUES ('71', '香蕉', '899', '3.8', '100', '31');
INSERT INTO `t_goods` VALUES ('72', '香梨', '558', '8.8', '100', '31');
INSERT INTO `t_goods` VALUES ('75', '金橘', '590', '4.9', '100', '31');
INSERT INTO `t_goods` VALUES ('76', '石榴', '597', '4.5', '100', '31');
INSERT INTO `t_goods` VALUES ('77', '红心柚', '598', '7.9', '100', '31');
INSERT INTO `t_goods` VALUES ('78', '雪莲果', '567', '4.9', '100', '31');
INSERT INTO `t_goods` VALUES ('89', '黑子雪碧', '455', '5', '1', '10');
INSERT INTO `t_goods` VALUES ('99', '绿色的果汁', '5510', '14.2', '100', '22');
INSERT INTO `t_goods` VALUES ('110', '奶草', '452', '41', '100', '10');
INSERT INTO `t_goods` VALUES ('526', '西瓜蛋糕', '12332', '100', '1', '22');
INSERT INTO `t_goods` VALUES ('527', '水', '142', '42', '421', '10');
INSERT INTO `t_goods` VALUES ('528', '水碧', '1422', '142', '142', '10');
INSERT INTO `t_goods` VALUES ('529', '芒果汁', '110', '10', '100', '10');
INSERT INTO `t_goods` VALUES ('530', '芒果汁', '110', '10', '100', '10');

-- ----------------------------
-- Table structure for `t_goodstype`
-- ----------------------------
DROP TABLE IF EXISTS `t_goodstype`;
CREATE TABLE `t_goodstype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsTypeName` varchar(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_goodstype
-- ----------------------------
INSERT INTO `t_goodstype` VALUES ('10', '饮料类');
INSERT INTO `t_goodstype` VALUES ('22', '图书类');
INSERT INTO `t_goodstype` VALUES ('29', '零食类');
INSERT INTO `t_goodstype` VALUES ('30', '蔬菜类');
INSERT INTO `t_goodstype` VALUES ('31', '水果类');

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `productName` varchar(20) NOT NULL,
  `productCode` int(20) DEFAULT NULL,
  `price` double NOT NULL,
  `orderTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('西瓜汁', '112', '3.9', '2023-06-08 15:08:44');
INSERT INTO `t_order` VALUES ('百事可乐', '117', '3', '2023-06-08 15:08:48');
INSERT INTO `t_order` VALUES ('美汁源果粒橙', '119', '3.5', '2023-06-08 15:08:50');
INSERT INTO `t_order` VALUES ('康师傅冰红茶', '201', '4', '2023-06-08 15:08:53');
INSERT INTO `t_order` VALUES ('石榴', '597', '4.5', '2023-06-08 17:27:47');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_password` varchar(20) NOT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', 'admin', '管理员');
INSERT INTO `users` VALUES ('2', 'qqq', '123', '用户');
INSERT INTO `users` VALUES ('3', '41', '111', '用户');
INSERT INTO `users` VALUES ('5', '455', '232', '用户');
INSERT INTO `users` VALUES ('6', '666', '666', '用户');
INSERT INTO `users` VALUES ('7', '123', '123', '用户');
