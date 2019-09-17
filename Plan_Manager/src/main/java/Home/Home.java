package Home;

import CreatePlan.CreatePlan;
import Home.Charts.HistogramChart;
import Home.Charts.LineChart;
import Home.Charts.PieChart;
import Home.JDBC.Home_SQL;
import Home.MenuBar.ChangePassword;
import Home.MenuBar.ContactUs;
import Home.MenuBar.GetDirection;
import Login.Login;
import Entity.JDBC.Plan_SQL;
import Entity.Plan;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Home {
    private String username;
    private String realname;
    private int totalAmount;
    private int doneAmount;
    private int doingAmount;
    private int failAmount;
    private int futureAmount;
    private double rate;
    private String comment;
    private String remarks;

    private SystemTray systemTray = SystemTray.getSystemTray();
    private TrayIcon trayIcon = null;

    private int pageIndex = 0;

    private Color themeColor = Color.WHITE;
    private Color textColor = Color.darkGray;

    public String getUsername(){return this.username;}

    public void setUsername(String username){this.username = username;}

//    获取每种状态的计划数量
    public int getAmount(String username, String status){
       return Home_SQL.getNums(username, status);
    }


//    限制打卡、休息操作每天只能进行一次
    public boolean dailyCheck(int id, String type){
       return Home_SQL.dailyCheck(id, type);
    }

    public void getComment(){
       if(totalAmount == 0) {
          comment = "你还没有创建计划\n去点击右侧'新计划'按钮创建一个吧";
          return;
       }
       if(doneAmount == 0 && failAmount == 0){
          comment = "你还没有完成任何计划,请再接再厉！";
          return;
       }
       if(failAmount > doneAmount){
          comment = "你的计划完成率有待提高\n请注意效率并不要忘记每日打卡！";
          return;
       }
       if(rate > 75){
          comment = "你的表现良好\n计划完成度较高，请继续努力！";
          return;
       }
       if (rate > 85){
          comment = "你的表现优秀\n计划完成度高，请继续保持！";
          return;
       }
       else
          comment = "你的表现还有提升空间，\n请继续加油！";
    }

    public String getRemarks(){
       return Home_SQL.getRemarks(username);
    }

    public void showUI() {
//        加载数据
        realname = Home_SQL.getRealName(username);
        remarks = getRemarks();
        futureAmount = getAmount(username, "未开始");
        doingAmount = getAmount(username, "进行中");
        doneAmount = getAmount(username, "已完成");
        failAmount = getAmount(username, "失败的");
        totalAmount = getAmount(username, "总数");
        if(doneAmount + failAmount > 0)
           rate = (double)doneAmount / (double)(doneAmount + failAmount) * 100;
        else
           rate = 0;
        getComment();
        List<Plan> planList = Plan_SQL.getPlan(username);
        List<Plan> todayPlan = new ArrayList<>();

        System.out.println("根据用户名SJL查询得到:");
        for(int i = 0; i < planList.size(); i++){
           planList.get(i).info();
           if(planList.get(i).getStatus().equals("进行中"))
              todayPlan.add(planList.get(i));
        }
        System.out.println("************************************分割*****************************************");
        System.out.println("今日计划 " + todayPlan.size() + " 个");
        for(int i = 0; i < todayPlan.size(); i++)
           todayPlan.get(i).info();


        JFrame homeFrame = new JFrame();
        homeFrame.setSize(800,575);                      //窗体大小
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //可以退出
        homeFrame.setLocationRelativeTo(null);                          //相对屏幕居中
        homeFrame.setTitle("Home");                                     //窗体名字
        homeFrame.setResizable(false);                                  //禁止放缩
        homeFrame.getContentPane().setBackground(Color.GRAY);
        ImageIcon imageIcon = new ImageIcon("imgs/PM.png");
        homeFrame.setIconImage(imageIcon.getImage());
       //       最小化到托盘
       homeFrame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowIconified(WindowEvent e) {
             homeFrame.setVisible(false);
             ImageIcon icon = new ImageIcon("imgs/PM.png");
             PopupMenu pop = new PopupMenu();  //增加托盘右击菜单
             MenuItem show = new MenuItem("Open");
             MenuItem exit = new MenuItem("Exit");
//             还原键
             show.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   systemTray.remove(trayIcon);
                   homeFrame.setVisible(true);
                   homeFrame.setExtendedState(JFrame.NORMAL);
                   homeFrame.toFront();
                }
             });
