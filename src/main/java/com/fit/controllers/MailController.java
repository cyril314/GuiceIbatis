package com.fit.controllers;

import lombok.extern.slf4j.Slf4j;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;

import com.fit.filter.LoginFilter;
import com.fit.service.MailService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @AUTO 发送邮件控制器
 * @FILE MailController.java
 * @DATE 2017-10-17 下午2:13:55
 * @Author AIM
 */
@Slf4j
@Singleton
public class MailController {

    @Inject
    MailService mailService;

    @FilterWith(LoginFilter.class)
    public Result sendMail() {
        log.debug("============== sendMail ==============");
        boolean sendMail = mailService.sendMail("test massage", "12345@qq.com", "12345@foxmail.com", false, "this is test message");
        return Results.json().render(sendMail);
    }
}