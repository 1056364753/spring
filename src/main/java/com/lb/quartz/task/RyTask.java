package com.lb.quartz.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lb.controller.PageSelectController;
import com.lb.entity.Person;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
	@Autowired
	private PageSelectController controller;
	
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println("1");
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
    
    public void insert()
    {
    	List<Person> list2 =controller.persons();
        System.out.println("增加数据成功！"+list2);
    }
}
