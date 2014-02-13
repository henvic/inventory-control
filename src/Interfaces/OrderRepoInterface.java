package Interfaces;

import Entities.Order;
import Entities.Product;

public interface OrderRepoInterface extends ItemRepoInterface<Order> {
    public boolean update(String id, String buyer, String seller);

    public void addProduct(String id, String productId);
}
