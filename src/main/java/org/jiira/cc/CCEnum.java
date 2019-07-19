package org.jiira.cc;

import java.util.Vector;

import org.jiira.utils.EnumParameter;
import org.jiira.utils.Lang;
import org.jiira.utils.Lang.LanguageEmum;

public class CCEnum {
	private static CCEnum instance;
	private CCEnum() {
	}

	public static CCEnum getInstance() {
		if (null == instance) {
			instance = new CCEnum();
		}
		return instance;
	}
	
	public void compile(Vector<String> protoTitles, StringBuffer[] enums, LanguageEmum[] languages, String className) {
		int i;
		EnumParameter[] protoTitlesToEnumList = new EnumParameter[protoTitles.size()];
		for(i = 0; i < protoTitles.size(); ++i){
			protoTitlesToEnumList[i] = EnumParameter.n(protoTitles.get(i), "");
		}
		EnumParameter[] BigListToEnumList = new EnumParameter[Lang.BigEnumList.length];
		for(i = 0; i < BigListToEnumList.length; ++i){
			BigListToEnumList[i] = EnumParameter.n(Lang.BigEnumList[i].type, Lang.BigEnumList[i].annotation);
		}
		EnumParameter[] SmallListToEnumList = new EnumParameter[Lang.SmallEnumList.length];
		for(i = 0; i < SmallListToEnumList.length; ++i){
			SmallListToEnumList[i] = EnumParameter.n(Lang.SmallEnumList[i].type, Lang.SmallEnumList[i].annotation);
		}
		
		StringBuffer _enum;
		LanguageEmum language;
		int len = enums.length;
		for(i = 0; i < len; ++i){
			_enum = enums[i];
			language = languages[i];
			writeEnum(protoTitlesToEnumList, Lang.ProtoEnumName, _enum, language, className);//proto
			writeEnum(Lang.GameTypeEnumList, Lang.GameTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.RankTypeEnumList, Lang.RankTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.ChatTypeEnumList, Lang.ChatTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.AccountTypeEnumList, Lang.AccountTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.TeamTypeEnumList, Lang.TeamTypeEnum, _enum, language, className);//proto
			writeEnum(BigListToEnumList, Lang.BigEnumName, _enum, language, className);//proto
			writeEnum(SmallListToEnumList, Lang.SmallEnumName, _enum, language, className);//proto
			writeEnum(Lang.ErrorCodeEnumList, Lang.ErrorCodeEnum, _enum, language, className);//proto
			writeEnum(Lang.SingleUpdateTypeEnumList, Lang.SingleUpdateTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.BattleMessageTypeEnumList, Lang.BattleMessageTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.MissionTypeEnumList, Lang.MissionTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.MissionConditionTypeEnumList, Lang.MissionConditionTypeEnum, _enum, language, className);//proto
			writeEnum(Lang.MissionNameTypeEnumList, Lang.MissionNameTypeEnum, _enum, language, className);//proto
		}
	}
	
	private void writeEnum(EnumParameter[] parameter, String enumName, StringBuffer _enum, LanguageEmum language, String className){
		switch(language){
			case Java:{
				_enum.append("\n\tpublic static enum " + enumName + " {");
				break;
			}
			case Lua:{
				_enum.append("\n" + className + "." + enumName + " = {");
				break;
			}
			case CSharp:{
				_enum.append("\n\tpublic enum " + enumName + " {");
				break;
			}
		}
		for(int i = 0; i < parameter.length; ++i){
			switch(language){
				case Java:
				case CSharp:{
					_enum.append("\n\t\t" + parameter[i].name + ", //" + parameter[i].annotation);
					break;
				}
				case Lua:{
					_enum.append("\n\t" + parameter[i].name + " = " + i + ", -- " + parameter[i].annotation);
					_enum.append("\n\t\t" + parameter[i].name + "Str = \"" + parameter[i].name + ".Lua\",");
					break;
				}
			}
		}
		switch(language){
			case Java:
			case CSharp:{
				_enum.append("\n\t}");
				break;
			}
			case Lua:{
				_enum.append("\n}");
				break;
			}
		}
		
	}
}

