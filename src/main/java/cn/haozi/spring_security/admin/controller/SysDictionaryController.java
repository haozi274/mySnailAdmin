package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.entity.SysDictionary;
import cn.haozi.spring_security.admin.entity.dto.SysDictionaryDTO;
import cn.haozi.spring_security.admin.service.SysDictionaryService;
import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.admin.utils.SysLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/16 21:40
 * @Description:
 */
@Slf4j
@Controller
@RequestMapping("/sys/dictionary")
@AllArgsConstructor
public class SysDictionaryController {

    private SysDictionaryService sysDictionaryService;


    @ApiIgnore
    @GetMapping("/list")
    @SysLog("跳转去字典数据页面")
    public String list(){
        return  "views/admin/dictionary/list1";
    }

    @RequiresPermissions("sys:dic:list")
    @ResponseBody
    @SysLog("查询字典数据")
    @GetMapping("findAllList")
    public LayuiUtils<SysDictionary> list( SysDictionaryDTO sysDictionaryDTO){
        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sysDictionaryDTO.getType())){
            wrapper.eq("type",sysDictionaryDTO.getType());
        }
        wrapper.orderByAsc("create_time");
        IPage<SysDictionary> page =  sysDictionaryService.page(new Page<>(sysDictionaryDTO.getPage(),sysDictionaryDTO.getLimit()),wrapper);
        LayuiUtils layuiUtils = new LayuiUtils();
        layuiUtils.setCount(page.getTotal());
        layuiUtils.setData(page.getRecords());
        return layuiUtils;
    }

    @GetMapping("/add")
    @SysLog("跳转去添加字典页面")
    @ApiIgnore
    public String add(Model model, String type,Integer parentId){
        SysDictionary sysDictionary = new SysDictionary();
        if (type == null) {
            sysDictionary.setType("");
            sysDictionary.setParentId(-1);
        }else{
            sysDictionary.setType(type);
            sysDictionary.setParentId(parentId);
        }
        model.addAttribute("sysDictionary",sysDictionary);
        return "views/admin/dictionary/add";
    }


    @RequiresPermissions("sys:dic:add")
    @PostMapping("/add")
    @ResponseBody
    @SysLog("添加字典数据")
    public RestResponse add(@RequestBody SysDictionary dictionary){
        boolean flag = sysDictionaryService.save(dictionary);

        return flag ?RestResponse.success():RestResponse.failure("添加失败");
    }

    @RequiresPermissions("sys:dic:list")
    @PostMapping("/delete_by_id")
    @ResponseBody
    @SysLog("删除字典数据")
    public RestResponse delete(@RequestParam(value = "id") Integer id){
        boolean flag = sysDictionaryService.removeById(id);

        return flag ?RestResponse.success():RestResponse.failure("删除失败");
    }


    @GetMapping("edit")
    @SysLog("跳转去编辑字典页面")
    @ApiIgnore
    public String edit(Model model,int id)
    {
        SysDictionary sysDictionary  = sysDictionaryService.getById(id);
        model.addAttribute("sysDictionary",sysDictionary);
        return "views/admin/dictionary/edit";
    }


    @RequiresPermissions("sys:dic:edit")
    @PostMapping("/edit")
    @ResponseBody
    @SysLog("修改字典数据")
    public RestResponse edit(@RequestBody SysDictionary dictionary){
        boolean flag = sysDictionaryService.updateById(dictionary);
        return flag ?RestResponse.success():RestResponse.failure("修改失败");
    }

}
