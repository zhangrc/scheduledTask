package com.yinhai.sheduledTask.frame.db.ext;


import com.yinhai.sheduledTask.frame.util.ValidateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/9/22.
 */
public class SqlBuilder {
    private String tableName;

    private String primaryKey = null;

    private List<HashMap> columns;

    public SqlBuilder(String tableName, List<HashMap> columns) {
        this.tableName = " " + tableName + " ";
        this.columns = columns;
        for (Map map : columns) {
            if ("PRI".equals(map.get("column_key"))) {
                primaryKey = (String) map.get(DbInformation.MAP_COLUMN_NAME_KEY);
                primaryKey = primaryKey.toLowerCase();
            }
        }

    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public List<HashMap> getColumns() {
        return this.columns;
    }

    /**
     * 查询tableName 的所有字段
     *
     * @param conditions where a = ** b= ***
     * @return select a,b,c,from tableName  [ conditions]
     */
    public String buildSelect(String conditions) {
        StringBuilder builder = new StringBuilder();
        columns = this.getColumns();
        builder.append("select").append(" ");
        for (Object column : columns) {
            HashMap map = (HashMap) column;
            builder.append(map.get("column_name"));
            builder.append(",");
        }
        String sql = builder.toString();
        sql = sql.substring(0, sql.lastIndexOf(',')) + " from " + tableName + conditions;
        return sql;
    }

    /**
     * 构建新增sql 语句包含所有的字段
     *
     * @param param
     * @return
     */
    public String buildInsert(Map param) {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into ").append(this.tableName).append("(");
        String columnName;
        for (int i = 0, len = columns.size(); i < len; i++) {
            columnName = columns.get(i).get(DbInformation.MAP_COLUMN_NAME_KEY).toString();
            if (this.hasValue(param, columnName)) {
                builder.append(columnName)
                        .append(",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" )")
                .append("values ")
                .append("(");
        for (int i = 0, len = columns.size(); i < len; i++) {
            columnName = columns.get(i).get(DbInformation.MAP_COLUMN_NAME_KEY).toString();
            if (this.hasValue(param, columnName)) {
                builder.append("'")
                        .append(param.get(columnName))
                        .append("'")
                        .append(",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        return builder.toString();
    }

    /**
     *
     * @param param update 的参数
     * @param updateCondition 需要添加的条件 and a=1 and b=2
     * @return
     */
    public String buildUpdate(Map param, String updateCondition) {
        StringBuilder builder = new StringBuilder();
        String columnName;
        builder.append("update ")
                .append(this.tableName)
                .append(" set ");
        for (int i = 0 ,len = columns.size() ; i < len ; i ++ ) {
            columnName = columns.get(i).get(DbInformation.MAP_COLUMN_NAME_KEY).toString();
            if((!columnName.equals(this.primaryKey)) && (this.hasValue(param,columnName))){
                builder.append(" ")
                        .append(columnName)
                        .append(" =  '")
                        .append(param.get(columnName))
                        .append("'  ,");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        if(ValidateUtil.isNotEmpty(this.primaryKey)) {
            builder.append(" where ")
                    .append(this.primaryKey)
                    .append(" =  '")
                    .append(param.get(this.primaryKey))
                    .append("' ");
        }
        return builder.append(updateCondition).toString();
    }

    public String buildDelete(Map param) {
        StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

    /**
     * select count(*) from table where primaryKey = param.get(primaryKey)
     *
     * @param param     map key=primarykey value=*****
     * @param condition 需要补充的参数
     * @return select count(*) from table where primaryKey = param.get(primaryKey)
     */
    public String buildSelectCountWithPri(Map param, String condition) {
        StringBuilder builder = new StringBuilder();
        builder.append("select")
                .append(" count(*)")
                .append(" as count")
                .append(" from")
                .append(tableName);
        if (ValidateUtil.isNotEmpty(primaryKey)) {
            builder.append(" where ")
                    .append(primaryKey)
                    .append(" = ")
                    .append("'")
                    .append(param.get(primaryKey))
                    .append(" '")
                    .append(condition);
        }
        return builder.toString();
    }

    public String buildSelectCountWithAllCount(Map param) {
        StringBuilder builder = new StringBuilder();
        builder.append("select")
                .append(" count(*)")
                .append(" as count")
                .append(" from ")
                .append(tableName)
                .append(" where ");
        String columnName;
        for (Map column : columns) {
            columnName = column.get(DbInformation.MAP_COLUMN_NAME_KEY).toString();
            if (this.hasValue(param, columnName)) {
                builder.append(columnName)
                        .append(" = ")
                        .append("'")
                        .append(param.get(columnName))
                        .append("'")
                        .append(" and ");
            }
        }
        builder.append(" 1=1");
        return builder.toString();
    }

    private boolean hasValue(Map containers, Object key) {
        return !ValidateUtil.isEmpty(containers.get(key));
    }
}
