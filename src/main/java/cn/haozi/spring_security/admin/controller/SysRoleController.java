package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.SysRolePermission;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.dto.SsysUserDTO;
import cn.haozi.spring_security.admin.entity.dto.SysRoleDTO;
import cn.haozi.spring_security.admin.entity.vo.TreeEntity;
import cn.haozi.spring_security.admin.entity.vo.TreeRolePermission;
import cn.haozi.spring_security.admin.mapper.SysRolePermissionMapper;
import cn.haozi.spring_security.admin.service.SysMeunService;
import cn.haozi.spring_security.admin.service.SysRolePermissionService;
import cn.haozi.spring_security.admin.service.SysRoleService;
import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.admin.utils.SysLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoleilu.hutool.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/14 15:11
 * @Description:
 */
@Controller
@RequestMapping("/sys/role")
@Slf4j
@Api("系统角色管理")
@AllArgsConstructor
public class SysRoleController {

    private SysRoleService sysRoleService;

    private SysMeunService sysMeunService;

    private SysRolePermissionMapper sysRolePermissionMapper;

    private SysRolePermissionService sysRolePermissionService;

    @GetMapping("/list")
    @ApiIgnore
    @SysLog("跳转去角色列表")
    public String list(){
        return "views/admin/role/list";
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("系统角色列表")
    public LayuiUtils<SysRole> list(@RequestBody SysRoleDTO sysRoleDTO) {
        //   Map map = WebUtils.getParametersStartingWith(request, "s_");
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (sysRoleDTO != null) {
            if (StringUtils.isNotBlank(sysRoleDTO.getName())) {
                wrapper.like("name", sysRoleDTO.getName());
            }
        }
        wrapper.orderByAsc("create_time");
        IPage<SysRole> iPage = sysRoleService.page(new Page(sysRoleDTO.getPage(), sysRoleDTO.getLimit()), wrapper);
        //添加查询条件
        LayuiUtils<SysRole> layuiUtils = new LayuiUtils<>();
        layuiUtils.setCount(iPage.getTotal());//数量
        layuiUtils.setData(iPage.getRecords());//数据
        return layuiUtils;
    }

    @ApiIgnore
    @SysLog("跳转角色添加页面")
    @GetMapping("/add")
    public String add(Model model){
        /***
         * 查出菜单权限树
         */
        List<TreeEntity>  list = sysMeunService.findAllList(true);
        model.addAttribute("treeList",list);
        return "views/admin/role/add";
    }

    @RequiresPermissions("sys:role:add")
    @SysLog("角色添加")
    @PostMapping("/add")
    @ResponseBody
    public RestResponse add(@RequestBody SysRoleDTO sysRole){
        /**
         * 校验角色名是否重复
         */
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("name",sysRole.getName());
        List<SysRole> exitRole =  sysRoleService.list(wrapper);
        if(exitRole.size()>0){
            return  RestResponse.failure("角色名已存在");
        }
       boolean flag =  sysRoleService.saveRole(sysRole);
        return flag?RestResponse.success():RestResponse.failure("保存失败");
    }


    @ApiIgnore
    @SysLog("跳转角色编辑页面")
    @GetMapping("/edit")
    public String edit(int id, Model model){
        /***
         * 查出菜单权限树
         */
        List<TreeEntity>  list = sysMeunService.findAllList(true);
        model.addAttribute("treeList",list);
        QueryWrapper<SysRolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        List<SysRolePermission> rlList = sysRolePermissionService.list(wrapper);
        List<TreeRolePermission> trpList = new ArrayList<>();

        getTreeList(list,rlList,trpList) ;
        SysRole sysRole =  sysRoleService.getById(id);
        model.addAttribute("sysRole",sysRole);
        model.addAttribute("trpList",trpList);
        return "views/admin/role/edit";
    }

    @RequiresPermissions("sys:role:delete")
    @SysLog("角色删除")
    @PostMapping("/delete_by_id")
    @ResponseBody
    public RestResponse delete(@RequestParam(value = "id") int id){

        boolean flag = sysRoleService.deleteRole(id);
        return flag?RestResponse.success():RestResponse.failure("角色删除失败");
    }


    @RequiresPermissions("sys:role:edit")
    @ApiIgnore
    @SysLog("角色编辑")
    @PostMapping("/edit")
    @ResponseBody
    public RestResponse edit(@RequestBody SysRoleDTO sysRole){
        /***
         * 修改角色和权限
         */
        boolean flag = sysRoleService.updateRoleById(sysRole);

        return flag?RestResponse.success():RestResponse.failure("保存失败");
    }

    /**
     * 递归遍历
     * @param treeEntityList 菜单树
     * @param rlList 角色与权限
     * @param trpList  角色与权限树
     */
    public void getTreeList(List<TreeEntity> treeEntityList,List<SysRolePermission> rlList,List<TreeRolePermission> trpList){
        /***
         *
         */
        for (TreeEntity entity:treeEntityList) {
            /**
             * 判断有没有子类
             */
            TreeRolePermission treeRolePermission = new TreeRolePermission();
            BeanUtil.copyProperties(entity,treeRolePermission,new String[]{"list"});

            if (entity.getList().size()>0){
                getTreeList(entity.getList(),rlList,treeRolePermission.getList());
            }
            for (SysRolePermission tp:rlList) {
                if (entity.getId().equals(tp.getPerId())) {
                    treeRolePermission.setStatus("1");
                }
            }
            trpList.add(treeRolePermission);
        }
    }

}