//             退出键
             exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   systemTray.remove(trayIcon);
                   System.exit(0);
                }
             });

             pop.add(show);
             pop.add(exit);

             trayIcon = new TrayIcon(icon.getImage(),"Plan_Manager", pop);
             trayIcon.setImageAutoSize(true);

             trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                   if(e.getClickCount() == 2){
                      systemTray.remove(trayIcon);
                      homeFrame.setVisible(true);
                      homeFrame.setExtendedState(JFrame.NORMAL);
                      homeFrame.toFront();
                   }
                }
             });

             try {
                systemTray.add(trayIcon);
             } catch (AWTException ex){
                ex.printStackTrace();
             }
          }
       });

//        设置为空布局
       homeFrame.setLayout(null);

//        个人信息区
       JTextArea personalInfo = new JTextArea();
       personalInfo.setBackground(themeColor);
       personalInfo.setEnabled(false);
       personalInfo.setLineWrap(true);
       personalInfo.setDisabledTextColor(textColor);
       personalInfo.setBounds(0,0,200,350);
       personalInfo.setBorder(BorderFactory.createTitledBorder("个人信息"));
       personalInfo.append("用户名:" + username +"\n\n");
       personalInfo.append("姓名:" + realname + "\n\n");
       personalInfo.append("计划总数:" + totalAmount + "\n\n");
       personalInfo.append("未开始:" + futureAmount +"\n\n");
       personalInfo.append("已完成:" + doneAmount +"\n\n");
       personalInfo.append("进行中:" + doingAmount + "\n\n");
       personalInfo.append("失败:" + failAmount + "\n\n");
       personalInfo.append("完成率:" + String.format("%.2f", rate) + "%\n\n");
       personalInfo.append("系统评价:" + comment + "\n\n");
       homeFrame.add(personalInfo);

//       备注信息区
       JPanel remarksPanel = new JPanel();
       remarksPanel.setLayout(null);
       remarksPanel.setEnabled(false);
       remarksPanel.setBounds(0,350,200,162);
       remarksPanel.setBackground(themeColor);
       remarksPanel.setBorder(BorderFactory.createTitledBorder("备注信息"));


       JTextArea remarkArea = new JTextArea();
       JScrollPane scrollRemark = new JScrollPane(remarkArea);
       remarkArea.setEnabled(false);
       scrollRemark.setBounds(5,15,190,102);
//       remarkArea.setBorder(BorderFactory.createEtchedBorder());
       remarkArea.setEnabled(false);
       remarkArea.setLineWrap(true);
       remarkArea.setBackground(themeColor);
       remarkArea.setDisabledTextColor(textColor);
       remarkArea.setText(remarks);
       remarksPanel.add(scrollRemark);

       JButton editButton = new JButton("编辑");
       editButton.setBounds(5,120,90,30);
       editButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             remarkArea.setEnabled(true);
             remarkArea.requestFocus();
          }
       });

       JButton saveButton = new JButton("保存");
       saveButton.setBounds(105,120,90,30);
       saveButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
               remarkArea.setEnabled(false);
               Home_SQL.setRemarks(username, remarkArea.getText());
             JOptionPane.showMessageDialog(null, "保存成功！", "通知", JOptionPane.INFORMATION_MESSAGE);
          }
       });

       remarksPanel.add(editButton);
       remarksPanel.add(saveButton);

       homeFrame.add(remarksPanel);

//       今日计划区
       JPanel planToday = new JPanel();
       planToday.setLayout(null);
       planToday.setEnabled(false);
       planToday.setBounds(200,0,585,260);
       planToday.setBackground(themeColor);
       planToday.setBorder(BorderFactory.createTitledBorder("今日计划"));

//       计划显示区
       JTextArea plan = new JTextArea();
       JScrollPane scrollPlan = new JScrollPane(plan);
       plan.setEnabled(false);
       scrollPlan.setBounds(5,20,480,230);
