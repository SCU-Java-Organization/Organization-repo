package SCUNET;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static SCUNET.ConnectNetwork.login;
import static SCUNET.ConnectNetwork.logout;

/**
 * @author ShaoJiale
 * @create 2019/10/31
 * @function 测试
 */
public class Run {
    /**
     * @function 不断进行重连，适用于DCHP协议
     * @throws Exception
     */
    private static void normalLogin(String userId, String password) throws Exception{
        while (!ConnectNetwork.loginStatus) {
            try {
                login(userId, password);
            } catch (Exception e) {
                //System.out.println("main catch exception");
                System.err.println("*********************");
                System.err.println("尝试重连...");
                System.out.println();
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
    
    private static void tryLogout(){
        try {
            logout();
        } catch (RuntimeException e){
            System.out.println("下线失败");
            //System.out.println(e.getMessage());
        }
    }

    private static void showTips(){
        System.out.println("1.普通连接，使用失败重试的方式不断尝试连接网络");
        System.out.println("2.断开连接，使用Post请求尝试断开连接，但是有可能失败，此时需要手动重试");
        System.out.println("3.查看当前IP地址，用于查看在你的PC上使用什么网络接口来连接SCUNET，以及查看当前校园网IP");
        System.out.println("4,轮询IP池连接，通过netsh命令手动设置静态IP，并用此IP尝试连接");
        System.out.println("5.重置为DHCP协议，用于恢复使用功能4后造成的IP静态化，导致无法联网问题");
        System.out.println();
        System.out.println("如果程序提示你需要管理员权限，使用管理员打开CMD运行本程序即可");
        System.out.println("使用过功能4——轮询IP池连接之后，务必记得再不需要使用网络时重置为DHCP协议！");
        System.out.println("作者:ShaoJiale 组织:SCU-Java程序设计协会");
        System.out.println("联系方式：QQ-953188895");
    }

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        Runtime runtime = Runtime.getRuntime();
        Process process;

        System.out.println("如果不用于登录可以回车跳过输入");
        System.out.print("学号：");
        String userId = in.nextLine();
        System.out.print("密码：");
        String password = in.nextLine();

        System.out.println("***************************");
        System.out.println("*    1.普通连接           *");
        System.out.println("*    2.断开连接           *");
        System.out.println("*    3.查看当前IP地址     *");
        System.out.println("*    4.轮询IP池连接       *");
        System.out.println("*    5.重置为DHCP协议     *");
        System.out.println("*    6.查看使用帮助       *");
        System.out.println("***************************");
        System.out.println("*         Tips            *");
        System.out.println("*   首次使用请先查看帮助   *");
        System.out.println("*   SCU-Java程序设计协会   *");
        System.out.println("****************************");

        int choice = in.nextInt();
        switch (choice){
            case 1:
                normalLogin(userId, password);
                break;
            case 2:
                tryLogout();
                break;
            case 3:
                ChangeIP.getNetworkInterface();
                break;
            case 4:
                ChangeIP.changeIP(userId, password);
                break;
            case 5:
                System.out.println();
                System.out.println("*******************************************");
                System.out.println("*       本选项需要你拥有管理员权限          *");
                System.out.println("*      重置协议为DCHP后可能导致断网         *");
                System.out.println("*       需要重新使用'普通连接'连网          *");
                System.out.println("* 本功能仅用于恢复轮询IP池之后造成的IP静态化 *");
                System.out.println("********************************************");
                System.out.println();
                System.out.print("输入你的网络适配器名称：");
                in.nextLine();
                ChangeIP.networkName = in.nextLine();
                process = runtime.exec("netsh interface ip set address \"" + ChangeIP.networkName + "\" DHCP");

                BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
                String line = null;
                StringBuffer sb = new StringBuffer();

                while((line = bfr.readLine()) != null)
                    sb.append(line);

                bfr.close();
                System.err.println(sb);
                break;
            case 6:
                showTips();
                break;
                default:
                    System.out.println("输入有误!");
        }
    }
}
