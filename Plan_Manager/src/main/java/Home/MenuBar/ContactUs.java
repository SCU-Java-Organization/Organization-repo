package Home.MenuBar;

import javax.swing.*;

public class ContactUs {
    public static void main(String[] args){
        ContactUs contactUs = new ContactUs();
        contactUs.showUI();
    }
    
    public static void showUI(){
        JFrame contactFrame = new JFrame();
        contactFrame.setSize(300,240);
        contactFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contactFrame.setLocationRelativeTo(null);
        contactFrame.setResizable(false);
        contactFrame.setTitle("Contact Us");

        contactFrame.setLayout(null);

        JLabel email = new JLabel("E-Mail: sjl66666666@gmail.com");
        email.setBounds(20,30,200,30);

        JLabel qq = new JLabel("QQ: 953188895");
        qq.setBounds(20,65,200,30);

        JLabel phone = new JLabel("Phone: 17394947483");
        phone.setBounds(20,100,200,30);

        JLabel address = new JLabel("Address: 四川省成都市四川大学江安校区");
        address.setBounds(20,135,250,30);

        contactFrame.add(email);
        contactFrame.add(qq);
        contactFrame.add(phone);
        contactFrame.add(address);
        contactFrame.setVisible(true);
    }
}
