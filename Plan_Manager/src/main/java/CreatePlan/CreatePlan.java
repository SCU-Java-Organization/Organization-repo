package CreatePlan;

import CreatePlan.JDBC.CreatePlan_SQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreatePlan {
    public static void main(String[] args) {
        CreatePlan frame = new CreatePlan();
        frame.showUI();
    }
    private String username;
    private String planName;
    private int year_begin = 0, year_end = 0;
    private int month_begin = 0, month_end = 0;
    private int day_begin = 0, day_end = 0;
    private int lastDays = 0;
    private int dayOff = 0;
    private String description;
    private Calendar currentTime;

    public void setCurrentTime(){
        currentTime = Calendar.getInstance();
    }

    public Date getDate(int year, int month, int day){
        String YEAR = year + "";
        String MONTH = month + "";
        String DAY = day + "";
        if(month < 10)
            MONTH = "0" + month;
        if(day < 10)
            DAY = "0" + day;

        String dateString = YEAR + "-" + MONTH + "-" + DAY;
        System.out.println(dateString);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = format.parse(dateString);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.out.println(date);
            return date;
        }

    }

    public String checkout() {
        if(year_begin == 0 || year_end == 0 || month_begin == 0 || month_end == 0 || day_begin == 0 || day_end == 0)
            return "未选日期";
        lastDays = getLastTime(year_end, month_end, day_end) - getLastTime(year_begin, month_begin, day_begin);
        if(lastDays < 0)
            return "日期错误";
        return "正确";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public int getLastTime(int year,int month,int day) {
        int daySum = 0;
//        获取年份代表的天数，注意年份要减一
        for(int i = 1; i < year; i++){
            if(isLeapYear(i))
                daySum += 366;
            else
                daySum += 365;
        }
//        获取月份代表的天数，注意月份要减一
        for(int i = 1; i < month; i++){
            switch (i) {
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    daySum += 31;
                    break;

                case 4: case 6: case 9: case 11:
                    daySum += 30;
                    break;

                case 2:
                    if(isLeapYear(year))
                        daySum += 29;
                    else
                        daySum += 28;
                    break;
            }
        }
        daySum += day;
        return daySum;
    }

    public void showUI() {
        JFrame createFrame = new JFrame();
        createFrame.setSize(345,510);                      //窗体大小
        createFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    //可以退出
        createFrame.setLocationRelativeTo(null);                          //相对屏幕居中
        createFrame.setTitle("CreatePlan");                               //窗体名字
        createFrame.setResizable(false);                                  //禁止放缩
        setCurrentTime();                                                 //获取当前时间
        ImageIcon imageIcon = new ImageIcon("imgs/PM.png");
        createFrame.setIconImage(imageIcon.getImage());
        final int YEAR = currentTime.get(Calendar.YEAR);
        final int MONTH = currentTime.get(Calendar.MONTH) + 1;
        final int DAY = currentTime.get(Calendar.DAY_OF_MONTH);
        final int WEEK = DAY / 7 + 1;
        System.out.println("YEAR = " + YEAR);
        System.out.println("MONTH = " + MONTH);
        System.out.println("DAY = " + DAY);
        System.out.println("WEEK = " + WEEK);

//        设置为空布局
        createFrame.setLayout(null);

//        标题
        JLabel title = new JLabel("制定计划");
        title.setBounds(140,0,150,20);
        title.setFont(new Font("Dialog",1,15));
        createFrame.add(title);

//        计划名
        JLabel nameLabel = new JLabel("计划名称:");
        nameLabel.setFont(new Font("Dialog",1,13));
        nameLabel.setBounds(20,30,60,30);
        JTextField nameField = new JTextField();
        nameField.setBounds(80,30,220,30);
        createFrame.add(nameLabel);
        createFrame.add(nameField);

//         天数box，放在计算之前
        JComboBox offBox = new JComboBox();
        offBox.addItem("--days--");
        offBox.setBounds(80,180,80,20);
        offBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dayOff = ((JComboBox)e.getSource()).getSelectedIndex() - 1;
                if(dayOff < 0)
                    dayOff = 0;
            }
        });

//        开始时间栏目
        JLabel beginLabel = new JLabel("开始时间:");
        beginLabel.setFont(new Font("Dialog",1,13));
        beginLabel.setBounds(20,70,60,30);
//        日选择
        JComboBox dayBox = new JComboBox();
        dayBox.addItem("--day--");
        dayBox.setBounds(240,75,80,20);
        dayBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day_begin = ((JComboBox)e.getSource()).getSelectedIndex();
                if(day_begin > 0)
                    day_begin = Integer.parseInt(dayBox.getItemAt(day_begin).toString());
                offBox.removeAllItems();
                offBox.addItem("--days--");
            }
        });
