package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.pojo.Student;

import java.util.List;

@Repository
public interface StudentMapper {
    public List<Student> queryAll();

    public Student queryByStuNum(String stuNum);

    public Integer delete(String name);

    public Integer addStudent(Student student);

    public Integer updateStudent(Student student);

    public Integer checkExist(String stuNum);

    public Integer resetPassword(@Param("old") String oldPsw, @Param("new") String newPsw);
}
