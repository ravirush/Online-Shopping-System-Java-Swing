// Import necessary Java utility classes and I/O classes
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

// Define a class named WestminsterShoppingManager that implements the ShoppingManager interface
// This class serves as a shopping manager for the Westminster Shopping System
public class WestminsterShoppingManager implements ShoppingManager {
    // ArrayList to store the product list in the shopping system
    private ArrayList<Product> productListSystem = new ArrayList<>(50);

    // Default constructor for WestminsterShoppingManager
    public WestminsterShoppingManager() {}

    // Method to display and handle the Westminster Shopping Manager Menu
    public void shoppingMenu() {
        // Create a Scanner object for user input
        Scanner input = new Scanner(System.in);
        // Boolean variable to control the loop
        boolean exit = true;
        // Loop to keep displaying the menu until the user chooses to exit
        while (exit) {
            // Display the Westminster Shopping Manager Menu options
            System.out.println("\n----- Westminster Shopping Manager Menu -----");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print the list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Open graphical user interface (GUI)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            // Read user choice as a String
            String choice = input.next();
            input.nextLine(); // To clear the buffer, consume the newline character

            // Process the user choice using a switch statement
            switch (choice) {
                case "1":
                    // Call the addProduct method
                    addProduct();
                    break;

                case "2":
                    System.out.println();
                    // Call the removeProduct method
                    removeProduct();
                    break;

                case "3":
                    // Call the printProducts method to display the list of products
                    printProducts(false);
                    break;

                case "4":
                    // Call the saveProducts method to save the products to a file
                    saveProducts();
                    break;

                case "5":
                    // Create a new WestminsterShoppingFrame (GUI) and pass the product list
                    new WestminsterShoppingFrame(this.productListSystem);
                    break;

                case "6":
                    // Set exit to false to terminate the loop
                    exit = false;
                    System.out.println("Exiting the program, Goodbye");
                    break;

                default:
                    // Display an error message for invalid choices
                    System.out.println("Invalid choice, please try again");
            }
        }
    }

    // Method to add a new product to the product list
    @Override
    public void addProduct() {
        // Get the product type option from the user (1 for Electronics, 2 for Clothing)
        int option = InputValidator.validateNumberWithRange("1 for Electronics, 2 for Clothing: ", 1,2, Integer.class);
        // Get common attributes for the product from the user
        String productId = InputValidator.validateStringInput("Enter Product ID: ");
        String productName = InputValidator.validateStringInput("Enter Product Name: ");
        int availableItems = InputValidator.validateNumberWithRange("Enter Available Items: ", 1, 1000, Integer.class);
        double price = InputValidator.validateNumberWithRange("Enter Price: ", 1.0, 999999.0, Double.class);

        try {
            // Check the product type option and gather additional attributes accordingly
            if (option == 1) {
                // For Electronics, get brand and warranty period
                String brand = InputValidator.validateStringInput("Enter Brand: ");
                int warrantyPeriod = InputValidator.validateNumberWithRange("Enter Warranty Period: ", 1, 999, Integer.class);
                // Create and add an Electronics object to the product list
                productListSystem.add(new Electronics(productId, productName, availableItems, price, "Electronics", brand, warrantyPeriod));
            } else {
                // For Clothing, get size and color
                String size = InputValidator.validateStringInput("Enter Size: ");
                String color = InputValidator.validateStringInput("Enter color: ");
                // Create and add a Clothing object to the product list
                productListSystem.add(new Clothing(productId, productName, availableItems, price, "Clothing", size, color));
            }
        } catch (Exception e){
            // Handle exceptions, such as reaching the maximum product limit
            System.out.println("Cannot add more products, maximum product limit reached.");
        }
    }

