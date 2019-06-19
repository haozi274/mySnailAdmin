package cn.haozi.spring_security.chat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分组名称
 */
@Data
public class GroupName implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 分组名称
     */
    @JsonProperty(value = "groupname")
    private String groupName;
    /**
     * 创建分组用户
     */
    private Integer userId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 用户列表
     */
    private List<ChatUser>  list;

}
