import javax.swing.*;
import java.awt.*;

public class Xregister extends JFrame {
    public Xregister() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);   //不可改变大小
        setBounds(530, 220, 440, 340);

        JPanel panel1 = new JPanel();
        add(panel1, BorderLayout.NORTH);

        JLabel title = new JLabel("X Bank Register");
        title.setFont(new Font("黑体", Font.PLAIN, 30));
        panel1.add(title);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4, 1));  //网格布局
        add(panel2, BorderLayout.WEST);

        JLabel account = new JLabel("  设置账号:");
        account.setFont(new Font("黑体", Font.BOLD, 20));
        panel2.add(account);
        JLabel password1 = new JLabel("  设置密码:");
        password1.setFont(new Font("黑体", Font.BOLD, 20));
        panel2.add(password1);
        JLabel password2 = new JLabel("  确认密码:");
        password2.setFont(new Font("黑体", Font.BOLD, 20));
        panel2.add(password2);

        JPanel panel3 = new JPanel();
        panel3.setLayout(null);  //设置为绝对布局
        add(panel3, BorderLayout.CENTER);

        JTextField act = new JTextField();    //文本框
        act.setBounds(50, 20, 200, 25);
        JPasswordField psw1 = new JPasswordField(200);   //密码框
        psw1.setBounds(50, 85, 200, 25);
        JPasswordField psw2 = new JPasswordField(200);   //密码框
        psw2.setBounds(50, 150, 200, 25);
        panel3.add(act);
        panel3.add(psw1);
        panel3.add(psw2);

        JButton button = new JButton("Confirm");
        button.setBounds(20, 200, 150, 40);
        button.addActionListener(e -> {
            String userName = act.getText();
            String passwd1 = new String(psw1.getPassword());
            String passwd2 = new String(psw2.getPassword());

            //先判断账户是否存在
            Xuser xuser = Xbank.getXuser(userName);
            if (xuser != null)//如果查找到了账户
                JOptionPane.showMessageDialog(null, "该账号已存在！");
            else {
                if (userName.equals(""))
                    JOptionPane.showMessageDialog(null, "账号不能为空！");
                else if (passwd1.equals(""))
                    JOptionPane.showMessageDialog(null, "密码不能为空！");
                    //判断两次密码输入是否相等
                    //两个数组不能用equals？？
                else if (passwd1.equals(passwd2)) {
                    Xuser newUser = new Xuser(userName, passwd1);
                    Xbank.add(newUser);
                    JOptionPane.showMessageDialog(null, "注册成功！",
                            "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                    dispose();
                    new Login();
                } else
                    JOptionPane.showMessageDialog(null, "两次密码不一致");
            }

        });
        panel3.add(button);
    }
}
