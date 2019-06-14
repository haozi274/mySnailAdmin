<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>开启头部工具栏 - 数据表格</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>
<body>
<form class="layui-form" action="" lay-filter="component-form-element" id="myform">

    <div class="layui-fluid">
        <div class="layui-card" style="margin-bottom: 0px">
            <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">执行</label>
                        <div class="layui-input-block">
                            <input type="text" name="operation" placeholder="请输入" autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>

                    <div class="layui-inline">
                        <label class="layui-form-label">操作人</label>
                        <div class="layui-input-block">
                            <input type="text" name="operator" placeholder="请输入" autocomplete="off" class="layui-input">
                        </div>
                    </div>

                    <button class="layui-btn layuiadmin-btn-admin" lay-submit lay-filter="searchForm">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="layui-card-body" style="background: white">
            <div style="padding-bottom: 10px;">
                <button class="layui-btn layui-btn-primary">重置</button>
                <a class="layui-btn layuiadmin-btn-admin" id="addUser" data-type="addUser1">添加</a>
            </div>
            <table id="test-table-toolbar" lay-filter="test-table-toolbar"></table>
        </div>
    </div>
    </div>

</form>


<script src="/static/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '/static/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table'], function () {
        var admin = layui.admin
            , $ = layui.jquery
            , form = layui.form,

            table = layui.table
            , t;
        t = {
            method: 'post',
            contentType: 'application/json',
            elem: '#test-table-toolbar'
            , url: '/sys/task/list'
            // ,toolbar: '#test-table-toolbar-toolbarDemo'
            , height: '400'
            , title: '用户数据表'
            , cols: [
                [
                    {type: 'checkbox', fixed: 'left'}
                    , {type: 'numbers', title: '序号'}
                    , {field: 'taskName', title: '定时名称', width: '20%'}
                    , {field: 'className', title: '类名', width: '15%'}
                    , {field: 'methodName', title: '方法名称', width: '10%'}
                    , {field: 'cron', title: '执行时间', width: '20%', sort: true},
                    , {field: 'createTime', title: '创建时间', width: '20%', sort: true}
                ]
            ]
            , page: true
        }
        table.render(t);

        //头工具栏事件
        table.on('toolbar(test-table-toolbar)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var data = checkStatus.data;
                    layer.msg('选中了：' + data.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选' : '未全选');
                    break;
            }
            ;
        });

        //监听行工具事件
        table.on('tool(test-table-toolbar)', function (obj) {
            var data = obj.data;
            console.log(data.id);
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {
                    $.ajax({
                        type: "Get",
                        // contentType: "application/json;charset=UTF-8",
                        url: "/api/user/delete_by_id?id=" + data.id,
                        dataType: 'json',
                        success: function (result) {
                            layer.close(loadIndex);
                            if (result.success) {
                                parent.layer.msg("用户删除成功!", {time: 1000}, function () {
                                    //刷新父页面
                                    parent.location.reload();
                                });
                            } else {
                                layer.msg(result.message);
                            }
                        }
                    });
                });
            } else if (obj.event === 'edit') {
                var editIndex = layer.open({
                    title: "编辑用户",
                    type: 2,
                    content: "/api/user/edit?id=" + data.id,
                    success: function (layero, addIndex) {
                        setTimeout(function () {
                            layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500);
                    }
                });
                //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                $(window).resize(function () {
                    layer.full(editIndex);
                });
                layer.full(editIndex);
            }
        });


        form.on("submit(searchForm)", function (data) {
            console.log(data.field)
            t.where = data.field;
            table.reload('test-table-toolbar', t);
            return false;
        });

        $("#addUser").click(function () {
            var addIndex = layer.open({
                title: "添加字典",
                type: 2,
                area: ['100%', '100%'],
                content: "/sys/task/add",
                success: function (layero, addIndex) {
                    setTimeout(function () {
                        layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500);
                }
            });
            layer.full(addIndex);
        })


        $("#addDic").click(function () {
            layer.open({
                title: "添加用户",
                type: 2,
                area: ['100%', '100%'],
                content: "/api/user/add",
                success: function (layero, addIndex) {
                    setTimeout(function () {
                        layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500);
                }
            });
        })
    });
</script>
</body>
</html>