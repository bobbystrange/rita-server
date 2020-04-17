drop database if exists rita;
create database if not exists rita;
use rita;

--
create table if not exists `user` (
  `id` bigint not null auto_increment,
  `ctime` bigint default null,
  `mtime` bigint default null,

  `name` varchar(255) not null,
  `password` varchar(255) default null,
  `email` varchar(255) not null,
  `first_name` varchar(255) default null,
  `last_name` varchar(255) default null,
  `birthday` varchar(10) default null,
  `gender` tinyint default 0,
  `style` varchar(63) default null,

  primary key `primary` (`id`),
  unique key `uniq_name` (`name`),
  key `email` (`email`)
) engine=InnoDB default charset=UTF8MB4;
-- show indexes from user;

create table if not exists `avatar` (
  `id` int not null auto_increment,
  `ctime` bigint default null,
  `mtime` bigint default null,

  `uid` bigint not null,
  `avatar` mediumtext default null,
  primary key `primary` (`id`),
  unique key `uniq_uid` (`uid`)
) engine=InnoDB default charset=UTF8MB4;
-- show indexes from user_avatar;

create table if not exists `post` (
  `id` bigint not null auto_increment,
  `ctime` bigint default null,
  `mtime` bigint default null,

  `uid` bigint not null,
  `name` varchar(511) not null,
  `title` varchar(511) default null,
  `style` varchar(63) default null,
  `published` bit(1) default 1,
  `favorite` bit(1) default 0,
  `summary` varchar(4095) default null,
  `content` text default null,
  primary key `primary` (`id`),
  key `uid` (`uid`),
  key `name` (`name`),
  unique key `uniq_uid_name` (`uid`, `name`)
) engine=InnoDB default charset=UTF8MB4;
-- show indexes from post;

create table if not exists `tag` (
  `id` bigint not null auto_increment,
  `ctime` bigint default null,
  `mtime` bigint default null,

  `uid` bigint not null,
  `name` varchar(255) not null,
  `favorite` bit(1) default 0,
  primary key `primary` (`id`),
  key `uid` (`uid`),
  key `name` (`name`),
  unique key `uniq_uid_name` (`uid`, `name`)
) engine=InnoDB default charset=UTF8MB4;
-- show indexes from tag;

create table if not exists `post_tag` (
  `id` bigint not null auto_increment,
  `ctime` bigint default null,
  `mtime` bigint default null,

  `uid` bigint not null,
  `pid` bigint not null,
  `tid` bigint not null,
  primary key `primary` (`id`),
  key `uid` (`uid`),
  key `pid` (`pid`),
  key `tid` (`tid`),
  unique key `uniq_pid_tid` (`pid`, `tid`)
) engine=InnoDB default charset=UTF8MB4;
-- show indexes from post_tag;

create table if not exists `deleted_post` (
  `id` bigint not null auto_increment,
  `ctime` bigint default null,
  `mtime` bigint default null,

  `uid` bigint not null,
  `name` varchar(511) not null,
  `title` varchar(511) default null,
  `style` varchar(255) default null,
  `published` bit(1) default 1,
  `favorite` bit(1) default 0,
  `summary` varchar(4095) default null,
  `content` text default null,
  primary key `primary` (`id`),
  key `uid` (`uid`),
  key `name` (`name`),
  unique key `uniq_uid_name` (`uid`, `name`)
) engine=InnoDB default charset=UTF8MB4;
-- show indexes from post;
