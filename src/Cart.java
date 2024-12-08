import java.util.ArrayList;

public class Cart {
    private final ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(String name, double price, int quantity) {
        boolean itemExists = false;

        // Check if the item already exists in the cart
        for (CartItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                // Update the quantity if the item exists
                item.setQuantity(item.getQuantity() + quantity);
                itemExists = true;
                break;
            }
        }

        // Add as a new item if it doesn't exist
        if (!itemExists) {
            items.add(new CartItem(name, price, quantity));
        }
    }

    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public ArrayList<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public void clear() {
        items.clear();
    }
}
