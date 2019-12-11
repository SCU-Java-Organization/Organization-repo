package org.litespring.core.io;

import java.io.InputStream;

/**
 * Description: Describe the xml config file path
 * We always have 2 ways to describe a file path:
 * 1.Class-path
 * 2.File-System-Path
 *
 * @see ClassPathResource
 * @see FileSystemResource
 * @author ShaoJiale
 * date 2019/12/11
 */
public interface Resource {
    InputStream getInputStream() throws Exception;
    String getDescription();
}
