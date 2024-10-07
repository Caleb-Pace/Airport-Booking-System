package airportbookingsystem;

import airportbookingsystem.Seat.SeatType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/**
 * Custom TypeAdapter for serializing and deserializing Seat objects with Gson.
 * 
 * @author Caleb
 * 
 * Assisted by ChatGPT 4o-mini
 */
public class SeatTypeAdapter extends TypeAdapter<Seat>
{
    /**
     * Serializes a Seat object to JSON.
     *
     * @param writer The JsonWriter used for serialization.
     * @param obj    The Seat object to serialize.
     */
    @Override
    public void write(JsonWriter writer, Seat obj) throws IOException
    {
        writer.beginObject();
        writer.name("seatType").value(obj.getSeatType().name()); // Write seat type
        writer.name("isTaken").value(obj.isTaken);              // Write seat status
        writer.endObject();
    }

    /**
     * Deserializes a Seat object from JSON.
     *
     * @param reader The JsonReader used for deserialization.
     * @return The deserialized Seat object.
     */
    @Override
    public Seat read(JsonReader reader) throws IOException
    {
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        SeatType type = SeatType.valueOf(jsonObject.get("seatType").getAsString()); // Get seat type
        
        boolean isTaken = jsonObject.get("isTaken").getAsBoolean(); // Get seat status
        switch (type)
        {
            case ECONOMY:
                return new EconomyClassSeat(isTaken); // Return corresponding seat type
            case FIRST_CLASS:
                return new FirstClassSeat(isTaken);
            case BUSINESS:
                return new BusinessClassSeat(isTaken);
            default:
                return null; // Handle unexpected seat type
        }
    }
}
