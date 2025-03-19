package com.fit.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @AUTO SQL过滤
 * @FILE SQLFilterUtil.java
 * @DATE 2017-10-2 下午9:54:02
 * @Author AIM
 */
public class SQLFilterUtil {

	/**
	 * SQL注入过滤
	 * 
	 * @param str
	 *            待验证的字符串
	 */
	public static String sqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		// 去掉'|"|;|\字符
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");

		// 转换成小写
		str = str.toLowerCase();

		// 非法字符
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop" };

		// 判断是否包含非法字符
		for (String keyword : keywords) {
			if (str.indexOf(keyword) != -1) {
				throw new RuntimeException("包含非法字符");
			}
		}

		return str;
	}
}
