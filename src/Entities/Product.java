package Entities;

public class Product extends ProductAbstract {
    private String prototype;
    private int amount;

    public String getPrototype() {
        return prototype;
    }

    public void setPrototype(String prototype) {
        this.prototype = prototype;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void increaseAmount(int diff) {
        this.setAmount(this.amount + diff);
    }

    public void decreaseAmount(int diff) {
        this.increaseAmount(- diff);
    }

    public Product(String id, int price, String name, String vendor, String prototype, int amount) {
        super(id, price, name, vendor);
        this.prototype = prototype;
        this.amount = amount;
    }
}
