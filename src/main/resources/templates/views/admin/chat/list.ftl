

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>聊天记录</title>

    <link rel="stylesheet" href="/static/layuiadmin/assets/layui/css/layui.css">
    <link rel="stylesheet" href="/static/layuiadmin/assets/common.css"/>
    <style>
        body .layim-chat-main{height: auto;}
    </style>
</head>
<body>

<div class="layim-chat-main">
    <ul id="LAY_view"></ul>
</div>

<div id="LAY_page" style="margin: 0 10px;"></div>


<textarea title="消息模版" id="LAY_tpl" style="display:none;">
{{# layui.each(d.data, function(index, item){
  if(item.id == parent.layui.layim.cache().mine.id){ }}
    <li class="layim-chat-mine"><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite><i>{{ layui.data.date(item.timestamp) }}</i>{{ item.username }}</cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
  {{# } else { }}
    <li><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite>{{ item.username }}<i>{{ layui.data.date(item.timestamp) }}</i></cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
  {{# }
}); }}
</textarea>

<!-- 
上述模版采用了 laytpl 语法，不了解的同学可以去看下文档：http://www.layui.com/doc/modules/laytpl.html

-->

<div id="test1"></div>
<script src="/static/layuiadmin/layui/layui.js"></script>
<script>
    layui.use(['layim', 'laypage'], function(){
        var layim = layui.layim
            ,layer = layui.layer
            ,laytpl = layui.laytpl
            ,$ = layui.jquery
            ,laypage = layui.laypage;

        //聊天记录的分页此处不做演示，你可以采用laypage，不了解的同学见文档：http://www.layui.com/doc/modules/laypage.html
        //执行一个laypage实例
        var total =0;
        //开始请求聊天记录
        var param = {page:0,size:10,id:'${acceptId}'}    //实际使用时，下述的res一般是通过Ajax获得，而此处仅仅只是演示数据格式
            ,res = {    };
        laypage.render({
            elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: total //数据总数，从服务端得到
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数
                param.page=obj.curr;
                param.size=obj.limit;
                getChatList();
            }
        });



        getChatList();

        function getChatList() {

            $.ajax({
                type: "get",
                 contentType: "application/json;charset=UTF-8",
                url: "/sys/chat/chatlog",
                data: param,
                dataType: 'json',
                success: function(result) {
                        res = result;
                    total = result.total;
                     console.log(res.data);
                }

            });

        }


        //console.log(param)
    setTimeout(function () {
        var html = laytpl(LAY_tpl.value).render({
            data: res.data
        });
        $('#LAY_view').html(html);
    },200)


    });
</script>
</body>
</html>
