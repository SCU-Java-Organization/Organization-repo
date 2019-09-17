package Entity.JDBC;

import Entity.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Plan_SQL {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://106.52.33.76:3306/plan_manager?useSSL=false&serverTimezone=Asia/Shanghai";
    static final String USER = "root";
    static final String PASSWORD = "bSaYKJyJstQ6YtD";

    public static List<Plan> getPlan(String username){
        Connection connection = null;
        Statement statement = null;
        List<Plan> planList = new ArrayList<>();
        try {
//            注册JDBC驱动
            Class.forName(JDBC_DRIVER);
//            打开链接
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//            执行查询
            statement = connection.createStatement();
            String sql = "SELECT id, name, begin, end, status, dayoff, description, attend_days, attend_flag, off_flag, last_days " +
                         "FROM plans WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);

//            将获取的计划数据处理为一个LIst返回
            int index = 0;
            while (resultSet.next()) {
                Plan plan = new Plan();
                /*
                此处注意上方，在每一次的循环中重新构造新的Plan
                如果将Plan置于外层，由于引用的原因，将会出现错误
                planList的每一项都是最后一次set的数据
                 */
                plan.setUsername(username);
                plan.setId(resultSet.getInt("id"));
                plan.setPlanname(resultSet.getString("name"));
                plan.setBegin(resultSet.getDate("begin"));
                plan.setEnd(resultSet.getDate("end"));
                plan.setStatus(resultSet.getString("status"));
                plan.setDayOff(resultSet.getInt("dayoff"));
                plan.setDescription(resultSet.getString("description"));
                plan.setAttendance(resultSet.getInt("attend_days"));
                plan.setLastDays(resultSet.getInt("last_days"));

                int attend_flag = resultSet.getInt("attend_flag");
                int off_flag = resultSet.getInt("off_flag");
                if(attend_flag == 1 && off_flag == 1)
                    plan.setDailyStatus("待操作");
                else if(attend_flag == 1 && off_flag == 0)
                    plan.setDailyStatus("休息中");
                else if (attend_flag == 0 && off_flag == 1)
                    plan.setDailyStatus("已打卡");
                planList.add(plan);
            }
            resultSet.close();
            return planList;
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
            return planList;
        }
    }
}
