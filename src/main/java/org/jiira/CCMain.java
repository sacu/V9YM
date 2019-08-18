package org.jiira;

/**
 * 将protobuf文件生成lua、c#、java三语言的公共文件集合（collection）
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import org.jiira.cc.CCClass;
import org.jiira.cc.CCCost;
import org.jiira.cc.CCEnum;
import org.jiira.cc.CCFun;
import org.jiira.cc.CCRelease;
import org.jiira.utils.Lang;
import org.jiira.utils.Lang.LanguageEmum;

public class CCMain {
	public static void main(String[] args) {
		// 读取proto文件
		final String protoPath = args[0];
		final String javaCommandPath = args[1];
		final String csharpCommandPath = args[2];
		final String luaCommandPath = args[3];
//		final String csharpWrapPath = args[4];
		
		try {
			// proto 读取
			BufferedReader protoRead = new BufferedReader(new FileReader(protoPath));
			String line = "";
			StringBuffer protoBuffer = new StringBuffer();
			while ((line = protoRead.readLine()) != null) {
				protoBuffer.append(line);
			}
			protoRead.close();
			// csharp wrap 读取
			
			// 切割message头为数组
			String[] strs = protoBuffer.toString().split("message");
			String str;
			int end;
			Vector<String> protoTitles = new Vector<String>();
			for(int i = 0; i < strs.length; ++i){
				str = strs[i];
				end = str.indexOf("{");
				if (end == -1) {
					continue;
				}
				protoTitles.addElement(str.substring(0, end).trim());
			}
			
			//路径
			String[] paths = {javaCommandPath, csharpCommandPath, luaCommandPath};
			//类型
			LanguageEmum[] languages = {LanguageEmum.Java, LanguageEmum.CSharp, LanguageEmum.Lua};
			//代码
			StringBuffer[] scripts = {new StringBuffer(), new StringBuffer(), new StringBuffer()};

			CCEnum.getInstance().compile(protoTitles, scripts, languages, Lang.ClassName);
			CCClass.getInstance().compileBegin(scripts, languages, Lang.ClassName);
			CCCost.getInstance().compile(scripts, languages, Lang.ClassName);
			CCFun.getInstance().compile(protoTitles, scripts, languages, Lang.ClassName);
			CCClass.getInstance().compileEnd(scripts, languages);
			CCRelease.getInstance().compile(scripts, languages, paths, Lang.ClassName);
			
			//不需要了ulua才会用到
//			CCCSharpWrap.getInstance().compile(protoTitles, scripts, languages, csharpWrapPath);//转换文件 修改（可不生成）
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
