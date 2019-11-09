package ioc_upgrade.bean.reader;

import ioc_upgrade.bean.BeanDefinition;
import ioc_upgrade.bean.BeanReference;
import ioc_upgrade.property.PropertyValue;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 解析XML获取bean信息的工具类
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    // 保存配置信息的Map
    private Map<String, BeanDefinition> registry;

    public XmlBeanDefinitionReader() {
        registry = new HashMap<>();
    }

    @Override
    public void loadBeanDefinition(String location) throws Exception {
        InputStream in = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(in);
        Element root = document.getDocumentElement();
        parseXML(root);
    }

    private void parseXML(Element root) throws Exception{
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                parseBean(element);
            }
        }
    }

    private void parseBean(Element element) throws Exception{
        String name = element.getAttribute("id");
        String className = element.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setClassName(className);
        parseProperty(element, beanDefinition);
        registry.put(name, beanDefinition);
    }

    private void parseProperty(Element element, BeanDefinition beanDefinition){
        NodeList propertyList = element.getElementsByTagName("property");
        for (int i = 0; i < propertyList.getLength(); i++) {
            Node property = propertyList.item(i);
            if(property instanceof Element){
                Element propertyEle = (Element) property;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");

                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("ref config error");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }
}
