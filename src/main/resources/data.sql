CREATE DATABASE /*!32312 IF NOT EXISTS*/`fitnesslive.db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fitnesslive.db`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'UID',
  `account` varchar(200) NOT NULL COMMENT '账户',
  `name` varchar(200)  DEFAULT NULL COMMENT '姓名',
  `password` varchar(200)  DEFAULT NULL COMMENT '密码',
  `gender` varchar(20)  DEFAULT NULL COMMENT '性别',
  `nickname` varchar(200)  DEFAULT NULL COMMENT '昵称',
  `email` varchar(200)  DEFAULT NULL COMMENT '邮箱',
  `idcard` varchar(200)  DEFAULT NULL COMMENT '身份证',
  `phonenum` varchar(200)  DEFAULT NULL COMMENT '手机号',
  `role` bigint(20)  DEFAULT NULL COMMENT '角色',
  `amatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `age` bigint(20) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `fans`;

CREATE TABLE `fans` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'FID',
  `f_account` varchar(200) NOT NULL COMMENT '账户',
  `f_nickname` varchar(200)  DEFAULT NULL COMMENT '昵称',
  `f_phonenum` varchar(200)  DEFAULT NULL COMMENT '手机号',
  `f_amatar` varchar(200) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`f_id`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

insert  into `user`(`account`,`password`,`nickname`,`role`) values('admin','admin','admin',1);
insert  into `user`(`account`,`password`,`nickname`,`role`) values('admin_1','admin','admin-1',1);
insert  into `user`(`account`,`password`,`nickname`,`role`) values('admin_2','admin','admin_3',1);

insert  into `fans`(`f_account`,`f_nickname`,`f_phonenum`) values('admin_1','admin','17862901468');

