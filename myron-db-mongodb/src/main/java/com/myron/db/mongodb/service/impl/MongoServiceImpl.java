package com.myron.db.mongodb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.myron.db.mongodb.dao.MongoDao;
import com.myron.db.mongodb.service.MongoService;




@Service("mongoService")
public class MongoServiceImpl implements MongoService{
	
    @Autowired
    private MongoDao mongoDao;
    /**
     * 保存
     *
     * @param obj
     * @author lxc
     */
	public void save(Object obj) {
		// TODO Auto-generated method stub
		mongoDao.save(obj);
	}
	/**
     * 批量保存
     *
     * @param objs
     * @author lxc
     */
	public <T> void saveAll(List<T> objs) {
		// TODO Auto-generated method stub
		mongoDao.saveAll(objs);
	}
	/**
     * 根据条件查找第一个返回的数据
     *
     * @param clazz	实体类
     * @param query	查询条件
     * @author lxc
     * @return
     */
	public <T> T findOne(Class<T> clazz, Query query) {
		// TODO Auto-generated method stub
		return mongoDao.findOne(clazz, query);
	}
	 /**
     * 查询所有
     *
     * @param clazz
     * @author lxc
     * @return
     */
	public <T> List<T> findAll(Class<T> clazz) {
		// TODO Auto-generated method stub
		return mongoDao.findAll(clazz);
	}
	/**
     * 根据ID查询
     *
     * @param clazz
     * @param id
     * @author lxc
     * @return
     */
	public <T> T findById(Class<T> clazz, Object id) {
		// TODO Auto-generated method stub
		return mongoDao.findById(clazz, id);
	}
	/**
     * 根据条件查询
     *
     * @param clazz
     * @param query
     * @author lxc
     * @return
     */
	public <T> List<T> find(Class<T> clazz, Query query) {
		// TODO Auto-generated method stub
		return mongoDao.find(clazz, query);
	}
	 /**
     * 分页查询
     *
     * @param clazz
     * @param query	查询条件
     * @param currentPage	当前页
     * @param pageSize	每页显示多少条
     * @author lxc
     * @return
     */
	public <T> List<T> findList(Class<T> clazz, Query query, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		return mongoDao.findList(clazz, query, currentPage, pageSize);
	}
	/**
     * 查询总数
     *
     * @param clazz	实体
     * @param query	查询条件
     * @author lxc
     * @return
     */
	public <T> long findCount(Class<T> clazz, Query query) {
		// TODO Auto-generated method stub
		return mongoDao.findCount(clazz, query);
	}
	 /**
     * 更新第一条记录
     *
     * @param query	更新条件
     * @param update	更新
     * @param clazz
     * @author lxc
     * @return
     */
	public <T> int update(Query query, Update update, Class<T> clazz) {
		// TODO Auto-generated method stub
		return mongoDao.update(query, update, clazz);
	}
	 /**
     * 批量更新
     *
     * @param query	更新条件
     * @param update	更新
     * @param clazz
     * @author lxc
     * @return
     */
	public <T> int updateAll(Query query, Update update, Class<T> clazz) {
		// TODO Auto-generated method stub
		return mongoDao.updateAll(query, update, clazz);
	}
	 /**
     * 删除所有
     *
     * @param clazz
     * @author lxc
     */
	public <T> void deleteAll(Class<T> clazz) {
		// TODO Auto-generated method stub
		mongoDao.deleteAll(clazz);
	}
	/**
     * 根据ID删除
     *
     * @param obj 删除实体,只需包含id
     * @author lxc
     */
	public <T> void deleteByEntity(Object obj) {
		// TODO Auto-generated method stub
		mongoDao.deleteByEntity(obj);
	}
	 /**
     * 删除
     *
     * @param criteria
     * @param clazz
     * @author lxc
     */
	public <T> void delete(Criteria criteria, Class<T> clazz) {
		// TODO Auto-generated method stub
		mongoDao.delete(criteria, clazz);
	}
	 /**
     * 删除
     *
     * @param query
     * @param clazz
     * @author lxc
     */
	public <T> void delete(Query query, Class<T> clazz) {
		// TODO Auto-generated method stub
		mongoDao.delete(query, clazz);
	}
	 /**
     * 聚合查询重复数据
     * @param collectionName	mongodb中collection的名字,及相当于oracle的表名
     * @param outputType			输出的类型
     * @param criteria				 条件
     * @param fields                   聚合的字段
     * 说明：根据某个字段进行分组时，fields需要添加一个"1",否则查出来的数据为空;
     * 若根据两个字段进行分组，则直接填写两个field,无需添加"1";
     * @return
     * @author lxc
     */
    public <T> List<T> findDuplicateDataByGroup(String collectionName,Class<T>outputType
    		,Criteria criteria,String...fields){
    	return this.mongoDao.findDuplicateDataByGroup(collectionName, outputType, criteria, fields);
    }
    /**
     * 查询去重数据
     * @param collectionName		更新的集合(Table)
     * @param key					查询的字段
     * @return  list<T>				返回list对象
     * @author jzj
     */
    public <T> List<T> findDistinctData(String collectionName,String key){
    	return this.mongoDao.findDistinctData(collectionName, key);
    }
    /**
     * 聚合查询个数
     * @param collectionName	mongodb中collection的名字,及相当于oracle的表名
     * @param criteria     			条件
     * @param fields                   聚合的字段
     * @return
     * @author lxc
     */
    public <T> List <T> findDataByGroupBy(String collectionName,Criteria criteria,String...fields){
    	return this.mongoDao.findDataByGroupBy(collectionName, criteria, fields);
    }
    
    /**
     * 根据条件去重数据
     * @param	key1				条件key
     * @param	value1				条件值
     * @param	collectionName		查询的集合
     * @param	key					去重的字段
     * @author	jzj
     */
    public <T> List<T> findDataByCondition(String key1,String value1,String collectionName,String key){
    	return this.mongoDao.findDataByCondition(key1, value1, collectionName, key);
    }
}
