import javax.swing.*;
import java.io.Serializable;

public class Xuser implements Serializable{
    private String userName;
    private String password;
    private double balance;

    public Xuser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addMoney(double money) {

        this.balance += money;
    }

    public boolean takeMoney(double money) {
        if(money>balance)
        {
            JOptionPane.showMessageDialog(null,"余额不足！");
            return false;
        }
        else
        {
            this.balance -= money;
            JOptionPane.showMessageDialog(null,"操作成功！");
            return true;
        }

    }
}