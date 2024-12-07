import javax.swing.*;
import java.awt.*;

public class CartView {
    private final Cart cart;

    public CartView(Cart cart) {
        this.cart = cart;
    }

    public void display(JFrame parent) {
        JFrame cartFrame = new JFrame("View Cart");
        cartFrame.setSize(500, 400);

        DefaultListModel<String> model = new DefaultListModel<>();
        cart.getItems().forEach(item -> model.addElement(item.getName() + " x" + item.getQuantity() + " - P" + (item.getPrice() * item.getQuantity())));

        JList<String> cartList = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(cartList);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int selectedIndex = cartList.getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedItem = cart.getItems().get(selectedIndex).getName();
                cart.removeItem(selectedItem);
                model.remove(selectedIndex);
                JOptionPane.showMessageDialog(cartFrame, selectedItem + " removed from cart.");
            } else {
                JOptionPane.showMessageDialog(cartFrame, "No item selected.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);

        cartFrame.add(scrollPane, BorderLayout.CENTER);
        cartFrame.add(buttonPanel, BorderLayout.SOUTH);
        cartFrame.setLocationRelativeTo(parent);
        cartFrame.setVisible(true);
    }
}
