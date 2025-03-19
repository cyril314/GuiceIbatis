package com.fit.dao;

import org.apache.ibatis.annotations.Mapper;
import com.fit.entity.GoodsEntity;
import com.fit.base.BaseCrudDao;

/**
 * @AUTO 商品信息
 * @FILE GoodsDAO.java
 * @DATE 2017-10-11 21:27:21
 * @Author AIM
 */
@Mapper
public interface GoodsDAO extends BaseCrudDao<GoodsEntity> {
	
}
