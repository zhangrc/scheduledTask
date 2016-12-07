package com.yinhai.sheduledTask.frame.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zrc on 2016/11/21.
 */
public class SystemConfig extends PropertyPlaceholderConfigurer {

    public static final String CENTERPROJECTNAME = "project.center.name" ;

    public static final String CENTERPORT ="project.center.port" ;

    private static Map<String, Object> ctxPropertiesMap;

    public final static String IGNORETABLEKEY = "project.db.ignoretable";

    public final static String ISSYNC = "project.store.issync";

    public final static String CENTERSELECTCONDITION = "projcet.db.centerSelectCondition";

    public final static String STORESELECTCONDITION = "projcet.db.storeSelectCondition";

    public final static String STOREID = "project.store.num";

    public final static String PORGID = "project.store.porgid";

    public static final String PROJECT_TYPE = "project.type";

    public static final String CENTER_IP = "center.ip";

    public static final String STORE_IPS = "store.ips";

    public static final String FILEFLODER = "file.floder";

    public static final String FILESERVERNAME = "file.ServerName";

    public static final String FILESERVERPORT = "file.ServerPort";

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props) throws BeansException {
        super.processProperties(beanFactory, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    //static method for accessing context properties
    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }


    public static String getFileVisitUrl() {
        String url = "http://" + SystemConfig.getContextProperty(CENTER_IP);
        String projectName = (String) getContextProperty(FILESERVERNAME);
        String port = (String) getContextProperty(FILESERVERPORT);
        return url+":"+port+"/"+projectName;
    }

    public static void addProperties(Map map) {
        ctxPropertiesMap.putAll(map);
    }
}
