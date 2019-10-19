package JDBC;

/**
 * @author ShaoJiale
 * @create 2019/10/19
 * @function 自定义线程池销毁异常
 */
public class PoolDestroyException extends Exception {
    public PoolDestroyException(){}
    public PoolDestroyException(String s){
        super(s);
    }
}
