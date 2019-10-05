package bank;
import java.util.Scanner;
public class person {
    //成员变量
    String name;
    int account;
    String password;
    float money;

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

}
