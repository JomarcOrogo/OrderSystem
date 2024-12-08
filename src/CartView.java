import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CartView {
    private final Cart cart;

    public CartView(Cart cart) {
        this.cart = cart;
    }

    public void display(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "View Cart", true);
        dialog.setSize(700, 500);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen

        // Table setup with checkboxes
        String[] columnNames = {"Select", "Item", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class; // First column for checkboxes
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only the checkbox column is editable
            }
        };

        for (CartItem item : cart.getItems()) {
            model.addRow(new Object[]{false, item.getName(), item.getQuantity(), String.format("₱%.2f", item.getPrice() * item.getQuantity())});
        }

        JTable cartTable = new JTable(model);

        // Adjust column widths
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // Select column (Checkbox)
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Item column
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Quantity column
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Price column

        JScrollPane scrollPane = new JScrollPane(cartTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel buttonPanel = new JPanel();
        JLabel totalLabel = new JLabel(String.format("Total: ₱%.2f", cart.calculateTotal()));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton removeButton = new JButton("Remove Selected");
        JButton checkoutButton = new JButton("Checkout");

        // ActionListener for removing selected items
        removeButton.addActionListener(e -> {
            ArrayList<Integer> rowsToRemove = new ArrayList<>();
            IntStream.range(0, model.getRowCount()).forEach(i -> {
                if ((boolean) model.getValueAt(i, 0)) { // If checkbox is selected
                    rowsToRemove.add(i);
                }
            });

            if (rowsToRemove.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No items selected for removal.");
                return;
            }

            // Remove items in reverse order to avoid shifting indices
            for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
                int rowIndex = rowsToRemove.get(i);
                String itemName = (String) model.getValueAt(rowIndex, 1);
                cart.removeItem(itemName);
                model.removeRow(rowIndex);
            }

            // Update the total
            totalLabel.setText(String.format("Total: ₱%.2f", cart.calculateTotal()));
        });

        // ActionListener for checkout button
        checkoutButton.addActionListener(e -> {
            if (cart.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Cart is empty!");
            } else {
                new ReceiptView(cart).display(parentFrame);
                dialog.dispose();
            }
        });

        buttonPanel.add(totalLabel);
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
