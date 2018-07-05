Ext.define("${table.className}Manager.controller.${table.className}Controller",{
	extend:"Ext.app.Controller",
	models:['${table.className}'], 
	stores:['${table.className}'],
	views:['${table.className}Panel','${table.className}Win','ImportWin'], //每一个控制器需要列举它使用的Views并且这些Views会自动被加载
	refs: [{
		ref		  	: '${table.className?uncap_first}GridPanel',
	   	selector  	: '${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel]'
	}],
	init:function(){
		this.control({
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=query]' :{//查询
				click:this.query${table.className}
			},		
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=add]' :{//添加
				click:this.add${table.className}
			},			
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=copy]' :{//复制
				click:this.copy${table.className}
			},
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=delete]' :{//删除
				click:this.delete${table.className}
			},
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=refresh]' :{//刷新
				click:this.refresh${table.className}
			},
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=exportExcel]' :{//导出Excel
				click:this.exportExcel
			},
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel] button[action=importExcel]':{ //导入Excel
				click:this.importExcel
			},
			'${table.className?uncap_first}Panel gridpanel[name=${table.className?uncap_first}GridPanel]' :{ //编辑
				itemdblclick:this.edit${table.className}
			}	
		})
	},
	
	add${table.className}: function(button){		
		var me = this;
		var win = me.get${table.className}Win("${table.className}添加");     //创建组件
		var formPanel = win.down('form');	
		//formPanel.form.findField("id").setDisabled(true);
		//formPanel.form.findField("id").setReadOnly(true);
		formPanel.down('button[action=save]').setHandler(function(){
			var record = Ext.create('${table.className}Manager.model.${table.className}');
			formPanel.getForm().updateRecord(record); //传入的Ext.data.Model对象内保存表单写入的值
			console.log(record.data);
			var store = me.get${table.className}Store();	 
			store.add(record);					 //添加 Model 实例到 Store
			store.sync({
				success: function(batch, options){
					var data = batch.proxy.reader.jsonData;
					Ext.Msg.alert('提示',data.msg || '添加成功!');
					win.hide();
				},
				failure: function(batch, options){			
					var data = batch.proxy.reader.jsonData;
					Ext.Msg.alert('提示',data.msg || '操作失败');
					store.reload();
					win.hide();
				},
				callback: function(batch, options){
					
				}
			});						 
		
		});

		win.show();
	},
	
	copy${table.className}: function(button){
		var me = this;
		
		var records = button.up('grid').getSelectionModel().getSelection();
		if (records.length != 1) {
			Ext.Msg.alert('提示','请选择一条记录');
			return;
		}
		
		records[0].data.id = '';						//复制选中记录除ID以外的数据,
		var model = Ext.create('${table.className}Manager.model.${table.className}',records[0].data);
		var win = me.get${table.className}Win("${table.className}复制"); 
		var formPanel = win.down('form');	
		
		formPanel.loadRecord(model);					//载入一个 Ext.data.Model 到表单中,用于保存表单数据
		formPanel.down('button[action=save]').setHandler(function(){
			
			var record = formPanel.getRecord();		 	//返回当前通过 loadRecord 加载的 Ext.data.Model 实例.
			formPanel.getForm().updateRecord(record);   //传入的Ext.data.Model对象内保存表单写入的值
			console.log(record.data);
			var store = me.get${table.className}Store();	 
			store.add(record);					 		//添加 Model 实例到 Store
			store.sync({//后台实现数据库同步
				success: function(batch, options){
					var data = batch.proxy.reader.jsonData;
					Ext.Msg.alert('提示',data.msg || '复制成功！');
					store.reload();
					win.hide();
				},
				failure: function(batch, options){			
					var data =batch.proxy.reader.jsonData;
					Ext.Msg.alert('提示',data.msg || '操作失败');
					store.reload();
					win.hide();
				},
				callback: function(batch, options){
					
				}
			});						 
			
		});
		
		win.show();
	},
	
	edit${table.className}: function(view, record, item, index, e, obj){
		var me = this;
		var win = me.get${table.className}Win("${table.className}修改"); 
		var formPanel = win.down('form');
		
		formPanel.loadRecord(record);		//传入选中records	
		formPanel.down('button[action=save]').setHandler(function() {
			if (! formPanel.getForm().isValid()) {
				Ext.Msg.alert('提示','表单验证不通过!');
				return;								 
			}
			
			var record = formPanel.getRecord();				 //返回当前通过 loadRecord 加载的 Ext.data.Model 实例.
			formPanel.getForm().updateRecord(record);		 //传入的Ext.data.Model对象内保存表单写入的值
			console.log(record.data);			 			 //测试数据是否更新
			var store = me.get${table.className}Store();	 //var store=Ext.getCmp('listID').getStore();
			store.sync({//后台实现数据库同步
				success: function(batch, options){
					var data = batch.proxy.reader.jsonData;
					Ext.Msg.alert('提示',data.msg || '修改成功!');
					win.hide();
				},
				failure: function(batch, options){			
					var data = batch.proxy.reader.jsonData;
					Ext.Msg.alert('提示',data.msg || '操作失败');
					store.reload();
					win.hide();
				},
				callback: function(batch, options){
					
				}
			});	
		});
	
		win.show();
	},
	
	/**
	 * 查询用户
	 */
	query${table.className}: function(button){
		var me = this;
		var win = me.get${table.className}Win("${table.className}查询");
		var formPanel = win.down('form');	
		formPanel.down('button[action=save]').setHandler(function(){
			var values = formPanel.getForm().getValues();
			var store = me.get${table.className}Store();	
			store.load({
				params: { "filter": JSON.stringify(values)} 
			});
	
		});
		win.show();
	},

	
	delete${table.className}: function(btn){
		var me = this;
		var records = me.get${table.className}GridPanel().getSelectionModel().getSelection();
		
		if (records.length == 0) {
			Ext.Msg.show({
				title:'提示',
				msg:"请选择删除对象",
				icon:Ext.MessageBox.WARNING,
				buttons:Ext.MessageBox.OK
			});
		}
		
		Ext.MessageBox.confirm('提示','是否删除选中的 '+records.length+' 条数据', function(button){
			if (button == 'yes') {
				var store = me.get${table.className}Store();
				store.remove(records);
				store.sync({
					success: function(batch, options){
						var data = batch.proxy.reader.jsonData;
						Ext.Msg.alert('提示','成功删除'+data.count+'条记录');
						var currentPage = store.currentPage; // 当前页码  
						if (store.count() == 0 && currentPage != 1) {
				            store.currentPage = currentPage - 1;
							me.get${table.className}GridPanel().down('pagingtoolbar').doRefresh();
							return;
						}
						store.reload();

					}
				});
			}
		});
			
		
	},
	
	refresh${table.className}: function(){
		var me = this;
		var store = me.get${table.className}Store();	
		store.reload();
	},
	/**
	 * 导出excel
	 */
	exportExcel: function(){
		//导出全部
    	var form = Ext.create('Ext.form.Panel', {
			standardSubmit : true,
			url :webRoot+'${table.jspContextPath}/exportExcel.do'
		});
    	var params = {};
    	form.submit({
			params : params
		});
	},
	
	/**
	 * 导入excel
	 */
	importExcel: function(){
		var me = this;
		var win = Ext.getCmp('importWinID');
		if (! win) {
			win = Ext.widget('importWin');
		}
		var formPanel = win.down('form');
		
		//下载模板
		formPanel.down('button[action=download]').setHandler(function(){				
			var fields = me.get${table.className}Win('${table.className}修改').down('form').getForm().getFields();
			
			var obj = {};
			fields.each(function(field){
				//console.log(field);
				obj[field.name]=field.fieldLabel;
			})
			var json = JSON.stringify(obj);  //var json=Ext.encode(obj);会有中文乱码问题
			
			var form = Ext.create('Ext.form.Panel', {
				standardSubmit : true,
				url :webRoot+'${table.jspContextPath}/downloadTemplate.do'
			});
	    	var params = {'model':json};
	    	form.submit({
				params : params
			});
	    	
		})
		
		//执行导入
		formPanel.down('button[action=import]').setHandler(function(){
			if (! formPanel.getForm().isValid()) {
				Ext.Msg.alert('提示','表单校验未通过');
				return;
			}
			//提交上传文件
			formPanel.submit({
				waitMsg: '正在导入数据，请稍候...',
				url: webRoot+'${table.jspContextPath}/importExcel.do',
				success: function(form, action){
					Ext.Msg.alert('提示',  '导入成功');
					var data = action.result;
					var store = me.get${table.className}Store();
					store.load();
					win.close();				
				},
				failure: function(form, action) {
					var data=action.result;
					Ext.Msg.alert('提示',  '导入失败');

				} 
			})
			
		});
		win.show();
	},
	
	/**
	 * 获取资源窗口
	 */
	get${table.className}Win: function(title){
		if (title == null || title == '') {
			title ='窗口标题未定义';
		}
		var win = Ext.getCmp('${table.className?uncap_first}WinID');
		
     	if (! win) {
     		win = Ext.widget("${table.className?uncap_first}Win", {title:title});     //创建组件
     	} else {
     		win.setTitle(title);
     	}
     	return win;
	}

})