package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MonthYearBookingsFrame extends JFrame {
    private DatabaseInstance databaseInstance;

    public MonthYearBookingsFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        setTitle("View Bookings by Month and Year");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel monthLabel = new JLabel("Enter Month:");
        panel.add(monthLabel);

        JTextField monthField = new JTextField();
        panel.add(monthField);

        JLabel yearLabel = new JLabel("Enter Year:");
        panel.add(yearLabel);

        JTextField yearField = new JTextField();
        panel.add(yearField);

        JButton viewButton = new JButton("View");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String month = monthField.getText();
                String year = yearField.getText();
                if (!month.isEmpty() && !year.isEmpty()) {
                    try {
                        dispose();
                        new BookingsListFrame(username, year, month);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(MonthYearBookingsFrame.this, "Please enter both month and year.");
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
    }
}
