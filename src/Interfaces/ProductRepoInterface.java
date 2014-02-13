package Interfaces;

import Entities.Product;

public interface ProductRepoInterface extends ItemRepoInterface<Product> {
    public boolean update(String id, int price, String name, String vendor, int amount);
}