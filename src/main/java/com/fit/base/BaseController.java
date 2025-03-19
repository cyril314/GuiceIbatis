package com.fit.base;

import java.io.File;
import java.util.Map;

import org.apache.commons.fileupload.FileItemIterator;

import ninja.Context;

/**
 * @AUTO 基类控制器
 * @FILE BaseController.java
 * @DATE 2017-10-2 下午2:06:13
 * @Author AIM
 */
public class BaseController {

	public static final String LIST = "list";
	public static final String EDIT = "edit";
	public static final String ADD = "add";
	public static final String SELECT = "select";
	public static final String QUERY = "query";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String INDEX = "index";
	public static final String MAIN = "main";
	public static final String JSON = "json";

	/**
	 * 请求的方案所看到的服务器(如。“http”或“https”)
	 */
	public String getScheme(Context context) {
		return context.getScheme();
	}

	/**
	 * 包含客户机的IP地址,发送请求。考虑X-Forwarded-For头如果配置
	 */
	public String getRemoteAddr(Context context) {
		return context.getRemoteAddr();
	}

	/**
	 * 在服务器的路径。并排除任何容器设置上下文的前缀
	 */
	public String getRequestPath(Context context) {
		return context.getRequestPath();
	}

	/**
	 * 领先的上下文路径“/”或“如果运行在根
	 */
	public String getContextPath(Context context) {
		return context.getContextPath();
	}

	/**
	 * 服务器的主机名
	 */
	public String getHostname(Context context) {
		return context.getHostname();
	}

	/**
	 * 项目访问路径
	 */
	public String getWebPath(Context context) {
		return getScheme(context) + "://" + getHostname(context) + getContextPath(context) + File.separator;
	}

	/**
	 * 获取所有从请求参数
	 */
	public Map<String, String[]> getParameters(Context context) {
		return context.getParameters();
	}

	@SuppressWarnings("deprecation")
	public FileItemIterator getFile(Context context) {
		return context.getFileItemIterator();
	}
}
