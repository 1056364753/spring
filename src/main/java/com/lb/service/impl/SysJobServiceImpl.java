//package com.lb.service.impl;
//
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.lb.entity.SysJob;
//import com.lb.mapper.SysJobMapper;
//import com.lb.quartz.ScheduleUtils;
//import com.lb.service.SysJobService;
//
//@Service
//public class SysJobServiceImpl implements SysJobService{
//	
//    @Autowired
//    private Scheduler scheduler;
//
//    @Autowired
//    private SysJobMapper jobMapper;
//
//	@Override
//	public int insertJob(SysJob job) throws SchedulerException {
//		job.setStatus("1");
//        int rows = jobMapper.insertJob(job);
//        if (rows > 0)
//        {
//            ScheduleUtils.createScheduleJob(scheduler, job);
//        }
//        return rows;
//	}
//
//}
