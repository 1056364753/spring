package com.lb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lb.common.PageQurey;
import com.lb.entity.Page;
import com.lb.entity.Person;
import com.lb.mapper.PersonMapper;
@Service
public class PersonService {
	
	@Autowired
	private PersonMapper personMapper;
	
	public List<Person> getAll(){
		return personMapper.getAll();
	}
	
	
	public List<Person> findById(Integer id){
		return personMapper.findById(id);
	}
	
	public List<Person> findByName(String name){
		return personMapper.findByName(name);
	}
	
	public List<Person> find(Person p){
		return personMapper.find(p);
	}
	
	public Person insert(Person p) {
		 personMapper.insert(p);
		 System.out.println("添加数据成功，数据为：" + p);
		 return p;
	}
	
	public boolean delete(Integer id) {
		return personMapper.delete(id);
	}
	
	public Person update(Person p) {
		int i = personMapper.update(p);
		System.out.println(i);
		return p;
	}
	
	
	public List<Person> queryPage(Integer startRows) {
        return personMapper.queryPage(startRows);
    }

    public Integer getRowCount() {

        return personMapper.getRowCount();
    }


	public Page queryPage(PageQurey p) {
		
		Page page = new Page();
		
		Integer total = personMapper.total(p.getOnSearch());
		Integer currPage = p.getCurrPage();
		Integer pageSize = p.getPageSize();
		p.setCurrPage((currPage-1)*pageSize);
		List<Person> data = personMapper.data(p);
		
		page.setTotal(total);
		page.setData(data);
		return page;
	}


	public Page a(PageQurey p) {
		Page b = new Page();
		
		Integer total = personMapper.tot(p.getOnSearch());
		Integer currPage = p.getCurrPage();
		Integer pageSize = p.getPageSize();
		p.setCurrPage((currPage-1)*pageSize);
		List<Person> data = personMapper.data(p);
		
		String onsearch = p.getOnSearch();
		
		b.setTotal(total);
		b.setData(data);
		if(onsearch!="") {
			System.out.println("搜索(" + onsearch + ")返回结果的总数为：" + total );
			System.out.println("搜索(" + onsearch + ")返回的结果数据为：" + data);
		}else {
			System.out.println("查询所有数据的总数为：" + total );
			System.out.println("查询所有数据返回的结果为：" + data);
		}
		
		return b;
	}


	public boolean deleteids(String[] ids) {
		return personMapper.deleteIds(ids);
	}


	public Page pageHelp(PageQurey p) {
		Page page = new Page();
		com.github.pagehelper.Page<Object> p2 = PageHelper.startPage(p.getCurrPage(), p.getPageSize(), true);
//		com.github.pagehelper.Page<Object> pages = PageHelper.startPage(p.getCurrPage(), p.getPageSize(), true);
		List<Person> persons = personMapper.getPageHelp(p);
		page.setData(persons);
		page.setTotal((int) p2.getTotal());
		return page;
	}


	public List<Person> insertList(List<Person> list) {
		// TODO Auto-generated method stub
		personMapper.insertList(list);
		return list;
	}


	public boolean deleteAll() {
		// TODO Auto-generated method stub
		return personMapper.deleteAll();
	}

	/**
	 * @param list
	 * @return
	 * 每次插入10条数据
	 */
	public List<Person> insertTen(List<Person> list) {
		// TODO Auto-generated method stub
		for(int i=0;i<=list.size()-1;i+=10) {
			List<Person> list2 = new ArrayList<Person>();
			if(i+10<list.size()) {
				for(int j=i;j<i+10;j++) {
					list2.add(list.get(j));
				}
			}else{
				for(int j=i;j<=list.size()-1;j++) {
					list2.add(list.get(j));
				}
			}
			personMapper.insertList(list2);
			if(list2.size()!=10) {
				System.out.println("最后一次插入的"+list2.size()+"条数据为：" + list2);
			}else {
				System.out.println("每次插入的"+list2.size()+"条数据为：" + list2);
			}
	}
		return list;
	}


	public List<Person> updateList(List<Person> list) {
		// TODO Auto-generated method stub
		personMapper.updateList(list);
		return list;
	}

	
}
