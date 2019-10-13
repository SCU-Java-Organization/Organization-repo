package bank;
import java.util.Scanner;
public class bank {
    Scanner sc = new Scanner(System.in);
    private person[] array = new person[100];
    int account;


    public int register(int a) {

        System.out.println("请注册");
        person one = new person();
        one.setAccount(a);
        System.out.println("账号：" + one.getAccount());
        System.out.println("请输入姓名：");
        one.setName(sc.next());
        System.out.println("请输入密码");
        one.setPassword(sc.next());

        System.out.println("注册成功！");
        array[a - 1] = one;//将one地址给array的第i项
        return a + 1;
    }

    public int login(int i) {

        System.out.println("账号：");
        account = sc.nextInt();
        System.out.println("密码：");
        String password = sc.next();
        if (account >= i) {
            System.out.println("该用户不存在！");
        } else if (password.equals(array[account - 1].getPassword())) {
            System.out.println(array[account - 1].getName() + "登陆成功！");
        }
        return account;
    }

    public void check1(int a){

        array[a-1].check();
    }

    public void save(int a){

        System.out.println("输入存款金额：");
        array[a-1].save(sc.nextInt());
    }

    public void withdraw(int a){
        System.out.println("输入取款金额：");
        array[a-1].withdraw(sc.nextInt());
    }

    public void trans(int a,int b){
        System.out.println("转账账号：");
        int c=sc.nextInt();
        System.out.println("转账金额：");
        int num=sc.nextInt();
        if (c>=b)
            System.out.println("该用户不存在！");
        else {
            if (array[a - 1].getMoney() < num)
                System.out.println("余额不足");
            else {
                array[c - 1].trans(num);
                array[a - 1].trans(-num) ;
                System.out.println("转账成功！");
            }
        }
    }

}
