/**
 * 
 */
package com.chinamobile.hnu.xiangyu.common.service;

/**
 * @ClassName:     PubPointService.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author          dev31
 * @version         V1.0  
 * @Date           2018年7月30日 下午4:09:58 
 */
public interface PubPointService {
	
	
	/****
	 * 增加记录
	 * @param uid
	 * @param type
	 * @param ruleid
	 * @param bizid
	 */
	void addPoint(int uid,int ruleid,String bizid,String extid);

}
