package com.fit.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fit.base.BaseController;
import com.fit.utils.StringUtil;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;
import ninja.session.Session;

/**
 * @AUTO 后台用户登录拦截器
 * @FILE LoginFilter.java
 * @DATE 2017-10-17 下午10:29:08
 * @Author AIM
 */
public class LoginFilter extends BaseController implements Filter {

    private static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public Result filter(FilterChain chain, Context context) {
        log.debug("============== LoginFilter ==============");
        String path = context.getRequestPath();
        if (path.indexOf("admin") != -1 && path.indexOf("main") == -1) {
            Session session = context.getSession();
            String str = session.get("adminUser");
            log.debug(str + " ===== " + session.getData().toString());
            if (StringUtil.isBlank(str)) {
                return Results.html().template("/com/fit/views/AdminController/login.ftl.html").render("webPath", getWebPath(context));
            }
        }
        return chain.next(context);
    }
}