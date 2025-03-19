package com.fit.base;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fit.utils.ConverterUtils;
import com.fit.utils.SQLFilterUtil;
import com.fit.utils.StringUtil;

/**
 * @AUTO 查询类
 * @FILE Query.java
 * @DATE 2017-10-8 下午6:55:18
 * @Author AIM
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 当前页码
	private int page;
	// 每页条数
	private int limit;

	public Query(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		this.page = ConverterUtils.toInt(params.get("page"), 1);
		this.limit = ConverterUtils.toInt(params.get("limit"), 10);
		this.put("offset", (page - 1) * limit);
		this.put("page", page);
		this.put("limit", limit);

		// 防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
		String sidx = (String) params.get("sidx");
		String order = (String) params.get("order");
		if (StringUtil.isNotBlank(sidx)) {
			this.put("sidx", SQLFilterUtil.sqlInject(sidx));
		}
		if (StringUtil.isNotBlank(order)) {
			this.put("order", SQLFilterUtil.sqlInject(order));
		}

	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
