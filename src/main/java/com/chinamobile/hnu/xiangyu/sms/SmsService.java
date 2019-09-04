/**
 * 
 */
package com.chinamobile.hnu.xiangyu.sms;

import java.io.IOException;

/**
 * @author douzisong
 * @date 2018年04月23日
 */
public interface SmsService {
	/**
	 * 
	 * @param mobile
	 * @param msg
	 * @return 0:发送成功，非0：发送失败
	 * @throws IOException
	 */
	int sendSms(String mobile, String msg) throws IOException;
}
