package SCUNET;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * @author ShaoJiale
 * @create 2019/10/29
 * @function 登录校园网
 */
public class ConnectNetwork {
    /**
     * @function 利用Jsoup分析未登录时返回的HTML
     * @return 利用正则表达式提取的queryString参数
     */
    private static String getLoginPath(){
        String href = "";
        try {
            Document document = Jsoup.connect("http://192.168.2.135").get();
            href = document.head().data();
            href = href.split("\\?")[1];
            href = href.split("\'")[0];
            System.err.println(href);
        } catch (IOException e){
            System.err.println("URL错误！");
        } finally {
            return href;
        }
    }

    /**
     * @function 向目标地址进行post请求
     * @throws Exception
     */
    private static void login() throws Exception{
        String url = "http://192.168.2.135/eportal/InterFace.do?method=login";
        String href = getLoginPath();

        Param param = new Param(href);

        /**
         * 添加post参数
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

        System.err.println(httpPost.getRequestLine());

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null)
                System.err.println(response.getStatusLine());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        // 登录成功会打印状态码为200
        // Fixme 未实现无限循环连接，大家有兴趣可以自己更改main函数
       login();
    }
}
