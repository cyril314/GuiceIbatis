package com.fit.base;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTO 分页实体类
 * @FILE Pager.java
 * @DATE 2017-8-27 下午1:07:18
 * @Author AIM
 */
public class Pager<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 当前页码 */
	private Integer page = 1;
	/** 分页的总页数 */
	private Integer total = 0;
	/** 每页多少行数据 */
	private Integer rows = 10;
	/** 分页的总条目数 */
	private Integer records = 0;
	/** 数据集合 */
	private List<T> list;
	/** 开始(mysql oracle 使用) */
	private Integer start = 0;
	/** 结束(oracle 使用) */
	private Integer end = 0;

	public Pager() {
		super();
	}

	/**
	 * @param rows
	 *            查询数量
	 * @param start
	 *            查询起始位置
	 */
	public Pager(Integer rows, Integer start) {
		super();
		this.rows = rows;
		this.start = start;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		total = (records + rows - 1) / rows;
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getRecords() {
		return records;
	}

	/**
	 * 设置数据总数
	 */
	public void setRecords(Integer records) {
		this.total = (records + rows - 1) / rows;
		this.records = records;
	}

	public Integer getStart() {
		start = (page - 1) * rows;
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		end = page * rows;
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

}
