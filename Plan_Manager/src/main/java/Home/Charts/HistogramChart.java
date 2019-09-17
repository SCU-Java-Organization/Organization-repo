package Home.Charts;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class HistogramChart {
    public static ChartPanel getHistogram(int total, int done, int doing, int fail, String username){
        ChartPanel chartPanel;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(fail, username, "失败的");
        dataset.addValue(doing, username, "进行中");
        dataset.addValue(done, username, "已完成");
        dataset.addValue(total, username, "计划总数");

        JFreeChart chart = ChartFactory.createBarChart3D(
                "分类统计", // 图表标题
                "", // 目录轴的显示标签
                "数量", // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.HORIZONTAL, // 图表方向：水平、垂直
                true,      // 是否显示图例(对于简单的柱状图必须是false)
                false,     // 是否生成工具
                false      // 是否生成URL链接
        );
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        //从这里开始
        CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
        plot.setNoDataMessage("无数据显示！");
        plot.setBackgroundPaint(ChartColor.darkGray);
        CategoryAxis domainAxis=plot.getDomainAxis();     //水平底部列表
        domainAxis.setLabelFont(new Font("宋体",Font.BOLD,13));     //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体",Font.PLAIN,13)); //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("宋体",Font.PLAIN,13));
        chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,13));
        chart.getTitle().setFont(new Font("宋体",Font.PLAIN,13));//设置标题字体
        //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题

        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//设置y轴显示整数数据

        chartPanel = new ChartPanel(chart,true);    //这里也可以用chartFrame,可以直接生成一个独立的Frame
        return chartPanel;
    }
}
