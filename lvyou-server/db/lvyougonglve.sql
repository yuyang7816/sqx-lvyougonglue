-- ============================================================
-- 旅游攻略系统演示库（无隐私数据）
-- 库名: lvyougonglve
-- 说明: 仅含表结构 + 开源演示数据；不含真实用户、手机号、密钥、订单隐私
-- 后台演示账号: admin / 123456
-- 生成方式: 依据服务端实体推断，非生产库导出
-- ============================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `lvyougonglve` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `lvyougonglve`;

DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` varchar(64) NOT NULL COMMENT 'uuid',
  `code` varchar(16) DEFAULT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统验证码';

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_key` varchar(64) DEFAULT NULL,
  `param_value` text,
  `status` varchar(16) DEFAULT '1',
  `remark` varchar(255) DEFAULT NULL,
  `param_type` varchar(64) DEFAULT NULL,
  `param_type_desc` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_param_key` (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `order_num` int DEFAULT 0,
  `remark` varchar(255) DEFAULT NULL,
  `del_flag` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据字典';

DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `operation` varchar(128) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `params` text,
  `time` bigint DEFAULT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT 0,
  `name` varchar(64) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `perms` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL COMMENT '0目录 1菜单 2按钮',
  `icon` varchar(64) DEFAULT NULL,
  `order_num` int DEFAULT 0,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL,
  `menu_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `salt` varchar(32) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `status` int DEFAULT 1,
  `create_user_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `inviter_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';

DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint NOT NULL,
  `token` varchar(128) NOT NULL,
  `expire_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `sex` int DEFAULT NULL,
  `age` int DEFAULT NULL,
  `open_id` varchar(128) DEFAULT NULL,
  `wx_open_id` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `apple_id` varchar(128) DEFAULT NULL,
  `sys_phone` int DEFAULT NULL,
  `status` int DEFAULT 1,
  `platform` varchar(32) DEFAULT NULL,
  `client_id` varchar(64) DEFAULT NULL,
  `user_type` int DEFAULT 0,
  `is_vip` int DEFAULT 0,
  `invitation_code` varchar(64) DEFAULT NULL,
  `superior_invitation_code` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `pic` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='App用户';

DROP TABLE IF EXISTS `user_background`;
CREATE TABLE `user_background` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `img_url` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户背景图';

DROP TABLE IF EXISTS `user_money`;
CREATE TABLE `user_money` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `money` decimal(12,2) DEFAULT 0.00,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包';

DROP TABLE IF EXISTS `user_money_details`;
CREATE TABLE `user_money_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `by_user_id` bigint DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `classify` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `money` decimal(12,2) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `relation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钱包明细';

DROP TABLE IF EXISTS `message_info`;
CREATE TABLE `message_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `content` text,
  `image` varchar(512) DEFAULT NULL,
  `is_see` int DEFAULT 0,
  `url` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `data_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息';

DROP TABLE IF EXISTS `verify_code`;
CREATE TABLE `verify_code` (
  `id` bigint NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `code` varchar(16) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码';

DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `image_url` varchar(512) DEFAULT NULL,
  `classify` int DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sort` int DEFAULT 0,
  `description` varchar(255) DEFAULT NULL,
  `state` int DEFAULT 1,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播';

DROP TABLE IF EXISTS `destination`;
CREATE TABLE `destination` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(64) DEFAULT NULL,
  `cover` varchar(512) DEFAULT NULL,
  `popular` int DEFAULT 0,
  `choiceness` int DEFAULT 0,
  `description` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sort` int DEFAULT 0,
  `album` text,
  `overseas` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='目的地';

DROP TABLE IF EXISTS `search_history`;
CREATE TABLE `search_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `keyword` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索历史';

DROP TABLE IF EXISTS `cancel_account`;
CREATE TABLE `cancel_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `content` text,
  `status` int DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='注销申请';

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `opinion` text,
  `contact` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈';

DROP TABLE IF EXISTS `sms_template`;
CREATE TABLE `sms_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `biz_code` varchar(64) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板';

DROP TABLE IF EXISTS `help_category`;
CREATE TABLE `help_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `client_type` int DEFAULT NULL,
  `sort` int DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助分类';

DROP TABLE IF EXISTS `help_article`;
CREATE TABLE `help_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `content` text,
  `sort` int DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帮助文章';

