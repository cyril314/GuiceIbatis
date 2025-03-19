package com.fit.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.utils.NinjaConstant;
import ninja.utils.NinjaProperties;
import ninja.utils.Message;

import com.fit.base.Ajax;
import com.fit.base.BaseController;
import com.fit.base.ModelAndView;
import com.fit.base.Pager;
import com.fit.base.Query;
import com.fit.entity.BannerEntity;
import com.fit.entity.GoodsEntity;
import com.fit.service.BannerService;
import com.fit.service.GoodsService;
import com.fit.utils.ConverterUtils;
import com.fit.utils.SQLFilterUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @AUTO 商品控制器
 * @FILE GoodsAction.java
 * @DATE 2017-9-8 上午11:00:31
 * @Author AIM
 */
@Slf4j
@Singleton
public class GoodsController extends BaseController {

    @Inject
    GoodsService goodsService;
    @Inject
    BannerService bannerService;
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * 商品列表
     */
    public Result goodsIndex() {
        List<GoodsEntity> findList = goodsService.querySQL("SELECT * FROM `goods` WHERE status=1 ORDER BY creatDate DESC LIMIT 0,6");
        List<GoodsEntity> list = goodsService.querySQL("SELECT * FROM `goods` WHERE status=1 and hot=1 ORDER BY creatDate DESC LIMIT 0,3");
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", findList);
        map.put("hot", list);
        modelAndView.setModel(map);
        return Results.html().render("map", modelAndView);
    }

    /**
     * 条件查询商品列表
     */
    public Result conditionQry(Context context, @Param("currentPage") Integer currentPage, @Param("goodName") String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<BannerEntity> bannerList = bannerService.findList(map);
        Pager<GoodsEntity> pager = new Pager<GoodsEntity>();
        if (ConverterUtils.isNotEmpty(currentPage)) {
            pager.setPage(Integer.parseInt(currentPage.toString().trim()));
        }
        if (ConverterUtils.isNotEmpty(name)) {
            map.put("goodName", SQLFilterUtil.sqlInject(name));
        }
        map.put("page", pager.getPage());
        map.put("limit", 6);
        Query query = new Query(map);
        List<GoodsEntity> list = goodsService.findList(query);
        pager.setList(list);
        pager.setRecords(goodsService.findCount());
        map.put("pager", pager);
        map.put("bannerList", bannerList);
        map.put("webPath", getWebPath(context));
        return Results.html().render("map", map);
    }

    /**
     * 商品详情
     */
    public Result goodsDetails(@Param("id") Integer id) {
        GoodsEntity entity = goodsService.get(id);
        if (entity == null) {
            Message message = new Message("没有找到商品哦！");
            return Results.forbidden().render("contextPath", "goodsList").render(message).template(ninjaProperties.getWithDefault(NinjaConstant.LOCATION_VIEW_HTML_FORBIDDEN_KEY, NinjaConstant.LOCATION_VIEW_FTL_HTML_FORBIDDEN));
        }
        return Results.html().render(entity);
    }

    /**
     * 首页
     */
    public Result index() {
        log.debug("============== index ==============");
        return Results.html();
    }

    /**
     * 测试数据
     *
     * @param aa 参数
     */
    public Result helloWorldJson(@Param("aa") String aa) {// 接收aa参数
        Ajax simplePojo = new Ajax();
        simplePojo.setMsg("Hello World! Hello Json!");
        return Results.json().render(simplePojo);
    }
}