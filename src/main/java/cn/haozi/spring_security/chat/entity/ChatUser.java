package cn.haozi.spring_security.chat.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatUser implements Serializable {

    private Integer id;

    private String username;
    /**
     * 个性签名
     */
    private String sign;
    /**
     * 头像
     */
    private String  avatar;


}
