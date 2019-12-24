package org.litespring.core.type.classreading;

import asm.ClassReader;
import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: A implement class for MetadataReader
 * We use this class to pack asm so that we do not
 * need to care about those complex codes in asm or
 * visitors.
 *
 * @author ShaoJiale
 * date 2019/12/24
 * @see org.litespring.core.type.classreading.MetadataReader
 */
public class SimpleMetadataReader implements MetadataReader {
    private final Resource resource;

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;

    /**
     * core constructor of this class
     * we packed visitor and asm here
     * when the instance is created.
     *
     * @param resource resource of classes
     * @throws IOException we have to do IO operations here
     */
    public SimpleMetadataReader(Resource resource) throws IOException {
        InputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;

        try {
            classReader = new ClassReader(is);
        } finally {
            is.close();
        }

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
