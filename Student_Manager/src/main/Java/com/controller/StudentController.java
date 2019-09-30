package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pojo.Student;
import com.service.StudentService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/html/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/getAllStudents.do")
    @ResponseBody
    public List<Student> getAllStudents (){
        return studentService.queryAll();
    }

    @RequestMapping("/getStudentByStuNum.do")
    @ResponseBody
    public Student getStudentByStuNum(Student param){
        return studentService.queryByStuNum(param.getStuNum());
    }

    @RequestMapping("/deleteStudent.do")
    @ResponseBody
    public Integer deleteStudent(Student student){
        return studentService.delete(student.getName());
    }

    @RequestMapping("/uploadImg.do")
    @ResponseBody
    public void uploadImg(Student student, MultipartFile img){

    }

    @RequestMapping("/addStudent.do")
    @ResponseBody
    public Integer addStudent(Student student){
        int exist = studentService.checkExist(student.getStuNum());
        if(exist > 0)
            return -1;
        return studentService.addStudent(student);
    }

    @RequestMapping("/updateStudent.do")
    @ResponseBody
    public Integer updateStudent(Student student){
        return studentService.updateStudent(student);
    }

    @RequestMapping("/resetPassword.do")
    @ResponseBody
    public Integer resetPassword(HttpServletRequest request){
        return studentService.resetPassword(request.getParameter("oldPsw"), request.getParameter("newPsw"));
    }
}
