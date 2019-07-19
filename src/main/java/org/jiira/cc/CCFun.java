package org.jiira.cc;


import java.util.Vector;

import org.jiira.utils.FunParameter;
import org.jiira.utils.Lang;
import org.jiira.utils.Lang.LanguageEmum;

public class CCFun {
	private static CCFun instance;

	private CCFun() {
	}

	public static CCFun getInstance() {
		if (null == instance) {
			instance = new CCFun();
		}
		return instance;
	}

	public void compile(Vector<String> protoTitles, StringBuffer[] funs, LanguageEmum[] languages, String className) {
		int len = funs.length;
		LanguageEmum language;
		StringBuffer fun;
		for (int i = 0; i < len; ++i) {
			//proto函数
			language = languages[i];
			fun = funs[i];
			DataModelFun(protoTitles, fun, language, className);
			if (language == LanguageEmum.CSharp) {
				getDataModelToByteArray(protoTitles, fun);
			}
			//物品类型函数
			ItemTypeFun(fun, language, Lang.BigEnumName, Lang.BigEnumList);
			ItemTypeFun(fun, language, Lang.SmallEnumName, Lang.SmallEnumList);
			//物品大类型枚举 转换 单条消息枚举(暂时取消 貌似没啥用)
//			bigTypeConvertSingleUpdateType(Lang.BigEnumList, fun, language, className);
		}
	}

	// 反序列化
	private void getDataModelToByteArray(Vector<String> protoTitles, StringBuffer fun) {
		fun.append("\n\t\tpublic static byte[] getDataModelToByteArray(" + Lang.ProtoEnumName
				+ " type, object model) {\n\t\t\tswitch(type){");

		String protoTitle;
		for (int i = 0; i < protoTitles.size(); ++i) {
			protoTitle = protoTitles.get(i);
			fun.append("\n\t\t\t\tcase " + Lang.ProtoEnumName + "." + protoTitle + ":{"// C#附加
																						// 解决lua
																						// 数据传递
																						// byte丢失问题
					+ "\n\t\t\t\t\treturn ((" + protoTitle + ".Builder)model).Build().ToByteArray();" + "\n\t\t\t\t}");
		}
		fun.append("\n\t\t\t}\n\t\t\treturn null;\n\t\t}");// C#附加 解决lua 数据传递
															// byte丢失问题
	}

	private void DataModelFun(Vector<String> protoTitles, StringBuffer fun, LanguageEmum language, String className) {
		StringBuilder gmd = null;
		switch (language) {
			case Java: {
				fun.append("\n\tpublic static Object getDataModel(" + Lang.ProtoEnumName
						+ " type, byte[] bytes){\n\t\ttry {\n\t\t\tswitch(type){\n");
				break;
			}
			case Lua: {
				fun.append("\n" + className + ".dataModel = {}" + "\n" + className + ".getDataModel = function(body)"
						+ "\n\tlocal model = " + className + ".dataModel[body.type]"
						+ "\n\tmodel:ParseFromString(body.context)" + "\n\treturn model" + "\nend");
				break;
			}
			case CSharp: {
				gmd = new StringBuilder("\n\t\tpublic static System.Object getDataModel(" + Lang.ProtoEnumName + " type) {"
				+ "\n\t\t\tif (dataModel.ContainsKey(type)) {" + "\n\t\t\t\treturn dataModel[type];" + "\n\t\t\t} else {"
						+ "\n\t\t\t\tswitch(type){");
				fun.append("\n\t\tprivate static Dictionary<" + Lang.ProtoEnumName
						+ ", System.Object> dataModel = new Dictionary<" + Lang.ProtoEnumName + ", object>();"
						+ "\n\t\tpublic static System.Object getDataModel("
						+ Lang.ProtoEnumName + " type, byte[] bytes){\n\t\t\tswitch(type){");
				break;
			}
		}

		String protoTitle;
		for (int i = 0; i < protoTitles.size(); ++i) {
			protoTitle = protoTitles.get(i);
			switch (language) {
				case Java: {
					fun.append("\t\t\t\tcase " + protoTitle + ":{\n\t\t\t\t\treturn " + protoTitle
							+ ".parseFrom(bytes);\n\t\t\t\t}\n");
					break;
				}
				case Lua: {
					fun.append("\n" + className + ".dataModel[" + className + "." + Lang.ProtoEnumName + "." + protoTitle
							+ "] = protobuf_pb." + protoTitle + "()");
					break;
				}
				case CSharp: {
					fun.append("\n\t\t\t\tcase " + Lang.ProtoEnumName + "." + protoTitle + ":{" + "\n\t\t\t\t\t"
							+ protoTitle + ".Builder model;" + "\n\t\t\t\t\tif(dataModel.ContainsKey(type)){"
							+ "\n\t\t\t\t\t\tmodel = (" + protoTitle + ".Builder)dataModel[type];"
							+ "\n\t\t\t\t\t\tmodel.Clear();" + "\n\t\t\t\t\t\tmodel.MergeFrom(bytes);"
							+ "\n\t\t\t\t\t} else {" + "\n\t\t\t\t\t\tmodel = " + protoTitle
							+ ".ParseFrom(bytes).ToBuilder();" + "\n\t\t\t\t\t\tdataModel.Add(type, model);"
							+ "\n\t\t\t\t\t}" + "\n\t\t\t\t\treturn model;" + "\n\t\t\t\t}");
					gmd.append("\n\t\t\t\t\tcase " + Lang.ProtoEnumName + "." + protoTitle + ":{\n\t\t\t\t\t\treturn " + protoTitle + ".CreateBuilder();\n\t\t\t\t}");
					break;
				}
			}
		}
		switch (language) {
			case Java: {
				fun.append(
						"\t\t\t}\n\t\t} catch (InvalidProtocolBufferException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t\treturn null;\n\t}\n");
				break;
			}
			case Lua: {
				break;
			}
			case CSharp: {
				fun.append("\n\t\t\t}\n\t\t\treturn null;\n\t\t}");
				gmd.append("\n\t\t\t}\n\t\t\treturn null;\n\t\t}\n\t}");
				fun.append(gmd);
				break;
			}
		}
	}

