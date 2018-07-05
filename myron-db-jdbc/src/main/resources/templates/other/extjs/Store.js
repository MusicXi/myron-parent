/**
 * @description ${dsTable.methodTableName}分页数据源
 * 
 * @author codeCreater
 * 
 */
<#if jsCodePathSplitByPoint ==""> 
	<#if model ==""> 
Ext.define('component.store.${dsTable.methodTableName}Store', {
	<#else>
Ext.define('component.${model}.store.${dsTable.methodTableName}Store', {
	</#if>
<#else> 
	<#if model ==""> 
Ext.define('component.${jsCodePathSplitByPoint}.store.${dsTable.methodTableName}Store', {
	<#else>
Ext.define('component.${jsCodePathSplitByPoint}.${model}.store.${dsTable.methodTableName}Store', {
	</#if>
</#if> 
    extend : 'Ext.data.Store',
<#if jsCodePathSplitByPoint ==""> 
	<#if model ==""> 
    model : 'component.model.${dsTable.methodTableName}Model',
	<#else>
    model : 'component.${model}.model.${dsTable.methodTableName}Model',
	</#if>
<#else> 
	<#if model ==""> 
    model : 'component.${jsCodePathSplitByPoint}.model.${dsTable.methodTableName}Model',
	<#else>
    model : 'component.${jsCodePathSplitByPoint}.${model}.model.${dsTable.methodTableName}Model',
	</#if>
</#if> 
    proxy : {
        type : 'ajax',
        url : webRoot + '${jsCodePath}/${model}/${dsTable.controllerTableName}/queryRecordByPage.do',
        reader : ztesoft_pageReader
    }

});
