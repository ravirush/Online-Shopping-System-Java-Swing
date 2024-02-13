// Define a class named Electronics that extends the abstract class Product
// This class represents electronic products and inherits attributes and methods from the Product class
public class Electronics extends Product{
    // Additional fields specific to Electronics class
    private String brand;
    private int warrantyPeriod;

    // Constructor to initialize electronic product with specified attributes, utilizing the superclass constructor
    public Electronics(String productId, String productName, int availableItems, double price, String productType, String brand, int warrantyPeriod) {
        // Call the constructor of the superclass (Product) to initialize common attributes
        super(productId, productName, availableItems, price, productType);
        // Initialize fields specific to Electronics class
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter method for retrieving the brand of the electronic product
    public String getBrand() {
        return brand;
    }

    // Setter method for updating the brand of the electronic product
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // Override the toString method to provide a string representation of the electronic product
    @Override
    public String toString() {
        // Utilize the toString method of the superclass (Product) and add specific information for Electronics
        return super.toString() +
                String.format(
                        "Brand:           %s\n" +
                                "Warranty Period: %d months",
                        brand, warrantyPeriod
                );
    }
}
