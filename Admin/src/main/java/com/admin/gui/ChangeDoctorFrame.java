package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChangeDoctorFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public ChangeDoctorFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("Change Patient's Doctor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel patientLabel = new JLabel("Patient Name:");
        panel.add(patientLabel);

        JTextField patientField = new JTextField();
        panel.add(patientField);

        JLabel currentDoctorLabel = new JLabel("Current Doctor:");
        panel.add(currentDoctorLabel);

        JTextField currentDoctorField = new JTextField();
        panel.add(currentDoctorField);

        JLabel newDoctorLabel = new JLabel("New Doctor:");
        panel.add(newDoctorLabel);

        JTextField newDoctorField = new JTextField();
        panel.add(newDoctorField);

        JLabel dateLabel = new JLabel("Current Date:");
        panel.add(dateLabel);

        JTextField dateField = new JTextField();
        panel.add(dateField);

        JButton changeDoctorButton = new JButton("Change Doctor");
        changeDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = patientField.getText();
                String selectedCurrentDoctor = currentDoctorField.getText();
                String selectedNewDoctor = newDoctorField.getText();
                String selectedDate = dateField.getText();
                if (!selectedPatient.isEmpty() && !selectedCurrentDoctor.isEmpty() && !selectedNewDoctor.isEmpty() && !selectedDate.isEmpty()) {
                    try {
                        if (changePatientDoctor(selectedPatient, selectedCurrentDoctor, selectedNewDoctor, selectedDate)) {
                            JOptionPane.showMessageDialog(ChangeDoctorFrame.this, "Doctor change successful.");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(ChangeDoctorFrame.this, "Failed to change doctor.");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(ChangeDoctorFrame.this, "Please fill in all fields.");
                }
            }
        });
        panel.add(changeDoctorButton);

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
    }

    private boolean changePatientDoctor(String patientName, String currentDoctor, String newDoctor, String date) throws SQLException {
        return databaseInstance.changePatientDoctor(patientName, currentDoctor, newDoctor, date);
    }
}
