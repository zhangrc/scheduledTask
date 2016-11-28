package com.yinhai.sheduledTask.business.dataTransfer.ext;

/**
 * Created by zrc on 2016/10/25.
 * 实现这个接口后，并加入观察者列表
 * 观察者接口
 */
public interface TransferListener {
    /**
     * 在初始化数据传输时调用这个接口
     */
    void beforeTransferInit();

    /**
     * 在初始化完成数据传输时调用这个接口
     */
    void afterTransferInit();
}
