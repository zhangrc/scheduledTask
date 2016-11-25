package com.yinhai.sheduledTask.base.impl;

import com.yinhai.sheduledTask.base.BaseService;
import com.yinhai.sheduledTask.base.IDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zrc on 2016/11/21.
 */
public class BaseServiceImpl implements BaseService{
    @Autowired
    private IDao dao;

    protected IDao getDao() {
        return this.dao;
    }
}
