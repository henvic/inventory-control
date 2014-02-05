package Entities;

public class Product extends ProductAbstract {
    private String prototype;

    public String getPrototype() {
        return prototype;
    }

    public void setPrototype(String prototype) {
        this.prototype = prototype;
    }

    public Product(String id, int price, String name, String vendor, String prototype, int amount) {
        super(id, price, amount, name, vendor);
        this.prototype = prototype;
    }
}
