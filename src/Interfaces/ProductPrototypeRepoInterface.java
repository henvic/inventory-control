package Interfaces;

import Entities.ProductPrototype;

public interface ProductPrototypeRepoInterface extends ItemRepoInterface<ProductPrototype> {
    public boolean update(String id, int price, int amount, String name, String vendor);
}
