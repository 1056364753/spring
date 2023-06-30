package com.lb.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lb.common.PageQurey;
import com.lb.common.ResultCode;
import com.lb.entity.Page;
import com.lb.entity.Person;
import com.lb.service.PersonService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class PersonController {
	
	@Autowired
	private PersonService personService;
		
//	测试地址：http://localhost:8087/select  查询全部！！
	@ApiOperation(value = "查询全部")
	@RequestMapping(value = "select",method = RequestMethod.GET)
	public List<Person> a(){
		System.out.println("查询全部的结果为" + personService.getAll());
		return personService.getAll();
	}
	
	
//	测试地址：http://localhost:8087/find?id=1   根据id查询！！
	@ApiOperation(value = "根据id查询")
	@RequestMapping(value = "find",method = RequestMethod.GET)
	public List<Person> b(Integer id){
		System.out.println("查询id=" + id + "的结果为："  + personService.findById(id));
		return personService.findById(id);
	}

//	测试地址：http://localhost:8087/find1/1  根据id查询！！
	@ApiOperation(value = "根据id查询方法2")
	@RequestMapping(value = "find1/{id}",method = RequestMethod.GET)
	public List<Person> b1(@PathVariable Integer id){
		return personService.findById(id);
	}
	
//	测试地址：http://localhost:8087/find2?name=李伟   根据姓名查询！！
	@ApiOperation(value = "根据姓名查询")
	@RequestMapping(value = "find2",method = RequestMethod.GET)
	public List<Person> b2(String name){
		System.out.println("根据name查询结果为:" + personService.findByName(name));
		return personService.findByName(name);
	}
	
//	测试地址：http://localhost:8087/find3/李伟   根据姓名查询！！
	@ApiOperation(value = "根据姓名查询方法2")
	@RequestMapping(value = "find3/{name}",method = RequestMethod.GET)
	public List<Person> b3(@PathVariable String name){
		System.out.println("根据name查询结果为:" + personService.findByName(name));
		return personService.findByName(name);
	}
	
//	@ApiOperation(value = "根据姓名查询方法2")
//	@RequestMapping(value = "find4/{name}",method = RequestMethod.GET)
//	public List<Person> b4(@PathVariable String name){
//		System.out.println("根据name查询结果为:" + personService.findByName(name));
//		return personService.findByName(name);
//	}
//	
	
//	测试地址：http://localhost:8087/insert 增加数据！！
	@ApiOperation(value = "添加数据")
	@PostMapping(value = "insert")
	public Person c(@RequestBody Person p) {
		
		return personService.insert(p);
	}
	
//	测试地址：http://localhost:8087/delete?id=1 根据id删除！！
	@ApiOperation(value = "根据id删除")
	@RequestMapping(value = "delete",method = RequestMethod.DELETE)
	public boolean d(Integer id) {
		if(Objects.nonNull(id)) {
		
		boolean success = personService.delete(id);
		if(success) {
			System.out.println(ResultCode.SUCCESS);
			return ResultCode.SUCCESS != null;
			}
		}
		System.out.println(ResultCode.Fail);
		return ResultCode.Fail != null;
	}

		
//	测试地址：http://localhost:8087/update	修改数据！！
	@ApiOperation(value = "更新数据")
	@RequestMapping(value = "update",method = RequestMethod.PUT)		
	public Person e(@RequestBody Person p) {
		System.out.println("更新数据成功，更新后的数据为：" + p);
		return personService.update(p);
	}


//	测试地址：http://localhost:8087/page 一页5条数据
    @RequestMapping(value="/page")
    public List<Person> page(Integer page){
        int pageNow = page == null ? 1 : page;
        int pageSize = 5;
        int startRows = pageSize*(pageNow-1);
        List<Person> list = personService.queryPage(startRows);
        return list;
    }

//	测试地址：http://localhost:8087/rows	查询页面总数！！
    @RequestMapping(value="/rows")
    public int rows(){
    	int i = personService.getRowCount();
    	System.out.println("页面总数：" + i);
        return i;
    }	
	
//	分页 和 查询    
	@RequestMapping("/a")
	public Page page(PageQurey p) {
		return personService.queryPage(p);
	}
	
//	测试地址：http://localhost:8087/del/1,2  根据id批量删除！！
	@ApiOperation(value = "根据id批量删除")
	@RequestMapping(value = "/del/{id}",method = RequestMethod.DELETE)
	public boolean del(@PathVariable("id") String id) {
		String [] ids = id.split(",");
		System.out.println("批量删除的id:(" + id +")");
		boolean ok = personService.deleteids(ids);
		if(ok) {
			System.out.println("删除" + ResultCode.SUCCESS);
//			return ResultCode.SUCCESS != null;
			return ok;
		}else {
			return ResultCode.Fail !=null ;
		}
		
	}
    
    
    
	
//	测试连通性
//	@RequestMapping("test")
//	public String test() {
//		return "123";
//	}
	
//	@RequestMapping("select")
//	public ModelAndView select() {
//		ModelAndView all = new ModelAndView("select");
//		List<Person> list = personService.getAll();
//		all.addObject("list", list);
//		return all;		
//	}

}
