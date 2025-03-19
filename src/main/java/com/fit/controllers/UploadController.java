package com.fit.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.uploads.FileItem;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.util.Streams;

import com.fit.base.BaseController;
import com.fit.filter.LoginFilter;
import com.fit.service.GoodsService;
import com.fit.utils.DateUtils;
import com.fit.utils.FileUtil;
import com.fit.utils.IdGen;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @AUTO 上传控制器
 * @FILE UploadController.java
 * @DATE 2017-10-8 下午7:35:22
 * @Author AIM
 */
@Slf4j
@Singleton
public class UploadController extends BaseController {

    @Inject
    GoodsService goodsService;

    private String resourcePath = "assets\\\\";
    private String uploadFile = resourcePath + "uploadFiles";

    // 存储参数的集合
    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * 返回参数map集合,自定义后上传文件,上传成功则返回文件名
     */
    public Map<String, Object> getParameterMap() {
        return params;
    }

    /**
     * 保存所有表单的from提交
     */
    @FilterWith(LoginFilter.class)
    public Result uploadFormFinish(Context context) throws Exception {
        log.debug("================= 多图片上传 =================");
        // Make sure the context really is a multipart context...
        String fname = IdGen.uuid();
        String sysDate = DateUtils.getDate();
        String serverpath = FileUtil.getWebSysPath() + File.separator + uploadFile + File.separator + sysDate;
        FileUtil.judeDirExists(serverpath);
        params.put("message", "上传失败");
        params.put("code", "-1");
        if (context.isMultipart()) {
            // 这是我们用来迭代请求内容的迭代器
            FileItemIterator fileItemIterator = getFile(context);
            if (fileItemIterator.hasNext()) {
                while (fileItemIterator.hasNext()) {
                    FileItemStream item = fileItemIterator.next();
                    // 表单标签name属性的值
                    String name = item.getFieldName();
                    log.debug("================= 表单标签name属性的值 =================> " + name);
                    // 打开对应的输入流
                    InputStream is = item.openStream();
                    // 文件的类型
                    String contentType = item.getContentType();
                    if (contentType != null) {

                    } else {

                    }
                    // 判断是否为普通表单域则返回true，否则文件上传表单域返回false
                    if (item.isFormField()) {
                        // do something with the form field
                        setFormParam(name, is);
                    } else {
                        // process file as input stream
                        if (is.available() > 0) {// 如果输出流的内容大于0
                            fname = item.getName();// 获取文件名
                            serverpath = serverpath + File.separator + fname;
                            Streams.copy(is, new FileOutputStream(serverpath), true);// 拷贝内容到上传路径
                            String[] split = serverpath.split(resourcePath);
                            params.put(name, split[1]);// 把文件名设置进request中
                            params.put("message", "上传成功");
                            params.put("code", "200");
                        }
                    }
                }
            } else {
                return Results.badRequest().json();
            }
        }
        // We always return ok. You don't want to do that in production ;)
        return Results.ok().json().render(params);
    }

    public Result uploadInputStream(Context context, @Param("upfile") InputStream upfile) throws Exception {

        return Results.ok();
    }

    public Result uploadFile(Context context, @Param("upfile") File upfile) throws Exception {

        return Results.ok();
    }

    public Result uploadFileItem(Context context) throws Exception {
        // 文件信息类
        FileItem upfile = context.getParameterAsFileItem("upfile");
        String fname = IdGen.uuid();
        String sysDate = DateUtils.getDate();
        String serverpath = FileUtil.getWebSysPath() + File.separator + "WebRoot/uploadFile" + File.separator + sysDate + File.separator;
        // FileUtil.judeDirExists(serverpath);
        InputStream inputStream = upfile.getInputStream();
        FileUtil.writeToLocal(serverpath + fname, inputStream);
        return Results.ok();
    }

    /**
     * 处理非上传的表单
     *
     * @param name
     * @param is
     */
    private void setFormParam(String name, InputStream is) {
        try {
            if (params.containsKey(name)) {// 判断当前值name是否已经存储过
                String[] values = (String[]) params.get(name);// 取出已经存储过的值
                values = Arrays.copyOf(values, values.length + 1);// 把当前数组扩大
                values[values.length - 1] = Streams.asString(is);// 增加新值
                params.put(name, values);// 重新添加到map中
            } else {
                params.put(name, new String[]{Streams.asString(is)});// 直接存入参数中
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}