DROP TABLE IF EXISTS `travel_shop_type`;
CREATE TABLE `travel_shop_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户类型';

DROP TABLE IF EXISTS `travel_shop`;
CREATE TABLE `travel_shop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type_id` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `intro` text,
  `grade` decimal(4,1) DEFAULT NULL,
  `open_time` varchar(128) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `region` varchar(64) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `lat` decimal(12,8) DEFAULT NULL,
  `lng` decimal(12,8) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `collect_num` int DEFAULT 0,
  `img` varchar(512) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `contact_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户';

DROP TABLE IF EXISTS `travel_scenic_spot`;
CREATE TABLE `travel_scenic_spot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `img` varchar(512) DEFAULT NULL,
  `open_hours` varchar(128) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `ticket_price` decimal(12,2) DEFAULT NULL,
  `intro` text,
  `traffic` text,
  `rate` int DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `district` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='景点';

DROP TABLE IF EXISTS `travel_scenic_spot_ticket`;
CREATE TABLE `travel_scenic_spot_ticket` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `scenic_spot_id` bigint DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `name_intro` varchar(255) DEFAULT NULL,
  `sort` int DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='景点票种';

DROP TABLE IF EXISTS `travel_base_info`;
CREATE TABLE `travel_base_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `img_url` varchar(512) DEFAULT NULL,
  `status` int DEFAULT 1,
  `user_id` bigint DEFAULT NULL,
  `remark` text,
  `days` varchar(32) DEFAULT NULL,
  `kilometres` int DEFAULT NULL,
  `times` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路线基础信息';

DROP TABLE IF EXISTS `travel_route`;
CREATE TABLE `travel_route` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `base_info_id` bigint DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `number` int DEFAULT NULL,
  `remark` text,
  `scenic_spot_id` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='路线日程';

DROP TABLE IF EXISTS `travel_moments`;
CREATE TABLE `travel_moments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `content` text,
  `img` text,
  `video_url` varchar(512) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `scenic_spot_id` bigint DEFAULT NULL,
  `views` int DEFAULT 0,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `district` varchar(64) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `audit_status` int DEFAULT 0,
  `audit_reason` varchar(255) DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='动态';

DROP TABLE IF EXISTS `travel_comment`;
CREATE TABLE `travel_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `data_id` bigint DEFAULT NULL,
  `type` int DEFAULT NULL,
  `content` text,
  `content_img` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论';

DROP TABLE IF EXISTS `travel_like`;
CREATE TABLE `travel_like` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `type` int DEFAULT NULL,
  `data_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞';

DROP TABLE IF EXISTS `travel_collect`;
CREATE TABLE `travel_collect` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `type` int DEFAULT NULL,
  `data_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏';

DROP TABLE IF EXISTS `travel_follow`;
CREATE TABLE `travel_follow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `target_user_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注';

DROP TABLE IF EXISTS `travel_black_list`;
CREATE TABLE `travel_black_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `black_user_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单';

DROP TABLE IF EXISTS `travel_group_type`;
CREATE TABLE `travel_group_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `status` int DEFAULT 1,
  `remark` varchar(255) DEFAULT NULL,
  `min_people_num` int DEFAULT NULL,
  `max_people_num` int DEFAULT NULL,
  `max_amount` decimal(12,2) DEFAULT NULL,
  `min_amount` decimal(12,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群类型';

DROP TABLE IF EXISTS `travel_group_info`;
CREATE TABLE `travel_group_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `charge` decimal(12,2) DEFAULT NULL,
  `people_count` int DEFAULT 0,
  `qr_code` varchar(512) DEFAULT NULL,
  `qr_start_time` datetime DEFAULT NULL,
  `expired_days` int DEFAULT NULL,
  `expired_time` datetime DEFAULT NULL,
  `apply_condition` int DEFAULT NULL,
  `audit_status` int DEFAULT 0,
  `audit_remark` varchar(255) DEFAULT NULL,
  `status` int DEFAULT 1,
  `extended_fields` text,
  `cover_img` varchar(512) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `region` varchar(64) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` text,
  `remark` varchar(255) DEFAULT NULL,
  `views` bigint DEFAULT 0,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_by` int DEFAULT NULL,
  `update_by` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='攻略群';

DROP TABLE IF EXISTS `travel_group_user`;
CREATE TABLE `travel_group_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `group_info_id` bigint DEFAULT NULL,
  `status` int DEFAULT 1,
  `expire_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群成员';

DROP TABLE IF EXISTS `travel_concert`;
CREATE TABLE `travel_concert` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `img` varchar(512) DEFAULT NULL,
  `pic` varchar(512) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `concert_time` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `district` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='演唱会';

