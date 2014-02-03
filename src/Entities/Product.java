package Entities;

public class Product extends ProductAbstract {
    private int prototype;
    private int amount;

    public int getPrototype() {
        return prototype;
    }

    public void setPrototype(int prototype) {
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

    public Product(String id, int price, String name, String vendor, int prototype, int amount) {
        super(id, price, name, vendor);
        this.prototype = prototype;
        this.amount = amount;
    }
}
