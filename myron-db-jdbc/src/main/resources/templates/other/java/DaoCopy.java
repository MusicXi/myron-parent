package ${modelPath}.db.dao;

import java.lang.reflect.Method;
import java.math.*;
import java.util.*;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ztesoft.core.common.Page;
import com.ztesoft.framework.exception.SysRuntimeException;
import com.ztesoft.framework.util.StringUtils;

import ${modelPath}.db.arg.${dsTable.methodTableName}Arg;
import ${modelPath}.db.arg.${dsTable.methodTableName}Arg.${dsTable.methodTableName}Criteria;
import ${modelPath}.db.dao.mapper.I${dsTable.methodTableName}Mapper;
import ${modelPath}.db.po.${dsTable.methodTableName}PO;

@Repository
public class ${dsTable.methodTableName}Dao extends SqlSessionDaoSupport {

    @Resource(name = "majorSqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public List<Integer> countByArg(${dsTable.methodTableName}Arg arg) {
        return getMapper().countByArg(arg);
    }

    public int deleteByArg(${dsTable.methodTableName}Arg arg) {
        return getMapper().deleteByArg(arg);
    }

    public List<${dsTable.methodTableName}PO> selectByArg(${dsTable.methodTableName}Arg arg) {
        return getMapper().selectByArg(arg);
    }

    public int updateByArgSelective(${dsTable.methodTableName}PO record, ${dsTable.methodTableName}Arg arg) {
        return getMapper().updateByArgSelective(record, arg);
    }

    public int updateByArg(${dsTable.methodTableName}PO record, ${dsTable.methodTableName}Arg arg) {
        return getMapper().updateByArg(record, arg);
    }

    public Page<${dsTable.methodTableName}PO> selectByArgAndPage(${dsTable.methodTableName}Arg arg,
            Page<${dsTable.methodTableName}PO> resultPage) {
        List<${dsTable.methodTableName}PO> resultList = getMapper().selectByArgAndPage(arg,
                resultPage);
        resultPage.setResultList(resultList);
        return resultPage;
    }

    public int insert(${dsTable.methodTableName}PO record) {
        return getMapper().insert(record);
    }

    public int insertSelective(${dsTable.methodTableName}PO record) {
        return getMapper().insertSelective(record);
    }

    public int insertBatch(List<${dsTable.methodTableName}PO> records) {
        return getMapper().insertBatch(records);
    }

    public int deleteByPrimaryKey(${dsTable.idColumn.javaType} key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    public ${dsTable.methodTableName}PO selectByPrimaryKey(${dsTable.idColumn.javaType} key) {
        return getMapper().selectByPrimaryKey(key);
    }

    public int updateByPrimaryKeySelective(${dsTable.methodTableName}PO record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(${dsTable.methodTableName}PO record) {
        return getMapper().updateByPrimaryKey(record);
    }

    /**
     * 根据传入的Map条件进行查询，当前仅支持所有Map中Key字段的EqualTo查询
     * @param params Map,Key=字段名，value=查询值
     * @return
     */
    public List<${dsTable.methodTableName}PO> selectByMap(Map<String, Object> params) {
        return (selectByArg(buildQueryObject(params)));
    }

    private ${dsTable.methodTableName}Arg buildQueryObject(Map<String, Object> params) {

        ${dsTable.methodTableName}Arg arg = new ${dsTable.methodTableName}Arg();
        ${dsTable.methodTableName}Criteria criteria = arg.createCriteria();

        Class criteriaClass = criteria.getClass();
        Set keys = params.keySet();

        if (keys != null) {

            Iterator iterator = keys.iterator();

            while (iterator.hasNext()) {

                Object key = iterator.next();
                Object value = params.get(key);
                for (Method method : criteriaClass.getMethods()) {
                    if (method.getName().equals(
                            "and"+ StringUtils.toUpperCaseFirstOne(key.toString()) + "EqualTo")) {
                        try {
                            method.invoke(criteria, value);
                        }
                        catch (Exception e) {
                            throw new SysRuntimeException(e);
                        }
                        break;
                    }
                }
            }
        }
        return arg;
    }

    public I${dsTable.methodTableName}Mapper getMapper() {
    	return getSqlSession().getMapper(I${dsTable.methodTableName}Mapper.class);
    }

}
