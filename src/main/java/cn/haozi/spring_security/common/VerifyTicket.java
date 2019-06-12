package cn.haozi.spring_security.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 腾讯防水墙
 */
@Data
public class VerifyTicket implements Serializable {

    private String ticket;

    private String randstr;

    private String userIp;

    private String phone;

    private Integer reg;

    private String username;

    private String password;
}
