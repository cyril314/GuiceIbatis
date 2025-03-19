package com.fit.dao;

import org.apache.ibatis.annotations.Mapper;
import com.fit.entity.BannerEntity;
import com.fit.base.BaseCrudDao;

/**
 * @AUTO 横幅
 * @FILE BannerDAO.java
 * @DATE 2017-10-11 22:08:50
 * @Author AIM
 */
@Mapper
public interface BannerDAO extends BaseCrudDao<BannerEntity> {
	
}
