package com.service;

import com.pojo.Attendance;
import com.pojo.Sheet;

import java.util.List;

public interface SheetService {
    public void insertSheet(Sheet sheet);

    public void attend(Attendance attendance);

    public List<Attendance> getAttendanceByClass(String className);

    public String getCode(String className);
}
