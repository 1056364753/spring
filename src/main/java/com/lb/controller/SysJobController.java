package com.lb.controller;

import java.util.HashMap;
import java.util.Map;

import com.lb.entity.SysJob;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lb.quartz.CronSchedulerJob;



/**
 * 调度任务信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping
public class SysJobController 
{
	
    @Autowired
    private Scheduler scheduler;

    @Autowired
    public CronSchedulerJob scheduleJobs;
    
    /**
     * 暂停定时任务
     */
    @PostMapping("/pause")
    public Map pauseJob() throws SchedulerException
    {
    	Map ajax = new HashMap();

    	scheduler.pauseJob(getJobKey("0/8 * * * * ?", "0/8 * * * * ?"));
    	ajax.put("code", 200);
    	ajax.put("msg", "成功！");
        return ajax;
    }
    
    /**
     * 恢复定时任务
     */
    @PostMapping("/resume")
    public Map resumeJob(SysJob sysJob) throws SchedulerException
    {
    	Map ajax = new HashMap();
        JobKey jobKey = getJobKey("0/8 * * * * ?", "0/8 * * * * ?");
        if (!scheduler.checkExists(jobKey))
        {
        	//不存在的话创建一个
        	scheduleJobs.scheduleJobs();
          
        }
    	scheduler.resumeJob(jobKey);
    	ajax.put("code", 200);
    	ajax.put("msg", "成功！");
        return ajax;
    }
    
    /**
     * 立即执行定时任务
     */
    @PostMapping("/run")
    public Map run() throws SchedulerException
    {
    	Map ajax = new HashMap();
    	// 判断是否存在
        JobKey jobKey = getJobKey("0/8 * * * * ?", "0/8 * * * * ?");
        if (!scheduler.checkExists(jobKey))
        {
        	//不存在的话创建一个
        	scheduleJobs.scheduleJobs();
          
        }
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("name","撒大");
    	
    	scheduler.triggerJob(jobKey,dataMap);
    	ajax.put("code", 200);
    	ajax.put("msg", "成功！");
        return ajax;
    }
    
    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(String jobId, String jobGroup)
    {
        return JobKey.jobKey(jobId, jobGroup);
    }




}
