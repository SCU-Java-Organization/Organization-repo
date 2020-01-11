package org.litespring.test.version4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.dao.version4.AccountDao;
import org.litespring.service.version4.PetStoreService;

import java.lang.reflect.Field;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class DependencyDescriptorTest {
    @Test
    public void testResolveDependency() throws NoSuchFieldException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Field field = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(field, true);
        Object obj = factory.resolveDependency(descriptor);
        Assert.assertTrue(obj instanceof AccountDao);
    }
}
