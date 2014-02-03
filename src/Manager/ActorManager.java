package Manager;

import Entities.*;
import Exceptions.*;
import Repos.Array.*;

abstract public class ActorManager {
    private ActorRepoArray repo;

    public ActorManager(ActorRepoArray repo) {
        this.repo = repo;
    }

    public void validate(Actor actor) throws MissingRolesException, InvalidInputException {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (actor.getName().length() < 1) {
            throw new InvalidInputException("name");
        }

        if (actor.getCompany() == null) {
            throw new InvalidInputException("company");
        }

        if (!actor.getEmail().matches(emailPattern)) {
            throw new InvalidInputException("email");
        }

        if (actor.getPhone().length() < 1) {
            throw new InvalidInputException("phone");
        }

        if (actor.getAddress().length() < 1) {
            throw new InvalidInputException("address");
        }

        if (!actor.isBuyer() && !actor.isSeller()) {
            throw new MissingRolesException();
        }
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
