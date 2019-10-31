package com.controller;

import com.pojo.Student;
import com.service.LoginService;
import com.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/html/login")
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/signIn.do", method = RequestMethod.POST)
    @ResponseBody
    public List<String> signIn(HttpServletRequest request){
        // 获取输入的学号和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Student stu = new Student();
        stu.setStuNum(username);
        stu.setPassword(password);

        // 返回的list
        List<String> list = new ArrayList<>();
        list.add(username);

        // 获取session
        Student student = loginService.signIn(stu);
        if(student != null){
            HttpSession session = request.getSession();
            session.setAttribute("currentStu", username);
            System.out.print("Session = " + session);
            list.add(student.getRoleID() + "");
            return list;
        }
        else
            list.add("error");
        return list;
    }

    @RequestMapping("/signUp.do")
    @ResponseBody
    public Integer signUp(Student student){
        char[] stuNum = student.getStuNum().toCharArray();
        if(studentService.checkExist(student.getStuNum()) > 0)
            return 2;

        if(stuNum.length != 13)
            return 3;

        for (int i = 0; i < stuNum.length; i++){
            if(stuNum[i] > '9' || stuNum[i] < '0'){
                //System.out.println("出错在" + i + stuNum.indexOf(i));
                return 4;
            }
        }

        if(student.getName().length() > 5)
            return 5;

        loginService.signUp(student);
        return 1;
    }

    @RequestMapping("/shiro.do")
    @ResponseBody
    public String login(HttpServletRequest request){
        // 获取输入的学号和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Student stu = new Student();
        stu.setStuNum(username);
        stu.setPassword(password);

        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            try{
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                currentUser.login(token);
            } catch (Exception e){
                return "fail!";
            }

        }
        return "success!";
    }
}
