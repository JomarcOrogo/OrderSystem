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
        dialog.setLocationRelativeTo(null);

        // Table setup with checkboxes
        String[] columnNames = {"Select", "Item", "Quantity", "Price"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        for (CartItem item : cart.getItems()) {
            model.addRow(new Object[]{false, item.getName(), item.getQuantity(), String.format("₱%.2f", item.getPrice() * item.getQuantity())});
        }

        JTable cartTable = new JTable(model);

        // Adjust column widths
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel buttonPanel = new JPanel();
        JLabel totalLabel = new JLabel(String.format("Total: ₱%.2f", cart.calculateTotal()));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton removeButton = new JButton("Remove Selected");
        JButton editButton = new JButton("Edit Quantity");
        JButton checkoutButton = new JButton("Checkout");

        // ActionListener for removing selected items
        removeButton.addActionListener(e -> {
            ArrayList<Integer> rowsToRemove = new ArrayList<>();
            IntStream.range(0, model.getRowCount()).forEach(i -> {
                if ((boolean) model.getValueAt(i, 0)) {
                    rowsToRemove.add(i);
                }
            });

            if (rowsToRemove.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "No items selected for removal.");
                return;
            }

            for (int i = rowsToRemove.size() - 1; i >= 0; i--) {
                int rowIndex = rowsToRemove.get(i);
                String itemName = (String) model.getValueAt(rowIndex, 1);
                cart.removeItem(itemName);
                model.removeRow(rowIndex);
            }

            totalLabel.setText(String.format("Total: ₱%.2f", cart.calculateTotal()));
        });

        // ActionListener for editing item quantity
        editButton.addActionListener(e -> {
            int selectedRow = -1;
            for (int i = 0; i < model.getRowCount(); i++) {
                if ((boolean) model.getValueAt(i, 0)) {
                    selectedRow = i;
                    break;
                }
            }

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "No item selected for editing.");
                return;
            }

            String itemName = (String) model.getValueAt(selectedRow, 1);
            CartItem item = cart.findItem(itemName);

            if (item != null) {
                String newQuantityStr = JOptionPane.showInputDialog(dialog, "Enter new quantity for " + itemName + ":");
                try {
                    int newQuantity = Integer.parseInt(newQuantityStr);

                    if (newQuantity > 0) {
                        item.setQuantity(newQuantity);
                        model.setValueAt(newQuantity, selectedRow, 2);
                        model.setValueAt(String.format("₱%.2f", item.getPrice() * newQuantity), selectedRow, 3);
                        totalLabel.setText(String.format("Total: ₱%.2f", cart.calculateTotal()));
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Quantity must be greater than 0.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid input. Please enter a valid number.");
                }
            }
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
        buttonPanel.add(editButton);
        buttonPanel.add(checkoutButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
