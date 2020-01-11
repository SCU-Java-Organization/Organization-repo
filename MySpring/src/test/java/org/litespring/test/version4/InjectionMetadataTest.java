package org.litespring.test.version4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.annotation.AutowiredFieldElement;
import org.litespring.beans.factory.annotation.InjectionElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.dao.version4.AccountDao;
import org.litespring.dao.version4.ItemDao;
import org.litespring.service.version4.PetStoreService;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class InjectionMetadataTest {
    @Test
    public void testInjection() throws NoSuchFieldException {
        // load bean definitions
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        // get bean class and dependencies
        Class<?> clazz = PetStoreService.class;
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();

        // get first element
        {
            Field field = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElem = new AutowiredFieldElement(field, true, factory);
            elements.add(injectionElem);
        }

        // get second element
        {
            Field field = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElem = new AutowiredFieldElement(field, true, factory);
            elements.add(injectionElem);
        }

        InjectionMetadata metadata = new InjectionMetadata(clazz, elements);

        PetStoreService petStore = new PetStoreService();

        // inject dependencies into a bean instance
        metadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }
}
