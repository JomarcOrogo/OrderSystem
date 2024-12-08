import java.util.ArrayList;

public class Cart {
    private final ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    // Adds an item to the cart or updates quantity if it already exists
    public void addItem(String name, double price, int quantity) {
        CartItem existingItem = findItem(name);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.add(new CartItem(name, price, quantity));
        }
    }

    // Removes an item by name
    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    // Finds an item by name
    public CartItem findItem(String name) {
        for (CartItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    // Gets all items in the cart
    public ArrayList<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    // Clears all items from the cart
    public void clear() {
        items.clear();
    }

    // Calculates the total cost of the items in the cart
    public double calculateTotal() {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}
