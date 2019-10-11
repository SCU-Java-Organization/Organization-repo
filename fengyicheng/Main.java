import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("Xbank.txt");

        if (file.exists()) {  //如果文件存在
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ArrayList<Xuser> xusers = (ArrayList<Xuser>) ois.readObject();  //读取文件
            ois.close();
            Xbank xbank = new Xbank();
            xbank.setXusers(xusers);  //把用户信息同步进xbank
        } else {   //不存在就新建一个  并存入一个空的结构
            file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(Xbank.getXusers());
            oos.close();
        }
        new Login();
    }
}