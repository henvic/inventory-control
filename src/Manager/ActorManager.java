package Manager;

import Entities.*;
import Exceptions.*;
import Repos.Array.*;

abstract public class ActorManager {
    private ActorRepoArray repo;

    public ActorManager(ActorRepoArray repo) {
        this.repo = repo;
    }

    public void add(Actor item) throws ObjectAlreadyExistsException {
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
