import org.jetbrains.annotations.NotNull;
import java.util.Scanner;
public class People {
    public void put(Card card) {
        System.out.println("请输入存钱金额");
        Scanner scanner = new Scanner(System.in);
        double put_money = scanner.nextDouble();
        card.setBalance(card.getBalance()+put_money);
    }
    public void take(Card card) {
        System.out.println("请输入取钱金额");
        Scanner scanner = new Scanner(System.in);
        double take_money = scanner.nextDouble();
        if (card.getBalance() >= take_money) {
            card.setBalance(card.getBalance() -take_money);
        } else {
            System.out.println("余额不足");
        }
    }

    public void transfer(Card srcCard,Card[] card,int size)
    {
        System.out.println("输入转账账号（注意，只需要输入数字！）");
        Scanner scanner = new Scanner(System.in);
        int Id=scanner.nextInt();
        //String Name=scanner.nextLine();
        for (int i = 0; i < size; i++) {
            if (card[i].getId() == Id && card[i].getId() != srcCard.getId()) {
                System.out.println("请输入转账金额");
                double transfer_money = scanner.nextDouble();
                if (transfer_money <= srcCard.getBalance()) {
                    card[i].setBalance(card[i].getBalance() + transfer_money);
                    srcCard.setBalance(srcCard.getBalance() - transfer_money);
                    return;
                } else {
                    System.out.println("余额不足");
                }
            }
        }


    }
    public void check(Card card)
    {
        System.out.println("余额是"+card.getBalance());
    }

}
