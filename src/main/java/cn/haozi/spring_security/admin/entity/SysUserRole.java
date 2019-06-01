package cn.haozi.spring_security.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/15 09:50
 * @Description: 用户与角色
 */
@Data
public class SysUserRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer roleId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作时间
     */
    private Date operateTime;

    /***
     *
     * 操作地址
     */
    private String operateIp;
}
