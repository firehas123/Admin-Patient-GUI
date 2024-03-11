package com.patient.gui;

import com.patient.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ChooseDoctorFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public ChooseDoctorFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Choose Doctor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JComboBox<String> doctorComboBox = new JComboBox<>();
        panel.add(doctorComboBox);

        JButton selectDateButton = new JButton("Select Date");
        selectDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                if (selectedDoctor != null) {
                    // Open DateSelectionFrame and pass selectedDoctor
                    try {
                        new DateSelectionFrame(selectedDoctor, username);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ChooseDoctorFrame.this, "Please select a doctor.");
                }
            }
        });
        panel.add(selectDateButton);

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
}
