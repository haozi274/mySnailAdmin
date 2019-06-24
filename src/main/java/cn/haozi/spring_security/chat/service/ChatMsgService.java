package cn.haozi.spring_security.chat.service;

import cn.haozi.spring_security.chat.entity.ChatMsg;
import cn.haozi.spring_security.chat.entity.dto.ChatMsgDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ChatMsgService extends IService<ChatMsg> {
    Page<ChatMsgDTO> selectAll(IPage page, QueryWrapper<ChatMsg> entityWrapper);
}
