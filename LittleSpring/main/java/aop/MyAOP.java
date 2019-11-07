package aop;

import aop.advice.Advice;

import java.lang.reflect.Proxy;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 简易AOP的实现，最终调用advice中的invoke方法实现AOP
 */
public class MyAOP {
    public static Object getProxy(Object bean, Advice advice){
        return Proxy.newProxyInstance(MyAOP.class.getClassLoader(), bean.getClass().getInterfaces(), advice);
    }
}
