-- selete from auth_user where id=189535;
begin;

INSERT INTO `auth_user` VALUES
(189535,'ShopBoss189535','123456','64116555886',1,NULL,0,'穿品如衣服的老板',NULL,'2020-11-01 09:48:24','182.86.203.201',NULL,0,
	0,'2020-11-01 09:48:24','2020-11-01 09:48:24',NULL,NULL),
(189536,'ShopBoss189536','123456','64116666886',1,NULL,0,'不穿品如衣服的老板',NULL,'2020-11-01 09:48:24','182.86.203.201',NULL,0,
	1578998,'2020-11-01 09:48:24','2020-11-01 09:48:24',NULL,NULL);

INSERT INTO `shop` VALUES
(1578998, '品如OOAMLL旗舰店', 2, '2020-11-06 13:28:27', '2020-11-08 08:01:03');

commit;

-- CREATE TABLE `auth_user` (
--                              `id` bigint NOT NULL AUTO_INCREMENT,
--                              `user_name` varchar(32) NOT NULL COMMENT '用户名',
--                              `password` varchar(128) NOT NULL,
--                              `mobile` varchar(128) DEFAULT NULL,
--                              `mobile_verified` tinyint DEFAULT NULL,
--                              `email` varchar(255) DEFAULT NULL,
--                              `email_verified` tinyint DEFAULT NULL,
--                              `name` varchar(128) DEFAULT NULL COMMENT 'y用户真实姓名',
--                              `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
--                              `last_login_time` datetime DEFAULT NULL,
--                              `last_login_ip` varchar(63) DEFAULT NULL,
--                              `open_id` varchar(128) DEFAULT NULL COMMENT '用于第三方登录',
--                              `state` tinyint DEFAULT NULL COMMENT '用户状态',
--                              `depart_id` bigint DEFAULT NULL COMMENT '用户部门，0 平台',
--                              `gmt_create` datetime NOT NULL,
--                              `gmt_modified` datetime DEFAULT NULL,
--                              `signature` varchar(500) DEFAULT NULL COMMENT '对user_name,password,mobile,email,open_id,depart_id签名',
--                              `creator_id` bigint DEFAULT NULL,
--                              PRIMARY KEY (`id`),
--                              UNIQUE KEY `auth_user_user_name_uindex` (`user_name`),
--                              UNIQUE KEY `auth_user_open_id_uindex` (`open_id`),
--                              UNIQUE KEY `auth_user_mobile_uindex` (`mobile`),
--                              UNIQUE KEY `auth_user_email_uindex` (`email`),
--                              KEY `auth_user_depart_id_index` (`depart_id`),
--                              KEY `auth_user_creator_id_index` (`creator_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=17330 DEFAULT CHARSET=utf8 COMMENT='用户';

-- CREATE TABLE `shop` (
--                         `id` bigint NOT NULL AUTO_INCREMENT,
--                         `name` varchar(64) DEFAULT NULL,
--                         `state` tinyint DEFAULT NULL,
--                         `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
--                         `gmt_modified` datetime DEFAULT NULL,
--                         PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;