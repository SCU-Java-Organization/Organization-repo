package com.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)
            throws Exception{
        HttpSession session = request.getSession();
        System.out.print("username = " + request.getAttribute("username"));
        if(session.getAttribute("currentStu") != null){
            session.setAttribute("currentStu", null);
            return true;
        }
        else{
            response.sendRedirect(request.getContextPath() + "/html/login/login.html");
            System.out.println("进行拦截！！");
            System.out.println(request.getContextPath() + "/html/login/login.html");
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // 执行完毕，返回前拦截
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // 在处理过程中，执行拦截
    }
}
