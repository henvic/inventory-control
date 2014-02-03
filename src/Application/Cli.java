package Application;

import Entities.*;
import Exceptions.*;

public class Cli {
    private Facade facade;

    private String createMockProductPrototype() {
        try {
            String id = facade.createProductPrototype("B001G5ZTLS", 10000, "Canon 5D Mark IV", "BH Photovideo");
            System.out.println("Protótipo de produto criado com ID = " + id);
            return id;
        } catch (ObjectAlreadyExistsException ignore) {
            return null;
        }
    }

    private void cantCreateMockActor() {
        try {
            facade.createActor("Joseph",
                    "",
                    "foo@example.com",
                    "+55114141414",
                    "5th Ave, 140 NYC",
                    false,
                    false);
        } catch (MissingRolesException ignore) {
            System.out.println("buyer & seller == false");
        }
    }

    private String createMockActor() {
        String id;

        try {
            id = facade.createActor("Joseph",
                    "",
                    "foo@example.com",
                    "+55114141414",
                    "5th Ave, 140 NYC",
                    true,
                    false);
            System.out.println("Ator criado com ID = " + id);

            return id;
        } catch (MissingRolesException ignore) {
        }

        return null;
    }

    private Actor readMockActor(String id) {
        Actor actor;

        try {
            actor = facade.getActor(id);
            System.out.println(actor);
            return actor;
        } catch (ObjectNotFoundException ignore) {
            System.out.println("Ator não encontrado.");
        }

        return null;
    }

    private boolean updateMockActor(String id) {
        try {
            facade.updateActor(id, "John", "PB", "foo@example.net", "1-414-141-444", "K 140, D.C.", true, false);
            return true;
        } catch (ObjectNotFoundException ignore) {
            return false;
        }
    }

    public boolean removeMockActor(String id) {
        try {
            facade.removeActor(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
            return false;
        }
    }

    public boolean cantCreateProductPrototype() {
        try {
            facade.createProductPrototype("B00CO8TBQ0",
                    32000,
                    "Intel Core i7-4770K Quad-Core 3.5 GHZ 8 MB Cache BX80646I74770K",
                    "Intel");
            return false;
        } catch (ObjectAlreadyExistsException ignore) {
            System.out.println("product prototype already exists");
            return true;
        }
    }

    public void run () {
        String temp;
        System.out.println("inventory-control");

        //test all crud operations

        //CRUD for actor
        this.cantCreateMockActor();
        temp = this.createMockActor();
        this.updateMockActor(temp);
        this.readMockActor(temp);

        //CRUD for productPrototype
        this.cantCreateProductPrototype();
        this.createMockProductPrototype();
        //test createProductPrototype
    }

    public Cli (Facade facade) {
        this.facade = facade;
    }

    public static void main (String[] args) {
        try {
            new Cli(new Facade()).run();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema.\n" +
                    "É possível que o sistema esteja em um estado inconsistente.\n");
            System.err.println(e);
        }
    }
}
