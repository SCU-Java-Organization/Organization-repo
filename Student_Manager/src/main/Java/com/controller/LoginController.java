package com.controller;

import com.pojo.Student;
import com.service.LoginService;
import com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/html/login")
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    StudentService studentService;

    @RequestMapping("/signIn.do")
    @ResponseBody
    public List<String> signIn(String username, String password){
        List<String> list = new ArrayList<>();
        list.add(username);

        Student student = loginService.signIn(username);
        System.out.println(student);
        if(student.getPassword().equals(password)){
            list.add(student.getRoleID() + "");
            return list;
        }

        list.add("error");
        return list;
    }

    @RequestMapping("/signUp.do")
    @ResponseBody
    public Integer signUp(Student student){
        return loginService.signUp(student);
    }
}
