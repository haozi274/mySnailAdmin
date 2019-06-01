package cn.haozi.spring_security.admin.base;

import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.service.SysMeunService;
import cn.haozi.spring_security.admin.service.SysRoleService;
import cn.haozi.spring_security.admin.service.SysUserRoleService;
import cn.haozi.spring_security.admin.service.SysUserService;
import cn.haozi.spring_security.admin.shior.MyRealm;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 公用的控制器
 */
public class BaseController {
    @Autowired
    protected SysUserService sysUserService;
    @Autowired
    protected SysMeunService sysMeunService;
    @Autowired
    protected SysRoleService sysRoleService;
    @Autowired
    protected SysUserRoleService sysUserRoleService;

    public SysUser getCurrentUser() {
        MyRealm.ShiroUser shiroUser = (MyRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        if(shiroUser == null) {
            return null;
        }
        SysUser loginUser = sysUserService.getById(shiroUser.getId());
        return loginUser;
    }
}
