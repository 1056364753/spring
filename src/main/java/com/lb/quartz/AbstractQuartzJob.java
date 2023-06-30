//package com.lb.quartz;
//
//import java.util.Date;
//
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//
//import com.lb.common.BeanUtils;
//import com.lb.entity.SysJob;
//
///**
// * 抽象quartz调用
// *
// * @author ruoyi
// */
//public abstract class AbstractQuartzJob implements Job
//{
//
//    /**
//     * 线程本地变量
//     */
//    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException
//    {
//        SysJob sysJob = new SysJob();
//        BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(""));
//        try
//        {
//            before(context, sysJob);
//            if (sysJob != null)
//            {
//                doExecute(context, sysJob);
//            }
//            after(context, sysJob, null);
//        }
//        catch (Exception e)
//        {
//            after(context, sysJob, e);
//        }
//    }
//
//    /**
//     * 执行前
//     *
//     * @param context 工作执行上下文对象
//     * @param sysJob 系统计划任务
//     */
//    protected void before(JobExecutionContext context, SysJob sysJob)
//    {
//        threadLocal.set(new Date());
//    }
//
//    /**
//     * 执行后
//     *
//     * @param context 工作执行上下文对象
//     * @param sysJob 系统计划任务
//     */
//    protected void after(JobExecutionContext context, SysJob sysJob, Exception e)
//    {
//        Date startTime = threadLocal.get();
//    }
//
//    /**
//     * 执行方法，由子类重载
//     *
//     * @param context 工作执行上下文对象
//     * @param sysJob 系统计划任务
//     * @throws Exception 执行过程中的异常
//     */
//    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
//}
