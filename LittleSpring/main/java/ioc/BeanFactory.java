package ioc;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ShaoJiale
 * @create 2019/11/6
 * @function 简单的IOC容器
 */
public class BeanFactory {
    private Map<String, Object> beanMap = new HashMap<>();

    public BeanFactory(String location) throws Exception{
        loadBeans(location);
    }

    /**
     * @function 从容器中取出相应的bean
     * @param name bean的唯一标识
     * @return
     */
    public Object getBean(String name){
        Object bean = beanMap.get(name);
        if(bean == null)
            throw new IllegalArgumentException("No such bean with name of " + name);
        return bean;
    }

    /**
     * @function 加载配置文件中配置的bean到IOC容器中
     * @param location 配置文件路径
     * @throws Exception
     */
    private void loadBeans(String location) throws Exception{
        // 加载xml文件
        InputStream in = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(in);

        // 解析xml文件
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        // 遍历<bean>标签
        getBeanLabel(nodeList);
    }

    /**
     * @function 遍历容器中的bean标签
     * @param nodeList
     * @throws Exception
     */
    private void getBeanLabel(NodeList nodeList) throws Exception{
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(node instanceof Element) {
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String className = element.getAttribute("class");

                // 加载bean对应的Class
                Class beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                // 创建bean
                Object bean = beanClass.newInstance();

                // 遍历property标签来初始化bean，完成后在容器中注册该bean
                getPropertyLabel(element, bean, id);
            }
        }
    }

    /**
     * @function 遍历bean标签中的property标签
     * @param element 对应的bean标签
     * @param bean 需要初始化的bean对象
     * @param id bean的唯一标识id
     * @throws Exception
     */
    private void getPropertyLabel(Element element, Object bean, String id) throws Exception{
        // 获取bean标签中的property标签
        NodeList propertyList = element.getElementsByTagName("property");

        for (int j = 0; j < propertyList.getLength(); j++) {
            Node propertyNode = propertyList.item(j);
            if(propertyNode instanceof Element){
                Element property = (Element) propertyNode;
                String name = property.getAttribute("name");
                String value = property.getAttribute("value");

                // 利用反射将bean相关property字段的权限设置为可访问
                Field field = bean.getClass().getDeclaredField(name);
                field.setAccessible(true);

                // 初始化bean的相关字段
                if(value != null && value.length() > 0)
                    field.set(bean, value);
                else {
                    String ref = property.getAttribute("ref");
                    if(ref == null || ref.length() == 0)
                        throw new IllegalArgumentException("ref config error");
                    field.set(bean, getBean(ref));
                }
                registerBean(id, bean);
            }
        }
    }

    /**
     * @function 在IOC容器中注册一个bean
     * @param id bean的标识id
     * @param bean bean的实体对象
     */
    private void registerBean(String id, Object bean){
        beanMap.put(id, bean);
    }
}
