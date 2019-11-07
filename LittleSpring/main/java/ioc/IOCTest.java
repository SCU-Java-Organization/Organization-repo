package ioc;

import pojo.Car;
import pojo.Wheel;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 测试IOC容器
 */
public class IOCTest {
    public static void main(String[] args) throws Exception{
        String location = IOCTest.class.getResource("beans.xml").getFile();
        //System.out.println(BeanFactory.class.getResource("").getFile());
        BeanFactory factory = new BeanFactory(location);

        Wheel wheel = (Wheel)factory.getBean("wheel");
        System.out.println(wheel);

        Car car = (Car)factory.getBean("car");
        System.out.println(car);
    }
}
