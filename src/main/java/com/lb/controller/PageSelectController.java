package com.lb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lb.common.PageQurey;
import com.lb.entity.Page;
import com.lb.entity.Person;
import com.lb.service.PersonService;

import io.swagger.annotations.ApiOperation;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/person")
public class PageSelectController {
	
	private static final Logger log = LoggerFactory.getLogger(PageSelectController.class);
	@Autowired
	private PersonService perService;
	
	@ApiOperation(value = "分页 以及 分页查询数据")
	@RequestMapping(value = "/page",method = RequestMethod.GET)
	public Page page(PageQurey p) {
		log.info("1111" + p);
		return perService.a(p);
	}
	
	@ApiOperation(value = "pageHelp 分页查询数据")
	@RequestMapping(value = "/pageHelp",method = RequestMethod.POST)
	public Page findAll(PageQurey p) {
		return perService.pageHelp(p);
	}
	
	/**
	 * http://localhost:8087/person//aa
	 * insert into person (id,name,age) values (?,?,?) , (?,?,?) , (?,?,?)
	 * @return 批量插入数据，以集合的方式统一insert，然后list集合返回结果
	 */
	@RequestMapping(value = "/aa")
	public List<Person> persons(){
		Random rand = new Random();
	    int a,b,c,d;
		a = rand.nextInt(10);
	    b = rand.nextInt(100);
	    c = rand.nextInt(1000);
	    d = rand.nextInt(10000);
		
		long a1 = System.currentTimeMillis();

		List<Person> list = new ArrayList<Person>();
		for(int i=0;i<5;i++) {
			Person p = new Person();
			p.setAge(i);
			p.setName("xxxx_"+"188"+i);
			list.add(p);
		}
		System.out.println(list.get(0));
		List<Person> list2 = new ArrayList<Person>();
		try {
			list2 = perService.insertList(list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		
		long b1 = System.currentTimeMillis();
		log.info("sss"+((b1-a1)/1000/60));
		System.out.println((b1-a1)/1000/60);
		return list2;
	}
	
	@RequestMapping(value = "deleteAll")
	public boolean deleteAll() {
		return perService.deleteAll();
	}
	
	
	/**
	 * @return
	 * 添加前端所选的数据，json数组
	 */
	@RequestMapping(value = "/10")
	public List<Person> persons2(String persons){
		List<Person> list =  new ArrayList<Person>();
		
		long a1 = System.currentTimeMillis();
		JSONArray picArray = JSONArray.parseArray(persons);//这个方法的作用就是将json格式的数据转换成数组格式。
        //遍历得到单个的对象
        for (int i = 0; i < picArray.size(); i++) {
            JSONObject jsonObject = picArray.getJSONObject(i);
            Person e = new Person();
            e.setAge((Integer) jsonObject.get("age"));
            e.setName((String) jsonObject.get("name"));
            list.add(e);
            }
		
		List<Person> list3 = perService.insertList(list);
		long b1 = System.currentTimeMillis();
		System.out.println("插入"+list3.size()+"条数据耗时为："+(b1-a1)+"毫秒");
		System.out.println("插入的"+list3.size()+"条数据为："+list3);
		return list3;
	}
	
	
	/**
	 * @return
	 * 每次插入10条数据
	 */
	@RequestMapping(value = "/11")
	public List<Person> persons2(){

		long a1 = System.currentTimeMillis();
		List<Person> list3 = perService.insertTen(perService.getAll());
		long b1 = System.currentTimeMillis();
		System.out.println("插入"+list3.size()+"条数据耗时为："+(b1-a1)+"毫秒");
		System.out.println("插入的"+list3.size()+"条数据为："+list3);
		return list3;
	}
	
	/**
	 * @return
	 * 批量更新，未使用
	 */
	@RequestMapping(value = "/12")
	public List<Person> updateList(String persons){
		List<Person> list =  new ArrayList<Person>();
		
		long a1 = System.currentTimeMillis();
		JSONArray picArray = JSONArray.parseArray(persons);//这个方法的作用就是将json格式的数据转换成数组格式。
        //遍历得到单个的对象
        for (int i = 0; i < picArray.size(); i++) {
            JSONObject jsonObject = picArray.getJSONObject(i);
            Person e = new Person();
            e.setId((Integer) jsonObject.get("id"));
            e.setAge((Integer) jsonObject.get("age"));
            e.setName("测试");
            list.add(e);
            }
		
		List<Person> list3 = perService.updateList(list);
		long b1 = System.currentTimeMillis();
		System.out.println("插入"+list3.size()+"条数据耗时为："+(b1-a1)+"毫秒");
		System.out.println("插入的"+list3.size()+"条数据为："+list3);
		return list3;
	}
	
}
