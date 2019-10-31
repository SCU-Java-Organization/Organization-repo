package JDBC.SimpleConnectionPool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author ShaoJiale
 * @create 2019/10/21
 * @function 《并发编程的艺术》中的简单实现
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initSize){
        if(initSize > 0){
            for(int i = 0; i < initSize; i++)
                pool.addLast(ConnectionDriver.createConnection());
        }
    }

    /**
     * @function 归还连接到连接池中
     * @param connection
     * @Thinking 在归还时需要通知所有线程，让消费者感知到归还操作
     */
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    /**
     * 在限制时间内无法获取Connection则返回null
     * @param millis 限制等待的最大时间
     * @return null / Connection
     * @throws InterruptedException
     */
    public Connection fetchConnection(long millis) throws InterruptedException{
        synchronized (pool){
            // 完全超时等待
            if(millis <= 0){
                while (pool.isEmpty())
                    pool.wait();
                return pool.removeFirst();
            }
            else {
                long future = System.currentTimeMillis() + millis;
                long remaining = millis;
                while (pool.isEmpty() && remaining > 0){
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                /**
                 * @Status 连接池非空
                 * @Status 连接池空，但等待超时
                 */
                Connection result = null;
                if(!pool.isEmpty())
                    result = pool.removeFirst();
                return result;
            }
        }
    }
}