	private void ItemTypeFun(StringBuffer fun, LanguageEmum language, String EnumName,
			FunParameter[] bigEnums2) {
		FunParameter item;
		switch (language) {
			case Java: {
				fun.append("\n\tpublic static " + EnumName + " get" + EnumName + "(int itemId) {\n\t\t");
				break;
			}
			case Lua: {
				fun.append("\nCommandCollection.get" + EnumName + " = function(itemId)\n\t");
				break;
	
			}
			case CSharp: {
				fun.append("\n\t\tpublic static " + EnumName + " get" + EnumName + "(int itemId) {\n\t\t\t");
				break;
			}
		}

		for (int i = 0; i < bigEnums2.length; ++i) {
			item = bigEnums2[i];
			switch (language) {
				case Java: {
					fun.append("if (itemId >= " + item.min + " && itemId <= " + item.max + ") {\n" + "\t\t\treturn "
							+ EnumName + "." + item.type + ";\n" + "\t\t} else ");
					break;
				}
				case Lua: {
					fun.append("if itemId >= " + item.min + " and itemId <= " + item.max + " then\n"
							+ "\t\treturn CommandCollection." + EnumName + "." + item.type + "\n\telse");
					break;
	
				}
				case CSharp: {
					fun.append("if (itemId >= " + item.min + " && itemId <= " + item.max + ") {\n" + "\t\t\t\treturn "
							+ EnumName + "." + item.type + ";\n" + "\t\t\t} else ");
					break;
				}
			}
		}

		switch (language) {
			case Java: {
				fun.append("\n\t\t\treturn " + EnumName + ".None;\n\t}");
				break;
			}
			case Lua: {
				fun.append("\n\t\treturn CommandCollection." + EnumName + ".None\n\tend\nend");
				break;

			}
			case CSharp: {
				fun.append("\n\t\t\t\treturn " + EnumName + ".None;\n\t\t}");
				break;
			}
		}
	}
	
	public void bigTypeConvertSingleUpdateType(FunParameter[] bigEnumNames, StringBuffer fun, LanguageEmum language, String className){
		FunParameter param;
		switch(language){
			case Java:{
				fun.append("\n\tpublic static " + Lang.SingleUpdateTypeEnum + " get" + Lang.BigEnumName + "Convert" + Lang.SingleUpdateTypeEnum + "(" + Lang.BigEnumName + " type) {"
						+ "\n\t\tswitch(type) {");//java
				break;
			}
			case CSharp:{
				fun.append("\n\t\tpublic static " + Lang.SingleUpdateTypeEnum + " get" + Lang.BigEnumName + "Convert" + Lang.SingleUpdateTypeEnum + "(" + Lang.BigEnumName + " type) {"
						+ "\n\t\t\tswitch(type) {");
				break;
			}
			case Lua:{
				fun.append("\n" + className + ".get" + Lang.BigEnumName + "Convert" + Lang.SingleUpdateTypeEnum + " = function(type)");
				break;
			}
		}
		
		for(int i = 0; i < bigEnumNames.length; ++i){
			param = bigEnumNames[i];
			switch(language){
				case Java:{
					fun.append("\n\t\t\tcase " + param.type + ":return " + Lang.SingleUpdateTypeEnum + "." + param.type + ";");
					break;
				}
				case CSharp:{
					fun.append("\n\t\t\t\tcase " + Lang.BigEnumName + "." + param.type + ":return " + Lang.SingleUpdateTypeEnum + "." + param.type + ";");
					break;
				}
				case Lua:
			}
		}
		switch(language){
			case Java:{
				fun.append("\n\t\t\tdefault:return " + Lang.SingleUpdateTypeEnum + ".None;\n\t\t}\n\t}");
				break;
			}
			case CSharp:{
				fun.append("\n\t\t\t\tdefault:return " + Lang.SingleUpdateTypeEnum + ".None;\n\t\t\t}\n\t\t}");
				break;
			}
			case Lua:{
				fun.append("\n\treturn type\nend");
				break;
			}
		}
	}
}
