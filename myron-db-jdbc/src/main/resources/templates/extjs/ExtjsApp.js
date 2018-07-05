Ext.application({
	name: '${table.className}Manager',  //必须属性,定义其他应用例如Models、View和Controllers的使用。UserManager.controller.UserManager
	controllers:['${table.className}Controller'],
	launch: function() {
		Ext.create('Ext.container.Viewport', {
			layout: 'border',
			items:[
				/*{xtype: 'west', region: 'west'},*/
				{xtype: '${table.className?uncap_first}Panel',  region: 'center'}		  
			]		
			
		});
	}

});