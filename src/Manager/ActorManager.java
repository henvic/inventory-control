package Manager;

import Entities.*;
import Exceptions.*;
import Repos.Array.*;

public class ActorManager {
    private ActorRepoArray repo;

    public ActorManager(ActorRepoArray repo) {
        this.repo = repo;
    }

    public void validate(String name, String company, String email, String phone, String address,
                         boolean buyer, boolean seller) throws MissingRolesException, InvalidInputException {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (name.length() < 1) {
            throw new InvalidInputException("name");
        }

        if (company == null) {
            throw new InvalidInputException("company");
        }

        if (email.length() != 0 && !email.matches(emailPattern)) {
            throw new InvalidInputException("email");
        }

        if (phone.length() < 1) {
            throw new InvalidInputException("phone");
        }

        if (address.length() < 1) {
            throw new InvalidInputException("address");
        }

        if (!buyer && !seller) {
            throw new MissingRolesException();
        }
    }

    public void validate(Actor actor) throws MissingRolesException, InvalidInputException {
        this.validate(actor.getName(), actor.getCompany(), actor.getEmail(), actor.getPhone(),
                actor.getAddress(), actor.isBuyer(), actor.isSeller());
    }

    public void add(Actor item)
            throws ObjectAlreadyExistsException, MissingRolesException, InvalidInputException {
        this.validate(item);

        if (repo.has(item.getId())) {
            throw new ObjectAlreadyExistsException();
        }

        repo.add(item);
    }

    public Actor get(String id) throws ObjectNotFoundException {
        Actor actor;

        actor = repo.get(id);

        if (actor == null) {
            throw new ObjectNotFoundException();
        }

        return actor;
    }

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }
}
