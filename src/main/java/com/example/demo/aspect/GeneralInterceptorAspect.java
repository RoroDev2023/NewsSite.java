package com.example.demo.aspect;

import com.example.demo.entity.User;
import com.example.demo.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Slf4j
@Component
public class GeneralInterceptorAspect {

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("@annotation(com.example.demo.annotation.CustomAnnotation)")
    public void loggingPointCut() {
    }

    /**
     * Advice that logs methods execution around join points.
     *
     * @param joinPoint the join point of the advice
     * @return the result of the method execution
     * @throws Throwable if any error occurs during method execution
     */
    @Around("loggingPointCut()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before method invoked::" + joinPoint.getSignature());

        Object object = joinPoint.proceed();

        if (object instanceof User) {
            log.info("After method invoked with User::" + object);
        } else if (object instanceof News) {
            log.info("After method invoked with News::" + object);
        } else {
            log.info("After method invoked with unknown type::" + object);
        }

        return object;
    }

    /**
     * Advice that logs after a method returns.
     *
     * @param jp the join point of the advice
     * @param retVal the returned value of the method
     */
    @AfterReturning(value = "loggingPointCut()", returning = "retVal")
    public void afterReturningAdvice(final JoinPoint jp, final Object retVal) {
        log.info("Method Signature: " + jp.getSignature());
        log.info("Returning: " + retVal.toString());
    }

    /**
     * Advice that logs before a method execution.
     *
     * @param joinPoint the join point of the advice
     */
    @Before("loggingPointCut()")
    public void before(final JoinPoint joinPoint) {
        log.info("Before method invoked::" + joinPoint.getSignature());
    }

    /**
     * Advice that logs after a method throws an exception.
     *
     * @param joinPoint the join point of the advice
     * @param e the thrown exception
     */
    @AfterThrowing(value = "loggingPointCut()", throwing = "e")
    public void afterThrowingAdvice(
            final JoinPoint joinPoint, final Exception e) {
        log.info("Exception thrown in Method::"
                +
                joinPoint.getSignature());
        log.info("Exception is::" + e.getMessage());
    }
}



