import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartView {
    private final Cart cart;

    public CartView(Cart cart) {
        this.cart = cart;
    }

    public void display(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "View Cart", true);
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Item", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        double grandTotal = 0.0;

        for (CartItem item : cart.getItems()) {
            grandTotal += item.getPrice() * item.getQuantity();

            model.addRow(new Object[]{
                    item.getName(),
                    item.getQuantity(),
                    String.format("₱%.2f", item.getPrice())
            });
        }

        // Add grand total row
        model.addRow(new Object[]{"Total", "", String.format("₱%.2f", grandTotal)});

        JTable cartTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with checkout button
        JPanel buttonPanel = new JPanel();
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> {
            if (cart.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Cart is empty!");
            } else {
                new ReceiptView(cart).display(parentFrame);
                dialog.dispose();
            }
        });

        buttonPanel.add(checkoutButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
