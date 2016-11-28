package com.yinhai.sheduledTask.business.dataTransfer.customers;


import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomerAdapter;

/**
 * Created by zrc on 2016/11/14.
 */
public class SfmxTable extends TransferCustomerAdapter {
    @Override
    public String getTableName() {
        return "k_sfmx";
    }

    @Override
    public String getStoreSelectCondition() {
        return "and sf_status = 1";
    }

    @Override
    public String getCenterSelectCondition() {
        return this.getStoreSelectCondition();
    }
}

