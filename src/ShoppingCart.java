// Import necessary Java utility classes
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Define a class named ShoppingCart
// This class represents a shopping cart that can hold products
public class ShoppingCart {
    // Field to store the calculated 20% discount
    private double twentyPercentDiscount;

    // HashMap to store products in the cart along with their quantities
    private HashMap<Product, Integer> productListCart;

    // Constructor to initialize the shopping cart with an empty product list
    public ShoppingCart() {
        this.productListCart = new HashMap<>();
    }

    // Method to add a product to the cart
    public void addProduct(Product product ) {
        if (productListCart.containsKey(product)) {
            // Product already exists in the cart, update the quantity
            int currentQuantity = productListCart.get(product);
            productListCart.put(product, currentQuantity + 1);
        } else {
            // Product is not in the cart, add it with the given quantity
            productListCart.put(product, 1);
        }
    }

    // Method to remove a product from the cart
    public void removeProduct(Product product) {
        this.productListCart.remove(product);
    }

    // Method to calculate the total cost of products in the cart
    public double calculateTotal() {
        double totalCost = 0.0;
        for (Product product : productListCart.keySet()) {
            int quantity = productListCart.get(product);
            totalCost += product.getPrice() * quantity;
        }
        return totalCost;
    }

    // Method to calculate and return the 20% discount on the total cost
    public double calculateTwentyPercentDiscount() {
        twentyPercentDiscount = calculateTotal() * 0.2;
        System.out.println(twentyPercentDiscount + " £");
        return twentyPercentDiscount;
    }

    // Method to calculate and return the final total cost after applying the discount
    public double calculateFinalTotal() {
        double finalTotalCost = calculateTotal() - twentyPercentDiscount;
        System.out.println(finalTotalCost + " £");
        return finalTotalCost;
    }

    // Getter method to retrieve the product list in the cart
    public HashMap<Product,Integer> getproductListCart(){
        return this.productListCart;
    }

    // Getter method to retrieve the list of products in the cart as an ArrayList
    public ArrayList<Product> getProductListCart() {
        ArrayList<Product> products = new ArrayList<>();
        for (Map.Entry<Product,Integer> productIntegerEntry:productListCart.entrySet()){
            products.add(productIntegerEntry.getKey());
        }
        return products;
    }
}
