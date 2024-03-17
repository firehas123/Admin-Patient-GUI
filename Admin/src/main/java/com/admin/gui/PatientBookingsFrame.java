package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientBookingsFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public PatientBookingsFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("View Bookings by Patient");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel selectPatientLabel = new JLabel("Select Patient:");
        panel.add(selectPatientLabel);

        JComboBox<String> patientComboBox = new JComboBox<>();
        panel.add(patientComboBox);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = (String) patientComboBox.getSelectedItem();
                if (selectedPatient != null) {
                    try {
                        dispose();
                        new BookingsListFrame(username, selectedPatient, "patient");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(PatientBookingsFrame.this, "Please select a patient.");
                }
            }
        });
        panel.add(viewButton);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new ViewBookingsFrame(username);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(returnButton);

        add(panel);
        setVisible(true);

        populatePatientComboBox(patientComboBox);
    }

    private void populatePatientComboBox(JComboBox<String> patientComboBox) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchAllPatients();
        while (resultSet.next()) {
            String patientName = resultSet.getString("username");
            patientComboBox.addItem(patientName);
        }
    }
}
