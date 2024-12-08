import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptView {
    private final Cart cart;

    public ReceiptView(Cart cart) {
        this.cart = cart;
    }

    public void display(JFrame parent) {
        StringBuilder receipt = new StringBuilder("Laña's Eatery Receipt\n");
        String dateToday = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String orderNumber = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        receipt.append("Order Number: ").append(orderNumber).append("\n");
        receipt.append("Date: ").append(dateToday).append("\n\n");
        receipt.append("Items:\n");

        cart.getItems().forEach(item -> receipt.append(String.format("%s x%d - ₱%.2f\n", item.getName(), item.getQuantity(), item.getPrice() * item.getQuantity())));
        receipt.append(String.format("\nTotal: ₱%.2f\n", cart.calculateTotal()));
        receipt.append("\nThank you for your order!");

        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setEditable(false);

        int option = JOptionPane.showOptionDialog(parent, new JScrollPane(receiptArea), "Order Receipt",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Save"}, "Save");

        if (option == JOptionPane.YES_OPTION) {
            String filename = "order_" + orderNumber + ".txt";

            try (FileWriter writer = new FileWriter(filename)) {
                writer.write(receipt.toString());
                JOptionPane.showMessageDialog(parent, "Receipt saved as " + filename);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Failed to save receipt: " + ex.getMessage());
            }
        }
    }
}
