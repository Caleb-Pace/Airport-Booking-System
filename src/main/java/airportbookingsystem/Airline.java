/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportbookingsystem;

/**
 *
 * @author mehta
 */
public class Airline {

    private String name; // The name of the airline
    private String banner; // The banner or slogan associated with the airline

    // Constructor to initialize an Airline object with a name and a banner
    public Airline(String name, String banner) {
        this.name = name;
        this.banner = banner;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
