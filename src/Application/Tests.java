package Application;

import Entities.*;
import Exceptions.*;

public class Tests {
    private String providerPrototypeProductId = "B00CO8TBQ0";

    private Facade facade;

    private void test(boolean result, String what) {
        System.out.println(what + " = " + ((result) ? "ok" : "failed"));
    }

    private void test(Object result, String what) {
        test((result != null), what);
    }

    //actor tests
    private boolean cantCreateMockActorWithNoRole() {
        try {
            facade.createActor("Joseph",
                    "",
                    "foo@example.com",
                    "+55114141414",
                    "5th Ave, 140 NYC",
                    false,
                    false);
        } catch (MissingRolesException ignore) {
            return true;
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    private String createMockActor() {
        try {
            return facade.createActor("Joseph",
                    "",
                    "foo@example.com",
                    "+55114141414",
                    "5th Ave, 140 NYC",
                    true,
                    false);
        } catch (MissingRolesException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return null;
    }

    private Actor readMockActor(String id) {
        try {
            return facade.getActor(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    private boolean updateMockActor(String id) {
        try {
            facade.updateActor(id, "John", "PB", "foo@example.net", "1-414-141-444", "K 140, D.C.", true, false);
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (MissingRolesException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    private boolean removeMockActor(String id) {
        try {
            facade.removeActor(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    //product prototype tests
    private boolean cantCreateProductPrototypeThatExists() {
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

    private String createMockProductPrototype() {
        try {
            return facade.createProductPrototype(this.providerPrototypeProductId, 10000,
                    "Canon 5D Mark IV", "BH Photovideo");
        } catch (ObjectAlreadyExistsException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return null;
    }


    private boolean updateMockProductPrototype(String id) {
        try {
            facade.updateProductPrototype(id, 4000, "Canon 5D Mark IV", "Adorama");
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    private ProductPrototype readMockProductPrototype(String id) {
        try {
            return facade.getProductPrototype(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    private boolean removeMockProductPrototype(String id) {
        try {
            facade.removeProductPrototype(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public void run() {
        String temp;
        System.out.println("inventory-control tests");

        // CRUD for actor
        this.cantCreateMockActorWithNoRole();
        temp = this.createMockActor();
        this.updateMockActor(temp);
        this.readMockActor(temp);
        this.test(this.removeMockActor(temp), "removeMockActor");

        // CRUD for productPrototype
        temp = this.createMockProductPrototype();
        this.test(temp, "createMockProductPrototype");
        this.test(this.cantCreateProductPrototypeThatExists(), "cantCreateProductPrototypeThatExists");
        this.test(this.updateMockProductPrototype(temp), "updateMockProductPrototype");
        this.test(this.readMockProductPrototype(temp), "readMockProductPrototype");
        this.test(this.removeMockProductPrototype(temp), "removeMockProductPrototype");

        // CRUD for product

        // CRUD for order
    }

    public Tests(Facade facade) {
        this.facade = facade;
    }

    public static void main (String[] args) {
        try {
            new Tests(new Facade()).run();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema.\n" +
                    "É possível que o sistema esteja em um estado inconsistente.\n");
            System.err.println(e);
        }
    }
}
