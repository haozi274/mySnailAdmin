<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>设置我的资料</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>
<body>
<form class="layui-form" action="" lay-filter="component-form-element">


    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header">设置我的资料</div>
                    <div class="layui-card-body" pad15>
                        <input type="hidden" name="id" value="${userInfo.id}">
                      <input type="hidden" name="icon" value="${userInfo.icon}" id="icon">
                        <div class="layui-form" lay-filter="">
                            <div class="layui-form-item">
                                <label class="layui-form-label">我的角色</label>
                                <div class="layui-input-inline">
                                    <#list userInfo.roles as role>
                                        <input type="checkbox" disabled checked value="${role.id}" title="${role.name}">
                                    </#list>
                                </div>
                                <div class="layui-form-mid layui-word-aux">当前角色不可更改为其它角色</div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户名</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="loginName" value="${userInfo.loginName}" readonly
                                           class="layui-input">
                                </div>
                                <div class="layui-form-mid layui-word-aux">不可修改。一般用于后台登入名</div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">昵称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="username" value="${userInfo.username}"
                                           lay-verify="nickname" autocomplete="off" placeholder="请输入昵称"
                                           class="layui-input">
                                </div>
                            </div>
                            <#--     <div class="layui-form-item">
                                   <label class="layui-form-label">性别</label>
                                   <div class="layui-input-block">
                                     <input type="radio" name="sex" value="男" title="男">
                                     <input type="radio" name="sex" value="女" title="女" checked>
                                   </div>
                                 </div>-->
                            <div class="layui-form-item">
                                <label class="layui-form-label">头像</label>
                                <div class="layui-input-inline">
                                    <#--<input name="avatar" lay-verify="required" id="LAY_avatarSrc" placeholder="图片地址"

                                           value="" class="layui-input">-->
                                    <img <#if userInfo.ico?? > src="/static/image/bpdwn.png"</#if>  src="${userInfo.iconString}" id="LAY_avatarSrc" width="100px" height="100px">
                                </div>
                                <div class="layui-input-inline layui-btn-container" style="width: auto;">

                                    <button type="button" class="layui-btn" id="test1" style="position: relative;top:60px;left: -80px">
                                        <i class="layui-icon">&#xe67c;</i>上传图片
                                    </button>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">手机</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="telephone" value="${userInfo.telephone}" lay-verify="phone"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">邮箱</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="mail" value="${userInfo.mail}" lay-verify="email"
                                           autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item layui-form-text">
                                <label class="layui-form-label">备注</label>
                                <div class="layui-input-block">
                                    <textarea name="remark" placeholder="请输入内容"
                                              class="layui-textarea">${userInfo.remark}</textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="submitForm" >确认修改
                                    </button>
                                    <button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
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
    }).use(['index', 'set'], function () {
        var form = layui.form,
                $=layui.$,
            upload = layui.upload;;
        form.on('submit(submitForm)', function (data) {
          //   layer.msg(JSON.stringify(data.field));
            var loadIndex = layer.load(2, {
                shade: [0.3, '#333']
            });

            $.ajax({
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                url: "/api/user/user_info_edit",
                data: JSON.stringify(data.field),
                dataType: 'json',
                success: function (result) {
                    layer.close(loadIndex);
                    if (result.success) {
                        layer.msg("修改用户资料成功");
                    } else {
                        layer.msg(result.message);
                    }
                }
            });
            return false;
        });

        /***
         * 上传图片
         */
        var index;
        upload.render({
            elem: '#test1',
            url: '/sys/qiniu/qiniu',
            multiple: false,
            before: function(obj){
               index =   layer.load(); //上传loading
            },
            done: function(res){
                console.log(res)
                $("#LAY_avatarSrc").attr("src",res.data.url);
                $("#icon").val(res.data.id);
                layer.close(index);
                //上传完毕
            },
            error: function(index, upload){
                debugger
                //上传错误
            }
        });

    });
</script>
</body>
</html>