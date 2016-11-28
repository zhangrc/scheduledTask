package com.yinhai.sheduledTask.frame.db.ext;

import com.yinhai.sheduledTask.frame.system.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zrc on 2016/9/22.
 * 数据库信息
 */
@Component("dbInformation")
public final class DbInformation {

    public static final String MAP_TABLE_NAME_KEY = "table_name";
    public static final String MAP_COLUMN_NAME_KEY = "column_name";

    private String table_schema;

    @Autowired
    private SqlDataExecuter sqlDataExecuter;

    private static List<HashMap> tables;
    @PostConstruct
    public List<HashMap> getAllTables() {
        String jdbc = (String) SystemConfig.getContextProperty("jdbc.url");
        String shema = jdbc.substring(jdbc.lastIndexOf("/")+1,jdbc.indexOf("?"));
        table_schema = shema;
        if(DbInformation.tables == null) {
            String sql = "select table_name from information_schema.tables where table_schema='" + table_schema + "' and table_type='base table'";
            DbInformation.tables = sqlDataExecuter.selectList(sql);
        }
        return DbInformation.tables;
    }

    public List<HashMap> getAllColumns(String tableName) {
        String sql = "select column_name from information_schema.columns where table_schema='" + table_schema + "' and table_name='" +tableName+"'";
        String all = "select lower(c.column_name) column_name ,c.is_nullable,c.column_key,c.is_nullable,c.data_type from information_schema.columns c where table_schema='"+table_schema+"' and table_name='"+tableName+"'";
        return sqlDataExecuter.selectList(all);
    }

    public SqlDataExecuter getSqlDataExecuter() {
        return sqlDataExecuter;
    }
}
