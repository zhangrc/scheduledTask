package com.yinhai.sheduledTask.frame.db.ext.impl;


import com.yinhai.sheduledTask.frame.base.impl.BaseServiceImpl;
import com.yinhai.sheduledTask.frame.db.ext.SqlDataExecuter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/9/22.
 */
@Service("sqlDataExecuter")
public class SqlDataExecuterImpl extends BaseServiceImpl implements SqlDataExecuter {
    @Override
    public int updateSql(String sql) {
        return super.getDao().update("sqlExecuter.updateBySql",sql);
    }

    @Override
    public void saveSql(String sql) {
        super.getDao().insert("sqlExecuter.insertBySql",sql);
    }

    @Override
    public int saveSqlWithId(Map map, String sql) {
        map.put("sql",sql);
        return (int) super.getDao().insert("sqlExecuter.insertBySqlGetId",map);
    }

    @Override
    public List selectList(String sql) {
        return super.getDao().queryForList("sqlExecuter.selectListBySql",sql);
    }

    @Override
    public Map selectMap(String sql) {
        return (Map) super.getDao().queryForObject("sqlExecuter.selectMapBySql",sql);
    }
}
