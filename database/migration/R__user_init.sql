DROP TABLE IF EXISTS `t_permissions`;
CREATE TABLE `t_permissions`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `controller` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'controller地址',
  `priority` int NOT NULL DEFAULT 0 COMMENT '优先级(数字越小优先级越高)',
  `type` tinyint NOT NULL COMMENT '类型(0: 需要角色授权url、1: 无需登录的url)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10018 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限' ROW_FORMAT = DYNAMIC;

INSERT INTO `t_permissions` VALUES (10000, '平台管理端', '/api/admin/**', 0, 0, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10003, '用户', '/api/v1/**', 0, 0, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10004, 'swagger', '/swagger-ui/**', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10005, 'spring错误地址', '/error', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10006, '管理员登录', '/api/admin/sign-in', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10007, '管理员注册', '/api/admin/sign-up', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10010, '用户登录', '/api/v1/users/sign-in', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10011, '用户注册', '/api/v1/users/sign-up', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10015, '公共字典', '/api/dict/**', 0, 1, now(), now(), NULL);
INSERT INTO `t_permissions` VALUES (10017, '三方系统回调', '/api/callback/**', 0, 1, now(), now(), NULL);

DROP TABLE IF EXISTS `t_role_permissions`;
CREATE TABLE `t_role_permissions`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `permission_id` bigint NOT NULL COMMENT '权限id',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色的权限' ROW_FORMAT = DYNAMIC;

INSERT INTO `t_role_permissions` VALUES (1, 10000, 10000, now(), now(), NULL);
INSERT INTO `t_role_permissions` VALUES (4, 10003, 10003, now(), now(), NULL);

DROP TABLE IF EXISTS `t_roles`;
CREATE TABLE `t_roles`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `type` tinyint NOT NULL COMMENT '类型(0: 平台管理端、1: 商户管理端、2: 供应商管理端、3: 用户端)',
  `is_default` tinyint NOT NULL COMMENT '是否默认角色(0: 否、1: 是) 是默认角色的数据，会在用户注册时自动分配角色',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

INSERT INTO `t_roles` VALUES (10000, '平台超级管理员', 0, 1, now(), now(), NULL);
INSERT INTO `t_roles` VALUES (10003, '用户', 3, 1, now(), now(), NULL);

DROP TABLE IF EXISTS `t_user_roles`;
CREATE TABLE `t_user_roles`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `type` tinyint NOT NULL COMMENT '类型(0: 平台管理端、3: 用户端)',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted_at` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户的角色' ROW_FORMAT = DYNAMIC;

INSERT INTO `t_user_roles` VALUES (10000, 1, 10000, 0, now(), now(), NULL);

DROP TABLE IF EXISTS `sys_admins`;
CREATE TABLE `sys_admins`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '平台管理用户' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_admins` VALUES (1, 'admin', '$2a$04$fbbii0NUSE133B.YIlFlLeGrzjPSA.NuixmYJXXKLdrTDdSOMkHia', now(), now());

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted_at` varchar(255) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';

