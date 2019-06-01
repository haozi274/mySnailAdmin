

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>设置我的密码</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>
<body>
<form class="layui-form" action="" lay-filter="component-form-element">


  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">修改密码</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">当前密码</label>
                <div class="layui-input-inline">
                  <input type="password" value="1234567" name="oldPassword" lay-verify="required" lay-verType="tips" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="password" lay-verify="pass" lay-verType="tips" autocomplete="off" id="LAY_password" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">确认新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="repassword" lay-verify="repass" lay-verType="tips" autocomplete="off" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn" lay-submit lay-filter="component-form-element">确认修改</button>
                </div>
              </div>
            </div>
            
          </div>
        </div>
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
  }).use(['index', 'set'],function () {
    var form =layui.form,
            $=layui.$;
    form.on('submit(component-form-element)', function(data){
      console.log(data.field);
      // layer.msg(JSON.stringify(data.field));
      var loadIndex = layer.load(2, {
        shade: [0.3, '#333']
      });
      $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "/api/user/update_pwd",
        data: JSON.stringify(data.field),
        dataType: 'json',
        success: function(result) {
          layer.close(loadIndex);
          if(result.success){
            parent.layer.msg("修改密码成功!",{time:1000},function(){
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
  });
  </script>
</body>
</html>