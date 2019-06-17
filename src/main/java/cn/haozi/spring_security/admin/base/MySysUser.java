package cn.haozi.spring_security.admin.base;

import cn.haozi.spring_security.admin.shior.MyRealm;
import org.apache.shiro.SecurityUtils;

/**
 * 获取当前用户信息
 */
public class MySysUser {

    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Integer id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getloginName();
    }

    public static String nickName(){
        return ShiroUser().getNickName();
    }

    public static MyRealm.ShiroUser ShiroUser() {
        MyRealm.ShiroUser user = (MyRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}
