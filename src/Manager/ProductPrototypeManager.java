package Manager;

import Entities.*;
import Exceptions.*;
import Interfaces.ProductPrototypeRepoInterface;

public class ProductPrototypeManager {
    private ProductPrototypeRepoInterface repo;

    public ProductPrototypeManager(ProductPrototypeRepoInterface repo) {
        this.repo = repo;
    }

    public void validate(int price, int amount, String name, String vendor) throws InvalidInputException {
        if (price < 0) {
            throw new InvalidInputException("price < 0");
        }

        if (amount < 0) {
            throw new InvalidInputException("amount < 0");
        }

        if (name == null) {
            throw new InvalidInputException("name");
        }

        if (vendor.length() < 1) {
            throw new InvalidInputException("vendor");
        }
    }

    public void validate(ProductAbstract product) throws InvalidInputException {
        this.validate(product.getPrice(), product.getAmount(), product.getName(), product.getVendor());
    }

    public void add(ProductPrototype item)
            throws ObjectAlreadyExistsException, InvalidInputException {
        this.validate(item);

        if (repo.has(item.getId())) {
            throw new ObjectAlreadyExistsException();
        }

        repo.add(item);
    }

    public ProductPrototype get(String id) throws ObjectNotFoundException {
        ProductPrototype productPrototype;

        productPrototype = repo.get(id);

        if (productPrototype == null) {
            throw new ObjectNotFoundException();
        }

        return productPrototype;
    }

    public void update(String id, int price, int amount, String name, String vendor)
            throws ObjectNotFoundException, InvalidInputException {
        this.validate(price, amount, name, vendor);
        repo.update(id, price, amount, name, vendor);
    }

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }
}
