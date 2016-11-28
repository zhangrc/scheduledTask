package com.yinhai.sheduledTask.business.dataTransfer;


import com.yinhai.sheduledTask.business.dataTransfer.ext.TransferListener;
import com.yinhai.sheduledTask.frame.base.DynamicLoadBean;
import com.yinhai.sheduledTask.frame.plugin.quartz.api.TaskService;
import com.yinhai.sheduledTask.frame.plugin.quartz.domain.ScheduleJob;
import com.yinhai.sheduledTask.frame.system.SystemConfig;
import com.yinhai.sheduledTask.frame.system.SystemConst;
import com.yinhai.sheduledTask.frame.util.ValidateUtil;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zrc on 2016/9/26.
 * <p>
 * 数据传输模块的总入口
 */
@Component("transferApplicaion")
public final class TransferApplication {
    @Autowired
    private DynamicLoadBean dynamicLoadBean;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskService taskService;

    public List<TransferListener> observes = new ArrayList<TransferListener>();

    public static String orgid;

    public static boolean isSync = false;

    public static final List<String> IGNORETABLES = new ArrayList<>();

    private static final Logger log = Logger.getLogger(TransferApplication.class);

    public static String CENTERPROJECTNAME = "";

    public static String centerUrl = "";


    /**
     * 初始化，配置需要忽略的表
     * 忽略的表将不发送数据到mq中
     */
    @PostConstruct
    public void intilize() {
        orgid = (String) SystemConfig.getContextProperty(SystemConst.STOREID);
        String sync = (String) SystemConfig.getContextProperty(SystemConst.ISSYNC);
        TransferApplication.isSync = Boolean.valueOf(sync);
        if (isSync) {
            getLog().debug("begin transfer data ");
            loadTransferParams();
            String projectType = loadSpringConfigXmls();
            initDatatransferByType(projectType);
            notifyObservers();
        }
    }

    /**
     * 初始化传输参数
     * 1. 加载传输中需要忽略的表
     * 2. 加载传输数据时的查询条件
     */
    private void loadTransferParams() {
        String ignoreTableString = (String) SystemConfig.getContextProperty(SystemConst.IGNORETABLEKEY);
        IGNORETABLES.addAll(Arrays.asList(ignoreTableString.split(",")));
        CENTERPROJECTNAME = (String) SystemConfig.getContextProperty(SystemConst.CENTERPROJECTNAME);
    }

    /**
     * 加载需要进行数据传输的spring 模块配置文件
     */
    private String loadSpringConfigXmls() {
        dynamicLoadBean.loadBean("classpath:bs/dataTransfer.xml");
        getLog().debug("load dataTransfer.xml");
        String projectType = (String) SystemConfig.getContextProperty(SystemConst.PROJECT_TYPE);
        String fileName = "classpath:bs/" + projectType + ".xml";
        getLog().debug("load " + projectType + ".xml");
        dynamicLoadBean.loadBean(fileName);
        return projectType;
    }

    /**
     * 按照不同的项目类别配置不同mq
     *
     * @param projectType
     */
    private void initDatatransferByType(String projectType) {
        if ("store".equals(projectType)) {
            intStore();
            addShedruJob("com.yinhai.mis.dataTransfer.store.StoreJob");
        }
        if ("center".equals(projectType)) {
            setLocalMq();
            intCenter();
            addShedruJob("com.yinhai.mis.dataTransfer.center.CenterJob");
        }
    }

    /**
     * 通知其他客户端，已经初始化完成传输模块
     */
    private void notifyObservers() {
        for (TransferListener bean : observes) {
            bean.afterTransferInit();
        }
    }

