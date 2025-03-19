package com.fit.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @AUTO web工具
 * @FILE WebUtil.java
 * @DATE 2017-8-30 下午6:16:08
 * @Author AIM
 */
public class WebUtil {

	/**
	 * 类型转换为MAP
	 */
	public static Map<String, String> getPraramsAsMap(Map<String, String[]> paramap) {
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> keyIterator = paramap.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) paramap.get(key))[0];
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 请求参数转换为MAP
	 */
	public static Map<String, String> getPraramsAsMap(HttpServletRequest request) {
		Map<String, String[]> paramap = request.getParameterMap();
		return getPraramsAsMap(paramap);
	}

	/**
	 * 请求获取IP地址
	 */
	public static String getIpAddr(ServletRequest httpServletRequest) {
		HttpServletRequest request = (HttpServletRequest) httpServletRequest;
		String ip = request.getHeader("HTTP_CLIENT_IP");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("X-Real-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}