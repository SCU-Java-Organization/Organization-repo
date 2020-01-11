package org.litespring.test.version4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.annotation.AutowireAnnotationProcessor;
import org.litespring.beans.factory.annotation.AutowiredFieldElement;
import org.litespring.beans.factory.annotation.InjectionElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.dao.version4.AccountDao;
import org.litespring.dao.version4.ItemDao;
import org.litespring.service.version4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2020/1/10
 */
public class AutowireAnnotationProcessorTest {
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();

    DefaultBeanFactory beanFactory = new DefaultBeanFactory() {
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDao.class))
                return accountDao;
            if (descriptor.getDependencyType().equals(ItemDao.class))
                return itemDao;
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };


    @Test
    public void testGetInjectionMetadata() {
        AutowireAnnotationProcessor processor = new AutowireAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements, "accountDao");
        assertFieldExists(elements, "itemDao");


        PetStoreService petStore = new PetStoreService();

        // inject dependencies into a bean instance
        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for (InjectionElement ele : elements) {
            AutowiredFieldElement fieldElement = (AutowiredFieldElement) ele;
            Field field = fieldElement.getField();
            if (field.getName().equals(fieldName))
                return;
        }
        Assert.fail(fieldName + " does not exist!");
    }
}
