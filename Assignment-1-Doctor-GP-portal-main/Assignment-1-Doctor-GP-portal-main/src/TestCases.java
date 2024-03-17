import static org.junit.Assert.*;
import org.junit.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestCases {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private Connection conn;

    @Before
    public void setUp() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Test
    public void testFetchBookingsByDoctor() throws SQLException {
        String username = "testDoctor";
        String month = "01";
        String year = "2022";

        // Insert test data into the database
        insertTestData("INSERT INTO booking (doctor, date, patient) VALUES (?, '2022-01-01', 'testPatient')", username);

        // Create the database instance and execute the method
        DatabaseInstance databaseInstance = DatabaseInstance.getInstance();
        ResultSet resultSet = databaseInstance.fetchBookingsByDoctor(username, month, year);

        // Check if the result set is not null
        assertNotNull(resultSet);

        // Check if the result set contains the expected data
        assertTrue(resultSet.next());

        // Clean up the test data from the database
        cleanUpTestData(username);
    }

    @Test
    public void testCheckIfDoctorExists() throws SQLException {
        String username = "existingDoctor";

        // Insert test data into the database
        insertTestData("INSERT INTO doctor (name) VALUES (?)", username);

        // Create the database instance and execute the method
        DatabaseInstance databaseInstance = DatabaseInstance.getInstance();
        boolean doctorExists = databaseInstance.checkIfDoctorExists(username);

        // Check if the doctor exists
        assertTrue(doctorExists);

        // Clean up the test data from the database
        cleanUpTestData(username);
    }

    private void insertTestData(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setString(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
    }

    private void cleanUpTestData(String username) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM booking WHERE doctor = ?");
        preparedStatement.setString(1, username);
        preparedStatement.executeUpdate();
    }

    @After
    public void tearDown() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
