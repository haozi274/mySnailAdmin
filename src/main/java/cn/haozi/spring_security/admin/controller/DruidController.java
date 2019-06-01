package cn.haozi.spring_security.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:51
 * @Description: Druid数据源
 */
@Controller
@RequestMapping("api/druid")
@ApiIgnore
public class DruidController {

    @RequiresPermissions("sys:diurd:list")
    @RequestMapping("/index")
    public String druid(){
        return "view/admin/druid/index";
    }
}
