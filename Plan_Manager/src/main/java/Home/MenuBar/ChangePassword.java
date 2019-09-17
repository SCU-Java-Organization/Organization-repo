package Home.MenuBar;

import Login.JDBC.Login_SQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword {

    public static void main(String[] args){
        ChangePassword cPsd = new ChangePassword();
        cPsd.showUI("SJL");
    }
    public static void showUI(String username){
        JFrame changePsdFrame = new JFrame();
        changePsdFrame.setSize(300,240);
        changePsdFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changePsdFrame.setLocationRelativeTo(null);
        changePsdFrame.setResizable(false);
        changePsdFrame.setTitle("Change Password");

        changePsdFrame.setLayout(null);

        JLabel psdLabel = new JLabel("原始密码：");
        psdLabel.setBounds(20,30,60,30);
        JTextField psdField = new JTextField();
        psdField.setBounds(80,30,180,30);

        JLabel newLabel = new JLabel("新密码：");
        newLabel.setBounds(20,65,60,30);
        JPasswordField newField = new JPasswordField();
        newField.setBounds(80,65,180,30);

        JLabel confirmLabel = new JLabel("确认密码：");
        confirmLabel.setBounds(20,100,60,30);
        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(80,100,180,30);

        JButton acceptBtn = new JButton("修改");
        acceptBtn.setBounds(75,145,60,30);
        acceptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String psd1 = String.valueOf(newField.getPassword());
                String psd2 = String.valueOf(confirmField.getPassword());
                if(!psd1.equals(psd2)){
                    JOptionPane.showMessageDialog(null, "两次密码输入不一致！", "修改密码", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String input_psd = psdField.getText();
                    String sql_psd = Login_SQL.loginCheck(username, input_psd);
                    if(!(sql_psd.equals(input_psd))){
                        JOptionPane.showMessageDialog(null, "原密码错误！", "修改密码", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Login_SQL.updatePsd(username, psd1);
                    JOptionPane.showMessageDialog(null, "修改成功！", "修改密码", JOptionPane.INFORMATION_MESSAGE);
                    changePsdFrame.dispose();
                }
            }
        });
        JButton resetBtn = new JButton("重置");
        resetBtn.setBounds(150,145,60,30);
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                psdField.setText("");
                newField.setText("");
                confirmField.setText("");
            }
        });

        changePsdFrame.add(psdLabel);
        changePsdFrame.add(psdField);
        changePsdFrame.add(newLabel);
        changePsdFrame.add(newField);
        changePsdFrame.add(confirmLabel);
        changePsdFrame.add(confirmField);
        changePsdFrame.add(acceptBtn);
        changePsdFrame.add(resetBtn);
        changePsdFrame.setVisible(true);
    }
}
