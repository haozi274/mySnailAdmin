package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.QuartzTaskLog;
import cn.haozi.spring_security.admin.mapper.QuartzTaskLogDao;
import cn.haozi.spring_security.admin.service.QuartzTaskLogService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 任务执行日志 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTaskLogServiceImpl extends ServiceImpl<QuartzTaskLogDao, QuartzTaskLog> implements QuartzTaskLogService {

}
