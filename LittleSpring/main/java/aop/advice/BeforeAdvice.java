package aop.advice;

import aop.service.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 前置通知的实现
 */
public class BeforeAdvice implements Advice{
    private Object bean;
    private MethodInvocation methodInvocation;

    public BeforeAdvice(Object bean, MethodInvocation methodInvocation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        return method.invoke(bean, args);
    }
}
