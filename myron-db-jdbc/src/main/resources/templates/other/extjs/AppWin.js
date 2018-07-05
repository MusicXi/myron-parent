/**
 * 用户管理，DEMO， <br>
 * 布局方式：上（north，查询Form）、中（center，带分页数据Grid）、下（south，操作Form）
 * 
 * @author codeCreater
 */

Ext.onReady(function() {
    var thizPanel, thizSearchForm;

    // 记录表格
<#if jsCodePathSplitByPoint ==""> 
	<#if model ==""> 
    thizPanel = Ext.create('component.view.${dsTable.methodTableName}Panel', {
	<#else>
    thizPanel = Ext.create('component.${model}.view.${dsTable.methodTableName}Panel', {
	</#if>
<#else> 
	<#if model ==""> 
    thizPanel = Ext.create('component.${jsCodePathSplitByPoint}.view.${dsTable.methodTableName}Panel', {
	<#else>
    thizPanel = Ext.create('component.${jsCodePathSplitByPoint}.${model}.view.${dsTable.methodTableName}Panel', {
	</#if>
</#if>

        region : "center",
        // isPage : true,
        title : "${dsTable.methodTableName}列表"
    });

    // 查询条件框
    thizSearchForm = Ext.create("ZTEsoft.form.SearchForm", {
        region : "north",
        store : thizPanel.getBusizGrid().getStore(),
        items : [
		<#list dsTable.dsColumnExIdList as column>
	      	<#if column_has_next> 
	      	{
	            fieldLabel : "${column.comment}",
	            xtype : "textfield",
	            name : "${column.property}"
        	},
	       </#if> 	
	       <#if !column_has_next> 
	      	{
	            fieldLabel : "${column.comment}",
	            xtype : "textfield",
	            name : "${column.property}"
        	}	       
	       </#if>      
		</#list> 
        ]

    });

    // 整体页面布局
    Ext.create('Ext.container.Viewport', {
        layout : 'border',
        items : [thizSearchForm, thizPanel]
    });

});
