package ${table.packageName};

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
<#list table.columnList as column>
<#if column.javaType=="Date">

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
<#break>
</#if>
</#list>
<#list table.columnList as column>
<#if column.javaType=="BigDecimal">
import java.math.BigDecimal;
</#if>	
</#list>

public class ${table.className} implements Serializable{
	private static final long serialVersionUID = 1L;
	
	<#list table.columnList as column>
	<#if column.javaType=="Date">
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	</#if>
	private ${column.javaType} ${column.camelField};<#if column.comment!="">//${column.comment}</#if>	
	</#list>
	
	public ${table.className}(){
		super();
	}
	
	<#list table.columnList as column>
    <#if column.javaType=="String"> 
    public String get${column.capitalizeCamelField}() {
        return StringUtils.isBlank(${column.camelField}) ? ${column.camelField} : ${column.camelField}.trim();
    }
    
    public void set${column.capitalizeCamelField}(String ${column.camelField}) {
        this.${column.camelField} = ${column.camelField};
    }
    
    <#else>
    public ${column.javaType} get${column.capitalizeCamelField}() {
        return ${column.camelField};
    }
    
    public void set${column.capitalizeCamelField}(${column.javaType} ${column.camelField}) {
        this.${column.camelField} = ${column.camelField};
    }

	</#if>	
	</#list>
	
	@Override
	public String toString() {
		return "${table.className} ["
				<#list table.columnList as column>
				<#if column_has_next && column_index == 0>
				+ "${column.camelField} = " + ${column.camelField} 
				<#else>
				+ ", ${column.camelField} = " + ${column.camelField} 
				</#if>
				</#list>
				+ "]";
	}
	
}