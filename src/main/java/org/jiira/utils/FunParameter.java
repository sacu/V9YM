package org.jiira.utils;

public class FunParameter {
	public String type;
	public int min;
	public int max;
	public String annotation;
	private FunParameter(String type, int min, int max, String annotation){
		this.type = type;
		this.min = min;
		this.max = max;
		this.annotation = annotation;
	}
	
	public static FunParameter n(String type, int min, int max, String annotation){
		return new FunParameter(type, min, max, annotation);
	}
}
