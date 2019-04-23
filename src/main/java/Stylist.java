import org.sql2o.Connection;

import java.util.List;

public class Stylist {
    private String name;
    private int phone;
    private int id;

    public Stylist (String name,int phone ){
        this.name =name;
        this.phone =phone;
    }

    public String getName() {

        return name;
    }

    public int getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }
    public static List<Stylist> all() {
        String sql = "SELECT id, name , phone FROM stylists";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
    }
    @Override
    public boolean equals(Object otherStylist){
        if (!(otherStylist instanceof Stylist)) {
            return false;
        }else{
            Stylist newStylist =(Stylist) otherStylist;
            return this.getName().equals(newStylist.getName()) &&
                    this.getPhone() == newStylist.getPhone() &&
                    this.getId() == newStylist.getId();
        }
    }
    public static Stylist find(int id){
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM stylists where id=:id";
            Stylist stylist = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Stylist.class);
            return stylist;
        }
    }
}
