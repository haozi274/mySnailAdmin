/**

 @Name：layuiAdmin 用户登入和注册等
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License: LPPL
    
 */

window.CHAT = {
  socket: null,
  init: function() {
    if (window.WebSocket) {

      CHAT.socket = new WebSocket("ws://127.0.0.1:8088/ws");
      CHAT.socket.onopen = function() {
        console.log("连接建立成功...");
      },
          CHAT.socket.onclose = function() {
            console.log("连接关闭...");
          },
          CHAT.socket.onerror = function() {
            console.log("发生错误...");
          },
          CHAT.socket.onmessage = function(e) {

        var data = JSON.parse(e.data);
           var  obj = {
              username: data.username
              ,avatar: data.avatar
              ,id: data.id
              ,type: 'friend'
              ,content: data.content
            }
            console.log("接受到消息：" +data.username,obj);
            layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
            layim.getMessage(obj);
          }
    } else {
      alert("浏览器不支持websocket协议...");
    }
  },
  chat: function(msg) {
    // 构建ChatMsg
    // 如果当前websocket的状态是已打开，则直接发送， 否则重连
    if (CHAT.socket != null
        && CHAT.socket != undefined
        && CHAT.socket.readyState == WebSocket.OPEN) {
      CHAT.socket.send(msg);
    } else {
      // 重连websocket
      CHAT.init();
      setTimeout("CHAT.reChat('" + msg + "')", "1000");
    }
    // 构建DataContent


  }
  ,reChat: function(msg) {
    console.log("消息重新发送...");
    CHAT.socket.send(msg);
  },
  createUser:function (id) {
    var chatMsg = new app.ChatMsg(id, null, null, null);
    // 构建DataContent
    var dataContent = new app.DataContent(app.CONNECT, chatMsg, null);
    console.log(dataContent);
    // 发送websocket
    CHAT.chat(JSON.stringify(dataContent));
    /**
     * 发送心跳
     */
    // 定时发送心跳
    setInterval("CHAT.keepalive()", 10000);
  },
  keepalive: function() {
    // 构建对象
    var dataContent = new app.DataContent(app.KEEPALIVE, null, null);
    // 发送心跳
    CHAT.chat(JSON.stringify(dataContent));

    // 定时执行函数
    /*	fetchUnReadMsg();
        fetchContactList();*/
  },
  send:function (id,shouId,msg) {

    var chatMsg = new app.ChatMsg(id ,shouId,msg, null);
    var dataContent = new app.DataContent(app.CHAT, chatMsg, null);
    CHAT.chat(JSON.stringify(dataContent))
  }
};

