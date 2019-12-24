package org.litespring.core.type;


import asm.ClassVisitor;
import asm.Opcodes;
import asm.SpringAsmInfo;
import org.litespring.util.ClassUtils;

/**
 * Description: get information of a class from asm.
 * This is the 2nd step of resolving annotations: resolving
 * annotations in the Resources with ASM.
 * <p>
 * The 1st step is resolving classes of a exact package and
 * turn them into Resources.
 *
 * @author ShaoJiale
 * date 2019/12/20
 * @see org.litespring.core.io.support.PackageResourceLoader
 * @see org.litespring.core.type.ClassMetadata
 * @see org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor
 */
public class ClassMetadataReadingVisitor extends ClassVisitor
        implements ClassMetadata {
    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;

    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }

    /**
     * Get information from asm and set the information into this class.
     * This method will not be called until asm.ClassReader.accept() is called.
     *
     * @param version        compile version of a class
     * @param access         access type - public, private, protected or default
     * @param name           resource path of the class
     * @param signature      unused here
     * @param superClassName resource path of the super class
     * @param interfaces     resource path of the interfaces
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superClassName, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (superClassName != null)
            this.superClassName = ClassUtils.convertResourcePathToClassName(superClassName);

        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    public String getClassName() {
        return className;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaces;
    }

    @Override
    public boolean hasSuperClass() {
        return this.superClassName != null;
    }
}
