

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加菜单</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
  <div class="layui-row layui-col-space15">


    <div class="layui-card">
      <div class="layui-card-header">填写用户信息</div>
      <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
          <input type="hidden" name="parentId" value="${sysMeun.parentId}">
          <input type="hidden" name="level" value="${sysMeun.level}">
          <#if sysMeun.parentName != '-1'>
            <div class="layui-col-lg6">
              <label class="layui-form-label">父级菜单名称：</label>
              <div class="layui-input-block">
                <input type="text"   lay-verify="required" disabled value="${sysMeun.parentName }" placeholder="" autocomplete="off" class="layui-input">
              </div>
            </div>
          </#if>
          <div class="layui-row layui-col-space10 layui-form-item">
            <div class="layui-col-lg6">
              <label class="layui-form-label">菜单名称：</label>
              <div class="layui-input-block">
                <input type="text" name="name"  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
              </div>
            </div>
            <div class="layui-col-lg6">
              <label class="layui-form-label">菜单地址：</label>
              <div class="layui-input-block">
                <input type="text" name="href"  placeholder="" autocomplete="off" class="layui-input">
              </div>
            </div>
          </div>

          <div class="layui-row layui-col-space10 layui-form-item">
            <div class="layui-col-lg6">
              <label class="layui-form-label">菜单权限：</label>
              <div class="layui-input-block">
                <input type="text"  name="permission" placeholder="" autocomplete="off" class="layui-input">
              </div>
            </div>

            <div class="layui-col-lg6">
              <label class="layui-form-label">菜单图标：</label>
              <div class="layui-input-block">
                <input type="hidden" id="iconValue" name="icon"  placeholder="" autocomplete="off" class="layui-input">
                <div class="layui-input-inline" style="width: 100px;">
                  <i class="layui-icon" id="realIcon" style="display: none;font-size: 50px"></i>
                </div>
                <div class="layui-input-inline" style="width: 100px;">
                  <a class="layui-btn layui-btn-normal" id="selectIcon">我来选择一个图标</a>
                </div>
              </div>
            </div>
          </div>

          <div class="layui-row layui-col-space10 layui-form-item">
            <div class="layui-col-lg6">
              <label class="layui-form-label">左侧菜单是否显示：</label>
              <div class="layui-input-block">
                <input type="checkbox" id="isShow" value="0" name="is_show" lay-skin="switch" lay-filter="is_show" lay-text="是|否">
              </div>
            </div>
            <div class="layui-col-lg6">
              <label class="layui-form-label">排序：</label>
              <div class="layui-input-block">
                <input type="number"  name="sort" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
              </div>
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
    var iconShow;
    layui.config({
      base: '/static/layuiadmin/' //静态资源所在路径
    }).extend({
      index: 'lib/index' //主入口模块
    }).use(['index', 'form'], function(){
      var $ = layui.$
              ,admin = layui.admin
              ,element = layui.element
              ,form = layui.form;

      form.render(null, 'component-form-element');
      element.render('breadcrumb', 'breadcrumb');

      form.on('submit(component-form-element)', function(data){
        var loadIndex = layer.load(2, {
          shade: [0.3, '#333']
        });

        data.field.is_show= data.field.is_show==undefined||data.field.is_show==""?0: 1;
        $.ajax({
          type: "POST",
          contentType: "application/json;charset=UTF-8",
          url: "/sys/meun/add",
          data: JSON.stringify(data.field),
          dataType: 'json',
          success: function(result) {
            layer.close(loadIndex);
            if(result.success){
              parent.layer.msg("添加菜单成功!",{time:1000},function(){
                //刷新父页面
                parent.location.reload();
              });
            }else{
              layer.msg(result.message);
            }
          }
        });

        return false;
      });

      form.on('switch(is_show)', function(data){
        console.log(data.elem.checked); //开关是否开启，true或者false
        var is_show = data.elem.checked?'1':0;
        $("#isShow").val(is_show);
      });

      $("#selectIcon").on("click",function () {
        iconShow =layer.open({
          type: 2,
          title: '选择图标',
          shadeClose: true,
          content: '/static/layuiadmin/pages/icon.html'
        });
        layer.full(iconShow);
      });

    });
  </script>
</body>
</html>