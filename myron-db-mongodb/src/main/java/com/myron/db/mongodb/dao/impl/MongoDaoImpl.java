package com.myron.db.mongodb.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.CommandResult;
import com.mongodb.DBObject;
import com.myron.db.mongodb.dao.MongoDao;



@Repository("mongoDao")
public class MongoDaoImpl implements MongoDao {

	@Autowired
    private MongoTemplate mongoTemplate;
	  /**
     * 根据条件查找第一个返回的数据
     *
     * @param clazz	实体类
     * @param query	查询条件
     * @author lxc
     * @return
     */
    public <T> T findOne(Class<T> clazz, Query query) {
        return mongoTemplate.findOne(query, clazz);
    }
    /**
     * 保存
     *
     * @param obj
     * @author lxc
     */
    public void save(Object obj) {
        mongoTemplate.save(obj);
    }
    /**
     * 批量保存
     *
     * @param objs
     * @author lxc
     */
    public <T> void saveAll(List<T> objs) {
        //    	mongoTemplate.insertList(objs);
        mongoTemplate.insertAll(objs);
    }
    /**
     * 查询所有
     *
     * @param clazz
     * @author lxc
     * @return
     */
    public <T> List<T> findAll(Class<T> clazz) {
        return mongoTemplate.findAll(clazz);
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
        return mongoTemplate.findById(id, clazz);
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
        return mongoTemplate.find(query, clazz);
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
        //计算起始位置
        int startIndex = ((currentPage - 1) < 0 ? 0 : (currentPage - 1)) * pageSize;
        query.skip(startIndex);
        query.limit(pageSize);
        return mongoTemplate.find(query, clazz);
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
    	long count=mongoTemplate.count(query, clazz);
        return count;
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
        //        return  mongoTemplate.updateFirst(query, update).getN();
        return mongoTemplate.updateFirst(query, update, clazz).getN();
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
		return mongoTemplate.updateMulti(query, update, clazz).getN();
	}
    /**
     * 删除
     *
     * @param criteria
     * @param clazz
     * @author lxc
     */
    public <T> void delete(Criteria criteria, Class<T> clazz) {
        Query query = new Query(criteria);
        mongoTemplate.remove(query, clazz);
    }
    /**
     * 删除
     *
     * @param query
     * @param clazz
     * @author lxc
     */
    public <T> void delete(Query query, Class<T> clazz) {
        mongoTemplate.remove(query, clazz);
    }
    /**
     * 根据条件删除
     *
     * @param obj 删除实体
     * @author lxc
     */
    public <T> void deleteByEntity(Object obj) {
        mongoTemplate.remove(obj);
    }
    /**
     * 删除所有
     *
     * @param clazz
     * @author lxc
     */
    public <T> void deleteAll(Class<T> clazz) {
        mongoTemplate.dropCollection(clazz);
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List<T> findDuplicateDataByGroup(String collectionName,Class<T>outputType,Criteria criteria,String...fields){
    	AggregationOperation match = Aggregation.match(criteria);
	    AggregationOperation group = Aggregation.group(fields);
	    Aggregation aggregation = Aggregation.newAggregation(match, group);
	    AggregationResults result = this.mongoTemplate.aggregate(aggregation, collectionName, outputType);
	    return result.getMappedResults();
    }
    /**
     * 查询去重数据
     * @param collectionName		更新的集合(Table)
     * @param key					查询的字段
     * @return  list<T>				返回list对象
     * @author jzj
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> findDistinctData(String collectionName,String key){
    	String jsonSql = "{distinct:'"+collectionName+"', key:'"+key+"'}";
    	CommandResult commandResult=mongoTemplate.executeCommand(jsonSql);
    	List<T> list =(List<T>)commandResult.get("values");  
    	return list;
    }
    /**
     * 聚合查询个数
     * @param collectionName	mongodb中collection的名字,及相当于oracle的表名
     * @param criteria     			条件
     * @param fields                   聚合的字段
     * @return
     * @author lxc
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> List <T> findDataByGroupBy(String collectionName,Criteria criteria,String...fields){
    	AggregationOperation match = Aggregation.match(criteria);
    	GroupOperation group = Aggregation.group(fields).count().as("count");
	    Aggregation aggregation = Aggregation.newAggregation(match, group);
	    AggregationResults result = this.mongoTemplate.aggregate(aggregation, collectionName, DBObject.class);
	    return result.getMappedResults();
    }
    /**
     * 根据条件去重数据
     * @param	key1				条件key
     * @param	value1				条件值
     * @param	collectionName		查询的集合
     * @param	key					去重的字段
     * @author	jzj
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> findDataByCondition(String key1,String value1,String collectionName,String key){
    	 Query query = Query.query(Criteria.where(key1).is(value1));
	     List<T> result = mongoTemplate.getCollection(collectionName).distinct(key, query.getQueryObject());
	     return result;
    }
    
    
}