CHAT.init();
var layim;
layui.define(['index', 'layim'], function(exports){
  var $ = layui.$
  ,admin = layui.admin
  ,element = layui.element
  ,router = layui.router();


  layim  = layui.layim;
  
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
    //初始化接口
    init: {
      url: 'http://localhost:8098/sys/chat/list'
      ,data: {}
    }
    //查看群员接口
    ,members: {
      url: layui.setter.base + 'json/layim/getMembers.js'
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

  /**
   * 监听发送消息
   */
  layim.on('sendMessage', function(data){
    var To = data.to;
    var mine = data.mine;
    console.log(data);
      CHAT.send(To.id,mine.id,mine.content);
    if(To.type === 'friend'){
      layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
    }


  });
  //监听查看群员
  layim.on('members', function(data){
    //console.log(data);
  });

  /**
   * 监听聊天窗口的切换
   */
  layim.on('chatChange', function(res){
    var type = res.data.type;
    console.log(res.data.id)
    console.log(res.data.type)
    if(type === 'friend'){

      CHAT.createUser(res.data.id);

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
  
/*
  $('.LAY-senior-im-chat-demo .layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  })*/;
  
  
// exports('im', {});
});


window.app = {

  /**
   * netty服务后端发布的url地址
   */
  nettyServerUrl: 'ws://192.168.1.3:8088/ws',

  /**
   * 后端服务发布的url地址
   */
  serverUrl: 'http://192.168.1.3:8080',

  /**
   * 图片服务器的url地址
   */
  imgServerUrl: 'http://192.168.1.70:88/imooc/',

  /**
   * 判断字符串是否为空
   * @param {Object} str
   * true：不为空
   * false：为空
   */
  isNotNull: function(str) {
    if (str != null && str != "" && str != undefined) {
      return true;
    }
    return false;
  },

  /**
   * 封装消息提示框，默认mui的不支持居中和自定义icon，所以使用h5+
   * @param {Object} msg
   * @param {Object} type
   */
  showToast: function(msg, type) {
    plus.nativeUI.toast(msg,
        {icon: "image/" + type + ".png", verticalAlign: "center"})
  },

  /**
   * 保存用户的全局对象
   * @param {Object} user
   */
  setUserGlobalInfo: function(user) {
    var userInfoStr = JSON.stringify(user);
    plus.storage.setItem("userInfo", userInfoStr);
  },

  /**
   * 获取用户的全局对象
   */
  getUserGlobalInfo: function() {
    var userInfoStr = plus.storage.getItem("userInfo");
    return JSON.parse(userInfoStr);
  },

  /**
   * 登出后，移除用户全局对象
   */
  userLogout: function() {
    plus.storage.removeItem("userInfo");
  },

  /**
   * 保存用户的联系人列表
   * @param {Object} contactList
   */
  setContactList: function(contactList) {
    var contactListStr = JSON.stringify(contactList);
    plus.storage.setItem("contactList", contactListStr);
  },

  /**
   * 获取本地缓存中的联系人列表
   */
  getContactList: function() {
    var contactListStr = plus.storage.getItem("contactList");

    if (!this.isNotNull(contactListStr)) {
      return [];
    }

    return JSON.parse(contactListStr);
  },

  /**
   * 根据用户id，从本地的缓存（联系人列表）中获取朋友的信息
   * @param {Object} friendId
   */
  getFriendFromContactList: function(friendId) {
    var contactListStr = plus.storage.getItem("contactList");

    // 判断contactListStr是否为空
    if (this.isNotNull(contactListStr)) {
      // 不为空，则把用户信息返回
      var contactList = JSON.parse(contactListStr);
      for (var i = 0 ; i < contactList.length ; i ++) {
        var friend = contactList[i];
        if (friend.friendUserId == friendId) {
          return friend;
          break;
        }
      }
    } else {
      // 如果为空，直接返回null
      return null;
    }
  },

  /**
   * 保存用户的聊天记录
   * @param {Object} myId
   * @param {Object} friendId
   * @param {Object} msg
   * @param {Object} flag	判断本条消息是我发送的，还是朋友发送的，1:我  2:朋友
   */
  saveUserChatHistory: function(myId, friendId, msg, flag) {
    var me = this;
    var chatKey = "chat-" + myId + "-" + friendId;

    // 从本地缓存获取聊天记录是否存在
    var chatHistoryListStr = plus.storage.getItem(chatKey);
    var chatHistoryList;
    if (me.isNotNull(chatHistoryListStr)) {
      // 如果不为空
      chatHistoryList = JSON.parse(chatHistoryListStr);
    } else {
      // 如果为空，赋一个空的list
      chatHistoryList = [];
    }

    // 构建聊天记录对象
    var singleMsg = new me.ChatHistory(myId, friendId, msg, flag);

    // 向list中追加msg对象
    chatHistoryList.push(singleMsg);

    plus.storage.setItem(chatKey, JSON.stringify(chatHistoryList));
  },

  /**
   * 获取用户聊天记录
   * @param {Object} myId
   * @param {Object} friendId
   */
  getUserChatHistory: function(myId, friendId) {
    var me = this;
    var chatKey = "chat-" + myId + "-" + friendId;
    var chatHistoryListStr = plus.storage.getItem(chatKey);
    var chatHistoryList;
    if (me.isNotNull(chatHistoryListStr)) {
      // 如果不为空
      chatHistoryList = JSON.parse(chatHistoryListStr);
    } else {
      // 如果为空，赋一个空的list
      chatHistoryList = [];
    }

    return chatHistoryList;
  },

  // 删除我和朋友的聊天记录
  deleteUserChatHistory: function(myId, friendId) {
    var chatKey = "chat-" + myId + "-" + friendId;
    plus.storage.removeItem(chatKey);
  },

  /**
   * 聊天记录的快照，仅仅保存每次和朋友聊天的最后一条消息
   * @param {Object} myId
   * @param {Object} friendId
   * @param {Object} msg
   * @param {Object} isRead
   */
  saveUserChatSnapshot: function(myId, friendId, msg, isRead) {
    var me = this;
    var chatKey = "chat-snapshot" + myId;

    // 从本地缓存获取聊天快照的list
    var chatSnapshotListStr = plus.storage.getItem(chatKey);
    var chatSnapshotList;
    if (me.isNotNull(chatSnapshotListStr)) {
      // 如果不为空
      chatSnapshotList = JSON.parse(chatSnapshotListStr);
      // 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
      for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
        if (chatSnapshotList[i].friendId == friendId) {
          // 删除已经存在的friendId所对应的快照对象
          chatSnapshotList.splice(i, 1);
          break;
        }
      }
    } else {
      // 如果为空，赋一个空的list
      chatSnapshotList = [];
    }

    // 构建聊天快照对象
    var singleMsg = new me.ChatSnapshot(myId, friendId, msg, isRead);

    // 向list中追加快照对象
    chatSnapshotList.unshift(singleMsg);

    plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
  },

  /**
   * 获取用户快照记录列表
   */
  getUserChatSnapshot: function(myId) {
    var me = this;
    var chatKey = "chat-snapshot" + myId;
    // 从本地缓存获取聊天快照的list
    var chatSnapshotListStr = plus.storage.getItem(chatKey);
    var chatSnapshotList;
    if (me.isNotNull(chatSnapshotListStr)) {
      // 如果不为空
      chatSnapshotList = JSON.parse(chatSnapshotListStr);
    } else {
      // 如果为空，赋一个空的list
      chatSnapshotList = [];
    }

    return chatSnapshotList;
  },

  /**
   * 删除本地的聊天快照记录
   * @param {Object} myId
   * @param {Object} friendId
   */
  deleteUserChatSnapshot: function(myId, friendId) {
    var me = this;
    var chatKey = "chat-snapshot" + myId;

    // 从本地缓存获取聊天快照的list
    var chatSnapshotListStr = plus.storage.getItem(chatKey);
    var chatSnapshotList;
    if (me.isNotNull(chatSnapshotListStr)) {
      // 如果不为空
      chatSnapshotList = JSON.parse(chatSnapshotListStr);
      // 循环快照list，并且判断每个元素是否包含（匹配）friendId，如果匹配，则删除
      for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
        if (chatSnapshotList[i].friendId == friendId) {
          // 删除已经存在的friendId所对应的快照对象
          chatSnapshotList.splice(i, 1);
          break;
        }
      }
    } else {
      // 如果为空，不做处理
      return;
    }

    plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
  },

  /**
   * 标记未读消息为已读状态
   * @param {Object} myId
   * @param {Object} friendId
   */
  readUserChatSnapshot: function(myId, friendId) {
    var me = this;
    var chatKey = "chat-snapshot" + myId;
    // 从本地缓存获取聊天快照的list
    var chatSnapshotListStr = plus.storage.getItem(chatKey);
    var chatSnapshotList;
    if (me.isNotNull(chatSnapshotListStr)) {
      // 如果不为空
      chatSnapshotList = JSON.parse(chatSnapshotListStr);
      // 循环这个list，判断是否存在好友，比对friendId，
      // 如果有，在list中的原有位置删除该 快照 对象，然后重新放入一个标记已读的快照对象
      for (var i = 0 ; i < chatSnapshotList.length ; i ++) {
        var item = chatSnapshotList[i];
        if (item.friendId == friendId) {
          item.isRead = true;		// 标记为已读
          chatSnapshotList.splice(i, 1, item);	// 替换原有的快照
          break;
        }
      }
      // 替换原有的快照列表
      plus.storage.setItem(chatKey, JSON.stringify(chatSnapshotList));
    } else {
      // 如果为空
      return;
    }
  },

  /**
   * 和后端的枚举对应
   */
  CONNECT: 1, 	// 第一次(或重连)初始化连接
  CHAT: 2, 		// 聊天消息
  SIGNED: 3, 		// 消息签收
  KEEPALIVE: 4, 	// 客户端保持心跳
  PULL_FRIEND:5,	// 重新拉取好友

  /**
   * 和后端的 ChatMsg 聊天模型对象保持一致
   * @param {Object} senderId
   * @param {Object} receiverId
   * @param {Object} msg
   * @param {Object} msgId
   */
  ChatMsg: function(senderId, receiverId, msg, msgId){
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.msg = msg;
    this.msgId = msgId;
  },

  /**
   * 构建消息 DataContent 模型对象
   * @param {Object} action
   * @param {Object} chatMsg
   * @param {Object} extand
   */
  DataContent: function(action, chatMsg, extand){
    this.action = action;
    this.chatMsg = chatMsg;
    this.extand = extand;
  },

  /**
   * 单个聊天记录的对象
   * @param {Object} myId
   * @param {Object} friendId
   * @param {Object} msg
   * @param {Object} flag
   */
  ChatHistory: function(myId, friendId, msg, flag){
    this.myId = myId;
    this.friendId = friendId;
    this.msg = msg;
    this.flag = flag;
  },

  /**
   * 快照对象
   * @param {Object} myId
   * @param {Object} friendId
   * @param {Object} msg
   * @param {Object} isRead	用于判断消息是否已读还是未读
   */
  ChatSnapshot: function(myId, friendId, msg, isRead){
    this.myId = myId;
    this.friendId = friendId;
    this.msg = msg;
    this.isRead = isRead;
  }

}
