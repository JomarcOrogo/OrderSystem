import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartView {
    private final Cart cart;

    public CartView(Cart cart) {
        this.cart = cart;
    }

    public void display(JFrame parent) {
        JFrame cartFrame = new JFrame("View Cart");
        cartFrame.setSize(600, 400);

        // Define table column names
        String[] columnNames = {"Item", "Quantity", "Price"};

        // Populate table data from the cart
        Object[][] data = new Object[cart.getItems().size()][3];
        for (int i = 0; i < cart.getItems().size(); i++) {
            CartItem item = cart.getItems().get(i);
            data[i][0] = item.getName();
            data[i][1] = item.getQuantity();
            data[i][2] = String.format("P %.2f", item.getPrice() * item.getQuantity());
        }

        // Create the table model and JTable
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable cartTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        // Create a remove button
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedItem = (String) cartTable.getValueAt(selectedRow, 0);
                cart.removeItem(selectedItem);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(cartFrame, selectedItem + " removed from cart.");
            } else {
                JOptionPane.showMessageDialog(cartFrame, "No item selected.");
            }
        });

        // Layout for the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(removeButton);

        cartFrame.add(scrollPane, BorderLayout.CENTER);
        cartFrame.add(buttonPanel, BorderLayout.SOUTH);
        cartFrame.setLocationRelativeTo(parent);
        cartFrame.setVisible(true);
    }
}
