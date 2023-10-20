package com.greenatom.config.aop;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @Around("execution(* com.greenatom.service..*(..)))")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        LOGGER.info("Execution time of "
                + methodSignature.getDeclaringType().getSimpleName()
                + "." + methodSignature.getName() + " "
                +" with args: "+ Arrays.toString(proceedingJoinPoint.getArgs())
                + ":: " + stopWatch.getTime() + " ms");

        return result;
    }

    @Before("execution(* com.greenatom.service..*(..)))")
    public void logMethodEnter(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LOGGER.debug("Request execution of method"
                + methodSignature.getDeclaringType().getSimpleName()
                + "." + methodSignature.getName() + " "
                +" with args: "+ Arrays.toString(joinPoint.getArgs())
        );
    }
}