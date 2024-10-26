package airportbookingsystem;

import java.util.ArrayList;

public class BookingController {
    private static final String controllerName = "Booking Controller";
    private static boolean _isInitialised = false;

    /**
     * Checks if the controller is initialised and ready for use.
     *
     * @return boolean True if the controller is initialized, otherwise false
     */
    private static boolean isControllerReady() {
        if (_isInitialised)
            return true;

        // Not initialised
        System.err.printf("%s: Not initialised!\n", controllerName);
        return false;
    }

    /**
     * Initializes the controller by establishing a connection to the database.
     */
    public static void init() {
        BookingDBManager.getConnection();

        _isInitialised = true;
        System.out.printf("%s: Initialised!\n", controllerName);
    }

    /**
     * Cleans up resources by closing the connection to the database.
     */
    public static void cleanup() {
        BookingDBManager.close(); // Close connection

        _isInitialised = false;
        System.out.printf("%s: Cleanup completed!\n", controllerName);
    }

    /**
     * Creates and adds a booking to the database.
     *
     * @param passengerName Name of the passenger
     * @param flight        The flight object for the booking
     * @param seatNumber    The seat number for the booking
     */
    public static void addBooking(String passengerName, Flight flight, String seatNumber) {
        // Get flight number
        String flightNumber = flight.getFlightNumber();

        // Add booking
        addBooking(passengerName, flightNumber, seatNumber);
    }

    /**
     * Creates and adds a booking to the database.
     *
     * @param passengerName Name of the passenger
     * @param flightNumber  Flight number for the booking
     * @param seatNumber    The seat number for the booking
     */
    public static void addBooking(String passengerName, String flightNumber, String seatNumber) {
        if (!isControllerReady())
            return;
        
        BookingDBManager.add(getNextID(), passengerName, flightNumber, seatNumber);
    }

    /**
     * Retrieves a list of bookings from the database.
     *
     * @return ArrayList<Booking> List of booking IDs
     */
    public static ArrayList<Booking> getBookings() {
        if (!isControllerReady())
        return null;
        
        return BookingDBManager.getAllBookings();
    }
    
    /**
     * Retrieves a booking from the database by its ID.
     *
     * @param id Unique booking ID to search for
     * @return Booking The booking object if found, or null if not found
     */
    public static Booking getBooking(int id) {
        if (!isControllerReady())
            return null;

        return BookingDBManager.getByID(id);
    }

    /**
     * Cancels a booking by removing it from the database.
     *
     * @param booking The booking object to be canceled
     */
    public static void cancel(Booking booking) {
        if (!isControllerReady())
            return;

        BookingDBManager.removeByID(booking.getId());
    }

    /**
     * Cancels a booking by removing it from the database using the booking ID.
     *
     * @param id Unique booking ID of the booking to be canceled
     */
    public static void cancel(int id) {
        if (!isControllerReady())
            return;

        BookingDBManager.removeByID(id);
    }

    /**
     * Retrieves the next available booking ID, which is the ID following the last used ID.
     *
     * @return int The next available booking ID
     */
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
