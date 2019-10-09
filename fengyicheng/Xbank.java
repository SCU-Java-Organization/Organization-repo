import java.util.ArrayList;

public class Xbank {
    private static ArrayList<Xuser> xusers=new ArrayList<>();

    public static void add(Xuser xuser){
        xusers.add(xuser);
    }

    public static Xuser getXusers(String userName) {
        for(int i=0;i<xusers.size();i++)
        {
            Xuser xuser=xusers.get(i);
            if(xuser.getUserName().equals(userName))
                return xuser;
        }
        return null;
    }
}