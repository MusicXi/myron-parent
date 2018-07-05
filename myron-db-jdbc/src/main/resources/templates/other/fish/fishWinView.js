define([
    'text!modules/${model}/templates/${dsTable.methodTableName}WinView.html',
    'modules/${model}/action/${dsTable.methodTableName}Action',
    'i18n!modules/common/i18n/common',
    'i18n!modules/${model}/i18n/${dsTable.propertyTableName}'
], function(WinViewTpl, Action,COMMON_I18N,I18N) {
    return fish.View.extend({
        template: fish.compile(WinViewTpl),
        // 提供模板数据
        // 如果想支持多语言的话，view中的元素要使用变量，变量对应的值写到i18n文件中
        serialize: function() {
            return fish.extend(I18N, COMMON_I18N);
        },
        //事件绑定
        //事件对象的书写格式为 {"event selector": "callback"}。 
        //省略 selector(选择器,规则可参考Jquery的选择器) 则事件被绑定到视图的根元素（this.el）
        events: {
            "click .commitbtn": 'commitHandler',
            "click .resetbtn": 'resetHandler'
        },
        initialize: function() {
           
        },

        //这里用来初始化页面上要用到的fish组件
        afterRender: function() {
            me = this;
            me.$form = me.$('.detailform').form({
                validate: 1   // 0:不校验;1:初始化form的时候检验;2:提交的时候再检验; 默认为2
            });
			me.pkFiledId = me.options.pkFiledId;
			if(me.options.model){
                me.$form.form('value', me.options.model);
            }
        },
        commitHandler:function(){
            var me = this;
            var valid = this.$form.isValid();
            if(!valid){
                return;
            }
            //获取表单数据
            var record = this.$form.form('value');
			record["${dsTable.idColumn.property}"] = me.pkFiledId;
            if(me.pkFiledId){
                Action.modRecord(record,function(result){
                    me.popup.close(result);
                });
            }else{
                Action.addRecord(record,function(result){
                    me.popup.close(result);
                });
            }
        },
        resetHandler:function(){
            this.$form.validator("cleanUp");
        }
    });
});
