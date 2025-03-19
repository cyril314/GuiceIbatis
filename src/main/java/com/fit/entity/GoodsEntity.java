package com.fit.entity;

import com.fit.base.BaseEntity;
import java.math.BigDecimal;

/**
 * @AUTO 商品信息
 * @FILE GoodsEntity.java
 * @DATE 2017-10-11 21:27:21
 * @Author AIM
 */
public class GoodsEntity extends BaseEntity<GoodsEntity> {

	private static final long serialVersionUID = 1L;

	/** 商品名称 */
	private String goodsname;
	/** 商品简述 */
	private String description;
	/** 商品详情 */
	private String goodsdetails;
	/** 商品价格 */
	private BigDecimal goodsprice = BigDecimal.ZERO;
	/** 商品上架状态 0:未上架 1:已上架 */
	private Integer status = 0;
	/** 商品主图ID */
	private Long goodspicid;
	/** 商品主图URL */
	private String goodspicurl;
	/** 是否商品URL 0:否 1:是 */
	private Integer isurl = 0;
	/** 热门商品 0:正常 1:热门 */
	private Integer hot = 0;

	/**
	 * 设置：商品名称
	 */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	/**
	 * 获取：商品名称
	 */
	public String getGoodsname() {
		return goodsname;
	}

	/**
	 * 设置：商品简述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取：商品简述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置：商品详情
	 */
	public void setGoodsdetails(String goodsdetails) {
		this.goodsdetails = goodsdetails;
	}

	/**
	 * 获取：商品详情
	 */
	public String getGoodsdetails() {
		return goodsdetails;
	}

	/**
	 * 设置：商品价格
	 */
	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}

	/**
	 * 获取：商品价格
	 */
	public BigDecimal getGoodsprice() {
		return goodsprice;
	}

	/**
	 * 设置：商品上架状态 0:未上架 1:已上架
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：商品上架状态 0:未上架 1:已上架
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：商品主图ID
	 */
	public void setGoodspicid(Long goodspicid) {
		this.goodspicid = goodspicid;
	}

	/**
	 * 获取：商品主图ID
	 */
	public Long getGoodspicid() {
		return goodspicid;
	}

	/**
	 * 设置：商品主图URL
	 */
	public void setGoodspicurl(String goodspicurl) {
		this.goodspicurl = goodspicurl;
	}

	/**
	 * 获取：商品主图URL
	 */
	public String getGoodspicurl() {
		return goodspicurl;
	}

	/**
	 * 设置：是否商品URL 0:否 1:是
	 */
	public void setIsurl(Integer isurl) {
		this.isurl = isurl;
	}

	/**
	 * 获取：是否商品URL 0:否 1:是
	 */
	public Integer getIsurl() {
		return isurl;
	}

	/**
	 * 设置：热门商品 0:正常 1:热门
	 */
	public void setHot(Integer hot) {
		this.hot = hot;
	}

	/**
	 * 获取：热门商品 0:正常 1:热门
	 */
	public Integer getHot() {
		return hot;
	}

}
