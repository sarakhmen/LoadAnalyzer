package com.sarakhman.loadanalyzer.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
@Aspect
public class ExecutionTimeComputingAspect {
    @Pointcut("execution(* com.sarakhman.loadanalyzer.service.impl.CsvFieldsValidatorService.validateData(..))")
    public void validateDataExecution(){}

    @Around("validateDataExecution()")
    public Object computeValidateDataExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalTime beforeMethodCall = LocalTime.now();
        Object result = joinPoint.proceed();
        LocalTime afterMethodCall = LocalTime.now();
        ExecutionTimeSecondsThreadLocal.set(beforeMethodCall.until(afterMethodCall, ChronoUnit.SECONDS));
        return result;
    }
}
