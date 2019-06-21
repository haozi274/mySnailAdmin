package cn.haozi.spring_security.chat.mapper;

import cn.haozi.spring_security.chat.entity.ChatMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ChatMsgMapper extends BaseMapper<ChatMsg> {

    void saveChatmsg(ChatMsg chatMsg);
}
