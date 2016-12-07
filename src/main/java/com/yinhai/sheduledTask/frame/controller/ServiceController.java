package com.yinhai.sheduledTask.frame.controller;

import com.yinhai.sheduledTask.frame.plugin.network.service.NetworkService;
import com.yinhai.sheduledTask.frame.plugin.network.service.impl.NetworkServiceImpl;
import com.yinhai.sheduledTask.frame.plugin.network.util.NetWorkConstUtil;
import com.yinhai.sheduledTask.frame.plugin.network.util.ParamUtil;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zrc on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/service")
public class ServiceController extends BaseController {

    @RequestMapping(value = "/postTransferData")
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        HttpPost post = new HttpPost();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            System.out.println(key + " " + request.getParameter(key));
        }
    }

    @RequestMapping(value = "/networkRequest")
    public void netWorkRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        NetworkService service = new NetworkServiceImpl();
        String param = service.receive(NetWorkConstUtil.SECURITY_LEVEL_ONE,request,response);
        System.out.println(param);
        Map map = new HashMap();
        map.put("hello", "1");
        map.put("world", "2");
        service.write(map, NetWorkConstUtil.SECURITY_LEVEL_ONE,response);
    }

    @RequestMapping(value = "/hello")
    public String toHello(HttpServletRequest request) {
        // return "hello" //跳转到webapp/service/hello.jsp
        String param = request.getParameter(ParamUtil.PARAM_NAME);
        System.out.println(param);
        return "/hello"; //跳转到根目录下的hello.jsp
    }
}
