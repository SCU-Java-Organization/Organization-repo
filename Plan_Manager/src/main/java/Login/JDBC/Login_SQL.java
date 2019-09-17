package Login.JDBC;

import JDBCTools.Tools;

import java.sql.*;

public class Login_SQL {
    public static String loginCheck(String username, String password) {
        Connection connection = null;
        Statement statement = null;
        String sqlPassword = "";
        try {
            connection = Tools.getConnection();

//            执行查询
            statement = connection.createStatement();
            String sql = "SELECT password FROM users WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);

//            获取结果
            if(!resultSet.next())
                return null;

            sqlPassword = resultSet.getString("password");
//            System.out.println("Password in database is: " + sqlPassword);
//            System.out.println("Your input password is: " + password);

//            关闭对象释放资源
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
//            处理Class.forName错误
            e.printStackTrace();
        } finally {
//            关闭资源
            try {
                if(statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       return sqlPassword;
    }

    public static String signUp(String username, String password, String name) {
        Connection connection = null;
        Statement statement = null;
        String sqlPassword = "";
        String info = "SUCCESS";

        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();
            String sql = "SELECT username FROM users WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                System.out.println("用户名: " + username + "已存在");
                info = "EXIST";
            }
            else {
                sql = "INSERT INTO users (username, password, realname, plan_amount, complete_amount, fail_amount) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1,username);
                pst.setString(2,password);
                pst.setString(3,name);
                pst.setInt(4,0);
                pst.setInt(5,0);
                pst.setInt(6,0);
                pst.executeUpdate();

                sql = "INSERT INTO months (username, week1_create, week1_delete, week1_complete," +
                        "week2_create, week2_delete, week2_complete," +
                        "week3_create, week3_delete, week3_complete," +
                        "week4_create, week4_delete, week4_complete) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst2 = connection.prepareStatement(sql);
                pst2.setString(1, username);
                for(int i = 2; i < 14; i++)
                    pst2.setInt(i, 0);
                pst2.executeUpdate();

                pst.close();
                pst2.close();
            }
//            关闭对象释放资源
            resultSet.close();
        } catch (Exception e) {
//            处理Class.forName错误
            e.printStackTrace();
        } finally {
//            关闭资源
            try {
                if(statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return info;
        }
    }

    public static void updatePsd(String username, String password){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Tools.getConnection();

//            执行查询
            statement = connection.createStatement();
            String sql = "UPDATE users set password = '"+password+"' WHERE username = '"+username+"'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            pst.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
//            处理Class.forName错误
            e.printStackTrace();
        } finally {
//            关闭资源
            try {
                if(statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
