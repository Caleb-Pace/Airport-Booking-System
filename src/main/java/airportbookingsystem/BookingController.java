package airportbookingsystem;

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
    public static void addBooking(int id, String passengerName , String flightNumber, String seatNumber) {
        if (!isControllerReady())
            return;
        
        BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
    }
}
