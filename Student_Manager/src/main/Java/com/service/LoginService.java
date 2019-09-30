package com.service;

import com.pojo.Student;

public interface LoginService {
    public Student signIn(Student student);

    public Integer signUp(Student student);
}
