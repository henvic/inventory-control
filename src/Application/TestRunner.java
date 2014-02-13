package Application;

import Application.Tests.*;
import Exceptions.UnavailableRepoTypeException;

public class TestRunner {
    private Facade facade;
    private int errors = 0;
    private int total = 0;
    private String graph = "";

    public void test(boolean result, String what) {
        this.total += 1;

        if (result) {
            this.graph += ".";
            System.out.println(what + ": pass");
            return;
        }

        this.errors += 1;
        this.graph += "x";
        System.out.println(what + ": fail");
    }

    public void test(Object result, String what) {
        test((result != null), what);
    }

    public void run() {
        try {
            System.out.println("Running the inventory-control tests");

            new ActorTests(facade, this).runSuite();
            new ProductPrototypeTests(facade, this).runSuite();
            new ProductTests(facade, this).runSuite();
            new OrderTests(facade, this).runSuite();

            System.out.println("\n" + this.graph + "\n" +
                    "Total: " + this.total + "; Passing: " + (this.total - this.errors) + "; " +
                    "Failing: " + this.errors + ";");

            if (this.errors != 0) {
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema.\n" +
                    "É possível que o sistema esteja em um estado inconsistente.\n\n" + e);
        }
    }

    public TestRunner(Facade facade) {
        this.facade = facade;
    }

    public static void main (String[] args) {
        try {
            new TestRunner(new Facade()).run();
        } catch (UnavailableRepoTypeException ignore) {
            System.out.println("config.txt não contém tipo de repositório disponível.\n" +
                    "Use: array ou list ou file\n" +
                    "Note: no clean up is made for file.\n");
        }
    }
}
