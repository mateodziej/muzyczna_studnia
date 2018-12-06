CREATE DATABASE  IF NOT EXISTS `muzyczna_studnia`;
USE `muzyczna_studnia`;

DROP TABLE IF EXISTS `user_artist`
DROP TABLE IF EXISTS `user_event`
DROP TABLE IF EXISTS `user_tag`
DROP TABLE IF EXISTS `stored_event`;
DROP TABLE IF EXISTS `artist`;
DROP TABLE IF EXISTS `tag`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_fm_username` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `stored_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `currency` varchar(255) DEFAULT NULL,
  `datetba` bit(1) NOT NULL,
  `datetbd` bit(1) NOT NULL,
  `genre_name` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `local_date` varchar(255) DEFAULT NULL,
  `local_time` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price_max` float NOT NULL,
  `price_min` float NOT NULL,
  `promoter_name` varchar(255) DEFAULT NULL,
  `sale_end_date` varchar(255) DEFAULT NULL,
  `sale_start_date` varchar(255) DEFAULT NULL,
  `status_code` varchar(255) DEFAULT NULL,
  `sub_genre_name` varchar(255) DEFAULT NULL,
  `ticketmaster_id` varchar(255) DEFAULT NULL,
  `timetba` bit(1) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `venue_address` varchar(255) DEFAULT NULL,
  `venue_city` varchar(255) DEFAULT NULL,
  `venue_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6ic5o6hkety408ghu8v7kghxw` (`ticketmaster_id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

CREATE TABLE `artist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r03a96lqhsb7djq2tn4rq60vn` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1wdpsed5kna2y38hnbgrnhi5b` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `user_artist` (
  `artist_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`artist_id`),
  KEY `FK22f68iny3vux2a570hq961t4w` (`artist_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `user_event` (
  `keyword` varchar(255) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `stored_event_id` bigint(20) NOT NULL,
  `user_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`stored_event_id`,`user_user_id`),
  KEY `FKj2c9rd3kpbb9guquvk94xlypc` (`user_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `user_tag` (
  `user_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`tag_id`,`user_id`),
  KEY `FKhqbypqh9kyjp3jcslfg67c6n5` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;