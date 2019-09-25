package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {
    @RequestMapping("/index")
    public String index(){
        System.out.println("SpringMVC配置成功！");
        //html/index.html
        return "/student/index";
    }
}
