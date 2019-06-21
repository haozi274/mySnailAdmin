<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>LayIM测试</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/static/layuiadmin/style/admin.css" media="all">
</head>

<body>




<script src="../../../layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '../../../layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'layim'], function(){
        var $ = layui.$
            ,admin = layui.admin
            ,element = layui.element
            ,router = layui.router();


        var layim = layui.layim;

        //演示自动回复
        var autoReplay = [
            '您好，我现在有事不在，一会再和您联系。',
            '你没发错吧？face[微笑] ',
            '洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！face[哈哈] ',
            '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
            'face[威武] face[威武] face[威武] face[威武] ',
            '<（@￣︶￣@）>',
            '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
            'face[黑线]  你慢慢说，别急……',
            '(*^__^*) face[嘻嘻] ，是贤心吗？'
        ];

        //基础配置
        layim.config({
            //初始化接口  显示的朋友列表
            init: {
                url: layui.setter.base + 'http://localhost:8098/sys/chat/list?id=38'
                ,data: {}
            }
            //查看群员接口
            ,members: {
                url: layui.setter.base + 'json/layim/getMembers.json'
                ,data: {}
            }

            ,uploadImage: {
                url: '' //（返回的数据格式见下文）
                ,type: '' //默认post
            }
            ,uploadFile: {
                url: '' //（返回的数据格式见下文）
                ,type: '' //默认post
            }

            ,isAudio: true //开启聊天工具栏音频
            ,isVideo: true //开启聊天工具栏视频

            //扩展工具栏
            ,tool: [{
                alias: 'code'
                ,title: '代码'
                ,icon: '&#xe64e;'
            }]

            //,brief: true //是否简约模式（若开启则不显示主面板）

            //,title: 'WebIM' //自定义主面板最小化时的标题
            //,right: '100px' //主面板相对浏览器右侧距离
            //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
            ,initSkin: '3.jpg' //1-5 设置初始背景
            //,skin: ['aaa.jpg'] //新增皮肤
            //,isfriend: false //是否开启好友
            //,isgroup: false //是否开启群组
            //,min: true //是否始终最小化主面板，默认false
            //,notice: true //是否开启桌面消息提醒，默认false
            //,voice: false //声音提醒，默认开启，声音文件为：default.mp3

            ,msgbox: '/layim/demo/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
            ,find: '/layim/demo/find.html' //发现页面地址，若不开启，剔除该项即可
            ,chatLog: '/layim/demo/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

        });
        //监听在线状态的切换事件
        layim.on('online', function(status){
            layer.msg(status);
        });

        //监听签名修改
        layim.on('sign', function(value){
            layer.msg(value);
        });
        //监听自定义工具栏点击，以添加代码为例
        layim.on('tool(code)', function(insert){
            layer.prompt({
                title: '插入代码 - 工具栏扩展示例'
                ,formType: 2
                ,shade: 0
            }, function(text, index){
                layer.close(index);
                insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
            });
        });

        //监听layim建立就绪
        layim.on('ready', function(res){
            //console.log(res.mine);
            layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
        });
        //监听发送消息
        layim.on('sendMessage', function(data){
            var To = data.to;
            //console.log(data);

            if(To.type === 'friend'){
                layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
            }

            //演示自动回复
            setTimeout(function(){
                var obj = {};
                if(To.type === 'group'){
                    obj = {
                        username: '模拟群员'+(Math.random()*100|0)
                        ,avatar: layui.cache.dir + 'images/face/'+ (Math.random()*72|0) + '.gif'
                        ,id: To.id
                        ,type: To.type
                        ,content: autoReplay[Math.random()*9|0]
                    }
                } else {
                    obj = {
                        username: To.name
                        ,avatar: To.avatar
                        ,id: To.id
                        ,type: To.type
                        ,content: autoReplay[Math.random()*9|0]
                    }
                    layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
                }
                layim.getMessage(obj);
            }, 1000);
        });
        //监听查看群员
        layim.on('members', function(data){
            //console.log(data);
        });

        //监听聊天窗口的切换
        layim.on('chatChange', function(res){
            var type = res.data.type;
            console.log(res.data.id)
            if(type === 'friend'){
                //模拟标注好友状态
                //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
            } else if(type === 'group'){
                //模拟系统消息
                layim.getMessage({
                    system: true
                    ,id: res.data.id
                    ,type: "group"
                    ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
                });
            }
        });



    });
</script>