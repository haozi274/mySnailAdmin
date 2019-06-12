package cn.haozi.spring_security.admin.controller;

import cn.haozi.spring_security.admin.entity.Log;
import cn.haozi.spring_security.admin.entity.dto.SysLogDTO;
import cn.haozi.spring_security.admin.service.LogService;
import cn.haozi.spring_security.admin.utils.ExcelUtil;
import cn.haozi.spring_security.admin.utils.LayuiUtils;
import cn.haozi.spring_security.admin.utils.SysLog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 13:23
 * @Description:
 */
@Controller
@RequestMapping("/sys/log")
@AllArgsConstructor
@Slf4j
public class LogController {

    private LogService logService;

    @ApiIgnore
    @SysLog("查看操作日志")
    @GetMapping("/list")
    public String list(){
        return "views/admin/log/list";
    }

    @SysLog("查询日志数据")
    @PostMapping("/list")
    @ResponseBody
    public LayuiUtils<Log> list(@RequestBody SysLogDTO sysLogDTO){

        IPage<Log> page = new Page<>();
        page.setCurrent(1); // 当前页
        page.setSize(10); // 页大小
        QueryWrapper<Log> wrapper =new QueryWrapper<>();
        //排序
        wrapper.orderByDesc("create_time");
        LayuiUtils<Log> data = new LayuiUtils<>();
        Page<Log>list = logService.selectAll(page,wrapper);
        data.setData(list.getRecords());
        data.setCount(list.getTotal());
        return  data;
    }

    /**
     * 导出
     * @param response
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            String name = "日志";
            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat sdf_ymd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatDate_ymd = sdf_ymd.format(date);
            // 设置文件名
            String fileName = formatDate_ymd + name;
            String sheetName = "数据展示";

            // 按条件筛选records
            List<Log> list = logService.list();
            // easyexcel工具类实现Excel文件导出
            ExcelUtil.writeExcel(response, list, fileName, sheetName,new Log());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
