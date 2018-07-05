/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.myron.db.mybatis.dao;

import java.util.List;
import java.util.Map;

/**
 * DAO支持类实现
 * @param <T>
 */
public interface BaseMybatisDao<T> extends BaseDao {

	//增加记录
	public int insert(T entity);
	public int insertSelective(T entity);

	//删除记录
	public int deleteByPrimaryKey(String id);
	
	//修改记录
	public int updateByPrimaryKey(T entity);
	public int updateByPrimaryKeySelective(T entity);
	
	//查询记录
	public T selectByPrimaryKey(String id);
	
	//查询记录列表
	public List<T> selectList(T entity);
	public List<T> selectListByPage(Map<String, Object> map);	
	
	public List<Map<String, Object>> selectMapList(T entity);
	public List<Map<String, Object>> selectMapListByPage(Map<String, Object> map);
	
}