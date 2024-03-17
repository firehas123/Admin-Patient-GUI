package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBookingsFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public ViewBookingsFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("View Bookings");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel selectOptionLabel = new JLabel("Select an option:");
        panel.add(selectOptionLabel);

        String[] options = {"View by Doctor", "View by Patient", "View by Month and Year"};
        JComboBox<String> optionsComboBox = new JComboBox<>(options);
        panel.add(optionsComboBox);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) optionsComboBox.getSelectedItem();
                if (selectedOption != null) {
                    try {
                        dispose();
                        if (selectedOption.equals("View by Doctor")) {
                            new DoctorBookingsFrame(username);
                        } else if (selectedOption.equals("View by Patient")) {
                            new PatientBookingsFrame(username);
                        } else if (selectedOption.equals("View by Month and Year")) {
                            new MonthYearBookingsFrame(username);
                        }

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
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
