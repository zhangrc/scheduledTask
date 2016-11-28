package com.yinhai.sheduledTask;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by zrc on 2016/11/21.
 */
@ContextConfiguration(locations={"classpath:/app-context.xml"})
public class BaseTest extends AbstractJUnit4SpringContextTests {
    @Test
    public void testVoid() {
        //System.out.println("------------");
    }
}