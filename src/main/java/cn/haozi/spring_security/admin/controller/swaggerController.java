package cn.haozi.spring_security.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/***
 * 接口文档
 */
@Controller
@RequestMapping("/sys/swagger")
@ApiIgnore
public class swaggerController {

    @GetMapping("/index")
    @RequiresPermissions("sys:swagger:list")
    public String index(){
        return "views/admin/swagger/list";
    }
}
