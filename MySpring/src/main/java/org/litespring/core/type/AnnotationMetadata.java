package org.litespring.core.type;

import org.litespring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * Description: A interface for packing asm and visitor
 *
 * @author ShaoJiale
 * date 2019/12/24
 * @see org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor
 */
public interface AnnotationMetadata extends ClassMetadata {
    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    public AnnotationAttributes getAnnotationAttributes(String annotationType);
}
