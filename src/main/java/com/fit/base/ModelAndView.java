package com.fit.base;

import java.util.Collections;
import java.util.Map;

/**
 * @AUTO ModelAndView对象包含一个模型和一个视图。模型是代表作为一个 地图,观点是表示为字符串。
 * @FILE ModelAndView.java
 * @DATE 2017-8-30 下午2:50:02
 * @Author AIM
 */
public final class ModelAndView {

	/** 视图 */
	private String view;
	/** 模型 */
	private Map<String, Object> model;

	public ModelAndView() {
		this.model = Collections.emptyMap();
	}

	/**
	 * 构造一个空模型视图.
	 * 
	 * @param view
	 *            视图的逻辑名称
	 */
	public ModelAndView(String viewName) {
		this.view = viewName;
		this.model = Collections.emptyMap();
	}

	/**
	 * 构造一个视图和模型.
	 * 
	 * @param view
	 *            视图的逻辑名称.
	 * @param model
	 *            模型作为MAP.
	 */
	public ModelAndView(String viewName, Map<String, Object> model) {
		this.view = viewName;
		this.model = model;
	}

	/**
	 * 获取视图的名称.
	 */
	public String getView() {
		return view;
	}

	/**
	 * 获取模型.
	 */
	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

}
