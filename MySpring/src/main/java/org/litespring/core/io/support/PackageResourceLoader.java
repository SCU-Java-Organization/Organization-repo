package org.litespring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.util.Assert;
import org.litespring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Description: Load classes of a package and convert
 * each of them into a FileSystemResource.
 * With this class, we can do an important operation
 * which is "component-scan".
 *
 * @author ShaoJiale
 * date 2019/12/19
 */
public class PackageResourceLoader {
    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    /**
     * Get Resources from a package
     *
     * @param basePackage the name of the package
     * @return an array of Resource
     * @throws IOException we have to do I/O operations with files
     */
    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "base package must not be null");

        // replace '.' with '/'
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);

        // get file with the location
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);     // get absolute path
        File rootDir = new File(url.getFile()); // get file

        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for (File file : matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    /**
     * Test if the current root directory is available,
     * and if it's available, we'll call another method
     * to retrieve files from the root directory.
     *
     * @param rootDir root directory
     * @return a set which contains files of the root directory
     * @throws IOException we have to do I/O operations with files
     */
    private Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
        if (!rootDir.exists()) {
            if (logger.isDebugEnabled())
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
            return Collections.emptySet();
        }

        if (!rootDir.isDirectory()) {
            if (logger.isWarnEnabled())
                logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            return Collections.emptySet();
        }

        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled())
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            return Collections.emptySet();
        }

        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    /**
     * When the root is available, use this method to resolve classes.
     *
     * @param dir    root directory
     * @param result a {@code Set} for the classes
     * @throws IOException we have to do I/O operations with files
     */
    private void doRetrieveMatchingFiles(File dir, Set<File> result) throws IOException {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (logger.isWarnEnabled())
                logger.warn("Couldn't retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            return;
        }

        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!content.canRead()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Skipping [" + dir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
