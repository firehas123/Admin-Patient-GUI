package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private DatabaseInstance databaseInstance;

    public LoginFrame() throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Patient Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        DatabaseInstance finalDatabaseInstance = databaseInstance;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // Call authentication method from DatabaseInstance
                // If authentication successful, close login frame and open main menu with username
                // Else, show error message
                try {
                    if (finalDatabaseInstance.checkIfExists(username)) {
                        if(finalDatabaseInstance.authenticateUser(username,password)) {
                            System.out.println("Admin: "+username+" has logged in");
                            dispose(); // Close login frame
                            new MainMenuFrame(username); // Open main menu frame
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Please register first", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        DatabaseInstance finalDatabaseInstance1 = databaseInstance;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                //checking if user already exists
                try {
                    if (!finalDatabaseInstance1.checkIfExists(username)) {
                        if(finalDatabaseInstance1.registerUser(username,password)) {
                            System.out.println("Admin: "+username+" has registered");
                            dispose(); // Close login frame
                            new MainMenuFrame(username); // Open main menu frame
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Please login", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }
}
