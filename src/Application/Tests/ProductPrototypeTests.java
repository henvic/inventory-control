package Application.Tests;

import Entities.*;
import Application.Facade;
import Exceptions.*;

public class ProductPrototypeTests {
    private Facade facade;

    private String providerPrototypeProductId = "B00CO8TBQ0";

    public boolean cantCreateProductPrototypeThatExists() {
        try {
            facade.createProductPrototype(this.providerPrototypeProductId,
                    32000,
                    "Intel Core i7-4770K Quad-Core 3.5 GHZ 8 MB Cache BX80646I74770K",
                    "Intel");
        } catch (ObjectAlreadyExistsException ignore) {
            return true;
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public String createMockProductPrototype() {
        try {
            return facade.createProductPrototype(this.providerPrototypeProductId, 10000,
                    "Canon 5D Mark IV", "BH Photovideo");
        } catch (ObjectAlreadyExistsException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return null;
    }


    public boolean updateMockProductPrototype(String id) {
        try {
            facade.updateProductPrototype(id, 4000, "Canon 5D Mark IV", "Adorama");
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public ProductPrototype readMockProductPrototype(String id) {
        try {
            return facade.getProductPrototype(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    public boolean removeMockProductPrototype(String id) {
        try {
            facade.removeProductPrototype(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public ProductPrototypeTests(Facade facade) {
        this.facade = facade;
    }
}