    // Method to remove a product from the product list
    @Override
    public void removeProduct() {
        // Display the list of products with additional details for deletion
        printProducts(true);
        System.out.println();
        // Get the Product ID to delete from the user
        String deleteId = InputValidator.validateStringInput("Enter Product ID to delete the product: ");
        // Iterate through the product list to find and delete the specified product
        for (Product product : productListSystem) {
            if (product.getProductId().equals(deleteId)) {
                // Print details of the deleted product based on its type (Electronics or Clothing)
                if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    System.out.println("Product deleted! Type = Electronics, Product ID = " + deleteId + ", Product Name = " + product.getProductName() + ", Unit Price = " + product.getPrice() + " £" + ", Item Quantity = " + product.getAvailableItems() + ", Brand = " + electronics.getBrand() + ", Warranty Period = " + electronics.getWarrantyPeriod() + " months");
                } else if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    System.out.println("Product deleted! Type = Clothing, Product ID = " + deleteId + ", Product Name = " + product.getProductName() + ", Unit Price = " + product.getPrice() + " £" + ", Item Quantity = " + product.getAvailableItems() + ", Size = " + clothing.getSize() + ", Color = " + clothing.getColor());
                }
                // Remove the product from the product list
                productListSystem.remove(product);
                // Display the total number of products left in the list
                System.out.println("Total number of products left: " + productListSystem.size());
                // Exit the loop after deleting the product
                break;
            } else {
                // Display a message if the specified product ID is not found
                System.out.println("Product with ID " + deleteId + " is not found");
            }
        }
    }

    // Method to print the list of products, with an option to show additional details for deletion
    @Override
    public void printProducts(boolean forDelete) {
        // Implement sorting logic here (alphabetical order based on Product ID)
        Collections.sort(productListSystem, Comparator.comparing(Product::getProductId));
        // Check if the list is intended for deletion
        if (forDelete) {
            // Print basic details for each product in the productList (Product ID, Product Name, Product Type)
            for (Product product : productListSystem) {
                System.out.println(product.getProductId() + " " + product.getProductName() + " " + product.getProductType());
            }
        } else {
            // Print full details for each product in the productList
            for (Product product : productListSystem) {
                System.out.println(product);
            }
        }
    }

    // Method to save the products to a file named "products.txt"
    @Override
    public void saveProducts() {
        try (PrintWriter writer = new PrintWriter("products.txt")) {
            // Iterate through each product in the productListSystem
            for (Product product : productListSystem) {
                // Write common attributes to the file
                writer.println(product.getProductType());
                writer.println(product.getProductId());
                writer.println(product.getProductName());
                writer.println(product.getAvailableItems());
                writer.println(product.getPrice());

                // Check if the product is an instance of Electronics or Clothing and write additional attributes accordingly
                if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    writer.println(electronics.getBrand());
                    writer.println(electronics.getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    writer.println(clothing.getSize());
                    writer.println(clothing.getColor());
                }
                // Add a delimiter after each product in the file
                writer.println("---");
            }
            System.out.println("Products saved to file.");
        } catch (IOException e) {
            // Handle IOException by printing the stack trace
            e.printStackTrace();
        }
    }

    // Method to load products from the text file when the application starts
    public void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check for delimiter or empty lines and skip
                if (line.trim().equals("---") || line.trim().isEmpty()) {
                    continue;
                }

                // Read common attributes from the file
                String productType = line.trim();
                String productId = reader.readLine().trim();
                String productName = reader.readLine().trim();
                int availableItems = Integer.parseInt(reader.readLine().trim());
                double price = Double.parseDouble(reader.readLine().trim());

                // Check the product type and read additional attributes accordingly
                if (productType.equals("Electronics")) {
                    String brand = reader.readLine().trim();
                    int warranty = Integer.parseInt(reader.readLine().trim());
                    // Create and add an Electronics object to the productListSystem
                    productListSystem.add(new Electronics(productId, productName, availableItems, price, "Electronics", brand, warranty));

                } else if (productType.equals("Clothing")) {
                    String size = reader.readLine().trim();
                    String color = reader.readLine().trim();
                    // Create and add a Clothing object to the productListSystem
                    productListSystem.add(new Clothing(productId, productName, availableItems, price, "Clothing", size, color));
                }
            }
            System.out.println("Products loaded from file.");
        } catch (IOException e) {
            // Handle IOException by printing the stack trace
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // Handle NumberFormatException when parsing numbers from the file
            System.out.println("Error parsing number from file: " + e.getMessage());
        }
    }
}
