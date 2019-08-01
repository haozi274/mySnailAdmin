<!DOCTYPE html>
<html lang="en">
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
<body style="background: white">

<form class="layui-form" action="" lay-filter="component-form-element" id="myform">

    <div class="layui-fluid">
        <div class="layui-card" style="margin-bottom: 0px">
            <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">类型</label>
                        <div class="layui-input-block">
                            <input type="text" name="type" placeholder="请输入" autocomplete="off" class="layui-input">
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
<script type="text/html" id="barDemo">
    {{# if(d.description == null || d.description == "" || d.description == undefined || d.description.indexOf('系统')<0){ }}
    <a class="layui-btn layui-btn-xs" lay-event="addType">新增type</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    {{# } }}
</script>
<script type="text/html" id="zizeng">
    {{d.LAY_TABLE_INDEX+1}}
</script>

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
            , url: '/sys/dictionary/list'
            // ,toolbar: '#test-table-toolbar-toolbarDemo'
            // ,height:'400'
            , title: '数据字典表'
            , cols: [
                [
                    {type: 'checkbox', fixed: 'left'}
                    ,{type: 'numbers', title: '序号'}
                    /* , {field: 'id', title: 'ID', width: '10%', fixed: 'left', unresize: true, sort: true}*/
                    , {field: 'type', title: '类型'}
                    , {field: 'value', title: '值'}
                    , {field: 'remark', title: '描述'}
                    /*  , {field: 'operator', title: '操作人', width: '10%'}
                      , {field: 'operateTime', title: '操作时间', width: '10%', sort: true}*/
                    /* ,{field:'operateIp', title:'IP', width:120}*/
                    , {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
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
                layer.confirm('确定删除字典数据吗？', function (index) {
                    $.ajax({
                        type: "Post",
                        //  contentType: "application/json;charset=UTF-8",
                        url: "/sys/dictionary/delete_by_id" ,
                        data:{"id":data.id},
                        dataType: 'json',
                        success: function (result) {
                            layer.close(index);
                            if (result.success) {
                                parent.layer.msg("删除字典数据成功!", {time: 1000}, function () {
                                    //刷新
                                    table.reload('test-table-toolbar', t);
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
                    content: "/sys/dictionary/edit?id=" + data.id,
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

            else if (obj.event === 'addType') {
                var editIndex = layer.open({
                    title: "添加用户",
                    type: 2,
                    content: "/sys/dictionary/add?type="+data.type,
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
        var active = {
            addUser1: function () {
                var addIndex = layer.open({
                    title: "添加字典",
                    type: 2,
                    content: "/sys/dictionary/add",
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
                    layer.full(addIndex);
                });
                layer.full(addIndex);
            }
        };

        $('.layui-btn-container .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form.on("submit(searchForm)", function (data) {
            console.log(data.field)
            t.where = data.field;
            table.reload('test-table-toolbar', t);
            return false;
        });

        $("#addUser").click(function(){
          layer.open({
                title: "添加字典",
                type: 2,
                content: "/sys/dictionary/add",
                area: ['100%', '100%'],
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



