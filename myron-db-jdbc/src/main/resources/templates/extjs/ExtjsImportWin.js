Ext.define('${table.className}Manager.view.ImportWin',{
	extend:'Ext.window.Window',
	alias:'widget.importWin',
	id:'importWinID',
	closeAction:'hide',
	modal:true,
	title:'导入Excel',
	initComponent : function() {
		var me=this;
		//Vtype自定义增加校验类型
		Ext.apply(Ext.form.field.VTypes, {
		    excel: function(val, field) {//  vtype 校验函数
		    	var fileType = val.substring(val.lastIndexOf('.'));
				if (fileType != '.xlsx' && fileType != '.xls') {
					//Ext.Msg.alert('提示：', '导入文件格式错误，请重新选择！');
					return false;
				}
		        return true;
		    },
		    excelText: '请导入.xlsx或.xls格式文件'    // vtype文本属性：当验证函数返回false显示的出错文本
		   
		    //excelMask: /[\d\s:amp]/i     // vtype Mask 属性: 按键过滤器
		});
		
		
		this.callParent([this]);
	},
	items:[{
		xtype : 'displayfield',
		value : '<div style="color:#000000;">温馨提示<br/></div><div style="text-align:left;">1、请用户下载统一的台账导入模板(见系统界面)。<br/>2、请严格按照模板提示进行必填项、日期、数字等填写。<br/>3、导入成功失败日志可进行下载查看。</div>'
	},{
		xtype:'form',
		layout:'column',
		frame : false, 		//True 为 Panel 填充画面
		border:false,
		fieldDefaults : {
		
			labelWidth: 60,
			margin : '3 5 3 10'  //上右下左
		},
		items:[{
    	    xtype: 'filefield',
    	    name : 'excelFile',
    	    fieldLabel: '上传文件',
    	    buttonText : '打开',
    	    //action : 'import',
    	    allowBlank:false,	//是否允许为空
			blankText:'请选择上传Excel文件',
    	    vtype:'excel'
		}],
		buttonAlign:'center',
		buttons : [{
			text : '导入',
			action : 'import'
		}, {
			text : '下载模板',
			action : 'download'
		},{
			text : '取消',
			action : 'cancel',
			handler: function(button) {
				button.up('window').hide();
			}
		}]
	
	
	}]
});