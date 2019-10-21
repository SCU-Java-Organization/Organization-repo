package com.company;

import java.util.ArrayList;

public class Stack {
    ArrayList<Integer> list = new ArrayList<>();
    public void add(int number){
        list.add(number);
    }
    public int size(){
        return list.size();
    }

    public int qu(){
        return list.remove(list.size() - 1);
    }
    public boolean isEmply(){
        return list.size() == 0;
    }
    public void clear(){
        list.clear();
    }


    @Override
    public String toString() {
        return "Stack{" +
                "list=" + list +
                '}';
    }
}
