package com.yinhai.sheduledTask.frame.db.ext;


import com.yinhai.sheduledTask.frame.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by zrc on 2016/9/22.
 * 这个service 的每个方法都会执行一个sql 字符串
 *
 */
public interface SqlDataExecuter extends BaseService {

    int updateSql(String sql) ;

    void saveSql(String sql);

    int saveSqlWithId(Map map, String sql);

    List selectList(String sql);

    Map selectMap(String sql);
}
