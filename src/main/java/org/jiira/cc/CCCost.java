package org.jiira.cc;

import org.jiira.utils.Lang.LanguageEmum;
import org.jiira.utils.Lang.TypeEnum;

public class CCCost {
	private static CCCost instance;
	
	private final CostModel[] costModels = {
			CostModel.n(TypeEnum.String, "Sock", "\".sock\""),
			CostModel.n(TypeEnum.String, "DB_ID_BASETABLE", "\"gamedb\""),
			CostModel.n(TypeEnum.Boolean, "EnableAutocommit", "true"),
			CostModel.n(TypeEnum.Int, "FISH_ROOM_MAX", "1000"),
			CostModel.n(TypeEnum.Int, "FISH_ROOM_USER_MAX", "4"),
			CostModel.n(TypeEnum.Int, "SOCK_TYPE_LENGTH", "2"),
			CostModel.n(TypeEnum.Int, "SOCK_CONTEXT_LENGTH", "2"),
			CostModel.n(TypeEnum.Int, "SOCK_HEAD_LENGTH", "4"),
			CostModel.n(TypeEnum.Int, "UPLEVEL", "1000"),
			CostModel.n(TypeEnum.Int, "Exp", "33"),
			CostModel.n(TypeEnum.Int, "Currency", "66"),
			CostModel.n(TypeEnum.Int, "Diamond", "88"),
			};
	private CCCost(){
	}
	
	public static CCCost getInstance(){
		if(null == instance){
			instance = new CCCost();
		}
		return instance;
	}
	
	public void compile(StringBuffer[] cost, LanguageEmum[] languages, String className){
		int len = costModels.length;
		int clen = cost.length;
		for(int i = 0; i < len; ++i){
			for(int j = 0; j < clen; ++j){
				cost[j].append(getConstWithLanguage(costModels[i], languages[j], className));
			}
		}
	}
	
	private String getConstWithLanguage(CostModel costModel, LanguageEmum language, String className) {
		switch(language){
			case Java: {
				return "\n\tpublic final static " + getTypeWitchLanguage(costModel.type, language) + " " + costModel.key + " = " + costModel.value + ";"; 
			}
			case Lua: {
				return "\n" + className + "." + costModel.key + " = " + costModel.value + ";";
			}
			case CSharp: {
				return "\n\t\tpublic const " + getTypeWitchLanguage(costModel.type, language) + " " + costModel.key + " = " + costModel.value + ";"; 
			}
		}
		return "";
	}
	public String getTypeWitchLanguage(TypeEnum type, LanguageEmum language){
		switch(type){
			case Int:{
				switch(language) {
					case Java:
					case CSharp: {
						return "int";
					}
					default:
						break;
				}
			}
			case String:{
				switch(language) {
					case Java:{
						return "String";
					}
					case CSharp: {
						return "string";
					}
					default:
						break;
				}
			}
			case Boolean:{
				switch(language) {
					case Java:{
						return "boolean";
					}
					case CSharp: {
						return "bool";
					}
					default:
						break;
				}
			}
		}
		return "";
	}
}

class CostModel {
	public String key;
	public String value;
	public TypeEnum type;
	private CostModel(TypeEnum type, String key, String value) {
		this.type = type;
		this.key = key;
		this.value = value;
	}
	public static CostModel n(TypeEnum type, String key, String value){
		return new CostModel(type, key, value);
	}
}
