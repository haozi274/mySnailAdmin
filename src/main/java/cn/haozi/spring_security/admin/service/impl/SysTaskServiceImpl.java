package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.SysTask;
import cn.haozi.spring_security.admin.mapper.SysTaskMapper;
import cn.haozi.spring_security.admin.service.SysTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements SysTaskService {
}
