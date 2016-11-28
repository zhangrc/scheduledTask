package com.yinhai.sheduledTask.business.dataTransfer.service.impl;

import com.yinhai.sheduledTask.business.dataTransfer.service.TransferService;
import com.yinhai.sheduledTask.frame.base.impl.BaseServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zrc on 2016/11/1.
 */
public class TransferServiceImpl extends BaseServiceImpl implements TransferService {


    @Override
    public void saveTransferLog(Map sendData, boolean isSuccess) {
        Map saveInfo = new HashMap();
        saveInfo.put("transfer_id", sendData.get("transferId"));
        saveInfo.put("orgid", sendData.get("orgid"));
        super.getDao().insert("transfer.saveTransfer", saveInfo);
        if (isSuccess) {
            this.updateTransferToSuccess();
        }
    }

    @Override
    public void updateTransferToSuccess() {
        super.getDao().update("transfer.updateTransferStatus");
    }


}
