package aop.service;

/**
 * @author ShaoJiale
 * @create 2019/11/7
 * @function 切面逻辑的接口
 *  所有切面方法都需要实现这个接口
 */
public interface MethodInvocation {
    void invoke();
}
