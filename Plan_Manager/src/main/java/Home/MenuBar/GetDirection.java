package Home.MenuBar;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GetDirection {
    public static void openURL() {
        String url = "http://106.52.33.76/?p=15";
        //判断是否支持Desktop扩展,如果支持则进行下一步
        if (Desktop.isDesktopSupported()){
            try {
                URI uri = new URI(url);
                Desktop desktop = Desktop.getDesktop(); //创建desktop对象
                //调用默认浏览器打开指定URL
                desktop.browse(uri);

            } catch (URISyntaxException e) {
                e.printStackTrace();

            } catch (IOException e) {
                //如果没有默认浏览器时，将引发下列异常
                e.printStackTrace();
            }
        }
    }
}
