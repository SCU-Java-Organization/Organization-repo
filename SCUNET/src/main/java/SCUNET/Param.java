package SCUNET;

/**
 * @author ShaoJiale
 * @create 2019/10/29
 * @function 定义HttpPost参数
 */
public class Param {
    /**
     * @Param userId:填写你的学号
     * @Param password:填写你的密码
     */
    private final String userId;
    private final String password;
    private final String service = "internet";
    private final String queryString;
    private final String operatorPwd = "";
    private final String operatorUserId = "";
    private final String validcode = "";
    private final String passwordEncrypt = "false";

    public Param(String userId, String password, String queryString) {
        this.userId = userId;
        this.password = password;
        this.queryString = queryString;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getService() {
        return service;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getOperatorPwd() {
        return operatorPwd;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public String getValidcode() {
        return validcode;
    }

    public String getPasswordEncrypt() {
        return passwordEncrypt;
    }

    @Override
    public String toString() {
        return  "userId=" + userId + "&" +
                "password=" + password + "&" +
                "service=" + service + "&" +
                "queryString=" + queryString + "&" +
                "operatorPwd=" + operatorPwd + "&" +
                "operatorUserId=" + operatorUserId + "&" +
                "validcode=" + validcode + "&" +
                "passwordEncrypt=" + passwordEncrypt;
    }

}
