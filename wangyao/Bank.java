package com.company;

import java.util.*;

public class Bank {

    private List<Person> person = new ArrayList<>();

    public List<Person> getPerson() {
        return person;
    }
    public void quqian(int j, int money){
        int money1 = person.get(j).getMoney();
        money1 -= money;
        person.get(j).setMoney(money1);
        System.out.println(""+person.get(j).getName()+"的余额还剩"+money1);
    }

    public void cunqian(int j,int money){
        int money1 = person.get(j).getMoney();
        money1 += money;
        person.get(j).setMoney(money1);
        System.out.println(""+person.get(j).getName()+"的余额还剩"+money1);
    }

    public void transfer(int j,int j2,int money){
        int money1 = person.get(j).getMoney();
        money1 -= money;
        person.get(j).setMoney(money1);
        int money2 = person.get(j2).getMoney();
        money2 += money;
        person.get(j2).setMoney(money2);
        System.out.println(""+person.get(j).getName()+"的余额还剩"+money1);
        System.out.println(""+person.get(j2).getName()+"的余额还剩"+money2);
    }
    public int huanhao(String temp){
        for(int i = 0;i < person.size();i++){
            if(temp.equals(person.get(i).getName())) {
                return i;
            }
        }
        System.out.println("你输入的账号不存在,返回原账号");
        return 0;
    }
}