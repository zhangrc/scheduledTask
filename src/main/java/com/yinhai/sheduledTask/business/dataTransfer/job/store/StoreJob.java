package com.yinhai.sheduledTask.business.dataTransfer.job.store;

import com.alibaba.fastjson.JSONObject;
import com.yinhai.sheduledTask.business.dataTransfer.TransferApplication;
import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomers;
import com.yinhai.sheduledTask.business.dataTransfer.job.SenderJob;
import com.yinhai.sheduledTask.business.dataTransfer.service.TransferService;
import com.yinhai.sheduledTask.frame.plugin.network.service.NetworkService;
import com.yinhai.sheduledTask.frame.plugin.network.util.ConstUtil;
import com.yinhai.sheduledTask.frame.system.SystemConfig;
import com.yinhai.sheduledTask.frame.system.SystemConst;
import com.yinhai.sheduledTask.frame.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by zrc on 2016/9/26.
 */
public class StoreJob extends SenderJob {

    @Autowired
    private NetworkService networkService;

    private TransferService transferService;

    @Override
    public void sendTo(Map sendingData) throws Exception {
        TransferApplication.getLog().debug("store  job  sendingData " + sendingData);
        String resultString = networkService.post(TransferApplication.centerUrl, sendingData, ConstUtil.SECURITY_LEVEL_ONE);
        Map map = JSONObject.parseObject(resultString);
        if(!ValidateUtil.isEmpty(map.get("success"))) {
            transferService.saveTransferLog(sendingData, true);
        }
    }

    @Override
    public String getSelectCondition(String tableName) {
        String condition =(String) SystemConfig.getContextProperty(SystemConst.STORESELECTCONDITION);
        if(super.customersMap.containsKey(tableName)) {
            TransferCustomers tableExecuter = super.customersMap.get(tableName);
            condition +=" " +tableExecuter.getStoreSelectCondition();
        }
        return condition;
    }

    @Override
    protected String getCustomerSelectSql(String tableName) {
        TransferCustomers transferCustomers = super.getTransferCustomers(tableName);
        return transferCustomers.getStoreSelectSql();
    }

    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }
}
