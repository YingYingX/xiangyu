package com.chinamobile.hnu.xiangyu.util.tree;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.ArrayUtils;

public class mapUtil {
	public mapUtil() {
	}

	public static JSONObject getJsonObjectByMap(Map<String, Object> objectMap) {
		JSONObject jsonObject = new JSONObject();
		Iterator it = objectMap.entrySet().iterator();

		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			jsonObject.put(entry.getKey().toString(), entry.getValue());
		}

		return jsonObject;
	}

	public static JSONObject getJsonObjectByMapNotKeys(
			Map<String, Object> objectMap, String[] notKeys) {
		JSONObject jsonObject = new JSONObject();
		Iterator it = objectMap.entrySet().iterator();

		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			if (!ArrayUtils.contains(notKeys, entry.getKey().toString())) {
				jsonObject.put(entry.getKey().toString(), entry.getValue());
			}
		}

		return jsonObject;
	}

	public static JSONObject getJsonObjectByMapHasKeys(
			Map<String, Object> objectMap, String[] keys) {
		JSONObject jsonObject = new JSONObject();
		String[] var3 = keys;
		int var4 = keys.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			String key = var3[var5];
			if (objectMap.containsKey(key)) {
				jsonObject.put(key, objectMap.get(key));
			}
		}

		return jsonObject;
	}
}
