Ext.define('${table.className}Manager.model.${table.className}', {
	extend:'Ext.data.Model',
	<#list table.columnList as column>	
	<#if column.key=="PRI">
	idProperty:'${column.camelField}',
	<#break>
    </#if>	      
	</#list>
	fields:[
	        <#list table.columnList as column>	        
	        <#if column.javaType=="Date">
	        {name:'${column.camelField}',type:'date',convert:function(value){<#if column.comment!="">//${column.comment}</#if>
	        	if(value!=""){
		        	return  Ext.Date.format(new Date(value),"Y-m-d H:i:s")}	        	
	        	}
	        }<#if column_has_next>,</#if>
	        <#else>
	        {name:'${column.camelField}'}<#if column_has_next>,</#if><#if column.comment!="">//${column.comment}</#if>
	        </#if>	      
	        </#list>
	]
})