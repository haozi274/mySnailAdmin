package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.entity.SysTask;
import cn.haozi.spring_security.admin.entity.dto.SysTaskDTO;
import cn.haozi.spring_security.admin.service.SysTaskService;
import cn.haozi.spring_security.admin.utils.Constants;
import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.RestResponse;
import cn.haozi.spring_security.admin.utils.SysLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 定时任务
 */
@Controller
@RequestMapping("/sys/task")
@Slf4j
@AllArgsConstructor
public class SysTaskController {

    private SysTaskService taskServer;

    @GetMapping("/list")
    @ApiIgnore
    @SysLog("跳转去定时任务列表")
    public String list(){
        return "views/admin/task/list";
    }

    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("系统定时任务列表")
    public LayuiUtils<SysTask> list(@RequestBody SysTaskDTO sysTask) {
        LayuiUtils<SysTask> layuiUtils = new LayuiUtils<>();
        QueryWrapper<SysTask> wrapper = new QueryWrapper<>();
        if (sysTask != null) {
            if (StringUtils.isNotBlank(sysTask.getTaskName())) {
                wrapper.like("task_name", sysTask.getTaskName());
            }
        }
        wrapper.orderByAsc("create_time");
        IPage<SysTask> iPage = taskServer.page(new Page(sysTask.getPage(), sysTask.getLimit()), wrapper);
        //添加查询条件
        layuiUtils.setCount(iPage.getTotal());//数量
        layuiUtils.setData(iPage.getRecords());//数据
        return layuiUtils;
    }

    @GetMapping("/add")
    @ApiIgnore
    @SysLog("跳转去添加定时任务页面")
    public String add(){
        return "views/admin/task/add";
    }

    @RequiresPermissions("sys:user:list")
    @ApiOperation("添加任务")
    @SysLog(value = "添加任务")
    @PostMapping("/add")
    @ResponseBody
    public RestResponse add(@RequestBody SysTaskDTO taskDTO) {
        /**
         * 校验登录名是否重复
         */
        QueryWrapper<SysTask> wrapper = new QueryWrapper<>();
        wrapper.eq("task_name",taskDTO.getTaskName());
        List<SysTask> exitUset =  taskServer.list(wrapper);
        if(exitUset.size()>0){
            return  RestResponse.failure("任务名称已存在");
        }
        boolean flag = taskServer.save(taskDTO);

        return flag?RestResponse.success():RestResponse.failure("添加失败");
    }

}
