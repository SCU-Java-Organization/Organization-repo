package org.litespring.test.version1;

import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.factory.exception.BeanCreationException;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.exception.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.service.version1.PetStoreService;
import static org.junit.Assert.*;

/**
 * Description: TDD
 *
 * @author ShaoJiale
 * date 2019/12/10
 */
public class BeanFactoryTest {
    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean(){
        reader.loadBeanDefinitions("petStore-v1.xml");

        BeanDefinition definition = factory.getBeanDefinition("petStore");

        // is singleton ?
        assertTrue(definition.isSingleton());

        // or prototype ?
        assertFalse(definition.isPrototype());

        // default scope is singleton ?
        assertEquals(definition.SCOPE_DEFAULT, definition.getScope());

        // test class name
        assertEquals("org.litespring.service.version1.PetStoreService", definition.getBeanClassName());

        // get a bean
        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");
        assertNotNull(petStore);

        // get another bean
        PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");

        // is it really singleton ?
        assertTrue(petStore.equals(petStore1));
    }

    @Test
    public void testInvalidBean(){
        reader.loadBeanDefinitions("petStore-v1.xml");

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
            reader.loadBeanDefinitions("invalid.xml");
        } catch (BeanDefinitionStoreException e){
            return;
        }
        fail("cannot catch BeanDefinitionStoreException!");
    }
}
