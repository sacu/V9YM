package org.jiira.cc;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Vector;

import org.jiira.utils.Lang.LanguageEmum;


public class CCCSharpWrap {
	private static CCCSharpWrap instance;

	private CCCSharpWrap() {
	}

	public static CCCSharpWrap getInstance() {
		if (null == instance) {
			instance = new CCCSharpWrap();
		}
		return instance;
	}

	public void compile(Vector<String> protoTitles, StringBuffer[] scripts, LanguageEmum[] languages, String path) {
		String line;
		try {
			BufferedReader csharpWrapRead = new BufferedReader(new FileReader(path));
			StringBuffer csharpWrapBuffer = new StringBuffer();
			StringBuffer csharpWrapAdd = new StringBuffer("//protobuf begin\n\t\t");
			while ((line = csharpWrapRead.readLine()) != null) {
				csharpWrapBuffer.append(line + "\n");
			}
			csharpWrapRead.close();
			int protoTypeBeign = csharpWrapBuffer.indexOf("//protobuf begin");
			int protoTypeEnd = csharpWrapBuffer.indexOf("//protobuf end");

			for(int i = 0; i < protoTitles.size(); ++i){
				csharpWrapAdd.append("_GT(typeof(" + protoTitles.get(i) + ")),\n\t\t");
			}

			csharpWrapBuffer.replace(protoTypeBeign, protoTypeEnd, csharpWrapAdd.toString());
			FileOutputStream csharpWrapWrite = new FileOutputStream(path);
			csharpWrapWrite.write(csharpWrapBuffer.toString().getBytes("gbk"));
			csharpWrapWrite.flush(); // ˢ�»����������ݵ��ļ�
			csharpWrapWrite.close();
		} catch (Exception e) {

		}
	}
}
