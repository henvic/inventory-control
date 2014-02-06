package Repos.Array;

import Interfaces.ItemRepo;

import Entities.Actor;

public class ActorRepoArray implements ItemRepo<Actor> {
    private Actor[] actors;

    @Override
    public boolean add(Actor actor) {
        int length = actors.length;
        Actor[] newActors;

        for (int counter = 0; counter < length; counter += 1) {
            if (actors[counter] == null) {
                actors[counter] = actor;
                return true;
            }
        }

        newActors = new Actor[length + 1];

        System.arraycopy(actors, 0, newActors, 0, length);
        this.actors = newActors;

        actors[length] = actor;

        return true;
    }

    @Override
    public boolean remove(String id) {
        for (int counter = 0, length = actors.length; counter < length; counter += 1) {
            if (actors[counter] != null && actors[counter].getId().equalsIgnoreCase(id)) {
                actors[counter] = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public Actor get(String id) {
        for (Actor current : actors) {
            if (current != null && current.getId().equalsIgnoreCase(id)) {
                return current;
            }
        }

        return null;
    }

    @Override
    public boolean has(String id) {
        return (this.get(id) != null);
    }

    public boolean replace(String id, Actor actor) {
        for (int counter = 0, length = actors.length; counter < length; counter += 1) {
            if (actors[counter] != null && actors[counter].getId().equalsIgnoreCase(id)) {
                actors[counter] = actor;
                return true;
            }
        }

        return false;
    }

    public ActorRepoArray() {
        this.actors = new Actor[1];
    }
}