package com.yinhai.sheduledTask.business.dataTransfer.ext;

import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/11/7.
 */
public abstract class TransferCustomerAdapter implements TransferCustomers{
    @Override
    public String getCenterSelectCondition() {
        return null;
    }

    @Override
    public String getStoreSelectCondition() {
        return null;
    }

    @Override
    public String getStoreSelectSql() {
        return null;
    }

    @Override
    public String getCenterSelectSql() {
        return null;
    }

    @Override
    public boolean doLocalSaver(String tableName, List<Map> tableData) {
        return false;
    }
}
