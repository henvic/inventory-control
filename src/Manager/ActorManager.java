package Manager;

import Entities.*;
import Exceptions.*;
import Interfaces.ActorRepoInterface;

public class ActorManager {
    private ActorRepoInterface repo;

    public ActorManager(ActorRepoInterface repo) {
        this.repo = repo;
    }

    public void validate(String name, String company, String email, String phone, String address)
            throws InvalidInputException {
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
    }

    public void validate(Actor actor) throws InvalidInputException {
        this.validate(actor.getName(), actor.getCompany(), actor.getEmail(), actor.getPhone(), actor.getAddress());
    }

    public void add(Actor item)
            throws ObjectAlreadyExistsException, InvalidInputException {
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

    public void update(String id, String name, String company, String email, String phone, String address)
            throws ObjectNotFoundException, InvalidInputException {
        this.validate(name, company, email, phone, address);

        repo.update(id, name, company, email, phone, address);
    }

    public void remove(String id) throws ObjectNotFoundException {
        boolean removed = repo.remove(id);

        if (!removed) {
            throw new ObjectNotFoundException();
        }
    }
}
