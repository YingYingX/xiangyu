/**
 * 
 */
package com.chinamobile.hnu.xiangyu.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

/**
 * @author songcl
 * @2018年5月18日
 */
public class Utils {
	/**
	 * "yy-mm-dd"
	 */
	private static SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");;
	/**
	 * "yy-mm-dd HH:mm"
	 */
	private static SimpleDateFormat middleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * "yyyyMMdd"
	 */
	private static SimpleDateFormat intFormatter = new SimpleDateFormat("yyyyMMdd");
	/**
	 * "0.00"
	 */
	private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	// private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	/**
	 * 生成json字符串，格式如：{"ret":0,"msg":"密码修改成功"}
	 * 
	 * @param ret
	 * @param msg
	 * @return
	 */
	public static String genJsonString(int ret, String msg) {
		JSONObject json = new JSONObject();
		json.put("ret", ret);
		json.put("msg", msg);
		return json.toString();
	}

	/**
	 * 还没有登录的提示json字符串，格式如：{"ret":1,"msg":"你还没有登录哦"}
	 * 
	 * @return
	 */
	public static String genUnloginJson() {
		return genJsonString(1, "你还没有登录哦");
	}

	/**
	 * 格式化日期时间为“yyyy.MM.dd”格式的字符串。
	 * <p>
	 * 本方法是线程安全的。
	 * </p>
	 * 
	 * @param date
	 *            日期时间。
	 * @return “yyyy.MM.dd”格式的字符串。
	 */
	public static String formatSimple(Date date) {
		if (date == null) {
			return null;
		}
		String retVal = "";
		// 同步访问
		synchronized (simpleFormatter) {
			retVal = simpleFormatter.format(date);
		}
		return retVal;
	}

