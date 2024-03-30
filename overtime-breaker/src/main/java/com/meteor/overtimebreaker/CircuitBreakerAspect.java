package com.meteor.overtimebreaker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class CircuitBreakerAspect {
    @Pointcut("@annotation(com.meteor.overtimebreaker.annotation.ACircuitBreaker)")
    public void aop(){

    }

    @Around("aop()")
    public Object aroundCircuitBreaker(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        // 获取切面切到的方法
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = proceedingJoinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        return null;
    }

}
