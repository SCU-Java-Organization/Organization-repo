package com.dao;

import com.pojo.Attendance;
import com.pojo.Sheet;

import java.util.List;

public interface SheetMapper {
    public void insertSheet(Sheet sheet);

    public void attend(Attendance attendance);

    public String getCode(String className);

    public List<Attendance> getAttendanceByClass(String className);
}