    /**
     * 添加定时任务，定时发送数据到本地mq
     */
    private void addShedruJob(String className) {
        ScheduleJob job = new ScheduleJob();
        job.setSpring_id("sender");
        job.setBeanClass(className);
        String cron = (String) SystemConfig.getContextProperty("project.store.syncCron");
        job.setCron_expression(cron);
        job.setMethod_name("quartzJob");
        job.setJob_id("sender");
        job.setJob_name("senderQuartz");
        job.setJob_group("localTransfer");
        try {
            taskService.addJob(job);
        } catch (SchedulerException e) {
            getLog().error("启动同步任务失败！！！");
        }
    }

    /**
     * 配置本地的mq ，设置clientID
     */
    private void setLocalMq() {
        ActiveMQConnectionFactory localMqConnectionFactory = (ActiveMQConnectionFactory) applicationContext.getBean("localMqConnectionFactory");
        localMqConnectionFactory.setBrokerURL(getBrokerUrl());
        localMqConnectionFactory.setClientID(orgid);
    }

    /**
     * 配置门店同步的中心me 的url
     * clentID
     */
    private void intStore() {
        ActiveMQConnectionFactory centerMqConnectionFactory = (ActiveMQConnectionFactory) applicationContext.getBean("centerMqConnectionFactory");
        centerMqConnectionFactory.setBrokerURL(this.getBrokerUrl());
        centerMqConnectionFactory.setClientID(orgid);
        setCenterUrl();
    }

    /**
     * 设置中心连接的url
     */
    private void setCenterUrl() {
        String port = (String) SystemConfig.getContextProperty(SystemConst.CENTERPORT);
        String actionName = TransferApplication.CENTERPROJECTNAME + "/service/centerAction!reciveTransferData.do";
        //String url = "http://" + ip + ":8080/"+ TransferApplication.CENTERPROJECTNAME+"/service/centerAction!reciveTransferData.do";
        String url = "http://" + SystemConfig.getContextProperty(SystemConst.AMQ_CENTER_IP);
        String projectName = (String) SystemConfig.getContextProperty(SystemConst.CENTERPROJECTNAME);
        if ("80".equals(port) || ValidateUtil.isEmpty(port)) {
            if (ValidateUtil.isEmpty(projectName)) {
                url = url + actionName;
            } else {
                url = url + "/" + actionName;
            }
        } else {
            if (ValidateUtil.isEmpty(projectName)) {

                url = url + ":" + port + "" + actionName;
            } else {
                url = url + ":" + port + "/" + actionName;
            }
        }
        TransferApplication.centerUrl = url;
    }

    private String getBrokerUrl() {
        //return "failover:(tcp://" + SystemConfig.getContextProperty(SystemConfig.AMQ_CENTER_IP) + ":61616/)";
        return "tcp://" + SystemConfig.getContextProperty(SystemConst.AMQ_CENTER_IP) + ":61616/";
    }

    /**
     * 中心门店单独配置的mq
     * 开启订阅
     * 设置默认的Destination
     */
    private void intCenter() {
        // setStoresConnectonFactoryBrokerUrls();
        // andConfigToCenterJmsTemplete();
    }

    private void andConfigToCenterJmsTemplete() {
        JmsTemplate jmsTeplate = (JmsTemplate) applicationContext.getBean("jmsTemplete");
        ActiveMQTopic centerTopic = (ActiveMQTopic) applicationContext.getBean("centerTopic");
        jmsTeplate.setPubSubDomain(true);
        jmsTeplate.setDefaultDestination(centerTopic);
    }

    private void setStoresConnectonFactoryBrokerUrls() {
        String storeIps = (String) SystemConfig.getContextProperty(SystemConst.STORE_IPS);
        storeIps = storeIps.replaceAll(",", ":61616,tcp://");
        storeIps = "failover:(tcp://" + storeIps + ":61616)";
        ActiveMQConnectionFactory storesMqConnectionFactory = (ActiveMQConnectionFactory) applicationContext.getBean("storesMqConnectionFactory");
        storesMqConnectionFactory.setBrokerURL(storeIps);
    }


    public void addObserver(TransferListener bean) {
        this.observes.add(bean);
    }

    public static Logger getLog() {
        return log;
    }

}