package org.litespring.core.type.classreading;

import asm.AnnotationVisitor;
import asm.SpringAsmInfo;
import org.litespring.core.annotation.AnnotationAttributes;

import java.util.Map;

/**
 * Description: resolve attributes of a annotation
 * When asm.ClassReader.accept(String, int) is called,
 * AnnotationMetaReadingVisitor.visitAnnotation(String, boolean) will be
 * called to resolve class names of annotations and return an instance of
 * this class. Then asm will use visitEnd() method to resolve attributes
 * of the annotations.
 *
 * @author ShaoJiale
 * date 2019/12/20
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);
        this.annotationType = annotationType;
        this.attributesMap = attributesMap;
    }

    /**
     * after resolving all the attributes of the current
     * annotation, asm will call this method. And we will
     * put the attributes into a map.
     */
    @Override
    public final void visitEnd() {
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    /**
     * this method will be called when asm is resolving each
     * attribute of the current annotation.
     * @param attributeName name of the attribute
     * @param attributeValue value of the attribute
     */
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }
}
