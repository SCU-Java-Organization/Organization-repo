package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pojo.Student;
import com.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/html/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/getAllStudents.do")
    @ResponseBody
    public List<Student> getAllStudents (){
        System.out.println("SpringMVC配置成功！");
        List<Student> list = studentService.queryAll();
        //html/allStudents.html
        return list;
    }

    @RequestMapping("/getStudentByStuNum.do")
    @ResponseBody
    public Student getStudentByStuNum(Student param){
        Student student = studentService.queryByStuNum(param.getStuNum());
        return student;
    }
}
