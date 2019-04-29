import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class StylistTest {

    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "ndibui", "qwerty");
    }

    @After
    public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
            String deleteClientsQuery = "DELETE FROM Clients *;";
            String deleteStylistsQuery = "DELETE FROM Stylists *;";
            con.createQuery(deleteClientsQuery).executeUpdate();
            con.createQuery(deleteStylistsQuery).executeUpdate();
        }
    }

    @Test
    public void Stylist_instantiatesCorrectly_true() {
        Stylist testStylist = new Stylist("Naomi",07);
        assertEquals(true, testStylist instanceof Stylist);
    }
    @Test
    public void getName_StylistInstantiatesWithName_Naomi() {
        Stylist testStylist = new Stylist("Naomi", 07);
        assertEquals("Naomi", testStylist.getName());
    }
    @Test
    public void getPhone_StylistInstantiatesWithPhone_07() {
        Stylist testStylist = new Stylist("Naomi", 07);
        assertEquals(07, testStylist.getPhone());
    }
    @Test
    public void all_returnsAllInstancesOfStylist_true() {
        Stylist firstStylist = new Stylist("Naomi", 07);
        firstStylist.save();
        Stylist secondStylist = new Stylist("Joy",07);
        secondStylist.save();
        assertEquals(true, Stylist.all().get(0).equals(firstStylist));
        assertEquals(true, Stylist.all().get(1).equals(secondStylist));
    }
    @Test
    public void getId_StylistsInstantiateWithAnId_1() {
        Stylist testStylist = new Stylist("Naomi", 07);
        testStylist.save();
        assertTrue(testStylist.getId() > 0);
    }
    @Test
    public void find_returnsNullWhenNoClientFound_null() {
        assertTrue(Stylist.find(999) == null);
    }
    @Test
    public void equals_returnsTrueIfNamesAretheSame() {
        Stylist firstStylist = new Stylist("Naomi", 07);
        Stylist secondStylist = new Stylist("Naomi", 07);
        assertTrue(firstStylist.equals(secondStylist));
    }

    @Test
    public void save_savesIntoDatabase_true() {
        Stylist myStylist = new Stylist("Naomi", 07);
        myStylist.save();
        assertTrue(Stylist.all().get(0).equals(myStylist));
    }
    @Test
    public void save_assignsIdToObject() {
        Stylist myStylist = new Stylist("Naomi", 07);
        myStylist.save();
        Stylist savedStylist = Stylist.all().get(0);
        assertEquals(myStylist.getId(), savedStylist.getId());
    }
    @Test
    public void find_returnsStylistWithSameId_secondStylist() {
        Stylist firstStylist = new Stylist("Naomi", 07);
        firstStylist.save();
        Stylist secondStylist = new Stylist("Joy", 07);
        secondStylist.save();
        assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
    }
    @Test
    public void delete_deletesStylist_true() {
        Stylist firstStylist = new Stylist("Naomi", 1);
        firstStylist.save();
        int newStylistId = firstStylist.getId();
        firstStylist.delete();
        assertEquals(null, Client.find(newStylistId));
    }
    @Test
    public void update_updatesStylist_true() {
        Stylist firstStyList = new Stylist("Naomi", 07);
        firstStyList.save();
        firstStyList.update("Naomi");
        assertEquals("Naomi", Stylist.find(firstStyList.getId()).getName());
    }
    @Test
    public void getClients_retrievesALlClientsFromDatabase_clientsList() {
        Stylist firstStyList = new Stylist("Naomi", 07);
        firstStyList.save();

        Client newClient = new Client("Naomi", firstStyList.getId());
        newClient.save();
        Client newClient2 = new Client("Joan", firstStyList.getId());
        newClient2.save();

        Client[] clients = new Client[]{newClient, newClient2};

        assertTrue(firstStyList.getClients().containsAll(Arrays.asList(clients)));
    }
    }
//    @Test
//    public void getClients_retrievesALlClientsFromDatabase_ClientsList() {
//        Stylist myStylist = new Stylist("Naomi", 07);
//        myStylist.save();
//        Client firstClient = new Client("Joy",myStylist.getId());
//        firstClient.save();
//        Client secondClient = new Client("Joan", myStylist.getId());
//        secondClient.save();
//        Client[] clients = new Client[] { firstClient, secondClient };
//        assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
//    }

