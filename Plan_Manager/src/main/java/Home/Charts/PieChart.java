package Home.Charts;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PieChart {
    public static ChartPanel getPie(int done, int fail, int doing){
        ChartPanel chartPanel;
        DefaultPieDataset data = getDataSet(done, fail, doing);
        JFreeChart chart = ChartFactory.createPieChart3D("问卷比例", data, true, false, false);

        PiePlot pieplot = (PiePlot)chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
        pieplot.setLabelGenerator(sp1);//设置饼图显示百分比

        //没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);//设置不显示空值
        pieplot.setIgnoreZeroValues(true);//设置不显示负值
        pieplot.setBackgroundPaint(ChartColor.GRAY);
        chartPanel = new ChartPanel (chart,true);
        chart.getTitle().setFont(new Font("宋体",Font.PLAIN,13));//设置标题字体
        PiePlot piePlot = (PiePlot)chart.getPlot();//获取图表区域对象
        piePlot.setLabelFont(new Font("宋体",Font.PLAIN,13));//解决乱码
        chart.getLegend().setItemFont(new Font("黑体",Font.PLAIN,13));
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        return chartPanel;
    }

    private static DefaultPieDataset getDataSet(int done, int fail, int doing){
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("已完成",done);
        dataset.setValue("失败的",fail);
        dataset.setValue("进行中",doing);
        return dataset;
    }
}
