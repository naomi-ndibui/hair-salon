public class Client {
    private String name;
    private int stylistId;
    private int id;

    public  Client (String name, int stylistId){
        this.name = name;
        this.stylistId = stylistId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getStylistId() {
        return stylistId;
    }
}
