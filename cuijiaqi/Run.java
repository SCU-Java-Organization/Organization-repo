import java.util.Scanner;

public class Run {
    public void run() {
        Bank bank = new Bank();
        Scanner input = new Scanner(System.in);
        while (true) {
            bank.add_accout();
            System.out.println("Please enter your ID:");
            String id = input.nextLine();
            while (true) {
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
                        String id2 = input.nextLine();
                        System.out.println("Please enter the sum you want to transfer:");
                        double money_trans = input.nextDouble();
                        bank.transfer_money(id, id2, money_trans);
                        break;
                }
            }
        }
    }
}