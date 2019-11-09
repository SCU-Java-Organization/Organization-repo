package ioc_upgrade.bean.reader;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function XML分析工具接口
 */
public interface BeanDefinitionReader {
    void loadBeanDefinition(String location) throws Exception;
}
