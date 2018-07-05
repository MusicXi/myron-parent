Ext.define('${table.className}Manager.store.${table.className}', {
	extend: 'Ext.data.Store',
	model:'${table.className}Manager.model.${table.className}',//store的配置model必须要是类完整的名称,否则store对象无法引用到
	autoLoad:true,//自动载入
	//remoteFilter:true, 
	remoteSort:true,
	pageSize:15,	//设置分页大小
	proxy: {
		type: 'ajax',
		actionMethods:{
			read: "POST"  //修改read提交方式可以解决,提交的中文乱码问题,通过过滤器
		},
		api:{
			read:webRoot+'${table.jspContextPath}/findListByPage.do',
			create:webRoot+'${table.jspContextPath}/create${table.className}.do',
			update:webRoot+'${table.jspContextPath}/update${table.className}.do',
			destroy:webRoot+'${table.jspContextPath}/delete${table.className}.do'
		},
		reader:{//用来对服务器端响应数据进行解码，或从客户端读取数据
			type:'json',
			root:'records',
			totalProperty:'total',  //Defaults to: "total" 检索数据集中记录总数的属性名称. 只有在所有的数据集没有一次得到，而是由服务端分页得到时，该属性才需要用。
			successProperty: 'success'//Defaults to: "success" 检索'success'标识的属性名称，该标识属性的值标示指定请求是否成功
		},
		writer:{
			type:'json',
			allowSingle:false //设为false表示确定记录集要被组装成数组，即使发送的记录只有一条
		}
	}
});	