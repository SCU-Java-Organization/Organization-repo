import java.util.ArrayList;

public class Xbank {
    private static ArrayList<Xuser> xusers=new ArrayList<>();

    public static void add(Xuser xuser){

        xusers.add(xuser);
    }

    public static Xuser getXuser(String userName) {
        for(int i=0;i<xusers.size();i++)
        {
            Xuser xuser=xusers.get(i);
            if(xuser.getUserName().equals(userName))
                return xuser;
        }
        return null;
    }

    public  void setXusers(ArrayList<Xuser> xusers){
        this.xusers=xusers;
    }

    public static ArrayList<Xuser> getXusers(){
        return xusers;
    }
}