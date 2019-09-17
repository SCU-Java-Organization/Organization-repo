package JDBCTools;

import Entity.Plan;
import Entity.User;
import JDBCTools.ManageTools.ManageTools;
import org.jfree.data.xy.YisSymbolic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class getData {
    public static List<User> getUsers(){
        Connection connection = null;
        Statement statement = null;
        List<User> userList = new ArrayList<>();
        try {
            connection = Tools.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPlan_amount(resultSet.getInt("plan_amount"));
                user.setRemarks(resultSet.getString("remarks"));
                userList.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    public static List<Plan> getPlans(){
        Connection connection = null;
        Statement statement = null;
        List<Plan> planList = new ArrayList<>();
        try {
            connection = Tools.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM plans";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Plan plan = new Plan();
                plan.setPlanname(resultSet.getString("name"));
                plan.setLastDays(resultSet.getInt("last_days"));
                plan.setStatus(resultSet.getString("status"));
                planList.add(plan);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return planList;
    }

    public static void main(String[] args){
        List<User> userList = getUsers();
//        List<Plan> planList = getPlans();
        for(User u : userList) {
            System.out.println("ID:" + u.getId() + " Username:" + u.getUsername() + " Plans:" + u.getPlan_amount());
            System.out.println("");
        }
//        for(Plan p : planList){
//            System.out.println("Name:" + p.getPlanname() + " last_days:" + p.getLastDays() + " status:" + p.getStatus());
//            System.out.println("");
//        }

//        ManageTools manageTools = new ManageTools();
//        System.out.println(System.getProperty("user.dir"));
//        manageTools.insertPlanByUsername("SJL", 1, manageTools.readNameFromFile("students.txt"));
    }
}
