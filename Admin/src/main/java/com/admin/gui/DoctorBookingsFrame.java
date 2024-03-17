package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorBookingsFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public DoctorBookingsFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("View Bookings by Doctor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JLabel selectDoctorLabel = new JLabel("Select Doctor:");
        panel.add(selectDoctorLabel);

        JComboBox<String> doctorComboBox = new JComboBox<>();
        panel.add(doctorComboBox);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                if (selectedDoctor != null) {
                    try {
                        dispose();
                        new BookingsListFrame(username, selectedDoctor, "doctor");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(DoctorBookingsFrame.this, "Please select a doctor.");
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

        populateDoctorComboBox(doctorComboBox);
    }

    private void populateDoctorComboBox(JComboBox<String> doctorComboBox) throws SQLException {
        ResultSet resultSet = databaseInstance.fetchAllDoctor();
        while (resultSet.next()) {
            String doctorName = resultSet.getString("name");
            doctorComboBox.addItem(doctorName);
        }
    }
}
