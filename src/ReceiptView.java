import javax.swing.*;
import java.awt.*;
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
        receipt.append("Order Number: ").append(System.currentTimeMillis() % 10000).append("\n");
        receipt.append("Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        receipt.append("Items:\n");

        cart.getItems().forEach(item -> receipt.append(String.format("%s x%d - P%.2f\n", item.getName(), item.getQuantity(), item.getPrice() * item.getQuantity())));
        receipt.append(String.format("\nTotal: P%.2f\n", cart.calculateTotal()));
        receipt.append("\nThank you for your order!");

        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setEditable(false);

        int option = JOptionPane.showOptionDialog(parent, new JScrollPane(receiptArea), "Order Receipt",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Save"}, "Save");

        if (option == JOptionPane.YES_OPTION) {
            try (FileWriter writer = new FileWriter("order_receipt.txt")) {
                writer.write(receipt.toString());
                JOptionPane.showMessageDialog(parent, "Receipt saved as order_receipt.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Failed to save receipt: " + ex.getMessage());
            }
        }
    }
}
