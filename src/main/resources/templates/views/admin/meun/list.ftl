<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>开启头部工具栏 - 数据表格</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/assets/layui/css/layui.css">
    <link rel="stylesheet" href="/static/layuiadmin/assets/common.css"/>
</head>
<body style="background: white">
<fieldset class="layui-elem-field">
    <legend>菜单信息检索</legend>
    <div class="layui-field-box">
        <#--    <div class="layui-inline">
                <input type="text" value="" name="s_type" placeholder="请输入类型" class="layui-input search_input">
            </div>
            <div class="layui-inline">
                <input type="text" value="" name="s_label" placeholder="请输入标签名" class="layui-input search_input">
            </div>-->
        <div class="layui-inline">
            <button class="layui-btn" style="height: 40px;width: 80px" id="btn-expand">全部展开</button>
        </div>
        <div class="layui-inline">
            <button class="layui-btn" style="height: 40px;width: 80px" id="btn-fold">全部折叠</button>
        </div>
        <div class="layui-inline">
            <button class="layui-btn" style="height: 40px;width: 120px" id="addMeun"><i class="layui-icon">&#xe608;</i>添加根节点
            </button>
        </div>

    </div>
</fieldset>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <#--  <div class="layui-btn-group">
                      <button class="layui-btn" id="btn-expand">全部展开</button>
                      <button class="layui-btn" id="btn-fold">全部折叠</button>
                      <button class="layui-btn" id="btn-refresh">刷新表格</button>
                  </div>-->
                <div class="layui-btn-container" style="margin: 0 auto">
                    <br>

                </div>
                <div class="layui-row layui-col-space10 layui-form-item">

                    <div class="layui-card-body">
                        <table id="table1" class="layui-table" lay-filter="table1"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 操作列 -->
    <script type="text/html" id="barDemo">
        {{# if(d.description == null || d.description == "" || d.description == undefined || d.description.indexOf('系统')<0){ }}
        <a class="layui-btn layui-btn-xs" lay-event="add">添加</a>
       <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        {{# } }}
    </script>

    <script src="/static/layuiadmin/assets/layui/layui.js"></script>
    <script>
        layui.config({
            base: '/static/layuiadmin/treetable-lay/module/'
        }).extend({
            treetable: 'treetable-lay/treetable'
        }).use(['table', 'form', 'element', 'treetable'], function () {
            var $ = layui.jquery;
            var table = layui.table;
            var form = layui.form;
            var element = layui.element;
            var treetable = layui.treetable;

            // 渲染表格
            var renderTable = function () {
                layer.load(2);
                treetable.render({
                    treeColIndex: 1,
                    treeSpid: -1,
                    treeIdName: 'id',
                    treePidName: 'parentId',
                    elem: '#table1',
                    url: '/sys/meun/treeList',
                    page: false,
                    cols: [[
                        {type: 'numbers', title: '序号'},
                        {field: 'name', title: '菜单名称'},
                        {field: 'permission', title: '权限名称'},
                        {field: 'href', title: '链接地址'},
                        {field: 'icon', title: '图标',templet:function (d) {
                                return '<i class="layui-icon ">'+d.icon+'<\i>';
                            }},
                        {field: 'sort', title: '排序'},
                        {templet: '#barDemo', title: '操作',width:'22%'}
                    ]],
                    done: function (a,b,c) {
                        console.log('1111111111111111111111',a);
                        layer.closeAll('loading');
                    },
                    text: {
                        none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
                    }
                });
            };

            renderTable();


            $('#btn-expand').click(function () {
                treetable.expandAll('#table1');
            });

            $('#btn-fold').click(function () {
                treetable.foldAll('#table1');
            });

            $('#btn-refresh').click(function () {
                renderTable();
            });
            /***
             * 添加
             */
            var active;
            $("#addMeun").click(function () {
                console.log('11111111111');
                layer.open({
                    title: "添加用户",
                    type: 2,
                    content: "/sys/meun/add",
                    area: ['100%', '100%'],
                    success: function (layero, addIndex) {
                        setTimeout(function () {
                            layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        }, 500);
                    }
                });
            });

            //监听行工具事件
            table.on('tool(table1)', function (obj) {
                var data = obj.data;
                console.log(data.id);
                if (obj.event === 'del') {
                    layer.confirm('你确定真的要删除吗?这会使得该菜单下的所有子菜单都将删除', function (index) {
                        $.ajax({
                            type: "Post",
                            url: "/sys/meun/delete" ,
                            data:{"id":data.id},
                            dataType: 'json',
                            success: function (result) {
                                layer.close(index);
                                if (result.success) {
                                    parent.layer.msg("菜单删除成功!", {time: 1000}, function () {
                                        //刷新
                                        renderTable();
                                    });
                                } else {
                                    layer.msg(result.message);
                                }
                            }
                        });
                    });
                } else if (obj.event === 'edit') {
                    var editIndex = layer.open({
                        title: "编辑菜单",
                        type: 2,
                        content: "/sys/meun/edit?id=" + data.id,
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
                else if (obj.event === 'add') {
                    var editIndex = layer.open({
                        title: "添加用户",
                        type: 2,
                        area: ['100%', '100%'],
                        content: "/sys/meun/add?parent_id=" + data.id+"&level="+data.level+"&parentName="+data.name,
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

        });
    </script>
</body>
</html>