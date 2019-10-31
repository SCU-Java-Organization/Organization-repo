package JDBC;

import java.sql.Connection;

/**
 * @author ShaoJiale
 * @create 2019/10/18
 * @function 连接池接口，定义基本功能
 */
public interface Pool {
    /**
     * 获取池中连接的"连接信息"
     * @return ConnectionInfo
     */
    ConnectionInfo getConnectionInfo() throws PoolDestroyException;

    /**
     * 释放连接后更新该连接的"连接信息"
     * @param connectionInfo
     * @return success / failure
     */
    boolean releaseConnectionInfo(ConnectionInfo connectionInfo);

    /**
     * 返回连接池大小
     * @return size of pool
     */
    int getPoolSize();

    /**
     * 销毁连接池
     * @return success / failure
     */
    boolean destroy();

    /**
     * 归还连接
     * @param connection 被归还的连接
     * @return success / failure
     */
    boolean releaseConnection(Connection connection);
}
