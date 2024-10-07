package airportbookingsystem;

import java.util.HashMap;
import java.util.HashSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * Manages flight data, including loading and saving flights from/to JSON.
 * 
 * @author mehta
 */
public class FlightManager {

    // HashMap to store flights using their flight number as the key
    private static final HashMap<String, Flight> flights = new HashMap<>();

    // File path for saving and loading flight data in JSON format
    private static final String filepath = "./resources/flights.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
                                                      .registerTypeAdapter(Seat.class, new SeatTypeAdapter())
                                                      .create();

    /**
     * Returns all flights as a HashSet.
     * 
     * @return A HashSet containing all flights.
     */
    public static HashSet<Flight> getFlights() {
        return new HashSet<>(flights.values());
    }

    /**
     * Retrieves a flight by its flight number.
     * 
     * @param flightNumber The flight number.
     * @return The Flight object or null if not found.
     */
    public static Flight getFlightByNumber(String flightNumber) {
        return flights.get(flightNumber);
    }

    /**
     * Retrieves all flight numbers.
     * 
     * @return An array of flight numbers.
     */
    public static String[] getFlightNumbers() {
        return flights.keySet().toArray(new String[0]);
    }

    /**
     * Retrieves a map of seat numbers to Seat objects for a given flight number.
     * 
     * @param flightNumber The flight number.
     * @return A HashMap mapping seat numbers to Seat objects.
     */
    public static HashMap<String, Seat> getSeatMap(String flightNumber) {
        return getSeatMap(getFlightByNumber(flightNumber));
    }

    /**
     * Retrieves a map of seat numbers to Seat objects for a given flight.
     * 
     * @param flight The Flight object.
     * @return A HashMap mapping seat numbers to Seat objects.
     */
    public static HashMap<String, Seat> getSeatMap(Flight flight) {
        HashMap<String, Seat> map = new HashMap<>();
        Seat[] seats = flight.getAeroplane().getSeats();

        // Failsafe: Too many columns (Should not be reached!)
        if (seats.length > (26 * Aeroplane.SEAT_ROWS)) {
            return null;
        }

        // Loop through seats and create seat map
        for (int j = 0; j < Aeroplane.SEAT_ROWS; j++) {
            int rowNum = j + 1;
            char columnCh = 'A';
            for (int i = j; i < seats.length; i += Aeroplane.SEAT_ROWS) {
                map.put(("" + columnCh++ + rowNum), seats[i]);
            }
        }

        return map;
    }

    /**
     * Loads flight data from a JSON file.
     */
    public static void load() {
        String json = FileManager.load(filepath);

        // Create a TypeToken for Flight[]
        Type flightArrayType = new TypeToken<Flight[]>() {}.getType(); // Credit ChatGPT 4o-mini

        // Parse flight data
        Flight[] flightObjs = gson.fromJson(json, flightArrayType);
        if (flightObjs == null) {
            return;
        }

        // Populate the flights map
        for (Flight flight : flightObjs) {
            flights.put(flight.getFlightNumber(), flight);
        }
    }

    /**
     * Saves the current flight data to a JSON file.
     */
    public static void save() {
        // Convert flights to array
        Flight[] flightObjs = flights.values().toArray(new Flight[0]);

        // Convert to JSON
        String json = gson.toJson(flightObjs);

        // Save JSON to file
        FileManager.save(filepath, json);
    }
}
