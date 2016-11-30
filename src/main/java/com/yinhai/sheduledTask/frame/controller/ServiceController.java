package com.yinhai.sheduledTask.frame.controller;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zrc on 2016/11/28.
 */
public class ServiceController {

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("hello");
        return view;
    }
}
