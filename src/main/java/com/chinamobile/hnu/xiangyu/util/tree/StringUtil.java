//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chinamobile.hnu.xiangyu.util.tree;

public class StringUtil {
	public StringUtil() {
	}

	public static String getStringByObject(Object object) {
		return object != null ? object.toString() : null;
	}

	public static Boolean IsNotNullByString(String strValue) {
		return Boolean
				.valueOf(strValue != null && strValue.trim().length() > 0);
	}

	public static Boolean IsNotNullByObject(Object objectValue) {
		return Boolean.valueOf(objectValue != null
				&& objectValue.toString().trim().length() > 0);
	}
}
