package org.jiira.proto;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SACSharpProtoDecode implements ISAProtoDecode{
	private final String _package = "org.jiira.protobuf";
	private StringBuffer DecodeProto;
	private StringBuffer DecodeGetProto;
	private StringBuffer DecodeProtoType;
	private static SACSharpProtoDecode decode;
	public static ISAProtoDecode getInstance() {
		if(null == decode){
			decode = new SACSharpProtoDecode();
		}
		return decode;
	}
	public void start() {
		// 解析json&导出数据结构 saproto
		DecodeProto = new StringBuffer("using System.Collections.Generic;"
+ "\nnamespace " + _package + " {"
+ "\n\tpublic class ConvertModel{"
+ "\n\t\tprivate int x, y, len;"
+ "\n\t\tprivate string iostring;"
+ "\n\t\tprivate static ConvertModel instance;"
+ "\n\t\tpublic static ConvertModel getInstance() {"
+ "\n\t\t\tif (null == instance) {"
+ "\n\t\t\t\tinstance = new ConvertModel();"
+ "\n\t\t\t}"
+ "\n\t\t\treturn instance;"
+ "\n\t\t}"
+ "\n\t\tpublic void setting(string iostring) {"
+ "\n\t\t\tsetting(iostring, 0, 0);"
+ "\n\t\t}"
+ "\n\t\tpublic void setting(string iostring, int x, int y) {"
+ "\n\t\t\tlen = iostring.Length;"
+ "\n\t\t\tthis.iostring = iostring;"
+ "\n\t\t\tthis.x = x;"
+ "\n\t\t\tthis.y = y;"
+ "\n\t\t}"
+ "\n\t\tprivate string read() {"
+ "\n\t\t\tint offset = SAProtoDecode.decodeAssign.Length;"
+ "\n\t\t\tx = iostring.IndexOf(SAProtoDecode.decodeAssign, x) + offset;"
+ "\n\t\t\ty = iostring.IndexOf(SAProtoDecode.decodeSplit, x);"
+ "\n\t\t\tif (y == -1) {"
+ "\n\t\t\t\ty = iostring.IndexOf(SAProtoDecode.decodeEnd, x);//结尾保护"
+ "\n\t\t\t\toffset = SAProtoDecode.decodeEnd.Length;"
+ "\n\t\t\t}"
+ "\n\t\t\tstring value = iostring.Substring(x, y - x);"
+ "\n\t\t\tx = y + offset;"
+ "\n\t\t\treturn value;"
+ "\n\t\t}"
+ "\n\t\tpublic int readInt() {"
+ "\n\t\t\treturn int.Parse(read());"
+ "\n\t\t}"
+ "\n\t\tpublic bool readBoolean() {"
+ "\n\t\t\treturn read().Equals(SAProtoDecode.isBooleanStr);"
+ "\n\t\t}"
+ "\n\t\tpublic float readFloat()"
+ "\n\t\t{"
+ "\n\t\t\treturn float.Parse(read());"
+ "\n\t\t}"
+ "\n\t\tpublic string readString()"
+ "\n\t\t{"
+ "\n\t\t\treturn read();"
+ "\n\t\t}"
+ "\n\t\tpublic string[] readArray()"
+ "\n\t\t{"

+ "\n\t\t\tx = iostring.IndexOf(SAProtoDecode.decodeArrayAssign, x) + SAProtoDecode.decodeArrayAssign.Length;"
+ "\n\t\t\ty = iostring.IndexOf(SAProtoDecode.decodeArrayEnd, x);"
+ "\n\t\t\tstring value = iostring.Substring(x, y - x);"
+ "\n\t\t\tx = y + SAProtoDecode.decodeArrayEnd.Length;"
+ "\n\t\t\treturn System.Text.RegularExpressions.Regex.Split(value,SAProtoDecode.splitStr);"

+ "\n\t\t}"
+ "\n\t\tpublic bool limit()"
+ "\n\t\t{"
+ "\n\t\t\treturn x >= len;"
+ "\n\t\t}"
+ "\n\t\tpublic void flip()"
+ "\n\t\t{"
+ "\n\t\t\tx = 0;"
+ "\n\t\t\ty = 0;"
+ "\n\t\t}"
+ "\n\t}"
+ "\n\tpublic class SAProtoDecode"
+ "\n\t{"
+ "\n\t\tpublic const string SAString = \"" + SAProtoEncode.SAString + "\";"
+ "\n\t\tpublic const string SAInt = \"" + SAProtoEncode.SAInt + "\";"
+ "\n\t\tpublic const string SAFloat = \"" + SAProtoEncode.SAFloat + "\";"
+ "\n\t\tpublic const string SABoolean = \"" + SAProtoEncode.SABoolean + "\";"
+ "\n\t\tpublic const string SAArray = \"" + SAProtoEncode.SAArray + "\";"
+ "\n\t\tpublic const string isBooleanStr = \"" + SAProtoEncode.isBooleanStr + "\";"
+ "\n\t\tpublic const string splitStr = \"" + SAProtoEncode.javaSplitStr + "\";"
+ "\n\t\tpublic const string decodeAssign = \"" + SAProtoEncode.decodeAssign + "\";"
+ "\n\t\tpublic const string decodeSplit = \"" + SAProtoEncode.decodeSplit + "\";"
+ "\n\t\tpublic const string decodeEnd = \"" + SAProtoEncode.decodeEnd + "\";"
+ "\n\t\tpublic const string decodeArrayAssign = \"" + SAProtoEncode.decodeArrayAssign + "\";"
+ "\n\t\tpublic const string decodeArrayEnd = \"" + SAProtoEncode.decodeArrayEnd + "\";"
+ "\n\t\tpublic const string decodeStrEnd = \"" + SAProtoEncode.decodeStrEnd + "\";"
+ "\n\t\tpublic const string classNameEnd = \"" + SAProtoEncode.classNameEnd + "\";"
+ "\n\t\tpublic SAProtoDecode() {}"
+ "\n\t\t/**"
+ "\n\t\t * 解析一套数据表"
+ "\n\t\t * @param iostring"
+ "\n\t\t */"
+ "\n\t\tpublic static void parsing(string iostring)"
+ "\n\t\t{"
+ "\n\t\t\tint x = 0, y = 0, len = iostring.Length;"
+ "\n\t\t\tstring typeName;"
+ "\n\t\t\tstring endName;"
+ "\n\t\t\tstring fragmentStr;"
+ "\n\t\t\twhile (x < len) {"
+ "\n\t\t\t\tx = iostring.IndexOf(decodeStrEnd, x) + 1;//查询类型名称首部"
+ "\n\t\t\t\tif (x == -1)"
+ "\n\t\t\t\t\tbreak;//解析完毕"
+ "\n\t\t\t\ty = iostring.IndexOf(decodeStrEnd, x);//查询类型名称尾部"
+ "\n\t\t\t\ttypeName = iostring.Substring(x, y - x);//获得类型"
+ "\n\t\t\t\tendName = classNameEnd + typeName + decodeStrEnd;//获得类型结尾(如果解析内容中包含了自身类型，无法解析)"
+ "\n\t\t\t\tx = y + decodeAssign.Length + 1;//查询内容首部(跳过冒号)"
+ "\n\t\t\t\ty = iostring.IndexOf(endName, x);//查询内容尾部"
+ "\n\t\t\t\tfragmentStr = iostring.Substring(x, y - x);//获得内容"
+ "\n\t\t\t\tx = y + endName.Length;"
+ "\n\t\t\t\tsetIOString(typeName, fragmentStr);"
+ "\n\t\t\t}"
+ "\n\t\t}"
+ "\n\t\tpublic static void setIOString(string type, string iostring) {"
+ "\n\t\t\tswitch (type) {");
		DecodeGetProto = new StringBuffer();//结构
		DecodeProtoType = new StringBuffer();//类型
	}

	public void process(SAProtoTask task, ArrayList<String> type, ArrayList<String> title) {
		String BigProtoType = SAProtoEncode.getFirstBig(task.type);
		String LittleProtoType = SAProtoEncode.getFirstLittle(task.type);
		DecodeProtoType.append("\n\t\tpublic const string " + BigProtoType + "Type = \"" + LittleProtoType + "\";");
		
		DecodeProto.append("\n\t\t\t\tcase " + BigProtoType + "Type: {"
				+ "\n\t\t\t\t\t" + BigProtoType + ".parsing(iostring);"
				+ "\n\t\t\t\t\tbreak;\n\t\t\t\t}");
		
		DecodeGetProto.append("\n\tpublic class " + BigProtoType + " {"
				+ "\n\t\tpublic " + BigProtoType + "() { }"
				+ "\n\t\tprivate static List<" + BigProtoType + "> list;"
				+ "\n\t\tprivate static Dictionary<int, " + BigProtoType + "> map;"
				+ "\n\t\tpublic static List<" + BigProtoType + "> getList() {"
				+ "\n\t\t\tif (null == list) {"
				+ "\n\t\t\t\tlist = new List<" + BigProtoType + ">();"
                + "\n\t\t\t}"
                + "\n\t\t\treturn list;"
                + "\n\t\t}"
				+ "\n\t\tpublic static Dictionary<int, " + BigProtoType + "> getMap() {"
				+ "\n\t\t\tif (null == map) {"
				+ "\n\t\t\t\tmap = new Dictionary<int, " + BigProtoType + ">();"
                + "\n\t\t\t}"
                + "\n\t\t\treturn map;"
                + "\n\t\t}"
                + "\n\t\tpublic static void parsing(string iostring) {"
				+ "\n\t\t\tList<" + BigProtoType + "> list = getList();"
				+ "\n\t\t\tDictionary<int, " + BigProtoType + "> map = getMap();"
				+ "\n\t\t\tlist.Clear();"
				+ "\n\t\t\tmap.Clear();"
				+ "\n\t\t\tConvertModel buf = ConvertModel.getInstance();"
				+ "\n\t\t\tbuf.setting(iostring);"
				+ "\n\t\t\t" + BigProtoType + " " + LittleProtoType + ";"
				+ "\n\t\t\twhile(!buf.limit()) {"
				+ "\n\t\t\t\t" + LittleProtoType + "= new " + BigProtoType + "();");
		
		StringBuffer DecodeProtoDecode = new StringBuffer();
		
		String titleLittle;
		String titleType;
		for(int i = 0; i < title.size(); ++i){
			titleLittle = SAProtoEncode.getFirstLittle(title.get(i));
			titleType = getType(type.get(i));
			DecodeProtoDecode.append("\n\t\tprivate " + titleType + " " + titleLittle + ";");
			DecodeProtoDecode.append("\n\t\tpublic " + titleType + " " + SAProtoEncode.getFirstBig(title.get(i)) + " {get{return " + titleLittle + ";}}");
			DecodeGetProto.append("\n\t\t\t\t" + LittleProtoType + "." + titleLittle + " = " + getFragment(type.get(i)));
		}
		DecodeGetProto.append("\n\t\t\t\tlist.Add(" + LittleProtoType + ");"
				+ "\n\t\t\t\tmap.Add(" + LittleProtoType + ".Id, " + LittleProtoType + ");");
		
		DecodeGetProto.append("\n\t\t\t}");
		DecodeGetProto.append("\n\t\t}");
		DecodeGetProto.append(DecodeProtoDecode);
		DecodeGetProto.append("\n\t}");
	}

	public void finish(String path) {
		DecodeProto.append("\n\t\t\t}");
		DecodeProto.append("\n\t\t}");
		DecodeProto.append(DecodeProtoType);
		DecodeProto.append("\n\t}");
		DecodeProto.append(DecodeGetProto);
		DecodeProto.append("\n}");

		release(path);
		System.out.println(path);
		//release("D:/svn/serv/CardGameMaster/src/com/game/sa/protobuf/SAProtoDecode.java");
	}
	
	private void release(String path){
		try {
			FileOutputStream javaWrite = new FileOutputStream(path);
			javaWrite.write(DecodeProto.toString().getBytes("UTF8"));
			javaWrite.flush(); // 刷新缓冲区的数据到文件
			javaWrite.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFragment(String type) {
		switch (type) {
			case SAProtoEncode.SAInt: {
				return "buf.readInt();";
			}
			case SAProtoEncode.SABoolean: {
				return "buf.readBoolean();";
			}
			case SAProtoEncode.SAFloat: {
				return "buf.readFloat();";
			}
			case SAProtoEncode.SAArray:{
				return "buf.readArray();";
			}
			case SAProtoEncode.SAString:
			default:{
				return "buf.readString();";
			}
		}
	}
	
	public String getType(String type){
		switch (type) {
			case SAProtoEncode.SAInt: {
				return "int";
			}
			case SAProtoEncode.SABoolean: {
				return "bool";
			}
			case SAProtoEncode.SAFloat: {
				return "float";
			}
			case SAProtoEncode.SAArray:{
				return "string[]";
			}
			case SAProtoEncode.SAString:
			default:{
				return "string";
			}
		}
	}
}
