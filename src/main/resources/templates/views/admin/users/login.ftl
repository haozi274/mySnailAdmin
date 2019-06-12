
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>登入 - 奔跑的蜗牛</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="icon" type="image/x-icon" href="/static/image/favicon.ico">
  <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href=/static/layuiadmin/style/admin.css" media="all">
  <link rel="stylesheet" href="/static/layuiadmin/style/login.css" media="all">
  <script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
  <style>

    .inp {
      border: 1px solid #cccccc;
      border-radius: 2px;
      padding: 0 10px;
      width: 278px;
      height: 40px;
      font-size: 18px;
    }
    .btn {
      border: 1px solid #cccccc;
      border-radius: 2px;
      width: 100px;
      height: 40px;
      font-size: 16px;
      color: #666;
      cursor: pointer;
      background: white linear-gradient(180deg, #ffffff 0%, #f3f3f3 100%);
    }
    .btn:hover {
      background: white linear-gradient(0deg, #ffffff 0%, #f3f3f3 100%)
    }
    #captcha1,
    #captcha2 {
      width: 333px;
      display: inline-block;
    }
    .show {
      display: block;
    }
    .hide {
      display: none;
    }
    #notice1,
    #notice2 {
      color: red;
    }
    label {
      vertical-align: top;
      display: inline-block;
      width: 80px;
      text-align: right;
    }
    #wait1, #wait2 {
      text-align: left;
      color: #666;
      margin: 0;
    }
  </style>
</head>
<body>

  <div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>奔跑的蜗牛</h2>
        <p>Snail官方出品的单页面后台管理模板系统</p>
      </div>
      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
          <input type="text" name="loginName" id="username" lay-verify="required" placeholder="用户名" class="layui-input">
        </div>
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
          <input type="password" name="password" id="password" lay-verify="required" placeholder="密码" class="layui-input">
        </div>
        <br>

        <div class="layui-form-item">
          <#---->
          <button class="layui-btn layui-btn-fluid"  id="TencentCaptcha" data-appid="2033625672" data-cbfn="callback">登 入</button>
        </div>
        <div class="layui-trans layui-form-item layadmin-user-login-other">
          <label>社交账号</label>
          <a href="javascript:;"><i class="layui-icon layui-icon-login-qq"></i></a>
          <a href="javascript:;"><i class="layui-icon layui-icon-login-wechat"></i></a>
          <a href="javascript:;"><i class="layui-icon layui-icon-login-weibo"></i></a>
          <a href="reg.html" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
        </div>
      </div>
    </div>
    
    <div class="layui-trans layadmin-user-login-footer">
      
      <p>© 2018 <a href="http://www.layui.com/" target="_blank">haozi.com</a></p>
      <p>
        <span><a href="http://www.layui.com/admin/#get" target="_blank">获取授权</a></span>
        <span><a href="http://www.layui.com/admin/pro/" target="_blank">在线演示</a></span>
        <span><a href="http://www.layui.com/admin/" target="_blank">前往官网</a></span>
      </p>
    </div>
    
    <!--<div class="ladmin-user-login-theme">
      <script type="text/html" template>
        <ul>
          <li data-theme=""><img src="{{ layui.setter.base }}style/res/bg-none.jpg"></li>
          <li data-theme="#03152A" style="background-color: #03152A;"></li>
          <li data-theme="#2E241B" style="background-color: #2E241B;"></li>
          <li data-theme="#50314F" style="background-color: #50314F;"></li>
          <li data-theme="#344058" style="background-color: #344058;"></li>
          <li data-theme="#20222A" style="background-color: #20222A;"></li>
        </ul>
      </script>
    </div>-->
    
  </div>

  <script src="/static/layuiadmin/layui/layui.js"></script>



  <!-- 引入 gt.js，既可以使用其中提供的 initGeetest 初始化函数 -->
  <script src="/static/layuiadmin/js/gt.js"></script>
  <script>
    var jquery;
    layui.config({
      base: '/static/layuiadmin/' //静态资源所在路径
    }).extend({
      index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function() {
      var $ = layui.$
              , setter = layui.setter
              , admin = layui.admin
              , form = layui.form
              , router = layui.router()
              , search = router.search;
      jquery = $;
      form.render();
    })

    var item ={ticket:'',randstr:''};
    function callback(res){
      console.log(res)
      // res（未通过验证）= {ret: 1, ticket: null}
      // res（验证成功） = {ret: 0, ticket: "String", randstr: "String"}
      if(res.ret === 0){
        // alert(res.ticket)   // 票据
        console.info(jquery,'验证通过-----',res);

        item = {
          ticket:res.ticket,
          randstr:res.randstr
        }
        if (jquery("#username").val() ==''||jquery("#password").val()=='') {
          layer.msg("用户名或密码不能为空");
          return;
        }
        jquery.ajax({
          url: '/sys/verify/ticket',
          type: 'POST',
          dataType: 'json',
          data: {
            username:jquery("#username").val(),
            password:jquery("#password").val(),
            ticket:item.ticket,
            randstr:item.randstr
          },
          success: function (data) {
            if (data.success) {
              location.href="/sys/login/main"
            } else  {
              layer.msg(data.message);
            }
          }
        })
      }
    }


  </script>


</body>
</html>