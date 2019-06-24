package cn.haozi.spring_security.chat.mapper;

import cn.haozi.spring_security.admin.entity.Log;
import cn.haozi.spring_security.chat.entity.ChatMsg;
import cn.haozi.spring_security.chat.entity.dto.ChatMsgDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatMsgMapper extends BaseMapper<ChatMsg> {

    void saveChatmsg(ChatMsg chatMsg);

    List<ChatMsgDTO> chatList(IPage page, @Param("ew") QueryWrapper<ChatMsg> entityWrapper);
}
