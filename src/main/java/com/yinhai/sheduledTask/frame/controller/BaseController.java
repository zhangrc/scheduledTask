package com.yinhai.sheduledTask.frame.controller;

import com.alibaba.fastjson.JSON;
import com.yinhai.sheduledTask.frame.exception.AppException;

import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zrc on 2016/12/1.
 */
public class BaseController {

    public void write(HttpServletResponse response ,Object result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            throw new AppException("调用ajax 返回页面时response 找不到页面！");
        }
        writer.print(JSON.toJSONString(result));
        writer.flush();
    }
}
