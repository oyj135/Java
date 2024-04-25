/*
 Navicat MySQL Data Transfer

 Source Server         : mysq01
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : bookshop

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 07/12/2023 11:29:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '作者',
  `price` decimal(11, 2) NOT NULL COMMENT '价格',
  `sales` int NOT NULL COMMENT '销量',
  `stock` int NOT NULL COMMENT '库存',
  `image_path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图片地址',
  `delflg` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '0' COMMENT '删除标记',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (13, 'java从入门到放弃', '国哥', 80.00, 10002, 12, 'static/img/java.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (14, '数据结构与算法', '严敏君', 78.50, 13, 20, 'static/img/data_structure.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (15, '怎样拐跑别人的媳妇', '龙伍', 68.00, 100006, 59, 'static/img/how.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (16, '木虚肉盖饭', '大胖', 16.00, 1012, 60, 'static/img/mood.png', '0', '2023-12-06 22:52:15', '2023-12-06 23:40:52');
INSERT INTO `t_book` VALUES (17, 'C++编程思想', '刚哥', 45.50, 14, 95, 'static/img/c.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (18, '蛋炒饭', '周星星', 9.90, 12, 53, 'static/img/ege.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (19, '赌神', '龙伍', 66.50, 125, 535, 'static/img/god.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (20, 'Java编程思想', '阳哥', 99.50, 47, 36, 'static/img/javathank.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (21, 'JavaScript从入门到精通', '婷姐', 9.90, 85, 95, 'static/img/JavaScript.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (22, 'cocos2d-x游戏编程入门', '国哥', 49.00, 52, 62, 'static/img/cocos2d.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (23, 'C语言程序设计', '谭浩强', 28.00, 52, 74, 'static/img/c2.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (24, 'Lua语言程序设计', '雷丰阳', 51.50, 48, 82, 'static/img/lua.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (25, '西游记', '罗贯中', 12.00, 19, 9999, 'static/img/xyj.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (26, '水浒传', '华仔', 33.05, 22, 88, 'static/img/w.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (27, '操作系统原理', '刘优', 133.05, 122, 188, 'static/img/cz.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (28, '数据结构 java版', '封大神', 173.15, 21, 81, 'static/img/cjava.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (29, 'UNIX高级环境编程', '乐天', 99.15, 210, 810, 'static/img/unix.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (30, 'javaScript高级编程', '国哥', 69.15, 210, 810, 'static/img/js.png', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (31, '大话设计模式', '国哥', 89.15, 20, 10, 'static/img/big.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (32, '人月神话', '刚哥', 88.15, 20, 80, 'static/img/p.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (33, '神祇', '技术哥', 200.00, 500, 1000, 'static/img/gods.jpg', '0', '2023-12-06 22:52:15', '2023-12-06 22:52:15');
INSERT INTO `t_book` VALUES (34, 'java从入门到放弃', '国哥', 80.00, 10002, 12, 'static/img/java.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (35, '数据结构与算法', '严敏君', 78.50, 13, 20, 'static/img/data_structure.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (36, '怎样拐跑别人的媳妇', '龙伍', 68.00, 100006, 59, 'static/img/how.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (37, '木虚肉盖饭', '大胖', 16.00, 1011, 61, 'static/img/mood.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (38, 'C++编程思想', '刚哥', 45.50, 14, 95, 'static/img/c.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (39, '蛋炒饭', '周星星', 9.90, 12, 53, 'static/img/ege.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (40, '赌神', '龙伍', 66.50, 125, 535, 'static/img/god.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (41, 'Java编程思想', '阳哥', 99.50, 47, 36, 'static/img/javathank.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (42, 'JavaScript从入门到精通', '婷姐', 9.90, 85, 95, 'static/img/JavaScript.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (43, 'cocos2d-x游戏编程入门', '国哥', 49.00, 52, 62, 'static/img/cocos2d.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (44, 'C语言程序设计', '谭浩强', 28.00, 52, 74, 'static/img/c2.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (45, 'Lua语言程序设计', '雷丰阳', 51.50, 48, 82, 'static/img/lua.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (46, '西游记', '罗贯中', 12.00, 19, 9999, 'static/img/xyj.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (47, '水浒传', '华仔', 33.05, 22, 88, 'static/img/w.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (48, '操作系统原理', '刘优', 133.05, 122, 188, 'static/img/cz.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (49, '数据结构 java版', '封大神', 173.15, 21, 81, 'static/img/cjava.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (50, 'UNIX高级环境编程', '乐天', 99.15, 210, 810, 'static/img/unix.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (51, 'javaScript高级编程', '国哥', 69.15, 210, 810, 'static/img/js.png', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (52, '大话设计模式', '国哥', 89.15, 20, 10, 'static/img/big.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (53, '人月神话', '刚哥', 88.15, 20, 80, 'static/img/p.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');
INSERT INTO `t_book` VALUES (54, '神祇', '技术哥', 200.00, 500, 1000, 'static/img/gods.jpg', '0', '2023-12-06 22:52:22', '2023-12-06 22:52:22');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `orderid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订单号',
  `createtime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单创建时间',
  `price` decimal(11, 2) NULL DEFAULT NULL COMMENT '订单总金额',
  `status` int NULL DEFAULT NULL COMMENT '订单状态',
  `userid` int NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`orderid`) USING BTREE,
  INDEX `userid`(`userid`) USING BTREE,
  CONSTRAINT `t_order_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('170185653216416', '2023-12-06 05:55:32', 399.96, 2, 16);
INSERT INTO `t_order` VALUES ('170187725205316', '2023-12-06 11:40:52', 16.00, 2, 16);

-- ----------------------------
-- Table structure for t_orderitem
-- ----------------------------
DROP TABLE IF EXISTS `t_orderitem`;
CREATE TABLE `t_orderitem`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goodsname` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goodscount` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品件数',
  `price` decimal(11, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `totalprice` decimal(11, 2) NULL DEFAULT NULL COMMENT '商品总价格',
  `orderid` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderid`(`orderid`) USING BTREE,
  CONSTRAINT `t_orderitem_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `t_order` (`orderid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_orderitem
-- ----------------------------
INSERT INTO `t_orderitem` VALUES (1, 'Java程序设计', '1', 99.99, 99.99, '170185653216416');
INSERT INTO `t_orderitem` VALUES (2, 'Java程序设计', '1', 99.99, 99.99, '170185653216416');
INSERT INTO `t_orderitem` VALUES (3, 'Java程序设计', '1', 99.99, 99.99, '170185653216416');
INSERT INTO `t_orderitem` VALUES (4, '数据结构与算法', '1', 99.99, 99.99, '170185653216416');
INSERT INTO `t_orderitem` VALUES (5, '木虚肉盖饭', '1', 16.00, 16.00, '170187725205316');

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `permissionid` char(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '权限ID',
  `permissionname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '权限名称',
  `permissionremarker` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限备注',
  `createdatetime` datetime NOT NULL COMMENT '创建时间',
  `updatedatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`permissionid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('M001', '图书管理', '查看，添加，删除，更新图书信息，', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_permission` VALUES ('M002', '订单管理', '查看，更新订单信息', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_permission` VALUES ('R001', '管理员管理', '系统管理员添加管理员', '2023-11-21 10:26:02', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `roleid` char(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色ID',
  `rolename` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色名称',
  `roleremarker` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色备注',
  `createdatetime` datetime NOT NULL COMMENT '创建时间',
  `updatedatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('00', '系统管理员', '用于管理整个系统，拥有所有权限', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_role` VALUES ('01', '管理员', '管理图书信息，订单信息等', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_role` VALUES ('02', '普通用户', '用户', '2023-11-21 10:26:02', NULL);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `roleid` char(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色ID',
  `permissionid` char(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '权限ID',
  `createdatetime` datetime NOT NULL COMMENT '创建时间',
  `updatedatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roleid`(`roleid`) USING BTREE,
  INDEX `permissionid`(`permissionid`) USING BTREE,
  CONSTRAINT `t_role_permission_ibfk_1` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`roleid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_role_permission_ibfk_2` FOREIGN KEY (`permissionid`) REFERENCES `t_permission` (`permissionid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES (1, '00', 'R001', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_role_permission` VALUES (2, '01', 'M001', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_role_permission` VALUES (3, '01', 'M002', '2023-11-21 10:26:02', NULL);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `registtime` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `updatetime` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', 'admin', 'admin@test.com', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user` VALUES (2, 'root', 'root', 'root@test.com', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user` VALUES (3, 'BeijingAdmin', 'BeijingAdmin', 'BeijingAdmin@test.com', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user` VALUES (4, 'ShanghaiAdmin', 'ShanghaiAdmin', 'ShanghaiAdmin@test.com', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user` VALUES (5, 'GuangzhouAdmin', 'GuangzhouAdmin', 'GuangzhouAdmin@test.com', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user` VALUES (6, 'ShenzhenAdmin', 'ShenzhenAdmin', 'ShenzhenAdmin@test.com', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user` VALUES (16, 'qwer123', 'qwer123', '666@test.com', '2023-12-06 17:52:58', NULL);
INSERT INTO `t_user` VALUES (23, 'qwer111', 'qwer111', 'qwer111@qq.com', '2023-12-06 22:17:56', NULL);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `roleid` char(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色ID',
  `createdatetime` datetime NOT NULL COMMENT '创建时间',
  `updatedatetime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 'admin', '01', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user_role` VALUES (2, 'root', '00', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user_role` VALUES (3, 'BeijingAdmin', '01', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user_role` VALUES (4, 'ShanghaiAdmin', '01', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user_role` VALUES (5, 'GuangzhouAdmin', '01', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user_role` VALUES (6, 'ShenzhenAdmin', '01', '2023-11-21 10:26:02', NULL);
INSERT INTO `t_user_role` VALUES (7, 'qwer123', '02', '2023-12-06 17:54:20', NULL);
INSERT INTO `t_user_role` VALUES (8, 'qwer111', '02', '2023-12-06 22:17:56', NULL);

SET FOREIGN_KEY_CHECKS = 1;
