package org.litespring.test.version1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.InputStream;

/**
 * Description: Test if Resource works
 *
 * @see org.litespring.core.io
 * @author ShaoJiale
 * date 2019/12/11
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception{
        Resource resource = new ClassPathResource("petstore-v1.xml");

        InputStream is = null;

        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if(is != null)
                is.close();
        }
    }

    @Test
    public void testFileSystemResource() throws Exception{
        Resource resource = new FileSystemResource("src\\test\\resources\\petstore-v1.xml");

        InputStream is = null;

        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if(is != null)
                is.close();
        }
    }
}
