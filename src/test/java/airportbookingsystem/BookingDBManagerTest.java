/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package airportbookingsystem;

import java.sql.Connection;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Caleb
 */
public class BookingDBManagerTest {
    
    public BookingDBManagerTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        FlightManager.load();             // Load flights
        BookingDBManager.getConnection(); // Connect to embedded database
        System.out.println("\n[Tests] (for Booking DB Manager)");
    }
    
    @AfterClass
    public static void tearDownClass() {
        BookingDBManager.resetDB(); // Clear database
        BookingDBManager.close();
    }

    /**
     * Test of getConnection method, of class BookingDBManager.
     */
    // @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Connection result = BookingDBManager.getConnection();
        assertNotNull(result);
    }

    /**
     * Test of add method, of class BookingDBManager.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int id = 1000;
        String passengerName = "Steve";
        String flightNumber = "AB1234";
        String seatNumber = "B19";
        boolean expResult = true;
        boolean result = BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
        assertEquals(expResult, result);

        System.out.println("add - Duplicate id");
        passengerName = "Bob";
        flightNumber = "AB9876";
        seatNumber = "C2";
        expResult = false;
        result = BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
        assertEquals(expResult, result);

        System.out.println("add - id too long");
        id = 10000;
        result = BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
        assertEquals(expResult, result);
        id = 1001;
        
        System.out.println("add - passengerName too long");
        passengerName = "Aequilibrium Generatrix Doloribus";
        result = BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
        assertEquals(expResult, result);
        passengerName = "Bob";

        System.out.println("add - flightNumber too long");
        flightNumber = "XY1234ABCD567";
        result = BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
        assertEquals(expResult, result);
        flightNumber = "AB9876";

        System.out.println("add - seatNumber too long");
        seatNumber = "C2052";
        result = BookingDBManager.add(id, passengerName, flightNumber, seatNumber);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeByID method, of class BookingDBManager.
     */
    // @Test
    public void testRemoveByID() {
        System.out.println("removeByID");
        int id = 1001;
        BookingDBManager.removeByID(id);

        Booking expResult = null;
        Booking result = BookingDBManager.getByID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getByID method, of class BookingDBManager.
     */
    @Test
    public void testGetByID() {
        System.out.println("getByID");
        int id = 1003;

        // TODO: Remove - debug
        // Preparation
        boolean a = BookingDBManager.add(id, "Steve", "JH9302", "C243");
        System.out.printf("Added %d? %s\n", id, (a?"T":"F"));
        System.out.printf("ID count: %d\n", BookingDBManager.getAllIDs().size());

        // Test
        Booking result = BookingDBManager.getByID(id);

        // TODO: Remove - debug
        if (result != null)
            System.out.printf("%d", result.getId());
        else
            System.out.println("null result");

        assertNotNull(result);
    }

    /**
     * Test of getAllBookings method, of class BookingDBManager.
     */
    // @Test
    public void testGetAllBookings() {
        System.out.println("getAllBookings");
        ArrayList<Booking> result = BookingDBManager.getAllBookings();
        assertNotNull(result);
    }

    /**
     * Test of getAllIDs method, of class BookingDBManager.
     */
    // @Test
    public void testGetAllIDs() {
        System.out.println("getAllIDs");
        ArrayList<Integer> result = BookingDBManager.getAllIDs();
        assertNotNull(result);
    }
}
