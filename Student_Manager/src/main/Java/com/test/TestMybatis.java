package com.test;

import com.dao.StudentMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.pojo.Student;

import java.util.List;

public class TestMybatis {
    @Test
    public void testMybatis() throws Exception{
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentMapper mapper = (StudentMapper)applicationContext.getBean("studentMapper");
        //List<Student> list = mapper.queryAll();
        //for (Student s : list)
        //    System.out.println(s);
        Student student = mapper.queryByStuNum("2017141461144");
        System.out.println(student);
    }
}
