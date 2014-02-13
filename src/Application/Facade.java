package Application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.UUID;

import Exceptions.*;
import Interfaces.*;
import Manager.*;
import Entities.*;
import Repos.Array.*;
import Repos.File.*;
import Repos.List.*;

public class Facade {
    private final char REPO_TYPE_ARRAY = 'A';
    private final char REPO_TYPE_LIST = 'L';
    private final char REPO_TYPE_FILE = 'F';
    private final char REPO_TYPE_ERROR = 'E';

    private final char ACTOR = 'a';
    private final char ORDER = 'o';
    private final char PRODUCT_PROTOTYPE = 'p';
    private final char PRODUCT = 'P';

    private ProductPrototypeManager productPrototypeManager;
    private ProductManager productManager;
    private BuyerManager buyerManager;
    private SellerManager sellerManager;
    private OrderManager orderManager;

    private String getUUID(char type) {
        return type + "-" + UUID.randomUUID().toString();
    }

    public String createBuyer(String name, String company, String email, String phone, String address)
            throws InvalidInputException {
        String id = this.getUUID(ACTOR);
        Actor actor;

        actor = new Actor(id, name, company, email, phone, address);

        buyerManager.validate(name, company, email, phone, address);

        try {
            buyerManager.add(actor);
        } catch (ObjectAlreadyExistsException ignore) {
        //safe to assume it's never going to happen
        }

        return id;
    }

    public String createSeller(String name, String company, String email, String phone, String address)
            throws InvalidInputException {
        String id = this.getUUID(ACTOR);
        Actor actor;

        actor = new Actor(id, name, company, email, phone, address);

        sellerManager.validate(name, company, email, phone, address);

        try {
            sellerManager.add(actor);
        } catch (ObjectAlreadyExistsException ignore) {
            //safe to assume it's never going to happen
        }

        return id;
    }

    public Actor getBuyer(String id) throws ObjectNotFoundException {
        return buyerManager.get(id);
    }

    public Actor getSeller(String id) throws ObjectNotFoundException {
        return sellerManager.get(id);
    }

    public void updateBuyer(String id, String name, String company, String email, String phone, String address)
            throws ObjectNotFoundException, InvalidInputException {
        buyerManager.update(id, name, company, email, phone, address);
    }

    public void updateSeller(String id, String name, String company, String email, String phone, String address)
            throws ObjectNotFoundException, InvalidInputException {
        sellerManager.update(id, name, company, email, phone, address);
    }

    public void removeBuyer(String id) throws ObjectNotFoundException {
        buyerManager.remove(id);
    }

    public void removeSeller(String id) throws ObjectNotFoundException {
        sellerManager.remove(id);
    }

    public String createProductPrototype(String id, int price, int amount, String name, String vendor)
            throws ObjectAlreadyExistsException, InvalidInputException {
        productPrototypeManager.add(new ProductPrototype(id, price, amount, name, vendor));
        return id;
    }

    public ProductPrototype getProductPrototype(String id) throws ObjectNotFoundException {
        return productPrototypeManager.get(id);
    }

    public void updateProductPrototype(String id, int price, int amount, String name, String vendor)
            throws ObjectNotFoundException, InvalidInputException {
        productPrototypeManager.update(id, price, amount, name, vendor);
    }

    public void removeProductPrototype(String id) throws ObjectNotFoundException {
        productPrototypeManager.remove(id);
    }

    public String createProduct(ProductPrototype productPrototype, int amount)
            throws ObjectAlreadyExistsException, InvalidInputException {
        int price = productPrototype.getPrice();
        String prototype = productPrototype.getId();
        String name = productPrototype.getName();
        String vendor = productPrototype.getVendor();
        String id = this.getUUID(PRODUCT);

        productManager.add(new Product(id, price, name, vendor, prototype, amount));
        return id;
    }

    public Product getProduct(String id) throws ObjectNotFoundException {
        return productManager.get(id);
    }

    public void updateProduct(String id, int price, String name, String vendor, int amount)
            throws ObjectNotFoundException, InvalidInputException {
        productManager.update(id, price, name, vendor, amount);
    }

    public void removeProduct(String id) throws ObjectNotFoundException {
        productManager.remove(id);
    }

    public String createOrder(String buyer, String seller)
            throws ObjectAlreadyExistsException, ObjectNotFoundException, InvalidInputException {
        String id = getUUID(ORDER);

        orderManager.add(new Order(id, buyer, seller));
        return id;
    }

    public Order getOrder(String id) throws ObjectNotFoundException {
        return orderManager.get(id);
    }

    public void updateOrder(String id, String buyer, String seller)
            throws ObjectNotFoundException, MissingRolesException, InvalidInputException {
        orderManager.update(id, buyer, seller);
    }

