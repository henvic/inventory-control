package Entities;

public abstract class ProductAbstract extends Item {
    private int price;
    private int amount;
    private String name;
    private String vendor;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    protected ProductAbstract(String id, int price, int amount, String name, String vendor) {
        super(id);
        this.price = price;
        this.amount = amount;
        this.name = name;
        this.vendor = vendor;
    }
}
