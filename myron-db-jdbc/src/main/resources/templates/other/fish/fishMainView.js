define([
    'text!modules/${model}/templates/${dsTable.methodTableName}MainView.html',
    'modules/${model}/action/${dsTable.methodTableName}Action',
    'i18n!modules/common/i18n/common',
    'i18n!modules/${model}/i18n/${dsTable.propertyTableName}'
    ], function(MainViewTpl, Action,COMMON_I18N,I18N) {
    return fish.View.extend({
        //设置模板
        template: fish.compile(MainViewTpl),
        // 提供模板数据
        // 如果想支持多语言的话，view中的元素要使用变量，变量对应的值写到i18n文件中
        serialize: function() {
            return fish.extend(I18N, COMMON_I18N);
        },
        //事件绑定
        //事件对象的书写格式为 {"event selector": "callback"}。 
        //省略 selector(选择器,规则可参考Jquery的选择器) 则事件被绑定到视图的根元素（this.el）
        events: {
            "click .js-add": 'addUser',
            "click .js-edit": 'editUser',
            "click .js-del": 'delUser',
            "click .query-btn": 'queryUser'
        },

        initialize: function() {
            /*this.setViews({
                '.header': [new HeaderView()],  //初始化载入视图,载入到那个对应的 class 标签里
                '.footer': [new FooterView()]
            })*/
        },

        //这里用来初始化页面上要用到的fish组件
        afterRender: function() {
            var me = this;
            me.$queryform = me.$(".queryform");
            me.initGrid();
        },
        initGrid:function(){
            var me = this;
            //在视图中推荐this.$，这样只会在当前视图中搜索选择器
            //将变量缓存到this.$busizGrid中，便于后期使用
            this.$busizGrid = this.$(".detail-grid").jqGrid({
                height:540,
                width : "100%",
                forceFit:true,  //当为ture时,调整列宽度不会改变表格的宽度,默认为false
                // gridview:true,  //是否开启快速加载模式,如果定义了afterInsertRow事件,此参数则要求设置为false,默认为true
                // caption : "用户列表",  //设置grid标题
                colModel: [
                {
                    name: '${dsTable.idColumn.property}',
                    sortable: false,
                    hidden:true
                }
				<#list dsTable.dsColumnExIdList as column>
				,{
                    label : I18N.${column.column},
                    name : "${column.property}",
                    width : "15%"
                }
				</#list>
				],
                datatype: 'json',
                // rowNum: 15,
                // rowList : [15,20,25],
                pginput:false, //是否显示跳转页面的输入框
                pager: true,    //是否分页
                pageData: me.pageData.bind(me)
            });

            this.$busizGrid.grid("navButtonAdd",[{
                caption: I18N.COMMON_ADD,
                cssprop: "js-add"
            },{
                caption: I18N.COMMON_EDIT,
                cssprop: "js-edit"
            },{
                caption: I18N.COMMON_DELETE,
                cssprop: "js-del"
            }]);
            //加载grid数据
            this.pageData();
        },
        pageData: function() {
            var me = this;
            var params = portal.utils.getQueryParams(me.$busizGrid,me.$queryform);
            Action.queryRecordByPage(params,function(records){
                //可以通过jsonReader选项设置rows、page、records属性名
                me.$busizGrid.jqGrid("reloadData", {
                    'rows': records.resultList,
                    'page': me.$busizGrid.jqGrid("getGridParam", "page"),
                    'records': records.totalRecords
                });
            });
        },
        addUser: function() {
            var me = this;
            //fish.popupView弹出的窗口似乎会自己创建一个带ui-dialog样式的div，所以在弹窗页面中需去掉这个div，否则页面会显示异常
            fish.popupView({
                url: "modules/${model}/views/${dsTable.methodTableName}WinView",
                height : 270,
                width : 800,
                // 当弹窗视图调用this.popup.close方法关闭窗口时，会执行此回调函数
                // 通过弹窗右上角的关闭按钮关闭窗口时，不会执行此回调函数
                close: function(data) {
                    fish.toast('info',I18N.HINT_ADD_SUCCESS);
                    this.$busizGrid.grid("addRowData", data, "first"); //第二个位置参数默认是‘last’,可选‘first’，‘last’,‘before’，‘after’，第三个参数是参照的rowid
                }.bind(this)
            });
        },
        editUser: function() {
            var me = this;
            var record = me.$busizGrid.grid("getSelection"); 
            if(fish.isEmpty(record)){
                fish.toast('warn',I18N.HINT_SELECT_FIRST);
                return;
            }
            fish.popupView({
                url: "modules/${model}/views/${dsTable.methodTableName}WinView",
                height : 270,
                width : 800,
                // 通过viewOption向视图传递数据，在视图中可以通过this.options获取传递过来的数据
                viewOption: {
                    model : record,
					pkFiledId : record["${dsTable.idColumn.property}"]
                },
                //弹窗视图渲染完成后会执行此回调函数
                // callback: function(popup, view) {
                // }.bind(this),
                // 当弹窗视图调用this.popup.close方法关闭窗口时，会执行此回调函数
                // 通过弹窗右上角的关闭按钮关闭窗口时，不会执行此回调函数
                close: function(data) {
                    fish.toast('info',I18N.HINT_MOD_SUCCESS);
                    var selrowid = this.$busizGrid.grid("getGridParam", "selrow");
                    this.$busizGrid.grid("setRowData",selrowid,data);
                }.bind(this)
            });
        },
        delUser: function() {
            var me = this;
            var record = me.$busizGrid.grid("getSelection"); 
            if(fish.isEmpty(record)){
                fish.toast('warn',I18N.HINT_SELECT_FIRST);
                return;
            }
            fish.confirm(I18N.HINT_DEL_CONFIRM,function(){
                Action.delRecord(record["${dsTable.idColumn.property}"],function(result){
                    fish.toast('info',I18N.HINT_DEL_SUCCESS);
                    me.$busizGrid.grid("delRowData",record); 
                });
            });
        },
        queryUser: function() {
            var me = this;
            me.$busizGrid.jqGrid("setGridParam", {page:1});
            me.pageData();
        }
    });
});
