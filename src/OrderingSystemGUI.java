import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderingSystemGUI {

    public static void main(String[] args) {
        Cart cart = new Cart();  // Create a new cart object

        // JFrame setup for the GUI
        JFrame frame = new JFrame("Fast Food Ordering System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 2));

        // Example menu items
        String[][] menuItems = {
                {"Burger", "5.99"},
                {"Fries", "2.99"},
                {"Soda", "1.99"}
        };

        // Adding menu items to the panel with buttons
        for (String[] item : menuItems) {
            String name = item[0];
            double price = Double.parseDouble(item[1]);

            JLabel itemLabel = new JLabel(name + " - $" + price);
            JButton addButton = new JButton("Add to Cart");

            // Add action listener for the button to add item to the cart
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String quantityStr = JOptionPane.showInputDialog(null, "Enter quantity:");
                    try {
                        int quantity = Integer.parseInt(quantityStr);
                        cart.addItem(name, price, quantity);
                        JOptionPane.showMessageDialog(null, name + " added to cart!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity.");
                    }
                }
            });

            menuPanel.add(itemLabel);
            menuPanel.add(addButton);
        }

        // Add menu panel to the frame
        frame.add(menuPanel, BorderLayout.CENTER);

        // Button to view cart contents
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<CartItem> items = cart.getItems();  // Get the list of items in the cart
                if (items.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Cart is empty!");
                } else {
                    StringBuilder cartContents = new StringBuilder("Cart Contents:\n");
                    for (CartItem item : items) {
                        cartContents.append(String.format("%s x%d - $%.2f\n",
                                item.getName(), item.getQuantity(), item.getPrice() * item.getQuantity()));
                    }
                    cartContents.append(String.format("Total: $%.2f", cart.calculateTotal()));
                    JOptionPane.showMessageDialog(frame, cartContents.toString());
                }
            }
        });

        // Navigation panel with the "View Cart" button
        JPanel navigationPanel = new JPanel();
        navigationPanel.add(viewCartButton);
        frame.add(navigationPanel, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
    }
}
