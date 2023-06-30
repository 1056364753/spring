package com.lb.quartz.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

import com.lb.quartz.JobInvokeUtil;

public class Task implements Job{
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	JobKey triggerName = jobExecutionContext.getJobDetail().getKey();
    	String expression = triggerName.getName();
    	try {
    		
			JobInvokeUtil.invokeMethod();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.println("CRON ----> schedule job1 is running ... + " + triggerName + "  ---->  " + dateFormat.format(new Date()));
    }
}