//       plan.setBorder(BorderFactory.createEtchedBorder());
       plan.setEnabled(false);
       plan.setLineWrap(true);
       plan.setBackground(themeColor);
       plan.setDisabledTextColor(textColor);
       plan.setText("今日无计划\n");
       plan.append("点击右侧“新计划”来开始你的计划！");
       planToday.add(scrollPlan);

       if(todayPlan.size() > 0) {
          planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
          plan.setText("");
          plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
          plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
          plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
          plan.append("打卡天数:  " + todayPlan.get(pageIndex).getAttendance() + "\n\n");
          plan.append("剩余可休息天数:  " + todayPlan.get(pageIndex).getDayOff() + "\n\n");
          plan.append("计划详情:  " + todayPlan.get(pageIndex).getDescription() + "\n\n");
       }

//       按钮组
       JButton preButton = new JButton("上一个", new ImageIcon("imgs/up.png"));
//       preButton.setBackground(themeColor);
       preButton.setFocusPainted(false);
       preButton.setBounds(490,20,90,30);
       preButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             if(todayPlan.size() == 0){
                JOptionPane.showMessageDialog(null, "今天已经没有计划了！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             if(pageIndex == 0) {
                JOptionPane.showMessageDialog(null, "当前已经是第一个计划了！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             pageIndex--;
             planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
             plan.setText("");
             plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
             plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
             plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
             plan.append("打卡天数:  " + todayPlan.get(pageIndex).getAttendance() + "\n\n");
             plan.append("剩余可休息天数:  " + todayPlan.get(pageIndex).getDayOff() + "\n\n");
             plan.append("计划详情:  " + todayPlan.get(pageIndex).getDescription() + "\n\n");
          }
       });
       planToday.add(preButton);
       JButton nextButton = new JButton("下一个", new ImageIcon("imgs/down.png"));
       nextButton.setFocusPainted(false);
       nextButton.setBounds(490,60,90,30);
       nextButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             if(pageIndex == todayPlan.size() - 1) {
                JOptionPane.showMessageDialog(null, "当前已经是最后一个计划了！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             if(todayPlan.size() == 0) {
                JOptionPane.showMessageDialog(null, "今天已经没有计划了！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             pageIndex++;

             planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
             plan.setText("");
             plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
             plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
             plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
             plan.append("打卡天数:  " + todayPlan.get(pageIndex).getAttendance() + "\n\n");
             plan.append("剩余可休息天数:  " + todayPlan.get(pageIndex).getDayOff() + "\n\n");
             plan.append("计划详情:  " + todayPlan.get(pageIndex).getDescription() + "\n\n");
          }
       });
       planToday.add(nextButton);

       JButton createPlan = new JButton("新计划", new ImageIcon("imgs/edit.png"));
       createPlan.setFocusPainted(false);
       createPlan.setBounds(490,100,90,30);
       createPlan.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             CreatePlan createFrame = new CreatePlan();
             createFrame.setUsername(username);
             createFrame.showUI();
          }
       });
       planToday.add(createPlan);

