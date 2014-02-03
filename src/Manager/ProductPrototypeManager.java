package Manager;

import Entities.*;
import Exceptions.*;
import Repos.Array.*;

public class ProductPrototypeManager {
    private ProductPrototypeRepoArray repo;

    public ProductPrototypeManager(ProductPrototypeRepoArray repo) {
        this.repo = repo;
    }

    public void validate(ProductAbstract product) throws MissingRolesException, InvalidInputException {
       if (product.getPrice() < 0) {
            throw new InvalidInputException("price < 0");
        }

        if (product.getDescription() == null) {
            throw new InvalidInputException("description");
        }

        if (product.getVendor().length() < 1) {
            throw new InvalidInputException("vendor");
        }
    }

    public void add(ProductPrototype item)
            throws ObjectAlreadyExistsException, MissingRolesException, InvalidInputException {
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

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }
}
