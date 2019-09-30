package com.pojo;

import java.sql.Date;

public class Sheet {
    private String className;
    private String code;
    private Date date;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(java.util.Date utilDate) {
        this.date = new Date(utilDate.getTime());
    }
}
