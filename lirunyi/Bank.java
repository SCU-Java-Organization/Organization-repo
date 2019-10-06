import java.util.Arrays;
import java.util.Scanner;
public class Bank {
    private  Card[] card;
    private int size;
    public Bank() {
        this(10);
    }
    public Bank(int size) {
        this.card = new Card[size];
        this.size = 0;
    }
    public void register(int Id, String Name) {
        for (int i = 0; i < this.size; i++) {
            if(card[i].getId() == Id) {
                System.out.println("您已经注册过了！");
                return;
            }
        }
        this.card[this.size++] = new Card(Id,Name);



    }
    public Card login(int Id, String Name)
        {
            for (int i = 0; i < this.card.length; i++) {
                if(card[i].getId() == Id && card[i].getName().equals(Name)) {
                    return card[i];
                }
            }
            System.out.println("登录失败");
            return null;

    }
    public void operateLoginSuccess(People people,int n,Card card){
        switch (n) {
            case 1:people.put(card);break;
            case 2:people.take(card);break;
            case 3:people.check(card);break;
            case 4:people.transfer(card,this.card,this.size); break;
            default:break;
        }
    }



    public void in_bank_operate(People people) {
        while (true)
            while(true) {
                System.out.println("欢迎");
                System.out.println("按1开户，按2登录，按0退出");
                Scanner scanner = new Scanner(System.in);
                int ans = scanner.nextInt();
                if(ans==0)return;
                System.out.println("请输入账号和姓名，中间用空格隔开：");
                int id = scanner.nextInt();
                String name = scanner.nextLine();
                switch (ans) {
                    case 1:
                        register(id, name);
                        break;


                    case 2: {
                        Card card = login(id, name);
                        if (card != null) {
                            System.out.println("登录成功");
                            while (true) {
                                System.out.println("按1存钱，按2取钱，按3查看余额，按4转账，按5退出");
                                int n = scanner.nextInt();
                                if(n==5)break;
                                operateLoginSuccess(people,n,card);
                            }
                        }
                        break;

                    }

                    }
                }
            }
}