//       放弃后页面自动显示第一个计划，或显示无计划提醒
       JButton giveUp = new JButton("放弃", new ImageIcon("imgs/close.png"));
       giveUp.setBounds(490,140,90,30);
       giveUp.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             if(todayPlan.size() == 0){
                JOptionPane.showMessageDialog(null, "当前无计划可操作！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             int result = JOptionPane.showConfirmDialog(null, "是否确认要放弃该计划？", "放弃计划", JOptionPane.YES_NO_OPTION);
             if(result == JOptionPane.YES_OPTION) {
                Home_SQL.threeOperation("giveUp", todayPlan.get(pageIndex).getId(), todayPlan.get(pageIndex).getAttendance(), todayPlan.get(pageIndex).getDayOff(), username);
                todayPlan.remove(pageIndex);
                if(todayPlan.size() > 0){
                   pageIndex = 0;
                   planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
                   plan.setText("");
                   plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
                   plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
                   plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
                   plan.append("打卡天数:  " + todayPlan.get(pageIndex).getAttendance() + "\n\n");
                   plan.append("剩余可休息天数:  " + todayPlan.get(pageIndex).getDayOff() + "\n\n");
                   plan.append("计划详情:" + todayPlan.get(pageIndex).getDescription() + "\n\n");
                }
                else
                   plan.setText("今日无计划");
             }
          }
       });
       planToday.add(giveUp);

       JButton check = new JButton("打卡", new ImageIcon("imgs/check.png"));
       check.setBounds(490,180,90,30);
       check.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             if(todayPlan.size() == 0){
                JOptionPane.showMessageDialog(null, "当前无计划可操作！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             if (!dailyCheck(todayPlan.get(pageIndex).getId(),"attend")){
                JOptionPane.showMessageDialog(null, "今天已经不能进行该操作了！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             int result = JOptionPane.showConfirmDialog(null, "是否确认要打卡今日的计划？", "打卡", JOptionPane.YES_NO_OPTION);
             if(result == JOptionPane.YES_OPTION) {
                Home_SQL.threeOperation("attend", todayPlan.get(pageIndex).getId(), todayPlan.get(pageIndex).getAttendance(), todayPlan.get(pageIndex).getDayOff(), username);
                todayPlan.get(pageIndex).setAttendance(todayPlan.get(pageIndex).getAttendance() + 1);

                if(todayPlan.get(pageIndex).getLastDays() == todayPlan.get(pageIndex).getAttendance()){
                   JOptionPane.showMessageDialog(null, "当前计划已完成！", "Complete!", JOptionPane.INFORMATION_MESSAGE);
                   todayPlan.remove(pageIndex);
                   if(todayPlan.size() > 0){
                      pageIndex = 0;
                      planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
                      plan.setText("");
                      plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
                      plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
                      plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
                      plan.append("打卡天数:  " + todayPlan.get(pageIndex).getAttendance() + "\n\n");
                      plan.append("剩余可休息天数:  " + todayPlan.get(pageIndex).getDayOff() + "\n\n");
                      plan.append("计划详情:" + todayPlan.get(pageIndex).getDescription() + "\n\n");
                   }
                   else
                      plan.setText("今日无计划");
                   return;
                }
//                更改数据
                todayPlan.get(pageIndex).setDailyStatus("已打卡");
                todayPlan.get(pageIndex).setAttendance(todayPlan.get(pageIndex).getAttendance());

                planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
                plan.setText("");
                plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
                plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
                plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
                plan.append("打卡天数:  " + (todayPlan.get(pageIndex).getAttendance()) + "\n\n");
                plan.append("剩余可休息天数:  " + todayPlan.get(pageIndex).getDayOff() + "\n\n");
                plan.append("计划详情:  " + todayPlan.get(pageIndex).getDescription() + "\n\n");

             }
          }
       });
       planToday.add(check);
       JButton dayOff = new JButton("休息", new ImageIcon("imgs/link.png"));
       dayOff.setBounds(490,220,90,30);
       dayOff.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             if(todayPlan.size() == 0) {
                JOptionPane.showMessageDialog(null, "当前无计划可操作！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             if(todayPlan.get(pageIndex).getDayOff() == 0){
                JOptionPane.showMessageDialog(null, "当前计划已经没有休息天数！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             if (!dailyCheck(todayPlan.get(pageIndex).getId(), "off")){
                JOptionPane.showMessageDialog(null, "今天已经不能进行该操作了！", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
             }
             int result = JOptionPane.showConfirmDialog(null, "是否确认今日不进行当前的计划（将会消耗一天的休息天数）？", "休息", JOptionPane.YES_NO_OPTION);
             if(result == JOptionPane.YES_OPTION) {
                Home_SQL.threeOperation("off",todayPlan.get(pageIndex).getId(),todayPlan.get(pageIndex).getAttendance(),todayPlan.get(pageIndex).getDayOff(), username);

                todayPlan.get(pageIndex).setDailyStatus("休息中");
                todayPlan.get(pageIndex).setDayOff(todayPlan.get(pageIndex).getDayOff() - 1);

                planToday.setBorder(BorderFactory.createTitledBorder("今日计划(" + (pageIndex+1) + "/" + todayPlan.size() + ") - " + todayPlan.get(pageIndex).getDailyStatus()));
                plan.setText("");
                plan.append("计划名称:  " + todayPlan.get(pageIndex).getPlanname() + "\n\n");
                plan.append("开始时间:  " + todayPlan.get(pageIndex).getBegin() + "\n\n");
                plan.append("结束时间:  " + todayPlan.get(pageIndex).getEnd() + "\n\n");
                plan.append("打卡天数:  " + todayPlan.get(pageIndex).getAttendance() + "\n\n");
                plan.append("剩余可休息天数:  " + (todayPlan.get(pageIndex).getDayOff() - 1) + "\n\n");
                plan.append("计划详情:  " + todayPlan.get(pageIndex).getDescription() + "\n\n");
             }
          }
       });
       planToday.add(dayOff);

       homeFrame.add(planToday);

