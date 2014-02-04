package Application.Tests;

import Entities.*;
import Application.Facade;
import Exceptions.*;

public class ProductTests {
    private Facade facade;

    public String createMockProduct(ProductPrototype productPrototype) {
        try {
            return facade.createProduct(productPrototype, 1);
        } catch (ObjectAlreadyExistsException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return null;
    }

    public boolean updateMockProduct(String id) {
        try {
            facade.updateProduct(id, 4000, "Canon 5D Mark IV", "Adorama", 5);
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public Product readMockProduct(String id) {
        try {
            return facade.getProduct(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    public boolean removeMockProduct(String id) {
        try {
            facade.removeProduct(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public ProductTests(Facade facade) {
        this.facade = facade;
    }
}
