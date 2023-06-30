package com.lb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lb.common.PageQurey;
import com.lb.entity.Person;

@Mapper
public interface PersonMapper {
	
	List<Person> getAll();
	List<Person> findById(Integer id);
	List<Person> findByName(String name);
	List<Person> find(Person p);
	public int insert(Person p);
	public boolean delete(Integer id);
	public int update(Person p);
	
	//分页
	public List<Person> queryPage(Integer startRows);
    public int getRowCount();
	Integer total(String onSearch);
	List<Person> data(PageQurey p);
	Integer tot(String onSearch);
	
	
	/**
	 * @param ids
	 * @return 批量删除
	 */
	boolean deleteIds(String[] ids);
	
	/**
	 * @param PageHelp
	 * @return 分页工具
	 */
	List<Person> getPageHelp(PageQurey p);
	void insertList(List<Person> list);
	boolean deleteAll();
	void updateList(List<Person> list);
	
}
