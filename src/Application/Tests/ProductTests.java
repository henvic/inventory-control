package Application.Tests;

import Application.Facade;
import Application.TestRunner;
import Entities.*;
import Exceptions.*;
import Interfaces.*;

public class ProductTests implements TestsInterface {
    private Facade facade;
    private TestRunner testRunner;

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

    public void runSuite() {
        String temp;

        temp = this.createMockProduct(new ProductPrototype("foo", 4000, 100, "60D", "Canon"));
        testRunner.test(temp, "createMockProduct");
        testRunner.test(this.updateMockProduct(temp), "updateMockProduct");
        testRunner.test(this.readMockProduct(temp), "readMockProduct");
        testRunner.test(this.removeMockProduct(temp), "removeMockProduct");
    }

    public ProductTests(Facade facade, TestRunner testRunner) {
        this.facade = facade;
        this.testRunner = testRunner;
    }
}
