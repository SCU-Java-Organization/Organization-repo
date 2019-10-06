package com.company;

import java.util.Scanner;

public class Run {
    public void run(){
        Scanner in = new Scanner(System.in);
        Bank bank = new Bank();
        Person person = new Person();
        person.setName("wangyao");
        bank.getPerson().add(0,person);
        while (true){
            System.out.println("是否开户,是输入y");
            String t = in.nextLine();
            if (t.equals("y")){
                System.out.println("输入您要创建的账号");
                Person person1 = new Person();
                person1.setName(in.nextLine());
                bank.getPerson().add(person1);
            }else break;
        }
        int j = 0;
        System.out.println("请输入您账号:");
        j = bank.huanhao(in.next());
        while(true){
            int j2 = 0;;
            System.out.println("请输入您要办理的业务,取钱请按1,存钱请按2,转账请按3,查看余额请按4,查看所有账户请按5,换账号登陆请按6,退出请按0");
            int type = in.nextInt();
            switch(type){
                case 1:
                    System.out.println("请输入你取钱的金额");
                    bank.quqian(j,in.nextInt());
                    break;
                case 2:
                    System.out.println("请输入你存钱的金额");
                    bank.cunqian(j,in.nextInt());
                    break;
                case 3:
                    System.out.println("请输入你要转入的账号");
                    j2 = bank.huanhao(in.next());
                    System.out.println("请输入你转账的金额");
                    bank.transfer(j,j2,in.nextInt());
                    break;
                case 4:
                    System.out.println(""+bank.getPerson().get(j).getName()+"的余额还剩"+bank.getPerson().get(j).getMoney());
                    break;
                case 5:
                    for(int i = 0;i < bank.getPerson().size();i++){
                        System.out.println(bank.getPerson().get(i).getName());
                    }
                    break;
                case 6:
                    System.out.println("请输入您账号:");
                    j=bank.huanhao(in.next());
                    break;
                default:
                    System.out.println("您已退出程序,欢迎下次使用");
                    System.exit(0);
            }
        }
    }
}
