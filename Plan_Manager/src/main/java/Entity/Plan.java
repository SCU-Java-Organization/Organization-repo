package Entity;

import java.util.Date;

public class Plan {
    private int id;
    private String username;
    private String planname;
    private Date begin;
    private Date end;
    private int lastDays;
    private String status;
    private int dayOff;
    private String description;
    private int attendance;
    private String dailyStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDayOff() {
        return dayOff;
    }

    public void setDayOff(int dayOff) {
        this.dayOff = dayOff;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getDailyStatus(){
        return dailyStatus;
    }

    public void setDailyStatus(String dailyStatus){
        this.dailyStatus = dailyStatus;
    }

    public int getLastDays() {
        return lastDays;
    }

    public void setLastDays(int lastDays) {
        this.lastDays = lastDays;
    }

    public void info(){
        System.out.print(" id: " + id);
        System.out.print(" username: " + username);
        System.out.print(" planname: " + planname);
        System.out.print(" begin: " + begin);
        System.out.print(" end: " + end);
        System.out.print(" description: " + description);
        System.out.print(" dayOff: " + dayOff);
        System.out.print(" attendance: " + attendance + "天\n");
    }
}
