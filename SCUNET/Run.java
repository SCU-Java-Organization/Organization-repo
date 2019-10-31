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
    public static void normalLogin() throws Exception{
        while (!ConnectNetwork.loginStatus) {
            try {
                login();
            } catch (Exception e) {
                //System.out.println("main catch exception");
                System.err.println("*********************");
                System.err.println("尝试重连...");
                System.out.println();
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
    
    public static void tryLogout(){
        try {
            logout();
        } catch (RuntimeException e){
            System.out.println("下线失败");
            //System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        Runtime runtime = Runtime.getRuntime();
        Process process;
        // Fixme 未实现无限循环连接，大家有兴趣可以自己更改main函数
        System.out.println("**************************");
        System.out.println("*    1.普通连接          *");
        System.out.println("*    2.断开连接          *");
        System.out.println("*    3.查看本机IP地址    *");
        System.out.println("*    4.IP池连接          *");
        System.out.println("*    5.重置为DCHP协议    *");
        System.out.println("*                        *");
        System.out.println("*        Tips!           *");
        System.out.println("*  选项4的响应速度不理想 *");
        System.out.println("*   所以并不推荐你使用   *");
        System.out.println("*   建议每次都先重置DCHP *");
        System.out.println("*  SCU-Java程序设计协会  *");
        System.out.println("**************************");

        int choice = in.nextInt();
        switch (choice){
            case 1:
                normalLogin();
                break;
            case 2:
                tryLogout();
                break;
            case 3:
                ChangeIP.getNetworkInterface();
                break;
            case 4:
                ChangeIP.changeIP();
                break;
            case 5:
                System.out.println();
                System.out.println("****************************");
                System.out.println("*本选项需要你拥有管理员权限*");
                System.out.println("****************************");
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
                default:
                    System.out.println("输入有误!");
        }
    }
}
