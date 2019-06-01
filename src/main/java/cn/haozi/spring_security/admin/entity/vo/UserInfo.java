package cn.haozi.spring_security.admin.entity.vo;

import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.SysUser;
import lombok.Data;

import java.util.List;
@Data
public class UserInfo extends SysUser {

    private List<SysRole> roles;
    /**
     * 显示头像
     */
    private String iconString;

}
