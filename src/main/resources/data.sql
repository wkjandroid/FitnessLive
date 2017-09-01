CREATE DATABASE /*!32312 IF NOT EXISTS*/`fitnesslive.db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fitnesslive.db`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'UID',
  `account` varchar(200) NOT NULL COMMENT '账户',
  `name` varchar(200)  DEFAULT NULL COMMENT '姓名',
  `password` varchar(200)  DEFAULT NULL COMMENT '密码',
  `gender` varchar(20)  DEFAULT "男" COMMENT '性别',
  `nickname` varchar(200)  DEFAULT NULL COMMENT '昵称',
  `email` varchar(200)  DEFAULT NULL COMMENT '邮箱',
  `idcard` varchar(200)  DEFAULT NULL COMMENT '身份证',
  `phonenum` varchar(200)  DEFAULT NULL COMMENT '手机号',
  `role` bigint(20)  DEFAULT NULL COMMENT '角色',
  `amatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `age` bigint(20) DEFAULT 0 COMMENT '年龄',
  `personalsign` VARCHAR(200) DEFAULT NULL COMMENT '个性签名',
  `islive` TINYINT DEFAULT 0 COMMENT '是否直播',
  `grade` bigint(200) DEFAULT 0 COMMENT '积分',
  `fansnum` bigint(200) DEFAULT 0 COMMENT '粉丝数',
  `attentionnum` bigint(200) DEFAULT 0 COMMENT '关注数',
  `livebigpic` VARCHAR(200) DEFAULT NULL COMMENT '直播大图',
  `createtime` DATETIME DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `fans`;

CREATE TABLE `fans` (
  `fs_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'FID',
  `fs_account` varchar(200) NOT NULL COMMENT '账户',
  `fs_nickname` varchar(200)  DEFAULT NULL COMMENT '昵称',
  `fs_phonenum` varchar(200)  DEFAULT NULL COMMENT '手机号',
  `fs_amatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `uid` bigint(20) NOT NULL COMMENT 'userid',
  PRIMARY KEY (`fs_id`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `attention`;

CREATE TABLE `attention` (
  `gz_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'FID',
  `gz_account` varchar(200) NOT NULL COMMENT '账户',
  `gz_nickname` varchar(200)  DEFAULT NULL COMMENT '昵称',
  `gz_phonenum` varchar(200)  DEFAULT NULL COMMENT '手机号',
  `gz_amatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `uid` bigint(20) NOT NULL COMMENT 'userid',
  PRIMARY KEY (`gz_id`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `livethemes`;

CREATE TABLE `livethemes` (
  `lt_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ltID',
  `lt_name` varchar(200)  COMMENT '直播风格',
  `lt_islive` TINYINT DEFAULT 0 COMMENT '是否在直播',
  `uid` bigint(20) NOT NULL COMMENT 'userid',
  PRIMARY KEY (`lt_id`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

insert  into `user`(`account`,`password`,`nickname`,`role`) values('admin','admin','admin',1);
insert  into `user`(`account`,`password`,`nickname`,`role`) values('admin_1','admin','admin-1',1);
insert  into `user`(`account`,`password`,`nickname`,`role`) values('admin_2','admin','admin_2',1);
insert  into `user`(`account`,`password`,`nickname`,`phonenum`) values('100000','123456','小灰灰','17862901468');

insert  into `fans`(`fs_account`,`fs_nickname`,`fs_phonenum`,`uid`) values('admin','admin','17862901470',2);
insert  into `attention`(`gz_account`,`gz_nickname`,`gz_phonenum`,`uid`) values('admin','admin','17862901470',2);

insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('性感',0,1);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('妖娆',0,1);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('妩媚',0,1);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('端庄',0,2);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('大方',0,2);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('端庄',0,3);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('大方',0,3);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('性感',0,4);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('妖娆',0,4);
insert  into `livethemes`(lt_name,`lt_islive`,`uid`) values('妩媚',0,4);