package cn.haozi.spring_security.admin.service;

import cn.haozi.spring_security.admin.entity.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:19
 * @Description:
 */
public interface LogService extends IService<Log> {
    Page<Log> selectAll(IPage page, QueryWrapper<Log> entityWrapper);
}
