package Application;

import Application.Tests.*;

public class TestRunner {
    private Facade facade;
    private int errors = 0;
    private int total = 0;

    public void test(boolean result, String what) {
        this.total += 1;

        if (!result) {
            this.errors += 1;
        }

        System.out.println(what + ": " + ((result) ? "pass" : "fail"));
    }

    public void test(Object result, String what) {
        test((result != null), what);
    }

    public void run() {
        try {
            System.out.println("Running the inventory-control tests");

            new ActorTests(facade, this).runAll();
            new ProductPrototypeTests(facade, this).runAll();
            new ProductTests(facade, this).runAll();
            new OrderTests(facade, this).runAll();

            System.out.print("\nTotal: " + this.total + "; Passing: " + (this.total - this.errors) +
                    "; Failing: " + this.errors + ";");
            System.out.println((this.errors == 0) ? " Perfect!" : "");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema.\n" +
                    "É possível que o sistema esteja em um estado inconsistente.\n");
            System.err.println(e);
        }
    }

    public TestRunner(Facade facade) {
        this.facade = facade;
    }

    public static void main (String[] args) {
        new TestRunner(new Facade()).run();
    }
}
