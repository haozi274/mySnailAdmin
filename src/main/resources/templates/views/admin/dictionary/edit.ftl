<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加用户</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
  <div class="layui-row layui-col-space15">


    <div class="layui-card">
      <div class="layui-card-header">填写字典信息</div>
      <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
          <input type="hidden" name="id" value="${sysDictionary.id}">
          <div class="layui-row layui-col-space10 layui-form-item">
            <div class="layui-col-lg6">
              <label class="layui-form-label">类型：</label>
              <div class="layui-input-block">
                <input type="text" name="type" lay-verify="required" value="${sysDictionary.type}" placeholder="" autocomplete="off"
                       class="layui-input">
              </div>
            </div>
          </div>

          <div class="layui-form-item">
            <label class="layui-form-label">标签：</label>
            <div class="layui-input-block">
              <input type="text" name="label" lay-verify="required"  value="${sysDictionary.label}" placeholder="" autocomplete="off"
                     class="layui-input">
            </div>
          </div>

          <div class="layui-form-item">
            <label class="layui-form-label">值：</label>
            <div class="layui-input-block">
              <input type="text" name="value" lay-verify="required"  value="${sysDictionary.value}" placeholder="" autocomplete="off"
                     class="layui-input">
            </div>
          </div>

          <div class="layui-form-item">
            <label class="layui-form-label">描述：</label>
            <div class="layui-input-block">
              <textarea name="remark" placeholder="" class="layui-textarea">${sysDictionary.remark}</textarea>
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
    }).use(['index', 'form'], function () {
      var $ = layui.$
              , admin = layui.admin
              , element = layui.element
              , form = layui.form;

      form.render(null, 'component-form-element');
      element.render('breadcrumb', 'breadcrumb');

      form.on('submit(component-form-element)', function (data) {
        var loadIndex = layer.load(2, {
          shade: [0.3, '#333']
        });
        $.ajax({
          type: "POST",
          contentType: "application/json;charset=UTF-8",
          url: "/sys/dictionary/edit",
          data: JSON.stringify(data.field),
          dataType: 'json',
          success: function (result) {
            layer.close(loadIndex);
            if (result.success) {
              parent.layer.msg("修改数据字典成功!", {time: 1000}, function () {
                //刷新父页面
                parent.location.reload();
              });
            } else {
              layer.msg(result.message);
            }
          }
        });

        return false;
      });
      /***
       * 开关事件
       */
      form.on('switch(status)', function (data) {
        console.log(data.elem.checked); //开关是否开启，true或者false
        var status = data.elem.checked ? '1' : 0;
        $("#status").val(status);
      });
    });
  </script>
</body>
</html>