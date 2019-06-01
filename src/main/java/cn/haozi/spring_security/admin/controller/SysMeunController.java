package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.entity.SysMenu;
import cn.haozi.spring_security.admin.entity.SysRole;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.dto.SysMeunDTO;
import cn.haozi.spring_security.admin.entity.vo.TreeData;
import cn.haozi.spring_security.admin.entity.vo.TreeEntity;
import cn.haozi.spring_security.admin.service.SysMeunService;
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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 17:01
 * @Description:
 */

@Controller
@Slf4j
@RequestMapping("/sys/meun")
@AllArgsConstructor
@Api("系统菜单Api")
public class SysMeunController {

    private SysMeunService sysMeunService;

    @ApiIgnore
    @SysLog("跳转系统菜单页面")
    @GetMapping("/list")
    public String list(){
        return "views/admin/meun/list";
    }


    /**
     * 查询菜单列表
     * @return
     */
    @ApiOperation("查询菜单列表数据")
    @SysLog("查询菜单列表数据")
    @GetMapping("/treeList")
    @ResponseBody
    @RequiresPermissions("sys:menu:list")
    public TreeData<SysMenu> findAllList(){
        TreeData<SysMenu> treeEntity = new TreeData<>();
        IPage<SysMenu> page = sysMeunService.page(new Page<>(1,1000));
        treeEntity.setData(page.getRecords());
        treeEntity.setCount(page.getTotal());
        treeEntity.setCode(0);
        treeEntity.setMsg("ok");
        return treeEntity;
    }

    /***
     * 主页菜单显示
     * @return
     */
    @SysLog("主页菜单列表")
    @ApiOperation("主页菜单列表")
    @ResponseBody
    @GetMapping("/meun_list")
    public RestResponse seletMenuList(){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
         List<TreeEntity> list =  sysMeunService.findAllList(false);
         return RestResponse.success().setData(list);
    }

    @SysLog("跳转菜单列表")
    @ApiIgnore
    @GetMapping("/add")
    @RequiresPermissions("sys:menu:add")
    public String add(Model model,@RequestParam(value = "level",defaultValue = "0") int level ,
                      @RequestParam(value = "parent_id",defaultValue = "-1") int parentId,
                      @RequestParam(value = "parentName",defaultValue = "-1") String parentName){
        SysMeunDTO sysMenu = new SysMeunDTO();
        sysMenu.setLevel(level);
        sysMenu.setParentId(parentId);
        sysMenu.setParentName(parentName);
        model.addAttribute("sysMeun",sysMenu);
        return "views/admin/meun/add";
    }

    @SysLog("添加菜单")
    @ApiOperation("添加菜单列表")
    @PostMapping("/add")
    @RequiresPermissions("sys:menu:add")
    @ResponseBody
    public RestResponse add(@RequestBody SysMenu sysMenu){
        /**
         * 校验菜单名是否重复
         */
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("name",sysMenu.getName());
        List<SysMenu> exitRole =  sysMeunService.list(wrapper);
        if(exitRole.size()>0){
            return  RestResponse.failure("菜单名已存在");
        }
         boolean flag = sysMeunService.save(sysMenu);
        return flag?RestResponse.success():RestResponse.failure("保存失败");
    }

    @GetMapping("/edit")
    @RequiresPermissions("sys:menu:edit")
    @ApiIgnore
    @SysLog("跳转去菜单编辑页")
    public String edit(Integer id,Model model){

        SysMenu sysMenu =  sysMeunService.getById(id);
        /**
         * 查询父类
         */
        SysMenu psysMeun =  sysMeunService.getById(sysMenu.getParentId());
        SysMeunDTO sysMeunDTO = new SysMeunDTO();
        BeanUtil.copyProperties(sysMenu,sysMeunDTO);
        if (psysMeun != null) {
            sysMeunDTO.setParentName(psysMeun.getName());
        }
        model.addAttribute("sysMeun",sysMeunDTO);
        return "views/admin/meun/edit";
    }

    @ApiOperation("修改菜单")
    @PostMapping("/edit")
    @SysLog("修改菜单")
    @ResponseBody
    public RestResponse edit(@RequestBody SysMenu sysMenu){
        boolean flag = sysMeunService.updateById(sysMenu);
        return flag?RestResponse.success():RestResponse.failure("保存菜单失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    @SysLog("删除菜单")
    @ApiOperation("删除菜单")
    public RestResponse delete(@RequestParam(value = "id")  Integer id){
        /**
         * 删除此菜单的所有子菜单
         */
        boolean flag = sysMeunService.deleteById(id);
        return flag?RestResponse.success():RestResponse.failure("删除菜单失败");
    }
}
