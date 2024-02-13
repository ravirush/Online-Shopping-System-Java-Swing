// Define an interface named ShoppingManager
// This interface outlines the methods that a shopping manager in the online shopping system should implement
public interface ShoppingManager {
    // Method to add a product to the system
    void addProduct();

    // Method to remove a product from the system
    void removeProduct();

    // Method to print the list of products
    void printProducts(boolean forDelete);

    // Method to save the products to a text file
    void saveProducts();
}
