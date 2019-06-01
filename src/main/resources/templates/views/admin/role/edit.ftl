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
      <div class="layui-card-header">填写角色信息</div>
      <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
          <input type="hidden" value="${sysRole.id}" name="id">
          <div class="layui-row layui-col-space10 layui-form-item">
            <div class="layui-col-lg6">
              <label class="layui-form-label">角色名称：</label>
              <div class="layui-input-block">
                <input type="text" name="name" lay-verify="required" value="${sysRole.name}" placeholder="" autocomplete="off"
                       class="layui-input">
              </div>
            </div>

          </div>


          <div class="layui-form-item">
            <label class="layui-form-label">备注：</label>
            <div class="layui-input-block">
              <textarea name="remark"  placeholder="" class="layui-textarea">${sysRole.remark}</textarea>
            </div>
          </div>
          <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title"
                      style="margin-top: 30px;margin-left: 10px;">
              <legend>请分配角色权限</legend>
            </fieldset>
          </div>
          <div class="layui-form-item">
            <div class="layui-input-block">
              <#list trpList as tree>
                <ul>
                  <li>
                    <div style="float: left;margin-left: 10px">
                      <fieldset class="layui-elem-field">
                        <legend> <input type="checkbox"  name="permissionList" value="${tree.id}" title="${tree.name}"
                      <#if (tree.status ??)>checked</#if>
                          ></legend>
                        <ul>
                          <#list tree.list as twotree>
                            <li>
                              <div style="margin-left: 50px;margin-top: 10px">
                                <div>
                                  <input type="checkbox" name="permissionList"  value="${twotree.id}" title="${twotree.name}" <#if (twotree.status ??)>checked</#if>>
                                  <br>
                                  <ul>
                                    <#list twotree.list as threeTree>
                                      <li>
                                        <div style="margin-left: 70px;margin-top: 10px">
                                          <input type="checkbox"  name="permissionList" value="${threeTree.id}" title="${threeTree.name}"<#if (threeTree.status ??)>checked</#if>>
                                          <ul>
                                            <#list threeTree.list as fourTree>
                                              <li>
                                                <div style="margin-left: 90px;margin-top: 10px">
                                                  <input type="checkbox"  name="permissionList"   value="${fourTree.id}" title="${fourTree.name}"<#if (fourTree.status ??)>checked</#if> >
                                                  <ul>
                                                    <#list fourTree.list as fiveTree>
                                                      <li>
                                                        <div style="margin-left: 100px;margin-top: 10px">
                                                          <input type="checkbox"  name="permissionList" value="${fiveTree.id}" title="${fiveTree.name}"<#if (fiveTree.status ??)>checked</#if>>
                                                        </div>
                                                      </li>
                                                    </#list>
                                                  </ul>
                                                </div>
                                              </li>
                                            </#list>
                                          </ul>
                                        </div>
                                      </li>
                                    </#list>
                                  </ul>
                                </div>
                            </li>
                          </#list>
                        </ul>
                      </fieldset>
                    </div>
                  </li>
                </ul>
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
        var arr = [];
        $("input:checkbox[name='permissionList']:checked").each(function(i){
          arr[i] = $(this).val();
        });
        data.permissionList
        var item = {
          "name" :data.name,
          "remark":data.remark,
          "permissionList":arr
        }

        console.log(data.field.permissionList=arr);
        $.ajax({
          type: "POST",
          contentType: "application/json;charset=UTF-8",
          url: "/sys/role/edit",
          data: JSON.stringify(data.field),
          dataType: 'json',
          success: function (result) {
            layer.close(loadIndex);
            if (result.success) {
              parent.layer.msg("修改角色成功!", {time: 1000}, function () {
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