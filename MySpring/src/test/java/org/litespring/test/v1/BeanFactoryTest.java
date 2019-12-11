package org.litespring.test.v1;

import org.junit.Test;
import org.litespring.beans.BeanCreationException;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;
import static org.junit.Assert.*;

/**
 * description: TDD
 * @author ShaoJiale
 * date 2019/12/10
 */
public class BeanFactoryTest {
    @Test
    public void testGetBean(){
        BeanFactory factory = new DefaultBeanFactory("petStore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        // 判断类名是否正确
        assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());

        // 判断类示例是否正确加载
        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);
        assertEquals(petStore.getOwner(), "SJL");
    }

    @Test
    public void testInvalidBean(){
        BeanFactory factory = new DefaultBeanFactory("petStore-v1.xml");

        try{
            factory.getBean("invalidBean");
        } catch (BeanCreationException e){
            return;
        }
        fail("cannot catch BeanCreationException!");
    }

    @Test
    public void testInvalidXML(){
        try {
            new DefaultBeanFactory("invalid.xml");
        } catch (BeanDefinitionStoreException e){
            return;
        }
        fail("cannot catch BeanDefinitionStoreException!");
    }
}
