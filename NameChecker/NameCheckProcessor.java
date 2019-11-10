package annotation.nameCheck;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author ShaoJiale
 * @create 2019/11/10
 * @function 自定义注解处理器
 */
@SupportedAnnotationTypes("*")
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    /**
     * @function 初始化名称检查插件
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    /**
     * @function 对输入的语法树的各个节点进行名称检查
     * @param annotations
     * @param roundEnv
     * @return false, 不改变语法树的内容
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(!roundEnv.processingOver()){
            for(Element element : roundEnv.getRootElements())
                nameChecker.checkNames(element);
        }
        return false;
    }
}
