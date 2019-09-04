/**
 * 
 */
package com.um.service;

import com.um.pojo.Alarm;

/**
 * @author The Old Man and the Sea
 *
 *         2018年1月15日
 */
public interface UmengService {

	/***
	 * 安卓自定义播
	 * 
	 * @throws Exception
	 */
	public void sendAndroidCustomizedcast(Alarm alarm) throws Exception;

	/***
	 * 安卓的列播消息推送
	 * 
	 * @throws Exception
	 */
	public void sendAndroidListUnicast(Alarm alarm) throws Exception;

	/**
	 * Android的消息推送
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void sendAndroidBroadcast(Alarm alarm) throws Exception;

	/**
	 * iOS的消息推送
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void sendIOSBroadcast(Alarm alarm) throws Exception;

	/***
	 * IOS自定义播
	 * 
	 * @throws Exception
	 */
	public void sendIOSCustomizedcast(Alarm alarm) throws Exception;
}
