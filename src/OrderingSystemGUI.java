import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderingSystemGUI {
    public static void main(String[] args) {
        Cart cart = new Cart();

        JFrame frame = new JFrame("Laña's Eatery Ordering System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        menuPanel.setBackground(new Color(255, 204, 0));

        String[][] menuItems = {
                {"Burger", "P 150.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\burger.jpg"},
                {"Fries", "P 60.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\fries.jpg"},
                {"Soda", "P 40.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\soda.jpg"},
                {"Pizza", "P 250.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\pizza.jpg"},
                {"Pasta", "P 180.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\pasta.jpg"},
                {"Salad", "P 120.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\salad.jpg"},
                {"Ice Cream", "P 100.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\ice cream.jpg"},
                {"Coffee", "P 90.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\coffee.jpg"},
                {"Chicken", "P 160.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\fried chicken.jpg"},
                {"Smoothie", "P 130.00", "C:\\Users\\jomar\\IdeaProjects\\OrderSystem\\OOP Final Project\\smoothie.jpg"}
        };

        for (String[] item : menuItems) {
            String name = item[0];
            double price = Double.parseDouble(item[1].substring(2)); // Remove "P"
            String imagePath = item[2];

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(new Color(255, 204, 0));

            ImageIcon itemImage = new ImageIcon(imagePath);
            JLabel imageLabel = new JLabel(itemImage);

            JLabel nameLabel = new JLabel(name + " - " + item[1]);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            nameLabel.setForeground(Color.RED);

            JButton addButton = new JButton("Add to Cart");
            addButton.setBackground(Color.RED);
            addButton.setForeground(Color.WHITE);
            addButton.addActionListener(e -> {
                String quantityStr = JOptionPane.showInputDialog("Enter quantity:");
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    cart.addItem(name, price, quantity);
                    JOptionPane.showMessageDialog(frame, name + " added to cart!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity.");
                }
            });

            itemPanel.add(imageLabel, BorderLayout.WEST);
            itemPanel.add(nameLabel, BorderLayout.CENTER);
            itemPanel.add(addButton, BorderLayout.SOUTH);

            menuPanel.add(itemPanel);
        }

        frame.add(menuPanel, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(255, 204, 0));

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.setBackground(new Color(0, 128, 255));
        viewCartButton.setForeground(Color.WHITE);
        viewCartButton.addActionListener(e -> new CartView(cart).display(frame));

        navigationPanel.add(viewCartButton);
        frame.add(navigationPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
