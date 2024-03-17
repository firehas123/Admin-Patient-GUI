package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NewPatientFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public NewPatientFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("New Patient Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel nameLabel = new JLabel("Patient Name:");
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        panel.add(nameField);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        panel.add(doctorLabel);

        JComboBox<String> doctorComboBox = new JComboBox<>();
        panel.add(doctorComboBox);

        JLabel dateLabel = new JLabel("Select Date:");
        panel.add(dateLabel);

        JTextField dateField = new JTextField();
        panel.add(dateField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientName = nameField.getText();
                String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                String selectedDate = dateField.getText();
                if (!patientName.isEmpty() && selectedDoctor != null && !selectedDate.isEmpty()) {
                    // Perform patient registration and doctor assignment
                    try {
                        if (registerPatient(patientName, selectedDoctor, selectedDate)) {
                            JOptionPane.showMessageDialog(NewPatientFrame.this, "Patient registered successfully.");
                            nameField.setText("");
                            doctorComboBox.setSelectedIndex(-1);
                        } else {
                            JOptionPane.showMessageDialog(NewPatientFrame.this, "Failed to register patient. Doctor not available.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(NewPatientFrame.this, "Please fill in all fields.");
                }
            }
        });
        panel.add(registerButton);

        JButton cancelButton = new JButton("Return");
        cancelButton.addActionListener(new ActionListener() {
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
        panel.add(cancelButton);

        add(panel);
        setVisible(true);

        populateDoctorComboBox(doctorComboBox);
    }

    private void populateDoctorComboBox(JComboBox<String> doctorComboBox) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchAllDoctor();
        while (resultSet.next()) {
            String doctorName = resultSet.getString("name");
            doctorComboBox.addItem(doctorName);
        }
    }

    private boolean registerPatient(String patientName, String selectedDoctor, String selectedDate) throws SQLException {
        if(databaseInstance.checkIfDoctorAvailable(selectedDoctor,selectedDate)) {
            //creating user for the new registered patient
            if(!databaseInstance.checkIfExists(patientName)) {
                databaseInstance.registerUser(patientName, "123");
            }
            return databaseInstance.insertBooking(selectedDoctor, selectedDate, patientName);

        }
        return false;
    }
}

