package com.yinhai.sheduledTask.business.dataTransfer.service;


import com.yinhai.sheduledTask.business.dataTransfer.bean.TransferDTO;
import com.yinhai.sheduledTask.frame.base.BaseService;

import java.util.Map;

/**
 * Created by zrc on 2016/9/29.
 * 保存数据到本地数据
 */
public interface DataSaverService extends BaseService {
    /**
     * 保存更新过来的数据
     * @param dto
     */
    void saveTransfedData(TransferDTO dto);

    /**
     * 保存json格式的数据
     * @param map
     */
    Map saveJsonData(Map map);

}
