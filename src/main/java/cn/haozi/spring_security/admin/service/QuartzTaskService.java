package cn.haozi.spring_security.admin.service;

import cn.haozi.spring_security.admin.entity.QuartzTask;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
public interface QuartzTaskService extends IService<QuartzTask> {

    /**
     * 根据ID，查询定时任务
     */
    QuartzTask queryObject(Integer jobId);

    /**
     * 分页查询定时任务列表
     * @return
     */
    IPage<QuartzTask> queryList(QueryWrapper<QuartzTask> wrapper, Page<QuartzTask> page);

    /**
     * 保存定时任务
     */
    void saveQuartzTask(QuartzTask quartzTask);

    /**
     * 更新定时任务
     */
    void updateQuartzTask(QuartzTask quartzTask);

    /**
     * 批量删除定时任务
     */
    void deleteBatchTasks(List<Integer> ids);

    /**
     * 批量更新定时任务状态
     */
    int updateBatchTasksByStatus(List<Integer> ids, Integer status);

    /**
     * 立即执行
     */
    void run(List<Integer> jobIds);

    /**
     * 暂停运行
     */
    void paush(List<Integer> jobIds);

    /**
     * 恢复运行
     */
    void resume(List<Integer> jobIds);
}
