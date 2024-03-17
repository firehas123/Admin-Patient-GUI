import java.sql.SQLException;

//Authors: Rielle
public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseInstance databaseInstance = DatabaseInstance.getInstance();
        new LoginPanel();
    }
}