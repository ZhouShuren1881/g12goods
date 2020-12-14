drop database if exists g12demo;
create database g12demo;
use g12demo;

CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `create_time` datetime NOT null,
  `password` varchar(128) not null,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_new_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into customer values
(1, 'zhangsan', now(), '123456'),
(2, 'lisi', "2020-12-01 04:25:31", 'abcdef'),
(4, 'wangwu', "2020-12-06 07:35:48", '561df');

CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL DEFAULT 0,
  `customer_id` bigint not null,
  PRIMARY KEY (`id`),
  constraint orders_customer
  foreign key(customer_id) references customer(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into orders values
(101, 66, 1),(102, 128, 2),(103, 1000, 2);

create table `detail` (
	`id` bigint not null auto_increment,
    `avatar` varchar(50) not null,
    `addr` varchar(50) default null,
    `customer_id` bigint not null,
	PRIMARY KEY (`id`),
    unique key `unique_customer_id` (customer_id),
	constraint detail_customer
	foreign key(customer_id) references customer(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into detail values
(10, "1.png", null, 1),
(11, "2.png", "china", 2),
(12, "4.png", "UK", 4);

select * from customer;
select * from orders;
select * from detail;