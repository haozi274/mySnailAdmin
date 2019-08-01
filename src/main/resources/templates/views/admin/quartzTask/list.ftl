<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>定时任务</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/assets/layui/css/layui.css">
    <link rel="stylesheet" href="/static/layuiadmin/assets/common.css"/>
</head>
<body class="childrenBody" style="background: white;">
<fieldset class="layui-elem-field">
  <legend>定时任务检索</legend>
  <div class="layui-field-box">
    <form class="layui-form" id="searchForm">
    <div class="layui-inline" style="margin-left: 15px">
            <label>任务名称:</label>
                <div class="layui-input-inline">
                <input type="text" value="" name="s_name" placeholder="请输入任务名称" class="layui-input search_input">
                </div>
    </div>
    <div class="layui-inline" style="margin-left: 15px">
            <label>任务状态:</label>
                <div class="layui-input-inline">
                <select name="s_status">
                    <option value="" selected="">请选择任务状态</option>
                 <#--   <@my type="quartz_task_status">
                    <#list result as r>
                    <option value="${r.value}" >${r.label}</option>
                    </#list>
                    </@my>-->
                        <option value="0">正常</option>
                        <option value="1" >暂停</option>
                </select>
                </div>
    </div>
        <div class="layui-inline">
            <a class="layui-btn" lay-submit="" lay-filter="searchForm">查询</a>
        </div>
        <div class="layui-inline" >
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-normal" data-type="addQuartzTask">添加定时任务</a>
        </div>
    </form>
  </div>
</fieldset>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">

                <div class="layui-btn-container" style="margin: 0 auto">
                    <br>

                </div>
                <div class="layui-row layui-col-space10 layui-form-item">

                    <div class="layui-card-body">
                        <table class="layui-table" id="test" lay-filter="demo"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/html" id="status">
    {{#  if(d.status == 0){ }}
    <span  class="layui-badge layui-bg-blue"> 正常</span>
    {{# } }}
    {{#  if(d.status == 1){ }}

    <span  class="layui-badge layui-bg-badge"> 暂停</span>
    {{# } }}
</script>
<script type="text/html" id="barDemo">
    {{#  if(d.status == 0){ }}
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="paush">暂停</a>
    {{# }else{ }}
    <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="resume">恢复</a>
    {{# } }}
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="run">立即执行</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>

</script>

<script src="/static/layuiadmin/assets/layui/layui.js"></script>
<script src="/static/layuiadmin/js/tools.js"></script>

<script>
    layui.use(['layer','form','table','laydate'], function() {
        var layer = layui.layer,
                $ = layui.jquery,
                form = layui.form,
                laydate = layui.laydate,
                table = layui.table;


        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                var editIndex = layer.open({
                    title : "编辑定时任务",
                    type : 2,
                    content : "/admin/quartzTask/edit?id="+data.id,
                    success : function(layero, index){
                        setTimeout(function(){
                            layer.tips('点击此处返回定时任务列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        },500);
                    }
                });
                //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                $(window).resize(function(){
                    layer.full(editIndex);
                });
                layer.full(editIndex);
            }
            if(obj.event === "del"){
                layer.confirm("你确定要删除该定时任务么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("/admin/quartzTask/delete",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("删除成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
            if(obj.event === "paush"){
                layer.confirm("你确定要暂停该定时任务么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("/admin/quartzTask/paush",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("暂停成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
            if(obj.event === "resume"){
                layer.confirm("你确定要恢复该定时任务么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("/admin/quartzTask/resume",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("恢复成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
            if(obj.event === "run"){
                layer.confirm("你确定要立即执行该定时任务么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            $.post("/admin/quartzTask/run",{"ids":[data.id]},function (res){
                                if(res.success){
                                    layer.msg("执行成功",{time: 1000},function(){
                                        location.reload();
                                    });
                                }else{
                                    layer.msg(res.message);
                                }

                            });
                        }
                )
            }
        });

        var t = {
            elem: '#test',
            url:'/admin/quartzTask/list',
            method:'post',
            width: $(parent.window).width()-223,
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                groups: 2, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
                limits:[3,10, 20, 30]
            },
            cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            cols: [[
                {type:'checkbox'},
                {field:'name', title: '任务名称'},
                {field:'cron', title: '任务表达式'},
                {field:'targetBean', title: '执行的类'},
                {field:'trgetMethod', title: '执行方法'},
                {field:'status', title: '任务状态',templet:'#status'},
                {field:'remarks',    title: '任务说明',width:'15%'},
                {field:'createTime',  title: '创建时间',width:'15%',unresize: true}, //单元格内容水平居中
                {fixed: 'right', title:'操作',  width: '20%', align: 'center',toolbar: '#barDemo'}
            ]]
        };
        table.render(t);

        var active={
            addQuartzTask : function(){
                var addIndex = layer.open({
                    title : "添加定时任务",
                    type : 2,
                    content : "/admin/quartzTask/add",
                    success : function(layero, addIndex){
                        setTimeout(function(){
                            layer.tips('点击此处返回定时任务列表', '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                        },500);
                    }
                });
                //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
                $(window).resize(function(){
                    layer.full(addIndex);
                });
                layer.full(addIndex);
            }
        };

        $('.layui-inline .layui-btn-normal').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        form.on("submit(searchForm)",function(data){
            t.where = data.field;
            table.reload('test', t);
            return false;
        });

    });
</script>
</body>
</html>