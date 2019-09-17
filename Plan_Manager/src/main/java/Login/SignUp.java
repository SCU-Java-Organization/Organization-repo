package Login;

import Login.JDBC.Login_SQL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp {
    public void showUI(){
        JFrame signUpFrame = new JFrame();
        signUpFrame.setSize(400,460);                      //窗体大小
        signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       //可以退出
        signUpFrame.setLocationRelativeTo(null);                          //相对屏幕居中
        signUpFrame.setTitle("Sign Up");                                  //窗体名字
        signUpFrame.setResizable(false);                                  //禁止放缩
        ImageIcon imageIcon = new ImageIcon("imgs/PM.png");
        signUpFrame.setIconImage(imageIcon.getImage());
        FlowLayout flow = new FlowLayout();
        signUpFrame.setLayout(flow);

        ImageIcon icon = new ImageIcon("imgs/Logo.png");
        icon.setImage(icon.getImage().getScaledInstance(500,221,Image.SCALE_DEFAULT));
        JLabel logo = new JLabel(icon);
        signUpFrame.add(logo);

        JLabel nameLabel  = new JLabel("你的姓名：");
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300,30));
        JLabel accountLabel = new JLabel("注册账号：");
        JTextField accountField = new JTextField();
        accountField.setPreferredSize(new Dimension(300,30));
        JLabel psdLabel = new JLabel("输入密码：");
        JPasswordField psdField = new JPasswordField();
        psdField.setPreferredSize(new Dimension(300,30));
        JLabel psdLabel2 = new JLabel("确认密码：");
        JPasswordField psdField2 = new JPasswordField();
        psdField2.setPreferredSize(new Dimension(300,30));
        signUpFrame.add(accountLabel);
        signUpFrame.add(accountField);
        signUpFrame.add(nameLabel);
        signUpFrame.add(nameField);
        signUpFrame.add(psdLabel);
        signUpFrame.add(psdField);
        signUpFrame.add(psdLabel2);
        signUpFrame.add(psdField2);

        JButton submitButton = new JButton("提交注册");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = accountField.getText();
                String name = nameField.getText();
                String psd1 = String.valueOf(psdField.getPassword());
                String psd2 = String.valueOf(psdField2.getPassword());
                System.out.println("username: " +  username + " psd1: " + psd1 + " psd2: " + psd2);
                if(username.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "用户名不能含有空格！", "注册失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(username.trim().equals("")|| psd1.equals("") || psd2.equals("")) {
                    JOptionPane.showMessageDialog(null, "用户名或密码不能为空！", "注册失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(!psd1.equals(psd2)) {
                    JOptionPane.showMessageDialog(null, "两次密码输入不一致！", "注册失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String info = Login_SQL.signUp(username, psd1,name);
                System.out.println(info);
                if(info.equals("EXIST")) {
                    JOptionPane.showMessageDialog(null, "该用户名已经存在！", "注册失败", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null, "已通过验证，注册成功！", "注册信息", JOptionPane.INFORMATION_MESSAGE);
                signUpFrame.dispose();
                Login loginFrame = new Login();
                loginFrame.showUI();
            }
        });
        signUpFrame.add(submitButton);

        JButton clearButton = new JButton("重置信息");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountField.setText("");
                nameField.setText("");
                psdField.setText("");
                psdField2.setText("");
            }
        });
        signUpFrame.add(clearButton);

        JButton backButton = new JButton("返回登录");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpFrame.setVisible(false);
                Login loginFrame = new Login();
                loginFrame.showUI();
                signUpFrame.dispose();
            }
        });
        signUpFrame.add(backButton);

        signUpFrame.setVisible(true);
    }
}
