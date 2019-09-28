package com.dao;

import com.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper {
    public Student signIn(String username);

    public Integer signUp(Student student);
}
