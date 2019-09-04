/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**

 * @ClassName:     SearchUtil.java

 * @Description:   TODO(搜索引擎工具类) 

 * 

 * @author          dev31

 * @version         V1.0  

 * @Date           2018年7月18日 下午8:11:08 

 */
public class ElasticSearchUtil {
	
	
	private static Logger log = LoggerFactory.getLogger(ElasticSearchUtil.class);
	
	
	/****
	 * @param tag tag:团队 tag1:问问  tag2: 专场
	 * @param params 搜索的参数
	 * @param returnPropertys 需要返回的属性
	 * @param form
	 * @param
	 * @return
	 */
	public static String sendPost(String tag,Map<String,String> params,Map<String,String> returnPropertys,Integer from,Integer size){
		String service = "127.0.0.1";
		String path = "http://"+service+":9200/"+("tag1".equals(tag)?"club1":"club")+"/"+tag+"/_search";
		StringBuilder sb = new StringBuilder();
		sb.append("{ \"query\" : {\"bool\": {\"should\": [");
		int index = 1;
		for (String key : params.keySet()) {
			sb.append("{ \"match\": {\"")
			.append(key).append("\":\"").append(params.get(key)).append("\"}}");
			if(index != params.size()){
				sb.append(",");
			}
			index++;
		}
		sb.append("]}},\"from\":").append(from)
		.append(",\"size\":").append(size)
		.append("}");
		String body = sb.toString();
		log.info(" Search elasticsearch url ------------------> {} ----------->body:{}",path,body);
		return HttpUtil.sendPost(path, body);
	}
	
	
	/****
	 * 获得id集合
	 * @param params
	 * @param returnPropertys
	 * @param form
	 * @param size
	 * @return
	 */
	public static List<Long> getIdList(String tag,Map<String,String> params,Map<String,String> returnPropertys,Integer form,Integer size){
		List<Long> list = new ArrayList<>();
		try{
			String result = sendPost(tag,params,returnPropertys,form,size);
//			log.info("Search elasticsearch result --------------------- >{}",result);
			JSONObject obj1 = JSONObject.fromObject(result);
			JSONObject obj2 = JSONObject.fromObject(obj1.getString("hits"));
			if(obj2.getInt("total") <= 0){
				return null;
			}else{
				JSONArray arrayObj = JSONArray.fromObject(obj2.getString("hits"));
//				log.info("arrayObj ----------->{}",arrayObj);
				for (Object object : arrayObj) {
					JSONObject obj3 = JSONObject.fromObject(object);
//					log.info("obj3 ----------->{}",obj3);
					list.add(obj3.getLong("_id"));
				}
			}
			log.info("Search elasticsearch idList size -------------- >{}",list.size());
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		return list;
	}
	

}
