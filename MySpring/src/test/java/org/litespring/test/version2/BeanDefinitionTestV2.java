package org.litespring.test.version2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;

import java.util.List;

/**
 * Description: Class for test
 * We only test BeanDefinition here, test if we can
 * resolve <property> tag and get BeanDefinition from ref.
 * ATTENTION! We do not test if we can get bean from the tag.
 * @version 2
 * @author ShaoJiale
 * date 2019/12/12
 */
public class BeanDefinitionTestV2 {
    @Test
    public void testGetBeanDefinition(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition definition = factory.getBeanDefinition("petStore");

        List<PropertyValue> propertyValues = definition.getPropertyValues();

        //Assert.assertTrue(propertyValues.size() == 4);
        {
            PropertyValue pv = this.getPropertyValue("accountDao", propertyValues);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue pv = this.getPropertyValue("itemDao", propertyValues);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> pvs){
        for (PropertyValue pv : pvs){
            if(pv.getName().equals(name))
                return pv;
        }
        return null;
    }
}
