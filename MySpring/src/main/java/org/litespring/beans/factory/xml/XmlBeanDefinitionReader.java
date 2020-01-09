package org.litespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.exception.*;
import org.litespring.beans.factory.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Description: A util for resolving XML config file.
 * <p>
 * After resolving each bean definition, we use BeanDefinitionRegistry
 * to register the bean definition to bean-container.
 * Actually the BeanDefinitionRegistry is always a factory.
 *
 * @author ShaoJiale
 * date 2019/12/11
 * @see BeanDefinitionRegistry
 */
public class XmlBeanDefinitionReader {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    BeanDefinitionRegistry registry;

    protected final Log logger = LogFactory.getLog(getClass());

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * Load bean definitions with config file
     * This method is deprecated, we suggest you to use Resource
     *
     * @param configFile config file path (Class-Path)
     */
    @Deprecated
    public void loadBeanDefinitions(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            load(is);
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Parsing XML document:[" + configFile + "] failed", e);
        }
    }

    /**
     * Load bean definitions with Resource
     *
     * @param resource ClassPathResource or FileSystemResource
     * @see Resource
     * @see org.litespring.core.io.ClassPathResource
     * @see org.litespring.core.io.FileSystemResource
     */
    public void loadBeanDefinitions(Resource resource) {
        try {
            InputStream is = resource.getInputStream();
            load(is);
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Parsing XML document:[" + resource.getDescription() + "] failed", e);
        }
    }

    /**
     * Load bean definition with InputStream
     *
     * @param is InputStream created by methods below
     * @see #loadBeanDefinitions(Resource)
     * @see #loadBeanDefinitions(String)
     */
    private void load(InputStream is) {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iter = root.elementIterator();

            while (iter.hasNext()) {  // Get bean id and class name from XML
                Element elem = iter.next();

                String namespaceUri = elem.getNamespaceURI();
                if (this.isDefaultNamespace(namespaceUri)) {
                    // regular bean
                    parseDefaultElement(elem);
                } else if (this.isContextNamespace(namespaceUri)) {
                    // tag like <context:component-scan>
                    parseComponentElement(elem);
                }
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("Parsing XML document failed", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    public boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    /**
     * parse default element in the XML config file
     *
     * @param elem current element
     */
    public void parseDefaultElement(Element elem) {
        String id = elem.attributeValue(ID_ATTRIBUTE);
        String beanClassName = elem.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition definition = new GenericBeanDefinition(id, beanClassName);

        if (elem.attributeValue(SCOPE_ATTRIBUTE) != null) {
            definition.setScope(elem.attributeValue(SCOPE_ATTRIBUTE));
        }

        // parse constructor args firstly
        parseConstructorArgElements(elem, definition);

        // parse the property tag secondly
        parsePropertyElement(elem, definition);

        // load definition to registry thirdly
        this.registry.registerBeanDefinition(id, definition);
    }

    /**
     * parse the package-scan attribute
     * @param elem current element
     */
    public void parseComponentElement(Element elem) {
        String basePackages = elem.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    /**
     * Parse the constructor-args elements.
     * For each of the constructor-arg element,
     * we use another method to parse it.
     *
     * @param beanEle current bean element
     * @param bd      bean definition
     * @see #parseConstructorArgElement(Element, BeanDefinition)
     */
    public void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iter.hasNext()) {
            Element elem = (Element) iter.next();
            parseConstructorArgElement(elem, bd);
        }
    }

    /**
     * Parse current constructor-arg element.
     * Use this method to inject args into ConstructorArgument
     * of the current BeanDefinition. Like we explained in class
     * ConstructorArgument, we haven't supported the 'type' attribute
     * or the 'name' attribute. But we still left them in this method
     * because we may implement them in the future.
     *
     * @param elem current constructor-arg element
     * @param bd   current bean definition
     * @see ConstructorArgument
     */
    public void parseConstructorArgElement(Element elem, BeanDefinition bd) {
        String typeAttr = elem.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = elem.attributeValue(NAME_ATTRIBUTE);

        Object value = parsePropertyValue(elem, null);

        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);

        if (StringUtils.hasLength(typeAttr))
            valueHolder.setType(typeAttr);
        if (StringUtils.hasLength(nameAttr))
            valueHolder.setName(nameAttr);

        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    /**
     * Parse property tag of the current bean tag.
     * Such as:
     * <bean id="xxx" class="xxx.xxx.xxx">
     * <property name="xxx" ref="xxx"/>
     * <property name="xxx" value="xxx"/>
     * </bean>
     * The property can be a bean or a String.
     *
     * @param beanElem Element of the current bean tag
     * @param bd       BeanDefinition of the current bean
     * @see #parsePropertyValue(Element, String)
     */
    public void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);

        while (iter.hasNext()) {
            Element propertyElem = (Element) iter.next();

            // get name attribute
            String propertyName = propertyElem.attributeValue(NAME_ATTRIBUTE);

            // the name cannot be null or empty
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            // parse value attribute
            Object val = parsePropertyValue(propertyElem, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);

            // add properties to bean definition
            bd.getPropertyValues().add(pv);
        }
    }

    /**
     * Parse the value of the current property tag
     * Such as:
     * <bean id="xxx" class="xxx.xxx.xxx">
     * <property name="xxx" ref="xxx"/>
     * <property name="xxx" value="xxx"/>
     * </bean>
     * <p>
     * The 'ref' and 'value' tag is what we focus on.
     * We cannot have both attributes at a same time,
     * such as: <property name="xxx" ref="xxx" value="xxx"/>
     *
     * @param propertyElem the current property element
     * @param propertyName the current property name
     * @return a wrapper class of a bean or a String
     * @see RuntimeBeanReference  wrapper class of a bean
     * @see TypedStringValue      wrapper class of a String
     */
    private Object parsePropertyValue(Element propertyElem, String propertyName) {

        // define this for exception description
        String elementName = propertyName != null ?
                "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";

        // judge if the current 'property' has a 'ref' tag or 'value' tag
        boolean hasRefAttribute = (propertyElem.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (propertyElem.attribute(VALUE_ATTRIBUTE) != null);

        // a property cannot have both attributes at a same time
        if (hasRefAttribute) {
            String refName = propertyElem.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName))
                logger.error(elementName + " contains empty 'ref' attribute");

            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if (hasValueAttribute) {
            TypedStringValue holder = new TypedStringValue(propertyElem.attributeValue(VALUE_ATTRIBUTE));
            return holder;
        } else { // a 'property' must have a 'ref' or 'value' attribute
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

}
