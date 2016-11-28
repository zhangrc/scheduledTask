package com.yinhai.sheduledTask.business.dataTransfer.customers;


import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomers;

import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/11/7.
 */
public class TestTable implements TransferCustomers {
    @Override
    public String getTableName() {
        return "test";
    }

    @Override
    public String getCenterSelectCondition() {
        return "and 1 = 1 ";
    }

    @Override
    public String getStoreSelectCondition() {
        return this.getCenterSelectCondition();
    }

    @Override
    public String getStoreSelectSql() {
        return "select * from test "/* + TransferCustomers.STORESELECTCONDITION*/;
    }

    @Override
    public String getCenterSelectSql() {
        return "select * from test " + TransferCustomers.CENTERSELECTCONDITION;
    }

    @Override
    public boolean doLocalSaver(String tableName, List<Map> tableData) {
        return false;
    }
}
