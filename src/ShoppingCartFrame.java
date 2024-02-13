// Import necessary Java Swing, awt, utility classes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartFrame extends JFrame {
    // Instance of the ShoppingCart class
    private ShoppingCart shoppingCart;

    // JFrame for the shopping cart
    private JFrame shoppingCartFrame;

    // JTable to display the shopping cart items
    private JTable shoppingCartTable;

    // Panels for organizing components in the frame
    private JPanel bottomPanel, bottomPanel1, bottomPanel2;

    // Labels for displaying information about the shopping cart
    private JLabel totalLabel, finalTotalLabel, tenDisLabel, twentyDisLabel,
            totalNumLabel, finalTotalNumLabel, tenDisNumLabel, twentyDisNumLabel;

    // Constructor for the ShoppingCartFrame class
    public ShoppingCartFrame(ShoppingCart shoppingCart) {
        // Set the shopping cart for this frame
        this.shoppingCart = shoppingCart;

        // Create the main frame for the shopping cart
        shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(600, 500);
        shoppingCartFrame.setLayout(new GridLayout(2, 1));

        // Create the table displaying the products in the shopping cart
        shoppingCartTable = createTable(this.shoppingCart.getproductListCart());
        JScrollPane shoppingCartPane = new JScrollPane(shoppingCartTable);
        shoppingCartPane.setBorder(new EmptyBorder(28, 28, 7, 28));

        // Create panels for the bottom part of the frame
        bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel1 = new JPanel(new GridLayout(4,1));
        bottomPanel2 = new JPanel(new GridLayout(4,1));

        // Create labels for displaying total, discounts, and final total
        totalLabel = new JLabel("Total");
        finalTotalLabel = new JLabel("Final Total");
        tenDisLabel = new JLabel("First Purchase Discount (10%)");
        twentyDisLabel = new JLabel("Three Items in same Category Discount (20%)");
        // Create labels for displaying corresponding numerical values
        totalNumLabel = new JLabel();
        finalTotalNumLabel = new JLabel();
        tenDisNumLabel = new JLabel();
        twentyDisNumLabel = new JLabel();

        // Add labels to the panels
        bottomPanel1.add(totalLabel);
        bottomPanel1.add(tenDisLabel);
        bottomPanel1.add(twentyDisLabel);
        bottomPanel1.add(finalTotalLabel);

        bottomPanel2.add(totalNumLabel);
        bottomPanel2.add(tenDisNumLabel);
        bottomPanel2.add(twentyDisNumLabel);
        bottomPanel2.add(finalTotalNumLabel);

        // Add the label panels to the main bottom panel
        bottomPanel.add(bottomPanel1);
        bottomPanel.add(bottomPanel2);

        // Add the shopping cart table to the frame
        shoppingCartFrame.add(shoppingCartPane);

        // Add a TableModelListener to monitor changes in the shopping cart table
        shoppingCartTable.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (column == 1) { // Check if the edited column is the Quantity column
                DefaultTableModel model1 = (DefaultTableModel) shoppingCartTable.getModel();
                Object quantity = model1.getValueAt(row, column);
                System.out.println(quantity.toString());
                // Get the relative Product object and update the quantity using the setter method
                Product product = this.shoppingCart.getProductListCart().get(row);
                product.setAvailableItems((Integer) quantity);
            }
        });
        // Update the displayed information in labels
        updateInformation();
        // Add the bottom panel to the frame
        shoppingCartFrame.add(bottomPanel);
        // Set the frame as visible
        shoppingCartFrame.setVisible(true);
    }

    // Creates a JTable to display the contents of the shopping cart, including product details, quantity, and price.
    public JTable createTable(HashMap<Product,Integer> productListSystem) {
        // Define column names for the table
        String[] columnNames = {"Product ", "Quantity", "Price"};
        // Create a DefaultTableModel with specified column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Iterate through the entries in the productListSystem
        for (Map.Entry<Product, Integer> entry : productListSystem.entrySet()) {
            // Extract the product and its quantity
            Product product = entry.getKey();
            int count = entry.getValue();

            // Check if the product is of type Electronics and an instance of Electronics class
            if (product.getProductType().equals("Electronics") && product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                // Create an array of row data for Electronics product
                Object[] rowDataElectronics = {
                        // HTML formatting to display product details in multiple lines
                        "<html>" + electronics.getProductId() + "<br>" +
                                electronics.getProductName() + "<br>" + electronics.getBrand() + ", " + electronics.getWarrantyPeriod() + " months" + "</html>",
                        count,
                        electronics.getPrice() + " £"};
                // Add the row data to the table model
                model.addRow(rowDataElectronics);
            } else if (product.getProductType().equals("Clothing") && product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                // Create an array of row data for Clothing product
                Object[] rowDataClothing = {
                        // HTML formatting to display product details in multiple lines
                        "<html>" + clothing.getProductId() + "<br>" +
                                clothing.getProductName() + "<br>" + clothing.getSize() + ", " + clothing.getColor() + "</html>",
                        count,
                        clothing.getPrice() + " £"};
                // Add the row data to the table model
                model.addRow(rowDataClothing);
            }
        }
        // Create a JTable with the populated table model
        JTable table = new JTable(model);
        // Disable cell editing in the table
        table.setDefaultEditor(Object.class, null);
        // Set row height for better visual representation
        table.setRowHeight(100);
        // Return the created JTable
        return table;
    }

    // Updates the information displayed in the shopping cart frame, including total, discounts, and final total.
    public void updateInformation() {
        // Update the label displaying the total cost of products in the shopping cart
        totalNumLabel.setText(String.format("%.2f £", calculateTotal()));

        // Update the label displaying the discount for the first purchase (10%)
        tenDisNumLabel.setText(String.format("%.2f £", calculateTenDiscount()));

        // Check if any product in the cart has more than 3 available items for the discount
        if (this.shoppingCart.getProductListCart().stream().anyMatch(product -> product.getAvailableItems() > 3)) {
            // Update the label displaying the discount for having three items in the same category (20%)
            twentyDisNumLabel.setText(String.format("%.2f £", calculateTwentyDiscount()));
        } else {
            // If no discount is applicable, set the label to display 0.00 £
            twentyDisNumLabel.setText("0.00 £");
        }

        // Update the label displaying the final total after applying discounts
        finalTotalNumLabel.setText(String.format("%.2f £", calculateFinalTotal()));
    }

    // Iterates through each product in the cart, multiplies the price by the quantity,
    // Calculates the total cost of products in the shopping cart.
    public double calculateTotal() {
        // Variable to store the total cost
        double totalCost = 0.0;
        // Iterate through each product-entry pair in the shopping cart
        for (Map.Entry<Product,Integer> entry:shoppingCart.getproductListCart().entrySet()){
            // Calculate the cost of the current product by multiplying its price with the quantity
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        // Return the total cost
        return totalCost;
    }

    // Calculates a 10% discount on the total cost of products in the shopping cart.
    public double calculateTenDiscount() {
        // Calculate the discount amount by multiplying the total cost with the discount percentage (10%)
        return calculateTotal() * 0.1;
    }

    // Calculates a 20% discount on the total cost of products in the shopping cart
    public double calculateTwentyDiscount() {
        // Check if there are at least three items of the same category in the shopping cart
        if(shoppingCart.getproductListCart().entrySet().stream().anyMatch(e->e.getValue() >= 3)){
            // Calculate the discount amount by multiplying the total cost with the discount percentage (20%)
            return calculateTotal() * 0.2;
        }
        // No discount if the condition is not met
        return 0;
    }

    // Calculates the final total cost after applying discounts.
    public double calculateFinalTotal() {
        // Calculate the final total cost by subtracting discounts from the total cost
        double finalTotalCost = calculateTotal() - calculateTenDiscount() - calculateTwentyDiscount();
        return finalTotalCost;
    }
}