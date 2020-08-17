package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysLogService iSysLogService;

    private Date visitTime; //开始时间
    private Class clazz; //访问的类
    private Method method;//访问的方法
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        visitTime=new Date();//当前时间就是开始访问的时间
        clazz=joinPoint.getTarget().getClass();  //具体要访问的类
        String methodName=joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();//获取访问方法的参数
        if(args==null||args.length==0){
            method=clazz.getMethod(methodName);  //只能获取无参数的方法
        }else {
            Class[] classArgs=new Class[args.length];
            for(int i=0;i<args.length;i++)
            {
                classArgs[i]=args[i].getClass();
            }
            clazz.getMethod(methodName,classArgs);
        }

    }

    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        long time=new Date().getTime()-visitTime.getTime();//获取访问的时长
        //获取url
        String url="";
        if(clazz!=null&&method!=null&clazz!=LogAop.class){
            //1.获取类上的@RequestMapping("/orders")
            RequestMapping classAnnotation=(RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation !=null){
                String[] classvalue=classAnnotation.value();
                //2.获取方法上的@RequestMapping("/orders")注解的值
                RequestMapping methodannotation=method.getAnnotation(RequestMapping.class);
                if(methodannotation!=null){
                    String [] methodvalue=methodannotation.value();
                    url=classvalue[0]+methodvalue[0];
                }
            }
        }

        //获取访问的ip地址
        String ip=request.getRemoteAddr();
        //获取当前操作的用户
        SecurityContext securityContext= SecurityContextHolder.getContext(); //从上下文中获取登录的用户
        User user= (User) securityContext.getAuthentication().getPrincipal();
        String username=user.getUsername();


        //将日志信息封装到SysLog对象
        SysLog sysLog=new SysLog();
        sysLog.setExecutionTime(time);
        sysLog.setId(ip);
        sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setVisitTime(visitTime);

        iSysLogService.save(sysLog);
    }
}
