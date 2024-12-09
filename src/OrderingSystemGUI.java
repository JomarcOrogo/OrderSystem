import javax.swing.*;
import java.awt.*;

public class OrderingSystemGUI {
    public static void main(String[] args) {
        Cart cart = new Cart();

        JFrame frame = new JFrame("Laña's Eatery Ordering System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Add MenuPanel
        MenuPanel menuPanel = new MenuPanel(cart);
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
