package Application;

import java.util.UUID;

import Exceptions.*;
import Manager.*;
import Entities.*;
import Repos.Array.*;

public class Facade {
    private final char ACTOR = 'a';
    private final char ORDER = 'o';
    private final char PRODUCT_PROTOTYPE = 'p';
    private final char PRODUCT = 'P';

    private ProductPrototypeManager productPrototypeManager;
    private BuyerManager buyerManager;
    private SellerManager sellerManager;

    private String getUUID(char type) {
        return type + "-" + UUID.randomUUID().toString();
    }

    public String createActor(String name, String company, String email, String phone, String address,
                              boolean buyer, boolean seller)
            throws MissingRolesException, InvalidInputException {
        String id = this.getUUID(ACTOR);
        Actor actor;

        actor = new Actor(id, name, company, email, phone, address, buyer, seller);

        try {
            if (buyer) {
                buyerManager.add(actor);
            }

            if (seller) {
                sellerManager.add(actor);
            }
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

    public Actor getActor(String id) throws ObjectNotFoundException {
        try {
            return this.getSeller(id);
        } catch (ObjectNotFoundException ignore) {
        }

        try {
            return this.getBuyer(id);
        } catch (ObjectNotFoundException ignore) {
        }

        throw new ObjectNotFoundException();
    }

    public void updateActor(String id, String name, String company, String email, String phone, String address,
                             boolean buyer, boolean seller)
            throws ObjectNotFoundException, MissingRolesException, InvalidInputException {
        Actor actor = this.getActor(id);

        try {
            if (buyer && !actor.isBuyer()) {
                buyerManager.add(actor);
            }

            if (!buyer && actor.isBuyer()) {
                buyerManager.remove(id);
            }

            if (seller && !actor.isSeller()) {
                sellerManager.add(actor);
            }

            if (!seller && actor.isSeller()) {
                sellerManager.remove(id);
            }
        } catch (ObjectAlreadyExistsException ignore) { // can safely ignore
        }

        buyerManager.validate(name, company, email, phone, address, buyer, seller);

        actor.setName(name);
        actor.setCompany(company);
        actor.setEmail(email);
        actor.setPhone(phone);
        actor.setAddress(address);
        actor.setBuyer(buyer);
        actor.setSeller(seller);
    }

    public void removeBuyer(String id) throws ObjectNotFoundException {
        buyerManager.remove(id);
    }

    public void removeSeller(String id) throws ObjectNotFoundException {
        sellerManager.remove(id);
    }

    public void removeActor(String id) throws ObjectNotFoundException {
        // verify if actor exists in either the buyer / seller repo
        // and if not, let getActor throw a ObjectNotFoundException
        // which won't be catch here
        this.getActor(id);

        try {
            this.removeBuyer(id);
        } catch (ObjectNotFoundException ignore) {// can safely ignore
        }

        //it's necessary this second block, otherwise a actor who is
        //a seller but not a buyer won't get removed
        try {
            this.removeSeller(id);
        } catch (ObjectNotFoundException ignore) {// can safely ignore
        }
    }

    public String createProductPrototype(String id, int price, String description, String vendor)
            throws ObjectAlreadyExistsException, InvalidInputException {
        productPrototypeManager.add(new ProductPrototype(id, price, description, vendor));
        return id;
    }

    public ProductPrototype getProductPrototype(String id) throws ObjectNotFoundException {
        return productPrototypeManager.get(id);
    }

    public void updateProductPrototype(String id, int price, String description, String vendor)
            throws ObjectNotFoundException, InvalidInputException {
        ProductPrototype productPrototype = this.getProductPrototype(id);

        productPrototypeManager.validate(price, description, vendor);

        productPrototype.setPrice(price);
        productPrototype.setDescription(description);
        productPrototype.setVendor(vendor);
    }

    public void removeProductPrototype(String id) throws ObjectNotFoundException {
        productPrototypeManager.remove(id);
    }

    public Facade() {
        productPrototypeManager = new ProductPrototypeManager(new ProductPrototypeRepoArray());
        buyerManager = new BuyerManager(new ActorRepoArray());
        sellerManager = new SellerManager(new ActorRepoArray());
    }
}
