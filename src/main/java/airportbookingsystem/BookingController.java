package airportbookingsystem;

public class BookingController {
    public static void init() {
        FlightManager.load(); // Load in flight data
        BookingDBManager.getConnection();
        
        System.out.println("Controller: Initialised!");
    }

    public static void cleanup() {
        BookingDBManager.close(); // Close connection

        System.out.println("Controller: Cleanup completed!");
    }
}
