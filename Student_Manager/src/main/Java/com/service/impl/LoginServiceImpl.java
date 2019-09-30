package com.service.impl;

import com.dao.LoginMapper;
import com.dao.StudentMapper;
import com.pojo.Student;
import com.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    StudentMapper studentMapper;

    public Student signIn(Student student) {
        return loginMapper.signIn(student);
    }

    public Integer signUp(Student student){
        return studentMapper.addStudent(student);
    }
}
