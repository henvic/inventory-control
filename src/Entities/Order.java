package Entities;

import java.util.ArrayList;

public class Order extends Item {
    private int timestamp;
    private boolean open = true;
    private String buyer;
    private String seller;
    private ArrayList<Product> products;

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

    public Product[] getProducts() {
        return products.toArray(new Product[products.size()]);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Order(String id) {
        super(id);
    }

    public Order(String id, String buyer, String seller) {
        super(id);
        this.products = new ArrayList<Product>();
        this.open = true;
        this.buyer = buyer;
        this.seller = seller;
    }

    public Order(String id, int timestamp, boolean open, String buyer, String seller, ArrayList<Product> products) {
        super(id);
        this.timestamp = timestamp;
        this.open = open;
        this.buyer = buyer;
        this.seller = seller;
        this.products = products;
    }
}
