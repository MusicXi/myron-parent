Ext.define("${table.className}Manager.view.${table.className}Panel",{
	extend : 'Ext.panel.Panel',
	alias : 'widget.${table.className?uncap_first}Panel',
	layout : 'fit',

	initComponent : function() {
		this.callParent([this]);
	},
	items:{
		xtype:'gridpanel',
		name:'${table.className?uncap_first}GridPanel',
		extend: 'Ext.grid.Panel',
		title: '${table.className}列表',
		store: '${table.className}',
		selType : 'checkboxmodel',//选则类型为checkbox
		//selType: 'rowmodel',
		enableKeyNav:true,		  //鼠标控制列移动
		columnLines:true,		  //显示纵向表格线,默认false
		border:false,
		
	    columns: [
	        <#list table.columnList as column>
	        <#if column.javaType=="Integer" 
				|| column.javaType=="Short" 
				|| column.javaType=="Double" 
				|| column.javaType=="Float" 
				|| column.javaType=="BigDecimal" 
				|| column.javaType=="Long">
	        { header: '${column.comment}',  dataIndex: '${column.camelField}', xtype: 'numbercolumn', format:'0.00' }<#if column_has_next>,</#if>
	        <#else>
	        { header: '${column.comment}',  dataIndex: '${column.camelField}' }<#if column_has_next>,</#if>
	        </#if>
	        </#list>	
	    ],
	    //顶部工具条
		tbar: [
			{
				xtype: 'button',
				text: '查询',
				iconCls:'toolbar-search', 
				action:'query',
				disabled: false
			},
			'-',
			{
				xtype: 'button',
				text: '新增',
				iconCls:'toolbar-add', 
				action:'add',
				disabled: false
			},
			'-',
			{
				xtype: 'button',
				text: '复制',
				iconCls:'toolbar-add', 
				action:'copy',
				disabled: false
			},
			'-',
			{
				xtype: 'button',
				text: '删除',
				iconCls:'toolbar-delete', 
				action:'delete',
				disabled: false
			},
			'-',
			{
				xtype: 'button',
				text: '刷新',
				iconCls:'toolbar-refresh',
				action:'refresh',
				disabled: false
			},
			'-',
			{
	    	    xtype: 'button',
	    	    text: '导入Excel',
	    	    action : 'importExcel',
	    	    disabled: false
			},
			'-',
			{
	    	    xtype: 'button',
	    	    text: '导出Excel',
	    	    action : 'exportExcel',
	    	    disabled: false
			},
			'->',
			{
	    	    xtype: 'button',
	    	    text: '下载Excel模板',
	    	    action : 'download',
	    	    disabled: false
			}
		],
		//分页设置
		dockedItems: [
			{
				xtype: 'pagingtoolbar',
				dock: 'bottom',
				store: '${table.className}',
				displayInfo: true
			}
		]
	  
	}
})

