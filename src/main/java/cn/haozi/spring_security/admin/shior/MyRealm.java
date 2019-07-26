package cn.haozi.spring_security.admin.shior;

import cn.haozi.spring_security.admin.entity.SysMenu;
import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.service.SysMeunService;
import cn.haozi.spring_security.admin.service.SysRoleService;
import cn.haozi.spring_security.admin.service.SysUserService;
import cn.haozi.spring_security.admin.utils.Constants;
import cn.haozi.spring_security.admin.utils.Encodes;
import cn.haozi.spring_security.admin.utils.JwtToken;
import cn.haozi.spring_security.admin.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/22 21:05
 * @Description:
 */
@AllArgsConstructor
@Component("myRealm")
public class MyRealm extends AuthorizingRealm {
    @Lazy
    private SysUserService sysUserService;
    @Lazy
    private SysRoleService sysRoleService;
    @Lazy
    private SysMeunService sysMeunService;


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        /***
         * 获取用户信息查询相关的权限
         */
        ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",shiroUser.getloginName());
        SysUser user = sysUserService.getOne(wrapper);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获得该用户角色
        //每个角色拥有默认的权限
        //每个用户可以设置新的权限
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        List<SysRole> roleList =  sysRoleService.findByRoleId(user.getId());
        roleList.stream().forEach(role->{
            roleSet.add(role.getName());
            List<SysMenu> menuList = sysMeunService.findMenuByRoleId(role.getId());
            menuList.stream().forEach(meun->{
                /***
                 * 0 :不显示的都是权限配置
                 */
                if (meun.getIsShow().equals(0)){
                    if (!"".equals(meun.getPermission())) {
                        permissionSet.add(meun.getPermission());
                    }

                }

            });
        });

        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = (String)token.getPrincipal();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",username);
        SysUser user = sysUserService.getOne(wrapper);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if(Boolean.TRUE.equals(user.getStatus())) {
            throw new LockedAccountException(); //帐号锁定
        }
        byte[] salt = Encodes.decodeHex(user.getSalt());
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        sysUser.setLoginName(username);
        sysUser.setPassword(user.getPassword());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(new ShiroUser(user.getId(),user.getLoginName(),user.getUsername()),
                user.getPassword(), //密码
                ByteSource.Util.bytes(salt),
                getName()  //realm name
        );

        return authenticationInfo;
    }

    public void removeUserAuthorizationInfoCache(String username) {
        SimplePrincipalCollection pc = new SimplePrincipalCollection();
        pc.add(username, super.getName());
        super.clearCachedAuthorizationInfo(pc);
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    public void setUserService(SysUserService userService) {
        this.sysUserService = userService;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Integer id;
        public String loginName;
        public String nickName;
        public String icon;

        public ShiroUser(Integer id, String loginName, String nickName) {
            this.id = id;
            this.loginName = loginName;
            this.nickName = nickName;
        }

        public String getloginName() {
            return loginName;
        }
        public String getNickName() {
            return nickName;
        }
        public String getIcon() {
            return icon;
        }
        public Integer getId() {
            return id;
        }



        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return nickName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null) {
                return other.loginName == null;
            } else return loginName.equals(other.loginName);
        }
    }

}
