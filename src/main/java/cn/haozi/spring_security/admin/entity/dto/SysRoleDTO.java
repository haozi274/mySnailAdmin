package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 15:36
 * @Description:
 */
@Data
public class SysRoleDTO extends SysRole {

    private Integer page;

    private Integer limit;

    private Integer[] permissionList;
}
