package org.litespring.test.version4;

import asm.ClassReader;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.type.ClassMetadataReadingVisitor;
import org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/20
 */
public class ClassReaderTest {
    @Test
    public void testGetClassMetaData() throws Exception {
        ClassPathResource resource = new ClassPathResource("org/litespring/service/version4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertEquals("org.litespring.service.version4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnotation() throws Exception {
        ClassPathResource resource = new ClassPathResource("org/litespring/service/version4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "org.litespring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));
    }
}