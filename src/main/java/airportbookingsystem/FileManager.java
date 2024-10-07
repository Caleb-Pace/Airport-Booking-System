/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author mehta
 */
public class FileManager {

    // Method to save the data to a file
    public static void save(String file, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write the provided data to the specified file
            writer.write(data);
            writer.close(); // Explicitly close the writer

        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }

    }

    // Method to load the data from a file
    public static String load(String file) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line; // Variable to hold each line read from the file

            // Read each line from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
            reader.close(); // Explicitly close the reader
        } catch (java.io.FileNotFoundException e) {
            return ""; // Return an empty string if the file doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toString(); // Convert StringBuilder content to a String and return it
    }
}