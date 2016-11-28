package com.yinhai.sheduledTask.business.dataTransfer.service;


import com.yinhai.sheduledTask.frame.base.BaseService;

import java.util.Map;

/**
 * Created by zrc on 2016/11/1.
 * Base_transfer 的数据库操作
 */
public interface TransferService extends BaseService {

    void saveTransferLog(Map sendData, boolean isSuccess);

    void updateTransferToSuccess();

}
