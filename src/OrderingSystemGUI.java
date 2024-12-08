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

        JPanel menuPanel = new JPanel(new GridLayout(0, 2, 20, 20));
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
            itemPanel.setPreferredSize(new Dimension(250, 150)); // Smaller item panels

            // Centering the image in the panel with a yellow background
            JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center layout for image
            imagePanel.setBackground(new Color(255, 204, 0)); // Set yellow background
            ImageIcon itemImage = new ImageIcon(imagePath);
            Image img = itemImage.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Scale image
            itemImage = new ImageIcon(img);
            JLabel imageLabel = new JLabel(itemImage);
            imagePanel.add(imageLabel);

            JLabel nameLabel = new JLabel(name + " - " + item[1]);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Smaller font
            nameLabel.setForeground(Color.RED);

            JButton addButton = new JButton("Add to Cart");
            addButton.setPreferredSize(new Dimension(150, 30)); // Smaller button size
            addButton.setBackground(Color.RED);
            addButton.setForeground(Color.WHITE);
            addButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font
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

            itemPanel.add(imagePanel, BorderLayout.NORTH); // Add image panel to the North
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
        viewCartButton.setPreferredSize(new Dimension(150, 40)); // Smaller button size
        viewCartButton.addActionListener(e -> new CartView(cart).display(frame));

        navigationPanel.add(viewCartButton);
        frame.add(navigationPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
