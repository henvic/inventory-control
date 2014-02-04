package Application.Tests;

import Application.Facade;
import Application.TestRunner;
import Entities.*;
import Exceptions.*;
import Interfaces.*;

public class ActorTests implements Tests {
    private Facade facade;
    private TestRunner testRunner;

    public boolean cantCreateMockActorWithNoRole() {
        try {
            System.out.println("SKMADKMDASKMDSAKMMKDSA");
            facade.createActor("Joseph",
                    "",
                    "foo@example.com",
                    "+55114141414",
                    "5th Ave, 140 NYC",
                    false,
                    false);
            System.out.println("SKMADKMDASKMDSAKMMKDSA");
        } catch (MissingRolesException ignore) {
            return true;
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public String createMockActor() {
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

    public Actor readMockActor(String id) {
        try {
            return facade.getActor(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    public boolean updateMockActor(String id) {
        try {
            facade.updateActor(id, "John", "PB", "foo@example.net", "1-414-141-444", "K 140, D.C.", true, false);
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (MissingRolesException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public boolean removeMockActor(String id) {
        try {
            facade.removeActor(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public void runAll() {
        String temp;

        // @todo add missing tests
        testRunner.test(this.cantCreateMockActorWithNoRole(), "cantCreateMockActorWithNoRole");
        temp = this.createMockActor();
        testRunner.test(temp, "createMockActor");
        testRunner.test(this.updateMockActor(temp), "updateMockActor");
        testRunner.test(this.readMockActor(temp), "readMockActor");
        testRunner.test(this.removeMockActor(temp), "removeMockActor");
    }

    public ActorTests(Facade facade, TestRunner testRunner) {
        this.facade = facade;
        this.testRunner = testRunner;
    }
}
