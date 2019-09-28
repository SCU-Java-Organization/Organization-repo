package com.service;

import com.pojo.Student;

public interface LoginService {
    public Student signIn(String username);

    public Integer signUp(Student student);
}