DROP TABLE IF EXISTS `travel_complaint`;
CREATE TABLE `travel_complaint` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `target_id` bigint DEFAULT NULL,
  `target_type` int DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `Instruction` text,
  `img_url` varchar(512) DEFAULT NULL,
  `status` int DEFAULT 0,
  `feedback` text,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投诉';

DROP TABLE IF EXISTS `travel_complaint_reason`;
CREATE TABLE `travel_complaint_reason` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reason` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='投诉原因';

DROP TABLE IF EXISTS `pay_details`;
CREATE TABLE `pay_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `platform` int DEFAULT NULL,
  `trade_type` varchar(32) DEFAULT NULL,
  `order_no` varchar(64) DEFAULT NULL,
  `trade_no` varchar(64) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT NULL,
  `business_type` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `extend` text,
  PRIMARY KEY (`id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付明细';
-- ----------------------------
-- 演示数据（无隐私）
-- ----------------------------

-- 后台管理员 admin / 123456  (Shiro Sha256Hash, salt=YzcmCZNvbXocrsz9dm8e)
INSERT INTO `sys_user` (`user_id`,`username`,`password`,`salt`,`email`,`mobile`,`status`,`create_user_id`,`create_time`,`inviter_code`) VALUES
(1,'admin','cdac762d0ba79875489f6a8b430fa8b5dfe0cdd81da38b80f02f33328af7fd4a','YzcmCZNvbXocrsz9dm8e','admin@example.com','',1,1,NOW(),NULL);

INSERT INTO `sys_role` (`role_id`,`role_name`,`remark`,`create_user_id`,`create_time`) VALUES
(1,'超级管理员','拥有全部菜单',1,NOW());

INSERT INTO `sys_user_role` (`id`,`user_id`,`role_id`) VALUES (1,1,1);

INSERT INTO `sys_menu` (`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`) VALUES
(1,0,'数据中心','home',NULL,1,'DataAnalysis',1),
(2,0,'系统管理',NULL,NULL,0,'Setting',90),
(3,2,'菜单管理','menu',NULL,1,'Menu',1),
(4,2,'角色管理','role',NULL,1,'UserFilled',2),
(5,2,'管理员列表','user',NULL,1,'Avatar',3),
(6,2,'系统配置','allocationList',NULL,1,'Tools',4),
(10,0,'用户中心',NULL,NULL,0,'User',20),
(11,10,'用户列表','userList',NULL,1,'List',1),
(12,10,'注销中心','messageZx',NULL,1,'Delete',2),
(20,0,'内容运营',NULL,NULL,0,'Picture',30),
(21,20,'首页装修','bannerList',NULL,1,'PictureFilled',1),
(22,20,'目的地管理','destList',NULL,1,'Location',2),
(23,20,'景点管理','attraction',NULL,1,'Place',3),
(24,20,'线路管理','travelProducts',NULL,1,'Guide',4),
(25,20,'演唱会管理','destination',NULL,1,'Microphone',5),
(26,20,'商户管理','merchant',NULL,1,'Shop',6),
(27,20,'动态管理','shopManage',NULL,1,'ChatDotRound',7),
(28,20,'评论记录','commentList',NULL,1,'ChatLineSquare',8),
(29,20,'帮助中心','materialsList',NULL,1,'QuestionFilled',9),
(30,20,'搜索记录','search',NULL,1,'Search',10),
(31,20,'国家管理','country',NULL,1,'Flag',11),
(40,0,'社交通讯',NULL,NULL,0,'Comment',40),
(41,40,'聊天群管理','locality',NULL,1,'ChatRound',1),
(42,40,'聊天会话','comment',NULL,1,'ChatLineRound',2),
(43,40,'聊天记录','chatRecord',NULL,1,'Document',3),
(44,40,'消息中心','message',NULL,1,'Message',4),
(45,40,'投诉中心','complaint',NULL,1,'Warning',5),
(50,0,'财务订单',NULL,NULL,0,'Wallet',50),
(51,50,'财务中心','financeList',NULL,1,'Money',1),
(52,50,'订单中心','orderCenter',NULL,1,'Tickets',2),
(53,50,'会员特权','vipPrivilege',NULL,1,'Trophy',3),
(54,50,'会员配置','coupon',NULL,1,'Ticket',4),
(60,0,'课程管理',NULL,NULL,0,'Reading',60),
(61,60,'课程管理','courseManagement',NULL,1,'Notebook',1),
(62,60,'类型配置','courseType',NULL,1,'Collection',2);

INSERT INTO `sys_role_menu` (`role_id`,`menu_id`)
SELECT 1, menu_id FROM sys_menu;

INSERT INTO `sys_config` (`param_key`,`param_value`,`status`,`remark`,`param_type`,`param_type_desc`) VALUES
('sys_server_name','旅游攻略','1','系统名称','sys','系统'),
('sys_h5_domain','http://127.0.0.1:9097/sqx_fast','1','本机访问域名(示例)','sys','系统'),
('file_storage_type','local','1','文件存储类型 local/alioss/bt','file','文件'),
('file_local_upload_path','./upload','1','本地上传目录','file','文件'),
('file_endpoint','','1','OSS Endpoint(自行填写)','file','文件'),
('file_bucket','','1','OSS Bucket(自行填写)','file','文件'),
('file_ali_oss_accessKeyId','','1','阿里云OSS AccessKey(自行填写)','file','文件'),
('file_ali_oss_secretAccessKey','','1','阿里云OSS Secret(自行填写)','file','文件'),
('file_bt_endpoint','','1','宝塔文件地址(自行填写)','file','文件'),
('sms_provider','','1','短信服务商(自行填写)','sms','短信'),
('sms_ali_access_key','','1','阿里云短信Key(自行填写)','sms','短信'),
('sms_ali_access_secret','','1','阿里云短信Secret(自行填写)','sms','短信'),
('sms_smsbao_username','','1','短信宝账号(自行填写)','sms','短信'),
('sms_smsbao_password','','1','短信宝密码(自行填写)','sms','短信'),
('biz_js_appid','','1','微信小程序AppId(自行填写)','biz','业务'),
('biz_js_secret','','1','微信小程序Secret(自行填写)','biz','业务'),
('biz_unlock_group_num','3','1','解锁群次数示例','biz','业务'),
('biz_customer_type','','1','客服类型 1企业微信 2电话 3小程序 4二维码(自行填写)','biz','业务'),
('biz_customer_phone','','1','客服电话(自行填写)','biz','业务'),
('biz_customer_qrcode','','1','客服二维码图片地址(自行填写)','biz','业务'),
('biz_customer_wx','','1','客服微信号(自行填写)','biz','业务'),
('biz_customer_link','','1','企业微信客服链接(自行填写)','biz','业务'),
('biz_customer_appid','','1','企业微信客服AppId(自行填写)','biz','业务'),
('biz_default_group_avatar','','1','默认群头像(自行填写)','biz','业务'),
('sys_keyword','','1','聊天关键词过滤(自行填写)','sys','系统'),
('map_platform','tencent','1','地图平台','map','地图'),
('map_tencent_key','','1','腾讯地图Key(自行填写)','map','地图'),
('map_tianditu_key','','1','天地图Key(自行填写)','map','地图'),
('google_map_key','','1','谷歌地图Key(自行填写)','map','地图'),
('pay_wx_app_id','','1','微信APP支付AppId(自行填写)','pay','支付'),
('pay_wx_h5_app_id','','1','微信H5支付AppId(自行填写)','pay','支付'),
('pay_wx_jsapi_app_id','','1','微信JSAPI AppId(自行填写)','pay','支付'),
('pay_wx_mch_id','','1','微信商户号(自行填写)','pay','支付'),
('pay_wx_mch_key','','1','微信商户密钥(自行填写)','pay','支付'),
('pay_ali_appid','','1','支付宝AppId(自行填写)','pay','支付'),
('pay_ali_public_key','','1','支付宝公钥(自行填写)','pay','支付'),
('pay_ali_private_key','','1','支付宝私钥(自行填写)','pay','支付'),
('pay_ali_mode','1','1','支付宝模式 1公钥 2证书','pay','支付'),
('pay_ali_sandbox','2','1','支付宝沙箱 1是 2否','pay','支付'),
('mail_host','','1','邮件服务器(自行填写)','mail','邮件'),
('mail_port','465','1','邮件端口','mail','邮件'),
('mail_send_mail','','1','发件邮箱(自行填写)','mail','邮件'),
('mail_auth_code','','1','邮箱授权码(自行填写)','mail','邮件');

INSERT INTO `destination` (`id`,`city`,`cover`,`popular`,`choiceness`,`description`,`create_time`,`update_time`,`sort`,`album`,`overseas`) VALUES
(1,'示例城市A','',1,1,'这是开源演示用的示例目的地，可在管理端修改。',NOW(),NOW(),1,NULL,0),
(2,'示例城市B','',0,1,'第二个示例目的地。',NOW(),NOW(),2,NULL,0);

INSERT INTO `travel_scenic_spot` (`id`,`name`,`img`,`open_hours`,`location`,`ticket_price`,`intro`,`traffic`,`rate`,`tags`,`create_time`,`update_time`,`province`,`city`,`district`) VALUES
(1,'示例景点公园','','08:00-18:00','示例城市A示例路1号',0.00,'开源演示景点，无真实地址隐私。','公交示例线',5,'亲子,休闲',NOW(),NOW(),'示例省','示例城市A','示例区');

INSERT INTO `travel_base_info` (`id`,`title`,`img_url`,`status`,`user_id`,`remark`,`days`,`kilometres`,`times`,`update_time`,`tags`,`create_time`) VALUES
(1,'示例两日游路线','',1,NULL,'演示路线简介','2',10,'约6小时',NOW(),'休闲',NOW());

INSERT INTO `travel_route` (`id`,`base_info_id`,`title`,`number`,`remark`,`scenic_spot_id`,`create_time`,`update_time`) VALUES
(1,1,'第一天',1,'参观示例景点','1',NOW(),NOW());

INSERT INTO `travel_shop_type` (`id`,`name`,`create_time`,`update_time`) VALUES
(1,'餐饮',NOW(),NOW()),(2,'玩乐租赁',NOW(),NOW());

INSERT INTO `travel_shop` (`id`,`type_id`,`name`,`intro`,`grade`,`open_time`,`province`,`city`,`region`,`address`,`lat`,`lng`,`tags`,`collect_num`,`img`,`phone`,`contact_name`,`create_time`,`update_time`) VALUES
(1,1,'示例商户店','演示商户，电话为占位号',4.5,'09:00-21:00','示例省','示例城市A','示例区','示例路88号',NULL,NULL,'美食',0,'','10000000000','演示联系人',NOW(),NOW());

INSERT INTO `travel_concert` (`id`,`img`,`pic`,`title`,`concert_time`,`location`,`create_time`,`update_time`,`province`,`city`,`district`) VALUES
(1,'','','示例演唱会',DATE_ADD(NOW(), INTERVAL 30 DAY),'示例场馆',NOW(),NOW(),'示例省','示例城市A','示例区');

INSERT INTO `travel_group_type` (`id`,`name`,`status`,`remark`,`min_people_num`,`max_people_num`,`max_amount`,`min_amount`,`create_time`,`update_time`) VALUES
(1,'同城攻略群',1,'演示群类型',2,500,0.00,0.00,NOW(),NOW());

INSERT INTO `travel_complaint_reason` (`id`,`reason`,`create_time`,`update_time`) VALUES
(1,'垃圾广告',NOW(),NOW()),(2,'人身攻击',NOW(),NOW()),(3,'其他',NOW(),NOW());

INSERT INTO `help_category` (`id`,`name`,`client_type`,`sort`,`create_time`,`update_time`) VALUES
(1,'常见问题',1,1,NOW(),NOW());

INSERT INTO `help_article` (`id`,`category_id`,`title`,`content`,`sort`,`create_time`,`update_time`) VALUES
(1,1,'如何开始使用','这是开源演示帮助文章，可在管理端修改。',1,NOW(),NOW());

INSERT INTO `banner` (`id`,`name`,`image_url`,`classify`,`url`,`sort`,`description`,`state`,`create_time`,`update_time`) VALUES
(1,'首页示例轮播','',1,'',1,'演示轮播',1,NOW(),NOW());

INSERT INTO `sms_template` (`id`,`biz_code`,`content`,`create_time`,`update_time`) VALUES
(1,'login','您的验证码是{code}，5分钟内有效。',NOW(),NOW());

INSERT INTO `tb_user` (`user_id`,`user_name`,`phone`,`avatar`,`sex`,`age`,`password`,`status`,`platform`,`user_type`,`is_vip`,`invitation_code`,`create_time`,`update_time`,`remark`) VALUES
(1,'演示游客','13800000000','',1,25,NULL,1,'h5',0,0,'DEMO0001',NOW(),NOW(),'开源演示用户，非真实手机号');

INSERT INTO `user_money` (`id`,`money`,`user_id`) VALUES (1,0.00,1);

SET FOREIGN_KEY_CHECKS = 1;

-- done
