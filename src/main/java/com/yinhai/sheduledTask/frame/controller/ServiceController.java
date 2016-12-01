package com.yinhai.sheduledTask.frame.controller;

import com.yinhai.sheduledTask.frame.plugin.network.service.NetworkService;
import com.yinhai.sheduledTask.frame.plugin.network.service.impl.NetworkServiceImpl;
import com.yinhai.sheduledTask.frame.plugin.network.util.ConstUtil;
import com.yinhai.sheduledTask.frame.plugin.network.util.ParamUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zrc on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/service")
public class ServiceController extends BaseController{

    @RequestMapping(value = "/postTransferData")
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        NetworkService network = new NetworkServiceImpl();
//        String json = network.receive(ConstUtil.SECURITY_LEVEL_ONE,request,response);
//        System.out.println(json);
        String param = request.getParameter(ParamUtil.PARAM_NAME);
        System.out.println(param);
    }

    @RequestMapping(value = "/hello")
    public String toHello() {
        // return "hello" //跳转到webapp/service/hello.jsp
       return "/hello"; //跳转到根目录下的hello.jsp
    }
}
