package cn.haozi.spring_security.fish.controller;

import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.fish.dto.FishCategoryDTO;
import cn.haozi.spring_security.fish.entity.FishCategory;
import cn.haozi.spring_security.fish.entity.FishContent;
import cn.haozi.spring_security.fish.service.FishCategoryService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/31 13:33
 * @Description:
 */
@AllArgsConstructor
@Controller
@RequestMapping("/fish/category")
public class FishCategoryController {

    private FishCategoryService fishCategoryService;

    @GetMapping("/list")
    public String index(){

        return "views/fish/category/list";
    }

    @GetMapping("/find_list")
    @ResponseBody
    public LayuiUtils<FishCategory> list() {
        QueryWrapper<FishCategory> wrapper = new QueryWrapper<FishCategory>();
        //排序
        wrapper.orderByDesc("create_time");
        IPage<FishCategory> result = fishCategoryService.page(new Page<>(1,1000),wrapper);

        return LayuiUtils.data(result.getRecords(),result.getTotal(),"操作成功");
    }


    @GetMapping("/add")
    public String edit(@RequestParam(value = "parent_id",defaultValue = "-1") int parentId,
                       @RequestParam(value = "parentName",defaultValue = "-1") String parentName,
                       Model model){
        FishCategoryDTO fishCategory = new FishCategoryDTO();
        fishCategory.setParentId(parentId);
        fishCategory.setParentName(parentName);
        model.addAttribute("fishContent",fishCategory);
        return "views/fish/category/add";
    }


    @PostMapping("/add")
  //  @RequiresPermissions("sys:menu:add")
    @ResponseBody
    public RestResponse add(@RequestBody FishCategory fishCategory){
        /**
         * 类型是否重复
         */
        QueryWrapper<FishCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("type",fishCategory.getType());
        List<FishCategory> exitRole =  fishCategoryService.list(wrapper);
        if(exitRole.size()>0){
            return  RestResponse.failure("类型已存在");
        }
        boolean flag = fishCategoryService.save(fishCategory);
        return flag?RestResponse.success():RestResponse.failure("保存失败");
    }
    @GetMapping("/edit")
    @RequiresPermissions("sys:menu:edit")

    public String edit(Integer id,Model model){
        FishCategory fishCategory =  fishCategoryService.getById(id);
        /**
         * 查询父类
         */
        FishCategory fishCategory1 =  fishCategoryService.getById(fishCategory.getParentId());
        FishCategoryDTO sysMeunDTO = new FishCategoryDTO();
        BeanUtil.copyProperties(fishCategory1,sysMeunDTO);
        if (fishCategory1 != null) {
            sysMeunDTO.setParentName(fishCategory1.getType());
        }
        model.addAttribute("fishCategory",sysMeunDTO);
        return "views/fish/category/edit";
    }


    @PostMapping("/edit")
    @ResponseBody
    public RestResponse edit(@RequestBody FishCategory fishCategory){
        boolean flag = fishCategoryService.updateById(fishCategory);
        return flag?RestResponse.success():RestResponse.failure("保存菜单失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    @RequiresPermissions("sys:menu:delete")
    public RestResponse delete(@RequestParam(value = "id")  Integer id){
        /**
         * 删除此菜单的所有子菜单
         */
        boolean flag = fishCategoryService.removeById(id);
        return flag?RestResponse.success():RestResponse.failure("删除菜单失败");
    }
}
