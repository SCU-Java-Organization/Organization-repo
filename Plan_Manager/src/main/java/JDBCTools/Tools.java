package JDBCTools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Tools {
    static {
        Properties properties = new Properties();
        InputStream in = Tools.class.getClassLoader().getResourceAsStream("database.properties");
        try {
            properties.load(in);
        } catch (IOException e){
            e.printStackTrace();
        }
        JDBC_DRIVER = properties.getProperty("Driver");
        DB_URL = properties.getProperty("url");
        USER = properties.getProperty("username");
        PASSWORD = properties.getProperty("password");
    }

    static final String JDBC_DRIVER;
    static final String DB_URL;
    static final String USER;
    static final String PASSWORD;

    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}
