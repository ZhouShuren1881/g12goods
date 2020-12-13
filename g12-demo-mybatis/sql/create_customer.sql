use g12demo;
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_new_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into customer values (0, 'zhangsan', now(), '123456');
insert into customer values (0, 'lisi', "2020-12-01 04:25:31", 'abcdef');
