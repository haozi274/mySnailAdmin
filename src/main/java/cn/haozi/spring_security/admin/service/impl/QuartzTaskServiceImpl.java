package cn.haozi.spring_security.admin.service.impl;

import cn.haozi.spring_security.admin.entity.QuartzTask;
import cn.haozi.spring_security.admin.mapper.QuartzTaskDao;
import cn.haozi.spring_security.admin.service.QuartzTaskService;
import cn.haozi.spring_security.admin.utils.Constants;
import cn.haozi.spring_security.admin.utils.quartz.ScheduleUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTaskServiceImpl extends ServiceImpl<QuartzTaskDao, QuartzTask> implements QuartzTaskService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init(){
        QueryWrapper<QuartzTask> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag",false);
        List<QuartzTask> scheduleJobList = list(wrapper);
        for(QuartzTask scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public QuartzTask queryObject(Integer jobId) {
        return baseMapper.selectById(jobId);
    }

    @Override
    public IPage<QuartzTask> queryList(QueryWrapper<QuartzTask> wrapper, Page<QuartzTask> page) {
        return page(page,wrapper);
    }

    @Override
    public void saveQuartzTask(QuartzTask quartzTask) {
        baseMapper.insert(quartzTask);
        ScheduleUtils.createScheduleJob(scheduler,quartzTask);
    }

    @Override
    public void updateQuartzTask(QuartzTask quartzTask) {
        baseMapper.updateById(quartzTask);
        ScheduleUtils.updateScheduleJob(scheduler,quartzTask);
    }

    @Override
    public void deleteBatchTasks(List<Integer> ids) {
        for(Integer id : ids){
            ScheduleUtils.deleteScheduleJob(scheduler, id);
        }
       baseMapper.deleteBatchIds(ids);
    }

    @Override
    public int updateBatchTasksByStatus(List<Integer> ids, Integer status) {
        List<QuartzTask> list = baseMapper.selectBatchIds(ids);
        for (QuartzTask task : list){
            task.setStatus(status);
        }
        updateBatchById(list);
        return 0;
    }

    @Override
    public void run(List<Integer> jobIds) {
        for(Integer jobId : jobIds){
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    @Override
    public void paush(List<Integer> jobIds) {
        for(Integer jobId : jobIds){
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        updateBatchTasksByStatus(jobIds, Constants.QUARTZ_STATUS_PUSH);
    }

    @Override
    public void resume(List<Integer> jobIds) {
        for(Integer jobId : jobIds){
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatchTasksByStatus(jobIds, Constants.QUARTZ_STATUS_NOMAL);
    }
}
