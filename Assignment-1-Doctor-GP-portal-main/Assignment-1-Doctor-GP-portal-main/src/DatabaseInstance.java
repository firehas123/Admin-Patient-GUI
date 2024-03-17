import java.sql.*;

public class DatabaseInstance {

    private static DatabaseInstance databaseInstance;

    private Connection conn;

    // private constructor
    private DatabaseInstance() {
    }

    public static DatabaseInstance getInstance() throws SQLException{
        if(databaseInstance==null){
            databaseInstance = new DatabaseInstance();
            databaseInstance.conn = null;
            try {
                // Connect to the MySQL database
                String url = "jdbc:mysql://localhost:3306/test";
                String user = "root";
                String password = "password"; // Use the password you set for MySQL root
                databaseInstance.conn = DriverManager.getConnection(url, user, password);
                return databaseInstance;
            } catch (SQLException e) {
                throw new SQLException("\"Connection failed. Error: \" "+ e.getMessage());
            }
        }
        else {
            return databaseInstance;
        }
    }

    private PreparedStatement generateQuery(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = null;
        if(params.length>0 || !sql.contains("?")){
            preparedStatement = conn.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                preparedStatement.setString(i+1,params[i]);
            }
        }else{
            throw new SQLException("No Params given for placeholders");
        }
        return preparedStatement;
    }

    private ResultSet executeQuery(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = generateQuery(sql, params);
        return preparedStatement.executeQuery();
    }

    public ResultSet fetchBookingsByDoctor(String username, String month, String year) throws SQLException {
        return executeQuery(DatabaseConstants.fetchBookingByDoctor,username, month, year);
    }

    public boolean checkIfDoctorExists(String username) throws SQLException{
        return executeQuery(DatabaseConstants.fetchDoctorByUsername,username).next();
    }
}