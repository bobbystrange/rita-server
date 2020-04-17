package org.dreamcat.rita.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dreamcat.common.webmvc.util.ServletUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Create by tuke on 2020/2/23
 */
@Slf4j
@Profile({"dev"})
@Component
@Aspect
public class LoggingAspect {
    @Pointcut("execution(public * org.dreamcat.rita.controller.*.*Controller.*(..))")
    public void logging() {
    }

    @AfterReturning(value = "logging()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue) throws Throwable {
        ServletUtil.log(joinPoint.getArgs(), returnValue);
    }
}
