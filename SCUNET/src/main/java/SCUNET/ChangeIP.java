package SCUNET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ShaoJiale
 * @create 2019/10/31
 * @function
 */
public class ChangeIP {
    private static List<String> ipPool = new LinkedList<>();
    private static String netGate = "";
    public static String networkName;
    /**
     * @function 从连接池中更换IP进行连接的过程入口
     */
    public static void changeIP(String userId, String password){
        System.out.println("*************************");
        System.out.println("*     选择所在的地区    *");
        System.out.println("*      1.第一教学楼     *");
        System.out.println("*      2.江安图书馆     *");
        System.out.println("*      3.综合楼         *");
        System.out.println("*      4.二基楼         *");
        System.out.println("*************************");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        input.nextLine();

        switch (choice){
            case 1:
                initPool(1);
                break;
            case 2:
                initPool(2);
                break;
            case 3:
                initPool(3);
                break;
            case 4:
                initPool(4);
        }

        System.out.print("输入你的网络适配器名称：");
        ChangeIP.networkName = input.nextLine();
        Random random = new Random();
        while(true){
            String ip = ipPool.get(random.nextInt(ipPool.size()));
            try {
                reconnect(ip);
                // 等待IP分配完成
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e){
                System.err.println("程序无法执行Windows命令，请重试");
                return;
            }
            if(isConnected(userId, password)) {
                return;
            }
        }
    }

    /**
     * @function 尝试使用当前静态配置的IP进行连接
     * @param ip 当前配置的静态IP
     * @throws Exception
     */
    private static void reconnect(String ip) throws Exception{
        Runtime runtime = Runtime.getRuntime();
        Process process;
        System.out.println("netsh interface ip set address \"" + networkName + "\" " +
                "static " + ip + " " +
                "255.255.255.0 " +
                netGate);
        try {
            process = runtime.exec("netsh interface ip set address \"" + networkName + "\" " +
                    "static " + ip + " " +
                    "255.255.255.0 " +
                    netGate);

            BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            StringBuffer sb = new StringBuffer();

            while((line = bfr.readLine()) != null)
                sb.append(line);

            bfr.close();
            System.err.println(sb);
        } catch (Exception e){
            throw e;
        }
    }

    /**
     * 测试是否连接成功
     * @return
     */
    private static boolean isConnected(String userId, String password){
        try {
            ConnectNetwork.login(userId, password);
        } catch (IOException e){
            return false;
        }
        return true;
    }

    /**
     * @function 测试是否联网, 暂时无用
     */
    private static void isConnected(){
        Runtime runtime = Runtime.getRuntime();
        Process process;

        try {
            process = runtime.exec("ping " + "www.baidu.com");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            StringBuffer sb = new StringBuffer();

            while((line = bfr.readLine()) != null)
                sb.append(line);

            bfr.close();
            if (null != sb && !sb.toString().equals("")) {
                if (sb.toString().indexOf("TTL") > 0)
                    System.out.println("网络畅通");
                else
                    System.out.println("网络不畅");
            }

        } catch (IOException e){
            System.err.println("无法执行windows指令！");
            e.printStackTrace();
        }
    }

    /**
     * @function 工具类,用于初始化 IP 池
     * 地点       网关              IP范围
     * 一教      10.132.15.254      10.132.0.2 - 10.132.15.253
     * 图书馆    10.132.31.254      10.132.28.1 - 10.132.31.253
     * 综合楼    10.132.39.254	    10.132.36.1 - 10.132.39.253
     * 二基楼    10.132.51.254      10.132.48.1 - 10.132.51.253
     */
    private static void initPool(int location){
        ipPool.clear();
        switch (location){
            case 1:
                netGate = "10.132.15.254";
                for(int i = 0; i <= 15; i++){
                    for(int j = 2; j <=  253; j++){
                        ipPool.add("10.132." + i + "." + j);
                    }
                }
                break;
            case 2:
                netGate = "10.132.31.254";
                for(int i = 28; i <= 31; i++){
                    for(int j = 1; j <=  253; j++){
                        ipPool.add("10.132." + i + "." + j);
                    }
                }
                break;
            case 3:
                netGate = "10.132.39.254";
                for(int i = 36; i <= 39; i++){
                    for(int j = 1; j <=  253; j++){
                        ipPool.add("10.132." + i + "." + j);
                    }
                }
                break;
            case 4:
                netGate = "10.132.51.254";
                for(int i = 48; i <= 51; i++){
                    for(int j = 1; j <=  253; j++){
                        ipPool.add("10.132." + i + "." + j);
                    }
                }
                break;
        }
    }

    /**
     * @function 获取本机用于连接 SCUNET 的网络接口,暂时无用
     * @return networkInterface
     */
    public static void getNetworkInterface(){
        try {
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();

            // 遍历网络接口
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                // 遍历接口中的IP地址
                while(address.hasMoreElements()){
                    InetAddress nextElement = address.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    if (hostAddress.substring(0, 2).equals("10")) {
                        System.err.println("找到本机用于连接校园网的网络接口为：" + ni.getDisplayName());
                        System.err.println("当前你的校园网IP地址是：" + hostAddress);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("程序无法确认你本机的网络接口，请使用普通连接！");
    }

}
