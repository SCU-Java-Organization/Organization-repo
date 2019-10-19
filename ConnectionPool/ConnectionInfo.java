package JDBC;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ShaoJiale
 * @create 2019/10/18
 * @function 连接池中的一个连接
 */
public class ConnectionInfo {
    private int id = 0;
    private AtomicInteger atomicId = new AtomicInteger(0);

    private Connection connection;

    private volatile boolean isAvailable;

    public ConnectionInfo(Connection connection) {
        this(connection, true);
    }

    public ConnectionInfo(Connection connection, boolean isAvailable) {
        this.connection = connection;
        this.isAvailable = isAvailable;
        this.id = atomicId.getAndIncrement();
    }

    public int getId(){
        return id;
    }

    public Connection getConnection(){
        return connection;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "id=" + id +
                ", atomicId=" + atomicId +
                ", connection=" + connection +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
