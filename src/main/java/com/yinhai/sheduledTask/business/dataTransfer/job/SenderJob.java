package com.yinhai.sheduledTask.business.dataTransfer.job;

import com.yinhai.sheduledTask.business.dataTransfer.TransferApplication;
import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferCustomers;
import com.yinhai.sheduledTask.frame.db.ext.DbInformation;
import com.yinhai.sheduledTask.frame.db.ext.SqlBuilder;
import com.yinhai.sheduledTask.frame.util.DateUtil;
import com.yinhai.sheduledTask.frame.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/9/23.
 */
public abstract class SenderJob {


    @Autowired
    protected DbInformation dbInformation;

    protected Map<String, TransferCustomers> customersMap;
    private List tableData;

    public void quartzJob(Map map) throws Exception {

        List<HashMap> allTables = dbInformation.getAllTables();

        String tableName; //表名

        List<HashMap> allTableData = new ArrayList<HashMap>(); //所有表的所有数据
        int sendSize = 0; //数据量
        HashMap tableSendInfo ;

        for (HashMap tableMeta : allTables) {
            tableName = (String) tableMeta.get(DbInformation.MAP_TABLE_NAME_KEY);
            if (isNeedGetData(tableName)) {
                sendSize++;
                tableSendInfo = new HashMap();
                tableSendInfo.put("tableName", tableName);
                tableSendInfo.put("tableData", tableData);
                allTableData.add(tableSendInfo);
            }
        }
        createDTOAndSend(sendSize, allTableData);
    }

    /**
     * 需要传送
     *
     * @param tableName
     * @return true 放入数据集中，false 跳过
     */
    private boolean isNeedGetData(String tableName) {
        boolean exist = !TransferApplication.IGNORETABLES.contains(tableName);
        return exist && ((tableData = getTableData(tableName)).size() > 0);
    }

    private void createDTOAndSend(int sendSize, List<HashMap> allTableData) throws Exception {
        if (sendSize > 0) {
            Map sendingData = new HashMap();
            sendingData.put("orgid", TransferApplication.orgid);
            sendingData.put("transferId", this.getTransferId());
            sendingData.put("allData", allTableData);
            sendTo(sendingData);
        }
    }


    /**
     * 获取数据库的数据
     *
     * @param tableName 数据库表名称
     * @return
     */
    private List getTableData(String tableName) {
        String selectCondition = this.getSelectCondition(tableName);
        String selectSql = this.getSelectSql(tableName, selectCondition);
        TransferApplication.getLog().debug(selectSql);
        return dbInformation.getSqlDataExecuter().selectList(selectSql);
    }

    private String getTransferId() {
        return TransferApplication.orgid + DateUtil.getCurDateTime();
    }


    /**
     * 门店和中心使用不同的方式进行发送
     * 门店使用network 直接范送
     * 中心发送到mq 中
     *
     * @param sendingData
     * @throws Exception
     */
    protected abstract void sendTo(Map sendingData) throws Exception;

    /**
     * 默认采用配置文件中的条件
     * 获取查询条件，店铺和门店使用不同的条件
     *
     * @param tableName
     * @return
     */
    protected abstract String getSelectCondition(String tableName);

    /**
     * 获取查询数据的sql 语句
     *
     * @param tableName       数据库表名
     * @param selectCondition 条件
     * @return sql语句
     */
    private String getSelectSql(String tableName, String selectCondition) {
        String selectSql = "";
        SqlBuilder builder = new SqlBuilder(tableName, dbInformation.getAllColumns(tableName));
        TransferCustomers transferCustomers = getTransferCustomers(tableName);
        if (transferCustomers != null) {
            selectSql = getCustomerSelectSql(tableName);
        }
        if (ValidateUtil.isEmpty(selectSql)) {
            selectSql = builder.buildSelect(selectCondition);
        }
        return selectSql;
    }

    protected abstract String getCustomerSelectSql(String tableName);

    /**
     * 获取自定义操作的类,如果不存在返回null
     *
     * @param tableName
     * @return
     */
    protected TransferCustomers getTransferCustomers(String tableName) {
        if (this.customersMap.containsKey(tableName)) {
            return customersMap.get(tableName);
        } else {
            return null;
        }
    }

    public void setCustomersMap(Map<String, TransferCustomers> customersMap) {
        this.customersMap = customersMap;
    }
}
