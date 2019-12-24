package org.litespring.test.version4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.net.URL;

/**
 * Description:
 *
 * @author ShaoJiale
 * date 2019/12/19
 */
public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring.dao.version4");
        Assert.assertEquals(2, resources.length);
    }

    @Test
    public void test() {
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        URL url = loader.getResource("org/litespring/dao/version4");
        System.out.println(url);
    }
}
