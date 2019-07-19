package org.jiira;

/**
 * 自己写的proto序列化结构（没有压缩，主要用于数据表），使用SAProtoDecode反序列化
 * 发布后，会生成一个SAProtoDecode类（lua、java、c#三个）和一个saproto.u文件
 * 各语言通过自己的SAProtoDecode解析saproto.u
 * 
 * 结构说明：
 * 	第几个表#开始行#开始列#读多少行#表名#类型&...
 * 	表名为程序读取时的key值（例如任务表mission）
 * 	类型是小类别，基于表名下，会有多种类型的数据（mission中的STDailyMission和STAchievementMission）
 * 
 * 1#0#0#3#battleMessage#STBattleMessage&
 * 1#1#0#12#card#STCard&
 * 1#0#0#6#cardSkill#STCardSkill&
 * 0#1#0#2#shop#ShopList&
 * 1#0#0#3#gift#STFreeGift&
 * 2#0#0#7#gift#STLuckyDrawGift&
 * 1#0#0#2#levelGift#STLevelGift&
 * 1#0#0#2#signIn#STSignIn&
 * 1#0#0#4#skin#STSkin&
 * 1#0#0#2#upLevel#STUpLevel&
 * 0#0#0#3#other#STOther&
 * 1#0#0#7#AICardGroup_1#STAICardGroup_1&
 * 1#0#0#8#mission#STDailyMission&
 * 2#0#0#8#mission#STAchievementMission
 */

import org.jiira.proto.SAProtoEncode;
import org.jiira.proto.SAProtoTask;

public class SAProtoMain {
	public static void main(String[] args) {
		//args = new String[]{"D:/svn/serv/CardServer/config/local/saproto.u", "D:/svn/unity/dev_0.4/Assets/Resources/Data/saproto.u", "D:/svn/serv/CardServer/src/main/java/com/game/sa/protobuf/SAProtoDecode.java", "D:/svn/unity/dev_0.4/Assets/Script/Proto/SAProtoDecode.cs", "D:/svn/unity/dev_0.4/Assets/Resources/LuaScr/com/game/sa/protobuf/SAProtoDecode.lua", "table/", "1#1#0#16#card#CardList&2#1#0#2#card#CardSkill"};
		String javaEncodePath = args[0];
		String csharpEncodePath = args[1];
		String javaDecodePath = args[2];
		String csharpDecodePath = args[3];
		String luaDecodePath = args[4];
		String tablePath = args[5];
		String tables = args[6];
		String[] table = tables.split("&");
		String[] vo;
		SAProtoEncode encode = SAProtoEncode.getInstance();
		System.out.println(tables + " : " + table.length);
		for(String str : table) {
			vo = str.split("#");
			encode.addTask(new SAProtoTask(Integer.parseInt(vo[0]), Integer.parseInt(vo[1]), Integer.parseInt(vo[2]), Integer.parseInt(vo[3]), vo[4], vo[5]));
		}
		encode.execute(javaEncodePath, csharpEncodePath, javaDecodePath, csharpDecodePath, luaDecodePath, tablePath);
		System.out.println("完毕");
	}
}
