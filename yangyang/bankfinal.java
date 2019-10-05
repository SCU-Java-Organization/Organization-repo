package bank;

import java.util.ArrayList;
import java.util.Scanner;

public class bankfinal {
    public static void main(String[] args) {
        person[] array = new person[100];
        Scanner sc=new Scanner(System.in);
        int i=1;
        int j=0;
        int account;
        int e;
        System.out.println("欢迎来到模拟银行！");//引言
        do {
            System.out.println("输入1:注册；输入2：登录；输入3：退出");
            //防止注册人数超过数组长度
            if (i==101){
                System.out.println("注册人数已满");
                continue;
            }
            j=sc.nextInt();
            if(j==1) {
                System.out.println("请注册");
                person one = new person();
                one.account = i ;
                System.out.println("账号：" + one.account);
                System.out.println("请输入姓名：");
                one.name = sc.next();
                System.out.println("请输入密码");
                one.password = sc.next();

                System.out.println("注册成功！");
                array[i-1] = one;//将one地址给array的第i项
                i++;
                continue;
            }
            if(j==2){
                System.out.println("账号：");
                account=sc.nextInt();
                System.out.println("密码：");
                String password=sc.next();
                if (account>=i){
                    System.out.println("该用户不存在！");
                    continue;
                }
                if (password.equals(array[account-1].password)){
                    System.out.println(array[account-1].name+"登陆成功！");
                    do {
                        System.out.println("输入1：查看余额；2：存钱；3：取钱；4：转账;-1:退出");
                        e=sc.nextInt();
                        if(e==1){
                            array[account-1].check();
                        }
                        if(e==2){
                            System.out.println("输入存款金额：");
                            array[account-1].save(sc.nextInt());
                        }
                        if(e==3){
                            System.out.println("输入取款金额：");
                            array[account-1].withdraw(sc.nextInt());
                        }
                        if(e==4){
                            System.out.println("转账账号：");
                            int c=sc.nextInt();
                            System.out.println("转账金额：");
                            int num=sc.nextInt();
                            if (c>i)
                                System.out.println("该用户不存在！");
                            else {
                                if (array[account - 1].money < num)
                                    System.out.println("余额不足");
                                else {
                                    array[c - 1].money += num;
                                    array[account - 1].money -= num;
                                    System.out.println("转账成功！");
                                }
                            }
                        }
                    }while(e!=-1);
                }
                else
                    System.out.println("登录失败！请重试");
            }
        }while (j!=3);
    }



}
