import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    // Add an item to the cart
    public void addItem(String name, double price, int quantity) {
        for (CartItem item : items) {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity); // Update quantity if item already exists
                return;
            }
        }
        items.add(new CartItem(name, price, quantity)); // Add new item if it doesn't exist
    }

    // Get all items in the cart
    public ArrayList<CartItem> getItems() {
        return items;
    }

    // Calculate total price of all items in the cart
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
