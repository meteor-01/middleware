package com.meteor.whitelist.aspect;

import com.alibaba.fastjson.JSON;
import com.meteor.whitelist.annotation.AWhiteList;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Configuration
public class WhiteListAspect {
    @Resource
    private String whiteListConfig;

    @Pointcut("@annotation(com.meteor.whitelist.annotation.AWhiteList)")
    public void aop() {
    }

    @Around("aop()")
    public Object whiteListIntercept(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取切面切到的方法
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = proceedingJoinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        // 根据注解中的属性名获取值
        AWhiteList aWhiteList = method.getAnnotation(AWhiteList.class);
        String userName = aWhiteList.userName();
        String value = null;
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            try {
                if (value == null || value.equals("")) {
                    value = BeanUtils.getProperty(arg, userName);
                }
                else break;
            } catch (Exception e) {

            }
        }
        //如果value不为null和空字符串，进行白名单过滤
        if(!(value==null || value.equals(""))){
            //白名单过滤
            String[] names = whiteListConfig.split(",");
            for(String name: names){
                if(value.equals(name)){
                    return proceedingJoinPoint.proceed();
                }
            }
            return proceedingJoinPoint.proceed();
        }

        //拦截用户
        Class<?> returnType = method.getReturnType();
        if("".equals(aWhiteList.returnValue())){
            return returnType.newInstance();
        }
        return JSON.parseObject(aWhiteList.returnValue(), returnType);
    }
}
