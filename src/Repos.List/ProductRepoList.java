package Repos.List;

import Entities.Product;
import Interfaces.ProductRepoInterface;

public class ProductRepoList implements ProductRepoInterface {
    private Product current = null;
    private ProductRepoList next = null;

    public ProductRepoList() {
    }

    public ProductRepoList(Product item) {
        this.current = item;
    }

    public boolean add(Product item) {
        if (this.next == null) {
            this.next = new ProductRepoList(item);
            return true;
        }

        this.next.add(item);
        return true;
    }

    public boolean remove(String id) {
        if (this.current != null && this.current.getId().equalsIgnoreCase(id)) {
            if (this.next != null) {
                this.current = this.next.current;
                this.next = this.next.next;
                return true;
            }

            this.current = null;
            return true;
        }

        if (this.next != null) {
            this.next.remove(id);
            return true;
        }

        return false;
    }

    public Product get(String id) {
        if (this.current != null && this.current.getId().equalsIgnoreCase(id)) {
            return this.current;
        }

        if (this.next != null) {
            return this.next.get(id);
        }

        return null;
    }

    public boolean has(String id) {
        return this.get(id) != null;
    }

    public boolean update(String id, int price, String name, String vendor, int amount) {
        Product product = this.get(id);

        if (product != null) {
            product.setAmount(amount);
            product.setPrice(price);
            product.setName(name);
            product.setVendor(vendor);
            return true;
        }

        return false;
    }
}
