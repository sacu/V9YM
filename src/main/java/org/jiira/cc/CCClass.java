package org.jiira.cc;

import org.jiira.utils.Lang.LanguageEmum;

public class CCClass {

	private static CCClass instance;

	private CCClass() {
	}

	public static CCClass getInstance() {
		if (null == instance) {
			instance = new CCClass();
		}
		return instance;
	}

	public void compileBegin(StringBuffer[] scripts, LanguageEmum[] languages, String className) {
		int len = scripts.length;
		for(int i = 0; i < len; ++i){
			if(languages[i] == LanguageEmum.CSharp){
				scripts[i].append("\n\tpublic class " + className + " {");
			}
		}
	}
	public void compileEnd(StringBuffer[] scripts, LanguageEmum[] languages){
		int len = scripts.length;
		for(int i = 0; i < len; ++i){
			if(languages[i] == LanguageEmum.CSharp){
				scripts[i].append("\n\t}");
			}
		}
	}
}
