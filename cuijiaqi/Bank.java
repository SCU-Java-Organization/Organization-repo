import java.util.Scanner;

public class Bank {
    Person[] person = new Person[1000];
    int log_in=0;

    public void add_accout() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("0:exit  1：open an account");
            int n = input.nextInt();
            if (n == 0) break;
            else {
                System.out.println("Please enter your ID:");
                String temp = input.nextLine();
                String id = input.nextLine();
                Person person1=new Person();
                person1.setID(id);
                person1.setMoney(0);
                person[log_in] = person1;
                log_in++;
            }
        }
    }

    public void cash_in(String id,double money){
        for(int i=0;i<log_in;i++){
            if(person[i].getID().equals(id)){
                person[i].setMoney(person[i].getMoney()+money);
                System.out.println("Your current money is: "+person[i].getMoney());
                break;
            }
        }
    }
    public void cash_out(String id,double money){
        for(int i=0;i<log_in;i++){
            if(person[i].getID().equals(id)){
                person[i].setMoney(person[i].getMoney()-money);
                System.out.println("Your current money is: "+person[i].getMoney());
                break;
            }
        }
    }
    public void transfer_money(String id1,String id2,double money) {
        for (int i = 0; i < log_in; i++) {
            if (person[i].getID().equals(id1)) {
                person[i].setMoney(person[i].getMoney() - money);
                if (person[i].getID().equals(id2)) {
                    person[i].setMoney(person[i].getMoney() + money);
                    System.out.println("Your current money is: " + person[i].getMoney());
                }
            }

        }
    }
}
