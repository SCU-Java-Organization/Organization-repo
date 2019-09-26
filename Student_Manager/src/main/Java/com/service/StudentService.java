package com.service;

import com.pojo.Student;

import java.util.List;

public interface StudentService {
    public List<Student> queryAll();

    public Student queryByStuNum(String stuNum);
}
