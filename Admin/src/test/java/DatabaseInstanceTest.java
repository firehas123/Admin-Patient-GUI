import com.admin.dao.DatabaseInstance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.sql.ResultSet;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseInstanceTest {
    private final DatabaseInstance databaseInstance = DatabaseInstance.getInstance();

    DatabaseInstanceTest() throws SQLException {
    }

    @BeforeAll
    static void setup() throws SQLException {
        DatabaseInstance databaseInstance = DatabaseInstance.getInstance();
        databaseInstance.registerUser("testUser", "testPassword");
        databaseInstance.createDoctor("newDoctor", "1234567890", "background");
        databaseInstance.insertBooking("newDoctor", "2024-03-19", "testUser");
    }

    @Test
    void getInstance() throws SQLException {
        DatabaseInstance instance1 = DatabaseInstance.getInstance();
        DatabaseInstance instance2 = DatabaseInstance.getInstance();
        assertEquals(instance1, instance2);
    }

    @Test
    void authenticateUser() throws SQLException {
        boolean result = this.databaseInstance.authenticateUser("testUser", "testPassword");
        assertTrue(result);
    }

    @Test
    void checkIfExists() throws SQLException {
        boolean result = this.databaseInstance.checkIfExists("testUser");
        assertTrue(result);
    }

    @Test
    void fetchAllDoctor() throws SQLException {
        ResultSet resultSet = this.databaseInstance.fetchAllDoctor();
        assertNotNull(resultSet);
    }

    @Test
    void checkIfDoctorAvailable() throws SQLException {
        boolean result = this.databaseInstance.checkIfDoctorAvailable("newDoctor", "2024-03-20");
        assertTrue(result);
    }

    @Test
    void fetchAllPatients() throws SQLException {
        ResultSet resultSet = this.databaseInstance.fetchAllPatients();
        assertNotNull(resultSet);
    }

    @Test
    void fetchBookingsByDoctor() throws SQLException {
        ResultSet resultSet = this.databaseInstance.fetchBookingsByDoctor("newDoctor");
        assertNotNull(resultSet);
    }

    @Test
    void fetchBookingsByPatient() throws SQLException {
        ResultSet resultSet = this.databaseInstance.fetchBookingsByPatient("testUser");
        assertNotNull(resultSet);
    }

    @Test
    void fetchBookingsByMonthYear() throws SQLException {
        ResultSet resultSet = this.databaseInstance.fetchBookingsByMonthYear("03", "2024");
        assertNotNull(resultSet);
    }

    @Test
    void updateBooking() throws SQLException {
        boolean result = this.databaseInstance.updateBooking("2024-03-25", "testUser", "newDoctor", "2024-03-19");
        assertTrue(result);
    }

    @Test
    void removeBooking() throws SQLException {
        boolean result = this.databaseInstance.removeBooking("testUser", "newDoctor", "2024-03-25");
        assertTrue(result);
    }

    @AfterAll
    public static void deleteAllData() throws SQLException {
        executeUpdate("delete from users where username = 'testUser' and password = 'testPassword'");
        executeUpdate("delete from doctor where name = 'newDoctor';");
        executeUpdate("delete from booking where doctor = 'newDoctor' and date = '2024-03-19' and patient = 'testUser';");
    }

    private static void executeUpdate(String deleteQuery) throws SQLException{
        DatabaseInstance databaseInstance = DatabaseInstance.getInstance();
        databaseInstance.cleanUpTestData(deleteQuery);
    }
}