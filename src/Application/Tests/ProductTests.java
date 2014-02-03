package Application.Tests;

import Application.Facade;
import Exceptions.*;

public class ProductTests {
    private Facade facade;

    private String providerProductId = "B00CO8TBQ0";

    public boolean cantCreateProductThatExists() {
        try {
            facade.createProduct(this.providerProductId,
                    32000,
                    "Intel Core i7-4770K Quad-Core 3.5 GHZ 8 MB Cache BX80646I74770K",
                    "Intel");
        } catch (ObjectAlreadyExistsException ignore) {
            return true;
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public String createMockProduct() {
        try {
            return facade.createProduct(this.providerProductId, 10000,
                    "Canon 5D Mark IV", "BH Photovideo");
        } catch (ObjectAlreadyExistsException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return null;
    }


    public boolean updateMockProduct(String id) {
        try {
            facade.updateProduct(id, 4000, "Canon 5D Mark IV", "Adorama");
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public Entities.Product readMockProduct(String id) {
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
