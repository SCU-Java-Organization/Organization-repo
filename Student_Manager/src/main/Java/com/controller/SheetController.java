package com.controller;

import com.pojo.Attendance;
import com.pojo.Sheet;
import com.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/html/student")
public class SheetController {
    @Autowired
    private SheetService sheetService;

    @RequestMapping("/newSheet.do")
    @ResponseBody
    public void insertSheet(Sheet sheet){
        sheet.setDate(new Date());
        sheetService.insertSheet(sheet);
    }

    @RequestMapping("/getAttendance.do")
    @ResponseBody
    public List<Attendance> getAttendance(Attendance attendance){
        List<Attendance> list =  sheetService.getAttendanceByClass(attendance.getClassName());
        for (Attendance a : list) {
            a.setUtilDate(a.getDate().toString());
            System.out.print(a.getUtilDate().toString());
        }
        return list;
    }

    @RequestMapping("/attend.do")
    @ResponseBody
    public int attend(Attendance attendance){
        if(sheetService.checkExist(attendance.getClassName(), attendance.getStuNum()) > 0){
            System.out.println("FAIL!!!!!!!!!");
            return 0;
        }
        if(attendance.getCode().equals(sheetService.getCode(attendance.getClassName()))){
            attendance.setDate(new java.sql.Date(new Date().getTime()));
            sheetService.attend(attendance);
            return 1;
        }
        return 0;
    }
}
