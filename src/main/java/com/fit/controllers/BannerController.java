package com.fit.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fit.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;

import com.fit.base.BaseController;
import com.fit.base.Query;
import com.fit.entity.BannerEntity;
import com.fit.filter.LoginFilter;
import com.fit.service.BannerService;
import com.fit.utils.ConverterUtils;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @AUTO 横幅控制器
 * @FILE BannerController.java
 * @DATE 2017-10-11 下午10:11:09
 * @Author AIM
 */
@Slf4j
@Singleton
public class BannerController extends BaseController {

    @Inject
    BannerService bannerService;

    /**
     * 商品管理页面
     */
    @FilterWith(LoginFilter.class)
    public Result bannerManage(Context context) {
        Map<String, Object> map = new HashMap<String, Object>();
        Query query = new Query(map);
        List<BannerEntity> list = bannerService.findList(query);
        map.put("list", list);
        map.put("webPath", getWebPath(context));
        return Results.html().render("map", map);
    }

    /**
     * 根据ID查询信息
     */
    @FilterWith(LoginFilter.class)
    public Result editBanner(@Param("id") Integer id, Context context) {
        BannerEntity entity = new BannerEntity();
        if (id != null) {
            entity = bannerService.get(id);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("webPath", getWebPath(context));
        map.put("entity", entity);
        return Results.html().render("map", map);
    }

    /**
     * 保存
     */
    @FilterWith(LoginFilter.class)
    public Result saveBanner(Context context) {
        Map<String, String[]> parameters = getParameters(context);
        BannerEntity entity = ConverterUtils.converter2Map(parameters, BannerEntity.class);
        if (ConverterUtils.isEmpty(entity.getId())) {
            bannerService.save(entity);
        } else {
            BannerEntity bannerEntity = bannerService.get(ConverterUtils.toInt(entity.getId()));
            try {
                BeanUtils.copyProperties(bannerEntity, entity);
                bannerService.update(bannerEntity);
            } catch (InvocationTargetException e) {
                log.error("信息保存失败", e);
            }
        }

        return Results.redirect("bannerManage");
    }
}