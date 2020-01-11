package org.litespring.context.support;

import org.litespring.beans.factory.annotation.AutowireAnnotationProcessor;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/**
 * Description: Simplify the design by extending this abstract class
 * Focus on the path of the XML config file.
 *
 * @author ShaoJiale
 * date 2019/12/11
 * @see ClassPathXmlApplicationContext
 * @see FileSystemXmlApplicationContext
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;

    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        this(configFile, ClassUtils.getDefaultClassLoader());
    }

    /**
     * Define a Resource by path and load definitions.
     * Also we can set a ClassLoader for the factory.
     *
     * @param configFile config file path
     * @param cl         a class loader
     * @see #getResourceByPath(String)
     */
    public AbstractApplicationContext(String configFile, ClassLoader cl) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        factory.setBeanClassLoader(cl);
        registerBeanPostProcessor(factory);
    }

    public Object getBean(String beanID) {
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

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    protected void registerBeanPostProcessor(ConfigurableBeanFactory beanFactory) {
        AutowireAnnotationProcessor postProcessor = new AutowireAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);
    }
}
