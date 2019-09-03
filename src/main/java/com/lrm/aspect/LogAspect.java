package com.lrm.aspect;

import com.lrm.log.RequestLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    /**
     * 定义切面
     */
    @Pointcut("execution(* com.lrm.web.*.*(..))")
    public void log(){}

    /**
     * 前置通知在方法执行之前执行
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String typeName = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();


        RequestLog requestLog = new RequestLog(url,ip,typeName,args);
        logger.info("request : {}",requestLog);
    }

    /**
     * 在方法执行之后执行
     */
    @After("log()")
    public void doAfter(){
        logger.info("--------doAfter在方法执行之后执行--------------");
    }
    /**
     * 返回执行内容
     */
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturning(Object result){
        logger.info("result : {}",result);

    }
}
