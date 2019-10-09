import com.company.Account;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bank {
    private String name;
    protected Double interest1 = 1.75 / 100;
    protected Double interest2 = 2.35 / 100;
    protected Double interest3 = 3.00 / 100;
    private ArrayList<Account> list = new ArrayList<Account>();

    public Bank(String name,ArrayList<Account> list){
        this.name = name;
        this.list = list;
    }

    public String getName() { return name; }
    public ArrayList<Account> getList(){ return list; }
    public Double getInterest1(){ return interest1; }
    public Double getInterest2(){ return interest2; }
    public Double getInterest3(){ return interest3; }

    public void setInterest(Double interest1,Double interest2,Double interest3){
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
    }
    public void createFile(String filename) throws IOException{
        File file=new File("D:\\"+filename+ ".txt");
        if(!file.exists())
            file.createNewFile();
    }
    public void setList(ArrayList list){
        this.list = list;
    }

}