    private String setProductToOrder(String orderId, String productPrototypeId, int amount, boolean diff)
            throws ObjectNotFoundException, MissingRolesException, InvalidInputException,
            OrderAlreadyClosed, ObjectAlreadyExistsException {
        Order order = this.getOrder(orderId);
        ProductPrototype productPrototype = this.getProductPrototype(productPrototypeId);
        Product product = null;
        Product eachProduct;

        if (!order.isOpen()) {
            throw new OrderAlreadyClosed();
        }

        //see if the product exists on the order already
        for (String each : this.getProductsFromOrder(orderId)) {
            eachProduct = this.getProduct(each);
            if (eachProduct.getPrototype().equalsIgnoreCase(productPrototypeId)) {
                product = eachProduct;

                if (diff) {
                    amount = amount + product.getAmount();
                }

                break;
            }
        }

        if (product == null) {
            product = this.getProduct(this.createProduct(productPrototype, amount));
            orderManager.addProduct(orderId, product);
        }

        if (amount < 1 || productPrototype.getAmount() > amount + product.getAmount()) {
            throw new InvalidInputException("Invalid amount");
        }

        return product.getId();
    }

    public String setProductToOrder(String orderId, String productPrototypeId, int amount) throws Exception {
        return this.setProductToOrder(orderId, productPrototypeId, amount, false);
    }

    public String addProductToOrder(String orderId, String productPrototypeId, int amount) throws Exception {
        return this.setProductToOrder(orderId, productPrototypeId, amount, true);
    }

    public String removeProductFromOrder(String orderId, String productPrototypeId, int amount) throws Exception {
        return this.setProductToOrder(orderId, productPrototypeId, - amount, true);
    }

    public String[] getProductsFromOrder(String id) throws ObjectNotFoundException {
        return orderManager.get(id).getProducts();
    }

    public void closeOrder(String id) throws ObjectNotFoundException, NotEnoughProductsExceptionOnStock {
        String[] products;
        ProductPrototype productPrototype;
        Order order = this.getOrder(id);
        Product product;

        products = order.getProducts();

        //first, verify if there are enough products on the stock to make the order
        for (String each : products) {
            product = this.getProduct(each);
            if (this.getProductPrototype(product.getPrototype()).getAmount() < product.getAmount()) {
                throw new NotEnoughProductsExceptionOnStock();
            }
        }

        for (String each : products) {
            product = this.getProduct(each);
            productPrototype = this.getProductPrototype(product.getPrototype());
            productPrototype.setAmount(productPrototype.getAmount() - product.getAmount());
        }

        order.setOpen(false);
        order.setTimestamp(((Long) System.currentTimeMillis()).intValue());
    }

    public void removeOrder(String id) throws ObjectNotFoundException {
        orderManager.remove(id);
    }

    private char getRepoType() {
        String type = "";
        BufferedReader in;

        try {
            in = new BufferedReader(new FileReader("config.txt"));

            while (in.ready()) {
                type += in.readLine();
            }

            in.close();
        } catch (Exception ignore) {
        }

        if (type.length() > 0) {
            return type.toUpperCase().charAt(0);
        }

        return REPO_TYPE_ERROR;
    }

    public Facade() throws UnavailableRepoTypeException {
        ProductPrototypeRepoInterface productPrototypeRepo;
        ProductRepoInterface productRepo;
        ActorRepoInterface buyerRepo;
        ActorRepoInterface sellerRepo;
        OrderRepoInterface orderRepo;

        switch (getRepoType()) {
            case REPO_TYPE_ARRAY:
                productPrototypeRepo = new ProductPrototypeRepoArray();
                productRepo = new ProductRepoArray();
                buyerRepo = new ActorRepoArray();
                sellerRepo = new ActorRepoArray();
                orderRepo = new OrderRepoArray();
                break;
            case REPO_TYPE_LIST:
                productPrototypeRepo = new ProductPrototypeRepoList();
                productRepo = new ProductRepoList();
                buyerRepo = new ActorRepoList();
                sellerRepo = new ActorRepoList();
                orderRepo = new OrderRepoList();
                break;
            case REPO_TYPE_FILE:
                productPrototypeRepo = new ProductPrototypeRepoFile("db/product-prototypes.xls");
                productRepo = new ProductRepoFile("db/products.xls");
                buyerRepo = new ActorRepoArray();
                sellerRepo = new ActorRepoArray();
                orderRepo = new OrderRepoFile("db/order.xls");
                break;
            default:
                throw new UnavailableRepoTypeException();
        }

        productPrototypeManager = new ProductPrototypeManager(productPrototypeRepo);
        productManager = new ProductManager(productRepo);
        buyerManager = new BuyerManager(buyerRepo);
        sellerManager = new SellerManager(sellerRepo);
        orderManager = new OrderManager(orderRepo, buyerManager, sellerManager);
    }
}
