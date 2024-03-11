package com.admin.gui;

import com.admin.dao.DatabaseInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {
    private String username;

    public MainMenuFrame(String username, DatabaseInstance databaseInstance) {
        this.username = username;
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton chooseDoctorButton = new JButton("Choose Doctor");
        chooseDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ChooseDoctorFrame
            }
        });
        panel.add(chooseDoctorButton);

        JButton changeDoctorButton = new JButton("Change Doctor");
        changeDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ChangeDoctorFrame
            }
        });
        panel.add(changeDoctorButton);

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ViewBookingsFrame
            }
        });
        panel.add(viewBookingsButton);

        JButton rescheduleBookingButton = new JButton("Reschedule Booking");
        rescheduleBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open RescheduleBookingFrame
            }
        });
        panel.add(rescheduleBookingButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Back to homepage
                dispose();
                 new LoginFrame(databaseInstance);
            }
        });
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}

