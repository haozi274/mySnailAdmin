package cn.haozi.spring_security.admin.entity.dto;

import cn.haozi.spring_security.admin.entity.SysUser;
import lombok.Data;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/15 09:57
 * @Description:
 */
    @Data
    public class SysUserDTO extends SysUser {

    private Integer[] roleList;
}
