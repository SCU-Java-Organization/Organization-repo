import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class XInterface extends JFrame {
    public XInterface(Xuser xuser) {
        setBounds(610, 180, 340, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);   //不可改变大小

        JPanel panel1 = new JPanel();
        add(panel1, BorderLayout.NORTH);
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);  //采用绝对布局
        add(panel2);

        JLabel title = new JLabel("Welcome " + xuser.getUserName());
        title.setFont(new Font("黑体", Font.PLAIN, 30));
        panel1.add(title);

        JLabel label = new JLabel(LocalDate.now().toString());  //默认打印日期
        label.setFont(new Font("楷体", Font.PLAIN, 20));
        label.setBounds(100, 10, 200, 20);
        panel2.add(label);

        JButton button1 = new JButton("余额查询");
        button1.setBounds(65, 120, 200, 40);
        button1.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "您当前余额为"
                    + xuser.getBalance() + "元", "余额查询", JOptionPane.PLAIN_MESSAGE);
        });
        panel2.add(button1);

        JButton button2 = new JButton("存  款");      //存款功能
        button2.setBounds(65, 190, 200, 40);
        button2.addActionListener(e -> {
            double money = Double.valueOf(JOptionPane.showInputDialog("请输入取款金额："));
            xuser.addMoney(money);
            JOptionPane.showMessageDialog(null, "存款成功！");
        });
        panel2.add(button2);

        JButton button3 = new JButton("取  款");     //取款功能
        button3.setBounds(65, 260, 200, 40);
        button3.addActionListener(e -> {
            double money = Double.valueOf(JOptionPane.showInputDialog("请输入取款金额："));
            if (money > xuser.getBalance())
                JOptionPane.showMessageDialog(null, "余额不足！",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            else {
                xuser.takeMoney(money);
                JOptionPane.showMessageDialog(null, "取款成功！");
            }
        });
        panel2.add(button3);

        JButton button4 = new JButton("转  账");
        button4.setBounds(65, 330, 200, 40);
        button4.addActionListener(e -> {
            String otherUser = (String) JOptionPane.showInputDialog("请输入对方的账号：");
        });
        panel2.add(button4);

        JButton button5 = new JButton("注  销");    //切换用户功能
        button5.setBounds(246, 398, 80, 20);
        button5.addActionListener(e -> {
            dispose();
            new Login();
        });
        panel2.add(button5);

        setVisible(true);
    }
}