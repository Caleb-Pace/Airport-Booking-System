package airportbookingsystem;

import java.util.HashMap;
import java.util.HashSet;

public class FlightDataController {
    private static final String controllerName = "Flight data Controller";
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

        _isInitialised = true;
        System.out.printf("%s: Initialised!\n", controllerName);
    }

    // TODO: Comment
    public static HashSet<Flight> getFlights() {
        if (!isControllerReady())
            return null;
        
        return FlightManager.getFlights();
    }

    // TODO: Comment
    public static Flight getFlightByNumber(String flightNumber) {
        if (!isControllerReady())
            return null;
        
        return FlightManager.getFlightByNumber(flightNumber);
    }

    // TODO: Comment
    public static HashMap<String, Seat> getSeatMap(Flight flight) {
        if (!isControllerReady())
            return null;
        
        return FlightManager.getSeatMap(flight);
    }
}
