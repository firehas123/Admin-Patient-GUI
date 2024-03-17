package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RescheduleBookingFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public RescheduleBookingFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Reschedule or Remove Booking");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel patientLabel = new JLabel("Select Patient:");
        panel.add(patientLabel);

        JComboBox<String> patientComboBox = new JComboBox<>();
        panel.add(patientComboBox);

        JLabel doctorLabel = new JLabel("Select Doctor:");
        panel.add(doctorLabel);

        JComboBox<String> doctorComboBox = new JComboBox<>();
        panel.add(doctorComboBox);

        JLabel dateLabel = new JLabel("Enter New Date:");
        panel.add(dateLabel);

        JTextField dateField = new JTextField();
        panel.add(dateField);

        JLabel oldDateLabel = new JLabel("Select Old Date:");
        panel.add(oldDateLabel);

        JTextField oldDateField = new JTextField();
        panel.add(oldDateField);

        JButton rescheduleButton = new JButton("Reschedule");
        rescheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = (String) patientComboBox.getSelectedItem();
                String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                String selectedDate = dateField.getText();
                String oldDate = oldDateField.getText();
                if (selectedPatient != null && selectedDoctor != null && !selectedDate.isEmpty() && !oldDate.isEmpty()) {
                    try {
                        if (rescheduleBooking(selectedPatient, selectedDoctor, selectedDate, oldDate)) {
                            JOptionPane.showMessageDialog(RescheduleBookingFrame.this, "Booking rescheduled successfully.");
                        } else {
                            JOptionPane.showMessageDialog(RescheduleBookingFrame.this, "Failed to reschedule booking.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(RescheduleBookingFrame.this, "Please fill in all fields.");
                }
            }
        });
        panel.add(rescheduleButton);

        JButton removeButton = new JButton("Remove Booking");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = (String) patientComboBox.getSelectedItem();
                String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                String selectedDate = oldDateField.getText();
                if (selectedPatient != null && selectedDoctor != null && !selectedDate.isEmpty()) {
                    try {
                        if (removeBooking(selectedPatient, selectedDoctor, selectedDate)) {
                            JOptionPane.showMessageDialog(RescheduleBookingFrame.this, "Booking removed successfully.");
                        } else {
                            JOptionPane.showMessageDialog(RescheduleBookingFrame.this, "Failed to remove booking.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(RescheduleBookingFrame.this, "Please fill in all fields.");
                }
            }
        });
        panel.add(removeButton);

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
                // Handle cancel action, maybe return to previous frame
            }
        });
        panel.add(cancelButton);

        add(panel);
        setVisible(true);

        populatePatientComboBox(patientComboBox);
        populateDoctorComboBox(doctorComboBox);
    }

    private void populatePatientComboBox(JComboBox<String> patientComboBox) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchAllPatients();
        while (resultSet.next()) {
            String patientName = resultSet.getString("username");
            patientComboBox.addItem(patientName);
        }
    }

    private void populateDoctorComboBox(JComboBox<String> doctorComboBox) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchAllDoctor();
        while (resultSet.next()) {
            String doctorName = resultSet.getString("name");
            doctorComboBox.addItem(doctorName);
        }
    }

    private boolean rescheduleBooking(String patientName, String doctorName, String newDate, String oldDate) throws SQLException {
        if(databaseInstance.checkIfDoctorAvailable(doctorName,newDate)){
            return databaseInstance.updateBooking(newDate, patientName,doctorName, oldDate);
        }
        return false;
    }

    private boolean removeBooking(String patientName, String doctorName, String date) throws SQLException {
        return databaseInstance.removeBooking(patientName,doctorName,date); // Return true if booking removed successfully, false otherwise
    }
}
