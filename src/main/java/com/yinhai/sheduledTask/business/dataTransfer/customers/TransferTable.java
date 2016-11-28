package com.yinhai.sheduledTask.business.dataTransfer.customers;


import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomerAdapter;

/**
 * Created by zrc on 2016/11/7.
 */
public class TransferTable extends TransferCustomerAdapter {
    @Override
    public String getTableName() {
         return "base_transfer";
    }

    @Override
    public String getCenterSelectCondition() {
        return "and a = 1";
    }

    @Override
    public String getStoreSelectCondition() {
        return this.getCenterSelectCondition();
    }

    @Override
    public String getStoreSelectSql() {
        return "select * from base_transfer";
    }
}
