package org.litespring.core.type.classreading;

import asm.AnnotationVisitor;
import asm.ClassVisitor;
import asm.Type;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadataReadingVisitor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description: get annotation information from a class file with asm.
 * <p>
 * This class is a sub-class of ClassMetadataReadingVisitor which means
 * we still use ClassMetadataReadingVisitor.visit() to resolve class file
 * when asm.ClassReader.accept(ClassVisitor, int) is called.
 * <p>
 * But we overwrote visitAnnotation(String, boolean) method, which is
 * used to resolve annotations from class file.
 *
 * @author ShaoJiale
 * date 2019/12/20
 * @see asm.ClassReader#accept(ClassVisitor, int)
 * @see org.litespring.core.type.ClassMetadataReadingVisitor
 * @see org.litespring.core.type.AnnotationMetadata
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor
        implements AnnotationMetadata {

    private final Set<String> annotationSet = new LinkedHashSet<>(4);

    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>(4);

    public AnnotationMetadataReadingVisitor() {

    }

    /**
     * resolve annotations from class file when asm.ClassReader.accept(ClassVisitor, int)
     * is called. We'll add class name of the annotations to the annotation set, and return
     * a new visitor which is used to resolve attributes of the annotations.
     *
     * @param desc    a description path of the annotation class
     * @param visible if the annotation class is visible
     * @return visitor for resolving attributes of the annotations
     */
    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.attributesMap);
    }

    public Set<String> getAnnotationTypes() {
        return annotationSet;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }

    @Override
    public boolean hasSuperClass() {
        return false;
    }
}
