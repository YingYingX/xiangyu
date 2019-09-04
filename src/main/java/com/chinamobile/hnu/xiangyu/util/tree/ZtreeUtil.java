package com.chinamobile.hnu.xiangyu.util.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2018-2-2.
 */
public class ZtreeUtil {

	private String rootKey;
	private String keyName;
	private Integer keyMode = Integer.valueOf(0);
	private String[] keys;

	public ZtreeUtil(String rootKey, String keyName) {
		this.rootKey = rootKey;
		this.keyName = keyName;
	}

	public ZtreeUtil(String rootKey, String keyName, Integer keyMode,
			String[] keys) {
		this.rootKey = rootKey;
		this.keyName = keyName;
		this.keyMode = keyMode;
		this.keys = keys;
	}

	public JSONArray listMapToJSONArray(List<Map<String, Object>> maps,
			String rootValue, String flag) {
		JSONArray jsonArray = new JSONArray();
		Iterator var4 = maps.iterator();
		while (var4.hasNext()) {
			Map<String, Object> objectMap = (Map) var4.next();
			if (StringUtils.isNotBlank(flag) && ("all").equals(flag)) {
				if (StringUtil.IsNotNullByObject(objectMap.get(this.rootKey))
						.booleanValue()
						&& rootValue.equals(objectMap.get(this.rootKey)
								.toString())) {
					new JSONObject();
					JSONObject rootJsonObject;
					if (this.keyMode.equals(Integer.valueOf(1))) {
						rootJsonObject = mapUtil.getJsonObjectByMapHasKeys(
								objectMap, this.keys);
					} else if (this.keyMode.equals(Integer.valueOf(2))) {
						rootJsonObject = mapUtil.getJsonObjectByMapNotKeys(
								objectMap, this.keys);
					} else {
						rootJsonObject = mapUtil.getJsonObjectByMap(objectMap);
					}
					if (StringUtil.IsNotNullByObject(
							objectMap.get(this.keyName)).booleanValue()) {
						JSONArray childJsonObject = this.listMapToJSONArray(
								maps, objectMap.get(this.keyName).toString(),
								"all");
						if (childJsonObject.size() > 0) {
							rootJsonObject.put("children", childJsonObject);
						}
					}
					jsonArray.add(rootJsonObject);
				}
			} else {
				if (StringUtil.IsNotNullByObject(objectMap.get(this.keyName))
						.booleanValue()
						&& rootValue.equals(objectMap.get(this.keyName)
								.toString())) {
					new JSONObject();
					JSONObject rootJsonObject;
					if (this.keyMode.equals(Integer.valueOf(1))) {
						rootJsonObject = mapUtil.getJsonObjectByMapHasKeys(
								objectMap, this.keys);
					} else if (this.keyMode.equals(Integer.valueOf(2))) {
						rootJsonObject = mapUtil.getJsonObjectByMapNotKeys(
								objectMap, this.keys);
					} else {
						rootJsonObject = mapUtil.getJsonObjectByMap(objectMap);
					}
					if (StringUtil.IsNotNullByObject(
							objectMap.get(this.keyName)).booleanValue()) {
						JSONArray childJsonObject = this.listMapToJSONArray(
								maps, objectMap.get(this.keyName).toString(),
								"all");
						if (childJsonObject.size() > 0) {
							rootJsonObject.put("children", childJsonObject);
						}
					}
					jsonArray.add(rootJsonObject);
				}
			}
		}
		return jsonArray;
	}

}
