package Application.Tests;

import Application.Facade;
import Application.TestRunner;
import Entities.*;
import Exceptions.*;
import Interfaces.*;

public class ActorTests implements TestsInterface {
    private Facade facade;
    private TestRunner testRunner;

    public String createMockSeller() {
        try {
            return facade.createSeller("Joseph",
                    "",
                    "foo@example.com",
                    "+55114141414",
                    "5th Ave, 140 NYC");
        } catch (InvalidInputException ignore) {
        }

        return null;
    }

    public Actor readMockSeller(String id) {
        try {
            return facade.getSeller(id);
        } catch (ObjectNotFoundException ignore) {
        }

        return null;
    }

    public boolean updateMockSeller(String id) {
        try {
            facade.updateSeller(id, "John", "PB", "foo@example.net", "1-414-141-444", "K 140, D.C.");
            return true;
        } catch (ObjectNotFoundException ignore) {
        } catch (InvalidInputException ignore) {
        }

        return false;
    }

    public boolean removeMockSeller(String id) {
        try {
            facade.removeSeller(id);
            return true;
        } catch (ObjectNotFoundException ignore) {
        }

        return false;
    }

    public void runSuite() {
        String temp;

        // @todo add missing tests
        temp = this.createMockSeller();
        testRunner.test(temp, "createMockSeller");
        testRunner.test(this.updateMockSeller(temp), "updateMockSeller");
        testRunner.test(this.readMockSeller(temp), "readMockSeller");
        testRunner.test(this.removeMockSeller(temp), "removeMockSeller");
    }

    public ActorTests(Facade facade, TestRunner testRunner) {
        this.facade = facade;
        this.testRunner = testRunner;
    }
}
