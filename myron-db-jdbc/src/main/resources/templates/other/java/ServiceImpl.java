/**
 * 
 */
package ${modelPath}.service.impl;

import java.math.*;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztesoft.core.common.Page;
import com.ztesoft.core.convert.IArgConversionService;
import com.ztesoft.core.idproduce.ISequenceGenerator;
import com.ztesoft.framework.exception.BaseAppException;
import com.ztesoft.framework.log.ZTEsoftLogManager;
import com.ztesoft.framework.util.Utils;

import ${modelPath}.db.arg.${dsTable.methodTableName}Arg;
import ${modelPath}.db.arg.${dsTable.methodTableName}Arg.${dsTable.methodTableName}Criteria;
import ${modelPath}.db.dao.${dsTable.methodTableName}Dao;
import ${modelPath}.db.po.${dsTable.methodTableName}PO;
import ${modelPath}.service.I${dsTable.methodTableName}Service;

/**
 * <Description> <br>
 * 
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see ${modelPath}.service.impl <br>
 */

@Service("${dsTable.propertyTableName}Service")
public class ${dsTable.methodTableName}ServiceImpl implements I${dsTable.methodTableName}Service {

    private static final ZTEsoftLogManager logger = ZTEsoftLogManager
            .getLogger(${dsTable.methodTableName}ServiceImpl.class);

    @Autowired
    private ${dsTable.methodTableName}Dao ${dsTable.propertyTableName}Dao;
    

    /**
     * 查询条件转换成Arg类的服务接口
     */
    @Resource(name = "defaultArgConversionService")
    private IArgConversionService argConversionService;

    /**
     * 主键生成器
     */
    @Resource(name = "sequenceProcGenerator")
    private ISequenceGenerator sequenceGenerator;
    

    @Override
    public ${dsTable.methodTableName}PO selectByPrimaryKey(${dsTable.idColumn.javaType} key) throws BaseAppException {
        // TODO 根据业务场景，设置查询条件、数据校验等

        return ${dsTable.propertyTableName}Dao.selectByPrimaryKey(key);
    }

    @Override
    public List<${dsTable.methodTableName}PO> selectByArg(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("selectByArg begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作符
        //${dsTable.methodTableName}Arg arg = new ${dsTable.methodTableName}Arg();
        //${dsTable.methodTableName}Criteria criteria = arg.createCriteria();
        
        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        ${dsTable.methodTableName}Arg arg = argConversionService.invokeArg(${dsTable.methodTableName}Arg.class, record);

        // ///////
        // TODO 根据业务场景，设置查询条件，示例
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }
        // ///////

        return ${dsTable.propertyTableName}Dao.selectByArg(arg);
    }

    @Override
    public Page<${dsTable.methodTableName}PO> selectByArgAndPage(${dsTable.methodTableName}PO record, Page<${dsTable.methodTableName}PO> resultPage)
            throws BaseAppException {
        logger.debug("selectByArgAndPage begin...record={0}", record);

        // 第一种方式：自己创建arg，自行设置查询条件及操作符
        // ${dsTable.methodTableName}Arg arg = new ${dsTable.methodTableName}Arg();
        // //TODO 根据业务场景，设置查询条件，示例
        // ${dsTable.methodTableName}Criteria criteria = arg.createCriteria();
        // if (Utils.notEmpty(record.getUserName())) {
        // criteria.andUserNameLike(record.getUserName());
        // }

        // 第二种方式：利用arg转换服务，转换出arg，带上查询条件及操作符，
        // 转换后，还可以自行对arg进行设置修改
        ${dsTable.methodTableName}Arg arg = argConversionService.invokeArg(${dsTable.methodTableName}Arg.class, record);

        resultPage = ${dsTable.propertyTableName}Dao.selectByArgAndPage(arg, resultPage);


        return resultPage;
    }

    @Override
    public int add(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("add begin...record={0}", record);

        // ///////
        // TODO 根据业务场景，进行重名校验、设置主键、设置属性默认值等
        // 获取主键
        // int pkId = sequenceGenerator.sequenceIntValue("${dsTable.tableName}","${dsTable.tableName}_${dsTable.idColumn.column}");
        // record.set${dsTable.idColumn.methodProperty}(pkId);
        // record.setCreatedDate(new Date());
        // ///////

        return ${dsTable.propertyTableName}Dao.insertSelective(record);
    }

    @Override
    public int update(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("update begin...record={0}", record);
        // TODO 根据业务场景，进行重名校验、数据有效性校验、设置属性默认值等

        return ${dsTable.propertyTableName}Dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int delete(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("delete begin...record={0}", record);
        // TODO 根据业务场景，进行关联性校验、关联删除等

        return ${dsTable.propertyTableName}Dao.deleteByPrimaryKey(record.get${dsTable.idColumn.methodProperty}());
    }

}
