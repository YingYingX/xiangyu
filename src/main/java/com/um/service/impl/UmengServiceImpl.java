/**
 * 
 */
package com.um.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.um.pojo.Alarm;
import com.um.push.AndroidNotification;
import com.um.push.PushClient;
import com.um.push.android.AndroidCustomizedcast;
import com.um.push.android.AndroidUnicast;
import com.um.push.ios.IOSBroadcast;
import com.um.push.ios.IOSCustomizedcast;
import com.um.service.UmengService;

/**
 * @author The Old Man and the Sea
 *
 *         2018年1月15日
 */
@Service
public class UmengServiceImpl implements UmengService {

	private static final Logger logger = LoggerFactory
			.getLogger(UmengServiceImpl.class);

	/**
	 * 友盟推送android的app信息
	 */
	private String androidAppKey;
	private String androidAppSecret;

	/**
	 * 友盟推送ios的app信息
	 */
	private String iosAppKey;
	private String iosAppSecret;

	private PushClient client = new PushClient();

	/**
	 * @return the androidAppKey
	 */
	public String getAndroidAppKey() {
		return androidAppKey;
	}

	/**
	 * @param androidAppKey
	 *            the androidAppKey to set
	 */
	public void setAndroidAppKey(String androidAppKey) {
		this.androidAppKey = androidAppKey;
	}

	/**
	 * @return the androidAppSecret
	 */
	public String getAndroidAppSecret() {
		return androidAppSecret;
	}

	/**
	 * @param androidAppSecret
	 *            the androidAppSecret to set
	 */
	public void setAndroidAppSecret(String androidAppSecret) {
		this.androidAppSecret = androidAppSecret;
	}

	/**
	 * @return the iosAppKey
	 */
	public String getIosAppKey() {
		return iosAppKey;
	}

	/**
	 * @param iosAppKey
	 *            the iosAppKey to set
	 */
	public void setIosAppKey(String iosAppKey) {
		this.iosAppKey = iosAppKey;
	}

	/**
	 * @return the iosAppSecret
	 */
	public String getIosAppSecret() {
		return iosAppSecret;
	}

	/**
	 * @param iosAppSecret
	 *            the iosAppSecret to set
	 */
	public void setIosAppSecret(String iosAppSecret) {
		this.iosAppSecret = iosAppSecret;
	}

	/***
	 * 安卓自定义播
	 * 
	 * @throws Exception
	 */
	public void sendAndroidCustomizedcast(Alarm alarm) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(
				androidAppKey, androidAppSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		// customizedcast.setAlias(alarm.getToken(), "zy");
		customizedcast.setTicker("系统消息");
		customizedcast.setTitle("系统消息");
		customizedcast.setText(alarm.getMsg());
		customizedcast.goAppAfterOpen();
		customizedcast
				.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		// customizedcast.setExtraField("peopleid", alarm.getPeopleid());
		client.send(customizedcast);
	}

	/***
	 * 安卓的列播消息推送
	 * 
	 * @throws Exception
	 */
	public void sendAndroidListUnicast(Alarm alarm) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(androidAppKey,
				androidAppSecret);
		// TODO Set your device token
		// unicast.setDeviceToken(alarm.getToken());
		// unicast.setTicker("系统消息");
		// unicast.setTitle("老人异常通知");
		// unicast.setText(alarm.getMsg());
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}

	/**
	 * Android的广播消息推送
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void sendAndroidBroadcast(Alarm alarm) throws Exception {
		AndroidCustomizedcast broadcast = new AndroidCustomizedcast(
				androidAppKey, androidAppSecret);
		broadcast.setTicker("[相遇]系统消息");
		broadcast.setTitle(alarm.getTitle());
		broadcast.setText(alarm.getMsg());
		broadcast.setExtraField("type", alarm.getType().toString());
		broadcast.setAlias(alarm.getReceiveId().toString(), "uid");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// 正式模式
		// broadcast.setTestMode();
		// Set customized fields
		broadcast.setExtraField("what", "2");
		logger.info("=================== umeng push msg:" + alarm.getMsg());
		client.send(broadcast);
	}

	/**
	 * iOS的广播消息推送
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void sendIOSBroadcast(Alarm alarm) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(iosAppKey, iosAppSecret);
		broadcast.setAlert(alarm.getMsg());
		broadcast.setBadge(0);
		broadcast.setSound("default");
		// 正式模式
		// broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("what", "2");
		logger.info("=================== umeng push ios msg:" + alarm.getMsg());
		client.send(broadcast);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ruigan.sensing.lampApi.service.UmengService#sendIOSCustomizedcast
	 * (com.ruigan.sensing.lampApi.pojo.Alarm)
	 */
	/**
	 * iOS的自定义推送
	 * 
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void sendIOSCustomizedcast(Alarm alarm) throws Exception {
		// TODO Auto-generated method stub
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(iosAppKey,
				iosAppSecret);
		// TODO Set your alias here, and use comma to split them if there are
		// multiple alias.
		// And if you have many alias, you can also upload a file containing
		// these alias, then
		// use file_id to send customized notification.
		// customizedcast.setAlias(alarm.getToken(), "zy");
		customizedcast.setAlert(alarm.getMsg());
		customizedcast.setAlias(alarm.getReceiveId().toString(), "uid");
		customizedcast.setCustomizedField("type", alarm.getType().toString());
		// customizedcast.setBadge(alarm.getBadge());
		customizedcast.setSound("default");
		// 正式服务器为setProductionMode();
		customizedcast.setProductionMode();

		// customizedcast.setTestMode();
		// TODO Set 'production_mode' to 'false' if it's a test device.
		// For how to register a test device, please see the developer doc.
		// customizedcast.setCustomizedField("peopleid", alarm.getPeopleid());
		logger.info("=================== umeng push ios msg:" + alarm.getMsg());
		client.send(customizedcast);

	}
}
