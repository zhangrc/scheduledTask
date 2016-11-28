package com.yinhai.sheduledTask.frame.plugin.quartz.impl;


import com.yinhai.sheduledTask.frame.base.impl.BaseServiceImpl;
import com.yinhai.sheduledTask.frame.plugin.quartz.QuartzJobFactory;
import com.yinhai.sheduledTask.frame.plugin.quartz.api.TaskService;
import com.yinhai.sheduledTask.frame.plugin.quartz.domain.ScheduleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Service("taskService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TaskServiceImpl extends BaseServiceImpl implements TaskService {
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
	
	@PostConstruct
	public void init() throws Exception {
		//Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 这里获取任务信息数据
		List<ScheduleJob> jobList = createJobList();//获取任务列表
		for(ScheduleJob job:jobList){
			addJob(job);
		}
		//scheduler.start();
	}
	
	
	@Override
    public void addJob(ScheduleJob job) throws SchedulerException {
    	Scheduler scheduler = schedulerFactoryBean.getScheduler();
    	//这里获取任务信息数据
    	TriggerKey triggerKey = TriggerKey.triggerKey(job.getJob_name(), job.getJob_group());
    	//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
    	CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
    	//不存在，创建一个
    	if (null == trigger) {
    		Class clazz = QuartzJobFactory.class;
    		JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJob_name(), job.getJob_group()).build();
    		jobDetail.getJobDataMap().put("scheduleJob", job);
    		//表达式调度构建器
    		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
    			.getCron_expression());
    		//按新的cronExpression表达式构建一个新的trigger
    		trigger = TriggerBuilder.newTrigger().withIdentity(job.getJob_name(), job.getJob_group()).withSchedule(scheduleBuilder).build();
    		scheduler.scheduleJob(jobDetail, trigger);
    		//System.out.println(job.getDescription()+"添加成功");
    	} else {
    		// Trigger已存在，那么更新相应的定时设置
    		//表达式调度构建器
    		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
    			.getCron_expression());
    		//按新的cronExpression表达式重新构建trigger
    		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
    			.withSchedule(scheduleBuilder).build();
    		//按新的trigger重新设置job执行
    		scheduler.rescheduleJob(triggerKey, trigger);
    	}
    }
	
   
   @Override
   public ScheduleJob updateJobCron(ScheduleJob job) throws SchedulerException {
	   System.out.println("修改任务调度时间表达式");
	   Scheduler scheduler = schedulerFactoryBean.getScheduler();
	   //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
       TriggerKey triggerKey = TriggerKey.triggerKey(job.getJob_name(),job.getJob_group());
       //表达式调度构建器
       CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	   //表达式调度构建器
	   CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron_expression());
	   //按新的cronExpression表达式重新构建trigger
	   trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
	   //按新的trigger重新设置job执行
	   scheduler.rescheduleJob(triggerKey, trigger);
	   return job;
   } 
	
	@Override
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				ScheduleJob job = new ScheduleJob();
				job.setJob_name(jobKey.getName());
				job.setJob_group(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCron_expression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}
   
    @Override
	public List<ScheduleJob> getRunningJob() throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
		List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
		for (JobExecutionContext executingJob : executingJobs) {
			ScheduleJob job = new ScheduleJob();
			JobDetail jobDetail = executingJob.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			Trigger trigger = executingJob.getTrigger();
			job.setJob_name(jobKey.getName());
			job.setJob_group(jobKey.getGroup());
			job.setDescription("触发器:" + trigger.getKey());
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			job.setStatus(triggerState.name());
			if (trigger instanceof CronTrigger) {
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCron_expression(cronExpression);
			}
			jobList.add(job);
		}
		return jobList;
	}
   
    @Override
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJob_name(), scheduleJob.getJob_group());
		scheduler.pauseJob(jobKey);
	}

	@Override
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJob_name(), scheduleJob.getJob_group());
		scheduler.resumeJob(jobKey);
	}

	@Override
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJob_name(), scheduleJob.getJob_group());
		scheduler.deleteJob(jobKey);
	}

	@Override
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJob_name(), scheduleJob.getJob_group());
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 查询任务列表
	 * @return
	 */
	public List createJobList(){
		List<ScheduleJob> jobList = getDao().queryForList("taskSchedule.getList");
	   	return jobList;
	  }
}
