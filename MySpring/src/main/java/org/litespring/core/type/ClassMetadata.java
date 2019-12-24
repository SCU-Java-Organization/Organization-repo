package org.litespring.core.type;

/**
 * Description: A interface for packing asm and visitor
 *
 * @author ShaoJiale
 * date 2019/12/24
 */
public interface ClassMetadata {
    String getClassName();

    boolean isInterface();

    boolean isAbstract();

    boolean isFinal();

    boolean hasSuperClass();

    String getSuperClassName();

    String[] getInterfaceNames();
}
