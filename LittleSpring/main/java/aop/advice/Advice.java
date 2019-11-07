package aop.advice;

import java.lang.reflect.InvocationHandler;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 类Spring的通知，最终都需要使用Advice实例对象的invoke方法来实现AOP
 * @function 这里仅仅是将JDK动态代理的InvocationHandler包装成自己的Advice
 */
public interface Advice extends InvocationHandler {
}
