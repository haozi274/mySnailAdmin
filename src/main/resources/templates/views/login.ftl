<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
    <style>
        ::-webkit-input-placeholder { /* WebKit browsers */
            color: #adbdcc;
            font-size: 16px;
        }

        ::-moz-placeholder { /* Mozilla Firefox 19+ */
            color: #adbdcc;
            font-size: 16px;
        }

        :-ms-input-placeholder { /* Internet Explorer 10+ */
            color: #adbdcc;
            font-size: 16px;
        }

        * {
            margin: 0px;
            padding: 0px;
            border: 0px;
            font-family: Arial, 'Hiragino Sans GB', '微软雅黑', '黑体-简', Helvetica, sans-serif;
        }

        body {
            background-image: url("../../static/image/login_bg2.png");
            width: 100%;
            height: 100%;
            background-repeat: no-repeat;
            background-size: 150% 150%; /* 通过百分比设置 */
            background-size: cover; /* 覆盖:图片成比例填满盒子。可用于适配 */

        }

        ul {
            list-style: none;
        }

        a {
            text-decoration: none;
            backgrournd-color: rgb(116, 150, 247);
            width: 100px;
            height: 100px
        }

        a {
            font-size: 16px;
            color: white
        }

        a:hover {
            color: white
        }

        .div_login_btn {
            width: 130px;
            height: 40px;
            background-color: rgb(61, 108, 243);
            border-radius: 5px;
            text-align: center;
            line-height: 1.7;
            font-size: 18px;
            margin: 0 auto;
        }

        .login_main {
            width: 350px;
            margin: 0 auto;
            margin-top: 10%;
        }

        .div_input input {
            margin-left: 8px;
            outline: none;
            font-size: 16px;
            width: 67%;
            line-height: 32px;
            height: 32px;

            border: none;
            border-radius: 100px;
            background-color: transparent !important;
            padding: 8px 0;
            /* margin-left: 10px; */
            color: white;
            margin-top: 3px
        }
    </style>

    <script>

        function getOver(o) {
            o.style.background = "rgb(65, 111, 243)";
        }

        function getOut(o) {
            o.style.background = "rgb(61, 108, 243)";
        }

        function jiaoyan(o,v) {
            if (o.value != ''&&o.value.length<5) {
                document.getElementById(v).style.borderColor="#fa7252";
                document.getElementById("warn_username").innerText= "用户名格式不正确,请输入正确格式的用户名"
            }else{
                document.getElementById(v).style.borderColor="white";
                document.getElementById("warn_username").innerText= ""
            }

        }
    </script>
</head>
<body>
<form class="layui-form" action="" lay-filter="component-form-element">
    <header style="color: white; text-align: center;font-size: 13px;font-family: 宋体;">
        <h1 style="font-weight: 400;">欢迎  登陆管理系统</h1>
    </header>
    <div class="div_input" style="margin-top: 30px">
        <ul>
            <li>
                <div style="border-bottom:  1px solid;border-color: white;font-family: Arial" id="usernameDiv">
                    <input type="text" placeholder="请输入用户名" onblur="jiaoyan(this,'usernameDiv')">

                </div>
                <div id="warn_username" style="color: #fa7252;font-size: 12px;padding-top: 5px"></div>
            </li>
            <li>
                <div style="border-bottom:  1px solid;border-color: white;margin-top: 20px" id="pwdDiv">
                    <input type="text" placeholder="请输入密码" onfocus="jiaoyan(this,'pwdDiv')">
                    <div id="warn_pwd"></div>
                </div>
            </li>
        </ul>
    </div>

    <div class="div_login_btn" style="margin-top: 30px;font-size: 20px" onmouseover="getOver(this)"
         onmouseout="getOut(this)">
        <a href="#" style="font-size: 16px;letter-spacing: 2px">登录</a>
    </div>
</form>

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
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });
        console.log(data.field);
            $.ajax({
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                url: "/sys/login/login",
                data: JSON.stringify(data.field),
                dataType: 'json',
                success: function(result) {
                    layer.close(loadIndex);
                    if(result.success){
                          location.href="/sys/login/main"
                    }else{
                        layer.msg(result.message);
                    }
                },
                error:function(result) {
                    console.log(result.responseJSON.message)
                    layer.close(loadIndex);
                    layer.msg(result.responseJSON.message);

                }
            });
            return false;
        });

    });
</script>
</body>
</html>