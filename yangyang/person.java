package bank;
import java.util.Scanner;
public class person {
    //成员变量
    private String name;
    private int account;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    private float money;

    //成员方法
    public void check(){

        System.out.println("卡上余额"+money+"元");
    }

    public void save(int a){

        money=money+a;
        System.out.println("存钱成功！");
    }

    public void withdraw(int b){

        if (money<b)
            System.out.println("余额不足！");
        else {
            money = money - b;
            System.out.println("取钱成功！");
        }
    }

    public void trans(int a){

        money=money+a;

    }

}
