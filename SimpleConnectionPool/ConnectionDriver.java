package JDBC.SimpleConnectionPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author ShaoJiale
 * @create 2019/10/21
 * @function
 */
public class ConnectionDriver {
    /**
     * @function 创建动态代理
     * @function 在调用commit方法时休眠100毫秒
     */
    static class ConnectionHandler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit"))
                TimeUnit.MILLISECONDS.sleep(100);
            return null;
        }
    }

    /**
     * 返回一个有动态代理的Connection
     * @return
     */
    public static final Connection createConnection(){
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class<?>[] {Connection.class},
                new ConnectionHandler());
    }
}
