package Manager;

import Entities.*;
import Exceptions.*;
import Repos.Array.*;

public class ProductPrototypeManager {
    private ProductPrototypeRepoArray repo;

    public ProductPrototypeManager(ProductPrototypeRepoArray repo) {
        this.repo = repo;
    }

    public void add(ProductPrototype item) throws ObjectAlreadyExistsException {
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
