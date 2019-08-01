package cn.haozi.spring_security.admin.service;

import cn.haozi.spring_security.admin.entity.QuartzTaskLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 任务执行日志 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-25
 */
@Transactional(rollbackFor = Exception.class)
public interface QuartzTaskLogService extends IService<QuartzTaskLog> {

}
