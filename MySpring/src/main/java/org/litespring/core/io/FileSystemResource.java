package org.litespring.core.io;

import org.litespring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: Using this to package a resource file in
 * the file-system and get the InputStream of the resource.
 * <p>
 * If you want to package a resource through class-path, use another:
 *
 * @author ShaoJiale
 * date 2019/12/11
 * @see ClassPathResource
 */
public class FileSystemResource implements Resource {
    private final String path;
    private final File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "Path must not be null");
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        this.path = file.getPath();
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getDescription() {
        return "file [ " + this.file.getAbsolutePath() + " ]";
    }
}
