package com.yinhai.sheduledTask.frame.controller;

import com.alibaba.fastjson.JSON;
import com.yinhai.sheduledTask.frame.util.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zrc on 2016/12/1.
 */
public class ServiceControllerTest {
    public static void main(String[] args) throws Exception {
        String url = "http://127.0.0.1:8080/sheduledTask/service/postTransferData";
        Map param = new HashMap();
        param.put("hello","");
        param.put("world","");
        HttpUtils.post(url, JSON.toJSONString(param), new HttpUtils.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                System.out.println(resultCode);
            }
        });
    }
}
