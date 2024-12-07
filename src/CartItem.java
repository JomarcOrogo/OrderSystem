public class CartItem {
    private final String name;
    private final double price;
    private final int quantity;
    private static int cartItems = 0;

    public CartItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCartItems() {return cartItems;}
}
