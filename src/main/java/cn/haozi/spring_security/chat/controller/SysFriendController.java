package cn.haozi.spring_security.chat.controller;

import cn.haozi.spring_security.admin.base.BaseController;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.mapper.SysUserMapper;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.chat.entity.ChatMsg;
import cn.haozi.spring_security.chat.entity.ChatUser;
import cn.haozi.spring_security.chat.entity.dto.ChatMsgDTO;
import cn.haozi.spring_security.chat.service.ChatMsgService;
import cn.haozi.spring_security.chat.service.SysFriendService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonObject;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询好友列表
 */
@AllArgsConstructor
@Controller
@RequestMapping("/sys/chat")
public class SysFriendController extends BaseController {

    private SysFriendService sysFriendService;

    private ChatMsgService chatMsgService;

    private SysUserMapper sysUserMapper;

    @GetMapping("/list")
    @ResponseBody
    public RestResponse list(ChatUser chatUser){
       SysUser user =  getCurrentUser();
        chatUser.setId(user.getId());
        RestResponse data = new RestResponse();
        data.setData(sysFriendService.selectFriends(chatUser));
        data.setCode(0);
        return data;
    }

    @GetMapping("/index")
    public String list(){
        return "/views/admin/chat/index";
    }

    /**
     * 添加聊天记录
     * @param chatMsg
     * @return
     */
    @PostMapping("/addChat")
    @ResponseBody
    public RestResponse add(@RequestBody ChatMsg chatMsg){
        chatMsgService.save(chatMsg);
        return RestResponse.success();
    }

    /**
     * 聊天记录页面
     * @return
     */
    @GetMapping("/chatList")
    public String chatList(Integer id, Model model){
        model.addAttribute("acceptId",id);
        return "views/admin/chat/list";
    }

    @GetMapping("chatlog")
    @ResponseBody
    public Object chatlog( ChatMsgDTO chatMsgDTO){
        int acceptId = getCurrentUser().getId();
        QueryWrapper<ChatMsg> wrapper = new QueryWrapper<>();
        wrapper.in("send_id",chatMsgDTO.getId(),acceptId);
        wrapper.in("accept_id",chatMsgDTO.getId(),acceptId);
         IPage page =  chatMsgService.selectAll(new Page<>(chatMsgDTO.getPage(),chatMsgDTO.getSize()),wrapper);

        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",page.getRecords());
        map.put("page",page.getCurrent());
        map.put("size",page.getSize());
        map.put("total",page.getTotal());

        return map;
    }

    /**
     * 搜索好友
     */
    @GetMapping("/searchFirend")
    @ResponseBody
     public RestResponse searchFirend(String username){
         List<ChatUser> list =    sysUserMapper.selectChatUserList(username);
         RestResponse data = new RestResponse();
         data.setData(list);
         return data;
     }

     @GetMapping("/find")
     public String find(){
        return "views/admin/chat/find";
     }

    /**
     * 同意好友
     */


}
