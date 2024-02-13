// Import necessary Java Swing, awt, utility classes
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Define a class named WestminsterShoppingFrame that extends JFrame
// This class represents the graphical user interface for the Westminster Shopping System
public class WestminsterShoppingFrame extends JFrame {
    // Field to store the user's shopping cart
    private ShoppingCart userShoppingCart;

    // Main JFrame for the Westminster Shopping Center
    private JFrame westminsterFrame = new JFrame("Westminster Shopping Center");

    // Panels for organizing the layout of the GUI
    private JPanel topPanel, bottomPanel, detailPanel,
            detailPanel1, detailPanel2, detailPanel3,
            bottomPanel1, bottomPanel2;

    // JTable for displaying product information
    private JTable table;

    // ScrollPane to enable scrolling in the product table
    private JScrollPane scrollPane;

    // JLabels for displaying product details
    private JLabel idLabel, categoryLabel, nameLabel, extraLabel1, extraLabel2, availableItemsLabel;

    // String variable to store the selected option in the dropdown
    private String dropdownOption;

    // Product object to store the currently selected product
    private Product selectedProduct;

    // ArrayList to store the product list for the GUI
    private ArrayList<Product> productList;

    // Constructor for WestminsterShoppingFrame class, initializes the graphical user interface with the given list of products
    public WestminsterShoppingFrame(ArrayList<Product> products) {
        // Set the product list and create a new shopping cart
        this.productList = products;
        this.userShoppingCart=new ShoppingCart();

        // Set up the main JFrame
        westminsterFrame.setSize(1000, 700);
        westminsterFrame.setLayout(new GridLayout(2, 1));

        // Create panels for organizing the layout of the GUI
        topPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel = new JPanel(new GridLayout(2, 1));
        detailPanel = new JPanel(new GridLayout(1, 3));

        detailPanel1 = new JPanel(new GridBagLayout());
        detailPanel2 = new JPanel(new GridBagLayout());
        detailPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel1 = new JPanel();
        bottomPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        bottomPanel1.setLayout(new BoxLayout(bottomPanel1, BoxLayout.Y_AXIS));

        // Set margins and create an EmptyBorder
        int marginSize = 7;
        EmptyBorder emptyBorder = new EmptyBorder(marginSize, marginSize * 4, marginSize, 0);

        // Create JLabels for displaying product details
        idLabel = new JLabel();
        categoryLabel = new JLabel();
        nameLabel = new JLabel();
        extraLabel1 = new JLabel();
        extraLabel2 = new JLabel();
        availableItemsLabel = new JLabel();

        // Apply the EmptyBorder to the components
        bottomPanel1.setBorder(emptyBorder);
        idLabel.setBorder(emptyBorder);
        categoryLabel.setBorder(emptyBorder);
        nameLabel.setBorder(emptyBorder);
        extraLabel1.setBorder(emptyBorder);
        extraLabel2.setBorder(emptyBorder);
        availableItemsLabel.setBorder(emptyBorder);

        // Create JLabels and JComboBox for the top panel
        JLabel topPanelText = new JLabel("Select Product Category");
        JLabel bottomPanelText = new JLabel("Select Product - Details");

        String[] dropdownList = {"All", "Electronic", "Clothing"};
        JComboBox<String> dropdownMenu = new JComboBox<>(dropdownList);
        dropdownMenu.setPrototypeDisplayValue(dropdownList[0]);

        // Set the default dropdown option
        dropdownOption = (String) dropdownMenu.getSelectedItem();

        JButton shoppingCartButton = new JButton("Shopping Cart");
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        bottomPanel2.add(addToCartButton);

        // Add components to detail panels
        detailPanel1.add(topPanelText);
        detailPanel2.add(dropdownMenu);
        detailPanel3.add(shoppingCartButton);

        detailPanel.add(detailPanel1);
        detailPanel.add(detailPanel2);
        detailPanel.add(detailPanel3);

        // Create the product table and add it to a JScrollPane
        table = createTable(this.productList);
        scrollPane = new JScrollPane((table));

        // Action listener for dropdown menu changes
        dropdownMenu.addActionListener(e -> {
            dropdownOption = (String) dropdownMenu.getSelectedItem();
            System.out.println(dropdownOption);
            updateTableModel();
        });

        // Add components to top panel
        topPanel.add(detailPanel);
        topPanel.add(scrollPane);

        // Event listener for selecting a row in the table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                System.out.println("Item Selected " + selectedRow);
                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    selectedProduct = getProductList(this.productList, dropdownOption).get(modelRow);
                    updateDisplayPanel(selectedProduct);
                }
            }
        });

        // Event listener for the "Shopping Cart" button
        shoppingCartButton.addActionListener(e ->  {
            new ShoppingCartFrame(userShoppingCart);

        } );

        // Event listener for the "Add to Shopping Cart" button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct != null && selectedProduct.getAvailableItems()>0) {
                    userShoppingCart.addProduct(selectedProduct);
                    updateDisplayPanel(selectedProduct);
                }
                revalidate();
                repaint();
            }
        });

        // Add components to bottom panel
        bottomPanel1.add(bottomPanelText);
        bottomPanel1.add(idLabel);
        bottomPanel1.add(categoryLabel);
        bottomPanel1.add(nameLabel);
        bottomPanel1.add(extraLabel1);
        bottomPanel1.add(extraLabel2);
        bottomPanel1.add(availableItemsLabel);

        bottomPanel.add(bottomPanel1);
        bottomPanel.add(bottomPanel2);

        // Add panels to the main frame
        westminsterFrame.add(topPanel);
        westminsterFrame.add(bottomPanel);

        // Set frame visibility and default close operation
        westminsterFrame.setVisible(true);
        westminsterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to create and return a JTable based on the provided ArrayList of products
    public static JTable createTable(ArrayList<Product> productListSystem) {
        // Define column names for the table
        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        // Create a DefaultTableModel with the specified column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Populate the table model with data from the productListSystem
        for (Product product : productListSystem) {
            switch (product.getProductType()) {
                case "Electronics":
                    // If the product is of type Electronics, cast it to Electronics and extract relevant information
                    Electronics electronics = (Electronics) product;
                    // Create an Object array representing the row data for Electronics
                    Object[] rowDataElectronics = {
                            electronics.getProductId(),
                            electronics.getProductName(),
                            electronics.getProductType(),
                            electronics.getPrice(),
                            electronics.getBrand() + ", " + electronics.getWarrantyPeriod() + " months warranty"};
                    // Add the row data to the table model
                    model.addRow(rowDataElectronics);
                    break;
                case "Clothing":
                    // If the product is of type Clothing, cast it to Clothing and extract relevant information
                    Clothing clothing = (Clothing) product;
                    // Create an Object array representing the row data for Clothing
                    Object[] rowDataClothing = {
                            clothing.getProductId(),
                            clothing.getProductName(),
                            clothing.getProductType(),
                            clothing.getPrice(),
                            clothing.getSize() + ", " + clothing.getColor()};
                    // Add the row data to the table model
                    model.addRow(rowDataClothing);
                    break;
            }
        }
        // Create a JTable with the populated model
        JTable table = new JTable(model);
        // Disable cell editing in the table
        table.setDefaultEditor(Object.class, null);
        // Return the created table
        return table;
    }

    // Method to update the table model based on the selected product category in the dropdown menu
    public void updateTableModel() {
        // Get the current table model
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Clear existing rows in the model
        model.setRowCount(0);

        // Get the list of products based on the selected category in the dropdown menu
        ArrayList<Product> productList = getProductList(this.productList, dropdownOption);
        // Populate the table model with data from the updated productList
        for (Product product : productList) {
            switch (product.getProductType()) {
                case "Electronics":
                    // If the product is of type Electronics, cast it to Electronics and extract relevant information
                    Electronics electronics = (Electronics) product;
                    // Create an Object array representing the row data for Electronics
                    Object[] rowDataElectronics = {
                            electronics.getProductId(),
                            electronics.getProductName(),
                            electronics.getProductType(),
                            electronics.getPrice(),
                            electronics.getBrand() + ", " + electronics.getWarrantyPeriod() + " months warranty"};
                    // Add the row data to the table model
                    model.addRow(rowDataElectronics);
                    break;
                case "Clothing":
                    // If the product is of type Clothing, cast it to Clothing and extract relevant information
                    Clothing clothing = (Clothing) product;
                    // Create an Object array representing the row data for Clothing
                    Object[] rowDataClothing = {
                            clothing.getProductId(),
                            clothing.getProductName(),
                            clothing.getProductType(),
                            clothing.getPrice(),
                            clothing.getSize() + ", " + clothing.getColor()};
                    // Add the row data to the table model
                    model.addRow(rowDataClothing);
                    break;
            }
        }
    }

    // Method to update the display panel with information about the selected product
    public void updateDisplayPanel(Product product) {
        // Set the text of labels in the bottomPanel to display information about the selected product
        idLabel.setText("Product ID - " + product.getProductId());
        categoryLabel.setText("Category - " + product.getProductType());
        nameLabel.setText("Name - " + product.getProductName());
        availableItemsLabel.setText("Items available  - " + product.buyAvailableItems());

        // Check the product type to update additional information labels
        switch (product.getProductType()) {
            case "Electronics" -> {
                // If the product is of type Electronics, cast it to Electronics and extract relevant information
                Electronics electronics = (Electronics) product;
                // Set the text of extraLabel1 and extraLabel2 with Electronics-specific information
                extraLabel1.setText("Brand - " + electronics.getBrand());
                extraLabel2.setText("Warranty Period - " + electronics.getWarrantyPeriod());
            }
            case "Clothing" -> {
                // If the product is of type Clothing, cast it to Clothing and extract relevant information
                Clothing clothing = (Clothing) product;
                // Set the text of extraLabel1 and extraLabel2 with Clothing-specific information
                extraLabel1.setText("Size - " + clothing.getSize());
                extraLabel2.setText("Colour - " + clothing.getColor());
            }
        }
    }

    // Method to filter and return a list of products based on the selected category in the dropdown menu
    public ArrayList<Product> getProductList(ArrayList<Product> productListSystem, String dropdownOption) {
        // Create a new ArrayList to store the selected products
        ArrayList<Product> selectedProductList = new ArrayList<>();
        // Switch statement to determine the category and populate the selectedProductList accordingly
        switch (dropdownOption) {
            case "All":
                // If the option is "All," include all products in the selectedProductList
                selectedProductList = productListSystem;
                break;
            case "Electronic":
                // If the option is "Electronic," include only Electronics products in the selectedProductList
                for (Product product : productListSystem) {
                    if (product.getProductType().equals("Electronics")) {
                        selectedProductList.add(product);
                    }
                }
                break;
            case "Clothing":
                // If the option is "Clothing," include only Clothing products in the selectedProductList
                for (Product product: productListSystem) {
                    if (product.getProductType().equals("Clothing")){
                        selectedProductList.add(product);
                    }
                }
                break;
        }
        // Return the filtered list of selected products
        return selectedProductList;
    }
}