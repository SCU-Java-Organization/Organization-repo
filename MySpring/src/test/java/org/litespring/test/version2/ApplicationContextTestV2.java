package org.litespring.test.version2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.version2.AccountDao;
import org.litespring.dao.version2.ItemDao;
import org.litespring.service.version2.PetStoreService;

/**
 * Description: ApplicationContext version 2
 * including property.
 * @version 2
 * @author ShaoJiale
 * date 2019/12/12
 */
public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty(){
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService)context.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);

        Assert.assertEquals("ShaoJiale", petStore.getOwner());
        Assert.assertEquals(2, petStore.getVersion());
    }
}
