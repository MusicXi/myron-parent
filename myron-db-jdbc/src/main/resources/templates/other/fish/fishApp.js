define(function() {
    var isRunning = false;
    return {
        run: function() {
            //这里配置一下语言，可以动态设置，真实的系统需要根据浏览器设置来获取语言然后设置给fish
            //注意：此处必须使用setLanguage方法来设置语言，而不能使用fish.language = "zh"方式直接设置
            //使用setLanguage方法来设置语言时会触发"languageChanged.fish"事件，从而通知fish改变语言，使用=直接设值的方式只是改变了fish的一个变量，但是fish本身并不知道语言改变了
            fish.setLanguage("zh");
            if (!isRunning) {
                isRunning = true;
               require(['modules/${model}/views/${dsTable.methodTableName}MainView'], function(MainView) {
                    new MainView({
                        el: 'body'
                    }).render();
                });
            }
        }
    }
});