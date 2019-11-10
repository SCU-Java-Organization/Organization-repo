package annotation.nameCheck;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner9;
import javax.tools.Diagnostic;
import java.util.EnumSet;

import static javax.lang.model.element.Modifier.*;

/**
 * @author ShaoJiale
 * @create 2019/11/10
 * @function 名称检查插件,如果程序命名不符合规范，将会输出一个编译器的Warning信息
 */
public class NameChecker {
    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processingEnv){
        this.messager = processingEnv.getMessager();
    }

    public void checkNames(Element element){
        nameCheckScanner.scan(element);
    }

    private class NameCheckScanner extends ElementScanner9<Void, Void>{

        /**
         * @function 检查Java类名称
         * @param e 类节点
         * @param p
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void p) {
            scan(e.getTypeParameters(), p);
            checkCamelCase(e, true);
            super.visitType(e, p);
            return null;
        }

        /**
         * @function 检查方法命名
         * @param e
         * @param p
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void p) {
            if(e.getKind() == ElementKind.METHOD){
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING,
                            "一个普通方法 " + name + " 不应当与类名重复", e);
                }
                checkCamelCase(e, false);
            }

            super.visitExecutable(e, p);
            return null;
        }

        /**
         * @function 检查变量名是否合法
         * @param e
         * @param aVoid
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if(e.getKind() == ElementKind.ENUM_CONSTANT
                    || e.getConstantValue() != null
                    || heuristicallyConstant(e))
                checkAllCaps(e);
            else
                checkCamelCase(e, false);
            return null;
        }

        /**
         * @function 判读一个变量是否是常量
         * @param e
         * @return
         */
        private boolean heuristicallyConstant(VariableElement e){
            if(e.getEnclosingElement().getKind() == ElementKind.INTERFACE)
                return true;
            else if (e.getKind() == ElementKind.FIELD
                    && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL)))
                return true;
            else
                return false;
        }


        /**
         * @function 检查传入的名称是否符合驼峰命名法
         * @param e
         * @param initialCaps 变量名的首字母是否需要大写
         */
        private void checkCamelCase(Element e, boolean initialCaps){
            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if(Character.isUpperCase(firstCodePoint)){
                previousUpper = true;
                if(!initialCaps){
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + " 应当以小写字母开头", e);
                    return;
                }
            }else if (Character.isLowerCase(firstCodePoint)){
                if(initialCaps){
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + " 应当以大写字母开头", e);
                    return;
                }
            }else
                conventional = true;

            if(conventional){
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length() ; i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if(Character.isUpperCase(cp)){
                        if (previousUpper){
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    }else
                        previousUpper = false;
                }

                if(!conventional)
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 " + name + "应当符合驼峰命名法", e);
            }
        }

        /**
         * @function 大写命名检查，要求第一个字符必须是大写的英文字母，其余部分可以是下划线或大写字母
         * @param e
         */
        private void checkAllCaps(Element e){
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if(!Character.isUpperCase(firstCodePoint))
                conventional = false;
            else{
                // 以下划线开头
                boolean previousUnderscore = false;
                int cp = firstCodePoint;

                for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(cp == (int)'_'){
                        if(previousUnderscore){
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else{
                        previousUnderscore = false;
                        if(!Character.isUpperCase(cp) && !Character.isDigit(cp)){
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if(!conventional)
                messager.printMessage(Diagnostic.Kind.WARNING, "常量 " + name + "应当全部以大写字母或下划线命名，并且以字母开头", e);
        }
    }
}
