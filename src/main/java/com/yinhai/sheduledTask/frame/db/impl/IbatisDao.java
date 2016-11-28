package com.yinhai.sheduledTask.frame.db.impl;

import com.yinhai.sheduledTask.frame.db.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zrc on 2016/11/21.
 */
@Component("dao")
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

    public Integer insert(String statement , Object param) {
        return (Integer) sqlMapClientTemplate.insert(statement,param);
    }

    public Integer insert(String statement ) {
        return (Integer)sqlMapClientTemplate.insert(statement);
    }

    public Integer update(String statement ) {
        return sqlMapClientTemplate.update(statement);
    }

    public Integer update(String statement ,Object param) {
        return sqlMapClientTemplate.update(statement,param);
    }
}
