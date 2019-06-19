package cn.haozi.spring_security.chat.service;

import cn.haozi.spring_security.chat.entity.ChatData;
import cn.haozi.spring_security.chat.entity.ChatUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysFriendService  {

    ChatData<ChatUser> selectFriends(ChatUser chatUser);
}
