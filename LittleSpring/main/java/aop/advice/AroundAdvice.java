package aop.advice;

import aop.service.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 环绕通知
 */
public class AroundAdvice implements Advice{
    private Object bean;
    private MethodInvocation methodInvocation;

    public AroundAdvice(Object bean, MethodInvocation methodInvocation) {
        this.bean = bean;
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        bean = method.invoke(bean, args);
        methodInvocation.invoke();
        return bean;
    }
}
