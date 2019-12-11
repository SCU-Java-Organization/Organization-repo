package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.exception.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Description: A util for resolving XML config file.
 *
 * After resolving each bean definition, we use BeanDefinitionRegistry
 * to register the bean definition to bean-container.
 * Actually the BeanDefinitionRegistry is always a factory.
 *
 * @see BeanDefinitionRegistry
 * @author ShaoJiale
 * date 2019/12/11
 */
public class XmlBeanDefinitionReader  {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    /**
     * Load bean definitions with config file
     * @param configFile config file path (Class-Path)
     */
    @Deprecated
    public void loadBeanDefinitions(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            load(is);
        } catch (Exception e){
            throw new BeanDefinitionStoreException("Parsing XML document:[" + configFile + "] failed", e);
        }
    }

    /**
     * Load bean definitions with Resource
     * @see Resource
     * @see org.litespring.core.io.ClassPathResource
     * @see org.litespring.core.io.FileSystemResource
     * @param resource ClassPathResource or FileSystemResource
     */
    public void loadBeanDefinitions(Resource resource){
        try {
            InputStream is = resource.getInputStream();
            load(is);
        } catch (Exception e){
            throw new BeanDefinitionStoreException("Parsing XML document:[" + resource.getDescription() + "] failed", e);
        }
    }

    /**
     * Load bean definition with InputStream
     * @see #loadBeanDefinitions(Resource)
     * @see #loadBeanDefinitions(String)
     * @param is InputStream created by methods above
     */
    private void load(InputStream is){
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){  // Get bean id and class name from XML
                Element elem = iter.next();
                String id = elem.attributeValue(ID_ATTRIBUTE);
                String beanClassName = elem.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition definition = new GenericBeanDefinition(id, beanClassName);

                if(elem.attributeValue(SCOPE_ATTRIBUTE) != null){
                    definition.setScope(elem.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.registry.registerBeanDefinition(id, definition);
            }
        } catch (Exception e){
            throw new BeanDefinitionStoreException("Parsing XML document failed", e);
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

}
