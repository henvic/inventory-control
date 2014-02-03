package Entities;

public abstract class ProductAbstract extends Item {
    private int price;
    private String name;
    private String vendor;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    protected ProductAbstract(String id, int price, String name, String vendor) {
        super(id);
        this.price = price;
        this.name = name;
        this.vendor = vendor;
    }
}
