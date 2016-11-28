package com.yinhai.sheduledTask.business.dataTransfer.job.store;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinhai.sheduledTask.business.dataTransfer.TransferApplication;
import com.yinhai.sheduledTask.business.dataTransfer.bean.TransferDTO;
import com.yinhai.sheduledTask.business.dataTransfer.service.DataSaverService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by zrc on 2016/9/26.
 * 收取中心mq 的内容
 */
public class DataPullHandler implements MessageListener {

    private DataSaverService dataSavaerService;

    public void processData(String str) {
        TransferApplication.getLog().debug("get message from CenterMq " + str);
        JSONObject object = JSON.parseObject(str);
        TransferDTO dto = TransferDTO.fromateData(object);
        dataSavaerService.saveTransfedData(dto);
        TransferApplication.getLog().debug("get data from mq : " + str);
    }


    public void setDataSavaerService(DataSaverService dataSavaerService) {
        this.dataSavaerService = dataSavaerService;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage tm = (TextMessage) message;
        try {
            this.processData(tm.getText());
        } catch (JMSException e) {
            TransferApplication.getLog().error("中心同步数据失败！" +e );
        }
    }
}
