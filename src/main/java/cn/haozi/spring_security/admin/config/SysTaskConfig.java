package cn.haozi.spring_security.admin.config;

import cn.haozi.spring_security.admin.entity.SysTask;
import cn.haozi.spring_security.admin.service.SysTaskService;
import cn.haozi.spring_security.admin.utils.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时任务
 */
@Lazy(value = false)
//@Component
@Slf4j
//@EnableScheduling
public class SysTaskConfig implements SchedulingConfigurer {
    @Autowired
    private SysTaskService sysTaskService;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //从数据库里取得所有要执行的定时任务
    private List<SysTask> getAllTasks() {

        return sysTaskService.list();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<SysTask> tasks=getAllTasks();
        log.info("定时任务启动,预计启动任务数量="+tasks.size()+"; time="+sdf.format(new Date()));

        //校验数据（这个步骤主要是为了打印日志，可以省略）
        checkDataList(tasks);

        //通过校验的数据执行定时任务
        int count = 0;
        if(tasks.size()>0) {
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    taskRegistrar.addTriggerTask(getRunnable(tasks.get(i)), getTrigger(tasks.get(i)));
                    count++;
                } catch (Exception e) {
                    log.error("定时任务启动错误:" + tasks.get(i).getClassName() + ";" + tasks.get(i).getMethodName() + ";" + e.getMessage());
                }
            }
        }
        log.info("定时任务实际启动数量="+count+"; time="+sdf.format(new Date()));
    };


    private Runnable getRunnable(SysTask task){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Object obj = SpringUtil.getBean(task.getClassName());
                    Method method = obj.getClass().getMethod(task.getMethodName(),null);
                    method.invoke(obj);
                } catch (InvocationTargetException e) {
                    log.error("定时任务启动错误，反射异常:"+task.getClassName()+";"+task.getMethodName()+";"+ e.getMessage());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        };
    }

    private Trigger getTrigger(SysTask task){
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(task.getCron());
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };

    }

    private List<SysTask> checkDataList(List<SysTask> list) {
        String errMsg="";
        for(int i=0;i<list.size();i++){
            if(!checkOneData(list.get(i)).equalsIgnoreCase("success")){
                errMsg+=list.get(i).getTaskName()+";";
                list.remove(list.get(i));
                i--;
            };
        }
        if(!StringUtils.isBlank(errMsg)){
            errMsg="未启动的任务:"+errMsg;
            log.error(errMsg);
        }
        return list;
    }

    private String checkOneData(SysTask task){
        String result="success";
        Class cal= null;
        try {
            cal = Class.forName(task.getClassName());

            Object obj =SpringUtil.getBean(cal);
            Method method = obj.getClass().getMethod(task.getMethodName(),null);
            String cron=task.getCron();
            if(StringUtils.isBlank(cron)){
                result="定时任务启动错误，无cron:"+task.getTaskName();
                log.error(result);
            }
        } catch (ClassNotFoundException e) {
            result="定时任务启动错误，找不到类:"+task.getClassName()+ e.getMessage();
            log.error(result);
        } catch (NoSuchMethodException e) {
            result="定时任务启动错误，找不到方法,方法必须是public:"+task.getClassName()+";"+task.getMethodName()+";"+ e.getMessage();
            log.error(result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }



}
