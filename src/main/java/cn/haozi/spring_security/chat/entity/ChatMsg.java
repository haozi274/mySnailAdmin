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
public class ChatMsg   implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("send_id")
    private Integer sendId;

    @TableField("accept_id")
    private Integer acceptId;

    @TableField("msg")
    private String msg;

    @TableField("status")
    private Integer status;


}
