package com.company;

import java.util.Arrays;

public class Bank {

    private Person[] person = new Person[100];
    public Person[] getPerson() {
        return person;
    }

    public void setPerson(Person[] person) {
        this.person = person;
    }

    public void quqian(int j,int money){
        int money1 = getPerson()[j].getMoney();
        money1 -= money;
        getPerson()[j].setMoney(money1);
        System.out.println(""+getPerson()[j].getName()+"的余额还剩"+money1);
    }

    public void cunqian(int j,int money){
        int money1 = getPerson()[j].getMoney();
        money1 += money;
        getPerson()[j].setMoney(money1);
        System.out.println(""+getPerson()[j].getName()+"的余额还剩"+money1);
    }

    public void transfer(int j,int j2,int money){
        int money1 = getPerson()[j].getMoney();
        money1 -= money;
        getPerson()[j].setMoney(money1);
        int money2 = getPerson()[j2].getMoney();
        money2 += money;
        getPerson()[j2].setMoney(money2);
        System.out.println(""+getPerson()[j].getName()+"的余额还剩"+money1);
        System.out.println(""+getPerson()[j2].getName()+"的余额还剩"+money2);
    }
    public int huanhao(String temp3){
        for(int i = 0;i < getPerson().length;i++){
            if(temp3.equals(getPerson()[i].getName())) {
                return i;
            }
        }
        System.out.println("你输入的账号不存在,返回原账号");
        return 0;
    }
}
