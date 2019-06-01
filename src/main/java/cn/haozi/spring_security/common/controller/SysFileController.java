package cn.haozi.spring_security.common.controller;

import cn.haozi.spring_security.admin.entity.SysDictionary;
import cn.haozi.spring_security.admin.entity.SysFile;
import cn.haozi.spring_security.admin.entity.SysUser;
import cn.haozi.spring_security.admin.entity.dto.SysFileDTO;
import cn.haozi.spring_security.admin.service.SysFileService;
import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.RestResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/sys/file")
@AllArgsConstructor
@Api("系统文件Api")
public class SysFileController {

    private SysFileService sysFileService;

    @GetMapping("/list")
    public String list(){
        return "views/admin/file/list";
    }

    @PostMapping("/list")
    @ResponseBody
    public LayuiUtils list(@RequestBody SysFileDTO sysFile){
        QueryWrapper<SysFile> wrapper = new QueryWrapper<SysFile>();
        IPage<SysFile> iPage =  sysFileService.page(new Page<SysFile>(sysFile.getPage(),sysFile.getLimit()),wrapper);
        LayuiUtils<SysFile> layuiUtils = new LayuiUtils<SysFile>();
        layuiUtils.setCount(iPage.getTotal());//数量
        layuiUtils.setData(iPage.getRecords());//数据
        return layuiUtils;
    }


}
