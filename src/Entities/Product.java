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
}
