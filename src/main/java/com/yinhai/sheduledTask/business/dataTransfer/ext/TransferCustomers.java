package com.yinhai.sheduledTask.business.dataTransfer.ext;


import com.yinhai.sheduledTask.frame.system.SystemConfig;
import com.yinhai.sheduledTask.frame.system.SystemConst;

import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/11/7.
 */
public interface TransferCustomers {

    String CENTERSELECTCONDITION = (String) SystemConfig.getContextProperty(SystemConst.CENTERSELECTCONDITION);

    String STORESELECTCONDITION = (String) SystemConfig.getContextProperty(SystemConst.STORESELECTCONDITION);

    /**
     * 获取需要处理的表明
     * 多个用逗号分开
     *
     * @return 数据库表名称
     */
    String getTableName();

    /**
     * 同部时药店中心的查询条件
     * 如果使用了sql 那么这个条件就不会生效
     * @return "and a = 1 and b = 2 "
     */
    String getCenterSelectCondition();

    /**
     * 同步时，店铺的查询条件
     * 如果使用了sql 那么这个条件就不会生效
     * @return
     */
    String getStoreSelectCondition();

    /**
     * 同步时的门店的查询sql
     *
     * @return
     */
    String getStoreSelectSql();

    /**
     * 同步时中心的查询sql
     *
     * @return
     */
    String getCenterSelectSql();

    /**
     * 保存操作
     *
     * @param tableName
     * @param tableData
     * @return 如果需要指定的保存，执行操作，并返回true 如果不需要改变默认的操作，返回false
     */
    boolean doLocalSaver(String tableName, List<Map> tableData);

}
