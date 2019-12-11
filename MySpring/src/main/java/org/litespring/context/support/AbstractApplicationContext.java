package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/**
 * Description: Simplify the design by extending this abstract class
 * Focus on the path of the XML config file.
 * @see ClassPathXmlApplicationContext
 * @see FileSystemXmlApplicationContext
 * @author ShaoJiale
 * date 2019/12/11
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;

    private ClassLoader beanClassLoader;

    /**
     * Define a Resource by path and load definitions.
     * Also we can set a ClassLoader for the factory.
     * @see #getResourceByPath(String)
     * @see #getBeanClassLoader()
     * @param configFile config file path
     */
    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(this.getBeanClassLoader());
    }

    public Object getBean(String beanID){
        return factory.getBean(beanID);
    }

    /**
     * Subclass must implement this method,
     * in order to get the correct Resource.
     *
     * @param path config file path from constructor
     * @return A correct Resource, ClassPath or FileSystem
     */
    protected abstract Resource getResourceByPath(String path);

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }
}
