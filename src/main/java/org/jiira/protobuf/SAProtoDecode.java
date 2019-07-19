package org.jiira.protobuf;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class SAProtoDecode {
//	public static final String SAString = "String";
//	public static final String SAInt = "int";
//	public static final String SAFloat = "float";
//	public static final String SABoolean = "Boolean";
//	public static final String SAArray = "String[]";
	public static final String isBooleanStr = "1";
	public static final String splitStr = ",";
	public static final String decodeAssign = ":";
	public static final String decodeSplit = ",";
	public static final String decodeEnd = "}";
	public static final String decodeStrEnd = "\"";
	public SAProtoDecode(){
	}
	/**
	 * 解析一套数据表
	 * @param iostring
	 */
	public static void parsing(String iostring) {
		int x = 0, y = 0, len = iostring.length();
		String typeName;
		String endName;
		String fragmentStr;
		while(x < len){
			x = iostring.indexOf(decodeStrEnd, x) + 1;//查询类型名称首部
			if(x == -1)
				break;//解析完毕
			y = iostring.indexOf(decodeStrEnd, x);//查询类型名称尾部
			typeName = iostring.substring(x, y);//获得类型
			endName = "}]\"" + typeName + decodeStrEnd;//获得类型结尾(如果解析内容中包含了自身类型，无法解析)
			x = y + 2;//查询内容首部(跳过冒号)
			y = iostring.indexOf(endName, x);//查询内容尾部
			fragmentStr = iostring.substring(x, y);//获得内容
			x = y + endName.length();
			setIOString(typeName, fragmentStr);
		}
	}
	public static class ConvertModel{
		private int x, y, len;
		private String iostring;
		private static ConvertModel instance;
		public static ConvertModel getInstance(){
			if(null == instance){
				instance = new ConvertModel();
			}
			return instance;
		}
		public void setting(String iostring){
			setting(iostring, 0, 0);
		}
		public void setting(String iostring, int x, int y){
			len = iostring.length();
			this.iostring = iostring;
			this.x = x;
			this.y = y;
		}
		private String read() {
			x = iostring.indexOf(decodeAssign, x) + 1;
			y = iostring.indexOf(decodeSplit, x);
			if(y == -1)y = iostring.indexOf(decodeEnd, x);//结尾保护
			String value = iostring.substring(x, y);
			x = y + 1;
			return value;
		}
		public int readInt(){
			return Integer.parseInt(read());
		}
		public boolean readBoolean() {
			return read().equals(isBooleanStr);
		}
		public float readFloat() {
			return Float.parseFloat(read());
		}
		public String readString() {
			return read();
		}
		public String[] readArray(){
			return read().split(splitStr);
		}
		public boolean limit(){
			return x >= len;
		}
		public void flip() {
			x = 0;
			y = 0;
		}
	}
	public static final String CardListType = "cardList";
	public static class CardList {
		private int Id;
		public int getId(){return Id;}
		private String Name;
		public String getName(){return Name;}
		private int Total;
		public int getTotal(){return Total;}
		private int Up;
		public int getUp(){return Up;}
		private int Left;
		public int getLeft(){return Left;}
		private int Down;
		public int getDown(){return Down;}
		private int Right;
		public int getRight(){return Right;}
		private int Star;
		public int getStar(){return Star;}
		private boolean IsBlink;
		public boolean getIsBlink(){return IsBlink;}
		private int SkillId;
		public int getSkillId(){return SkillId;}
		private boolean IsSplit;
		public boolean getIsSplit(){return IsSplit;}
		private int SplitSYB;
		public int getSplitSYB(){return SplitSYB;}
		private boolean IsSynthetic;
		public boolean getIsSynthetic(){return IsSynthetic;}
		private int SyntheticSYB;
		public int getSyntheticSYB(){return SyntheticSYB;}
		private int MaxLevel;
		public int getMaxLevel(){return MaxLevel;}
		private String Describe;
		public String getDescribe(){return Describe;}
		private static ArrayList<CardList> list;
		private static Map<Integer, CardList> map;
		public CardList() {}
		public static ArrayList<CardList> getList(){
			if(null == list){
				list = new ArrayList<CardList>();
			}
			return list;
		}
		public static Map<Integer, CardList> getMap(){
			if(null == map){
				map = new ConcurrentHashMap<Integer, CardList>();
			}
			return map;
		}
		public static void parsing(String iostring) {
			ArrayList<CardList> list = getList();
			Map<Integer, CardList> map = getMap();
			list.clear();
			map.clear();
			ConvertModel buf = ConvertModel.getInstance();
			buf.setting(iostring);
			CardList cardList;
			while(!buf.limit()) {
				cardList= new CardList();
				cardList.Id = buf.readInt();
				cardList.Name = buf.readString();
				cardList.Total = buf.readInt();
				cardList.Up = buf.readInt();
				cardList.Left = buf.readInt();
				cardList.Down = buf.readInt();
				cardList.Right = buf.readInt();
				cardList.Star = buf.readInt();
				cardList.IsBlink = buf.readBoolean();
				cardList.SkillId = buf.readInt();
				cardList.IsSplit = buf.readBoolean();
				cardList.SplitSYB = buf.readInt();
				cardList.IsSynthetic = buf.readBoolean();
				cardList.SyntheticSYB = buf.readInt();
				cardList.MaxLevel = buf.readInt();
				cardList.Describe = buf.readString();
				list.add(cardList);
				map.put(cardList.Id, cardList);
			}
		}
	}
	public static final String CardSkillType = "cardSkill";
	public static class CardSkill {
		private int Id;
		public int getId(){return Id;}
		private String Describe;
		public String getDescribe(){return Describe;}
		private static ArrayList<CardSkill> list;
		private static Map<Integer, CardSkill> map;
		public CardSkill() {}
		public static ArrayList<CardSkill> getList(){
			if(null == list){
				list = new ArrayList<CardSkill>();
			}
			return list;
		}
		public static Map<Integer, CardSkill> getMap(){
			if(null == map){
				map = new ConcurrentHashMap<Integer, CardSkill>();
			}
			return map;
		}
		public static void parsing(String iostring) {
			ArrayList<CardSkill> list = getList();
			Map<Integer, CardSkill> map = getMap();
			list.clear();
			map.clear();
			ConvertModel buf = ConvertModel.getInstance();
			buf.setting(iostring);
			CardSkill cardSkill;
			while(!buf.limit()) {
				cardSkill= new CardSkill();
				cardSkill.Id = buf.readInt();
				cardSkill.Describe = buf.readString();
				list.add(cardSkill);
				map.put(cardSkill.Id, cardSkill);
			}
		}
	}
	public static final String ShopListType = "shopList";
	public static class ShopList {
		private int Id;
		public int getId(){return Id;}
		private int Price;
		public int getPrice(){return Price;}
		private static ArrayList<ShopList> list;
		private static Map<Integer, ShopList> map;
		public ShopList() {}
		public static ArrayList<ShopList> getList(){
			if(null == list){
				list = new ArrayList<ShopList>();
			}
			return list;
		}
		public static Map<Integer, ShopList> getMap(){
			if(null == map){
				map = new ConcurrentHashMap<Integer, ShopList>();
			}
			return map;
		}
		public static void parsing(String iostring) {
			ArrayList<ShopList> list = getList();
			Map<Integer, ShopList> map = getMap();
			list.clear();
			map.clear();
			ConvertModel buf = ConvertModel.getInstance();
			buf.setting(iostring);
			ShopList shopList;
			while(!buf.limit()) {
				shopList= new ShopList();
				shopList.Id = buf.readInt();
				shopList.Price = buf.readInt();
				list.add(shopList);
				map.put(shopList.Id, shopList);
			}
		}
	}
	public static void setIOString(String type, String iostring) {
		switch(type) {
			case SAProtoDecode.CardListType: {
				CardList.parsing(iostring);
				break;
			}//先看结果
			case SAProtoDecode.CardSkillType: {
				CardSkill.parsing(iostring);
				break;
			}//先看结果
			case SAProtoDecode.ShopListType: {
				ShopList.parsing(iostring);
				break;
			}//先看结果
		}
	}
}