

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加用户</title>
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
              <input type="hidden"  name="id" value="${sysUser.id}">





              <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                  <label class="layui-form-label">登录名：</label>
                  <div class="layui-input-block">
                    <input type="text" name="loginName" lay-verify="required" value="${sysUser.loginName}"   placeholder="" autocomplete="off" class="layui-input">
                  </div>
                </div>
                <div class="layui-col-lg6">
                  <label class="layui-form-label">昵称：</label>
                  <div class="layui-input-block">
                    <input type="text" name="username" lay-verify="required"   value="${sysUser.username}"   placeholder="" autocomplete="off" class="layui-input">
                  </div>
                </div>

              </div>



              <div class="layui-row layui-col-space10 layui-form-item">
                <div class="layui-col-lg6">
                  <label class="layui-form-label">联系电话：</label>
                  <div class="layui-input-block">
                    <input type="number" name="telephone" lay-verify="required"  value="${sysUser.telephone}"   placeholder="" autocomplete="off" class="layui-input">
                  </div>
                </div>
                <div class="layui-col-lg6">
                  <label class="layui-form-label">邮箱：</label>
                  <div class="layui-input-block">
                    <input type="email" name="mail" lay-verify="required" placeholder=""  value="${sysUser.mail}"   autocomplete="off" class="layui-input">
                  </div>
                </div>

                <div class="layui-form-item">
                  <label class="layui-form-label">是否启用：</label>
                  <div class="layui-input-block">

                    <input type="checkbox" id="status" name="status" lay-skin="switch"  <#if sysUser.status == 1>
                      checked
                    </#if> lay-filter="status" lay-text="是|否">
                  </div>
                </div>
              </div>




              <div class="layui-form-item">
                <label class="layui-form-label">备注：</label>
                <div class="layui-input-block">
                  <textarea name="remark" placeholder=""  class="layui-textarea" >${sysUser.remark}</textarea>
                </div>
              </div>

              <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title"
                          style="margin-top: 30px;margin-left: 10px;">
                  <legend>请分配角色</legend>
                </fieldset>
              </div>

              <div class="layui-form-item">
                <div class="layui-input-block">
                  <#list roleList as role>
                    <input type="checkbox" <#if role.status ??>checked</#if>  name="roleList" value="${role.id}" title="${role.name}">
                  </#list>
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
  }).use(['index', 'form'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,element = layui.element
    ,form = layui.form;
    
    form.render(null, 'component-form-element');
    element.render('breadcrumb', 'breadcrumb');

    form.on('submit(component-form-element)', function(data){
     // layer.msg(JSON.stringify(data.field));
      var loadIndex = layer.load(2, {
        shade: [0.3, '#333']
      });
      var arr = [];
      $("input:checkbox[name='roleList']:checked").each(function(i){
        arr[i] = $(this).val();
      });
      data.field.status= data.field.status=="on"?1: 0;
      console.log(data.field.roleList=arr);
      $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "/api/user/edit",
        data: JSON.stringify(data.field),
        dataType: 'json',
        success: function(result) {
          layer.close(loadIndex);
          if(result.success){
            parent.layer.msg("修改用户成功!",{time:1000},function(){
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
    form.on('switch(status)', function(data){
      console.log(data.elem); //得到checkbox原始DOM对象
      console.log(data.elem.checked); //开关是否开启，true或者false
    $("#status").val(data.elem.checked?1:0)
    });
  });
  </script>
</body>
</html>