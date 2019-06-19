package cn.haozi.spring_security.common.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务DEMO
 */
@Component("taskDemo")
public class TaskDemo {

    public void test(){
        System.out.println("开始执行时间："+ System.currentTimeMillis());
        System.out.println("开始执行定时任务了。。。。。。。。。。。。。。。。");
        System.out.println( "结束时间："+System.currentTimeMillis());
    }
}
