package org.litespring.test.version3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.version3.PetStoreService;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/18
 */
public class ApplicationContextTestV3 {
    @Test
    public void testGetBeanProperty(){
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService)context.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertEquals("ShaoJiale", petStore.getOwner());
        Assert.assertEquals(3, petStore.getVersion());
    }
}
