package com.company;

import java.util.ArrayList;
import java.util.Objects;

public class Queue {
    ArrayList <Integer> list = new ArrayList<>();
    public void add(int number){
        list.add(number);
    }
    public int qu(){
        return list.remove(0);
    }
    public int size(){
        return list.size();
    }

  public void clear(){
        list.clear();
    }
    public boolean isEmply(){
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return "Queue{" +
                "list=" + list +
                '}';
    }
}
