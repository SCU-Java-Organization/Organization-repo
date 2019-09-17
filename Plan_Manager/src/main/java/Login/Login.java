package Login;

import Home.Home;
import Login.JDBC.Login_SQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Login {
    public static void main(String[] args){
        Login login = new Login();
        login.showUI();
    }

    public void showUI(){
        //创建窗体
        JFrame loginFrame = new JFrame();
        loginFrame.setSize(400,400);                      //窗体大小
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //可以退出
        loginFrame.setLocationRelativeTo(null);                          //相对屏幕居中
        loginFrame.setTitle("Login");                                    //窗体名字
        loginFrame.setResizable(false);                                  //禁止放缩

        //流式布局
        FlowLayout flow = new FlowLayout();
        loginFrame.setLayout(flow);

        ImageIcon imageIcon = new ImageIcon("imgs/PM.png");
        loginFrame.setIconImage(imageIcon.getImage());

        //图像——创建JLabel对象，使用ImageIcon作为输入初始化JLabel
//        imgURL = Login.class.getResource("/imgs/Logo.png");
        ImageIcon icon = new ImageIcon("imgs/Logo.png");
        icon.setImage(icon.getImage().getScaledInstance(500,221,Image.SCALE_DEFAULT));
        JLabel logo = new JLabel(icon);
        loginFrame.add(logo);

        //文本输入——文字JLabel、账号JTextField、密码JPasswordField
        //除了JFrame设置大小为setSize(int x, int y),其他组件都用setPreferredSize(Dimension d)
        JLabel accountLabel = new JLabel("账号：");
        JTextField accountField = new JTextField();
        accountField.setPreferredSize(new Dimension(300,30));
        JLabel psdLabel = new JLabel("密码：");
        JPasswordField psdField = new JPasswordField();
        psdField.setPreferredSize(new Dimension(300,30));
        loginFrame.add(accountLabel);
        loginFrame.add(accountField);
        loginFrame.add(psdLabel);
        loginFrame.add(psdField);
        //JTextField jt3 = new JTextField(4);//设置输入框大小另一种方式——4个输入字符

        //复选框——JCheckBox
        JCheckBox keepPsd = new JCheckBox("记住密码");
        loginFrame.add(keepPsd);

        //登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = accountField.getText();
                String password = String.valueOf(psdField.getPassword());
                String sqlPassword = Login_SQL.loginCheck(username, password);
                System.out.println(sqlPassword);
                if (sqlPassword == null){
                    JOptionPane.showMessageDialog(null, "用户不存在！", "登录验证", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if (sqlPassword.equals(password)){
                    loginFrame.setVisible(false);
                    Home home = new Home();
                    home.setUsername(username);
                    home.showUI();
                    loginFrame.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "密码错误！", "登录验证", JOptionPane.WARNING_MESSAGE);
            }
        });
        loginFrame.add(loginButton);

//        注册按钮
        JButton signUpButton = new JButton("注册");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.setVisible(false);
                SignUp signUp = new SignUp();
                signUp.showUI();
                loginFrame.dispose();
            }
        });
        loginFrame.add(signUpButton);

//        监听回车键
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    String username = accountField.getText();
                    String password = String.valueOf(psdField.getPassword());
                    String sqlPassword = Login_SQL.loginCheck(username, password);
                    System.out.println(sqlPassword);
                    if (sqlPassword == null){
                        JOptionPane.showMessageDialog(null, "用户不存在！", "登录验证", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    else if (sqlPassword.equals(password)){
                        loginFrame.setVisible(false);
                        Home home = new Home();
                        home.setUsername(username);
                        home.showUI();
                        loginFrame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "密码错误！", "登录验证", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
        accountField.addKeyListener(keyListener);
        psdField.addKeyListener(keyListener);
        //窗体可见，写在add组件之后
        loginFrame.setVisible(true);
    }
}
