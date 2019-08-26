package org.jiira.proto;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SAProtoEncode {
	public static final String SAString = "string";//字符串
	public static final String SAInt = "int";//整形
	public static final String SAFloat = "float";//浮点型
	public static final String SABoolean = "boolean";//布尔型
	public static final String SAArray = "array";//数组型
	public static final String isBooleanStr = "1";//boolean

	//通用
	public static final String classBegin = "[[]";//类的开始
	public static final String classEnd = "[]]";//类的结束
//	public static final String splitStr = "\\\\[\\\\*]";//数组分隔符
	public static final String decodeAssign = "[:]";//键值分隔符
	public static final String decodeSplit = "[,]";//分隔符
	public static final String decodeBegin = "[{]";//结构体开始
	public static final String decodeEnd = "[}]";//结构体结尾
	public static final String arrayBegin = "[{]";//数组开始
	public static final String arrayEnd = "[}]";//数组结束
	public static final String decodeArrayAssign = decodeAssign + arrayBegin;//数组开始标识符
	public static final String decodeArrayEnd = arrayEnd + decodeSplit;//数组结束标识符
	public static final String decodeStrEnd = "\\\"";//名称包裹符号
	public static final String classNameEnd = decodeEnd + classEnd + "\\\"";//类名结尾
	
	//java
	public static final String javaSplitStr = "\\\\*";//数组分隔符
	
	//lua
	public static final String luaClassBegin = "%[[]";//类的开始
	public static final String luaClassEnd = "%[]]";//类的结束
	public static final String luaDecodeAssign = "%[:]";//键值分隔符
	public static final String luaDecodeSplit = "%[,]";//分隔符
	public static final String luaDecodeBegin = "%[{]";//结构体开始
	public static final String luaDecodeEnd = "%[}]";//结构体结尾
	public static final String luaArrayBegin = "%[{]";//数组开始
	public static final String luaArrayEnd = "%[}]";//数组结束
	public static final String luaSplitStr = "%[*%]";//数组分隔符
	public static final String luaDecodeArrayAssign = "%[:]%[{]";//数组开始标识符
	public static final String luaDecodeArrayEnd = "%[}]%[,]";//数组结束标识符
	public static final String luaClassNameEnd = luaDecodeEnd + luaClassEnd + "\\\"";//类名结尾

	public static final String _decodeStrEnd = "\"";//名称包裹符号
	public static final String _classNameEnd = decodeEnd + classEnd + "\"";//类名结尾
	
	private static SAProtoEncode instance;
	public final int isBoolean = 1;// 真
	public final String sheetLabel = "Worksheet";// 页码标签
	public final String tableLabel = "Table";// 表标签
	public final String rowLabel = "Row";// 行号标签
	public final String cellLabel = "Cell";// 列号标签

	private ISAProtoDecode javaDecode;
	private ISAProtoDecode csharpDecode;
	private ISAProtoDecode luaDecode;

	private ArrayList<SAProtoTask> tasks;
	private SAProtoEncode() {
		tasks = new ArrayList<SAProtoTask>();
	}
	
	public static SAProtoEncode getInstance() {
		if(null == instance) {
			instance = new SAProtoEncode();
		}
		return instance;
	}
	
	public void addTask(SAProtoTask task){
		tasks.add(task);
	}
	
	public void cleadTask() {
		tasks.clear();
	}
	
	public void execute(String javaEncodePath, String csharpEncodePath, String javaDecodePath, String csharpDecodePath, String luaDecodePath, String tablePath){
		javaDecode = SAJavaProtoDecode.getInstance();
		csharpDecode = SACSharpProtoDecode.getInstance();
		luaDecode = SALuaProtoDecode.getInstance();
		StringBuffer EncodeProto = new StringBuffer();
		
		javaDecode.start();
		csharpDecode.start();
		luaDecode.start();
		Map<String, ArrayList<ArrayList<ArrayList<Node>>>> tableCache = new ConcurrentHashMap<String, ArrayList<ArrayList<ArrayList<Node>>>>();
		ArrayList<ArrayList<ArrayList<Node>>> table;
		for(SAProtoTask task : tasks){
			if(tableCache.containsKey(task.tableName)){
				table = tableCache.get(task.tableName);
			} else {
				table = parsing(task.tableName, tablePath);
				tableCache.put(task.tableName, table);
			}
			compile(task, table, EncodeProto);
		}
		javaDecode.finish(javaDecodePath);
		csharpDecode.finish(csharpDecodePath);
		luaDecode.finish(luaDecodePath);
		
		String encode = EncodeProto.toString();
		writeEncode(encode, javaEncodePath);
		writeEncode(encode, csharpEncodePath);
	}
	
	protected void writeEncode(String encode, String path){
		try {
			FileOutputStream javaWrite = new FileOutputStream(path);
			javaWrite.write(encode.getBytes("UTF8"));
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

	protected ArrayList<ArrayList<ArrayList<Node>>> parsing(String tableName, String tablePath) {
		File f = new File(tablePath + tableName + ".xml");
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<ArrayList<ArrayList<Node>>> table = new ArrayList<ArrayList<ArrayList<Node>>>();
		ArrayList<ArrayList<Node>> tableRow;
		ArrayList<Node> tableCell;

		// 得到一个elment根元素
		Element element = document.getDocumentElement();
		NodeList notes = element.getChildNodes();
		Element sheetElement;
		NodeList rowList;
		NodeList cellList;
		Element value;
		int j, k;
		for (int i = 0; i < notes.getLength(); ++i) {
			if (notes.item(i).getNodeName().equals(sheetLabel)) {// 判断是页码
				sheetElement = (Element) notes.item(i);
				tableRow = new ArrayList<ArrayList<Node>>();
				table.add(tableRow);
				rowList = sheetElement.getChildNodes();
				j = 0;
				while (!rowList.item(j).getNodeName().equals(tableLabel)) {
					++j;
				}
				rowList = rowList.item(j).getChildNodes();
				for (j = 0; j < rowList.getLength(); ++j) {
					if (rowList.item(j).getNodeName().equals(rowLabel)) {
						tableCell = new ArrayList<Node>();
						cellList = rowList.item(j).getChildNodes();// cell
						for (k = 0; k < cellList.getLength(); ++k) {
							if (cellList.item(k).getNodeName().equals(cellLabel)) {
								value = (Element) cellList.item(k);
								if (null != value && value.getChildNodes().getLength() != 0
										&& null != value.getFirstChild().getTextContent()) {
									tableCell.add(value.getFirstChild());
								}
							}
						}
						if (tableCell.size() > 0) {
							tableRow.add(tableCell);
						}
					}
				}
			}
		}
		return table;
	}
//, StringBuffer DecodeProto, StringBuffer DecodeGetProto, StringBuffer EncodeProto
	public void compile(SAProtoTask task, ArrayList<ArrayList<ArrayList<Node>>> table, StringBuffer EncodeProto) {
		String LittleProtoType = getFirstLittle(task.type);
		//获得表结构
		ArrayList<ArrayList<Node>> tableRow = table.get(task.page);
		//获得type(属性类型)
		int row = task.row;
		int i;
		ArrayList<Node> nodeList = tableRow.get(row++);
		ArrayList<String> type = new ArrayList<String>();
		for (i = task.cell; i < task.totalCell; ++i) {
			type.add(nodeList.get(i).getTextContent());
		}
		//获得title(属性名称)
		nodeList = tableRow.get(row++);
		ArrayList<String> title = new ArrayList<String>();
		for (i = task.cell; i < task.totalCell; ++i) {
			title.add(nodeList.get(i).getTextContent());
		}
		
		//process
		javaDecode.process(task, type, title);
		csharpDecode.process(task, type, title);
		luaDecode.process(task, type, title);

		StringBuffer json = new StringBuffer(classBegin);
		final int rowDot = tableRow.size() - 1;
		for (i = row; i < tableRow.size(); ++i) {
			nodeList = tableRow.get(i);
			json.append(decodeBegin);
			for (int k = task.cell, j = 0; k < task.totalCell; ++k, ++j) {
				json.append(_decodeStrEnd + title.get(j) + _decodeStrEnd + decodeAssign + getValue(nodeList.get(k), type.get(j)) + decodeSplit);
			}
			json.append(decodeEnd);
			if (i < rowDot) {
				json.append(decodeSplit);
			}
		}
		System.out.println("json : " + (tableRow.size() - row));
		json.append(classEnd);
		EncodeProto.append(_decodeStrEnd + LittleProtoType + _decodeStrEnd + decodeAssign + json + _decodeStrEnd + LittleProtoType + _decodeStrEnd);
		//return json.toString();
	}
	protected String getValue(Node node, String type) {
		switch (type) {
			case SAInt: {
				return String.valueOf(Math.round(Double.parseDouble(node.getTextContent())));
			}
			case SABoolean: {
				return (Integer.parseInt(node.getTextContent())==isBoolean?"1":"0");
			}
			case SAFloat: {
				return String.valueOf(Float.parseFloat(node.getTextContent()));
			}
			case SAArray:{
				return arrayBegin + node.getTextContent() + arrayEnd;
			}
			case SAString:
			default:{
				return node.getTextContent();
			}
		}
	}
	
	public static String getFirstBig(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase());
	}
	public static String getFirstLittle(String str){
		return str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toLowerCase());
	}
}
