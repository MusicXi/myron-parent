package ${modelPath}.controller;

import java.math.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.core.common.Page;
import com.ztesoft.framework.exception.BaseAppException;
import com.ztesoft.framework.log.ZTEsoftLogManager;
import ${modelPath}.db.po.${dsTable.methodTableName}PO;
import ${modelPath}.service.I${dsTable.methodTableName}Service;

/**
 * <Description>${dsTable.controllerTableName}管理 <br>
 * 
 * @author codeCreater <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see ${modelPath}.controller <br>
 */

@Controller
@RequestMapping("/${jsCodePath}/${model}/${dsTable.controllerTableName}")
public class ${dsTable.methodTableName}Controller {

    private static final ZTEsoftLogManager logger = ZTEsoftLogManager
            .getLogger(${dsTable.methodTableName}Controller.class);

    @Autowired
    private I${dsTable.methodTableName}Service ${dsTable.propertyTableName}Service;

    @RequestMapping("index")
    public String index(Model model) {
        // ///////
        // TODO 根据业务场景，进行条件分支跳转、设置页面默认值等

        // ///////

        return "${jsCodePath}/${model}/jsp/${dsTable.propertyTableName}";
    }

    @RequestMapping("queryRecordByPage")
    @ResponseBody
    public Page<${dsTable.methodTableName}PO> queryRecordByPage(${dsTable.methodTableName}PO record,
            Page<${dsTable.methodTableName}PO> resultPage) throws BaseAppException {
        resultPage = ${dsTable.propertyTableName}Service.selectByArgAndPage(record, resultPage);
        return resultPage;
    }

    @RequestMapping("add")
    @ResponseBody
    public ${dsTable.methodTableName}PO add(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("add record begin...record=[{0}]", record);
        ${dsTable.propertyTableName}Service.add(record);
        return record;
    }

    @RequestMapping("update")
    @ResponseBody
    public ${dsTable.methodTableName}PO update(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("modify record begin...record=[{0}]", record);
        ${dsTable.propertyTableName}Service.update(record);
        return record;
    }

    @RequestMapping("delete")
    @ResponseBody
    public int delete(${dsTable.methodTableName}PO record) throws BaseAppException {
        logger.debug("delete record begin...record=[{0}]", record);
        return ${dsTable.propertyTableName}Service.delete(record);
    }

    @RequestMapping("qryRecordInfo")
    @ResponseBody
    public ${dsTable.methodTableName}PO qryRecordInfo(@RequestParam(value = "${dsTable.idColumn.property}",
            required = true) ${dsTable.idColumn.javaType} ${dsTable.idColumn.property}) throws BaseAppException {
        ${dsTable.methodTableName}PO record = ${dsTable.propertyTableName}Service.selectByPrimaryKey(${dsTable.idColumn.property});
        return record;
    }

}
