import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Login extends JFrame {
    public Login() {
        //关闭监听器
        //程序结束后把信息重新写入文件
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                ObjectOutputStream oos= null;
                try {
                    oos = new ObjectOutputStream(new FileOutputStream("Xbank.txt"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    oos.writeObject(Xbank.getXusers());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    oos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setBounds(540, 250, 460, 320);

        setDefaultCloseOperation(EXIT_ON_CLOSE);  //默认关闭
        setResizable(false);  //不可更改大小

        JPanel panel1 = new JPanel();
        add(panel1, BorderLayout.NORTH);   //把面板添加到窗口

        JLabel title = new JLabel("X Bank");
        title.setFont(new Font("黑体", Font.BOLD, 50));
        panel1.add(title);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3, 1));  //网格布局
        add(panel2, BorderLayout.WEST);

        JLabel account = new JLabel(" 账号:");
        account.setFont(new Font("黑体", Font.BOLD, 20));
        panel2.add(account);
        JLabel password = new JLabel(" 密码:");
        panel2.add(password);
        password.setFont(new Font("黑体", Font.BOLD, 20));

        JPanel panel3 = new JPanel();
        panel3.setLayout(null);  //设置为绝对布局
        add(panel3, BorderLayout.CENTER);

        JButton button3= new JButton("登陆");  //创建登陆按钮

        JTextField act = new JTextField();    //文本框
        act.setBounds(50, 25, 200, 25);
        JPasswordField psw = new JPasswordField(200);   //密码框
        psw.setBounds(50, 95, 200, 25);
        psw.addActionListener(e -> button3.doClick());  //回车即可登陆
        panel3.add(act);
        panel3.add(psw);

        JButton button1 = new JButton("注册账号");
        button1.setBounds(270, 25, 100, 25);
        button1.addActionListener(e -> {
            dispose();
            new Xregister();
        });
        panel3.add(button1);

        JButton button2 = new JButton("修改密码");
        button2.setBounds(270, 95, 100, 25);
        button2.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"作者太懒没有实现");
        });
        panel3.add(button2);


        button3.setBounds(80, 150, 140, 40);
        panel3.add(button3);
        button3.addActionListener(e -> {
            String Sact = act.getText();   //获取文本框中的账号

            Xuser user = Xbank.getXuser(Sact);
            if (user != null) {
                char[] Cpsw = psw.getPassword();
                String Spsw = new String(Cpsw);
                if (Spsw.equals(user.getPassword()))
                {
                    dispose();
                    new XInterface(user);
                }
                else{
                    JOptionPane.showMessageDialog(null, "密码错误！",
                            "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        setVisible(true);  //可见
    }


}