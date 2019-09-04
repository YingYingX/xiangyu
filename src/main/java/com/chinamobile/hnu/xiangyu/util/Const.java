package com.chinamobile.hnu.xiangyu.util;

/**
 * 公用常量
 */
public class Const {
	// 默认验证码参数名称
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	// 用户类型
	public static final String USER_TYPR = "user_type";
	// 默认错误提示
	public static final String DEFAULT_ERROR = "网络错误,请稍后重试!";
	public static final String OPETION_SUCC = "操作成功";
	public static final String OPETION_FAIL = "操作失败";
	public static final String REQUEST_PARAMS = "提交参数不全";
	public static final String BAD_PARAMS = "参数错误";
	public static final String HAS_DATA = "数据存在";
	public static final String NO_DATA = "数据不存在";
	// 选中
	public static final String CHECKED = "checked";
	public static final String STATUS = "ret";
	public static final String PAGINATION = "pagination";
	public static final String MSG = "msg";
	public static final String DATA = "data";

	public static final String IMG_URL = "http://139.224.233.10";

	// 换行符 从当前系统中获取换行符，默认是"\n"
	public static String LINE_SEPARATOR = System.getProperty("line.separator",
			"\n");

	// 图片前缀路径
	// public static final String IMG_SRC = "http://192.168.1.240:9080/";//
	// http://192.168.1.240:18080

	// app服务用到
	public static final String APP_RESP_RESULT = "result";
	public static final String APP_RESP_MESSAGE = "message";
	public static final String APP_RESP_DATA = "respData";
	public static final int APP_RESP_STATUS_SUCCESS = 0;
	public static final String APP_RESP_STATUS_SUCCESS_MSG = "成功";
	public static final int APP_RESP_STATUS_ERROR = -1; // 异常
	public static final String APP_RESP_STATUS_ERROR_MSG = "网络错误,请稍后重试!";
	public static final int APP_RESP_PARAMS_NULL = 1; // 参数为空
	public static final String APP_RESP_PARAMS_NULL_MSG = "参数为空：%s";
	public static final int APP_RESP_STATUS_FAULT = 2; // 参数错误
	public static final String APP_RESP_STATUS_FAULT_MSG = "参数错误：";
	public static final int APP_RESP_DATA_NULL = 3; // 数据为空
	public static final String APP_RESP_DATA_NULL_MSG = "查询数据为空";
	public static final int APP_RESP_SQL_ERROR = 4;
	public static final int APP_RESP_DATA_EXIST = 5;
	public static final String APP_RESP_SQL_ERROR_MSG = "sql执行失败";
	public static final String APP_REG_SMS_MSG = "尊敬的用户，您本次的注册验证码为：%s（如非本人操作，请勿将验证码告知他人）";
	public static String SMS_URL = "";
	public static String SMS_USERNAME = "";
	public static String SMS_PASSWORD = "";
	public static String SMS_PRODUCTID = "";

	/**
	 * 环境标识—— 0：测试环境 1：正式环境
	 */
	public static int IS_DEMO = 0;

	public static final String IMG_EXT = ".jpg .jpeg .gif .png .bmp";
}
