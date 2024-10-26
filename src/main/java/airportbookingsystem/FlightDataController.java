package airportbookingsystem;

import java.util.HashMap;
import java.util.HashSet;

public class FlightDataController {
    private static final String controllerName = "Flight data Controller";
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
     * Initializes the controller by loading flight data from a JSON file.
     */
    public static void init() {
        FlightManager.load(); // Load in flight data

        _isInitialised = true;
        System.out.printf("%s: Initialised!\n", controllerName);
    }

    /**
     * Retrieves a set of flights.
     *
     * @return HashSet<Flight> A set of flight objects
     */
    public static HashSet<Flight> getFlights() {
        if (!isControllerReady())
            return null;
        
        return FlightManager.getFlights();
    }

    /**
     * Retrieves a flight by its flight number.
     *
     * @param flightNumber The flight number to search for
     * @return Flight The flight object if found, or null if not found
     */
    public static Flight getFlightByNumber(String flightNumber) {
        if (!isControllerReady())
            return null;
        
        return FlightManager.getFlightByNumber(flightNumber);
    }

    /**
     * Retrieves a map of seats for the specified flight.
     *
     * @param flight The flight for which to retrieve the seat map
     * @return HashMap<String, Seat> A map of seat identifiers to their corresponding Seat objects
     */
    public static HashMap<String, Seat> getSeatMap(Flight flight) {
        if (!isControllerReady())
            return null;
        
        return FlightManager.getSeatMap(flight);
    }
}
