package airportbookingsystem;

import javax.swing.JFrame;

public class GUI {
     public static void main(String[] args) {
        JFrame frame = new JFrame("Airport Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BookingPanel panel = new BookingPanel();
        
        frame.getContentPane().add(panel);
        frame.setSize(1000, 1050);
        frame.setVisible(true);
    }
}
