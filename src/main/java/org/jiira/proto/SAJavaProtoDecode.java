package org.jiira.proto;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SAJavaProtoDecode implements ISAProtoDecode {
	private final String _package = "org.jiira.protobuf";
	private StringBuffer DecodeProto;
	private StringBuffer DecodeGetProto;
	private static SAJavaProtoDecode decode;
	public static ISAProtoDecode getInstance() {
		if(null == decode){
			decode = new SAJavaProtoDecode();
		}
		return decode;
	}
	public void start() {
		// 解析json&导出数据结构 saproto
		DecodeProto = new StringBuffer("package " + _package + ";" + "\nimport java.util.ArrayList;"
				+ "\nimport java.util.Map;"
				+ "\nimport java.util.concurrent.ConcurrentHashMap;"
				+ "\npublic class SAProtoDecode {"
				+ "\n\tpublic static final String SAString = \"" + SAProtoEncode.SAString + "\";"
				+ "\n\tpublic static final String SAInt = \"" + SAProtoEncode.SAInt + "\";"
				+ "\n\tpublic static final String SAFloat = \"" + SAProtoEncode.SAFloat + "\";"
				+ "\n\tpublic static final String SABoolean = \"" + SAProtoEncode.SABoolean + "\";"
				+ "\n\tpublic static final String SAArray = \"" + SAProtoEncode.SAArray + "\";"
				+ "\n\tpublic static final String isBooleanStr = \"" + SAProtoEncode.isBooleanStr + "\";"
				+ "\n\tpublic static final String splitStr = \"" + SAProtoEncode.javaSplitStr + "\";"
				+ "\n\tpublic static final String decodeAssign = \"" + SAProtoEncode.decodeAssign + "\";"
				+ "\n\tpublic static final String decodeSplit = \"" + SAProtoEncode.decodeSplit + "\";"
				+ "\n\tpublic static final String decodeEnd = \"" + SAProtoEncode.decodeEnd + "\";"
				+ "\n\tpublic static final String decodeArrayAssign = \"" + SAProtoEncode.decodeArrayAssign + "\";"
				+ "\n\tpublic static final String decodeArrayEnd = \"" + SAProtoEncode.decodeArrayEnd + "\";"
				+ "\n\tpublic static final String decodeStrEnd = \"" + SAProtoEncode.decodeStrEnd + "\";"
				+ "\n\tpublic static final String classNameEnd = \"" + SAProtoEncode.classNameEnd + "\";"
				+ "\n\tpublic SAProtoDecode(){" + "\n\t}"
				+ "\n\t/**" + "\n\t * 解析一套数据表" + "\n\t * @param iostring" + "\n\t */"
				+ "\n\tpublic static void parsing(String iostring) {"
				+ "\n\t\tint x = 0, y = 0, len = iostring.length();" + "\n\t\tString typeName;"
				+ "\n\t\tString endName;" + "\n\t\tString fragmentStr;" + "\n\t\twhile(x < len){"
				+ "\n\t\t\tx = iostring.indexOf(decodeStrEnd, x) + 1;//查询类型名称首部" + "\n\t\t\tif(x == -1)"
				+ "\n\t\t\t\tbreak;//解析完毕" + "\n\t\t\ty = iostring.indexOf(decodeStrEnd, x);//查询类型名称尾部"
				+ "\n\t\t\ttypeName = iostring.substring(x, y);//获得类型"
				+ "\n\t\t\tendName = classNameEnd + typeName + decodeStrEnd;//获得类型结尾(如果解析内容中包含了自身类型，无法解析)"
				+ "\n\t\t\tx = y + decodeAssign.length() + 1;//查询内容首部(跳过冒号)" + "\n\t\t\ty = iostring.indexOf(endName, x);//查询内容尾部"
				+ "\n\t\t\tfragmentStr = iostring.substring(x, y);//获得内容" + "\n\t\t\tx = y + endName.length();"
				+ "\n\t\t\tsetIOString(typeName, fragmentStr);" + "\n\t\t}" + "\n\t}"
				+ "\n\tpublic static class ConvertModel{"
				+ "\n\t\tprivate int x, y, len;"
				+ "\n\t\tprivate String iostring;"
				+ "\n\t\tprivate static ConvertModel instance;"
				+ "\n\t\tpublic static ConvertModel getInstance(){"
				+ "\n\t\t\tif(null == instance){"
				+ "\n\t\t\t\tinstance = new ConvertModel();"
				+ "\n\t\t\t}"
				+ "\n\t\t\treturn instance;"
				+ "\n\t\t}"
				+ "\n\t\tpublic void setting(String iostring){"
				+ "\n\t\t\tsetting(iostring, 0, 0);"
				+ "\n\t\t}"
				+ "\n\t\tpublic void setting(String iostring, int x, int y){"
				+ "\n\t\t\tlen = iostring.length();"
				+ "\n\t\t\tthis.iostring = iostring;"
				+ "\n\t\t\tthis.x = x;"
				+ "\n\t\t\tthis.y = y;"
				+ "\n\t\t}"
				+ "\n\t\tprivate String read() {"
				+ "\n\t\tint offset = decodeAssign.length();"
				+ "\n\t\t\tx = iostring.indexOf(decodeAssign, x) + offset;"
				+ "\n\t\t\ty = iostring.indexOf(decodeSplit, x);"
				+ "\n\t\t\tif(y == -1) {"
				+ "\n\t\t\t\ty = iostring.indexOf(decodeEnd, x);//结尾保护"
				+ "\n\t\t\t\toffset = decodeEnd.length();"
				+ "\n\t\t\t}"
				+ "\n\t\t\tString value = iostring.substring(x, y);"
				+ "\n\t\t\tx = y + offset;"
				+ "\n\t\t\treturn value;"
				+ "\n\t\t}"
				+ "\n\t\tpublic int readInt(){"
				+ "\n\t\t\treturn Integer.parseInt(read());"
				+ "\n\t\t}"
				+ "\n\t\tpublic boolean readBoolean() {"
				+ "\n\t\t\treturn read().equals(isBooleanStr);"
				+ "\n\t\t}"
				+ "\n\t\tpublic float readFloat() {"
				+ "\n\t\t\treturn Float.parseFloat(read());"
				+ "\n\t\t}"
				+ "\n\t\tpublic String readString() {"
				+ "\n\t\t\treturn read();"
				+ "\n\t\t}"
				+ "\n\t\tpublic String[] readArray(){"
				+ "\n\t\t\tx = iostring.indexOf(decodeArrayAssign, x) + decodeArrayAssign.length();"
				+ "\n\t\t\ty = iostring.indexOf(decodeArrayEnd, x);"
				+ "\n\t\t\tString value = iostring.substring(x, y);"
				+ "\n\t\t\tx = y + decodeArrayEnd.length();"
				+ "\n\t\t\treturn value.split(splitStr);"
				+ "\n\t\t}"
				+ "\n\t\tpublic boolean limit(){"
				+ "\n\t\t\treturn x >= len;"
				+ "\n\t\t}"
				+ "\n\t\tpublic void flip() {"
				+ "\n\t\t\tx = 0;"
				+ "\n\t\t\ty = 0;"
				+ "\n\t\t}"
				+ "\n\t}");
		DecodeGetProto = new StringBuffer(
				"\n\tpublic static void setIOString(String type, String iostring) {" + "\n\t\tswitch(type) {");
	}

	public void process(SAProtoTask task, ArrayList<String> type, ArrayList<String> title) {
		String BigProtoType = SAProtoEncode.getFirstBig(task.type);
		String LittleProtoType = SAProtoEncode.getFirstLittle(task.type);
		//需要进一步解析
		DecodeGetProto.append("\n\t\t\tcase SAProtoDecode." + BigProtoType + "Type: {"
				+ "\n\t\t\t\t" + BigProtoType + ".parsing(iostring);"
				+ "\n\t\t\t\tbreak;\n\t\t\t}//先看结果");
		DecodeProto.append("\n\tpublic static final String " + BigProtoType + "Type = \"" + LittleProtoType + "\";");
		DecodeProto.append("\n\tpublic static class " + BigProtoType + " {");
		
		StringBuffer DecodeProtoDecode = new StringBuffer("\n\t\tprivate static ArrayList<" + BigProtoType + "> list;"
				+ "\n\t\tprivate static Map<Integer, " + BigProtoType + "> map;"
				+ "\n\t\tpublic " + BigProtoType + "() {}"
				+ "\n\t\tpublic static ArrayList<" + BigProtoType + "> getList(){"
				+ "\n\t\t\tif(null == list){"
				+ "\n\t\t\t\tlist = new ArrayList<" + BigProtoType + ">();"
				+ "\n\t\t\t}"
				+ "\n\t\t\treturn list;"
				+ "\n\t\t}"
				+ "\n\t\tpublic static Map<Integer, " + BigProtoType + "> getMap(){"
				+ "\n\t\t\tif(null == map){"
				+ "\n\t\t\t\tmap = new ConcurrentHashMap<Integer, " + BigProtoType + ">();"
				+ "\n\t\t\t}"
				+ "\n\t\t\treturn map;"
				+ "\n\t\t}"
				+ "\n\t\tpublic static void parsing(String iostring) {"
				+ "\n\t\t\tArrayList<" + BigProtoType + "> list = getList();"
				+ "\n\t\t\tMap<Integer, " + BigProtoType + "> map = getMap();"
				+ "\n\t\t\tlist.clear();"
				+ "\n\t\t\tmap.clear();"
				+ "\n\t\t\tConvertModel buf = ConvertModel.getInstance();"
				+ "\n\t\t\tbuf.setting(iostring);"
				+ "\n\t\t\t" + BigProtoType + " " + LittleProtoType + ";"
				+ "\n\t\t\twhile(!buf.limit()) {");
		DecodeProtoDecode.append("\n\t\t\t\t" + LittleProtoType + "= new " + BigProtoType + "();");
		for(int i = 0; i < title.size(); ++i){
			DecodeProto.append("\n\t\tprivate " + getType(type.get(i)) + " " + title.get(i) + ";");
			DecodeProto.append("\n\t\tpublic " + getType(type.get(i)) + " get" + title.get(i) + "(){return " + title.get(i) + ";}");
			DecodeProtoDecode.append("\n\t\t\t\t" + LittleProtoType + "." + title.get(i) + " = " + getFragment(type.get(i)));
		}
		DecodeProtoDecode.append("\n\t\t\t\tlist.add(" + LittleProtoType + ");");
		DecodeProtoDecode.append("\n\t\t\t\tmap.put(" + LittleProtoType + ".Id, " + LittleProtoType + ");");
		
		DecodeProtoDecode.append("\n\t\t\t}");
		DecodeProtoDecode.append("\n\t\t}");
		DecodeProtoDecode.append("\n\t}");
		DecodeProto.append(DecodeProtoDecode);
	}

	public void finish(String path) {
		DecodeGetProto.append("\n\t\t}");
		DecodeGetProto.append("\n\t}");
		DecodeProto.append(DecodeGetProto);
		DecodeProto.append("\n}");

		release(path);
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
				return "boolean";
			}
			case SAProtoEncode.SAFloat: {
				return "float";
			}
			case SAProtoEncode.SAArray:{
				return "String[]";
			}
			case SAProtoEncode.SAString:
			default:{
				return "String";
			}
		}
	}
}
