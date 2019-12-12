package org.litespring.test.version1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.version1.PetStoreService;

/**
 * Description: Some tests for ApplicationContext
 *
 * @see ApplicationContext
 * @author ShaoJiale
 * date 2019/12/11
 */
public class ApplicationContextTest {
    @Test
    public void testGetBean(){
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)context.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext(){
        ApplicationContext context = new FileSystemXmlApplicationContext("src\\test\\resources\\petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)context.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
}
