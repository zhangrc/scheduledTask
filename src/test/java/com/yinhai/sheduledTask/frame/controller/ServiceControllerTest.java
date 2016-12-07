package com.yinhai.sheduledTask.frame.controller;

import com.alibaba.fastjson.JSON;
import com.yinhai.sheduledTask.frame.plugin.network.service.NetworkService;
import com.yinhai.sheduledTask.frame.plugin.network.service.impl.NetworkServiceImpl;
import com.yinhai.sheduledTask.frame.plugin.network.util.NetWorkConstUtil;
import com.yinhai.sheduledTask.frame.util.HttpUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zrc on 2016/12/1.
 */
public class ServiceControllerTest {

    private Map getParamMap() {
        Map param = new HashMap();
        param.put("hello","");
        param.put("world","");
        return param;
    }

    @Test
    public void testPostTransferData() {
        String url = "http://127.0.0.1:8080/sheduledTask/service/postTransferData";
        HttpUtils.post(url, JSON.toJSONString(getParamMap()), new HttpUtils.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                System.out.println(resultCode);
            }
        });
    }

    @Test
    public void testNetworkRequest() throws Exception {
        String url = "http://127.0.0.1:8080/sheduledTask/service/networkRequest";
        NetworkService service = new NetworkServiceImpl();
        String result = service.post(url,getParamMap(), NetWorkConstUtil.SECURITY_LEVEL_ONE);
        System.out.println(result);
    }
}
