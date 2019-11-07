package aop.service;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHelloWorld() {
        System.out.println("Hello World!");
    }
}
