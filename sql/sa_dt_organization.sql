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

DROP TABLE IF EXISTS `sa_dt_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_dt_organization` (
  `orgId` int(4) not  null COMMENT '主键',
  `orgName` varchar(16) COLLATE utf8mb4_unicode_ci not null COMMENT '公会名称',
  `orgInfo` int(50) not null COMMENT '工会说明',
  `orgHeadId` int(4) not null COMMENT '会长ID',
  `orgBodyId` binary(4) not null COMMENT '管理员ID',
  `orgLegId` binary(4) not null COMMENT '会员ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='这是一个测试数据表';
/*!40101 SET character_set_client = @saved_cs_client */;



#insert into sa_dt_organization(orgName, orgInfo, orgHeadId, orgBodyId, orgLegId) value('放荡不羁爱吃肉', '呵呵呵呵', 1, 0, 0);