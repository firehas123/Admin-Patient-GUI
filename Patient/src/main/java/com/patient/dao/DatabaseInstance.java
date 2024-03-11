package com.patient.dao;

import java.sql.*;
import java.util.Arrays;

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

    private int executeUpdate(String sql, String... params) throws SQLException {
        PreparedStatement preparedStatement = generateQuery(sql, params);
        return preparedStatement.executeUpdate();
    }

    public boolean authenticateUser(String username, String password) throws SQLException{
        return executeQuery(DatabaseConstants.authQuery, username, password).next();
    }

    public boolean checkIfExists(String username) throws SQLException{
        return executeQuery(DatabaseConstants.checkUserExists, username).next();
    }

    public boolean registerUser(String username, String password) throws SQLException{
            return executeUpdate(DatabaseConstants.registerQuery, username, password) > 0;
    }

    public ResultSet fetchAllDoctor() throws SQLException {
        return executeQuery(DatabaseConstants.getAllDoctorQuery);
    }

    public int insertBooking(String doctor, String date, String username) throws SQLException {
        return executeUpdate(DatabaseConstants.insertBookingQuery,doctor,date, username);
    }

    public boolean checkIfDoctorAvailable(String doctor, String date) throws SQLException {
        return !(executeQuery(DatabaseConstants.checkDoctorAvailableQuery,doctor,date).next());
    }
}

/*
// Close the connection
                if (databaseInstance.conn != null) {
                    try {
                        databaseInstance.conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
 */
