package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.base.BaseController;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.vo.TreeEntity;
import cn.haozi.spring_security.admin.entity.vo.UserInfo;
import cn.haozi.spring_security.admin.exception.MyException;
import cn.haozi.spring_security.admin.service.SysMeunService;
import cn.haozi.spring_security.admin.service.SysUserService;
import cn.haozi.spring_security.admin.shior.MyRealm;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.admin.utils.SysLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/22 22:02
 * @Description:
 */
@Controller
@RequestMapping("/sys/login")
@Slf4j
@AllArgsConstructor
public class LoginController extends BaseController {

    @GetMapping("/index")
    public String index(){
        return "views/admin/users/login";
    }


    /**
     * 跳转去主页
     *
     * @return
     */
    @ApiIgnore
    @SysLog(value = "访问主页")
    @GetMapping("/main")
    public String index(Model model) {
        SysUser user2 = getCurrentUser();

        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",
                user2.getLoginName());
        SysUser user = sysUserService.getOne(wrapper);
        List<TreeEntity> meunlist =  sysMeunService.findByUserId(user.getId());
        UserInfo userInfo = sysUserService.findUserInfo(user2.getId());
         model.addAttribute("userInfo",userInfo);
        model.addAttribute("meunlist",meunlist);
        return "views/index";
    }

    //@SysLog("退出登录")
    @GetMapping("/syslogout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "/views/admin/users/login";
    }

    /**
     * 主页展示页面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping("/console")
    public String console() {

        return "views/home/console";
    }

    /***
     * 消息页面
     * @return
     */
    @ApiIgnore
    @RequestMapping("/main1")
    public String message() {
        return "views/admin/main/homepage2";
    }

    @ApiIgnore
    @RequestMapping("/401")
    public String un401() {
        return "views/admin/error/401";
    }



}
