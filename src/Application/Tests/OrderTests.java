package Application.Tests;

import Application.Facade;
import Application.TestRunner;
import Entities.*;
import Exceptions.*;
import Interfaces.*;

public class OrderTests implements Tests {
    private Facade facade;
    private TestRunner testRunner;

    public void runSuite() {
        // @todo add missing tests
    }

    public OrderTests(Facade facade, TestRunner testRunner) {
        this.facade = facade;
        this.testRunner = testRunner;
    }
}
