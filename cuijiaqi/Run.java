import java.util.Scanner;

public class Run {
    public void run() {
        Bank bank = new Bank();
        while(true) {
            System.out.println("Enter 0:exit  1:open an account");
            Scanner input=new Scanner(System.in);
            int n=input.nextInt();
            if(n==0)  break;
            else  bank.add_accout();
        }
        while (true) {
            Scanner input=new Scanner(System.in);
            System.out.println("Please enter your ID:");
            String id = input.nextLine();
            System.out.println("Enter  0:exit   1:open an account   2:cash in   3:cash out   4:transfer money");
            int num = input.nextInt();
            switch (num) {
                case 0:
                    break;
                 case 1:
                     bank.add_accout();
                     break;
                 case 2:
                     System.out.println("Please enter the sum you want to cash in:");
                     double money_in = input.nextDouble();
                     bank.cash_in(id, money_in);
                     break;
                 case 3:
                     System.out.println("Please enter the sum you want to cash out:");
                     double money_out = input.nextDouble();
                     bank.cash_out(id, money_out);
                     break;
                 case 4:
                     System.out.println("Please enter id you want to transfer to:");
                     String temp=input.nextLine();
                     String id2 = input.nextLine();
                     System.out.println("Please enter the sum you want to transfer:");
                     double money_trans = input.nextDouble();
                     bank.transfer_money(id, id2, money_trans);
                     break;
                }
            }
        }
    }
