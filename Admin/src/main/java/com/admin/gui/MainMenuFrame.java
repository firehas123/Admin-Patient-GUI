package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainMenuFrame extends JFrame {
    private String username;
    private DatabaseInstance databaseInstance;

    public MainMenuFrame(String username) throws SQLException {
        databaseInstance = DatabaseInstance.getInstance();
        this.username = username;
        setTitle("Welcome "+username);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton chooseDoctorButton = new JButton("Add Doctor");
        chooseDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin: "+username+" is going to choose a doctor");
                // Open ChooseDoctorFrame
                dispose();
                new AddDoctorFrame(databaseInstance, username);
            }
        });
        panel.add(chooseDoctorButton);

        JButton changeDoctorButton = new JButton("Change Doctor");
        changeDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin: "+username+" is going to change a doctor");
                // Open ChangeDoctorFrame
            }
        });
        panel.add(changeDoctorButton);

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin: "+username+" has viewed their bookings");
                // Open ViewBookingsFrame
            }
        });
        panel.add(viewBookingsButton);

        JButton rescheduleBookingButton = new JButton("Reschedule Booking");
        rescheduleBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin: "+username+" has rescheduled their booking");
                // Open RescheduleBookingFrame
            }
        });
        panel.add(rescheduleBookingButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin: "+username+" has logged out");
                // Back to homepage
                dispose();
                try {
                    new LoginFrame();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}

