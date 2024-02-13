// Define an abstract class named Product that implements the Comparable interface
// This class serves as a template for different types of products in an online shopping system
public abstract class Product implements Comparable<Product> {
    // Private fields to store information about a product
    private String productId;
    private String productName;
    private int availableItems;
    private double price;
    private String productType;

    // Constructor to initialize the product with specified attributes
    public Product(String productId, String productName, int availableItems, double price, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
        this.productType = productType;
    }

    // Getter method for retrieving the product ID
    public String getProductId() {
        return productId;
    }

    // Setter method for updating the product ID
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    // Method to simulate buying one item, reducing available items by 1
    public int buyAvailableItems() {
        if(availableItems > 0){
            availableItems -= 1;
        }
        return availableItems;
    }

    // Implementation of the compareTo method from the Comparable interface
    // Used for sorting products based on their product IDs
    public int compareTo(Product product) {
        return productId.compareTo(product.productId);
    }

    // Override the toString method to provide a string representation of the product
    @Override
    public String toString() {
        return String.format("\n" +
                        "Product Type:    %s\n" +
                        "Product ID:      %s\n" +
                        "Product Name:    %s\n" +
                        "Available Items: %d\n" +
                        "Price:           $%.2f\n",
                productType, productId, productName, availableItems, price
        );
    }
}
