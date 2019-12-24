package org.litespring.core.io;

import org.litespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: Using this to package a resource file in
 * the class-path and get the InputStream of the resource.
 * <p>
 * If you want to package a resource with an exact path, use another:
 *
 * @author ShaoJiale
 * date 2019/12/11
 * @see FileSystemResource
 */
public class ClassPathResource implements Resource {
    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null) ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream(this.path);
        if (is == null)
            throw new FileNotFoundException(path + " cannot be found");
        return is;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
