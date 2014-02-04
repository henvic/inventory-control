package Manager;

import Entities.Order;
import Exceptions.InvalidInputException;
import Exceptions.MissingRolesException;
import Exceptions.ObjectAlreadyExistsException;
import Exceptions.ObjectNotFoundException;
import Repos.Array.OrderRepoArray;

public class OrderManager {
    private OrderRepoArray repo;

    public OrderManager(OrderRepoArray repo) {
        this.repo = repo;
    }

    public void validate(int timestamp, String buyer, String seller)
            throws InvalidInputException {
        if (timestamp < 0) {
            throw new InvalidInputException("timestamp < 0");
        }

        if (buyer.length() < 1) {
            throw new InvalidInputException("buyer");
        }

        if (seller.length() < 1) {
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

    public void update(String id)
            throws ObjectNotFoundException, MissingRolesException, InvalidInputException {
        Order Order = this.get(id);

        this.validate(Order);

    }

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }
}
