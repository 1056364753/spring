package com.lb.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.lb.quartz.task.Task;

@Component
public class CronSchedulerJob {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private void scheduleJob1(Scheduler scheduler) throws SchedulerException {
    	String express = "0/8 * * * * ?";
        JobDetail jobDetail = JobBuilder.newJob(Task.class) .withIdentity(express, express).build();
        // 6的倍数秒执行 也就是 6 12 18 24 30 36 42 ....
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(express);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(express, express)
                .usingJobData("name","王智1").withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.pauseJob(getJobKey(express, express));
    }

//    private void scheduleJob2(Scheduler scheduler) throws SchedulerException{
//        JobDetail jobDetail = JobBuilder.newJob(ScheduledJob2.class) .withIdentity("job2", "group2").build();
//        // 12秒的倍数执行  12  24 36  48  60
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/12 * * * * ?");
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2")
//                .usingJobData("name","王智2").withSchedule(scheduleBuilder).build();
//        scheduler.scheduleJob(jobDetail,cronTrigger);
//    }

    /**
     * @Author Smith
     * @Description 同时启动两个定时任务
     * @Date 16:31 2019/1/24
     * @Param
     * @return void
     **/
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduleJob1(scheduler);
//        scheduleJob2(scheduler);
    }
    
    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(String jobId, String jobGroup)
    {
        return JobKey.jobKey(jobId, jobGroup);
    }
}
