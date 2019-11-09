package ioc_upgrade.bean.factory;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
