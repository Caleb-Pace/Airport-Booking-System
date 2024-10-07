package airportbookingsystem;

import java.util.Scanner;

/**
 * Handles user input operations.
 * 
 * @author Caleb
 */
public class InputHandler
{
    /**
     * Pauses execution until the user presses Enter or 'Q/q' to quit.
     */
    public static void pause() {
        Scanner scan = new Scanner(System.in); // Setup
        
        System.out.println("\nPress Enter to continue! (or Q/q to quit)"); // Prompt
        String input = scan.nextLine();

        if (input.equalsIgnoreCase("q")) // Exit program
            System.exit(0);
    }
    
    /**
     * Prompts the user for an integer input within a specified range.
     *
     * @param prompt     The prompt message to display.
     * @param max        The maximum allowable value.
     * @param canAbort   Whether the user can abort the input.
     * @return           The valid integer input or -1 if aborted.
     */
    public static int GetInt(String prompt, int max, boolean canAbort)
    {
        if (max < 0) {
            throw new IllegalArgumentException("'max' cannot be less than 0!");
        }
        
        Scanner scan = new Scanner(System.in); // Setup
        
        boolean isValidInput = false;
        while (!isValidInput)
        {
            try {
                System.out.print(prompt); // Get input
                int input = scan.nextInt();
                
                if (input >= 0 && input <= max) { // Validate user input
                    return input;
                }
                
                System.out.println("Not in range, please try again!");
            } catch (java.util.InputMismatchException ex)
            {
                String str = scan.nextLine(); // Clears the newline character left after nextInt()
                
                if (canAbort && str.equalsIgnoreCase("b")) // Abort
                    return -1;
                
                if (str.equalsIgnoreCase("q")) // Exit
                    System.exit(0);
                
                System.out.println("Invalid integer, please try again!");
            }
        }
        
        return -1; // Unreachable
    }

    /**
     * Prompts the user to select an item from a list of string options.
     *
     * @param prompt    The prompt message to display.
     * @param options   The list of valid options.
     * @param abortStr  The string to return if the user aborts.
     * @return          The selected option or abortStr if aborted.
     */
    public static String GetListItem(String prompt, String[] options, String abortStr)
    {
        if (options == null)
        {
            throw new IllegalArgumentException("'options' cannot be null!");
        }

        Scanner scan = new Scanner(System.in); // Setup
        
        while (true)
        {
            System.out.print(prompt); // Get input
            String input = scan.nextLine();

            for (String item : options) // Search list for input
            {
                if (item != null && item.equalsIgnoreCase(input))
                    return item;
            }
            
            if (input.equalsIgnoreCase("b")) // Abort
                return abortStr;
            
            if (input.equalsIgnoreCase("q")) // Exit
                System.exit(0);
            
            System.out.println("Invalid option, please try again!");
        }
    }
    
    /**
     * Prompts the user to select an item from a list of integer options.
     *
     * @param prompt    The prompt message to display.
     * @param options   The list of valid options.
     * @param abortInt  The integer to return if the user aborts.
     * @return          The selected option or abortInt if aborted.
     */
    public static int GetListItem(String prompt, int[] options, int abortInt)
    {
        if (options == null)
        {
            throw new IllegalArgumentException("'options' cannot be null!");
        }

        Scanner scan = new Scanner(System.in); // Setup
        
        while (true)
        {
            try {
                System.out.print(prompt); // Get input
                int input = scan.nextInt();
                
                for (int item : options) // Search list for input
                {
                    if (input == item)
                        return item;
                }
                
                System.out.println("Invalid option, please try again!");
            } catch (java.util.InputMismatchException ex)
            {
                String str = scan.nextLine(); // Clears the newline character left after nextInt()
                
                if (str.equalsIgnoreCase("b")) // Abort
                    return abortInt;
                
                if (str.equalsIgnoreCase("q")) // Exit
                    System.exit(0);
                
                System.out.println("Invalid integer, please try again!");
            }
        }
    }
}
