package Home.JDBC;

import java.sql.*;
import java.util.Calendar;
import JDBCTools.Tools;

public class Home_SQL {
    public static String getRealName(String username) {
        Connection connection = null;
        Statement statement = null;
        String realName = "";
        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();
            String sql = "SELECT realname FROM users WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            realName = resultSet.getString("realname");
//            System.out.println(realName);
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
            return realName;
        }
    }

    public static int getNums(String username, String status){  //得到相应状态的计划数量
        Connection connection = null;
        Statement statement = null;
        int sum = 0;

        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();
            String sql = null;

            if(status.equals("失败的"))
                sql = "SELECT fail_amount FROM users WHERE username = '"+username+"'";
            else if (status.equals("已完成"))
                sql = "SELECT complete_amount FROM users WHERE username = '"+username+"'";
            else if(status.equals("总数"))
                sql = "SELECT plan_amount FROM users WHERE username = '"+username+"'";
            else
                sql = "SELECT * FROM plans WHERE status = '"+status+"' and username = '"+username+"'";

            ResultSet resultSet = statement.executeQuery(sql);

            if(status.equals("进行中") || status.equals("未开始")){
                while(resultSet.next())
                    sum++;
            }
            else{
                resultSet.next();
                sum = resultSet.getInt(1);
            }

//            System.out.println("Status = " + status + " and the amount is " + sum);
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
            return sum;
        }
    }

    public static String getRemarks(String username){
        Connection connection = null;
        Statement statement = null;
        String remarks = "";
        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();
            String sql = "SELECT remarks FROM users WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            remarks = resultSet.getString("remarks");
//            System.out.println("用户备注信息：" + remarks);
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
            return remarks;
        }
    }

    public static void setRemarks(String username, String remarks){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();
            String sql = "UPDATE users SET remarks='"+remarks+"' WHERE username = '"+username+"'";
            PreparedStatement pst = connection.prepareStatement(sql);
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

    public static void threeOperation(String operation, int id, int attendance, int dayOff, String username){
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();

            String sql = "";
            if(operation.equals("giveUp")) {
                sql = "UPDATE plans SET status = '失败的', attend_flag = 0, off_flag = 0 WHERE id = '" + id + "'";
                monthCheck(0, username);
            }
            if(operation.equals("attend")){
                attendance++;
                sql = "SELECT attend_days, last_days FROM plans WHERE id = '"+id+"'";
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();

                if(resultSet.getInt("last_days") == attendance) {
                    sql = "UPDATE plans SET attend_days = '" + attendance + "', attend_flag = 0, off_flag = 0, status = '已完成' WHERE id = '" + id + "'";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();
                    sql = "UPDATE users SET complete_amount = complete_amount + 1 WHERE username = '"+username+"'";
                    monthCheck(1, username);
                }
                else
                    sql = "UPDATE plans SET attend_days = '"+attendance+"', attend_flag = 0 WHERE id = '"+id+"'";

                resultSet.close();
            }
            if(operation.equals("off")){
                dayOff--;
                sql = "UPDATE plans SET dayoff = '"+dayOff+"', off_flag = 0 WHERE id = '"+id+"'";
            }

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
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

    public static boolean dailyCheck(int id, String type){
        Connection connection = null;
        Statement statement = null;
        boolean flag = false;
        String sql;
        try {
            connection = Tools.getConnection();
//            执行查询
            statement = connection.createStatement();

            sql = "SELECT attend_flag, off_flag FROM plans WHERE id = '"+id+"'";

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();

            int attend_flag = resultSet.getInt("attend_flag");
            int off_flag = resultSet.getInt("off_flag");

            if(attend_flag == 1 && off_flag == 1)
                flag = true;

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
            return flag;
        }
    }

    private static void monthCheck(int flag, String username){
        Connection connection = null;
        Statement statement = null;
        Calendar currentTime = Calendar.getInstance();
        int WEEK = currentTime.get(Calendar.DAY_OF_MONTH) / 7 + 1;

        try {
            connection = Tools.getConnection();
            statement = connection.createStatement();
            String sql = null;
            String resultLabel = null;
//            放弃计划
            if(flag == 0){
                switch (WEEK){
                    case 1:
                        sql = "SELECT week1_delete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week1_delete";
                        break;
                    case 2:
                        sql = "SELECT week2_delete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week2_delete";
                        break;
                    case 3:
                        sql = "SELECT week3_delete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week3_delete";
                        break;
                    case 4: case 5:
                        sql = "SELECT week4_delete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week4_delete";
                        break;
                    default:
                        break;
                }
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                int num = resultSet.getInt(resultLabel) + 1;

                switch (WEEK){
                    case 1:
                        sql = "UPDATE months SET week1_delete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    case 2:
                        sql = "UPDATE months SET week2_delete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    case 3:
                        sql = "UPDATE months SET week3_delete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    case 4:
                        sql = "UPDATE months SET week4_delete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    default:
                        break;
                }
            }
            else{
                switch (WEEK){
                    case 1:
                        sql = "SELECT week1_complete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week1_complete";
                        break;
                    case 2:
                        sql = "SELECT week2_complete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week2_complete";
                        break;
                    case 3:
                        sql = "SELECT week3_complete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week3_complete";
                        break;
                    case 4: case 5:
                        sql = "SELECT week4_complete FROM months WHERE username = '"+username+"'";
                        resultLabel = "week4_complete";
                        break;
                    default:
                        break;
                }
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                int num = resultSet.getInt(resultLabel) + 1;

                switch (WEEK){
                    case 1:
                        sql = "UPDATE months SET week1_complete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    case 2:
                        sql = "UPDATE months SET week2_complete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    case 3:
                        sql = "UPDATE months SET week3_complete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    case 4:
                        sql = "UPDATE months SET week4_complete = '"+num+"' WHERE username = '"+username+"'";
                        break;
                    default:
                        break;
                }
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
}
