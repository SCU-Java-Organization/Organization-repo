package ZOO;

import java.util.Scanner;

public class Search {
    public static void main(String[] args) {
        Zoo infomation=new Zoo();
        Scanner input=new Scanner(System.in);
        System.out.println("Input the name of the zoo");
        infomation.setName(input.next());
        System.out.println("Input the location of the zoo");
        infomation.setLocation(input.next());
        System.out.println("Input the tickerPrice");
        infomation.setTicketPrice(input.next());
        System.out.println("Input the opentime");
        infomation.setOpenTime(input.next());
        System.out.println("Input the number of animal kinds");
        int kinds=input.nextInt();
        String animalkinds[]=new String[kinds];
        for(int i=0;i<kinds;i++)
        {
            animalkinds[i]=input.next();
        }
        infomation.setAnimalKinds(animalkinds);
        System.out.println("Choose an info you want to konw");
        System.out.println("1.The name of the zoo");
        System.out.println("2.The location of the zoo");
        System.out.println("3.The ticketprice of the zoo");
        System.out.println("4.The opentime of the zoo");
        System.out.println("5.The animals kinds");
        int choose=input.nextInt();
        while(true){
        switch(choose){
            case 1:
                System.out.println(infomation.getName());
                choose=input.nextInt();
                break;
            case 2:
                System.out.println(infomation.getLocation());
                choose=input.nextInt();
                break;
            case 3:
                System.out.println(infomation.getTicketPrice());
                choose=input.nextInt();
                break;
            case 4:
                System.out.println(infomation.getOpenTime());
                choose=input.nextInt();
                break;
            case 5:
                infomation.showAnimalKinds();
                choose=input.nextInt();
                break;
                default:break;
        }
    }
    }
}
