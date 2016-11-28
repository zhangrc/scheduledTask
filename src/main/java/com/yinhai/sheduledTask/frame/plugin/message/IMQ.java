package com.yinhai.sheduledTask.frame.plugin.message;

/**
 * Created by zrc on 2016/9/22.
 */
public interface IMQ {
    /**
     * 使用指定的destention 发送
     * @param destention destention
     * @param param 参数
     */
    void pushData(String destention, String param);

    /**
     * 使用默认的destention 发送消息
     * @param param 发送的内容
     */
    void pushData(String param);
}
