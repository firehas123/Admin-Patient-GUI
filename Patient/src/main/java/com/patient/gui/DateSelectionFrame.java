package com.patient.gui;

import com.patient.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DateSelectionFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public DateSelectionFrame(String selectedDoctor, String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Select Date");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel dateLabel = new JLabel("Select Date:");
        panel.add(dateLabel);

        JTextField dateField = new JTextField();
        panel.add(dateField);

        JButton confirmBookingButton = new JButton("Confirm Booking");
        confirmBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDate = dateField.getText();
                if (!selectedDate.isEmpty()) {
                    // Perform booking
                    // Date entry -> YYYY/MM/DD
                    try {
                        if (bookAppointment(selectedDoctor, selectedDate, username)) {
                            JOptionPane.showMessageDialog(DateSelectionFrame.this, "Booking confirmed.");
                        } else {
                            JOptionPane.showMessageDialog(DateSelectionFrame.this, "Doctor already booked on selected date.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(DateSelectionFrame.this, "Please enter a date.");
                }
            }
        });
        panel.add(confirmBookingButton);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new ChooseDoctorFrame(username);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(returnButton);

        add(panel);
        setVisible(true);
    }

    private boolean bookAppointment(String doctor, String date, String username) throws SQLException {
        if(databaseInstance.checkIfDoctorAvailable(doctor,date))
            return databaseInstance.insertBooking(doctor,date, username) > 0;
        return false;
    }
}