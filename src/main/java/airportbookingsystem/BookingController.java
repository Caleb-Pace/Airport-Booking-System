package airportbookingsystem;

public class BookingController {
    private static boolean _isInitialised = false;

    // TODO: Comment
    public static void init() {
        FlightManager.load(); // Load in flight data
        BookingDBManager.getConnection();

        _isInitialised = true;
        System.out.println("Controller: Initialised!");
    }

    // TODO: Comment
    public static void cleanup() {
        BookingDBManager.close(); // Close connection

        _isInitialised = false;
        System.out.println("Controller: Cleanup completed!");
    }

    // TODO: Comment
    public static void addBooking(int id, String passengerName , String flightNumber, String seatNumber) {
        if (!isControllerReady())
            return;
        
        BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
    }

    // TODO: Comment
    private static boolean isControllerReady() {
        if (_isInitialised)
            return true;

        System.err.println("Controller: Not initialised!");
        return false;
    }
}
