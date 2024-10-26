package airportbookingsystem;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {

    public static void main(String[] args) {
        // Initialise controllers
        FlightDataController.init();
        BookingController.init();

        // Create frame/GUI
        JFrame frame = new JFrame("Airport Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BookingPanel panel = new BookingPanel();

        // Frame setup
        frame.getContentPane().add(panel);
        frame.setSize(1000, 1050);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                BookingController.cleanup();
            }
        }); // Run controller cleanup method on close
        frame.setVisible(true);
    }
}
