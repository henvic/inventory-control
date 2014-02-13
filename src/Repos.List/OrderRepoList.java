package Repos.List;

import Entities.Order;
import Entities.Product;
import Interfaces.OrderRepoInterface;

public class OrderRepoList implements OrderRepoInterface {
    private Order current = null;
    private OrderRepoList next = null;

    public OrderRepoList() {
    }

    public OrderRepoList(Order item) {
        this.current = item;
    }

    public boolean add(Order item) {
        if (this.next == null) {
            this.next = new OrderRepoList(item);
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

    public Order get(String id) {
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

    public boolean update(String id, String buyer, String seller) {
        Order order = this.get(id);

        if (order != null) {
            order.setBuyer(buyer);
            order.setSeller(seller);
            return true;
        }

        return false;
    }

    public void addProduct(String id, Product product) {
        this.get(id).addProduct(product);
    }
}
