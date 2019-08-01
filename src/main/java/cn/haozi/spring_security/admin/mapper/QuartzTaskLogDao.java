package cn.haozi.spring_security.admin.mapper;

import cn.haozi.spring_security.admin.entity.QuartzTaskLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * 任务执行日志 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-25
 */
@Repository
public interface QuartzTaskLogDao extends BaseMapper<QuartzTaskLog> {

}
