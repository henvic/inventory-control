package Repos.List;

import Entities.ProductPrototype;
import Interfaces.ProductPrototypeRepoInterface;

public class ProductPrototypeRepoList implements ProductPrototypeRepoInterface {
    private ProductPrototype current = null;
    private ProductPrototypeRepoList next = null;

    public ProductPrototypeRepoList() {
    }

    public ProductPrototypeRepoList(ProductPrototype item) {
        this.current = item;
    }

    public boolean add(ProductPrototype item) {
        if (this.next == null) {
            this.next = new ProductPrototypeRepoList(item);
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

    public ProductPrototype get(String id) {
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

    public boolean update(String id, int price, int amount, String name, String vendor) {
        ProductPrototype productPrototype = this.get(id);

        if (productPrototype != null) {
            productPrototype.setPrice(price);
            productPrototype.setAmount(amount);
            productPrototype.setName(name);
            productPrototype.setVendor(vendor);
            return true;
        }

        return false;
    }
}
