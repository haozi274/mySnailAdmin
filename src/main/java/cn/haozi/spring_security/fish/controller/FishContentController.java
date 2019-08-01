package cn.haozi.spring_security.fish.controller;

import cn.haozi.spring_security.admin.entity.Log;
import cn.haozi.spring_security.admin.entity.dto.SysLogDTO;
import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.fish.dto.FishContentDTO;
import cn.haozi.spring_security.fish.entity.FishContent;
import cn.haozi.spring_security.fish.service.FishContentService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 陈思浩
 * @Date: 2019/7/28 17:28
 * @Description:
 */
@Controller
@RequestMapping("/fish/content")
@AllArgsConstructor
public class FishContentController {

    private FishContentService fishContentService;

    @GetMapping("/list")
    public String index() {
        return "views/fish/content/list";
    }

    @PostMapping("/list")
    @ResponseBody
    public LayuiUtils<FishContent> list(@RequestBody FishContentDTO fishContentDTO) {
        QueryWrapper<FishContent> wrapper = new QueryWrapper<FishContent>();
        //排序
        wrapper.orderByDesc("create_time");
       IPage<FishContent> result = fishContentService.page(new Page<>(fishContentDTO.getPage(),fishContentDTO.getLimit()),wrapper);
        LayuiUtils<FishContent> layuiUtils = new LayuiUtils<>();
        layuiUtils.setData(result.getRecords());
        layuiUtils.setCount(result.getTotal());
        return layuiUtils;
    }

    @GetMapping("/add")
    public String add(){
        return "views/fish/content/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public  RestResponse add(@RequestBody FishContent fishContent){
        fishContentService.save(fishContent);
        return RestResponse.success();
    }

    @GetMapping("/edit")
    public String edit(int id,Model model){
        FishContent fishContent =  fishContentService.getById(id);
       model.addAttribute("fishContent",fishContent);
        return "views/fish/content/edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    public  RestResponse edit(@RequestBody FishContent fishContent){
        fishContentService.updateById(fishContent);
        return RestResponse.success();
    }

}