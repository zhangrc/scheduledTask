package com.yinhai.sheduledTask.base;

import java.util.List;

/**
 * Created by zrc on 2016/11/21.
 */
public interface IDao {
    List queryForList(String statement, Object param);

    List queryForList(String statement);

    Object queryForObject(String statement);

    Object queryForObject(String statement ,Object param);

    Object save(String statement ,Object param);

    Object save(String statement );

    Object update(String statement );

    Object update(String statement ,Object param);
}
