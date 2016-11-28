package com.yinhai.sheduledTask.frame.db;

import java.util.List;

/**
 * Created by zrc on 2016/11/21.
 */
public interface IDao {
    List queryForList(String statement, Object param);

    List queryForList(String statement);

    Object queryForObject(String statement);

    Object queryForObject(String statement ,Object param);

    Integer insert(String statement , Object param);

    Integer insert(String statement );

    Integer update(String statement );

    Integer update(String statement ,Object param);
}
