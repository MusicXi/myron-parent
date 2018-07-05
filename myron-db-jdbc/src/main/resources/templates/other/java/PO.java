package ${modelPath}.db.po;

import java.math.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import com.ztesoft.framework.dto.AbstractDto;

public class ${dsTable.methodTableName}PO extends AbstractDto{
	<#list dsTable.dsColumnList as column>
	private ${column.javaType}  ${column.property};
	</#list>
	<#list dsTable.dsColumnList as column>
    <#if column.javaType="String"> 
    public String get${column.methodProperty}() {
        return StringUtils.isBlank(${column.property}) ? ${column.property} : ${column.property}.trim();
    }
    public void set${column.methodProperty}(String ${column.property}) {
        this.${column.property} = ${column.property};
    }
    
    
    <#else>
    public ${column.javaType} get${column.methodProperty}() {
        return ${column.property};
    }
    public void set${column.methodProperty}(${column.javaType} ${column.property}) {
        this.${column.property} = ${column.property};
    }

    
	</#if>	
	</#list>	
}