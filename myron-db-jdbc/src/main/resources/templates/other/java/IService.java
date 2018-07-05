/**
 * 
 */
package ${modelPath}.service;

import java.math.*;
import java.util.*;

import com.ztesoft.core.common.Page;
import com.ztesoft.framework.exception.BaseAppException;

import ${modelPath}.db.po.${dsTable.methodTableName}PO;

/**
 * <Description> <br>
 * 
 * @author codeCreater<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2014年11月11日 <br>
 * @since V1.0<br>
 * @see ${modelPath}.service <br>
 */

public interface I${dsTable.methodTableName}Service {

    ${dsTable.methodTableName}PO selectByPrimaryKey(${dsTable.idColumn.javaType} key) throws BaseAppException;

    List<${dsTable.methodTableName}PO> selectByArg(${dsTable.methodTableName}PO record) throws BaseAppException;

    Page<${dsTable.methodTableName}PO> selectByArgAndPage(${dsTable.methodTableName}PO record, Page<${dsTable.methodTableName}PO> resultPage)
            throws BaseAppException;

    int add(${dsTable.methodTableName}PO record) throws BaseAppException;

    int update(${dsTable.methodTableName}PO record) throws BaseAppException;

    int delete(${dsTable.methodTableName}PO record) throws BaseAppException;

}
