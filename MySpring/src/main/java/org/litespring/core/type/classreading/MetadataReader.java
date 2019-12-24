package org.litespring.core.type.classreading;

import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;

/**
 * Description: A interface for packing asm and visitor
 *
 * @author ShaoJiale
 * date 2019/12/24
 * @see SimpleMetadataReader
 */
public interface MetadataReader {
    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetadata getAnnotationMetadata();
}
