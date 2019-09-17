package Home.Charts;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineChart {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://106.52.33.76:3306/plan_manager?useSSL=false&serverTimezone=Asia/Shanghai";
    static final String USER = "root";
    static final String PASSWORD = "bSaYKJyJstQ6YtD";

    public static ChartPanel getLineChart(String username){
        ChartPanel chartPanel;
        CategoryDataset categoryDataset = createDataset(username);
        JFreeChart jfreechart = ChartFactory.createLineChart("月度情况概览", // 标题
                "", // categoryAxisLabel （category轴，横轴，X轴标签）
                "数量", // valueAxisLabel（value轴，纵轴，Y轴的标签）
                categoryDataset, // dataset
                PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs
        // 使用CategoryPlot设置各种参数。以下设置可以省略。
        CategoryPlot plot = (CategoryPlot)jfreechart.getPlot();
        CategoryAxis domainAxis=plot.getDomainAxis();     //水平底部列表
        domainAxis.setLabelFont(new Font("宋体",Font.BOLD,13));     //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,13)); //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("宋体",Font.PLAIN,13));
        jfreechart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,13));
        jfreechart.getTitle().setFont(new Font("宋体",Font.PLAIN,13));//设置标题字体
        jfreechart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        plot.setBackgroundPaint(ChartColor.darkGray);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);
        // 其他设置 参考 CategoryPlot类
        LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
        renderer.setBaseShapesVisible(true); // series 点（即数据点）可见
        renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
        renderer.setUseSeriesOffset(true); // 设置偏移量
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//设置y轴显示整数数据

        chartPanel = new ChartPanel(jfreechart, true);
        return chartPanel;
    }
    public static CategoryDataset createDataset(String username) {
        String[] rowKeys = {"创建计划","放弃计划","完成计划"};
        String[] colKeys = {"第一周", "第二周", "第三周", "第四周"};
        List<Integer> list = getData(username);
        System.out.println("LIST = " + list);
        double[][] data = new double[3][4];

        for(int i = 0, index = 0; i < 3; i++)
            for(int j = 0; j < 4; j++, index++)
                data[i][j] = list.get(index);


        // 或者使用类似以下代码
        // DefaultCategoryDataset categoryDataset = new
        // DefaultCategoryDataset();
        // categoryDataset.addValue(10, "rowKey", "colKey");
        return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
    }

    public static List<Integer> getData(String username){
        Connection connection = null;
        Statement statement = null;
        List<Integer> data = new ArrayList<>();
        try {
//            注册JDBC驱动
            Class.forName(JDBC_DRIVER);
//            打开链接
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//            执行查询
            statement = connection.createStatement();
            String sql = "SELECT week1_create, week1_delete, week1_complete," +
                    "week2_create, week2_delete, week2_complete," +
                    "week3_create, week3_delete, week3_complete," +
                    "week4_create, week4_delete, week4_complete" +
                    " FROM months WHERE username = '"+username+"'";
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("1." + resultSet);
            while(resultSet.next()) {
                data.add(resultSet.getInt("week1_create"));
                data.add(resultSet.getInt("week2_create"));
                data.add(resultSet.getInt("week3_create"));
                data.add(resultSet.getInt("week4_create"));

                data.add(resultSet.getInt("week1_delete"));
                data.add(resultSet.getInt("week2_delete"));
                data.add(resultSet.getInt("week3_delete"));
                data.add(resultSet.getInt("week4_delete"));

                data.add(resultSet.getInt("week1_complete"));
                data.add(resultSet.getInt("week2_complete"));
                data.add(resultSet.getInt("week3_complete"));
                data.add(resultSet.getInt("week4_complete"));
                System.out.println(data);
            }
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
            return data;
        }
    }

}