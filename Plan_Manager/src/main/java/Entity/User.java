package Entity;

public class User {
    private int id;
    private String username;
    private String realname;
    private String password;
    private int plan_amount;
    private int complete_amount;
    private int fail_amount;
    private String remarks;

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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlan_amount() {
        return plan_amount;
    }

    public void setPlan_amount(int plan_amount) {
        this.plan_amount = plan_amount;
    }

    public int getComplete_amount() {
        return complete_amount;
    }

    public void setComplete_amount(int complete_amount) {
        this.complete_amount = complete_amount;
    }

    public int getFail_amount() {
        return fail_amount;
    }

    public void setFail_amount(int fail_amount) {
        this.fail_amount = fail_amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
