/**
 * 
 */
package com.chinamobile.hnu.xiangyu.sms;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruanwei.interfacej.SmsClientSend;

/**
 * @author douzisong
 * @date 2018年04月21日
 */
public class TelhkSmsService implements SmsService {

	private final static Logger logger = LoggerFactory.getLogger(TelhkSmsService.class);

	/**
	 * 短信账号信息
	 */
	private String url;
	private String userid;
	private String account;
	private String password;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ecobuild.common.sms.SmsService#sendSms(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int sendSms(String mobile, String msg) throws IOException {
		String send = SmsClientSend.sendSms(url, "send", userid, account, password, mobile, msg);
		logger.info("sms send result:{}", send);

		StringReader reader = new StringReader(new String(send.getBytes("UTF-8")));
		SAXBuilder builder = new SAXBuilder();
		try {
			Document document = builder.build(reader);
			Element message = document.getRootElement().getChild("message");
			if (message != null && "ok".equalsIgnoreCase(message.getText())) {
				return 0;
			}
		} catch (JDOMException | IOException ex) {
			logger.error("parse sms send result exeption", ex);
		}

		return 1;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
