package ${table.packageName};

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import com.myron.common.util.UuidUtils;
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
	private ${column.javaType} ${column.camelField};			<#if column.comment!="">//${column.comment}</#if>	
	</#list>
	
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
	
	/**
	 * 插入之前执行方法，需要手动调用
	 * @return this
	 */
	public void preInsert(){
		<#list table.columnList as column>
		<#if column.key=="PRI">	
		if(this.${column.camelField}==null || "".equals(this.${column.camelField})){
			this.set${column.capitalizeCamelField}(UuidUtils.creatUUID());
//			User user=(User) UserUtils.getPrincipal();
//			this.createBy=user.getId();
//			this.createDate=new Date();
		}
		
		</#if>
		</#list>
	}
	
	/**
	 * 更新之前调用,需要手动调用
	 * @return
	 */
	public Object preUpdate(){
		<#list table.columnList as column>
		<#if column.key=="PRI">	
		if(!(this.${column.camelField}==null || "".equals(this.${column.camelField}))){
//			User user=(User) UserUtils.getPrincipal();
//			this.updateBy = user.getId();
//			this.updateDate=new Date();
		}
		<#break>
		</#if>
		</#list>
		return this;
	}
}