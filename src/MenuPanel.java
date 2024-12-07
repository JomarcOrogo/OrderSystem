import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private Cart cart;

    public MenuPanel(Cart cart) {
        this.cart = cart;
        setLayout(new GridLayout(0, 2));

        String[][] menuItems = {
                {"Burger", "5.99"},
                {"Fries", "2.99"},
                {"Soda", "1.99"}
        };

        for (String[] item : menuItems) {
            String name = item[0];
            double price = Double.parseDouble(item[1]);

            JLabel itemLabel = new JLabel(name + " - $" + price);
            JButton addButton = new JButton("Add to Cart");

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

            add(itemLabel);
            add(addButton);
        }
    }
}
