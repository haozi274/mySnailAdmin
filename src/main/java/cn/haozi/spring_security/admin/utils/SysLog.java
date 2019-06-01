package cn.haozi.spring_security.admin.utils;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:15
 * @Description:
 */

import java.lang.annotation.*;

/**
 * 自定义注解类
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface SysLog {
    String value() default "";
}
