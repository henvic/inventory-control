package Repos.Array;

import Entities.Order;
import Entities.Product;
import Interfaces.OrderRepoInterface;

public class OrderRepoArray implements OrderRepoInterface {
    private Order[] orders;

    @Override
    public boolean add(Order order) {
        int length = orders.length;
        Order[] newOrders;

        for (int counter = 0; counter < length; counter += 1) {
            if (orders[counter] == null) {
                orders[counter] = order;
                return true;
            }
        }

        newOrders = new Order[length + 1];

        System.arraycopy(orders, 0, newOrders, 0, length);
        this.orders = newOrders;

        orders[length] = order;

        return true;
    }

    @Override
    public boolean remove(String id) {
        for (int counter = 0, length = orders.length; counter < length; counter += 1) {
            if (orders[counter] != null && orders[counter].getId().equalsIgnoreCase(id)) {
                orders[counter] = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public Order get(String id) {
        for (Order current : orders) {
            if (current != null && current.getId() == id) {
                return current;
            }
        }

        return null;
    }

    @Override
    public boolean has(String id) {
        return (this.get(id) != null);
    }

    public boolean replace(String id, Order order) {
        for (int counter = 0, length = orders.length; counter < length; counter += 1) {
            if (orders[counter] != null && orders[counter].getId().equalsIgnoreCase(id)) {
                orders[counter] = order;
                return true;
            }
        }

        return false;
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

    public void addProduct(String id, String product) {
        this.get(id).addProduct(product);
    }

    public OrderRepoArray() {
        this.orders = new Order[1];
    }
}