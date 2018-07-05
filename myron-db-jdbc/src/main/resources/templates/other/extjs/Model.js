/**
 * @description 表,${dsTable.tableName}
 * 
 * @author codeCreater
 */
<#if jsCodePathSplitByPoint ==""> 
	<#if model ==""> 
Ext.define('component.model.${dsTable.methodTableName}Model', {
	<#else>
Ext.define('component.${model}.model.${dsTable.methodTableName}Model', {
	</#if>
<#else> 
	<#if model ==""> 
Ext.define('component.${jsCodePathSplitByPoint}.model.${dsTable.methodTableName}Model', {
	<#else>
Ext.define('component.${jsCodePathSplitByPoint}.${model}.model.${dsTable.methodTableName}Model', {
	</#if>
</#if> 
    extend : 'Ext.data.Model',
    fields : [
    <#list dsTable.dsColumnList as column>
       <#if column_has_next> 
       		{
	            name : '${column.property}',
	            type : 'auto'
        	},
       </#if> 	
       <#if !column_has_next> 
       		{
	            name : '${column.property}',
	            type : 'auto'
        	}       
        </#if>   
	</#list>
			]
});