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

        String[] columnNames = {"Item", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        JTable cartTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        cart.getItems().forEach(item -> model.addRow(new Object[]{
                item.getName(),
                item.getQuantity(),
                String.format("₱%.2f", item.getPrice() * item.getQuantity())
        }));

        JPanel buttonPanel = new JPanel();
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow != -1) {
                String itemName = (String) model.getValueAt(selectedRow, 0);
                cart.removeItem(itemName);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(dialog, "Item removed!");
            } else {
                JOptionPane.showMessageDialog(dialog, "No item selected!");
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> {
            if (cart.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Cart is empty!");
            } else {
                new ReceiptView(cart).display(parentFrame);
                dialog.dispose();
            }
        });

        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
