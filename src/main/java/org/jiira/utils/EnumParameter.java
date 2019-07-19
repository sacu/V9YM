package org.jiira.utils;

public class EnumParameter {
	public String name;
	public String annotation;
	private EnumParameter(String name, String annotation){
		this.name = name;
		this.annotation = annotation;
	}
	
	public static EnumParameter n(String name, String annotation){
		return new EnumParameter(name, annotation);
	}
}
