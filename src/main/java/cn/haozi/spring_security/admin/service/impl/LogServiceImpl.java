package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.Log;
import cn.haozi.spring_security.admin.mapper.LogMapper;
import cn.haozi.spring_security.admin.service.LogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:19
 * @Description:
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {


    public Page<Log> selectAll(IPage page,  QueryWrapper<Log> entityWrapper){

        return (Page<Log>) page.setRecords(baseMapper.selectAll(page,entityWrapper));
    }
}
