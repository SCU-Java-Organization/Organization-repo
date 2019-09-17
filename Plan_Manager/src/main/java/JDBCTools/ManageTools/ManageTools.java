package JDBCTools.ManageTools;

import JDBCTools.Tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManageTools {
    public void insertPlanByUsername(String username, int flag, List<String> list){
        Connection connection = null;
        Statement statement = null;
        // 获取当前日期
        java.sql.Date currentDate = new java.sql.Date(getCurrentDate().getTime());

        String name;
        Scanner input = new Scanner(System.in);
        System.out.print("输入计划名：");
        name = input.nextLine();
        String status = "进行中";
        String description;
        System.out.print("输入计划描述：");
        description = input.nextLine();

        try{
            connection = Tools.getConnection();
            statement = connection.createStatement();
            if(flag == 0){
                String sql = "INSERT INTO plans (username, name, begin, end, status, dayoff, description, attend_days, attend_flag, off_flag, last_days) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, username);
                pst.setString(2, name);
                pst.setDate(3, currentDate);
                pst.setDate(4, currentDate);
                pst.setString(5, status);
                pst.setInt(6, 0);
                pst.setString(7, description);
                pst.setInt(8,0);
                pst.setInt(9,1);
                pst.setInt(10,1);
                pst.setInt(11,1);
                pst.executeUpdate();
                pst.close();
            } else{
                String sql = "INSERT INTO plans (username, name, begin, end, status, dayoff, description, attend_days, attend_flag, off_flag, last_days) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for(String s : list){
                    username = s;
                    pst.setString(1, username);
                    pst.setString(2, name);
                    pst.setDate(3, currentDate);
                    pst.setDate(4, currentDate);
                    pst.setString(5, status);
                    pst.setInt(6, 0);
                    pst.setString(7, description);
                    pst.setInt(8,0);
                    pst.setInt(9,1);
                    pst.setInt(10,1);
                    pst.setInt(11,1);
                    pst.executeUpdate();
                }
                pst.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                statement.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
            try{
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public List<String> readNameFromFile(String filename){
        List<String> nameList = new ArrayList<>();
        try(Scanner in = new Scanner(new FileInputStream(filename), "UTF-8")){
            while(in.hasNextLine())
                nameList.add(in.nextLine());
        } catch (IOException e){
            e.printStackTrace();
        }
        for (String s : nameList)
            System.out.println(s);
        return nameList;
    }

    public Date getCurrentDate(){
        Calendar currentDate = Calendar.getInstance();
        String dateString = currentDate.get(Calendar.YEAR) + "-" +
                currentDate.get(Calendar.MONTH) + "-" +
                currentDate.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try{
            date = format.parse(dateString);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return date;
        }
    }
}
