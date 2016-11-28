package com.yinhai.sheduledTask.business.dataTransfer.service.impl;


import com.yinhai.sheduledTask.business.dataTransfer.TransferApplication;
import com.yinhai.sheduledTask.business.dataTransfer.bean.TransferDTO;
import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomers;
import com.yinhai.sheduledTask.business.dataTransfer.service.DataSaverService;
import com.yinhai.sheduledTask.frame.base.impl.BaseServiceImpl;
import com.yinhai.sheduledTask.frame.db.ext.DbInformation;
import com.yinhai.sheduledTask.frame.db.ext.SqlBuilder;
import com.yinhai.sheduledTask.frame.exception.AppException;
import com.yinhai.sheduledTask.frame.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/9/29.
 */
public class DataSaverServiceImpl extends BaseServiceImpl implements DataSaverService {

    @Autowired
    private DbInformation dbInformation;

    private Map customersMap;

    @Override
    public void saveTransfedData(TransferDTO dto) {

        try {
            String orgid = dto.getOrgId();
            String transferId = dto.getTransferId();
            String tableName;
            List<Map> tableData;
            for (Map map : dto.getAllTableData()) {
                tableName = (String) map.get("tableName");
                tableData = (List) map.get("tableData");
                if (!(this.customersMap.containsKey(tableName) && executeCustomer(tableName, tableData))) {
                    defaultSaver(tableName, tableData);
                }
            }
        } catch (Exception e) {
            TransferApplication.getLog().error("保存同步数据出错 数据内容为：" + dto + "\n" + e);
            throw new AppException("同步数据保存到本地是数据出错，出错的内容为： " + dto + "\n" + e);
        }
    }

    /**
     * 默认保存方法
     *
     * @param tableName
     * @param tableData
     */
    private void defaultSaver(String tableName, List<Map> tableData) {
        SqlBuilder builder;
        String executeSql;
        builder = new SqlBuilder(tableName, dbInformation.getAllColumns(tableName));
        for (Map table : tableData) {
            int version = 1;
            if (!ValidateUtil.isEmpty(table.get("T_VERSION"))) {
                version = Integer.parseInt(table.get("T_VERSION").toString());
                version = version + 1;
            }
            table.put("T_VERSION", version);
            if (!this.isExistInLocalDb(tableName, table)) {
                executeSql = builder.buildInsert(table);
                dbInformation.getSqlDataExecuter().saveSql(executeSql);
            } else {
                executeSql = builder.buildUpdate(table, "");
                dbInformation.getSqlDataExecuter().updateSql(executeSql);
            }
        }
    }

    /**
     * 执行定制化的操作，
     */
    private boolean executeCustomer(String tableName, List<Map> tableData) {
        TransferCustomers customer;
        customer = (TransferCustomers) this.customersMap.get(tableName);
        return customer.doLocalSaver(tableName, tableData);
    }


    @Override
    public Map saveJsonData(Map map) {
        TransferDTO dto = TransferDTO.fromateData(map);
        this.saveTransfedData(dto);
        Map result = new HashMap();
        result.put("success", true);
        return result;
    }


    /**
     * 指定的数据在数据中是否存在
     *
     * @param tableName tableName
     * @param tableData 数据
     * @return 存在返回true
     */
    private boolean isExistInLocalDb(String tableName, Map tableData) {
        SqlBuilder sqlBuilder = new SqlBuilder(tableName, dbInformation.getAllColumns(tableName));
        String sql = "";
        if (ValidateUtil.isEmpty(sqlBuilder.getPrimaryKey())) {
            sql = sqlBuilder.buildSelectCountWithAllCount(tableData);
        } else {
            sql = sqlBuilder.buildSelectCountWithPri(tableData, "");
        }
        Map map = dbInformation.getSqlDataExecuter().selectMap(sql);
        int count = Integer.parseInt(map.get("count").toString());
        return count > 0;
    }

    public void setCustomersMap(Map customersMap) {
        this.customersMap = customersMap;
    }
}
