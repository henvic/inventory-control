package Application.Tests;

import Application.Facade;
import Application.TestRunner;
import Entities.*;
import Exceptions.*;
import Interfaces.*;

public class ProductPrototypeTests implements TestsInterface {
    private Facade facade;
    private TestRunner testRunner;

    private String providerPrototypeProductId = "B00CO8TBQ0";

    public boolean cantCreateProductPrototypeThatExists() {
        try {
            facade.createProductPrototype(this.providerPrototypeProductId, 32000, 10,
                    "Intel Core i7-4770K Quad-Core 3.5 GHZ 8 MB Cache BX80646I74770K", "Intel");
        } catch (ObjectAlreadyExistsException ignore) {
            return true;
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public String createMockProductPrototype() {
        try {
            return facade.createProductPrototype(this.providerPrototypeProductId, 10000, 200,
                    "Canon 5D Mark IV", "BH Photovideo");
        } catch (ObjectAlreadyExistsException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return null;
    }


    public boolean updateMockProductPrototype(String id) {
        try {
            facade.updateProductPrototype(id, 4000, 30, "Canon 5D Mark IV", "Adorama");
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

    public void runSuite() {
        String temp;

        //@todo add missing tests
        temp = this.createMockProductPrototype();
        testRunner.test(temp, "createMockProductPrototype");
        testRunner.test(this.cantCreateProductPrototypeThatExists(), "cantCreateProductPrototypeThatExists");
        testRunner.test(this.updateMockProductPrototype(temp), "updateMockProductPrototype");
        testRunner.test(this.readMockProductPrototype(temp), "readMockProductPrototype");
        testRunner.test(this.removeMockProductPrototype(temp), "removeMockProductPrototype");
    }

    public ProductPrototypeTests(Facade facade, TestRunner testRunner) {
        this.facade = facade;
        this.testRunner = testRunner;
    }
}
