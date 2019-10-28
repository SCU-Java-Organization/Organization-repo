package ZOO;
import java.util.Arrays;

public class Zoo {
    private String name;
    private String location;
    private String ticketPrice;
    private String openTime;
    private String[]animals={"Monkey","Lion","Dolphin","Koala","Zebra","etc..."};
    public void setName(String name){
       this.name=name;
    }
    public void setLocation(String location){
       this.location=location;
    }
    public void setTicketPrice(String ticketPrice){
        this.ticketPrice=ticketPrice;
    }
    public void setOpenTime(String openTime){
        this.openTime=openTime;
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public String getTicketPrice(){
        return ticketPrice;
    }
    public String getOpenTime(){
        return openTime;
    }
    public void showAnimalKinds(){
        System.out.println(Arrays.toString(animals));

    }

}