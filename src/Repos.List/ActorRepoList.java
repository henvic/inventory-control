package Repos.List;

import Entities.Actor;
import Interfaces.ActorRepoInterface;

public class ActorRepoList implements ActorRepoInterface {
    private Actor current = null;
    private ActorRepoList next = null;

    public ActorRepoList() {
    }

    public ActorRepoList(Actor item) {
        this.current = item;
    }

    public boolean add(Actor item) {
        if (this.next == null) {
            this.next = new ActorRepoList(item);
            return true;
        }

        this.next.add(item);
        return true;
    }

    public boolean remove(String id) {
        if (this.current != null && this.current.getId().equalsIgnoreCase(id)) {
            if (this.next != null) {
                this.current = this.next.current;
                this.next = this.next.next;
                return true;
            }

            this.current = null;
            return true;
        }

        if (this.next != null) {
            this.next.remove(id);
            return true;
        }

        return false;
    }

    public Actor get(String id) {
        if (this.current != null && this.current.getId().equalsIgnoreCase(id)) {
            return this.current;
        }

        if (this.next != null) {
            return this.next.get(id);
        }

        return null;
    }

    public boolean has(String id) {
        return this.get(id) != null;
    }
}
