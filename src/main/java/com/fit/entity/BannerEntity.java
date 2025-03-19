package com.fit.entity;

import com.fit.base.BaseEntity;
import lombok.Data;

/**
 * @AUTO 横幅
 * @FILE BannerEntity.java
 * @DATE 2017-10-11 22:08:50
 * @Author AIM
 */
@Data
public class BannerEntity extends BaseEntity<BannerEntity> {

	private static final long serialVersionUID = 1L;

	/** 标题 */
	private String title;
	/** 横幅图片 */
	private String bannerurl;
	/** 显示状态 0:不显示 1:显示 */
	private Integer status = 0;
	/** 跳转路径 */
	private String gotourl;

	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置：横幅图片
	 */
	public void setBannerurl(String bannerurl) {
		this.bannerurl = bannerurl;
	}

	/**
	 * 获取：横幅图片
	 */
	public String getBannerurl() {
		return bannerurl;
	}

	/**
	 * 设置：显示状态 0:不显示 1:显示
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：显示状态 0:不显示 1:显示
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：跳转路径
	 */
	public void setGotourl(String gotourl) {
		this.gotourl = gotourl;
	}

	/**
	 * 获取：跳转路径
	 */
	public String getGotourl() {
		return gotourl;
	}

}