//        开始月选择
        JComboBox monthBox = new JComboBox();
        monthBox.addItem("--month--");
        for(int i = MONTH; i < 13; i++)
            monthBox.addItem(i);
        monthBox.setBounds(160,75,80,20);
        monthBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                month_begin = ((JComboBox)e.getSource()).getSelectedIndex();
                if(month_begin > 0)
                    month_begin = Integer.parseInt(monthBox.getItemAt(month_begin).toString());
                int month_length = 0;
                dayBox.removeAllItems();
                dayBox.addItem("--day--");

                offBox.removeAllItems();
                offBox.addItem("--days--");

                day_begin = 0;
                switch (month_begin) {
                    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                        month_length = 31;
                        break;

                    case 4: case 6: case 9: case 11:
                        month_length = 30;
                        break;

                    case 2:
                        if(isLeapYear(year_begin)) {
                            month_length = 29;
                        }
                        else {
                            month_length = 28;
                        }
                        break;
                }

                if(month_begin == MONTH){
                    for(int i = DAY; i < month_length + 1; i++)
                        dayBox.addItem(i);
                    return;
                }
                for(int i = 1; i < month_length + 1; i++)
                    dayBox.addItem(i);
            }
        });
//         年选择
        JComboBox yearBox = new JComboBox();
        yearBox.addItem("--year--");
        yearBox.addItem(YEAR);
        yearBox.addItem(YEAR + 1);
        yearBox.setBounds(80,75,80,20);
        yearBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offBox.removeAllItems();
                offBox.addItem("--days--");
                dayBox.removeAllItems();
                dayBox.addItem("--day--");
                monthBox.removeAllItems();
                monthBox.addItem("--month--");
                month_begin = 0;
                day_begin = 0;

                year_begin = ((JComboBox)e.getSource()).getSelectedIndex();
                if(year_begin > 0)
                    year_begin = Integer.parseInt(yearBox.getItemAt(year_begin).toString());
                if(year_begin == YEAR)
                    for(int i = MONTH; i < 13; i++)
                        monthBox.addItem(i);
                else
                    for (int i = 1; i < 13; i++)
                        monthBox.addItem(i);
            }
        });
        createFrame.add(beginLabel);
        createFrame.add(yearBox);
        createFrame.add(monthBox);
        createFrame.add(dayBox);

//         结束时间栏目
        JLabel endLabel = new JLabel("结束时间:");
        endLabel.setFont(new Font("Dialog",1,13));
        endLabel.setBounds(20,105,60,30);

        JComboBox dayBox2 = new JComboBox();
        dayBox2.addItem("--day--");
        dayBox2.setBounds(240,110,80,20);
        dayBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                day_end = ((JComboBox)e.getSource()).getSelectedIndex();
                if(day_end > 0)
                    day_end = Integer.parseInt(dayBox2.getItemAt(day_end).toString());
                offBox.removeAllItems();
                offBox.addItem("--days--");
            }
        });

        JComboBox monthBox2 = new JComboBox();
        monthBox2.addItem("--month--");
        for(int i = 1; i < 13; i++)
            monthBox2.addItem(i);
        monthBox2.setBounds(160,110,80,20);
        monthBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offBox.removeAllItems();
                offBox.addItem("--days--");

                month_end = ((JComboBox)e.getSource()).getSelectedIndex();
                if(month_end > 0)
                    month_end = Integer.parseInt(monthBox2.getItemAt(month_end).toString());
                dayBox2.removeAllItems();
                dayBox2.addItem("--day--");
                day_end = 0;
                int month_length = 0;
                switch (month_end) {
                    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                        month_length = 31;
                        break;

                    case 4: case 6: case 9: case 11:
                        month_length = 30;
                        break;

                    case 2:
                        if(isLeapYear(year_end)) {
                            month_length = 29;
                        }
                        else {
                            month_length = 28;
                        }
                        break;
                }

                if(month_end == MONTH){
                    for(int i = DAY; i < month_length + 1; i++)
                        dayBox2.addItem(i);
                    return;
                }
                for(int i = 1; i < month_length + 1; i++)
                    dayBox2.addItem(i);
            }
        });

        JComboBox yearBox2 = new JComboBox();
        yearBox2.addItem("--year--");
        yearBox2.addItem(YEAR);
        yearBox2.addItem(YEAR + 1);
        yearBox2.setBounds(80,110,80,20);
        yearBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                offBox.removeAllItems();
                offBox.addItem("--days--");
                dayBox2.removeAllItems();
                dayBox2.addItem("--day--");
                monthBox2.removeAllItems();
                monthBox2.addItem("--month--");

                day_end = 0;
                month_end = 0;

                year_end = ((JComboBox)e.getSource()).getSelectedIndex();
                if(year_end > 0)
                    year_end = Integer.parseInt(yearBox2.getItemAt(year_end).toString());

                if(year_end > YEAR)
                    for(int i = 1; i < 13; i++)
                        monthBox2.addItem(i);
                else
                    for(int i = MONTH; i < 13; i++)
                        monthBox2.addItem(i);
            }
        });
        createFrame.add(endLabel);
        createFrame.add(yearBox2);
        createFrame.add(monthBox2);
        createFrame.add(dayBox2);

