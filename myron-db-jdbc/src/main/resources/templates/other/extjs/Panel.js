/**
 * @description 对${dsTable.methodTableName}的表格展现及增删改，基于Panel，持有Grid及工具栏，按钮工具栏再关联弹出窗口
 * @author codeCreater
 * @date 2014年11月19日
 */
<#if jsCodePathSplitByPoint ==""> 
	<#if model ==""> 
Ext.define('component.view.${dsTable.methodTableName}Panel', {
	<#else>
Ext.define('component.${model}.view.${dsTable.methodTableName}Panel', {
	</#if>
<#else> 
	<#if model ==""> 
Ext.define('component.${jsCodePathSplitByPoint}.view.${dsTable.methodTableName}Panel', {
	<#else>
Ext.define('component.${jsCodePathSplitByPoint}.${model}.view.${dsTable.methodTableName}Panel', {
	</#if>
</#if> 
    extend : 'Ext.panel.Panel',
    layout : "border",
<#if jsCodePathSplitByPoint ==""> 
	<#if model ==""> 
    requires : ['component.model.${dsTable.methodTableName}Model', 'ZTEsoft.button.AddButton', 'ZTEsoft.button.EditButton', 'ZTEsoft.button.DelButton'],
	<#else>
    requires : ['component.${model}.model.${dsTable.methodTableName}Model', 'ZTEsoft.button.AddButton', 'ZTEsoft.button.EditButton', 'ZTEsoft.button.DelButton'],
	</#if>
<#else> 
	<#if model ==""> 
    requires : ['component.${jsCodePathSplitByPoint}.model.${dsTable.methodTableName}Model', 'ZTEsoft.button.AddButton', 'ZTEsoft.button.EditButton', 'ZTEsoft.button.DelButton'],
	<#else>
    requires : ['component.${jsCodePathSplitByPoint}.${model}.model.${dsTable.methodTableName}Model', 'ZTEsoft.button.AddButton', 'ZTEsoft.button.EditButton', 'ZTEsoft.button.DelButton'],
	</#if>
</#if> 

    config : {
        busizGrid : null,
<#if jsCodePathSplitByPoint ==""> 
    <#if model ==""> 
        action : Ext.create("component.action.${dsTable.methodTableName}Action")
	<#else>
        action : Ext.create("component.${model}.action.${dsTable.methodTableName}Action")
	</#if>
<#else> 
	<#if model ==""> 
        action : Ext.create("component.${jsCodePathSplitByPoint}.action.${dsTable.methodTableName}Action")
	<#else>
        action : Ext.create("component.${jsCodePathSplitByPoint}.${model}.action.${dsTable.methodTableName}Action")
	</#if>
</#if> 

    },
    constructor : function(config) {
        var me = this;
        config = config || {};

        // TODO 设置额外的参数
        // me.pkFiledId = config.pkFiledId;

        // 创建顶部工具栏
        me.toolsBar = me.createTbar();

        me.tbar = me.toolsBar;

        // 创建业务相关的组件
        me.busizGrid = me.createGridPanel();
        me.items = [me.busizGrid];

        this.callParent([config]);
        me.bindEvent();
    },

    initComponent : function() {
        var me = this;
        // 载入的时候默认选中第一条；如果没有任何记录，则处理按钮灰化
        me.busizGrid.getStore().on("load", function(thiz, records) {
            if (Ext.isEmpty(records)) {
                var items = me.busizGrid.getSelectedItems();
                me.busizGrid.getSelectionModel().fireEvent('selectionchange', null, items);
            } else {
                me.busizGrid.select(0);
            }
        });
        var toolsBar = me.toolsBar;
        me.busizGrid.getSelectionModel().on('selectionchange', function(sm, items) {
            // TODO 设置工具栏按钮的状态
            if (Ext.isEmpty(items)) {
                toolsBar.down("zteeditbutton[itemId=btnEdit]").disable();
                toolsBar.down("ztedelbutton[itemId=btnDel]").disable();
                return;
            }

            toolsBar.down("zteeditbutton[itemId=btnEdit]").enable();
            toolsBar.down("ztedelbutton[itemId=btnDel]").enable();

        });

        me.busizGrid.getStore().getProxy().extraParams = {
        // TODO 自定义业务条件：带业务条件查询

        };
        
        this.callParent();
    },

    // 创建数据源
    createStore : function() {
<#if jsCodePathSplitByPoint ==""> 
    <#if model ==""> 
    	return Ext.create('component.store.${dsTable.methodTableName}Store', {
	<#else>
    	return Ext.create('component.${model}.store.${dsTable.methodTableName}Store', {
	</#if>
<#else> 
	<#if model ==""> 
    	return Ext.create('component.${jsCodePathSplitByPoint}.store.${dsTable.methodTableName}Store', {
	<#else>
    	return Ext.create('component.${jsCodePathSplitByPoint}.${model}.store.${dsTable.methodTableName}Store', {
	</#if>
</#if> 

        		autoLoad : true,
            // 定义分页大小
            pageSize : WEBConstants.DEFAULT_PAGE_SIZE
        });
    },

    // 创建表格
    createGridPanel : function() {
        var me = this;
        var grid = Ext.create('ZTEsoft.grid.Panel', {
            border : false,
            region : "center",
            isPage : true,
            store : me.createStore(),
            columns : [
			{
	            hidden : true,
	            dataIndex : "${dsTable.idColumn.property}"
        	}
		<#list dsTable.dsColumnExIdList as column>
	        ,{
	            text : "${column.comment}",
	            dataIndex : "${column.property}",
	            flex : 1
	        }     
		</#list>         
		]

        });
        return grid;
    },

    // 创建顶部工具栏：新增、修改、删除...
    createTbar : function() {
        var me = this;
        var tbar = Ext.create('Ext.toolbar.Toolbar', {
            items : ['->',{
                itemId : 'btnAdd',
                xtype : "zteaddbutton"
            }, '-', {
                itemId : 'btnEdit',
                xtype : "zteeditbutton"
            }, '-', {
                itemId : 'btnDel',
                xtype : "ztedelbutton"
            }, '-']
        });
        return tbar;
    },
    // 给按钮绑定事件
    bindEvent : function() {
        var me = this;
        if (me.down('[xtype=zteaddbutton]')) {
            me.down('[xtype=zteaddbutton]').on('click', Ext.bind(me.addBtnHandler, me));
        }
        if (me.down('[xtype=zteeditbutton]')) {
            me.down('[xtype=zteeditbutton]').on('click', Ext.bind(me.editBtnHandler, me));
        }
        if (me.down('[xtype=ztedelbutton]')) {
            me.down('[xtype=ztedelbutton]').on('click', Ext.bind(me.delBtnHandler, me));
        }
    },
    // 新增 按钮的事件
    addBtnHandler : function() {
        var me = this;
<#if jsCodePathSplitByPoint ==""> 
    <#if model ==""> 
        var win = Ext.create('component.view.${dsTable.methodTableName}Win', {
	<#else>
        var win = Ext.create('component.${model}.view.${dsTable.methodTableName}Win', {
	</#if>
<#else> 
	<#if model ==""> 
        var win = Ext.create('component.${jsCodePathSplitByPoint}.view.${dsTable.methodTableName}Win', {
	<#else>
        var win = Ext.create('component.${jsCodePathSplitByPoint}.${model}.view.${dsTable.methodTableName}Win', {
	</#if>
</#if>

            winType : WEBConstants.ACTIONTYPE.NEW,
            callback : function(result) {
                ExtUtils.info(StrConstants.HINT_ADD_SUCCESS);
<#if jsCodePathSplitByPoint ==""> 
    <#if model ==""> 
                var model = Ext.create("component.model.${dsTable.methodTableName}Model");
	<#else>
                var model = Ext.create("component.${model}.model.${dsTable.methodTableName}Model");
	</#if>
<#else> 
	<#if model ==""> 
                var model = Ext.create("component.${jsCodePathSplitByPoint}.model.${dsTable.methodTableName}Model");
	<#else>
                var model = Ext.create("component.${jsCodePathSplitByPoint}.${model}.model.${dsTable.methodTableName}Model");
	</#if>
</#if>

                model.data = result;
                me.busizGrid.getStore().add(model);
                me.busizGrid.select(model);
            }
        });
        win.show();
    },
    // 修改按钮的事件
    editBtnHandler : function() {
        var me = this;
        var items = me.busizGrid.getSelectedItems();
        if (Ext.isEmpty(items)) {
            ExtUtils.info(StrConstants.HINT_SELECT_FIRST);
            return;
        }
        var item = me.busizGrid.getSelectedItem();
        var pkFiledId = item.get("${dsTable.idColumn.property}");
<#if jsCodePathSplitByPoint ==""> 
    <#if model ==""> 
        var win = Ext.create('component.view.${dsTable.methodTableName}Win', {
	<#else>
        var win = Ext.create('component.${model}.view.${dsTable.methodTableName}Win', {
	</#if>
<#else> 
	<#if model ==""> 
        var win = Ext.create('component.${jsCodePathSplitByPoint}.view.${dsTable.methodTableName}Win', {
	<#else>
        var win = Ext.create('component.${jsCodePathSplitByPoint}.${model}.view.${dsTable.methodTableName}Win', {
	</#if>
</#if>
            pkFiledId : pkFiledId,
            winType : WEBConstants.ACTIONTYPE.EDIT,
            callback : function(result) {
                ExtUtils.info(StrConstants.HINT_MOD_SUCCESS);

                // TODO 设置要更新的字段
                var changedColumns = [${updateStr}];
                for (var colKey in changedColumns) {
                    var colValue = changedColumns[colKey];
                    item.set(colValue, result[colValue]);
                }
                // TODO 如果还有其他字段要更新（比如从其他地方获取到的值）
                // item.set("XXXX", others.XXXX);;

                me.busizGrid.getStore().commitChanges();
                var items = me.busizGrid.getSelectedItems();
                me.busizGrid.getSelectionModel().fireEvent('selectionchange', me.busizGrid.getSelectionModel(), items);
            }
        });
        win.show();
    },
    // 删除按钮的事件
    delBtnHandler : function() {
        var me = this;
        var items = me.busizGrid.getSelectedItems();
        if (Ext.isEmpty(items)) {
            ExtUtils.info(StrConstants.HINT_SELECT_FIRST);
            return;
        }
        Ext.MessageBox.confirm(StrConstants.HINT, StrConstants.HINT_DEL_CONFIRM, function(btn) {
            if (btn == 'yes') {
                var item = items[0];
                var pkFiledId = item.get("${dsTable.idColumn.property}");
                me.getAction().delRecord(pkFiledId, function(result) {
                    ExtUtils.info(StrConstants.HINT_DEL_SUCCESS);
                    me.busizGrid.removeItemToNext(item);
                });
            }
        });
    }

});
