package CreatePlan.JDBC;

import JDBCTools.Tools;

import java.sql.*;


public class CreatePlan_SQL {

    public static void monthCheck(int WEEK, String username) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Tools.getConnection();

            String sql = null;
            String resultLabel = null;
            switch (WEEK){
                case 1:
                    sql = "SELECT week1_create FROM months WHERE username = '"+username+"'";
                    resultLabel = "week1_create";
                    break;
                case 2:
                    sql = "SELECT week2_create FROM months WHERE username = '"+username+"'";
                    resultLabel = "week2_create";
                    break;
                case 3:
                    sql = "SELECT week3_create FROM months WHERE username = '"+username+"'";
                    resultLabel = "week3_create";
                    break;
                case 4: case 5:
                    sql = "SELECT week4_create FROM months WHERE username = '"+username+"'";
                    resultLabel = "week4_create";
                    break;
                default:
                    break;
            }

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int num = resultSet.getInt(resultLabel) + 1;

            switch (WEEK){
                case 1:
                    sql = "UPDATE months SET week1_create = '"+num+"' WHERE username = '"+username+"'";
                    break;
                case 2:
                    sql = "UPDATE months SET week2_create = '"+num+"' WHERE username = '"+username+"'";
                    break;
                case 3:
                    sql = "UPDATE months SET week3_create = '"+num+"' WHERE username = '"+username+"'";
                    break;
                case 4: case 5:
                    sql = "UPDATE months SET week4_create = '"+num+"' WHERE username = '"+username+"'";
                    break;
                default:
                    break;
            }
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
//            处理Class.forName错误
            e.printStackTrace();
        } finally {
//            关闭资源
            try {
                if (statement != null)
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

    public static void insertPlan(String username, String name, java.util.Date begin, java.util.Date end, int dayOff, String description, int lastDays){
        Connection connection = null;
        Statement statement = null;
        System.out.println("Description = " + description);
        System.out.println("Name = " + name);
        Date beginDate = new Date(begin.getTime());
        Date endDate = new Date(end.getTime());
        System.out.println("SqlDate = " + beginDate);
        System.out.println("SqlDate = " + endDate);
        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();
            String sql = "INSERT INTO plans (username, name, begin, end, status, dayoff, description, attend_days, attend_flag, off_flag, last_days) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,username);
            pst.setString(2,name);
            pst.setDate(3,beginDate);
            pst.setDate(4,endDate);
            java.util.Date current = new java.util.Date();
            if(current.before(begin))
                pst.setString(5,"未开始");
            else
                pst.setString(5,"进行中");
            pst.setInt(6,dayOff);
            pst.setString(7,description);
            pst.setInt(8,0);
            pst.setInt(9,1);
            pst.setInt(10,1);
            pst.setInt(11,lastDays);
            pst.executeUpdate();


//            更新用户创建的计划数量
            sql = "SELECT plan_amount FROM users WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            int amount = resultSet.getInt("plan_amount");
            System.out.println(amount + "个计划(增加之前)");
            amount++;
            sql = "UPDATE users SET plan_amount=? WHERE username = '"+username+"'";
            pst = connection.prepareStatement(sql);
            System.out.println("amount = " + amount);
            pst.setInt(1,amount);
            pst.executeUpdate();
            pst.close();

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
