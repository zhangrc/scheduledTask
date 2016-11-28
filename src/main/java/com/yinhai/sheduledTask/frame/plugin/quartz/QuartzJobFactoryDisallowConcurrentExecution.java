package com.yinhai.sheduledTask.frame.plugin.quartz;


import com.yinhai.sheduledTask.frame.plugin.network.util.RpcDTO;
import com.yinhai.sheduledTask.frame.plugin.quartz.domain.ScheduleJob;
import com.yinhai.sheduledTask.frame.plugin.quartz.util.TaskUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 若一个方法一次执行不完下次轮转时则等待该方法执行完后才执行下一次操作
 *
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Map<String,Object> dto = new HashMap<String,Object>();
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		try {
			TaskUtils.post(new RpcDTO(scheduleJob.getSpring_id(), scheduleJob.getMethod_name(), dto));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}