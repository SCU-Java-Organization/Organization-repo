package JDBC;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ShaoJiale
 * @create 2019/10/18
 * @function 简易数据库连接池的实现
 * @Thinking 预先在缓冲池中放入一定数量的连接，当需要建立连接时只需从中取出一个，使用完毕后放回去
 * @Thinking 当请求的连接数量超过最大数量时，这些请求将被加入等待队列中
 * @Upgrade 若等待超时后还不能拿到连接，则新建一个连接给请求者，用完后销毁
 */
public class ConnectionPool implements Pool{
    // 日志信息
    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    // 连接池的基本配置信息
    @Autowired
    private DataSource dataSource;

    // 连接池
    private final BlockingQueue<ConnectionInfo> blockingQueue;

    // 连接池大小
    private AtomicInteger poolSize = new AtomicInteger(0);

    // 判断连接池是否被初始化
    private AtomicBoolean isInitialized;

    // 判断连接池是否已经销毁
    private AtomicBoolean isDestroyed = new AtomicBoolean(false);

    // 允许的重连次数
    private int tryTimes;

    // 每次扩容的扩容量
    private int expansion;

    public ConnectionPool(DataSource dataSource, BlockingQueue<ConnectionInfo> blockingQueue) {
        this(dataSource, blockingQueue, 3);
    }

    public ConnectionPool(DataSource dataSource, BlockingQueue<ConnectionInfo> blockingQueue, int tryTimes) {
        this(dataSource, blockingQueue, tryTimes, 2);
    }

    public ConnectionPool(DataSource dataSource, BlockingQueue<ConnectionInfo> blockingQueue, int tryTimes, int expansion) {
        this.dataSource = dataSource;
        this.blockingQueue = blockingQueue;
        this.tryTimes = tryTimes;
        this.isInitialized = new AtomicBoolean(false);
        this.expansion = expansion;
        init();
    }

    /**
     * @function 初始化连接池
     */
    public void init(){
        if(isInitialized.compareAndSet(false, true)){
            logger.error("开始初始化连接池");
            try{
                Class driver = Class.forName(dataSource.getDRIVER());
                DriverManager.registerDriver((Driver)driver.getDeclaredConstructor().newInstance());
            } catch (ClassNotFoundException e){
                e.printStackTrace();
                throw new RuntimeException(e + "数据库驱动错误");
            } catch (NoSuchMethodException e){
                e.printStackTrace();
                throw new RuntimeException(e + "数据库驱动注册失败");
            } catch (InstantiationException e){
                e.printStackTrace();
                throw new RuntimeException(e + "数据库驱动注册失败");
            } catch (IllegalAccessException e){
                e.printStackTrace();
                throw new RuntimeException(e + "数据库驱动注册失败");
            } catch (InvocationTargetException e){
                e.printStackTrace();
                throw new RuntimeException(e + "数据库驱动注册失败");
            } catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e + "数据库驱动注册失败");
            }

            String URL = dataSource.getURL();
            String USER = dataSource.getUSER();
            String PASSWORD = dataSource.getPASSWORD();

