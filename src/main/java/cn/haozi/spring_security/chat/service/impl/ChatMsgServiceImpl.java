package cn.haozi.spring_security.chat.service.impl;

import cn.haozi.spring_security.chat.entity.ChatMsg;
import cn.haozi.spring_security.chat.mapper.ChatMsgMapper;
import cn.haozi.spring_security.chat.service.ChatMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper,ChatMsg> implements ChatMsgService {
}
