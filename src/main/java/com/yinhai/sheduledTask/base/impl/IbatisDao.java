package com.yinhai.sheduledTask.base.impl;

import com.yinhai.sheduledTask.base.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.List;

/**
 * Created by zrc on 2016/11/21.
 */
public class IbatisDao implements IDao {
    @Autowired
    private SqlMapClientTemplate sqlMapClientTemplate;

    public List queryForList(String statement, Object param) {
       return sqlMapClientTemplate.queryForList(statement,param);
    }

    public List queryForList(String statement) {
        return sqlMapClientTemplate.queryForList(statement);
    }

    public Object queryForObject(String statement) {
        return sqlMapClientTemplate.queryForObject(statement);
    }

    public Object queryForObject(String statement ,Object param) {
        return sqlMapClientTemplate.queryForObject(statement,param);
    }

    public Object save(String statement ,Object param) {
        return sqlMapClientTemplate.insert(statement,param);
    }

    public Object save(String statement ) {
        return sqlMapClientTemplate.insert(statement);
    }

    public Object update(String statement ) {
        return sqlMapClientTemplate.update(statement);
    }

    public Object update(String statement ,Object param) {
        return sqlMapClientTemplate.update(statement,param);
    }
}
