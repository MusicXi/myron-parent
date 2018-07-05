package com.myron.db.mybatis.dao;

/**
 * 继承 BaseMybatisDao<T>
 * @author Administrator
 *
 * @param <T>
 */
public interface BaseCacheDao<T> extends BaseMybatisDao<T>{
	public T createReturn(T entity);
	public T updateSelectiveReturn(T entity);

}
