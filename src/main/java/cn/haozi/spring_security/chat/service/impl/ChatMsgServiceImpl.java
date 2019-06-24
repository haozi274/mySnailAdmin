package cn.haozi.spring_security.chat.service.impl;

import cn.haozi.spring_security.chat.entity.ChatMsg;
import cn.haozi.spring_security.chat.entity.dto.ChatMsgDTO;
import cn.haozi.spring_security.chat.mapper.ChatMsgMapper;
import cn.haozi.spring_security.chat.service.ChatMsgService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper,ChatMsg> implements ChatMsgService {
    public Page<ChatMsgDTO> selectAll(IPage page, QueryWrapper<ChatMsg> entityWrapper){

        return (Page<ChatMsgDTO>) page.setRecords(baseMapper.chatList(page,entityWrapper));
    }
}
