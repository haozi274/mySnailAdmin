package cn.haozi.spring_security.admin.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/28 13:57
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/sys/message")
@AllArgsConstructor
public class SysMessageController {

    @GetMapping("/index")
    public String index(){
        return "views/admin/message/index";
    }

}