	/**
	 * 将日期时间为“yyyy.MM.dd”格式的字符串，解析为日期对象。
	 * <p>
	 * 本方法是线程安全的。
	 * </p>
	 * 
	 * @param strDate
	 *            字符串格式的日期时间。
	 * @return 日期对象，解析失败返回null。
	 */
	public static Date parseSimple(String strDate) {
		Date retVal = null;

		// 同步访问
		synchronized (simpleFormatter) {
			try {
				retVal = simpleFormatter.parse(strDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	/**
	 * 格式化日期时间为“yyyy.MM.dd HH:mm:ss”格式的字符串。
	 * <p>
	 * 本方法是线程安全的。
	 * </p>
	 * 
	 * @param date
	 *            日期时间。
	 * @return “yyyy.MM.dd HH:mm:ss”格式的字符串。
	 */
	public static String format(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date can't be empty.");
		}
		String retVal = "";
		// 同步访问
		synchronized (formatter) {
			retVal = formatter.format(date);
		}
		return retVal;
	}

	/**
	 * 格式化日期时间为“yyyy.MM.dd HH:mm”格式的字符串。
	 * <p>
	 * 本方法是线程安全的。
	 * </p>
	 * 
	 * @param date
	 *            日期时间。
	 * @return “yyyy.MM.dd HH:mm”格式的字符串。
	 */
	public static String formatMiddle(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("date can't be empty.");
		}
		String retVal = "";
		// 同步访问
		synchronized (middleFormatter) {
			retVal = middleFormatter.format(date);
		}
		return retVal;
	}

	/**
	 * 将日期时间为“yyyy.MM.dd HH:mm”格式的字符串，解析为日期对象。
	 * <p>
	 * 本方法是线程安全的。
	 * </p>
	 * 
	 * @param strDate
	 *            字符串格式的日期时间。
	 * @return 日期对象，解析失败返回null。
	 */
	public static Date parseMiddle(String strDate) {
		Date retVal = null;

		// 同步访问
		synchronized (middleFormatter) {
			try {
				retVal = middleFormatter.parse(strDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	/**
	 * 将日期时间为“yyyy.MM.dd HH:mm:ss”格式的字符串，解析为日期对象。
	 * <p>
	 * 本方法是线程安全的。
	 * </p>
	 * 
	 * @param strDate
	 *            字符串格式的日期时间。
	 * @return 日期对象，解析失败返回null。
	 */
	public static Date parse(String strDate) {
		Date retVal = null;
		// 同步访问
		synchronized (formatter) {
			try {
				retVal = formatter.parse(strDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	/***
	 * 将日期时间为“yyyy.MM.dd HH:mm:ss”格式的字符串，解析为yyyyMMdd的int类型。
	 * 
	 * @param date
	 * @return
	 */
	public static int parseInt(Date date) {
		String retVal = null;
		synchronized (intFormatter) {
			try {
				retVal = intFormatter.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Integer.parseInt(retVal);
	}

	/**
	 * 计算时间和当前时间的差，显示几分钟，几小时，多少天之前
	 * 
	 * @param date
	 * @return
	 */
	public static String formatAgo(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			return "刚刚";
		}

		if (delta < 60L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}

		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}

		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}

		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}

		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

	/**
	 * 验证是否为手机号码
	 * 
	 * @param phone
	 * @return true:是，false：不是
	 */
	public static boolean checkPhone(String phone) {
		if (phone == null) {
			return false;
		}

		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	/**
	 * 格式化数字为小数点后2位
	 * 
	 * @param f
	 * @return
	 */
	public static String formatDecimal(double d) {
		if (d < 0.00001 && d > -0.00001) {
			return "0.00";
		}

		return decimalFormat.format(d);
	}

	/**
	 * 如果是手机号码，在对中间4位进行掩码，否则返回原来的值
	 * 
	 * @param name
	 * @return
	 */
	public static String maskName(String name) {
		if (Utils.checkPhone(name)) {
			// 手机号码，中间四位使用掩码
			return name.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1****$3");
		} else {
			return name;
		}
	}

	/**
	 * 判断姓名，只能是中文，英文，数字
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkName(String name) {
		if (name == null) {
			return false;
		}

		Pattern p = Pattern.compile("^([\u4e00-\u9fa5]|[a-z·0-9]){1,15}$");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * 从身份证号码中解析出出生年月，如"1979.30"
	 * 
	 * @param idcard
	 * @param fullflag
	 *            true:解析出"1979.03.18",false:解析出"1979.03"
	 * @return
	 */
	public static String parseBirthday(String idcard, boolean fullflag) {
		if (idcard == null || idcard.length() < 14) {
			return "";
		}

		String day = idcard.substring(6, 10) + "." + idcard.substring(10, 12);
		if (fullflag) {
			return day + "." + idcard.substring(12, 14);
		} else {
			return day;
		}
	}

	/**
	 * 验证密码的有效性，规则：包含字母、数字、特殊字符中的至少两种,长度6位以上
	 * 
	 * @param pwd
	 * @return true：有效；false：无效
	 */
	public static boolean checkPassword(String pwd) {
		if (StringUtils.isBlank(pwd) || pwd.length() < 6) {
			return false;
		}

		// 判断密码是否包含数字：包含返回1，不包含返回0
		int i = pwd.matches(".*\\d+.*") ? 1 : 0;

		// 判断密码是否包含字母：包含返回1，不包含返回0
		int j = pwd.matches(".*[a-zA-Z]+.*") ? 1 : 0;

		// 判断密码是否包含特殊符号(~!@#$%^&*()_+|<>,.?/:;'[]{}\)：包含返回1，不包含返回0
		int k = pwd.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;

		if (i + j + k < 2) {
			return false;
		}

		return true;
	}

	/**
	 * 判断是否是邮箱
	 * 
	 * @param email
	 * @return true - 是， false - 否
	 */
	public static boolean checkEmail(String email) {
		// String check = "/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$/";
		String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}

	
	/****
	 * UUID
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
