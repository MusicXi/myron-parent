Ext.define('${table.className}Manager.view.${table.className}Win', {
	extend:'Ext.window.Window',
	alias:'widget.${table.className?uncap_first}Win',
	id:'${table.className?uncap_first}WinID',
	closeAction:'hide',
	border:false,
	modal:true,
	title:'${table.className}编辑窗口',
	width:600, 	//不定义大小Window将会自适应
	maxWidth:600, 
	maxHeight:500,
	items:[{
		xtype:'form',
		layout:'column',
		frame : false, 		//True 为 Panel 填充画面//True 为 Panel 填充画面,ps:为true会多出边框
		autoShow:true,		//创建window自动展现
		fieldDefaults : {
			xtype:'textfield',
			//labelAlign:'right',
			labelWidth: 60,
			margin : '3 5 3 10',  //上右下左
			columnWidth : 1 / 3
		},
		items:[
		<#list table.columnList as column>
		<#if column.javaType=="Date">
			{
				xtype:'datefield',
				fieldLabel:'${column.comment}',
				name:'${column.camelField}',
				format: 'Y-m-d H:i:s' 
			}<#if column_has_next>,</#if>
		<#elseif column.javaType=="Integer" 
				|| column.javaType=="Short" 
				|| column.javaType=="Double" 
				|| column.javaType=="Float" 
				|| column.javaType=="BigDecimal" 
				|| column.javaType=="Long">
			{
		        xtype: 'numberfield',
		        fieldLabel: '${column.comment}',
		        name:'${column.camelField}'
		        //maxValue: 99,
        		//minValue: 0
		    }<#if column_has_next>,</#if>	
		<#else>
			<#if column.key=="PRE">
			{
		        xtype: 'textfield',
		        fieldLabel: '${column.comment}',
		        name:'${column.camelField}',
		        readOnly:true
		    }<#if column_has_next>,</#if>
			<#else>
			{
		        xtype: 'textfield',
		        fieldLabel: '${column.comment}',
		        name:'${column.camelField}'
		    }<#if column_has_next>,</#if>
		    </#if>
	    </#if>
	    </#list>	
		],
		buttonAlign:'center',
		buttons : [{
			text : '保存',
			action : 'save'
		}, {
			text : '取消',
			action : 'cancel',
			handler: function(button) {
				button.up('window').hide();
			}
		}]
		
		
	}],
	listeners:{
		hide:function(editorWin){
			editorWin.down('form').getForm().reset();
		}
	}
	
})