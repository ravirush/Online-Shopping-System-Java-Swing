// Import the Scanner class to facilitate user input
import java.util.Scanner;

// Define a class named InputValidator
// This class provides methods to validate and retrieve user input
public class InputValidator {
    // Declare a static final Scanner object to be used for reading input from the console
    private static final Scanner scanner = new Scanner(System.in);

    // Method to validate and retrieve a number within a specified range from the user
    // The type parameter is a generic type that extends Number and implements Comparable
    public static <T extends Number & Comparable<T>> T validateNumberWithRange(String prompt, T min, T max, Class<T> type) {
        // Continue prompting the user until a valid input is provided
        while (true) {
            // Get a string input from the user using the validateStringInput method
            String input = validateStringInput(prompt);
            try {
                T numericValue;
                // Parse the input based on the specified type (Integer or Double)
                if (type.equals(Integer.class)) {
                    numericValue = type.cast(Integer.parseInt(input));
                } else if (type.equals(Double.class)) {
                    numericValue = type.cast(Double.parseDouble(input));
                } else {
                    // Throw an exception if the specified type is not supported
                    throw new IllegalArgumentException("Only Integer or Double types are supported.");
                }

                // Check if the parsed value is within the specified range
                if (numericValue.compareTo(min) >= 0 && numericValue.compareTo(max) <= 0) {
                    // Return the valid numeric value if it is within the range
                    return numericValue;
                } else {
                    // Throw an exception if the value is outside the range
                    throw new IllegalArgumentException("Value must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                // Handle exception for invalid number format input
                System.out.println("Invalid input. Please enter a " + (type.equals(Integer.class) ? "integer." : "double."));
            } catch (IllegalArgumentException e) {
                // Handle exception for other illegal arguments
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to validate and retrieve a string input from the user
    public static String validateStringInput(String prompt) {
        // Continue prompting the user until a valid input is provided
        while (true) {
            // Display the prompt to the user
            System.out.print(prompt);
            // Read a line of input from the user
            String userInput = scanner.nextLine();
            // Check if the input matches the specified pattern
            if (userInput.matches("[a-zA-Z0-9_\\s-]+")) {
                // Return the valid string input if it matches the pattern
                return userInput;
            } else {
                // Display an error message for invalid input
                System.out.println("Invalid input. Please enter a string containing only letters, numbers, hyphens, underscores, and spaces.");
            }
        }
    }
}