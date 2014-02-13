package Manager;

import Entities.Order;
import Exceptions.InvalidInputException;
import Exceptions.MissingRolesException;
import Exceptions.ObjectAlreadyExistsException;
import Exceptions.ObjectNotFoundException;
import Interfaces.OrderRepoInterface;
import Entities.*;

public class OrderManager {
    private OrderRepoInterface repo;

    private ActorManager buyerManager;
    private ActorManager sellerManager;

    public OrderManager(OrderRepoInterface repo, ActorManager buyerManager, ActorManager sellerManager) {
        this.repo = repo;
        this.buyerManager = buyerManager;
        this.sellerManager = sellerManager;
    }

    public void validate(int timestamp, String buyer, String seller)
            throws InvalidInputException {
        if (timestamp < 0) {
            throw new InvalidInputException("timestamp < 0");
        }

        try {
            buyerManager.get(buyer);
        } catch (ObjectNotFoundException ignore) {
            throw new InvalidInputException("buyer");
        }

        try {
            sellerManager.get(seller);
        } catch (ObjectNotFoundException ignore) {
            throw new InvalidInputException("seller");
        }
    }

    public void validate(Order order) throws InvalidInputException {
        this.validate(order.getTimestamp(), order.getBuyer(), order.getSeller());
    }

    public void add(Order item)
            throws ObjectAlreadyExistsException, InvalidInputException {
        this.validate(item);

        if (repo.has(item.getId())) {
            throw new ObjectAlreadyExistsException();
        }

        repo.add(item);
    }

    public Order get(String id) throws ObjectNotFoundException {
        Order Order;

        Order = repo.get(id);

        if (Order == null) {
            throw new ObjectNotFoundException();
        }

        return Order;
    }

    public void update(String id, String buyer, String seller)
            throws ObjectNotFoundException, MissingRolesException, InvalidInputException {
        Order order = this.get(id);

        this.validate(order.getTimestamp(), buyer, seller);
        repo.update(id, buyer, seller);
    }

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }

    public void addProduct(String orderId, Product product) {
        repo.addProduct(orderId, product.getId());
    }
}
