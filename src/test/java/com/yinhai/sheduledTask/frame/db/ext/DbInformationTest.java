package com.yinhai.sheduledTask.frame.db.ext;


import com.yinhai.sheduledTask.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zrc on 2016/11/28.
 */
public class DbInformationTest extends BaseTest {

    @Autowired
    private DbInformation dbInformation;

    @Test
    public void testGetAllTables() throws Exception {
        List<HashMap> allTables = dbInformation.getAllTables();
    }

    @Test
    public void testGetAllColumns() throws Exception {
        List list = dbInformation.getSqlDataExecuter().selectList("select * from tauser");
    }

}