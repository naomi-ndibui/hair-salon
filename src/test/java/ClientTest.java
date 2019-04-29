
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {
    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "ndibui", "qwerty");
    }

    @After
    public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
            String deleteClientsQuery = "DELETE FROM clients *;";
            String deleteStylistsQuery = "DELETE FROM stylists *;";
            con.createQuery(deleteClientsQuery).executeUpdate();
            con.createQuery(deleteStylistsQuery).executeUpdate();
        }
    }
    @Test
    public void Client_instantiatesCorrectly_true() {
        Client myClient = new Client("Naomi", 1);
        assertEquals(true, myClient instanceof Client);
    }
    @Test
    public void Client_instantiatesWithDescription_String() {
        Client myClient = new Client("Naomi", 1);
        assertEquals("Naomi", myClient.getName());
    }
    @Test
    public void all_returnsAllInstancesOfClient_true() {
        Client firstClient = new Client("Naomi", 1);
        firstClient.save();
        Client secondClient = new Client("Buy groceries", 1);
        secondClient.save();
        assertEquals(true, Client.all().get(0).equals(firstClient));
        assertEquals(true, Client.all().get(1).equals(secondClient));
    }

    @Test
    public void find_returnsClientWithSameId_secondClient() {
        Client firstClient = new Client("Naomi", 1);
        firstClient.save();
        Client secondClient = new Client("Buy groceries", 1);
        secondClient.save();
        assertEquals(Client.find(secondClient.getId()), secondClient);
    }

    @Test
    public void equals_returnsTrueIfDescriptionsAretheSame() {
        Client firstClient = new Client("Naomi", 1);
        Client secondClient = new Client("Naomi", 1);
        assertTrue(firstClient.equals(secondClient));
    }

    @Test
    public void save_returnsTrueIfDescriptionsAretheSame() {
        Client myClient = new Client("Naomi", 1);
        myClient.save();
        assertTrue(Client.all().get(0).equals(myClient));
    }

    @Test
    public void save_assignsIdToObject() {
        Client myClient = new Client("Naomi", 1);
        myClient.save();
        Client savedClient = Client.all().get(0);
        assertEquals(myClient.getId(), savedClient.getId());
    }

    @Test
    public void newClient_getsStylistId_1()
    {
        Client myClient = new Client("Naomi", 1);
        assertEquals(1, myClient.getStylistId());
    }
    @Test
    public void getId_ClientsInstantiateWithAnID() {
        Client myClient = new Client("Naomi", 1);
        myClient.save();
        assertTrue(myClient.getId() > 0);
    }
    @Test
    public void save_savesStylistIdIntoDB_true() {
        Stylist myStylist = new Stylist("Naomi", 073567);
        myStylist.save();
        Client myClient = new Client("Naomi", myStylist.getId());
        myClient.save();
        Client savedClient = Client.find(myClient.getId());
        assertTrue(savedClient.getId() > 0);
    }
    @Test
    public void delete_deletesClient_true() {
        Client myClient = new Client("Naomi", 1);
        myClient.save();
        int myClientId = myClient.getId();
        myClient.delete();
        assertEquals(null, Client.find(myClientId));
    }
    @Test
    public void update_updatesClient_true() {
        Client newClient = new Client("Naomi", 1);
        newClient.save();
        newClient.update("Naomi");
        assertEquals("Naomi", Client.find(newClient.getId()).getName());
    }

}
