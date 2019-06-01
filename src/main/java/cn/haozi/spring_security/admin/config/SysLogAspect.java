package cn.haozi.spring_security.admin.config;

import cn.haozi.spring_security.admin.controller.LogController;
import cn.haozi.spring_security.admin.entity.Log;
import cn.haozi.spring_security.admin.service.LogService;
import cn.haozi.spring_security.admin.shior.MyRealm;
import cn.haozi.spring_security.admin.utils.SysLog;
import cn.haozi.spring_security.admin.utils.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Auther: 陈思浩
 * @Date: 2019/4/10 10:17
 * @Description:
 */
@Aspect
@Component
@AllArgsConstructor
public class SysLogAspect {

    private LogService logService;

    private HttpServletRequest myHttpRequest;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( cn.haozi.spring_security.admin.utils.SysLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        System.out.println("切面。。。。。");
        //保存日志
        Log sysLog = new Log();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        SysLog myLog = method.getAnnotation(SysLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        /**
         * 日志操作不记录
         */
        if (className.equals( LogController.class.getName())) {
            return;
        }
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        params= params.length()>100?JSONObject.toJSONString("请求参数数据过长不与显示"):params;
        System.out.println(params.toString()+"--------------");
        sysLog.setParams(params);

        sysLog.setCreateTime(new Date());
        //获取用户名
       // sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());
        //获取用户ip地址
        //HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //sysLog.setIp(IPUtils.getIpAddr(request));

        String ip = ToolUtil.getClientIp(myHttpRequest);
        if("0.0.0.0".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) || "127.0.0.1".equals(ip)){
            ip = "127.0.0.1";
        }
        sysLog.setIp(ip);
        MyRealm.ShiroUser shiroUser = (MyRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        sysLog.setCreateBy(shiroUser.id);
        //调用service保存SysLog实体类到数据库
        logService.save(sysLog);
    }


}
