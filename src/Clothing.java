// Define a class named Clothing that extends the abstract class Product
// This class represents clothing products and inherits attributes and methods from the Product class
public class Clothing extends Product{
    // Additional fields specific to Clothing class
    private String size;
    private String color;

    // Constructor to initialize clothing product with specified attributes, utilizing the superclass constructor
    public Clothing(String productId, String productName, int availableItems, double price, String productType, String size, String color) {
        // Call the constructor of the superclass (Product) to initialize common attributes
        super(productId, productName, availableItems, price, productType);
        // Initialize fields specific to Clothing class
        this.size = size;
        this.color = color;
    }

    // Getter method for retrieving the size of the clothing product
    public String getSize() {
        return size;
    }

    // Setter method for updating the size of the clothing product
    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Override the toString method to provide a string representation of the clothing product
    @Override
    public String toString() {
        // Utilize the toString method of the superclass (Product) and add specific information for Clothing
        return super.toString() +
                String.format(
                        "Size:            %s\n" +
                                "Color:           %s",
                        size, color
                );
    }
}
