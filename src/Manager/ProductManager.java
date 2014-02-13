package Manager;

import Entities.Product;
import Exceptions.InvalidInputException;
import Exceptions.ObjectAlreadyExistsException;
import Exceptions.ObjectNotFoundException;
import Interfaces.ProductRepoInterface;

public class ProductManager {
    private ProductRepoInterface repo;

    public ProductManager(ProductRepoInterface repo) {
        this.repo = repo;
    }

    public void validate(int price, String name, String vendor, int amount) throws InvalidInputException {
        if (amount < 1) {
            throw new InvalidInputException("amount < 1");
        }

        if (price < 0) {
            throw new InvalidInputException("price < 0");
        }

        if (name == null) {
            throw new InvalidInputException("name");
        }

        if (vendor.length() < 1) {
            throw new InvalidInputException("vendor");
        }
    }

    public void validate(Product product) throws InvalidInputException {
        this.validate(product.getPrice(), product.getName(), product.getVendor(), product.getAmount());
    }

    public void add(Product item)
            throws ObjectAlreadyExistsException, InvalidInputException {
        this.validate(item);

        if (repo.has(item.getId())) {
            throw new ObjectAlreadyExistsException();
        }

        repo.add(item);
    }

    public Product get(String id) throws ObjectNotFoundException {
        Product product;

        product = repo.get(id);

        if (product == null) {
            throw new ObjectNotFoundException();
        }

        return product;
    }

    public void update(String id, int price, String name, String vendor, int amount)
            throws ObjectNotFoundException, InvalidInputException {
        this.validate(price, name, vendor, amount);
        repo.update(id, price, name, vendor, amount);
    }

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }
}
