package SCUNET;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author ShaoJiale
 * @create 2019/10/29
 * @function 登录校园网
 */
public class ConnectNetwork {
    public static boolean loginStatus = false;
    /**
     * @function 利用Jsoup分析未登录时返回的HTML
     * @return 利用正则表达式提取的queryString参数
     */
    public static String getLoginPath() throws IOException{
        String href = null;
        try {
            Document document = Jsoup.connect("http://192.168.2.135").get();
            if(document.title().equals("登录成功")){
                System.err.println("你已经登录");
                return "logged in";
            }
            href = document.head().data();
            href = href.split("\\?")[1];
            href = href.split("\'")[0];
        } catch (IOException e){
            System.err.println("连接到URL失败，等待重新分配IP");
            throw e;
        }

        return href;
    }

    /**
     * @function 向目标地址进行post请求
     * @throws Exception
     */
    public static void login(String userId, String password) throws IOException{
        String url = "http://192.168.2.135/eportal/InterFace.do?method=login";
        String href = "";

        /**
         * @function 解析登录的必要参数queryString
         * @Exception 解析失败时catch getLoginPath()中抛出的异常，再抛出到main函数中
         */
        try {
            href = getLoginPath();
        } catch (IOException e){
            System.err.println("Login caught exception...");
            throw e;
        }
        if (href.equals("logged in")) {
            loginStatus = true;
            return;
        }
        System.out.println("连接URL成功...");
        Param param = new Param(userId, password, href);
        try {
            /**
             * @function 添加post参数
             * @Exception 使用URIBuilder包装post请求可能产生异常，这里我们不作处理
             */
            URIBuilder builder = new URIBuilder(url);
            builder.addParameter("method", "login");
            builder.addParameter("userId", param.getUserId());
            builder.addParameter("password", param.getPassword());
            builder.addParameter("service", param.getService());
            builder.addParameter("queryString", param.getQueryString());
            builder.addParameter("operatorPwd", param.getOperatorPwd());
            builder.addParameter("operatorUserId", param.getOperatorUserId());
            builder.addParameter("validcode", param.getValidcode());
            builder.addParameter("passwordEncrypt", param.getPasswordEncrypt());

            HttpPost httpPost = new HttpPost(builder.build());

            /**
             * @function 尝试通过HttpClient发送POST请求
             * @Exception 发送请求可能产生异常，这里我们不作处理
             */
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
                HttpEntity responseEntity = response.getEntity();
                if(responseEntity != null){
                    JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity, "UTF-8"));
                    String connectloginStatus = (String)jsonObject.get("result");
                    if (connectloginStatus.equals("success")) {
                        System.err.println("登录成功");
                        loginStatus = true;
                    }
                    else {
                        System.err.println("连接失败！");
                        System.err.println(jsonObject.get("message"));
                        if(!jsonObject.get("message").equals("")){
                            System.exit(0);
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * @function 获取下线需要的POST参数
     * @return userIndex
     */
    public static String getLogoutMsg(){
        String url = "http://192.168.2.135/eportal/InterFace.do?method=getOnlineUserInfo";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        String message = "";
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if(responseEntity != null){
                JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity, "UTF-8"));
                String result = (String) jsonObject.get("result");
                message = (String) jsonObject.get("message");

                if(result.equals("fail")) {
                    message = (String) jsonObject.get("message");
                }
                String userIndex = (String)jsonObject.get("userIndex");
                if(userIndex != null)
                    return userIndex;
            }
        } catch (Exception e){
            System.err.println("下线失败");
            //e.printStackTrace();
        }
        throw new RuntimeException(message);
    }

    /**
     * @function 登出
     */
    public static void logout(){
        String url = "http://192.168.2.135/eportal/InterFace.do?method=logout";
        try {
            URIBuilder builder = new URIBuilder(url);
            try {
                builder.addParameter("userIndex", getLogoutMsg());
            } catch (RuntimeException e){
                System.err.println("logout catch runtime exception! " + e.getMessage());
                return;
            }

            HttpPost httpPost = new HttpPost(builder.build());
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = null;

            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if(responseEntity != null) {
                JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(responseEntity, "UTF-8"));
                //System.out.println(jsonObject);
                String logoutStatus = (String) jsonObject.get("message");
                if (logoutStatus.equals("下线成功！")) {
                    System.err.println("下线成功！");
                }
                else {
                    System.err.println("下线失败！");
                    System.err.println(logoutStatus);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
