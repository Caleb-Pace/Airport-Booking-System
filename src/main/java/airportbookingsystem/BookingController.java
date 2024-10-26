package airportbookingsystem;

import java.util.ArrayList;

public class BookingController {
    private static final String controllerName = "Booking Controller";
    private static boolean _isInitialised = false;

    // TODO: Comment
    private static boolean isControllerReady() {
        if (_isInitialised)
            return true;

        System.err.printf("%s: Not initialised!\n", controllerName);
        return false;
    }

    // TODO: Comment
    public static void init() {
        FlightManager.load(); // Load in flight data
        BookingDBManager.getConnection();

        _isInitialised = true;
        System.out.printf("%s: Initialised!\n", controllerName);
    }

    // TODO: Comment
    public static void cleanup() {
        BookingDBManager.close(); // Close connection

        _isInitialised = false;
        System.out.printf("%s: Cleanup completed!\n", controllerName);
    }

    // TODO: Comment
    public static void addBooking(String passengerName, Flight flight, String seatNumber) {
        // Get flight number
        String flightNumber = flight.getFlightNumber();

        // Add booking
        addBooking(passengerName, flightNumber, seatNumber);
    }

    // TODO: Comment
    public static void addBooking(String passengerName, String flightNumber, String seatNumber) {
        if (!isControllerReady())
            return;
        
        BookingDBManager.add(getNextID(), passengerName, flightNumber, seatNumber);
    }

    // TODO: Comment
    public static ArrayList<Integer> getBookingIDs() {
        if (!isControllerReady())
        return null;
        
        return BookingDBManager.getAllIDs();
    }
    
    // TODO: Comment
    public static Booking getBooking(int id) {
        if (!isControllerReady())
            return null;

        return BookingDBManager.getByID(id);
    }

    // TODO: Comment
    public static void cancel(Booking booking) {
        if (!isControllerReady())
            return;

        BookingDBManager.removeByID(booking.getId());
    }

    // TODO: Comment
    public static void cancel(int id) {
        if (!isControllerReady())
            return;

        BookingDBManager.removeByID(id);
    }

    // TODO: Comment
    private static int getNextID() {
        int id = 1000;
        ArrayList<Integer> ids = BookingDBManager.getAllIDs();
        
        // Get latest id
        if (!ids.isEmpty()) {
            int previousID = ids.get((ids.size() - 1));
            id = previousID + 1;
        }

        return id;
    }
}