            for(int i = 0; i < dataSource.getMinConnection(); i++){
                try{
                    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    blockingQueue.add(new ConnectionInfo(connection));
                    poolSize.getAndIncrement();
                } catch (SQLException e){
                    logger.error("获取一条数据库连接失败", e);
                    while (tryTimes > 0){
                        try{
                            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                            blockingQueue.add(new ConnectionInfo(connection));
                            poolSize.getAndIncrement();
                        } catch (SQLException e1){
                            e1.printStackTrace();
                            logger.error("重新获取数据库连接失败");
                        }
                        tryTimes--;
                        logger.error("重试次数剩余: " + tryTimes, e);
                    }
                }
            }
        }
        else {
            logger.warn("连接池正在初始化或已经被初始化，不能重复初始化");
            throw new RuntimeException("连接池不能被重复初始化");
        }
    }

    /**
     * @function 获取连接
     * @Thinking 获取连接时不能保证当连接池大小小于最大连接数时能立马获取连接
     * @Thinking 但保证最终能获取到连接
     * @Point 利用阻塞队列来实现并发获取连接
     * @Point 利用原子PoolSize来实现并发扩容
     * @return
     */
    public ConnectionInfo getConnectionInfo() throws PoolDestroyException{
        if(!isDestroyed.get()){
            try {
                ConnectionInfo connectionInfo = blockingQueue.poll();
                // 连接池已空
                if(connectionInfo == null) {
                    // 连接池无法扩容，则开始阻塞等待，直到连接池非空
                    if (poolSize.get() >= dataSource.getMaxConnection())
                        connectionInfo = blockingQueue.take();
                    // 连接池可以扩容，则进行扩容操作
                    else{
                        int currentSize;
                        int afterSize;
                        int newSize;

                        while(true){
                            currentSize = poolSize.get();
                            afterSize = currentSize + expansion;
                            newSize = afterSize > dataSource.getMaxConnection() ? dataSource.getMaxConnection() : afterSize;

                            int difference = newSize - currentSize;
                            if(poolSize.compareAndSet(currentSize, newSize)){
                                for(int i = 0; i < difference; i++)
                                    blockingQueue.add(getNewConnectionInfo());
                                /**
                                 * @Key break
                                 * @Status 1.扩容完成，直接退出
                                 * @Status 2.容量达到最大，不进行扩容，直接退出
                                 */
                                break;
                            }
                            /**
                             * 注意此处扩容时若竞争失败，则重新扩容
                             * 直至容量达到最大
                             */
                        }
                        return blockingQueue.take();
                    }
                }
                return connectionInfo;
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        throw new PoolDestroyException("连接池已经被销毁");
    }

    /**
     * @function 将被归还连接的'连接信息'添加到连接池中
     * @param connectionInfo 被归还连接对应的'连接信息'
     * @return
     */
    public boolean releaseConnectionInfo(ConnectionInfo connectionInfo) {
        blockingQueue.add(connectionInfo);
        return true;
    }

    /**
     * @function 真正意义上的归还Connection连接
     * @Thinking 依靠poolSize来确保连接池大小
     * @Thinking 在归还失败时，poolSize应当减一
     * @Thinking 在归还空值时，将会抛出异常
     * @param connection 被归还的连接
     * @return
     */
    public boolean releaseConnection(Connection connection) {
        if(connection == null)
            throw new RuntimeException("归还的连接请勿为空");

        // 归还是否失败的标志
        boolean flag = false;
        try {
            // 归还失败，连接失效
            if(connection.isClosed()){
                //poolSize.getAndDecrement();
                flag = true;
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            // 归还成功，连接回到池中
            //if(!flag)
            //    poolSize.getAndIncrement();
        }
        // 归还连接后，将连接信息加入到连接池中
        return releaseConnectionInfo(new ConnectionInfo(connection));
    }

    /**
     * @function 返回连接池大小
     * @return size
     */
    public int getPoolSize() {
        return poolSize.get();
    }

    /**
     * @NotFinished 尚未完成
     * @function 销毁连接池
     * @Thinking 逐个关闭连接池中的连接，将poolSize置0
     * @Thinking 此处需要验证连接池中是否有借出的连接
     * @Thinking 若有未归还的连接，则必须等待对方归还
     * @return success / failure
     */
    public boolean destroy() {
       isDestroyed.compareAndSet(false, true);
       return true;
    }

    /**
     * @function 得到可用的连接数
     * @return available connections in blocking-queue
     */
    public int getAvailableNum(){
        return blockingQueue.size();
    }

    /**
     * @function 获取一个新的连接，及其对应的'连接信息'
     * @return
     */
    private ConnectionInfo getNewConnectionInfo(){
        try {
            Connection connection = DriverManager.getConnection(dataSource.getURL(), dataSource.getUSER(), dataSource.getPASSWORD());
            return new ConnectionInfo(connection);
        } catch (SQLException e){
            e.printStackTrace();
            while(tryTimes > 0){
                try {
                    Connection connection = DriverManager.getConnection(dataSource.getURL(), dataSource.getUSER(), dataSource.getPASSWORD());
                    return new ConnectionInfo(connection);
                } catch (SQLException e1){
                    e1.printStackTrace();
                    logger.error("重新获取数据库连接仍然失败", e);
                }
                tryTimes--;
                logger.error("重试次数剩余: " + tryTimes, e);
            }
            throw new RuntimeException("重试次数用完，获取连接失败");
        }
    }
}
