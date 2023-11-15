package com.greenatom.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Этот код является аспектом логирования, написанным на языке программирования Java с использованием фреймворка
 * AspectJ. Он содержит два аспекта: logMethodExecutionTime и logMethodEnter.
 * <p>logMethodExecutionTime - это метод, который логирует время выполнения метода, на котором он применяется.
 * Он использует аннотацию @Around для перехвата вызова метода, измеряет время выполнения с помощью StopWatch
 * и регистрирует информацию о времени выполнения и аргументах метода в журнале.
 * <p>logMethodEnter - это метод, который логирует вход в метод, на котором он применяется. Он использует аннотацию
 * <p>@Before для перехвата вызова метода и регистрирует информацию о методе и его аргументах в журнале с уровнем
 * отладки.
 * <p>Этот код может быть использован для добавления логирования времени выполнения и входа в методы сервисного слоя
 * в приложении Java.
 *
 * @version 1.0
 * @autor Максим Быков
 */
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.greenatom.service..*(..)))")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("Execution time of "
                + methodSignature.getDeclaringType().getSimpleName()
                + "." + methodSignature.getName() + " "
                + " with args: " + Arrays.toString(proceedingJoinPoint.getArgs())
                + ":: " + stopWatch.getTime() + " ms");

        return result;
    }

    @Before("execution(* com.greenatom.service..*(..)))")
    public void logMethodEnter(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        log.debug("Order execution of method"
                + methodSignature.getDeclaringType().getSimpleName()
                + "." + methodSignature.getName() + " "
                + " with args: " + Arrays.toString(joinPoint.getArgs())
        );
    }
}
