package Application;

import Application.Tests.*;

public class TestRunner {
    private ProductPrototypeTests productPrototypeTests;
    private ProductTests productTests;
    private ActorTests actorTests;

    private void test(boolean result, String what) {
        System.out.println(what + ": " + ((result) ? "pass" : "fail"));
    }

    private void test(Object result, String what) {
        test((result != null), what);
    }

    public void run() {
        String temp;
        System.out.println("inventory-control tests");

        // CRUD for actor
        //@todo add missing tests
        this.test(actorTests.cantCreateMockActorWithNoRole(), "cantCreateMockActorWithNoRole");
        temp = actorTests.createMockActor();
        this.test(temp, "createMockActor");
        this.test(actorTests.updateMockActor(temp), "updateMockActor");
        this.test(actorTests.readMockActor(temp), "readMockActor");
        this.test(actorTests.removeMockActor(temp), "removeMockActor");

        // CRUD for productPrototype
        //@todo add missing tests
        temp = productPrototypeTests.createMockProductPrototype();
        this.test(temp, "createMockProductPrototype");
        this.test(productPrototypeTests.cantCreateProductPrototypeThatExists(), "cantCreateProductPrototypeThatExists");
        this.test(productPrototypeTests.updateMockProductPrototype(temp), "updateMockProductPrototype");
        this.test(productPrototypeTests.readMockProductPrototype(temp), "readMockProductPrototype");
        this.test(productPrototypeTests.removeMockProductPrototype(temp), "removeMockProductPrototype");

        // CRUD for product
        //@todo add missing tests
        temp = productTests.createMockProduct();
        this.test(temp, "createMockProduct");
        this.test(productTests.cantCreateProductThatExists(), "cantCreateProductThatExists");
        this.test(productTests.updateMockProduct(temp), "updateMockProduct");
        this.test(productTests.readMockProduct(temp), "readMockProduct");
        this.test(productTests.removeMockProduct(temp), "removeMockProduct");

        // CRUD for order
        //@todo add missing tests
    }

    public TestRunner(Facade facade) {
        this.productPrototypeTests = new ProductPrototypeTests(facade);
        this.productTests = new ProductTests(facade);
        this.actorTests = new ActorTests(facade);
    }

    public static void main (String[] args) {
        try {
            new TestRunner(new Facade()).run();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema.\n" +
                    "É possível que o sistema esteja em um estado inconsistente.\n");
            System.err.println(e);
        }
    }
}
