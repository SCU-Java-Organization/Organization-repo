package aop;

import aop.advice.Advice;
import aop.advice.AroundAdvice;
import aop.advice.BeforeAdvice;
import aop.service.HelloService;
import aop.service.HelloServiceImpl;
import aop.service.MethodInvocation;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 测试AOP
 */
public class TestAOP {
    public static void main(String[] args) {
        // 1.创建一个MethodInvocation实现类
        MethodInvocation proxyClass = new MethodInvocation() {
            @Override
            public void invoke() {
                System.out.println("I'm proxy method!");
            }
        };

        HelloServiceImpl hello = new HelloServiceImpl();

        // 2.创建一个Advice
        Advice beforeAdvice = new BeforeAdvice(hello, proxyClass);
        Advice aroundAdvice = new AroundAdvice(hello, proxyClass);

        // 3.为目标对象增强
        HelloService helloService = (HelloService)MyAOP.getProxy(hello, beforeAdvice);
        HelloService helloService1 = (HelloService)MyAOP.getProxy(hello, aroundAdvice);

        // 4.测试增强后的方法
        helloService.sayHelloWorld();
        System.out.println();
        helloService1.sayHelloWorld();
    }
}
