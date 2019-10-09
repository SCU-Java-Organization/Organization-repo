import java.util.Set;

public class Xuser {
    private String userName;
    private String password;
    private double balance;

    public Xuser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addMoney(double money){
        this.balance+=money;
    }

    public void takeMoney(double money){
        this.balance-=money;
    }
}