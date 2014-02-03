package Entities;

public abstract class ProductAbstract extends Item {
    private int price;
    private String description;
    private String vendor;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    protected ProductAbstract(String id, int price, String description, String vendor) {
        super(id);
        this.price = price;
        this.description = description;
        this.vendor = vendor;
    }
}
