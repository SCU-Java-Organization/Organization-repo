package JDBC;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ShaoJiale
 * @create 2019/10/19
 * @function 数据源，保留数据库连接池的基本配置
 */
public class DataSource {
    private static final String DRIVER;
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;
    private int maxConnection = 10;
    private int minConnection = 5;
    private int timeout;

    /**
     * 在创建对象之前初始化对象
     * 从properties文件中读取连接信息
     * 再创建一定数量的连接对象放入连接池中
     */
    static {
        Properties properties = new Properties();
        InputStream in = ConnectionPool.class.getClassLoader().getResourceAsStream("dbPool.properties");
        try {
            properties.load(in);
        } catch (IOException e){
            e.printStackTrace();
        }
        DRIVER = properties.getProperty("Driver");
        URL = properties.getProperty("url");
        USER = properties.getProperty("username");
        PASSWORD = properties.getProperty("password");
    }

    /**
     * 可以自定义数据库连接池的连接数量、超时时长
     * 数据库连接配置只能从dbPool.properties中更改
     */
    public DataSource(int maxConnection, int minConnection, int timeout) {
        this.maxConnection = maxConnection;
        this.minConnection = minConnection;
        this.timeout = timeout;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public int getMinConnection() {
        return minConnection;
    }

    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public static String getDRIVER() {
        return DRIVER;
    }

    public static String getURL() {
        return URL;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }
}
