package Entities;

import java.util.ArrayList;

public class Order extends Item {
    private int timestamp;
    private boolean open = true;
    private String buyer;
    private String seller;
    private int amount;
    private ArrayList<Product> products;

    public Order(String id) {
        super(id);
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product[] getProducts() {
        return products.toArray(new Product[products.size()]);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Order(String id, String buyer, String seller) {
        super(id);
        this.products = new ArrayList<Product>();
        this.open = true;
        this.buyer = buyer;
        this.seller = seller;
        this.amount = 0;
    }
}
