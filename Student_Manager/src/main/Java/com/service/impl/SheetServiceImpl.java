package com.service.impl;

import com.dao.SheetMapper;
import com.pojo.Attendance;
import com.pojo.Sheet;
import com.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetServiceImpl implements SheetService {
    @Autowired
    SheetMapper mapper;

    public void insertSheet(Sheet sheet) {
        mapper.insertSheet(sheet);
    }

    public void attend(Attendance attendance){
        mapper.attend(attendance);
    }

    public List<Attendance> getAttendanceByClass(String className){
        return mapper.getAttendanceByClass(className);
    }

    public String getCode(String className) {
        return mapper.getCode(className);
    }
}
