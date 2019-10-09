public class Card {
    int Id;
    String Name;
    double Balance;
    public Card (int Id,String Name)
    {
        this.Id = Id;
        this.Name = Name;
    }
    public int getId() {
        return Id;
    }
    public void setId(int Id) {
        this.Id = Id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public double getBalance() {
        return Balance;
    }
    public void setBalance(double Balance) {
        this.Balance = Balance;
    }


}
