package com.yinhai.sheduledTask.frame.plugin.quartz.api;

import com.yinhai.sheduledTask.frame.base.BaseService;
import com.yinhai.sheduledTask.frame.plugin.quartz.domain.ScheduleJob;
import org.quartz.SchedulerException;

import java.util.List;

public interface TaskService extends BaseService {
	/**
	 * 添加调度任务
	 * @param jobList
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException;
	/** 
	 * 更新任务时间表达式 
	 * 注意(更新后将立即执行一遍调度任务)
	 * @param scheduleJob 
	 * @throws SchedulerException
	 */
	public ScheduleJob updateJobCron(ScheduleJob job) throws SchedulerException;
	/**
	 * 获取所有计划中的任务列表
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getAllJob() throws SchedulerException;
	/**
	 * 所有正在运行的任务
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<ScheduleJob> getRunningJob() throws SchedulerException;
	/**
	 * 暂停一个任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;
	/**
	 * 恢复一个任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;
	/**
	 * 删除一个任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
	/**
	 * 立即执行任务
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException;
}
