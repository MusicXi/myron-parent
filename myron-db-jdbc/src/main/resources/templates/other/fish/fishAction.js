/**
 * @description 数据库交互管理类
 * @author codeCreater
 */

define(function() {
return {
    /**
     * 新增记录
     * @param {} params
     * @param {} callbackFunction
     */
    addRecord : function(params, callbackFunction) {
        var config = {
            url : '${jsCodePath}/${model}/${dsTable.controllerTableName}/add.do',
            params : params,
            callback : callbackFunction
        };

        portal.doAjax(config);
    },

    /**
     * 修改记录
     * @param {} params
     * @param {} callbackFunction
     */
    modRecord : function(params, callbackFunction) {
        var config = {
            url : '${jsCodePath}/${model}/${dsTable.controllerTableName}/update.do',
            params : params,
            callback : callbackFunction
        };

        portal.doAjax(config);
    },

    /**
     * 删除记录
     * @param {} ${dsTable.idColumn.property}
     * @param {} callbackFunction
     */
    delRecord : function(${dsTable.idColumn.property}, callbackFunction) {
        var config = {
            url : '${jsCodePath}/${model}/${dsTable.controllerTableName}/delete.do',
            params : {
                '${dsTable.idColumn.property}' : ${dsTable.idColumn.property}
            },
            callback : callbackFunction
        };

        portal.doAjax(config);
    },
    
    /**
     * 查询某条记录
     * @param {} ${dsTable.idColumn.property}
     * @param {} callbackFunction
     */
    qryRecord : function(${dsTable.idColumn.property}, callbackFunction) {
        var config = {
            url : '${jsCodePath}/${model}/${dsTable.controllerTableName}/qryRecordInfo.do',
            params : {
                '${dsTable.idColumn.property}' : ${dsTable.idColumn.property}
            },
            callback : callbackFunction
        };

        portal.doAjaxQuery(config);
    },

    queryRecordByPage : function(params, callbackFunction) {
        var config = {
            url : '${jsCodePath}/${model}/${dsTable.controllerTableName}/queryRecordByPage.do',
            params : params,
            callback : callbackFunction
        };

        portal.doAjaxQuery(config);
    }
};
})