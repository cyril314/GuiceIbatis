package com.fit.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * 日期处理工具类
 * 
 * @Description: 工具类，可以用作获取系统日期、订单编号等(该代码仅供学习和研究支付宝接口使用，只是提供一个参考)
 */
public class DateUtils {

	/** 定义常量 **/
	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	/** 完整时间 yyyy-MM-dd HH:mm:ss.SSS */
	public static final String DATE_LONG_STR = "yyyy-MM-dd kk:mm:ss.SSS";
	/** 年月日(下划线) yyyy-MM-dd */
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	/** 年月日时分秒(无下划线) yyMMddHHmmss */
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String DATE_All_KEY_STR = "yyyyMMddHHmmss";
	/** 年月日(无下划线) yyyyMMdd */
	public static final String dtShort = "yyyyMMdd";
	/** 年 yyyy */
	public static final String year = "yyyy";

	/**
	 * 给指定的日期加上(减去)月份
	 * 
	 * @param date
	 * @param pattern
	 * @param num
	 * @return
	 */
	public static String addMoth(Date date, String pattern, int num) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.MONTH, num);
		return simpleDateFormat.format(calender.getTime());
	}

	/**
	 * 给制定的时间加上(减去)天
	 * 
	 * @param date
	 * @param pattern
	 * @param num
	 * @return
	 */
	public static String addDay(Date date, String pattern, int num) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, num);
		return simpleDateFormat.format(calender.getTime());
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(new Date());
	}

	/**
	 * 获取系统当前时间(指定返回类型)
	 * 
	 * @return
	 */
	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String date) {
		return parse(date, DATE_FULL_STR);
	}

	/**
	 * 指定指定日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 两个时间比较
	 * 
	 * @param
	 * @return
	 */
	public static int compareDateWithNow(Date date) {
		Date now = new Date();
		int rnum = date.compareTo(now);
		return rnum;
	}

	/**
	 * 两个时间比较(时间戳比较)
	 * 
	 * @param
	 * @return
	 */
	public static int compareDateWithNow(long date) {
		long now = dateToUnixTimestamp();
		if (date > now) {
			return 1;
		} else if (date < now) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param date
	 *            需要转换的日期 yyyy-MM-dd HH:mm:ss
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return timestamp;
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param date
	 *            需要转换的日期 yyyy-MM-dd
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String 日期字符串
	 */
	public static String unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sd.format(new Date(timestamp));
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String 日期字符串
	 */
	public static String TimeStamp2Date(long timestamp, String dateFormat) {
		String date = new SimpleDateFormat(dateFormat).format(new Date(timestamp));
		return date;
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String 日期字符串
	 */
	public static String TimeStamp2Date(long timestamp) {
		String date = new SimpleDateFormat(DATE_FULL_STR).format(new Date(timestamp));
		return date;
	}

	/*
	 * 创建的对象
	 */
	public final static DateFormat YYYY_MM_DD_MM_HH_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public final static DateFormat YYYYMMDDMMHHSSSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public final static DateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final DateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return YYYY_MM_DD_MM_HH_SS.format(date);
	}

	/**
	 * 时间转换为yyyy-MM-dd HH:mm:ss格式的date类型
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date strToDate(String dateString) {
		Date date = null;
		try {
			date = YYYY_MM_DD_MM_HH_SS.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间转换为yyyy-MM-dd格式的date类型
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date strToYYMMDDDate(String dateString) {
		Date date = null;
		try {
			date = YYYY_MM_DD.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * 
	 * @return 以yyyyMMddHHmmss为格式的当前系统时间
	 */
	public static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(DATE_All_KEY_STR);
		return df.format(date);
	}

	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * 产生随机的三位数
	 * 
	 * @return
	 */
	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(1000) + "";
	}

	/**
	 * @MethodName: getMonthFirstDay
	 * @Descb: 获取当月的第一天
	 * @Return:
	 */
	public static String getMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Calendar f = (Calendar) cal.clone();
		f.clear();
		f.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		f.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		String firstday = new SimpleDateFormat("yyyy-MM-dd").format(f.getTime());
		firstday = firstday + " 00:00:00";
		return firstday;

	}

	/**
	 * @MethodName: getMonthLastDay
	 * @Descb: 获取当月的最后一天
	 * @Return:
	 */
	public static String getMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		Calendar l = (Calendar) cal.clone();
		l.clear();
		l.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		l.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		l.set(Calendar.MILLISECOND, -1);
		String lastday = new SimpleDateFormat("yyyy-MM-dd").format(l.getTime());
		lastday = lastday + " 23:59:59";
		return lastday;
	}

	/**
	 * @MethodName: getYesterDay
	 * @Descb: 获取昨天日期
	 * @Return:
	 */
	public static Date getYesterDay() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * @Descb 当前时间加上 年份 所得的 日期
	 * @param year
	 * @return
	 */
	public static String getYear(int year) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, +year);
		String theday = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		return theday;
	}

}
