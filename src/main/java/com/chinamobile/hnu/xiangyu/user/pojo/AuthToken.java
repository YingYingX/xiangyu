/**
 * 
 */
package com.chinamobile.hnu.xiangyu.user.pojo;

/**
 * 用户token表(auth_token)
 * 
 * @author douzisong
 * @date 2018年04月11日
 */
public class AuthToken {
	/**
	 * header attribute name
	 */
	public static final String USER_ACCESS_TOKEN = "XIANGYU-ACCESS-TOKEN";

	public static final String ATTR_SESSION_USER = "MyXiangYuUser";

	private int uid;
	private String token;

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