//       进度表区
       JPanel planTable = new JPanel();
       planTable.setLayout(null);
       planTable.setEnabled(false);
       planTable.setBounds(200,260,585,252);
       planTable.setBackground(themeColor);
       planTable.setBorder(BorderFactory.createTitledBorder("计划进度表"));

       ChartPanel chartPanel = HistogramChart.getHistogram(totalAmount, doneAmount, doingAmount, failAmount, username);
       chartPanel.setBounds(10,15,450,212);
       chartPanel.setBackground(themeColor);
       planTable.add(chartPanel);

       JButton chart1 = new JButton("柱状图", new ImageIcon("imgs/bar_chart.png"));
       JButton chart2 = new JButton("饼状图", new ImageIcon("imgs/pie_chart.png"));
       JButton chart3 = new JButton("折线图", new ImageIcon("imgs/line_chart.png"));
       JButton logout = new JButton(new ImageIcon("imgs/poweroff.png"));
       JButton refresh = new JButton(new ImageIcon("imgs/reload.png"));

       chart1.setBounds(490,40,90,30);
       chart2.setBounds(490,90,90,30);
       chart3.setBounds(490,140,90,30);
       logout.setBounds(525,220,25,25);
       refresh.setBounds(550,220,25,25);

       chart1.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             planTable.removeAll();
             planTable.repaint();
             planTable.add(chart1);
             planTable.add(chart2);
             planTable.add(chart3);
             planTable.add(logout);
             planTable.add(refresh);
             ChartPanel chartPanel = HistogramChart.getHistogram(totalAmount, doneAmount, doingAmount, failAmount, username);
             chartPanel.setBounds(10,15,450,212);
             planTable.add(chartPanel);
          }
       });

       chart2.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             planTable.removeAll();
             planTable.repaint();
             planTable.add(chart1);
             planTable.add(chart2);
             planTable.add(chart3);
             planTable.add(logout);
             planTable.add(refresh);
             ChartPanel piePanel = PieChart.getPie(doneAmount, failAmount, doingAmount);
             piePanel.setBounds(10,15,450,212);
             planTable.add(piePanel);
          }
       });

       chart3.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             planTable.removeAll();
             planTable.repaint();
             planTable.add(chart1);
             planTable.add(chart2);
             planTable.add(chart3);
             planTable.add(logout);
             planTable.add(refresh);
             ChartPanel linePanel = LineChart.getLineChart(username);
             linePanel.setBounds(10,15,450,212);
             planTable.add(linePanel);
          }
       });

       logout.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             homeFrame.dispose();
             Login login = new Login();
             login.showUI();
          }
       });

       refresh.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             homeFrame.setVisible(false);
             Home home = new Home();
             home.setUsername(username);
             home.showUI();
             homeFrame.dispose();
          }
       });

       planTable.add(chart1);
       planTable.add(chart2);
       planTable.add(chart3);
       planTable.add(logout);
       planTable.add(refresh);
       homeFrame.add(planTable);

//       顶部菜单栏
       JMenuBar menuBar = new JMenuBar();
//       JMenu aboutMenu = new JMenu("关于");
       JMenu helpMenu = new JMenu("帮助");
       JMenu optionMenu = new JMenu("选项");

       menuBar.add(optionMenu);
       menuBar.add(helpMenu);
//       menuBar.add(aboutMenu);

       JMenuItem changePsd = new JMenuItem("更改密码");
       JMenuItem changeTheme = new JMenuItem("更改主题");
       JMenuItem contactUs = new JMenuItem("联系作者");
       JMenuItem getDirections = new JMenuItem("使用说明");
//       JMenuItem getInfo = new JMenuItem("关于软件");

       changePsd.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             ChangePassword cPsd = new ChangePassword();
             cPsd.showUI(username);
          }
       });

       contactUs.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             ContactUs contactUs = new ContactUs();
             contactUs.showUI();
          }
       });

       getDirections.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             GetDirection.openURL();
          }
       });


       optionMenu.add(changePsd);
//       optionMenu.add(changeTheme);
       helpMenu.add(getDirections);
       helpMenu.addSeparator();
       helpMenu.add(contactUs);
//       aboutMenu.add(getInfo);

       homeFrame.setJMenuBar(menuBar);
       homeFrame.repaint();
       homeFrame.setVisible(true);
    }
}
