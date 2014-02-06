package Application.Tests;

import Application.Facade;
import Application.TestRunner;
import Entities.*;
import Exceptions.*;
import Interfaces.*;

public class OrderTests implements Tests {
    private Facade facade;
    private TestRunner testRunner;

    private String providerPrototypeProductId = "order-foo";

    public String createMockOrder() {
        String buyer;
        String seller;

        try {
            buyer = facade.createActor("Buyer #1",
                    "Example Company",
                    "foo@example.com",
                    "+55 11 4444-4444",
                    "5th Ave, 140 NYC",
                    true,
                    false);

            seller = facade.createActor("Seller #2",
                    "",
                    "",
                    "+55 88 8888-8888",
                    "Cin, UFPE",
                    false,
                    true);
        } catch (Exception ignore) {
            return null;
        }

        try {
            return facade.createOrder(buyer, seller);
        } catch (Exception ignore) {
        }

        return null;
    }

    public boolean closeMockOrder(String id) {
        try {
            facade.closeOrder(id);
            return true;
        } catch (Exception ignore) {
        }

        return false;
    }

    public Order readMockOrder(String id) {
        try {
            return facade.getOrder(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    public boolean removeMockOrder(String id) {
        try {
            facade.removeOrder(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public boolean getEmptyProductsArrayForMockOrder(String id) {
        try {
            if (facade.getProductsFromOrder(id).length == 0) {
                return true;
            }
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public boolean addProductToMockOrder(String id) {
        String productPrototypeId;

        try {
            productPrototypeId = facade.createProductPrototype(this.providerPrototypeProductId,
                    50000, 10, "prototype", "undefined");
            facade.addProductToOrder(id, productPrototypeId, 10);
            return true;
        } catch (Exception ignore) {
        }

        return false;
    }

    public boolean removeProductFromMockOrder(String id) {
        try {
            facade.removeProductFromOrder(id, this.providerPrototypeProductId, 9);
            return true;
        } catch (Exception ignore) {
        }

        return false;
    }

    public void runSuite() {
        String temp;

        // @todo add missing tests
        temp = this.createMockOrder();
        testRunner.test(temp, "createMockOrder");
        testRunner.test(this.readMockOrder(temp), "readMockOrder");
        testRunner.test(this.getEmptyProductsArrayForMockOrder(temp), "getEmptyProductsArrayForMockOrder");
        testRunner.test(this.addProductToMockOrder(temp), "addProductToMockOrder");
        testRunner.test(this.removeProductFromMockOrder(temp), "removeProductFromMockOrder");
        testRunner.test(this.closeMockOrder(temp), "closeMockOrder");
        testRunner.test(this.removeMockOrder(temp), "removeMockOrder");
    }

    public OrderTests(Facade facade, TestRunner testRunner) {
        this.facade = facade;
        this.testRunner = testRunner;
    }
}
