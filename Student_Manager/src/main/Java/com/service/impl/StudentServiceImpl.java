package com.service.impl;

import com.dao.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pojo.Student;
import com.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper mapper;

    public List<Student> queryAll(){
        return mapper.queryAll();
    }

    public Student queryByStuNum(String stuNum){
        return mapper.queryByStuNum(stuNum);
    }

    public Integer delete(String name){
        return mapper.delete(name);
    }

    public Integer addStudent(Student student) {
        return mapper.addStudent(student);
    }

    public Integer updateStudent(Student student) {
        return mapper.updateStudent(student);
    }
}
