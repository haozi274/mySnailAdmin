package cn.haozi.spring_security.chat.controller;

import cn.haozi.spring_security.admin.base.BaseController;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.chat.entity.ChatUser;
import cn.haozi.spring_security.chat.service.SysFriendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 查询好友列表
 */
@AllArgsConstructor
@Controller
@RequestMapping("/sys/chat")
public class SysFriendController extends BaseController {

    private SysFriendService sysFriendService;

    @GetMapping("/list")
    @ResponseBody
    public RestResponse list(ChatUser chatUser){
       /* SysUser user =  getCurrentUser();
        chatUser.setId(user.getId());*/
        RestResponse data = new RestResponse();
        data.setData(sysFriendService.selectFriends(chatUser));
        data.setCode(0);
        return data;
    }

    @GetMapping("/index")
    public String list(){
        return "/views/admin/chat/index";
    }
}
