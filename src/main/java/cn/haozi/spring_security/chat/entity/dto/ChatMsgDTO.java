package cn.haozi.spring_security.chat.entity.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 聊天记录
 */
@Data
public class ChatMsgDTO implements Serializable {

    private Integer id;

    private String username;

    private String avatar;
    private String timestamp;

    private String content;

    private Integer page =1;

    private Integer size =10;



}
