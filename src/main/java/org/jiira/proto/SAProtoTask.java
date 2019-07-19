package org.jiira.proto;


public class SAProtoTask {
	public int page;// 要解析的页数
	public int row;// 开始的行数
	public int cell;// 开始的列数
	public int totalCell;// 总列数
	public String tableName;// 表明
	public String type;//类型
	public SAProtoTask(int page, int row, int cell, int totalCell, String tableName, String type){
		this.page = page;
		this.row = row;
		this.cell = cell;
		this.totalCell = totalCell;
		this.tableName = tableName;
		this.type = type;
	}
}
