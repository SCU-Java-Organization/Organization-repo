package org.litespring.context.support;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/**
 * Description: Get config file from class path
 * <p>
 * Another subclass of AbstractApplicationContext
 *
 * @author ShaoJiale
 * date 2019/12/11
 * @see FileSystemXmlApplicationContext
 * @see AbstractApplicationContext
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path, this.getBeanClassLoader());
    }
}
