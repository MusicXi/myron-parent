require([
    '${dsTable.methodTableName}App.js','modules/common/public/Portal'
], function (app,Portal) {
   	fish.View.configure({manage: true});
    app.run();
});