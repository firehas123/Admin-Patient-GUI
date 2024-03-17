package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddDoctorFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public AddDoctorFrame(String username) throws SQLException {
        this.databaseInstance = DatabaseInstance.getInstance();
        setTitle("Add Doctor");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel);
        JTextField nameField = new JTextField();
        panel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField();
        panel.add(phoneField);

        JLabel backgroundLabel = new JLabel("Background:");
        panel.add(backgroundLabel);
        JTextField backgroundField = new JTextField();
        panel.add(backgroundField);

        JButton addButton = new JButton("Add Doctor");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();
                String background = backgroundField.getText();

                // Save doctor details to the database (implement this)
                try {
                    if(databaseInstance.createDoctor(name,phoneNumber,background)) {
                        JOptionPane.showMessageDialog(AddDoctorFrame.this, "Doctor added successfully.");
                        nameField.setText("");
                        phoneField.setText("");
                        backgroundField.setText("");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(addButton);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new MainMenuFrame(username);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(returnButton);

        add(panel);
        setVisible(true);
    }
}
