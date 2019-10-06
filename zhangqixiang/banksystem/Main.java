import com.sun.net.httpserver.Authenticator;
import com.sun.source.tree.NewArrayTree;
import jdk.dynalink.beans.StaticClass;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        // write your code here
        EventQueue.invokeLater(() -> {                         //事件分派线程
            int choice;
            try(Scanner in = new Scanner(System.in)) {
                System.out.println("你想创建一个银行吗？还是读取一个银行的账号信息？(创建银行请按1，读取银行请按2)");
                choice = in.nextInt();
                System.out.println("请输入银行名字");
                String name = in.next();

                Bank abank = new Bank(name, new ArrayList<Account>());
                if (choice == 1) {
                    try {
                        abank.createFile(name);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    File file = new File("D:\\" + name + ".txt");
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        ArrayList<Account> list = new ArrayList<>();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] source = line.split(",", 4);
                            Integer year = Integer.parseInt(source[3]);
                            Double balance = Double.parseDouble(source[2]);
                            Account temp = new Account(source[0], balance, year, source[1]);
                            list.add(temp);
                        }
                        abank.setList(list);
                    } catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(null, "There is no such file");
                        e.printStackTrace();
                        System.exit(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                File file1 = new File("D:\\" + name + ".txt");

                Toolkit toolkit = Toolkit.getDefaultToolkit();                                   //由于获取频幕的大小
                Dimension screenSize = toolkit.getScreenSize();
                int width = screenSize.width;
                int height = screenSize.height;

                JFrame systemFrame = new JFrame(abank.getName() + "的银行管理系统");
//            systemFrame.setSize(width / 2, height);
//            systemFrame.setLocationByPlatform(true);
                systemFrame.setBounds(0, 0, 800, 768);                     //设置图形界面大小
                systemFrame.setBackground(Color.lightGray);                                       //用于设置图形界面的颜色
                systemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                       //设置窗口点击关闭时会直接关闭

                JFrame assignFrame = new JFrame("注册");
                assignFrame.setBounds(width / 3, height / 4, 400, 500);
                assignFrame.setBackground(Color.lightGray);
                assignFrame.setVisible(false);
                assignFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                assignFrame.setVisible(true);

                JTextField assignNam = new JTextField(10);
                assignNam.setBounds(160, 115, 150, 24);
                assignNam.setEditable(true);
                JTextField assignPassword = new JTextField(10);
                assignPassword.setBounds(160, 215, 150, 24);
                assignPassword.setEditable(true);
                JLabel assignUser = new JLabel("账户：");
                assignUser.setBounds(70, 115, 80, 24);
                JLabel assignPasswd = new JLabel("密码：");
                assignPasswd.setBounds(70, 215, 80, 24);

                ActionListener assignAction1 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Account assignnew = new Account(assignNam.getText(), (double) 0, 1, assignPassword.getText());
                        abank.getList().add(assignnew);
                        try {
                            FileWriter writer = new FileWriter("D:\\" + name + ".txt", false);
                            for (Account account : abank.getList()) {
                                writer.write(account.getName() + "," + account.getPassword() + "," + account.getBalance() + "," + account.getYear() + "\n");
                                writer.flush();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        systemFrame.setVisible(true);
                        assignFrame.setVisible(false);
                    }
                };
                ActionListener assignExit = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };

                JButton assign = new JButton("注册");
                assign.setBounds(100, 300, 80, 24);
                assign.setBackground(Color.gray);
                assign.setEnabled(true);
                assign.addActionListener(assignAction1);

                JButton exit = new JButton("退出");
                exit.setBackground(Color.gray);
                exit.setEnabled(true);
                exit.setBounds(260, 300, 80, 24);
                exit.addActionListener(assignExit);
                JPanel assignPanel = new JPanel();
                assignPanel.setLayout(null);
                assignPanel.setBackground(Color.lightGray);
                assignPanel.add(exit);
                assignPanel.add(assign);
                assignPanel.add(assignNam);
                assignPanel.add(assignPassword);
                assignPanel.add(assignPasswd);
                assignPanel.add(assignUser);
                assignFrame.add(assignPanel);

                JFrame loginFrame = new JFrame("登录");
                loginFrame.setBounds(width / 3, height / 4, 400, 500);
                loginFrame.setBackground(Color.lightGray);
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setVisible(true);

                if(choice == 2) {
                    systemFrame.setVisible(false);                                                    //设置其不可以被观察到
                    loginFrame.setVisible(true);
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else{
                    systemFrame.setVisible(true);
                    loginFrame.setVisible(false);
                    systemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                JTextField loginNam = new JTextField(10);
                loginNam.setBounds(160, 115, 150, 24);
                loginNam.setEditable(true);
                JTextField loginPassword = new JTextField(10);
                loginPassword.setBounds(160, 215, 150, 24);
                loginPassword.setEditable(true);
                JLabel loginUser = new JLabel("账户：");
                loginUser.setBounds(70, 115, 80, 24);
                JLabel loginPasswd = new JLabel("密码：");
                loginPasswd.setBounds(70, 215, 80, 24);

                ActionListener loginAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (abank.getList().isEmpty())
                            JOptionPane.showMessageDialog(null, "There is no account in the bank system");
                        else {
                            int length = abank.getList().size();
                            String name = loginNam.getText();
                            String password = loginPassword.getText();
                            ArrayList<Account> list = abank.getList();
                            boolean isprime = false;
                            for (int i = 0; i < length; i++) {
                                if (name.equals(abank.getList().get(i).getName())) {
                                    isprime = true;
                                    if (password.equals(abank.getList().get(i).getPassword())) {
                                        systemFrame.setVisible(true);
                                        loginFrame.setVisible(false);
                                        assignFrame.setVisible(false);
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "PASSWORD OR ACCOUNT WRONG");
                                        break;
                                    }
                                }
                            }
                            if (!isprime)
                                JOptionPane.showMessageDialog(null, "Account not exist");
                        }
                    }
                };

                ActionListener assignAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        systemFrame.setVisible(false);
                        loginFrame.setVisible(false);
                        assignFrame.setVisible(true);
                    }
                };

                ActionListener exitAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };

                JButton loginbutton = new JButton("登录");
                loginbutton.setBackground(Color.gray);
                loginbutton.setEnabled(true);
                loginbutton.setBounds(60, 300, 80, 24);
                loginbutton.addActionListener(loginAction);
                JButton assignbutton = new JButton("注册");
                assignbutton.setBackground(Color.gray);
                assignbutton.setEnabled(true);
                assignbutton.setBounds(160, 300, 80, 24);
                assignbutton.addActionListener(assignAction);
                JButton exitbutton = new JButton("退出");
                exitbutton.setBackground(Color.gray);
                exitbutton.setEnabled(true);
                exitbutton.setBounds(260, 300, 80, 24);
                exitbutton.addActionListener(exitAction);
                exit.addActionListener(exitAction);

                JPanel loginPanel = new JPanel();
                loginPanel.setLayout(null);
                loginPanel.setBackground(Color.lightGray);                                          //创建一个组建容器
                loginPanel.add(loginNam);
                loginPanel.add(loginPassword);
                loginPanel.add(loginUser);
                loginPanel.add(loginPasswd);
                loginPanel.add(assignbutton);
                loginPanel.add(loginbutton);
                loginPanel.add(exitbutton);
                loginFrame.add(loginPanel);

                JTextField textNam = new JTextField(10);
                textNam.setBounds(350, 115, 200, 24);
                textNam.setEditable(true);
                JTextField textPassword = new JTextField(10);
                textPassword.setBounds(350, 215, 200, 24);
                textPassword.setEditable(true);
                JTextField textBalance = new JTextField(10);
                textBalance.setBounds(350, 315, 200, 24);
                textBalance.setEditable(true);
                JTextField textYear = new JTextField(10);
                textYear.setBounds(350, 415, 200, 24);
                textYear.setEditable(true);
                JTextField interestText1 = new JTextField(10);
                interestText1.setBounds(180, 25, 50, 24);
                interestText1.setEditable(true);
                JTextField interestText2 = new JTextField(10);
                interestText2.setBounds(450, 25, 50, 24);
                interestText2.setEditable(true);
                JTextField interestText3 = new JTextField(10);
                interestText3.setBounds(730, 25, 50, 24);
                interestText3.setEditable(true);
                JTextField nextYear = new JTextField(10);
                nextYear.setBounds(350, 515, 150, 24);
                nextYear.setEditable(true);
                JTextField addtion = new JTextField(10);
                addtion.setBounds(600, 315, 100, 24);
                addtion.setVisible(true);
                JTextField subtract = new JTextField(10);
                subtract.setBounds(600, 350, 100, 24);
                subtract.setVisible(true);

                ActionListener buttonAction = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (abank.getList().isEmpty())
                            JOptionPane.showMessageDialog(null, "There is no account in the bank system");
                        else {
                            int length = abank.getList().size();
                            String name = textNam.getText();
                            String password = textPassword.getText();
                            ArrayList<Account> list = abank.getList();
                            boolean isprime = false;
                            for (int i = 0; i < length; i++) {
                                if (name.equals(abank.getList().get(i).getName())) {
                                    isprime = true;
                                    if (password.equals(abank.getList().get(i).getPassword())) {
                                        textBalance.setText(list.get(i).getBalance().toString());
                                        textYear.setText(list.get(i).getYear().toString());
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "PASSWORD WRONG");
                                        break;
                                    }
                                }
                            }
                            if (!isprime)
                                JOptionPane.showMessageDialog(null, "Account not exist");
                        }
                    }
                };

                ActionListener buttonaction1 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (textNam.getText() == null || textPassword.getText() == null || textBalance.getText() == null || textYear.getText() == null)
                            JOptionPane.showMessageDialog(null, "message not filled");
                        else {
                            Account newone = new Account(
                                    textNam.getText(), Double.parseDouble(textBalance.getText()),
                                    Integer.parseInt(textYear.getText()), textPassword.getText());
                            abank.getList().add(newone);
                        }
                    }
                };

                ActionListener buttonaction2 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<Account> list = abank.getList();
                        Double amount = 0.0;
                        if (textBalance.getText() == null || textYear.getText() == null)
                            JOptionPane.showMessageDialog(null, "please enter the your username and password and press '查询'");
                        amount = Double.parseDouble(textBalance.getText()) * (1 + new Account("", 12.0, Integer.parseInt(textYear.getText()), "").computeInterest(
                                Double.parseDouble(interestText1.getText()), Double.parseDouble(interestText2.getText()), Double.parseDouble(interestText3.getText())));
                        nextYear.setText(amount.toString());
                    }
                };

                ActionListener buttonaction3 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        abank.setInterest(
                                Double.parseDouble(interestText1.getText()), Double.parseDouble(interestText2.getText()), Double.parseDouble(interestText3.getText()));
                    }
                };

                ActionListener buttonaction4 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        textNam.setText(null);
                        textBalance.setText(null);
                        textYear.setText(null);
                        nextYear.setText(null);
                        textPassword.setText(null);
                    }
                };

                ActionListener buttonaction5 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        interestText1.setText(null);
                        interestText2.setText(null);
                        interestText3.setText(null);
                    }
                };

                ActionListener buttonaction6 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            FileWriter writer = new FileWriter("D:\\" + name + ".txt", false);
                            for (Account account : abank.getList()) {
                                writer.write(account.getName() + "," + account.getPassword() + "," + account.getBalance() + "," + account.getYear() + "\n");
                                writer.flush();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                };

                ActionListener buttonaction7 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (abank.getList().isEmpty())
                            JOptionPane.showMessageDialog(null, "There is no account in the bank system");
                        else {
                            int length = abank.getList().size();
                            String name = textNam.getText();
                            String password = textPassword.getText();
                            ArrayList<Account> list = abank.getList();
                            boolean isprime = false;
                            for (int i = 0; i < length; i++) {
                                if (name.equals(abank.getList().get(i).getName())) {
                                    isprime = true;
                                    if (password.equals(abank.getList().get(i).getPassword())) {
                                        abank.getList().get(i).add(Double.parseDouble(addtion.getText()));
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "PASSWORD WRONG");
                                        break;
                                    }
                                }
                            }
                            if (!isprime)
                                JOptionPane.showMessageDialog(null, "Account not exist");
                        }
                    }
                };

                ActionListener buttonaction8 = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (abank.getList().isEmpty())
                            JOptionPane.showMessageDialog(null, "There is no account in the bank system");
                        else {
                            int length = abank.getList().size();
                            String name = textNam.getText();
                            String password = textPassword.getText();
                            ArrayList<Account> list = abank.getList();
                            boolean isprime = false;
                            for (int i = 0; i < length; i++) {
                                if (name.equals(abank.getList().get(i).getName())) {
                                    isprime = true;
                                    if (password.equals(abank.getList().get(i).getPassword())) {
                                        abank.getList().get(i).subtract(Double.parseDouble(subtract.getText()));
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null, "PASSWORD WRONG");
                                        break;
                                    }
                                }
                            }
                            if (!isprime)
                                JOptionPane.showMessageDialog(null, "Account not exist");
                        }
                    }
                };

                JButton button = new JButton("查询");
                button.setBackground(Color.gray);
                button.setEnabled(true);                                                          //设置按钮是否可以按动
                button.setBounds(100, 600, 160, 24);
                button.addActionListener(buttonAction);

                JButton button1 = new JButton("添加");
                button1.setBackground(Color.gray);
                button1.setEnabled(true);
                button1.setBounds(300, 600, 160, 24);
                button1.addActionListener(buttonaction1);

                JButton button2 = new JButton("计算");
                button2.setBackground(Color.gray);
                button2.setEnabled(true);
                button2.setBounds(500, 600, 160, 24);
                button2.addActionListener(buttonaction2);

                JButton button3 = new JButton("RESET");
                button3.setBackground(Color.gray);
                button3.setEnabled(true);
                button3.setBounds(180, 65, 160, 24);
                button3.addActionListener(buttonaction3);

                JButton button4 = new JButton("清除");
                button4.setBackground(Color.gray);
                button4.setEnabled(true);
                button4.setBounds(200, 650, 160, 24);
                button4.addActionListener(buttonaction4);

                JButton button5 = new JButton("CLEAR");
                button5.setBackground(Color.gray);
                button5.setEnabled(true);
                button5.setBounds(420, 65, 160, 24);
                button5.addActionListener(buttonaction5);

                JButton button6 = new JButton("保存");
                button6.setBackground(Color.gray);
                button6.setEnabled(true);
                button6.setBounds(400, 650, 160, 24);
                button6.addActionListener(buttonaction6);

                JButton button7 = new JButton("存款");
                button7.setBackground(Color.gray);
                button7.setEnabled(true);
                button7.setBounds(700, 315, 60, 24);
                button7.addActionListener(buttonaction7);

                JButton button8 = new JButton("取款");
                button8.setBackground(Color.gray);
                button8.setEnabled(true);
                button8.setBounds(700, 350, 60, 24);
                button8.addActionListener(buttonaction8);

                JLabel AccountName = new JLabel("USER NAME:");
                AccountName.setBounds(200, 100, 200, 50);
                JLabel AccountPassword = new JLabel("PASSWORD:");
                AccountPassword.setBounds(200, 200, 200, 50);
                JLabel AccountBalance = new JLabel("USER BALANCE:");
                AccountBalance.setBounds(200, 300, 200, 50);
                JLabel AccountYear = new JLabel("DEPOSIT YEAR:");
                AccountYear.setBounds(200, 400, 200, 50);

                JLabel NextYear = new JLabel("NEXT YEAR YOUR BALANCE WILL BE :");
                NextYear.setBounds(100, 500, 300, 50);
                JLabel interest1 = new JLabel("INTEREST FOR ONE YEAR:");
                interest1.setBounds(10, 10, 200, 50);
                JLabel interest2 = new JLabel("INTEREST FOR TWO YEAR:");
                interest2.setBounds(280, 10, 200, 50);
                JLabel interest3 = new JLabel("INTEREST FOR THREE YEAR:");
                interest3.setBounds(550, 10, 200, 50);

                JPanel apanel = new JPanel();
                apanel.setLayout(null);
                apanel.setBackground(Color.lightGray);                                          //创建一个组建容器
                apanel.add(button);
                apanel.add(button1);
                apanel.add(button2);
                apanel.add(button3);
                apanel.add(button4);
                apanel.add(button5);
                apanel.add(button6);
                apanel.add(button7);
                apanel.add(button8);
                apanel.add(interest1);
                apanel.add(interest2);
                apanel.add(interest3);
                apanel.add(AccountPassword);
                apanel.add(AccountName);
                apanel.add(AccountBalance);
                apanel.add(AccountYear);
                apanel.add(NextYear);
                apanel.add(textNam);
                apanel.add(textPassword);
                apanel.add(textBalance);
                apanel.add(textYear);
                apanel.add(interestText1);
                apanel.add(interestText2);
                apanel.add(interestText3);
                apanel.add(nextYear);
                apanel.add(addtion);
                apanel.add(subtract);

                systemFrame.add(apanel);
//            systemFrame.setVisible(true);                   //设置窗口可视性
                interestText1.setText(abank.getInterest1().toString());
                interestText2.setText(abank.getInterest2().toString());
                interestText3.setText(abank.getInterest3().toString());
            }catch (InputMismatchException e){
                System.out.println("what do you want to do?");
                System.exit(1);
            }
        });
    }
}



