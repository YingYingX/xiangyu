/**
 * 
 */
package com.chinamobile.hnu.xiangyu.common.service;

import com.chinamobile.hnu.xiangyu.common.pojo.PubBinding;

/**
 * @author douzisong
 * @date 2018年5月15日
 */
public interface PubBindingService {

	/**
	 * 
	 * @param bind
	 */
	void insert(PubBinding bind);

	/**
	 * 
	 * @param id
	 * @return
	 */
	PubBinding selectByPrimaryKey(Long id);

	/**
	 * 依据user id，phone，code选择
	 * 
	 * @param bind
	 * @return
	 */
	PubBinding selectByCnd(PubBinding bind);

	/**
	 * 可依据user id，起始时间查询记录个数
	 * 
	 * @param bind
	 * @return
	 */
	int countByCnd(PubBinding bind);

	/**
	 * 根据user id，phone，code，有效时间，验证是否存在记录
	 * 
	 * @param bind
	 * @return 如果存在，则返回true，否则返回false
	 */
	boolean verify(PubBinding bind);

	/**
	 * 是否超出当然能发验证码的最大量
	 * 
	 * @param phone
	 *            手机号码
	 * @param maximum
	 *            一个手机号码单天发验证码的最大量
	 * @return true-超过了，false-没有超过
	 */
	boolean getBindCountByNow(String phone, int maximum);

	/**
	 * 时间间隔以内指定手机号码是否还可以发送验证码
	 * 
	 * @param phone
	 *            手机号码
	 * @param offset
	 *            时间间隔，单位为分钟
	 * @return
	 */
	boolean getBindByTimeOffset(String phone, int offset);

	/**
	 * 查询用户输入的注册验证码是否正确
	 * 
	 * @param bindingItem
	 * @return
	 */
	boolean checkBindingCode(PubBinding bindingItem);
}
