package airportbookingsystem;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;


public class BookingPanel extends JPanel{
        public BookingPanel() {
        // Set layout
        setLayout(new BorderLayout());

        // Create title label
        JLabel titleLabel = new JLabel("AIRPORT BOOKING SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        add(titleLabel, BorderLayout.NORTH);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create buttons
        JButton makeBookingButton = new JButton("MAKE BOOKING");
        JButton viewBookingButton = new JButton("VIEW BOOKING");

        // Add buttons to the panel
        buttonPanel.add(makeBookingButton);
        buttonPanel.add(viewBookingButton);

        // Add button panel to the center of the main panel
        add(buttonPanel, BorderLayout.CENTER);
    }
}
