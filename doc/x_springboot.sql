CREATE DATABASE IF NOT EXISTS `yaoculture`

drop table if exists `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` bigint(20) DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

drop table if exists `collection_info`;
CREATE TABLE `collection_info` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
     `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
     `collection_uuid` varchar(64) NOT NULL COMMENT '合集uuid',
     `collection_name` varchar(64) NOT NULL COMMENT '合集名称`',
     `cover_img_url` varchar(200) DEFAULT NULL COMMENT '封面图',
     `description` varchar(200) DEFAULT NULL COMMENT '合集描述',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE KEY `collection_uuid` (`collection_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='合集信息表';

drop table if exists `scene_info`;
CREATE TABLE `scene_info` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
     `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 0-禁用 1-正常',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
     `collection_uuid` varchar(64) NOT NULL COMMENT '合集uuid',
     `scene_uuid` varchar(64) NOT NULL COMMENT '场景uuid',
     `scene_name` varchar(64) NOT NULL COMMENT '场景名称`',
     `scene_img_url` varchar(200) NOT NULL COMMENT '场景识别图',
     `ar_resource_url` varchar(200) NOT NULL COMMENT 'ar资源地址',
     `ar_resource_dimension` varchar(20) DEFAULT NULL COMMENT 'ar资源尺寸',
     `video_effect` varchar(10) DEFAULT NULL COMMENT 'ar视频类型',
     `space_param` varchar(200) DEFAULT NULL COMMENT '空间参数',
     `description` varchar(200) DEFAULT NULL COMMENT '合集描述',
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE KEY `scene_uuid` (`scene_uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='场景信息表';

drop table if exists `wx_app_info`;
CREATE TABLE `wx_app_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` bigint(20) DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',

  `app_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'app 名称',
  `app_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app id',
  `app_secret` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app secret',
  `wx_jump_param` varchar(100) DEFAULT NULL COMMENT '小程序跳转参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='微信小程序信息表';

drop table if exists `collection_app`;
CREATE TABLE `collection_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `collection_uuid` varchar(64) NOT NULL COMMENT '合集uuid',
  `app_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'app id',
  `wx_img_url` varchar(500) DEFAULT NULL COMMENT '小程序码',
  `wx_jump_param` varchar(100) DEFAULT NULL COMMENT '小程序跳转参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='合集与微信小程序关联表';

drop table if exists `access_statistics`;
CREATE TABLE `access_statistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `collection_uuid` varchar(64) NOT NULL COMMENT '合集uuid',
  `open_id` varchar(64) DEFAULT NULL COMMENT '用户openId',
  `statistic_type` varchar(20) NOT NULL COMMENT '统计类型',
  `user_ip` varchar(45) DEFAULT NULL COMMENT '用户IP地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_collection_type_time` (`collection_uuid`, `statistic_type`, `create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='访问统计表';

drop table if exists `user_history`;
CREATE TABLE `user_history` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `open_id` varchar(64) NOT NULL COMMENT '用户openId',
  `app_id` varchar(64) NOT NULL COMMENT '小程序appId',
  `collection_uuid` varchar(64) NOT NULL COMMENT '合集uuid',
  `collection_name` varchar(100) DEFAULT NULL COMMENT '合集名称',
  `access_count` int(11) DEFAULT 1 COMMENT '访问次数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '首次访问时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid_collection` (`open_id`, `collection_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户访问历史表';


INSERT INTO `yaoculture`.`t_user` (`id`, `username`, `password`, `email`, `phone`, `status`) VALUES ('1', 'user1', '$2a$10$oRUL6Z7B86L4mDfVaQzEEe9XxmfnnBOCcBmOUSVV/0XP1jIcosohe', '111124444', '13978786565', '0');
INSERT INTO `yaoculture`.`t_user` (`id`, `username`, `password`, `email`, `phone`, `status`) VALUES ('2', 'user2', '$2a$10$oRUL6Z7B86L4mDfVaQzEEe9XxmfnnBOCcBmOUSVV/0XP1jIcosohe', '111124444', '13978786565', '0');
INSERT INTO `yaoculture`.`t_user` (`id`, `username`, `password`, `email`, `phone`, `status`) VALUES ('3', 'user3', '$2a$10$oRUL6Z7B86L4mDfVaQzEEe9XxmfnnBOCcBmOUSVV/0XP1jIcosohe', '111124444', '13978786565', '0');
