package org.jiira.cc;


import java.io.FileOutputStream;

import org.jiira.utils.Lang.LanguageEmum;

public class CCRelease {
	private static CCRelease instance;

	private CCRelease() {
	}

	public static CCRelease getInstance() {
		if (null == instance) {
			instance = new CCRelease();
		}
		return instance;
	}
	
	public void compile(StringBuffer[] scripts, LanguageEmum[] languages, String[] paths, String className){
		LanguageEmum language;
		StringBuffer script;
		StringBuffer buffer = null;
		int len = scripts.length;
		for(int i = 0; i < len; ++i){
			language = languages[i];
			script = scripts[i];
			switch(language){
				case Java:{
					buffer = new StringBuffer("package org.jiira.utils;"
							+ "\nimport org.jiira.protobuf.ProtobufType.*;"
							+ "\nimport com.google.protobuf.InvalidProtocolBufferException;"
							+ "\npublic class " + className + " {");
					buffer.append(script);
					buffer.append("\n}");
					break;
				}
				case CSharp:{
					buffer = new StringBuffer("using org.jiira.protobuf;\nusing System.Collections.Generic;\nnamespace Sacu.Utils{");
					buffer.append(script);
					buffer.append("\n}");
					break;
				}
				case Lua:{
					buffer = new StringBuffer("require \"org.jiira.protobuf.protobuf_pb\"\nCommandCollection = {}");
					buffer.append(script);
					break;
				}
			}
			write(paths[i], buffer.toString());
		}
	}
	private void write(String path, String buffer){
		// 写入
		FileOutputStream javaWrite;
		try {
			javaWrite = new FileOutputStream(path);
			javaWrite.write(buffer.getBytes("UTF8"));
			javaWrite.flush(); // 刷新缓冲区的数据到文件
			javaWrite.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
