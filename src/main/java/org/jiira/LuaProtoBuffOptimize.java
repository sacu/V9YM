package org.jiira;

/**
 * 用来修改lua protobuf里的命名前缀……
 */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Vector;

public class LuaProtoBuffOptimize {
	public static void main(String[] args) {
		final String luaProtoFilePath = args[0];
		try{
			// proto 读取
			BufferedReader protoRead = new BufferedReader(new FileReader(luaProtoFilePath));
			String line = "";
			final String SA = "SA.";
			final int SA_len = SA.length();
			StringBuffer protoBuffer = new StringBuffer("--自定义结构要大写才帅\n\nlocal SA = {}\n\n");
			final String cstpro = " = protobuf.";
			final String local = "local ";
			int begin, end;
			Vector<String> names = new Vector<>();
			while ((line = protoRead.readLine()) != null) {
				end = line.indexOf(cstpro);
				if(end != -1){
					begin = line.indexOf(local);
					if(begin != -1){
						line = line.replace(local, SA);
						names.addElement(line.substring(line.indexOf(SA) + SA_len, end - SA_len));
					}
				}
				protoBuffer.append(line + "\n");
			}
			protoRead.close();
			line = protoBuffer.toString();
			String name;
			for(int i = 0; i < names.size(); ++i){
				name = names.get(i);
				line = line.replaceAll(name, SA + name);//替换属性设置
				line = line.replaceAll(SA+SA, SA);//替换属性设置
			}
			// 写入
			FileOutputStream javaWrite = new FileOutputStream(luaProtoFilePath);
			javaWrite.write(line.getBytes("UTF8"));
			javaWrite.flush(); // 刷新缓冲区的数据到文件
			javaWrite.close();
		}catch(Exception e){
			
		}
	}
}
