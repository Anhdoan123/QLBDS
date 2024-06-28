package com.javaweb.Until;

public class checkUntil {
	public static boolean valueIsEmpty(String value) {
		if(value.equals("") || value == "null" || value == null) {
			return true;
		}
		return false;
	}
	
	public static boolean isNumber(String value) {
		try {
			int a = Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
