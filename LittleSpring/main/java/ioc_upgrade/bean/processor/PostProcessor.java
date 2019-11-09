package ioc_upgrade.bean.processor;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 对外拓展的接口
 *
 * 让开发人员能够参与bean的实例化过程
 * 通过实现这个接口，我们能在bean实例化时，对bean进行一些处理
 * 可以通过此方式实现代理
 * 过程如下：
 * 1.根据BeanDefinition信息，寻找所有实现了PostProcessor的类
 * 2.实例化PostProcessor的实现类
 * 3.将实例化的对象放入List中
 * 4.重复2、3步，知道所有类完成注册
 */
public interface PostProcessor {
    Object postProcessBeforeInitialization(Object bean, String name) throws Exception;

    Object postProcessAfterInitialization(Object bean, String name) throws Exception;
}
