package com.yinhai.sheduledTask.frame.plugin.quartz;


import com.yinhai.sheduledTask.frame.plugin.network.util.RpcDTO;
import com.yinhai.sheduledTask.frame.plugin.quartz.domain.ScheduleJob;
import com.yinhai.sheduledTask.frame.plugin.quartz.util.TaskUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 计划任务执行处 无状态
 */
public class QuartzJobFactory implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Map<String, Object> dto = new HashMap<String, Object>();
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        try {
            TaskUtils.post(new RpcDTO(scheduleJob.getSpring_id(), scheduleJob.getMethod_name(), dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void work() {
        Map<String, Object> dto = new HashMap<String, Object>();
        try {
            TaskUtils.post(new RpcDTO("maintenanceService", "addImportantDetailByAuto", dto));
            TaskUtils.post(new RpcDTO("maintenanceService", "addNormalDetailByAuto", dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}