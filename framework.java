import java.util.*;

class ShoppingCart {
    private Map<String, Integer> cart = new HashMap<>();

    // synchronized ensures thread safety
    public synchronized void addItem(String product, int quantity) {
        cart.put(product, cart.getOrDefault(product, 0) + quantity);
        System.out.println(Thread.currentThread().getName() +
            " added " + quantity + " " + product + "(s).");
    }

    public synchronized void removeItem(String product) {
        if (cart.containsKey(product)) {
            cart.remove(product);
            System.out.println(Thread.currentThread().getName() +
                " removed " + product + ".");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    public synchronized void displayCart() {
        System.out.println("\n--- Shopping Cart ---");
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class Customer extends Thread {
    private ShoppingCart cart;

    Customer(String name, ShoppingCart cart) {
        super(name); // thread name
        this.cart = cart;
    }

    public void run() {
        cart.addItem("Laptop", 1);
        cart.addItem("Phone", 2);
        cart.removeItem("Laptop");
        cart.displayCart();
    }
}

public class ShoppingCartSystem {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Customer c1 = new Customer("Alice", cart);
        Customer c2 = new Customer("Bob", cart);

        c1.start();
        c2.start();
    }
}
