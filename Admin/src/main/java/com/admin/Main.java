package com.admin;

import com.admin.dao.DatabaseInstance;
import com.admin.gui.LoginFrame;

import java.sql.SQLException;

public class Main{
    public static void main(String[] args) {
        //loading database connection
        DatabaseInstance databaseInstance = null;
        try {
            databaseInstance = DatabaseInstance.getInstance();
        System.out.println("Connected to the MySQL database.");
        //loading the GUI
        new LoginFrame();
        System.out.println("GUI Initialized Successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
