package com.pojo;

import java.sql.Date;

public class Attendance {
    private String className;
    private String stuNum;
    private String stuName;
    private Date date;
    private String utilDate;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUtilDate() {
        return utilDate;
    }

    public void setUtilDate(String utilDate) {
        this.utilDate = utilDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
