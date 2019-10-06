public class Account{
    private String name;
    private Double balance;
    private Double interest;
    private String password;
    private Integer year;
    public Account(String name,Double balance,Integer year,String password){

        this.name = name;
        this.balance = balance;
        this.year = year;
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public Double getInterest(){
        return interest;
    }
    public Double getBalance(){
        return balance;
    }
    public String getPassword(){
        return password;
    }
    public Integer getYear(){
        return year;
    }
    public void add(Double addition){
        this.balance += addition;
    }
    public void subtract(Double subtraction){
        this.balance -= subtraction;
    }
    public double computeInterest(double interest1,double interest2,double interest3){
        double finalInterest = 0.0;
        while(true) {
            boolean isprime = false;
            switch (this.year.intValue()) {
                case 1:
                    finalInterest = interest1;
                    isprime = true;
                    break;
                case 2:
                    finalInterest = interest2;
                    isprime = true;
                    break;
                case 3:
                    finalInterest = interest3;
                    isprime = true;
                    break;
                default:
                    System.out.println("sorry we don't have this kind of service");
                    isprime = true;
                    break;
            }
            if(isprime)
                break;
        }
        return finalInterest;
    }
}
