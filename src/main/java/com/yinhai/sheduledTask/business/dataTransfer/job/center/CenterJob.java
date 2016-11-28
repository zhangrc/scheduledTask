package com.yinhai.sheduledTask.business.dataTransfer.job.center;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.sheduledTask.business.dataTransfer.TransferApplication;
import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomers;
import com.yinhai.sheduledTask.business.dataTransfer.job.SenderJob;
import com.yinhai.sheduledTask.business.dataTransfer.service.TransferService;
import com.yinhai.sheduledTask.frame.plugin.message.IMQ;
import com.yinhai.sheduledTask.frame.system.SystemConfig;
import com.yinhai.sheduledTask.frame.system.SystemConst;

import java.util.Map;

/**
 * Created by zrc on 2016/10/31.
 */
public class CenterJob extends SenderJob {

    protected IMQ imq;

    protected TransferService transferService;

    @Override
    public void sendTo(Map sendData) {
        String param = JSONObject.toJSONStringWithDateFormat(sendData,"yyyy-MM-dd HH:mm:ss.S");
        TransferApplication.getLog().debug("center job sendingData " + sendData);
        imq.pushData(param);
        transferService.saveTransferLog(sendData,true);
    }

    @Override
    protected String getSelectCondition(String tableName) {
        String condition =(String) SystemConfig.getContextProperty(SystemConst.CENTERSELECTCONDITION);
        if(super.customersMap.containsKey(tableName)) {
            TransferCustomers tableExecuter = super.customersMap.get(tableName);
            condition +=" " +tableExecuter.getCenterSelectCondition();
        }
        return condition;
    }

    @Override
    protected String getCustomerSelectSql(String tableName) {
        TransferCustomers transferCustomers = super.getTransferCustomers(tableName);
        return transferCustomers.getCenterSelectSql();
    }


    public void setImq(IMQ imq) {
        this.imq = imq;
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }
}
