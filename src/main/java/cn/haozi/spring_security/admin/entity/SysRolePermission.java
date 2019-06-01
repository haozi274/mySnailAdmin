package cn.haozi.spring_security.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 17:47
 * @Description:
 */
@Data
public class SysRolePermission implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer roleId;

    private Integer perId;


}
