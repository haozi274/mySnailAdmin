<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>聊天记录</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>

<body>




<script src="../../../layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '../../../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'layim'], function(){
        var $ = layui.$
            ,admin = layui.admin
            ,element = layui.element
            ,router = layui.router();



    });
</script>

</body>
</html>