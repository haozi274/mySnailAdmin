package cn.haozi.spring_security.chat.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatData <T> implements Serializable {

    private T mine;

    private List<GroupName> friend;
}
