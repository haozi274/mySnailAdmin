<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>添加用户</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">


        <div class="layui-card">
            <div class="layui-card-header">填写信息</div>
            <div class="layui-card-body">
                <form class="layui-form" action="" lay-filter="component-form-element">
                    <div class="layui-row layui-col-space10 layui-form-item">

                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">任务名称：</label>
                        <div class="layui-input-block">
                            <input type="text" name="taskName" lay-verify="required" placeholder="任务名称" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">任务表达式：</label>
                        <div class="layui-input-block">
                            <input type="text" name="cron" lay-verify="required" placeholder="任务表达式" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">执行的类：</label>
                        <div class="layui-input-block">
                            <input type="text" name="className" lay-verify="required" placeholder="执行的类" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">执行方法：</label>
                        <div class="layui-input-block">
                            <input type="text" name="methodName" lay-verify="required" placeholder="执行方法" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="component-form-element">立即提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <script src="/static/layuiadmin/layui/layui.js"></script>
    <script>
        layui.config({
            base: '/static/layuiadmin/' //静态资源所在路径
        }).extend({
            index: 'lib/index' //主入口模块
        }).use(['index', 'form'], function () {
            var $ = layui.$
                , admin = layui.admin
                , element = layui.element
                , form = layui.form;

            form.render(null, 'component-form-element');
            element.render('breadcrumb', 'breadcrumb');

            form.on('submit(component-form-element)', function (data) {
                var loadIndex = layer.load(2, {
                    shade: [0.3, '#333']
                });
                $.ajax({
                    type: "POST",
                    contentType: "application/json;charset=UTF-8",
                    url: "/sys/task/add",
                    data: JSON.stringify(data.field),
                    dataType: 'json',
                    success: function (result) {
                        layer.close(loadIndex);
                        if (result.success) {
                            parent.layer.msg("添加任务成功!", {time: 1000}, function () {
                                //刷新父页面
                                parent.location.reload();
                            });
                        } else {
                            layer.msg(result.message);
                        }
                    }
                });

                return false;
            });
            /***
             * 开关事件
             */
            form.on('switch(status)', function (data) {
                console.log(data.elem.checked); //开关是否开启，true或者false
                var status = data.elem.checked ? '1' : 0;
                $("#status").val(status);
            });
        });
    </script>
</body>
</html>