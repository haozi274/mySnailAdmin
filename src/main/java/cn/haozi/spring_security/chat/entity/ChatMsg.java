package cn.haozi.spring_security.chat.entity;

import cn.haozi.spring_security.admin.entity.vo.DataEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 聊天信息
 */
@Data
@TableName("chat_msg")
public class ChatMsg   extends DataEntity<ChatMsg> implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer sendId;

    private Integer acceptId;

    private String msg;

    private Integer status;


}
