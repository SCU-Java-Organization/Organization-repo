package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanCreationException;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * description: Default implementation of BeanFactory
 * usage: Default bean-container of lite-spring
 * @author ShaoJiale
 * date 2019/12/10
 */
public class DefaultBeanFactory implements BeanFactory {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * Constructor with file path
     * @see #loadBeanDefinition(String)
     * @param configFile XML file path
     */
    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){  // Get bean id and class name from XML
                Element elem = (Element)iter.next();
                String id = elem.attributeValue(ID_ATTRIBUTE);
                String beanClassName = elem.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition definition = new GenericBeanDefinition(id, beanClassName);
                this.beanDefinitionMap.put(id, definition);
            }
        } catch (DocumentException e){
            throw new BeanDefinitionStoreException("IOException parsing XML document: " + configFile, e);
        } finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get bean definition from container
     * @param beanID
     * @return BeanDefinition
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * Get instance of bean
     * @param beanID
     * @return instance
     */
    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);

        if(bd == null)
            throw new BeanCreationException("Bean Definition does not exist!");

        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();

        try {
            Class<?> clazz = loader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e){
            throw new BeanCreationException("create bean with name of " + beanClassName + " failed!");
        }
    }
}
