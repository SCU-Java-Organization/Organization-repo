package ioc_upgrade.bean.factory;

import ioc_upgrade.bean.BeanDefinition;
import ioc_upgrade.bean.BeanReference;
import ioc_upgrade.bean.processor.PostProcessor;
import ioc_upgrade.bean.reader.XmlBeanDefinitionReader;
import ioc_upgrade.property.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 根据XML配置解析Bean的IOC容器
 */
public class XmlBeanFactory implements BeanFactory{
    // 存储bean定义的HashMap
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    // 存储bean定义名的List
    private List<String> beanDefinitionNames = new ArrayList<>();

    // 存储Processor的List
    private List<PostProcessor> processors = new ArrayList<>();

    // XML分析工具
    private XmlBeanDefinitionReader reader;

    // 构造函数，需要制定配置文件路径
    public XmlBeanFactory(String location) throws Exception{
        reader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
    }

    /**
     * @function 从 IOC 容器中取出一个Bean
     * @param id bean的唯一标识
     * @return bean
     * @throws Exception
     */
    @Override
    public Object getBean(String id) throws Exception {
        // 获取bean的信息体
        BeanDefinition beanDefinition = beanDefinitionMap.get(id);

        // 容器中没有注册该bean
        if(beanDefinition == null)
            throw new IllegalArgumentException("Cannot find bean with name " + id);

        // 容器中注册了该bean
        Object bean = beanDefinition.getBean();

        // 该bean在容器中还未初始化为一个实体对象
        if(bean == null){
            bean = createBean(beanDefinition);  // 获取bean的实例化对象
            bean = initializeBean(bean, id);    // 初始化该bean对象(processor)
            beanDefinition.setBean(bean);       // 将准备好的bean注入到容器中
        }

        return bean;
    }

    /**
     * @function 使用反射新建一个bean的实例化对象，然后初始化它
     * @param definition bean的信息体对象
     * @return 获取相应的bean
     * @throws Exception
     */
    private Object createBean(BeanDefinition definition) throws Exception{
        Object bean = definition.getBeanClass().newInstance();
        setPropertyValues(bean, definition);
        return bean;
    }

    /**
     * @function 初始化bean的成员变量
     * @param bean
     * @param definition
     * @throws Exception
     */
    private void setPropertyValues(Object bean, BeanDefinition definition) throws Exception{
        // 如果bean实现了FactoryAware
        if(bean instanceof BeanFactoryAware)
            ((BeanFactoryAware)bean).setBeanFactory(this);

        for(PropertyValue propertyValue : definition.getPropertyValues().getPropertyValues()){
            Object value = propertyValue.getValue();

            // 如果property是ref类型，则重新调用getBean()去加载ref对应的bean
            if(value instanceof BeanReference){
                BeanReference reference = (BeanReference)value;
                value = getBean(reference.getName());
            }

            // 寻找setter方法
            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e){
                // 如果没有setter方法，则手动赋值
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }

    /**
     * @function 对bean的最终初始化，织入Processor
     * @param bean
     * @param name
     * @return 最终完成的bean
     * @throws Exception
     */
    private Object initializeBean(Object bean, String name) throws Exception{
        for(PostProcessor processor : processors)
            bean = processor.postProcessBeforeInitialization(bean, name);

        for (PostProcessor processor : processors)
            bean = processor.postProcessAfterInitialization(bean, name);

        return bean;
    }

    /**
     * @function 加载bean 的XML配置信息
     * @param location
     * @throws Exception
     */
    private void loadBeanDefinitions(String location) throws Exception{
        reader.loadBeanDefinition(location);
        registerBeanDefinition();   // 加载配置信息到IOC容器
        registerBeanProcessor();    // 加载Processor信息到IOC容器
    }

    /**
     * @function 将reader读取到的配置信息注册到IOC容器中
     */
    private void registerBeanDefinition(){
        for(Map.Entry<String, BeanDefinition> entry : reader.getRegistry().entrySet()){
            String name = entry.getKey();
            BeanDefinition definition = entry.getValue();

            beanDefinitionMap.put(name, definition);
            beanDefinitionNames.add(name);
        }
    }

    /**
     * @function 将Processor注册到容器
     * @throws Exception
     */
    public void registerBeanProcessor() throws Exception{
        List beans = getBeansForType(PostProcessor.class);

        for (Object bean : beans) {
            addPostProcessor((PostProcessor)bean);
        }
    }

    public void addPostProcessor(PostProcessor postProcessor){
        processors.add(postProcessor);
    }

    /**
     * @function 将每个bean与自己对应的processor关联起来
     * @param type processor.class
     * @return bean with processor
     * @throws Exception
     */
    public List getBeansForType(Class type) throws Exception{
        List beans = new ArrayList();
        for(String name : beanDefinitionNames){
            if(type.isAssignableFrom(beanDefinitionMap.get(name).getBeanClass()))
                beans.add(getBean(name));
        }
        return beans;
    }
}
