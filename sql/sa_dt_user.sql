CREATE DATABASE IF NOT EXISTS `sa_game_db`;
USE `sa_game_db`;
set names 'utf8';

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP TABLE IF EXISTS `sa_dt_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_user` (
  `userID` int(4) primary key auto_increment COMMENT '主键',
  `userName` varchar(32) COLLATE utf8mb4_unicode_ci not null COMMENT '帐号',
  `passWord` varchar(32) COLLATE utf8mb4_unicode_ci not null COMMENT '密码',
  `userHead` int(4) not null DEFAULT 0 COMMENT '头像',
  `nickName` varchar(16) COLLATE utf8mb4_unicode_ci not null DEFAULT 'none' COMMENT '昵称',
  `exp` int(4) not null DEFAULT 0 COMMENT '经验',
  `level` int(4) not null DEFAULT 0 COMMENT '级别',
  `currency` int(4) not null DEFAULT 0 COMMENT '游戏币(游戏获得的流通货币)',
  `diamond` int(4) not null DEFAULT 0 COMMENT '钻石(通过人民币获得的流通货币)',
  `win` int(4) not null DEFAULT 0 COMMENT '胜',
  `fail` int(4) not null DEFAULT 0 COMMENT '负',
  `dogfall` int(4) not null DEFAULT 0 COMMENT '平',
  `cardSkin` int(4) not null DEFAULT 0 COMMENT '卡牌皮肤',
  `dialogueSkin` int(4) not null DEFAULT 0 COMMENT '对白皮肤',
  `modelSkin` int(4) not null DEFAULT 0 COMMENT '模型皮肤',
  `popoSkin` int(4) not null DEFAULT 0 COMMENT '气泡皮肤',
  `levelGiftState` varbinary(128) not null DEFAULT 0x00000000 COMMENT '等级礼包状态 1-1024',
  `fundDiamond` bigint not null DEFAULT 0 COMMENT '钻石基金开始时间,+30',
  `endFundDiamondDay` int(4) not null DEFAULT 0 COMMENT '钻石最后领取天',
  `fundCurrency` bigint not null DEFAULT 0 COMMENT '游戏币开始时间,+30',
  `endFundCurrencyDay` int(4) not null DEFAULT 0 COMMENT '游戏币最后领取天',
  `signIn` bigint not null DEFAULT 0 COMMENT '签到日期',
  `signInCount` int(4) not null DEFAULT 0 COMMENT '签到次数',
  `missionRefreshTime` bigint not null DEFAULT 0 COMMENT '任务刷新时间',
  `guideStep` int(4) not null DEFAULT 0 COMMENT '引导步骤'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 auto_increment=1 COLLATE=utf8mb4_unicode_ci COMMENT='用户数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `sa_dt_card_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_card_group` (
  `userID` int(4) not null COMMENT '用户ID',
  `a` int(4) not null DEFAULT 0 COMMENT '卡组位置1',
  `b` int(4) not null DEFAULT 0 COMMENT '卡组位置2',
  `c` int(4) not null DEFAULT 0 COMMENT '卡组位置3',
  `d` int(4) not null DEFAULT 0 COMMENT '卡组位置4',
  `e` int(4) not null DEFAULT 0 COMMENT '卡组位置5'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='卡包数据表';

DROP TABLE IF EXISTS `sa_dt_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_card` (
  `ID` int(4) primary key auto_increment COMMENT '表ID(没用，先留着)',
  `userID` int(4) not null COMMENT '用户ID',
  `cardID` int(4) not null DEFAULT 0 COMMENT '卡牌ID',
  `equipID1` int(4) not null DEFAULT 0 COMMENT '装备1',
  `equipID2` int(4) not null DEFAULT 0 COMMENT '装备2',
  `equipID3` int(4) not null DEFAULT 0 COMMENT '装备3',
  `equipID4` int(4) not null DEFAULT 0 COMMENT '装备4',
  `equipID5` int(4) not null DEFAULT 0 COMMENT '装备5',
  `skillID` int(4) not null DEFAULT 0 COMMENT '技能槽',
  `state` boolean not null DEFAULT 0 COMMENT '卡组状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 auto_increment=1 COLLATE=utf8mb4_unicode_ci COMMENT='卡包数据表';

DROP TABLE IF EXISTS `sa_dt_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_item` (
  `userID` int(4) not null COMMENT '用户ID',
  `skinCount` int(2) not null DEFAULT 0x00000000 COMMENT '皮肤数量',
  `skinList` varbinary(1000) not null DEFAULT 0x00000000 COMMENT '皮肤列表',
  `giftCount` int(2) not null DEFAULT 0x00000000 COMMENT '抽奖礼包数量',
  `giftList` varbinary(500) not null DEFAULT 0x00000000 COMMENT '抽奖礼包列表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物品数据表';

DROP TABLE IF EXISTS `sa_dt_card_equip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_card_equip` (
  `ID` int(2) primary key auto_increment COMMENT '装备表ID',
  `userID` int(4) not null COMMENT '用户ID',
  `equipID` int(4) not null DEFAULT 0 COMMENT '装备ID',
  `state` boolean not null DEFAULT 0 COMMENT '状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 auto_increment=1 COLLATE=utf8mb4_unicode_ci COMMENT='装备数据表';

DROP TABLE IF EXISTS `sa_dt_firend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_firend` (
  `userID` int(4) not null COMMENT '用户ID',
  `firendCount` int(2) not null DEFAULT 0 COMMENT '好友数量',
  `firendList` varbinary(500) not null DEFAULT 0x00000000 COMMENT '好友列表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='好友数据表';

DROP TABLE IF EXISTS `sa_dt_mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_mission` (
  `userID` int(4) not null COMMENT '用户ID',
  `missionID` int(4) not null COMMENT '任务ID',
  `missionType` int(4) not null COMMENT '任务类型',
  `conditionCount` bigint not null COMMENT '任务条件数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务数据表';
