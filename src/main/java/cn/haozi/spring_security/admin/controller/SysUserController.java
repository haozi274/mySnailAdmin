package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.base.BaseController;
import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.SysUserRole;
import cn.haozi.spring_security.admin.entity.dto.SsysUserDTO;
import cn.haozi.spring_security.admin.entity.dto.SysUserDTO;
import cn.haozi.spring_security.admin.entity.vo.UserInfo;

import cn.haozi.spring_security.admin.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoleilu.hutool.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * @Auther: 陈思浩
 * @Date: 2019/4/6 10:44
 * @Description:
 */

@Controller
@RequestMapping("api/user")
@AllArgsConstructor
@Slf4j
@Api("系统用户Api")
public class SysUserController extends BaseController {



    /***
     * 用户列表
     * @param
     * @param
     * @return
     */
    @RequiresPermissions("sys:user:list")
    @SysLog(value = "访问用户数据列表")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @ApiImplicitParam(name = "page", value = "page")
    public LayuiUtils<SysUser> list(@RequestBody SsysUserDTO sysUser) {
        //   Map map = WebUtils.getParametersStartingWith(request, "s_");
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (sysUser != null) {
            if (StringUtils.isNotBlank(sysUser.getUsername())) {
                wrapper.like("username", sysUser.getUsername());
            }
        }
        wrapper.orderByAsc("create_time");
        IPage<SysUser> iPage = sysUserService.selectPageVo(new Page(sysUser.getPage(), sysUser.getLimit()), wrapper);
        //添加查询条件
        LayuiUtils<SysUser> layuiUtils = new LayuiUtils<>();
        layuiUtils.setCount(iPage.getTotal());//数量
        layuiUtils.setData(iPage.getRecords());//数据
        return layuiUtils;
    }

    /***
     * 列表
     * @return
     */
    @ApiIgnore
    @SysLog(value = "系统用户列表")
    @GetMapping("list")
    public String list() {
        return "views/admin/users/list";
    }



    /**
     * 跳转去添加页面
     *
     * @return
     */
    @ApiIgnore
    @SysLog(value = "跳转去添加用户页面")
    @GetMapping("/add")
    public String add(Model model) {
        List<SysRole> roleList =  sysRoleService.list();
        model.addAttribute("roleList",roleList);
        return "views/admin/users/add";
    }

    /**
     * 保存用户
     *
     * @param sysUser
     * @return
     */
    @RequiresPermissions("sys:user:list")
    @ApiOperation("添加系统用户")
    @SysLog(value = "添加系统用户")
    @PostMapping("/add")
    @ResponseBody
    public RestResponse add(@RequestBody SysUserDTO sysUser) {
        /**
         * 校验登录名是否重复
         */
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name",sysUser.getLoginName());
        List<SysUser> exitUset =  sysUserService.list(wrapper);
        sysUser.setPassword(Constants.DEFAULT_PASSWORD);
        if(exitUset.size()>0){
            return  RestResponse.failure("登录名已存在");
        }
        boolean flag = sysUserService.saveUser(sysUser);

        return flag?RestResponse.success():RestResponse.failure("添加失败");
    }

    /***
     * 删除用户
     * @param id
     * @return
     */
    @RequiresPermissions("sys:user:delete")
    @ApiOperation("删除系统用户")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Integer")
    @SysLog(value = "删除系统用户")
    @PostMapping("/delete_by_id")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id") Integer id) {
        if (sysUserService.removeUserById(id)) {
            return RestResponse.success();
        } else {
            return RestResponse.failure("删除失败");
        }
    }

    /**
     * 修改
     *
     * @param
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @ApiOperation("修改系统用户")
    @ApiImplicitParam(name = "sysUser", value = "sysUser", required = true, dataType = "SysUser")
    @SysLog(value = "修改系统用户")
    @PostMapping("/edit")
    @ResponseBody
    public RestResponse edit(@RequestBody SysUserDTO sysUserDTO) {

        boolean flag = sysUserService.updateUserById(sysUserDTO);
        return flag ? RestResponse.success() : RestResponse.failure("修改失败");
    }

    /**
     * 跳转修改
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("sys:user:edit")
    @ApiIgnore
    @SysLog(value = "跳转去用户修改页面")
    @GetMapping("/edit")
    public String edit(Integer id, Model model) {
       //查询所有角色
        List<SysRole> roleList =  sysRoleService.list();
        //查询用户
        SysUser sysUser = sysUserService.findById(id);
        //查询
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id);
        List<SysUserRole> userRoleList =  sysUserRoleService.list(wrapper);
        /***
         * 需要判断角色是否被选
         */
        for (SysUserRole sysUserRole:userRoleList) {
            for (SysRole sysRole :roleList) {
                if (sysUserRole.getRoleId() == sysRole.getId()) {
                    sysRole.setStatus(1);
                }
            }
        }

        model.addAttribute("roleList",roleList);
        if (sysUser != null) {
            model.addAttribute("sysUser", sysUser);
        }
        return "views/admin/users/edit";
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
    @RequestMapping("/message")
    public String message() {
        return "views/app/message/index";
    }

    @ApiIgnore
    @SysLog("基本资料")
    @RequestMapping("/userinfo")
    public String userinfo(Model model){
         SysUser user2 = getCurrentUser();
         UserInfo userInfo = sysUserService.findUserInfo(user2.getId());
         List<SysRole> list =  sysRoleService.findByRoleId(user2.getId());
         userInfo.setRoles(list);
         model.addAttribute("userInfo",userInfo);
        return "views/admin/users/info";
    }

    @ApiIgnore
    @SysLog("跳转修改密码")
    @RequestMapping("/updatepwd")
    public String updatepwd(Model model){
        SysUser sysUser = getCurrentUser();
        model.addAttribute("userInfo",sysUser);
        return "views/admin/users/password";
    }

    @SysLog("修改用户密码")
    @ResponseBody
    @PostMapping("/update_pwd")
    public RestResponse update_pwd(@RequestBody SysUser sysUser){
        /***
         * 为了不再表单中暴露太多信息直接从shior中获取用户信息
         */
        SysUser user = getCurrentUser();
        user.setPassword(sysUser.getPassword());
        /**
         * 密码加密 对密码进行加密加盐
         */
        ToolUtil.entryptPassword(user);
        sysUserService.updateById(user);
        return RestResponse.success();
    }

    @SysLog("修改用户基本信息")
    @PostMapping("/user_info_edit")
    @ResponseBody
    public RestResponse userInfoEdit(@RequestBody SysUser sysUser){
        SysUser user = sysUserService.getById(sysUser.getId());
        user.setUsername(sysUser.getUsername());
        user.setMail(sysUser.getMail());
        user.setTelephone(sysUser.getTelephone());
        user.setRemark(sysUser.getRemark());
        user.setIcon(sysUser.getIcon());
        sysUserService.updateById(user);
        return RestResponse.success();
    }
}
