
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
    <#--    <div class="layui-form-item">
          <div class="layui-row">
            <div class="layui-col-xs7">
              <label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label>
              <input type="text" name="vercode" id="LAY-user-login-vercode" lay-verify="required" placeholder="图形验证码" class="layui-input">
            </div>
            <div class="layui-col-xs5">
              <div style="margin-left: 10px;">
                <img src="https://www.oschina.net/action/user/captcha" class="layadmin-user-login-codeimg" id="LAY-user-get-vercode">
              </div>
            </div>
          </div>
        </div>-->

        <div >
          <label>完成验证：</label>
          <div id="captcha2">
            <p id="wait2" class="show">正在加载验证码......</p>
          </div>
        </div>
        <br>
        <p id="notice2" class="hide">请先完成验证</p>
        <div class="layui-form-item" style="margin-bottom: 20px;">
          <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
          <a href="forget.html" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">忘记密码？</a>
        </div>
        <div class="layui-form-item">
          <#---->
          <button class="layui-btn layui-btn-fluid" id="submit2">登 入</button>
        </div>
        <div class="layui-trans layui-form-item layadmin-user-login-other">
          <label>社交账号登入</label>
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
  <script>
  layui.config({
    base: '/static/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'user'], function(){
    var $ = layui.$
    ,setter = layui.setter
    ,admin = layui.admin
    ,form = layui.form
    ,router = layui.router()
    ,search = router.search;

    form.render();

    //提交
    form.on('submit(LAY-user-login-submit)', function(obj){
      //请求登入接口


      $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "/sys/login/login",
        data: JSON.stringify(obj.field),
        dataType: 'json',

        success: function(result) {
          console.log(result)
          if(result.success){
            location.href="/sys/login/main"
          }else{
            console.log(result)
            layer.msg(result.message);
          }
        },
        error:function(result) {
          console.log(result.responseJSON.message)
          layer.close(loadIndex);
          layer.msg(result.responseJSON.message);

        }
      });


    /*  admin.req({
        type:'post',
        url: "/sys/login/login"//实际使用请改成服务端真实接口
        ,data: obj.field
        ,done: function(res){
        
          //请求成功后，写入 access_token
          layui.data(setter.tableName, {
            key: setter.request.tokenName
            ,value: res.data.access_token
          });
          
          //登入成功的提示与跳转
          layer.msg('登入成功', {
            offset: '15px'
            ,icon: 1
            ,time: 1000
          }, function(){
            location.href = '/sys/login/main'; //后台主页
          });
        }
      });*/
      
    });
    
    
    //实际使用时记得删除该代码
    layer.msg('为了方便演示，用户名密码可随意输入', {
      offset: '15px'
      ,icon: 1
    });
    
  });
  </script>


  <!-- 引入 gt.js，既可以使用其中提供的 initGeetest 初始化函数 -->
  <script src="/static/layuiadmin/js/gt.js"></script>
  <script>
    layui.config({
      base: '/static/layuiadmin/' //静态资源所在路径
    }).extend({
      index: 'lib/index' //主入口模块
    }).use(['index', 'user'], function(){
      var $ = layui.$
              ,setter = layui.setter
              ,admin = layui.admin
              ,form = layui.form
              ,router = layui.router()
              ,search = router.search;

      form.render();

      //提交
      form.on('submit(LAY-user-login-submit)', function(obj){

        //请求登入接口
  /*      admin.req({
          url: '/sys/login/login' //实际使用请改成服务端真实接口
          ,data: obj.field
          ,done: function(res){

            //请求成功后，写入 access_token
            layui.data(setter.tableName, {
              key: setter.request.tokenName
              ,value: res.data.access_token
            });

            //登入成功的提示与跳转
            layer.msg('登入成功', {
              offset: '15px'
              ,icon: 1
              ,time: 1000
            }, function(){
              location.href = '../'; //后台主页
            });
          }
        });*/

      });


      //实际使用时记得删除该代码
     layer.msg('用户名:test 密码 :123456', {
        offset: '15px'
        ,icon: 1
      });
      var handler2 = function (captchaObj) {
        $("#submit2").click(function (e) {
          var result = captchaObj.getValidate();
          if (!result) {
            $("#notice2").show();
            setTimeout(function () {
              $("#notice2").hide();
            }, 2000);
          } else {

            if ($("#username").val() ==''||$("#password").val()=='') {

              layer.msg("用户名或密码不能为空");
              return;
            }
            $.ajax({
              url: '/sys/verify/login',
              type: 'POST',
              dataType: 'json',
              data: {
                userName:$("#username").val(),
                password:$("#password").val(),
                geetestChallenge: result.geetest_challenge,
                geetestValidate: result.geetest_validate,
                geetestSeccode: result.geetest_seccode
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
          e.preventDefault();
        });
        // 将验证码加到id为captcha的元素里，同时会有三个input的值用于表单提交
        captchaObj.appendTo("#captcha2");
        captchaObj.onReady(function () {
          $("#wait2").hide();
        });
        // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
      };
      $.ajax({
        url: "/sys/verify/first?t=" + (new Date()).getTime(), // 加随机数防止缓存
        type: "get",
        dataType: "json",
        success: function (data) {
          // 调用 initGeetest 初始化参数
          // 参数1：配置参数
          // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它调用相应的接口
          initGeetest({
            gt: data.gt,
            challenge: data.challenge,
            new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
            offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
            product: "popup", // 产品形式，包括：float，popup
            width: "100%"
            // 更多配置参数请参见：http://www.geetest.com/install/sections/idx-client-sdk.html#config
          }, handler2);
        }
      });
    });
  </script>


</body>
</html>