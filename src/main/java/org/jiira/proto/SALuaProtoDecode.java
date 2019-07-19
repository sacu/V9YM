package org.jiira.proto;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SALuaProtoDecode implements ISAProtoDecode {
	private StringBuffer DecodeProto;
	private StringBuffer DecodeSetProto;
	private StringBuffer DecodeGetProto;
	private static SALuaProtoDecode decode;
	public static ISAProtoDecode getInstance() {
		if(null == decode){
			decode = new SALuaProtoDecode();
		}
		return decode;
	}
	public void start() {
		// 解析json&导出数据结构 saproto
		DecodeProto = new StringBuffer("SAProtoDecode = {"
				+ "\n\tSAString = \"" + SAProtoEncode.SAString + "\","
				+ "\n\tSAInt = \"" + SAProtoEncode.SAInt + "\","
				+ "\n\tSAFloat = \"" + SAProtoEncode.SAFloat + "\","
				+ "\n\tSABoolean = \"" + SAProtoEncode.SABoolean + "\","
				+ "\n\tSAArray = \"" + SAProtoEncode.SAArray + "\","
				+ "\n\tisBooleanStr = \"" + SAProtoEncode.isBooleanStr + "\","
				+ "\n\t----------通用"
				+ "\n\tsplitStr = \"" + SAProtoEncode.splitStr + "\","
				+ "\n\tdecodeAssign = \"" + SAProtoEncode.decodeAssign + "\","
				+ "\n\tdecodeSplit = \"" + SAProtoEncode.decodeSplit + "\","
				+ "\n\tdecodeEnd = \"" + SAProtoEncode.decodeEnd + "\","
				+ "\n\tdecodeArrayAssign = \"" + SAProtoEncode.decodeArrayAssign + "\","
				+ "\n\tdecodeArrayEnd = \"" + SAProtoEncode.decodeArrayEnd + "\","
				+ "\n\tdecodeStrEnd = \"" + SAProtoEncode.decodeStrEnd + "\","
				+ "\n\tclassNameEnd = \"" + SAProtoEncode.classNameEnd + "\","
				+ "\n\t-------------Lua"
				+ "\n\tluaSplitStr = \"" + SAProtoEncode.luaSplitStr + "\","
				+ "\n\tluaDecodeAssign = \"" + SAProtoEncode.luaDecodeAssign + "\","
				+ "\n\tluaDecodeSplit = \"" + SAProtoEncode.luaDecodeSplit + "\","
				+ "\n\tluaDecodeEnd = \"" + SAProtoEncode.luaDecodeEnd + "\","
				+ "\n\tluaDecodeArrayAssign = \"" + SAProtoEncode.luaDecodeArrayAssign + "\","
				+ "\n\tluaDecodeArrayEnd = \"" + SAProtoEncode.luaDecodeArrayEnd + "\","
				+ "\n\tluaClassNameEnd = \"" + SAProtoEncode.luaClassNameEnd + "\","
				
				+ "\n\tparsing = {}"
				+ "\n}"
				+ "\nSAProtoDecode.parsing = function (iostring)"
				+ "\n\tlocal x = 0"
				+ "\n\tlocal y = 0"
				+ "\n\tlocal len = #iostring"
				+ "\n\tlocal typeName"
				+ "\n\tlocal endName"
				+ "\n\tlocal fragmentStr"
				+ "\n\twhile x < len do"
				+ "\n\t\tx = string.find(iostring, SAProtoDecode.decodeStrEnd, x) + 1 --查询类型名称首部"
				+ "\n\t\tif x == -1 then"
				+ "\n\t\t\tbreak--解析完毕"
				+ "\n\t\tend"
				+ "\n\t\ty = string.find(iostring, SAProtoDecode.decodeStrEnd, x) --查询类型名称尾部"
				+ "\n\t\ttypeName = string.sub(iostring, x, y - 1) --获得类型"
				+ "\n\t\tendName = SAProtoDecode.luaClassNameEnd .. typeName .. SAProtoDecode.decodeStrEnd --获得类型结尾(如果解析内容中包含了自身类型，无法解析)"
				+ "\n\t\tx = y + #SAProtoDecode.decodeAssign + 1 --查询内容首部(跳过冒号)"
				+ "\n\t\ty = string.find(iostring, endName, x) --查询内容尾部"
				+ "\n\t\tfragmentStr = string.sub(iostring, x, y - 1) --获得内容"
				+ "\n\t\tendName = SAProtoDecode.classNameEnd .. typeName .. SAProtoDecode.decodeStrEnd"
				+ "\n\t\tx = y + #endName"
				+ "\n\t\tSAProtoDecode.setIOString(typeName, fragmentStr)"
				+ "\n\tend"
				+ "\nend"
				+ "\nConvertModel = {x = 0, y = 0, len = 0, iostring = \"\"}"
				+ "\nConvertModel.setting = function(iostring, x, y)"
				+ "\n\tConvertModel.len = #iostring"
				+ "\n\tConvertModel.iostring = iostring"
				+ "\n\tConvertModel.x = x"
				+ "\n\tConvertModel.y = y"
				+ "\nend"
				+ "\nConvertModel.read = function()"
				+ "\n\tConvertModel.x = string.find(ConvertModel.iostring, SAProtoDecode.luaDecodeAssign, ConvertModel.x) + #SAProtoDecode.decodeAssign"
				+ "\n\tConvertModel.y = string.find(ConvertModel.iostring, SAProtoDecode.luaDecodeSplit, ConvertModel.x) - 1"
				+ "\n\tlocal value = string.sub(ConvertModel.iostring, ConvertModel.x, ConvertModel.y)"
				+ "\n\tConvertModel.x = ConvertModel.y + #SAProtoDecode.decodeEnd"
				+ "\n\treturn value"
				+ "\nend"
				+ "\nConvertModel.readInt = function()"
				+ "\n\treturn tonumber(ConvertModel.read())"
				+ "\nend"
				+ "\nConvertModel.readBoolean = function()"
				+ "\n\treturn ConvertModel.read() == SAProtoDecode.isBooleanStr"
				+ "\nend"
				+ "\nConvertModel.readFloat = function()"
				+ "\n\treturn tonumber(ConvertModel.read())"
				+ "\nend"
				+ "\nConvertModel.readString = function()"
				+ "\n\treturn ConvertModel.read()"
				+ "\nend"
				+ "\nConvertModel.readArray = function()"

				+ "\n\tConvertModel.x = string.find(ConvertModel.iostring, SAProtoDecode.luaDecodeArrayAssign, ConvertModel.x) + #SAProtoDecode.decodeArrayAssign + 1"
				+ "\n\tConvertModel.y = string.find(ConvertModel.iostring, SAProtoDecode.luaDecodeArrayEnd, ConvertModel.x)"
				+ "\n\tlocal value = string.sub(ConvertModel.iostring, ConvertModel.x - 1, ConvertModel.y - 1)"
				+ "\n\tConvertModel.x = ConvertModel.y + #SAProtoDecode.decodeArrayEnd"
				+ "\n\treturn value:split(SAProtoDecode.luaSplitStr)"
				
				+ "\nend"
				+ "\nConvertModel.limit = function()"
				+ "\n\treturn ConvertModel.x >= ConvertModel.len"
				+ "\nend"
				+ "\nConvertModel.flip = function()"
				+ "\n\tConvertModel.x = 0"
				+ "\n\tConvertModel.y = 0"
				+ "\nend"
				+ "\nSAProtoClass = {}"
		+ "\nfunction SAProtoClass()"
		+ "\n\tlocal class_type = { ctor = false}    -- 'ctor' field must be here"
		+ "\n\tclass_type.new = function()"
		+ "\n\t\tlocal obj = { class = class_type }"
		+ "\n\t\tsetmetatable(obj, { __index = SAProtoClass[class_type] })"
		+ "\n\t\tif class_type.ctor then"
		+ "\n\t\t\tclass_type:ctor()"
		+ "\n\t\tend"
		+ "\n\t\tobj.class = class_type              -- must be here, because some class constructor change class property."
		+ "\n\t\treturn obj"
		+ "\n\tend"
		+ "\n\treturn class_type"
		+ "\nend"
				+ "\nSAProtoDecode.setIOString = function(type, iostring)");
		
		DecodeSetProto = new StringBuffer();
		DecodeGetProto = new StringBuffer();
	}

	public void process(SAProtoTask task, ArrayList<String> type, ArrayList<String> title) {
		String BigProtoType = SAProtoEncode.getFirstBig(task.type);
		String LittleProtoType = SAProtoEncode.getFirstLittle(task.type);

		StringBuffer DecodeProtoDecode = new StringBuffer();
		
		DecodeSetProto.append("\n\telseif SAProtoDecode." + BigProtoType + "Type == type then"
				+ "\n\t\t" + BigProtoType + ".parsing(iostring)");
		DecodeProtoDecode.append("\nSAProtoDecode." + BigProtoType + "Type = \"" + LittleProtoType + "\""
				+ "\n" + BigProtoType + ".parsing = function(iostring)"
				+ "\n\t" + BigProtoType + "." + LittleProtoType + "s = {}"
				+ "\n\tConvertModel.setting(iostring, 0, 0)"
				+ "\n\t" + BigProtoType + ".list = {}"
				+ "\n\t" + BigProtoType + ".map = {}"
				+ "\n\tlocal i = 0"
				+ "\n\twhile not ConvertModel.limit() do"
				+ "\n\t\tlocal " + LittleProtoType + "= SAProtoClass()");

		DecodeGetProto.append("\n" + BigProtoType + " = SAProtoClass()");
		int len = title.size();
		for(int i = 0; i < len; ++i){
			DecodeProtoDecode.append("\n\t\t" + LittleProtoType + "." + title.get(i) + " = " + getFragment(type.get(i)));
		}
		DecodeProtoDecode.append("\n\t\ttable.insert(" + BigProtoType + ".list, i, " + LittleProtoType + ")"
				+ "\n\t\t" + BigProtoType + ".map[" + LittleProtoType + ".Id] = " + LittleProtoType
		+ "\n\t\ti = i + 1");
		DecodeProtoDecode.append("\n\tend");
		DecodeProtoDecode.append("\nend");
		DecodeGetProto.append(DecodeProtoDecode);
	}

	public void finish(String path) {
		DecodeProto.append("\n\t" + DecodeSetProto.substring("\n\telse".length()));
		DecodeProto.append("\n\tend");
		DecodeProto.append("\nend");
		DecodeProto.append(DecodeGetProto);
		
		
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
				return "ConvertModel.readInt()";
			}
			case SAProtoEncode.SABoolean: {
				return "ConvertModel.readBoolean()";
			}
			case SAProtoEncode.SAFloat: {
				return "ConvertModel.readFloat()";
			}
			case SAProtoEncode.SAArray:{
				return "ConvertModel.readArray()";
			}
			case SAProtoEncode.SAString:
			default:{
				return "ConvertModel.readString()";
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
