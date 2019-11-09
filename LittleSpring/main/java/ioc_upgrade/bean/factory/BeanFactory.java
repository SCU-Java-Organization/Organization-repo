package ioc_upgrade.bean.factory;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function IOC容器接口
 */
public interface BeanFactory {
    Object getBean(String id) throws Exception;
}
