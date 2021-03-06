package com.myron.db.mongodb.service;

import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public interface MongoService {
	/**
     * 保存
     * @param obj 任意对象
     * @author lxc
     */
    void save(Object obj);

    /**
     * 批量保存
     *
     * @param objs 对象集合
     * @author lxc
     */
    <T> void saveAll(List<T> objs);

    /**
     * 根据条件查找第一个返回的数据
     *
     * @param clazz	实体类
     * @param query	查询条件
     * @author lxc
     * @return
     * 
     */
    <T> T findOne(Class<T> clazz, Query query);

    /**
     * 查询所有
     *
     * @param clazz 类
     * @author lxc
     * @return
     */
    <T> List<T> findAll(Class<T> clazz);

    /**
     * 根据ID查询
     *
     * @param clazz 类对象
     * @param id 对象ID
     * @author lxc
     * @return
     */
    <T> T findById(Class<T> clazz, Object id);

    /**
     * 根据条件查询
     *
     * @param clazz 类对象
     * @param query 查询对象
     * @author lxc
     * @return
     */
    <T> List<T> find(Class<T> clazz, Query query);

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
    <T> List<T> findList(Class<T> clazz, Query query, int currentPage, int pageSize);

    /**
     * 查询总数
     *
     * @param clazz	实体
     * @param query	查询条件
     * @author lxc
     * @return
     */
    <T> long findCount(Class<T> clazz, Query query);

    /**
     * 更新第一条记录
     *
     * @param query	更新条件
     * @param update	更新
     * @param clazz
     * @author lxc
     * @return
     */
    <T> int update(Query query, Update update, Class<T> clazz);
    /**
     * 批量更新
     * @param query
     * @param update
     * @param clazz
     * @return
     * @author lxc
     */
    <T> int updateAll(Query query, Update update, Class<T> clazz);
    /**
     * 删除所有
     *
     * @param clazz
     * @author lxc
     */
    <T> void deleteAll(Class<T> clazz);

    /**
     * 根据ID删除
     *
     * @param obj 删除实体,只需包含id
     * @author lxc
     */
    public <T> void deleteByEntity(Object obj);

    /**
     * 删除
     *
     * @param criteria
     * @param clazz
     * @author lxc
     */
    public <T> void delete(Criteria criteria, Class<T> clazz);
    
    /**
     * 聚合查询重复数据
     * @param collectionName	mongodb中collection的名字,及相当于oracle的表名
     * @param outputType			输出的类型
     * @param criteria				条件
     * @param fields                   聚合的字段
     * 说明：根据某个字段进行分组时，fields需要添加一个"1",否则查出来的数据为空;
     * 若根据两个字段进行分组，则直接填写两个field,无需添加"1";
     * @return
     * @author lxc
     */
    public <T> List<T> findDuplicateDataByGroup(String collectionName,Class<T>outputType
    		,Criteria criteria,String...fields);
    /**
     * 查询去重数据
     * @param collectionName		更新的集合(Table)
     * @param key					查询的字段
     * @return  list<T>				返回list对象
     * @author jzj
     */
    public <T> List<T> findDistinctData(String collectionName,String key);
    
    /**
     * 聚合查询个数
     * @param collectionName	mongodb中collection的名字,及相当于oracle的表名
     * @param criteria     			条件
     * @param fields                   聚合的字段
     * @return
     * @author lxc
     */
    public <T> List <T> findDataByGroupBy(String collectionName,Criteria criteria,String...fields);
  
    /**
     * 根据条件去重数据
     * @param	key1				条件key
     * @param	value1				条件值
     * @param	collectionName		查询的集合
     * @param	key					去重的字段
     * @author	jzj
     */
    public <T> List<T> findDataByCondition(String key1,String value1,String collectionName,String key);
}
