package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public BookingFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Arrange Booking");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JLabel patientLabel = new JLabel("Select Patient:");
        panel.add(patientLabel);

        JComboBox<String> patientComboBox = new JComboBox<>();
        panel.add(patientComboBox);

        JLabel doctorLabel = new JLabel("Enter Doctor's Name:");
        panel.add(doctorLabel);

        JTextField doctorField = new JTextField();
        panel.add(doctorField);

        JLabel dateLabel = new JLabel("Select Date:");
        panel.add(dateLabel);

        JTextField dateField = new JTextField();
        panel.add(dateField);

        JButton confirmBookingButton = new JButton("Confirm Booking");
        confirmBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = (String) patientComboBox.getSelectedItem();
                String selectedDoctor = doctorField.getText();
                String selectedDate = dateField.getText();
                if (selectedPatient != null && !selectedDoctor.isEmpty() && !selectedDate.isEmpty()) {
                    try {
                        if (arrangeBooking(selectedPatient, selectedDoctor, selectedDate)) {
                            JOptionPane.showMessageDialog(BookingFrame.this, "Booking confirmed.");
                        } else {
                            JOptionPane.showMessageDialog(BookingFrame.this, "Doctor not available for selected date.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(BookingFrame.this, "Please fill in all fields.");
                }
            }
        });
        panel.add(confirmBookingButton);

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

        populatePatientComboBox(patientComboBox);
    }

    private void populatePatientComboBox(JComboBox<String> patientComboBox) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchAllPatients();
        while (resultSet.next()) {
            String patientName = resultSet.getString("username");
            System.out.println(patientName);
            patientComboBox.addItem(patientName);
        }
    }

    private boolean arrangeBooking(String patientName, String doctorName, String date) throws SQLException {
        // Check if the doctor is available for the chosen date
        if (databaseInstance.checkIfDoctorAvailable(doctorName, date)) {
            // Perform booking and send confirmation messages
            return databaseInstance.insertBooking(doctorName, date, patientName);
        }
        return false;
    }
}