//        计划时长栏目
        JLabel daysLabel = new JLabel("计划时长: ");
        daysLabel.setFont(new Font("Dialog",1,13));
        daysLabel.setBounds(20,140,60,30);

        JTextField daysArea = new JTextField();
        daysArea.setEnabled(false);
        daysArea.setDisabledTextColor(Color.BLACK);
        daysArea.setFont(new Font("Dialog",1,13));
        daysArea.setBounds(85,145,50,20);

        JButton calButton = new JButton("计算");
        calButton.setBounds(140,142,60,25);
        createFrame.add(daysArea);
        calButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(day_begin != 0 && day_end != 0 && year_begin != 0 && year_end != 0)
                    lastDays = getLastTime(year_end, month_end, day_end) - getLastTime(year_begin, month_begin, day_begin) + 1;
                else
                    lastDays = 0;
//                生成计划时长
                createFrame.remove(daysArea);
                daysArea.setText(lastDays + "天");
                System.out.println(lastDays);
                createFrame.add(daysArea);
//                生成休息天数
//                createFrame.remove(offBox);
                offBox.removeAllItems();
                offBox.addItem("--days--");
                for(int i = 0; i <= lastDays / 3; i++)
                    offBox.addItem(i + "天");
//                createFrame.add(offBox);
            }
        });
        createFrame.add(calButton);
        createFrame.add(daysLabel);

//          休息天数栏目
        JLabel offLabel = new JLabel("休息天数:");
        offLabel.setFont(new Font("Dialog",1,13));
        offLabel.setBounds(20,175,60,30);

        JLabel offTip = new JLabel("(计算后生成，不选则默认为0天)");
        offTip.setFont(new Font("Dialog",1,10));
        offTip.setBounds(165,175,160,30);
        createFrame.add(offLabel);
        createFrame.add(offBox);
        createFrame.add(offTip);

//        计划描述栏目
        JLabel descriptionLabel = new JLabel("计划描述:");
        descriptionLabel.setFont(new Font("Dialog",1,13));
        descriptionLabel.setBounds(20,210,60,30);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setText("在这里输入对计划的描述(255字以内)\n注意在你的计划时期内，你每天可以选择打卡或休息（若都不操作，则系统会在今天结束前扣除你一天休息天数），当休息天数用完后，则必须每日打卡，否则系统将判定你的计划失败！");
        descriptionArea.setBounds(20,240,300,180);
        descriptionArea.setBorder(BorderFactory.createEtchedBorder());
        descriptionArea.setLineWrap(true);
        createFrame.add(descriptionLabel);
        createFrame.add(descriptionArea);

//        按钮栏目
        JButton submitButton = new JButton("提交");
        submitButton.setBounds(70,430,60,30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkout() == "未选日期"){
                    JOptionPane.showMessageDialog(null, "请完善日期！", "提交失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(checkout() == "日期错误"){
                    JOptionPane.showMessageDialog(null, "请选择正确的结束时间！", "提交失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(checkout() == "正确"){
                    planName = nameField.getText();
                    description = descriptionArea.getText();
                    Date begin = getDate(year_begin, month_begin, day_begin);
                    Date end = getDate(year_end, month_end, day_end);

                    if(planName.equals("")){
                        JOptionPane.showMessageDialog(null, "请填写计划名！", "提交失败", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
//                    防止用户未点击计算按钮，导致未计算持续时长
                    lastDays = getLastTime(year_end, month_end, day_end) - getLastTime(year_begin, month_begin, day_begin) + 1;
//                    月度统计
                    CreatePlan_SQL.monthCheck(WEEK, username);
                    CreatePlan_SQL.insertPlan(username, planName, begin, end, dayOff, description, lastDays);
                    JOptionPane.showMessageDialog(null, "提交成功！", "提交计划", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Begin At:" + begin);
                    System.out.println("End At:" + end);
                    createFrame.dispose();
                    return;
                }

            }
        });

        JButton backButton = new JButton("取消");
        backButton.setBounds(200,430,60,30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFrame.dispose();
            }
        });

        createFrame.add(submitButton);
        createFrame.add(backButton);

        createFrame.setVisible(true);
    }
}
