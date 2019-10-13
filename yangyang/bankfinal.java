package bank;

import java.util.ArrayList;
import java.util.Scanner;

public class bankfinal {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int i=1;
        int account;
        int e;
        int j=0;
        System.out.println("欢迎来到模拟银行！");//引言
        bank two = new bank();
        while(j!=3) {
            System.out.println("输入1:注册；输入2：登录；输入3：退出");
            //防止注册人数超过数组长度
            if (i == 101) {
                System.out.println("注册人数已满");
                continue;
            }

            j=sc.nextInt();
            if (j == 1)
                i = two.register(i);
            else if (j == 2) {
                account = two.login(i);
                do {
                    System.out.println("输入1：查看余额；2：存钱；3：取钱；4：转账;-1:退出");
                    e = sc.nextInt();
                    if (e == 1) {
                        two.check1(account);
                    }
                    if (e == 2) {
                        two.save(account);
                    }
                    if (e == 3) {
                        two.withdraw(account);
                    }
                    if (e == 4) {
                        two.trans(account, i);
                    }
                } while (e != -1);
            } else
                System.out.println("登录失败！请重试");
        }
    }



}
